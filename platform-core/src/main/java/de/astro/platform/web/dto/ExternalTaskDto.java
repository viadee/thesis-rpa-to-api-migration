package de.astro.platform.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ExternalTaskDto {
    private static final long serialVersionUID = 15962928932189L;

    @NotBlank
    private String id;

    @NotBlank
    private String definitionId;

    @NotBlank
    private String topic;

    @NotBlank
    private String processKey;

    @NotBlank
    private String activityName;

    @NotBlank
    private String activityId;

    @NotBlank
    private String activityIdUserTask;

    @NotNull
    private long totalCasesHandled;

    @NotNull
    private long casesHandledManually;

    @NotNull
    private int frontendStability;

    @NotNull
    private int endOfLife;

    @NotNull
    private int qualityOfResults;

    @NotNull
    private int numberOfBotRunners;

    @NotNull
    private int businessImpact;

    @NotNull
    private int numberOfSystems;

    @NotNull
    private int regulatoryCompliance;

    @NotBlank
    private String customerWaitingTime;

    @NotBlank
    private String automationRate;

    @NotNull
    private long executionFrequency;

    @NotNull
    private long frequencyOfReuse;

    public ExternalTaskDto(String definitionId, String topic, String processKey, String activityName, String activityId, String activityIdUserTask, long totalCasesHandled, long casesHandledManually,
                           int frontendStability, int endOfLife, int qualityOfResults, int numberOfBotRunners, int businessImpact, int numberOfSystems, int regulatoryCompliance,
                           String customerWaitingTime ,String automationRate, long executionFrequency, long frequencyOfReuse) {
        this.id = java.util.UUID.randomUUID().toString();
        this.definitionId = definitionId;
        this.topic = topic;
        this.processKey = processKey;
        this.activityName = activityName;
        this.activityId = activityId;
        this.activityIdUserTask = activityIdUserTask;
        this.totalCasesHandled = totalCasesHandled;
        this.casesHandledManually = casesHandledManually;

        this.frontendStability = frontendStability;
        this.endOfLife = endOfLife;
        this.qualityOfResults = qualityOfResults;
        this.numberOfBotRunners = numberOfBotRunners;
        this.businessImpact = businessImpact;
        this.numberOfSystems = numberOfSystems;
        this.regulatoryCompliance = regulatoryCompliance;

        this.customerWaitingTime = customerWaitingTime;
        this.automationRate = automationRate;
        this.executionFrequency = executionFrequency;
        this.frequencyOfReuse = frequencyOfReuse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinitionId() {
        return definitionId;
    }

    public void setDefinitionId(String definitionId) {
        this.definitionId = definitionId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityIdUserTask() {
        return activityIdUserTask;
    }

    public void setActivityIdUserTask(String activityIdUserTask) {
        this.activityIdUserTask = activityIdUserTask;
    }

    public long getTotalCasesHandled() {
        return totalCasesHandled;
    }

    public void setTotalCasesHandled(long totalCasesHandled) {
        this.totalCasesHandled = totalCasesHandled;
    }

    public long getCasesHandledManually() {
        return casesHandledManually;
    }

    public void setCasesHandledManually(long casesHandledManually) {
        this.casesHandledManually = casesHandledManually;
    }

    public int getFrontendStability() {
        return frontendStability;
    }

    public void setFrontendStability(int frontendStability) {
        this.frontendStability = frontendStability;
    }

    public int getEndOfLife() {
        return endOfLife;
    }

    public void setEndOfLife(int endOfLife) {
        this.endOfLife = endOfLife;
    }

    public int getQualityOfResults() {
        return qualityOfResults;
    }

    public void setQualityOfResults(int qualityOfResults) {
        this.qualityOfResults = qualityOfResults;
    }

    public int getNumberOfBotRunners() {
        return numberOfBotRunners;
    }

    public void setNumberOfBotRunners(int numberOfBotRunners) {
        this.numberOfBotRunners = numberOfBotRunners;
    }

    public int getBusinessImpact() {
        return businessImpact;
    }

    public void setBusinessImpact(int businessImpact) {
        this.businessImpact = businessImpact;
    }

    public int getNumberOfSystems() {
        return numberOfSystems;
    }

    public void setNumberOfSystems(int numberOfSystems) {
        this.numberOfSystems = numberOfSystems;
    }

    public int getRegulatoryCompliance() {
        return regulatoryCompliance;
    }

    public void setRegulatoryCompliance(int regulatoryCompliance) {
        this.regulatoryCompliance = regulatoryCompliance;
    }

    public String getCustomerWaitingTime() {
        return customerWaitingTime;
    }

    public void setCustomerWaitingTime(String customerWaitingTime) {
        this.customerWaitingTime = customerWaitingTime;
    }

    public String getAutomationRate() {
        return automationRate;
    }

    public void setAutomationRate(String automationRate) {
        this.automationRate = automationRate;
    }

    public long getExecutionFrequency() {
        return executionFrequency;
    }

    public void setExecutionFrequency(long executionFrequency) {
        this.executionFrequency = executionFrequency;
    }

    public long getFrequencyOfReuse() {
        return frequencyOfReuse;
    }

    public void setFrequencyOfReuse(long frequencyOfReuse) {
        this.frequencyOfReuse = frequencyOfReuse;
    }
}
