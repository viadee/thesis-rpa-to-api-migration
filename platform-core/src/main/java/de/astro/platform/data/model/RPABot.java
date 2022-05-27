package de.astro.platform.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class RPABot implements Serializable {
    private static final long serialVersionUID = 7818194320117743916L;

    @Id
    @NotBlank
    private String topic;

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

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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
}
