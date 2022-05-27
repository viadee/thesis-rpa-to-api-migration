package org.example.bridge.rpa.uipath;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class ReleaseResponse {
    Release[] value;

    public ReleaseResponse() {
    }

    public Release[] getValue() {
        return this.value;
    }

    public void setValue(Release[] value) {
        this.value = value;
    }
}

