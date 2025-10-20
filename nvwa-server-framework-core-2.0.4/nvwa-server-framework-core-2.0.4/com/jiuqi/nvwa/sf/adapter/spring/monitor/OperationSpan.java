/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.RecordTag;
import java.util.List;

public class OperationSpan {
    private String operationName;
    private long startTime;
    private long endTime;
    private String spanType;
    private int spanId;
    private boolean isError;
    private int parentSpanId;
    private int componentId;
    private String peer;
    private String spanLayer;
    private List<RecordTag> tags;
    private boolean skipAnalysis;

    public String getOperationName() {
        return this.operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getSpanType() {
        return this.spanType;
    }

    public void setSpanType(String spanType) {
        this.spanType = spanType;
    }

    public int getSpanId() {
        return this.spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public boolean getIsError() {
        return this.isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public int getParentSpanId() {
        return this.parentSpanId;
    }

    public void setParentSpanId(int parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public int getComponentId() {
        return this.componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public String getPeer() {
        return this.peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    public String getSpanLayer() {
        return this.spanLayer;
    }

    public void setSpanLayer(String spanLayer) {
        this.spanLayer = spanLayer;
    }

    public List<RecordTag> getTags() {
        return this.tags;
    }

    public void setTags(List<RecordTag> tags) {
        this.tags = tags;
    }

    public boolean isSkipAnalysis() {
        return this.skipAnalysis;
    }

    public void setSkipAnalysis(boolean skipAnalysis) {
        this.skipAnalysis = skipAnalysis;
    }
}

