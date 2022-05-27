package de.astro.platform.web.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CriteriaWeightsDto implements Serializable {
    private static final long serialVersionUID = 918194320117743916L;

    @Id
    private long id;

    @NotNull
    private int frontendStabilityWeight;

    @NotNull
    private int endOfLifeWeight;

    @NotNull
    private int customerWaitingTimeWeight;

    @NotNull
    private int automationRateWeight;

    @NotNull
    private int qualityOfResultsWeight;

    @NotNull
    private int numberOfBotRunnersWeight;

    @NotNull
    private int executionFrequencyWeight;

    @NotNull
    private int numberOfSystemsWeight;

    @NotNull
    private int businessImpactWeight;

    @NotNull
    private int regulatoryComplianceWeight;

    @NotNull
    private int frequencyOfReuseWeight;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFrontendStabilityWeight() {
        return frontendStabilityWeight;
    }

    public void setFrontendStabilityWeight(int frontendStabilityWeight) {
        this.frontendStabilityWeight = frontendStabilityWeight;
    }

    public int getEndOfLifeWeight() {
        return endOfLifeWeight;
    }

    public void setEndOfLifeWeight(int endOfLifeWeight) {
        this.endOfLifeWeight = endOfLifeWeight;
    }

    public int getCustomerWaitingTimeWeight() {
        return customerWaitingTimeWeight;
    }

    public void setCustomerWaitingTimeWeight(int customerWaitingTimeWeight) {
        this.customerWaitingTimeWeight = customerWaitingTimeWeight;
    }

    public int getAutomationRateWeight() {
        return automationRateWeight;
    }

    public void setAutomationRateWeight(int automationRateWeight) {
        this.automationRateWeight = automationRateWeight;
    }

    public int getQualityOfResultsWeight() {
        return qualityOfResultsWeight;
    }

    public void setQualityOfResultsWeight(int qualityOfResultsWeight) {
        this.qualityOfResultsWeight = qualityOfResultsWeight;
    }

    public int getNumberOfBotRunnersWeight() {
        return numberOfBotRunnersWeight;
    }

    public void setNumberOfBotRunnersWeight(int numberOfBotRunnersWeight) {
        this.numberOfBotRunnersWeight = numberOfBotRunnersWeight;
    }

    public int getExecutionFrequencyWeight() {
        return executionFrequencyWeight;
    }

    public void setExecutionFrequencyWeight(int executionFrequencyWeight) {
        this.executionFrequencyWeight = executionFrequencyWeight;
    }

    public int getNumberOfSystemsWeight() {
        return numberOfSystemsWeight;
    }

    public void setNumberOfSystemsWeight(int numberOfSystemsWeight) {
        this.numberOfSystemsWeight = numberOfSystemsWeight;
    }

    public int getBusinessImpactWeight() {
        return businessImpactWeight;
    }

    public void setBusinessImpactWeight(int businessImpactWeight) {
        this.businessImpactWeight = businessImpactWeight;
    }

    public int getRegulatoryComplianceWeight() {
        return regulatoryComplianceWeight;
    }

    public void setRegulatoryComplianceWeight(int regulatoryComplianceWeight) {
        this.regulatoryComplianceWeight = regulatoryComplianceWeight;
    }

    public int getFrequencyOfReuseWeight() {
        return frequencyOfReuseWeight;
    }

    public void setFrequencyOfReuseWeight(int frequencyOfReuseWeight) {
        this.frequencyOfReuseWeight = frequencyOfReuseWeight;
    }
}
