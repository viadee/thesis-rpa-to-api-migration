package org.example.worker;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.commons.utils.StringUtil;
import org.example.bridge.rpa.RPARestService;
import org.example.bridge.rpa.telemetry.MetricsCollector;
import org.example.bridge.rpa.uipath.Job;
import org.example.bridge.rpa.variables.VariableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@ExternalTaskSubscription("FindCPLIDRobot")
public class RPABotWorkerFindCPLID implements ExternalTaskHandler {
    @Autowired
    private RPARestService rpaRestService;

    private static final Logger LOG = LoggerFactory.getLogger(ExternalTaskService.class);
    protected Map<Long, TaskHandler> botMap = Collections.synchronizedMap(new LinkedHashMap());
    protected MetricsCollector metricsCollector;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService){
        System.out.println("RPA Request for FindCPLIDRobot received.");
        try {
            String botName = "FindCPLIDRobot";
            String botVars = VariableConverter.toJsonString(
                    externalTask.getAllVariablesTyped(false));
            Job executionJob =
            rpaRestService.startRpaJob(botName, botVars);
            if(executionJob != null){
                this.botMap.put(executionJob.getId(), new RPABotWorkerFindCPLID.TaskHandler(externalTask, externalTaskService, executionJob.getCreationTime(), this.metricsCollector));
            } else {
                LOG.debug("No RPA bot started for External Task with Id {}, unlocking it for a later retry in the process engine", externalTask.getId());
                externalTaskService.unlock(externalTask);
            }
        } catch (Exception e) {
            LOG.warn(String.format("Starting an RPA job failed for External Task with Id %s, marking it as failed in the process engine", externalTask.getId(), e));
            externalTaskService.handleFailure(externalTask, "Starting an RPA job failed", StringUtil.getStackTrace(e), 0, 0L);
        }
    }

    public void botCompleted(Long botId, Map<String, Object> variables) {
        if (this.handlesJobId(botId)) {
            ((RPABotWorkerFindCPLID.TaskHandler)this.botMap.get(botId)).complete(variables);
            this.botMap.remove(botId);
        }

    }

    public void botFailed(Long botId, String errorMessage, String errorDetails) {
        if (this.handlesJobId(botId)) {
            ((RPABotWorkerFindCPLID.TaskHandler)this.botMap.get(botId)).handleFailure(errorMessage, errorDetails);
            this.botMap.remove(botId);
        }

    }

    public boolean handlesJobId(Long jobId) {
        return this.botMap.containsKey(jobId);
    }

    public Collection<Long> getJobIds(long limit) {
        return (Collection)this.botMap.entrySet().stream().map(Map.Entry::getKey).limit(limit).collect(Collectors.toList());
    }

    public String getOldestCreationTime() {
        Iterator<Map.Entry<Long, RPABotWorkerFindCPLID.TaskHandler>> iterator = this.botMap.entrySet().iterator();
        return iterator.hasNext() ? ((RPABotWorkerFindCPLID.TaskHandler)((Map.Entry)iterator.next()).getValue()).creationTime : null;
    }

    protected static class TaskHandler {
        protected ExternalTask task;
        protected ExternalTaskService service;
        protected String creationTime;
        protected MetricsCollector metricsCollector;

        public TaskHandler(ExternalTask task, ExternalTaskService service, String creationTime, MetricsCollector metricsCollector) {
            this.task = task;
            this.service = service;
            this.creationTime = creationTime;
            this.metricsCollector = metricsCollector;
        }

        public void complete(Map<String, Object> variables) {
            variables.values().removeAll(Collections.singleton(null));
            if(variables.isEmpty()){
                RPABotWorkerFindCPLID.LOG.debug("Send External Task with Id {} to agent in the process engine", this.task.getId());
                service.handleBpmnError(task, "bpmn-error", "System was unable to find CPL-ID. Send to agent.");
            } else {
                RPABotWorkerFindCPLID.LOG.debug("Marking External Task with Id {} as completed in the process engine", this.task.getId());
                VariableMap outputVariables = Variables.createVariables();
                for (Map.Entry<String, Object> entry : variables.entrySet()) {
                    outputVariables.put(entry.getKey(), entry.getValue());
                }
                this.service.complete(this.task, outputVariables);
                if (this.metricsCollector != null) {
                    this.metricsCollector.botCompleted();
                }
            }
        }

        public void handleFailure(String errorMessage, String errorDetails) {
            RPABotWorkerFindCPLID.LOG.debug("Marking External Task with Id {} as failed in the process engine", this.task.getId());
            this.service.handleFailure(this.task, errorMessage, errorDetails, 0, 0L);
            if (this.metricsCollector != null) {
                this.metricsCollector.botFailed();
            }

        }
    }
}
