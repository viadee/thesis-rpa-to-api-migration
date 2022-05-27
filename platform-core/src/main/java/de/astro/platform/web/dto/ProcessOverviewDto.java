package de.astro.platform.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ProcessOverviewDto implements Serializable {
    private static final long serialVersionUID = 1596299826472476L;

    @NotBlank
    private String name;

    @NotNull
    private long instances;

    @NotNull
    private long incidents;

    public ProcessOverviewDto(String name, long instances, long incidents) {
        this.name = name;
        this.instances = instances;
        this.incidents = incidents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInstances() {
        return instances;
    }

    public void setInstances(long instances) {
        this.instances = instances;
    }

    public long getIncidents() {
        return incidents;
    }

    public void setIncidents(long incidents) {
        this.incidents = incidents;
    }
}
