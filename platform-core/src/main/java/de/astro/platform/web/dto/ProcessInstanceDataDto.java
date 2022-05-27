package de.astro.platform.web.dto;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ProcessInstanceDataDto {
    private static final long serialVersionUID = 1596299826472476L;

    @NotBlank
    private String instanceId;

    @NotNull
    private boolean state;

    @FutureOrPresent
    private Date startTime;

    @NotBlank
    private String businessKey;

    public ProcessInstanceDataDto(String instanceId, boolean state, Date startTime, String businessKey) {
        this.instanceId = instanceId;
        this.state = state;
        this.startTime = startTime;
        this.businessKey = businessKey;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
