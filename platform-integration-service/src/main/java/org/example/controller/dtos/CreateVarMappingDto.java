package org.example.controller.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.data.model.VarType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateVarMappingDto implements Serializable {
    private static final long serialVersionUID = 15813298226472476L;

    @NotBlank
    private String apiTopicId;

    @NotBlank
    private String botVar;

    @NotBlank
    private String apiVar;

    @Enumerated(EnumType.STRING)
    private VarType varType;

    @NotNull
    @NotBlank
    private String restEndpoint;

    public String getApiTopicId() {
        return apiTopicId;
    }

    public void setApiTopicId(String apiTopicId) {
        this.apiTopicId = apiTopicId;
    }

    public String getBotVar() {
        return botVar;
    }

    public void setBotVar(String botVar) {
        this.botVar = botVar;
    }

    public String getApiVar() {
        return apiVar;
    }

    public void setApiVar(String apiVar) {
        this.apiVar = apiVar;
    }

    public VarType getVarType() {
        return varType;
    }

    public void setVarType(VarType varType) {
        this.varType = varType;
    }

    public String getRestEndpoint() {
        return restEndpoint;
    }

    public void setRestEndpoint(String restEndpoint) {
        this.restEndpoint = restEndpoint;
    }
}
