package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonTypeInfo(
        include = As.WRAPPER_OBJECT,
        use = Id.NAME
)
@JsonTypeName("startInfo")
@JsonNaming(UpperCamelCaseStrategy.class)
public class StartInfo {
    String releaseKey;
    String strategy = "JobsCount";
    @JsonInclude(Include.NON_EMPTY)
    int[] robotIds;
    int jobsCount = 1;
    String source = "Manual";
    @JsonInclude(Include.NON_EMPTY)
    String inputArguments;

    public StartInfo() {
    }

    public String getReleaseKey() {
        return this.releaseKey;
    }

    public void setReleaseKey(String releaseKey) {
        this.releaseKey = releaseKey;
    }

    public String getStrategy() {
        return this.strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int[] getRobotIds() {
        return this.robotIds;
    }

    public void setRobotIds(int[] robotIds) {
        this.robotIds = robotIds;
    }

    public int getJobsCount() {
        return this.jobsCount;
    }

    public void setJobsCount(int jobsCount) {
        this.jobsCount = jobsCount;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getInputArguments() {
        return this.inputArguments;
    }

    public void setInputArguments(String inputArguments) {
        this.inputArguments = inputArguments;
    }
}
