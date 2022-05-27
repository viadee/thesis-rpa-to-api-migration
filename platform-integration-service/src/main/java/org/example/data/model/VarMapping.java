package org.example.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"API_TOPIC_ID", "botVar", "apiVar"})
})
public class VarMapping implements Serializable {
    private static final long serialVersionUID = -2002616133498418192L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NotNull
    private ApiTopic apiTopic;

    @NotBlank
    private String botVar;

    @NotBlank
    private String apiVar;

    @Enumerated(EnumType.STRING)
    private VarType varType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApiTopic getApiTopic() {
        return apiTopic;
    }

    public void setApiTopic(ApiTopic apiTopic) {
        this.apiTopic = apiTopic;
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
}
