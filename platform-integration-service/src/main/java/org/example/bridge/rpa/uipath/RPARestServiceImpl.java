package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.example.bridge.rpa.RequestHandler;
import org.example.bridge.properties.BridgeProperties;
import org.example.bridge.rpa.RPARestService;
import org.example.bridge.rpa.uipath.auth.AuthToken;
import org.example.bridge.rpa.uipath.auth.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RPARestServiceImpl implements RPARestService {
    private static final Logger LOG = LoggerFactory.getLogger(RPARestServiceImpl.class);
    @Autowired
    HttpClient httpClient;
    @Autowired
    BridgeProperties properties;
    @Autowired
    AuthToken token;

    public RPARestServiceImpl() {
    }

    public Job startRpaJob(String processKey, String inputVariables) {
        BridgeProperties uiPathApiProperties = this.properties;
        try {
            Release release = this.getReleaseByProcessKey(processKey);
            StartInfo job = new StartInfo();
            job.setReleaseKey(release.getKey());
            job.setInputArguments(inputVariables);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(job);
            //StringEntity json = new StringEntity(jsonString);

            HttpEntity json = new ByteArrayEntity(jsonString.getBytes("UTF-8"));

            String url = uiPathApiProperties.getApiUrl() + "Jobs/UiPath.Server.Configuration.OData.StartJobs";
            LOG.debug("Sending request to UiPath for starting a job with URL {} and payload {}", url, jsonString);
            HttpUriRequest startJob = RequestBuilder.post(url).addHeader("Authorization", "Bearer " + this.token.getAccessToken()).addHeader(uiPathApiProperties.getContentTypeHeader()).addHeader(uiPathApiProperties.getTenantHeader()).addHeader(uiPathApiProperties.getOrgUnitIdHeader()).setEntity(json).build();
            JobResponse jobResponse = (JobResponse) RequestHandler.sendRequest(startJob, this.httpClient, 201, JobResponse.class);
            Job startedJob = jobResponse.getValue().length == 0 ? null : jobResponse.getValue()[0];

            return startedJob;
        } catch (Exception var13) {
            throw new IllegalStateException("Error while starting job in UiPath for process " + processKey, var13);
        }
    }

    protected Release getReleaseByProcessKey(String processKey) throws IOException {
        String url = this.properties.getApiUrl() + "Releases?$filter=ProcessKey%20eq%20%27" + processKey + "%27";
        LOG.debug("Sending request to UiPath for getting a release key for process {} with URL {}", processKey, url);
        HttpUriRequest getReleases = RequestBuilder.get(url).addHeader("Authorization", "Bearer " + this.token.getAccessToken()).addHeader(this.properties.getContentTypeHeader()).addHeader(this.properties.getTenantHeader()).addHeader(this.properties.getOrgUnitIdHeader()).addHeader(this.properties.getFolderPathHeader()).build();
        ReleaseResponse releaseResponse = (ReleaseResponse)RequestHandler.sendRequest(getReleases, this.httpClient, 200, (s) -> {
            return "Error while getting release for process with message " + s;
        }, ReleaseResponse.class);
        if (releaseResponse.getValue().length == 0) {
            LOG.warn("No releases found for process {}", processKey);
            throw new IllegalStateException("No releases could be found for process " + processKey);
        } else {
            return releaseResponse.getValue()[0];
        }
    }

    public JobStatus[] getRpaJobStatus(Collection<Long> jobIds) {
        if (jobIds.isEmpty()) {
            return new JobStatus[0];
        } else {
            String filter = null;
            filter = (String)jobIds.stream().map(String::valueOf).collect(Collectors.joining(" or Id eq ", "Id eq ", ""));
            return this.getFilteredJobs(filter);
        }
    }

    public JobStatus[] getRpaJobStatus(String creationTime) {
        if (creationTime == null) {
            return new JobStatus[0];
        } else {
            String filter = "CreationTime ge " + ZonedDateTime.parse(creationTime).minusSeconds(10L).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            return this.getFilteredJobs(filter);
        }
    }

    protected JobStatus[] getFilteredJobs(String filter) {
        BridgeProperties uiPathApiProperties = this.properties;
        try {
            String stateFilter = null;
            stateFilter = "State eq '" + String.join("' or State eq '", JobUtil.STATES) + "'";

            String url = String.format("%sJobs?$select=Key,State,Id,Info,OutputArguments&$filter=(%s) and (%s)", uiPathApiProperties.getApiUrl(), stateFilter, filter).replace(" ", "%20");
            LOG.debug("Sending request for job status to UiPath url {}", url);
            HttpUriRequest getStatus = RequestBuilder.get(url).addHeader("Authorization", "Bearer " + this.token.getAccessToken()).addHeader(uiPathApiProperties.getContentTypeHeader()).addHeader(uiPathApiProperties.getTenantHeader()).addHeader(uiPathApiProperties.getOrgUnitIdHeader()).build();
            return ((JobStatusResponsePolling)RequestHandler.sendRequest(getStatus, this.httpClient, 200, JobStatusResponsePolling.class)).getValue();
        } catch (Exception var5) {
            LOG.warn("Error while getting status for jobs in UiPath for filter " + filter, var5);
            return new JobStatus[0];
        }
    }
}
