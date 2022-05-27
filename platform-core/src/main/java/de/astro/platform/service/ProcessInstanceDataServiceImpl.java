package de.astro.platform.service;

import de.astro.platform.web.dto.ProcessInstanceDataDto;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcessInstanceDataServiceImpl implements ProcessInstanceDataService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Override
    public List<ProcessInstanceDataDto> getInstanceData(String processDefinitionId){
        List<ProcessInstanceDataDto> processInstanceDataDtos = new ArrayList<>();
        List<ProcessInstance> processInstances = getInstanceIDsAndKeys(processDefinitionId);
        for(int i=0; i<processInstances.size(); i++){
            ProcessInstance prcInst = processInstances.get(i);
            String processInstanceId = prcInst.getId();
            String businessKey = prcInst.getBusinessKey();
            boolean state = getInstanceCount(processInstanceId);
            Date startTime = getStartTime(processInstanceId);
            ProcessInstanceDataDto processInstanceDataDto = new ProcessInstanceDataDto(processInstanceId, state, startTime, businessKey);
            processInstanceDataDtos.add(processInstanceDataDto);
        }
        return processInstanceDataDtos;
    }

    public List<ProcessInstance> getInstanceIDsAndKeys(String processDefinitionId){
        return runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).list();
    }

    public boolean getInstanceCount(String processInstanceId){
        long count = runtimeService.createIncidentQuery().processInstanceId(processInstanceId).count();
        boolean isIncident = false;
        if(count > 0){
            isIncident = true;
        }
        return isIncident;
    }

    public Date getStartTime(String processInstanceID){
        return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceID).singleResult().getStartTime();
    }

}
