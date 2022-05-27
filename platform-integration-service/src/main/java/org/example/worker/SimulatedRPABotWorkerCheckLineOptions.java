package org.example.worker;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@ExternalTaskSubscription("CheckLineOptionsRobot")
public class SimulatedRPABotWorkerCheckLineOptions implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        System.out.println("RPA Request for CheckLineOptionsRobot received.");

        String cplID = externalTask.getVariable("cplID");
        String annexJRueckfall = getRandomBool();
        String adsl16 = getRandomBool();
        String adsl16plus = getRandomBool();
        String vdsl = getRandomBool();
        String sdslB = getRandomBool();
        String sdslBBonding = getRandomBool();
        String ftth = getRandomBool();

        VariableMap variableMap = Variables.createVariables();
        variableMap.put("annexJRueckfall", annexJRueckfall);
        variableMap.put("adsl16", adsl16);
        variableMap.put("adsl16plus", adsl16plus);
        variableMap.put("vdsl", vdsl);
        variableMap.put("sdslB", sdslB);
        variableMap.put("sdslBBonding", sdslBBonding);
        variableMap.put("ftth", ftth);

        if((adsl16=="false" && adsl16plus=="false" && annexJRueckfall=="false") ||
                (adsl16=="true" && adsl16plus=="true" && annexJRueckfall=="true") || cplID == null){
            externalTaskService.handleBpmnError(externalTask, "bpmn-error", "Re-Check DSL Options");
            Logger.getLogger("CheckLineOptionsRobot").log(Level.INFO, "Line Diagnosis for CPL-ID {0} not finished. Send to agent.", cplID);
        } else {
            externalTaskService.complete(externalTask, variableMap);
            Logger.getLogger("CheckLineOptionsRobot").log(Level.INFO, "Line Diagnosis for CPL-ID {0} finished.", cplID);
        }
        // externalTaskService.complete(externalTask);
        //externalTaskService.handleBpmnError(externalTask, "bpmn-error", "test");
        // "bpmn-error" corresponds to the "code" in the bpmn model; "test" is the content of the message variables
    }

    public static String getRandomBool() {
        if(Math.random()<0.5){
            return "true";
        } else {
            return "false";
        }
    }

}
