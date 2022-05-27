package org.example.data.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
public class ApiTopic implements Serializable {
    private static final long serialVersionUID = -2102616133492412128L;

    @Id
    private String id;

    @NotNull
    @NotBlank
    private String restEndpoint;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "apiTopic" , cascade = CascadeType.ALL)
    private Set<VarMapping> varMappingSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestEndpoint() {
        return restEndpoint;
    }

    public void setRestEndpoint(String restEndpoint) {
        this.restEndpoint = restEndpoint;
    }
}
