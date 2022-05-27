package org.example.bridge.rpa.variables;

import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.DateValue;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.spin.Spin;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class VariableConverter {
    public VariableConverter() {
    }

    public static String toJsonString(VariableMap variableMap) {
        Map<String, Object> variablesSafe = new HashMap();

        String variableKey;
        Object value;
        for(Iterator var2 = variableMap.keySet().iterator(); var2.hasNext(); variablesSafe.put(variableKey, value)) {
            variableKey = (String)var2.next();
            TypedValue valueTyped = variableMap.getValueTyped(variableKey);
            value = null;
            if (valueTyped instanceof DateValue) {
                value = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.ofInstant(((Date)((DateValue)valueTyped).getValue()).toInstant(), ZoneId.systemDefault()));
            } else if (valueTyped instanceof ObjectValue) {
                value = ((ObjectValue)valueTyped).getValueSerialized();
            } else {
                value = valueTyped.getValue();
            }
        }

        return Spin.JSON(variablesSafe).toString();
    }

    public static Map<String, Object> toEngineValues(Map<String, Object> properties) {
        if (properties != null && !properties.isEmpty()) {
            Map<String, Object> variables = new HashMap();

            Map.Entry property;
            Object value;
            for(Iterator var2 = properties.entrySet().iterator(); var2.hasNext(); variables.put(property.getKey().toString(), value)) {
                property = (Map.Entry)var2.next();
                value = property.getValue();
                if (value instanceof String) {
                    try {
                        value = Date.from(ZonedDateTime.parse((String)property.getValue(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).toInstant());
                    } catch (DateTimeParseException var6) {
                    }
                }
            }

            return variables;
        } else {
            return Collections.emptyMap();
        }
    }
}
