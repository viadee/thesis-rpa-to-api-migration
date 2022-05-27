package org.example.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.ExternalTaskClientBuilder;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.example.data.model.ApiTopic;
import org.example.data.model.VarMapping;
import org.example.data.repo.ApiTopicRepo;
import org.example.data.repo.VarMappingRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class GenericApiWorker {

    @Autowired
    private Environment environment;

    @Autowired
    private ApiTopicRepo apiTopicRepo;

    @Autowired
    private VarMappingRepo varMappingRepo;

    protected static final String API_WORKER = "API-Worker " + UUID.randomUUID();
    private static final Logger LOG = LoggerFactory.getLogger(GenericApiWorker.class);
    protected ExternalTaskClient externalTaskClient;
    protected List<ApiTopic> apiTopicList = new ArrayList<>();
    protected List<TopicSubscriptionBuilder> apiSubscriptions = new ArrayList();

    public void start() {
        if(this.externalTaskClient==null){
            this.initClient();
            this.apiSubscriptions.forEach(TopicSubscriptionBuilder::open);
        }
        this.externalTaskClient.start();
    }

    public void stop() {
        if (this.externalTaskClient != null) {
            this.externalTaskClient.stop();
        }
    }

    public void restart() {
        this.externalTaskClient.stop();
        this.apiSubscriptions.removeAll(this.apiSubscriptions);
        this.initClient();
        this.apiSubscriptions.forEach(TopicSubscriptionBuilder::open);
        this.externalTaskClient.start();
    }

    protected void initClient(){
        ExternalTaskClientBuilder clientBuilder = ExternalTaskClient.create().baseUrl(environment.getProperty("camunda.bpm.client.base-url")).asyncResponseTimeout(10000L).disableAutoFetching().maxTasks(1).workerId(API_WORKER);
        this.externalTaskClient = clientBuilder.build();
        this.apiTopicList = apiTopicRepo.findAll();
        for(int i = 0; i<apiTopicList.size(); i++){
            String topic = apiTopicList.get(i).getId();
            List<String> botVars = varMappingRepo.findBotVarsByTopic(topic);
            System.out.println(botVars);
            this.apiSubscriptions.add(externalTaskClient.subscribe(topic).lockDuration(TimeUnit.MINUTES.toMillis(30L)).variables(botVars.toArray(new String[0]))
                    .handler(this.getExternalTaskHandler(apiTopicList.get(i))));
            LOG.info("Listening to API-Topic: " + topic);
        }
        if(apiTopicList.isEmpty()){
            LOG.info("No API-Topic to listen to.");
        }
    }

    protected ExternalTaskHandler getExternalTaskHandler(ApiTopic apiTopic){
        return (task, service) -> {
            try {
                System.out.println("API Request for " + apiTopic.getId() + " received.");
                // Assign values to api variables from bot variables according to the mapping established
                List<VarMapping> inVarMappings = varMappingRepo.findInVarsOfTopic(apiTopic.getId());
                Map<String, String> mappedInValues = new HashMap<>();
                inVarMappings.forEach(varMapping -> task.getVariable(varMapping.getBotVar()));
                inVarMappings.forEach(varMapping -> mappedInValues.put(varMapping.getApiVar(),
                        task.getVariable(varMapping.getBotVar())));

                System.out.println(mappedInValues);

                // Issue rest call with the values for the api variables and retrieve api output
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<JSONObject> responseEntity =
                        restTemplate.postForEntity(apiTopic.getRestEndpoint(), mappedInValues, JSONObject.class);
                JSONObject outVarValues = responseEntity.getBody();

                System.out.println(outVarValues);

                // Map back the output of the api variables to conform to the expected output in the process engine
                List<VarMapping> outVarMappings = varMappingRepo.findOutVarsOfTopic(apiTopic.getId());
                Map<String, String> outVarValuesMap = new ObjectMapper().readValue(outVarValues.toJSONString(), HashMap.class);
                Map<String, Object> mappedOutValues = new HashMap<>();
                outVarMappings.forEach(varMapping -> mappedOutValues.put(varMapping.getBotVar(),
                                outVarValuesMap.get(varMapping.getApiVar())));

                System.out.println(mappedOutValues);

                // write variables and their values to the process engine as expected
                GenericApiWorker.LOG.info("Marking External Task with Id {} as completed in the process engine", task.getId());
                service.complete(task, mappedOutValues);

            } catch (Exception e){
                System.out.println(e.getMessage());
                service.handleBpmnError(task, "bpmn-error", "CPL-ID could not be found via API.");
                LOG.warn(String.format("Starting an API job failed for External Task with Id %s, sending it to agent for double-check.", task.getId()));
            }
        };
    }

}
