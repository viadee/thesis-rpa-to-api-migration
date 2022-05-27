package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class Job {
    String key;
    long id;
    String state;
    String creationTime;

    @JsonCreator
    public Job() {
    }

    public String getKey() {
        return this.key;
    }

    @JsonSetter("Key")
    public void setKey(String key) {
        this.key = key;
    }

    public long getId() {
        return this.id;
    }

    @JsonSetter("Id")
    public void setId(long id) {
        this.id = id;
    }

    public String getState() {
        return this.state;
    }

    @JsonSetter("State")
    public void setState(String state) {
        this.state = state;
    }

    public String getCreationTime() {
        return this.creationTime;
    }

    @JsonSetter("CreationTime")
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}

