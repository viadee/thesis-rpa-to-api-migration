package org.example.bridge.rpa.telemetry;

public class Metrics {
    protected long botsStarted;
    protected long botsFailed;
    protected long botsSuccessful;

    public Metrics(int botsStarted, int botsFailed, int botsSuccessfull) {
        this.botsStarted = (long)botsStarted;
        this.botsFailed = (long)botsFailed;
        this.botsSuccessful = (long)botsSuccessfull;
    }

    public long getBotsStarted() {
        return this.botsStarted;
    }

    public void setBotsStarted(long botsStarted) {
        this.botsStarted = botsStarted;
    }

    public long getBotsFailed() {
        return this.botsFailed;
    }

    public void setBotsFailed(long botsFailed) {
        this.botsFailed = botsFailed;
    }

    public long getBotsSuccessfull() {
        return this.botsSuccessful;
    }

    public void setBotsSuccessfull(long botsSuccessfull) {
        this.botsSuccessful = botsSuccessfull;
    }
}
