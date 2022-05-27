package org.example.bridge.rpa.telemetry;

public class MetricsCollector {
    int botsStarted;
    int botsSuccessful;
    int botsFailed;

    public MetricsCollector() {
    }

    public void botStarted() {
        ++this.botsStarted;
    }

    public void botCompleted() {
        ++this.botsSuccessful;
    }

    public void botFailed() {
        ++this.botsFailed;
    }

    public Metrics getCurrentMetrics() {
        return new Metrics(this.botsStarted, this.botsFailed, this.botsSuccessful);
    }
}