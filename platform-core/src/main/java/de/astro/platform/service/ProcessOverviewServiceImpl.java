package de.astro.platform.service;

import de.astro.platform.web.dto.ProcessOverviewDto;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.IncidentRestService;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDto;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.spring.boot.starter.rest.CamundaJerseyResourceConfig;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessOverviewServiceImpl implements ProcessOverviewService{

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Override
    public List<ProcessOverviewDto> getOverviewOfProcesses(){
        List<String> definitionKeys = getProcessDefinitionKeys();
        List<ProcessOverviewDto> processOverviewDtos = new ArrayList<>();
        for(int i = 0; i<definitionKeys.size(); i++){
            String key = definitionKeys.get(i);
            long numOfIncidents = getNumberOfIncidents(key);
            long numOfInstances = getNumberOfInstances(key);
            ProcessOverviewDto processOverviewDto = new ProcessOverviewDto(key, numOfInstances, numOfIncidents);
            processOverviewDtos.add(processOverviewDto);
        }
        return processOverviewDtos;
    }

    // helper function to get definition keys of all processes
    public List<String> getProcessDefinitionKeys(){
        List<String> keys = new ArrayList<>();
        List<ProcessDefinition> definitionDtos = repositoryService.createProcessDefinitionQuery().latestVersion().list();
        for(int i = 0; i<definitionDtos.size(); i++){
            keys.add(definitionDtos.get(i).getKey());
        }
        return keys;
    }

    // helper function to get number of instances of a single process
    public long getNumberOfInstances(String key){
        return runtimeService.createProcessInstanceQuery().processDefinitionKey(key).count();
    }

    // helper function to get number of incidents of a single process across all its definitions
    public long getNumberOfIncidents(String key){
        return runtimeService.createIncidentQuery().processDefinitionKeyIn(key).count();
    }

    @Override
    public List<String> deleteProcessDefinitionByKey(String processKey){
        List<String> processDefinitionsIdsList = new ArrayList<>();
        List<ProcessDefinition> processDefinitions;
        processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).list();
        for(int i=0; i<processDefinitions.size(); i++){
            String procDefIdToBeDeleted = processDefinitions.get(i).getId();
            repositoryService.deleteProcessDefinition(procDefIdToBeDeleted, true);
            processDefinitionsIdsList.add(procDefIdToBeDeleted);
        }
        return processDefinitionsIdsList;
    }

}
