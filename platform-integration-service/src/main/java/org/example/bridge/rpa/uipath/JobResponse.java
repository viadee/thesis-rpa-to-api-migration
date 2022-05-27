package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class JobResponse {
    Job[] value;

    public JobResponse() {
    }

    public Job[] getValue() {
        return this.value;
    }

    public void setValue(Job[] value) {
        this.value = value;
    }
}
