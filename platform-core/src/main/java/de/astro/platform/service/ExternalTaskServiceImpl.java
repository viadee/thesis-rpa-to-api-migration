package de.astro.platform.service;

import de.astro.platform.data.model.RPABot;
import de.astro.platform.web.dto.ExternalTaskDto;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExternalTaskServiceImpl implements ExternalTaskService{

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RPABotService rpaBotService;

    @Override
    public List<ExternalTaskDto> getExternTasks() throws ParserConfigurationException, IOException, SAXException {
        List<ExternalTaskDto> externalTaskDtos = new ArrayList<>();
        // get all existing process keys
        List<String> definitionKeys = getProcessDefinitionKeys();
        // walk through list, query xml for each
        for(int i = 0; i<definitionKeys.size(); i++){
            String key = definitionKeys.get(i);
            String definitionId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult().getId();
            BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(definitionId);
            Map<String, Map.Entry<String, String>> xmlRPAElements = findXmlElement(bpmnModelInstance, "bpmn:serviceTask");
            for(Map.Entry<String, Map.Entry<String, String>> s: xmlRPAElements.entrySet()){
                String actIdOfFollowingUserTask = findUserTaskFollowingRPATask(bpmnModelInstance, s.getKey());
                RPABot rpaBot = rpaBotService.findRobotByTopic(s.getValue().getKey());
                ExternalTaskDto externalTaskDto = new ExternalTaskDto(definitionId, s.getValue().getKey(), key, s.getValue().getValue(), s.getKey(), actIdOfFollowingUserTask, historyService.createHistoricActivityInstanceQuery().activityId(s.getKey()).finished().count(), historyService.createHistoricActivityInstanceQuery().activityId(actIdOfFollowingUserTask).finished().count(),
                        rpaBot.getFrontendStability(), rpaBot.getEndOfLife(), rpaBot.getQualityOfResults(), rpaBot.getNumberOfBotRunners(), rpaBot.getBusinessImpact(), rpaBot.getNumberOfSystems(), rpaBot.getRegulatoryCompliance(),
                        getAvgDurationTime(definitionId, s.getKey()) ,getAutoRate(s.getKey(), actIdOfFollowingUserTask), getExeFrequency(definitionId, s.getKey()), 0);
                externalTaskDtos.add(externalTaskDto);
            }
        }
        return externalTaskDtos;
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

    // helper function to retrieve value of xml tags
    private Map<String, Map.Entry<String, String>> findXmlElement(BpmnModelInstance bpmnModelInstance, String elementTag) throws ParserConfigurationException, IOException, SAXException {
        Map<String, Map.Entry<String, String>> xmlElements = new HashMap<String, Map.Entry<String, String>>();
        String xml = Bpmn.convertToString(bpmnModelInstance);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        NodeList elements = document.getElementsByTagName(elementTag);
        for(int i=0; i<elements.getLength(); i++){
            String e;
            try {
                e = elements.item(i).getAttributes().getNamedItem("camunda:topic").getNodeValue();
                String ee = elements.item(i).getAttributes().getNamedItem("camunda:modelerTemplate").getNodeValue();
                if(!ee.equals("com.camunda.example.RPATask")){
                    continue;
                }
            } catch (Exception err){
                e = "";
            }
            if(e != ""){
                String eleName;
                try {
                    eleName = elements.item(i).getAttributes().getNamedItem("name").getNodeValue();
                } catch (Exception err){
                    eleName = "";
                }
                java.util.Map.Entry<String,String> topicActivtiyPair = new java.util.AbstractMap.SimpleEntry<>(e, eleName);
                xmlElements.put(elements.item(i).getAttributes().getNamedItem("id").getNodeValue(),topicActivtiyPair);
            }
        }
        return xmlElements;
    }

    // helper function to get average duration time of task
    private String getAvgDurationTime(String procDefinitionId, String rpaActId){
        double durationInMillis = 0;
        List<HistoricActivityInstance> historicActivityInstances =
                historyService.createHistoricActivityInstanceQuery().processDefinitionId(procDefinitionId).
                        activityId(rpaActId).finished().list();
        int listSize = historicActivityInstances.size();
        for(int i=0; i<listSize; i++){
            HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
            durationInMillis = durationInMillis+historicActivityInstance.getDurationInMillis();
        }
        if(listSize==0){
            return "No completed cases yet.";
        } else {
            return "" + durationInMillis/listSize;
        }
    }

    // helper function to retrieve user task that follows the RPA Task
    private String findUserTaskFollowingRPATask(BpmnModelInstance bpmnModelInstance, String rpaActivityID) throws ParserConfigurationException, IOException, SAXException {
        String actIdUsrTask = "";
        String xml = Bpmn.convertToString(bpmnModelInstance);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        NodeList elements = document.getElementsByTagName("bpmn:boundaryEvent");
        for(int i=0; i<elements.getLength(); i++){
            String e;
            try {
                e = elements.item(i).getAttributes().getNamedItem("attachedToRef").getNodeValue();
            } catch (Exception err){
                e = "";
            }
            if(e != ""){
                if(e.equals(rpaActivityID)){
                    String idOfErrEvent = elements.item(i).getAttributes().getNamedItem("id").getNodeValue();
                    NodeList sequenceFlows = document.getElementsByTagName("bpmn:sequenceFlow");
                    for(int ii=0; ii<sequenceFlows.getLength(); ii++){
                        String sourceRefOfErrEvent = sequenceFlows.item(ii).getAttributes().getNamedItem("sourceRef").getNodeValue();
                        if(sourceRefOfErrEvent.equals(idOfErrEvent)){
                            actIdUsrTask = sequenceFlows.item(ii).getAttributes().getNamedItem("targetRef").getNodeValue();
                            break;
                        }
                    }
                }
            }
        }
        return actIdUsrTask;
    }

    // helper function to get automation rate
    private String getAutoRate(String rpaActId, String usrActId){
        double totalCasesAtRpaActivity = (double) historyService.createHistoricActivityInstanceQuery().activityId(rpaActId).finished().count();
        double casesManuallyHandled = (double) historyService.createHistoricActivityInstanceQuery().activityId(usrActId).finished().count();
        if(totalCasesAtRpaActivity==0){
            return "No cases yet.";
        } else {
            double res = 1 - (casesManuallyHandled / totalCasesAtRpaActivity);
            return "" + res;
        }
    }

    // helper function to get yesterday's unfinished cases
    private long getExeFrequency(String procDefinitionId, String rpaActId){
        long casesUnfinished = 0;
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyyMMdd");
        List<HistoricActivityInstance> historicActivityInstances =
        historyService.createHistoricActivityInstanceQuery().
                activityId(rpaActId).unfinished().list();
        for(int i=0; i<historicActivityInstances.size(); i++){
            HistoricActivityInstance historicActivityInstance = historicActivityInstances.get(i);
            //Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
            Date today = new Date(System.currentTimeMillis());
            Date instanceDate = historicActivityInstance.getStartTime();
            if(!dt1.format(today).equals(dt1.format(instanceDate))){
                casesUnfinished = casesUnfinished + 1;
            }
        }
        return casesUnfinished;
    }
}
