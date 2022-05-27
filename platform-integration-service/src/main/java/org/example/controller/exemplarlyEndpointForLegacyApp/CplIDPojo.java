package org.example.controller.exemplarlyEndpointForLegacyApp;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CplIDPojo implements Serializable {
    private static final long serialVersionUID = 23739123226476L;

    @NotBlank
    private String cplID;

    public String getCplID() {
        return cplID;
    }

    public void setCplID(String cplID) {
        this.cplID = cplID;
    }
}
