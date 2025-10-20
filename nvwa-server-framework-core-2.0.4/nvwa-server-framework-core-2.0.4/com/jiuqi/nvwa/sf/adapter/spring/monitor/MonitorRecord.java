/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.OperationSpan;
import java.util.List;

public class MonitorRecord {
    private String traceId;
    private String serviceInstance;
    private String service;
    private String traceSegmentId;
    private List<OperationSpan> spans;

    public String getTraceId() {
        return this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getServiceInstance() {
        return this.serviceInstance;
    }

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTraceSegmentId() {
        return this.traceSegmentId;
    }

    public void setTraceSegmentId(String traceSegmentId) {
        this.traceSegmentId = traceSegmentId;
    }

    public List<OperationSpan> getSpans() {
        return this.spans;
    }

    public void setSpans(List<OperationSpan> spans) {
        this.spans = spans;
    }

    public String toString() {
        if (this.spans.isEmpty()) {
            return "MonitorRecord{ }";
        }
        return "MonitorRecord{ id: " + this.traceId + ", name: " + this.spans.get(0).getOperationName() + ", type: " + this.spans.get(0).getSpanLayer() + ", startTime: " + this.spans.get(0).getStartTime() + ", endTime: " + this.spans.get(0).getEndTime() + '}';
    }
}

