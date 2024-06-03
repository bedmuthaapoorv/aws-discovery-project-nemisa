package com.nimesa.awsDiscovery.services.Jobservices;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class ThreadInfo {
    private final List<String> result;
    private final long threadId;

    public ThreadInfo(List<String> list, long threadId) {
        this.result = list;
        this.threadId = threadId;
    }

    public List<String> getResult() {
        return result;
    }

    public long getThreadId() {
        return threadId;
    }
}