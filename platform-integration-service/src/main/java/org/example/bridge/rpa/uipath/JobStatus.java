package org.example.bridge.rpa.uipath;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Map;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class JobStatus {
    String key;
    String state;
    long id;
    String info;
    Map<String, Object> outputArguments;

    @JsonCreator
    public JobStatus() {
    }

    public String getKey() {
        return this.key;
    }

    @JsonSetter("Key")
    public void setKey(String key) {
        this.key = key;
    }

    public String getState() {
        return this.state;
    }

    @JsonSetter("State")
    public void setState(String state) {
        this.state = state;
    }

    public long getId() {
        return this.id;
    }

    @JsonSetter("Id")
    public void setId(long id) {
        this.id = id;
    }

    public String getInfo() {
        return this.info;
    }

    @JsonSetter("Info")
    public void setInfo(String info) {
        this.info = info;
    }

    public Map<String, Object> getOutputArguments() {
        return this.outputArguments;
    }

    @JsonSetter("OutputArguments")
    public void setOutputArguments(Map<String, Object> outputArguments) {
        this.outputArguments = outputArguments;
    }
}

