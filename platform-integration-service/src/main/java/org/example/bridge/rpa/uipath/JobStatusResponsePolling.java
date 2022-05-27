package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class JobStatusResponsePolling {
    JobStatusPolling[] value;

    public JobStatusResponsePolling() {
    }

    public JobStatusPolling[] getValue() {
        return this.value;
    }

    public void setValue(JobStatusPolling[] value) {
        this.value = value;
    }
}
