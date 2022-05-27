package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Release {
    String key;
    String processKey;
    String environmentId;
    String environmentName;
    long id;

    @JsonCreator
    public Release() {
    }

    public String getKey() {
        return this.key;
    }

    @JsonSetter("Key")
    public void setKey(String key) {
        this.key = key;
    }

    public String getProcessKey() {
        return this.processKey;
    }

    @JsonSetter("ProcessKey")
    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getEnvironmentId() {
        return this.environmentId;
    }

    @JsonSetter("EnvironmentId")
    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public String getEnvironmentName() {
        return this.environmentName;
    }

    @JsonSetter("EnvironmentName")
    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public long getId() {
        return this.id;
    }

    @JsonSetter("Id")
    public void setId(long id) {
        this.id = id;
    }
}
