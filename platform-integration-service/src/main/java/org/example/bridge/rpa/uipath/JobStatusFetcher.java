package org.example.bridge.rpa.uipath;

import org.example.bridge.rpa.RPARestService;
import org.example.worker.RPABotWorkerFindCPLID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

public class JobStatusFetcher {
    private static final Logger LOG = LoggerFactory.getLogger(JobStatusFetcher.class);
    @Autowired
    protected RPABotWorkerFindCPLID taskAdapter;
    @Autowired
    protected RPARestService rpaRestService;
    @Value("${org.camunda.bpm.rpa.uipath-api.polling.poll-size:13}")
    protected Integer pollSize;

    public JobStatusFetcher() {
    }

    @Scheduled(
            fixedRateString = "${org.camunda.bpm.rpa.uipath-api.polling.rate-ms:4000}",
            initialDelayString = "${org.camunda.bpm.rpa.uipath-api.polling.init-delay-ms:4000}"
    )
    public void pollStatusUpdates() {
        LOG.trace("Polling for UiPath jobs' status");
        if (this.taskAdapter != null && this.rpaRestService != null) {
            JobStatus[] jobsStatus = this.rpaRestService.getRpaJobStatus(this.taskAdapter.getJobIds((long)this.pollSize));
            if (jobsStatus != null) {
                JobStatus[] var2 = jobsStatus;
                int var3 = jobsStatus.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    JobStatus jobStatus = var2[var4];
                    JobUtil.handleJobStatusResponse(jobStatus, this.taskAdapter);
                }
            }
        }

    }
}
