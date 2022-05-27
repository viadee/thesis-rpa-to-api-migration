package org.example.bridge.rpa.uipath;

import org.example.bridge.rpa.variables.VariableConverter;
import org.example.worker.RPABotWorkerFindCPLID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JobUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JobUtil.class);
    protected static final String SUCCESS_STATE = "Successful";
    protected static final Set<String> ERROR_STATES = (Set) Stream.of("Stopped", "Faulted", "Suspended").collect(Collectors.toSet());
    protected static final Set<String> STATES;

    private JobUtil() {
    }

    protected static void handleJobStatusResponse(JobStatus jobStatus, RPABotWorkerFindCPLID taskAdapter) {
        long jobId = jobStatus.getId();
        if (taskAdapter.handlesJobId(jobId)) {
            String state = jobStatus.getState();

            try {
                LOG.debug("Received UiPath job {} with state: {}", jobId, state);
                if ("Successful".equals(state)) {
                    taskAdapter.botCompleted(jobId, VariableConverter.toEngineValues(jobStatus.getOutputArguments())); // check the complete
                } else if (ERROR_STATES.contains(state)) {
                    taskAdapter.botFailed(jobId, "Error in UiPath job", jobStatus.getInfo());
                }
            } catch (Exception var6) {
                LOG.warn(String.format("Exception while reporting result of RPA bot %s back to the Camunda engine", jobId), var6);
            }
        }
    }

    static {
        STATES = (Set)Stream.concat(ERROR_STATES.stream(), Stream.of("Successful")).collect(Collectors.toSet());
    }
}
