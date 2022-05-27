package org.example.bridge.rpa.variables;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.camunda.spin.Spin;

import java.util.Map;

public class StringToMapConverter extends StdConverter<String, Map<String, Object>> {
    public StringToMapConverter() {
    }

    public Map<String, Object> convert(String value) {
        return (Map) Spin.JSON(value).mapTo(Map.class);
    }
}

