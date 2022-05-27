package org.example.bridge.rpa;

import org.example.bridge.rpa.uipath.Job;
import org.example.bridge.rpa.uipath.JobStatus;
import org.example.bridge.rpa.uipath.auth.AuthToken;

import java.util.Collection;

public interface RPARestService {
    Job startRpaJob(String var1, String var2);
    JobStatus[] getRpaJobStatus(Collection<Long> var1);
    JobStatus[] getRpaJobStatus(String var1);
}
