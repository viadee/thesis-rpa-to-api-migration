package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.example.bridge.rpa.variables.StringToMapConverter;

import java.util.Map;

public class JobStatusPolling extends JobStatus {
    public JobStatusPolling() {
    }

    @JsonDeserialize(
            converter = StringToMapConverter.class
    )
    public void setOutputArguments(Map<String, Object> outputArguments) {
        this.outputArguments = outputArguments;
    }
}
