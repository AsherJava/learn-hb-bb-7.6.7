/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class NrEventSubscrEntityImpl {
    private String id;
    private BigDecimal rev;
    private String eventType;
    private String eventName;
    private String executionId;
    private String procInstId;
    private String activityId;
    private String configuration;
    private Timestamp created;
    private String procDefId;
    private String tenantId;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return this.rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getActivityId() {
        return this.activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public Timestamp getCreated() {
        return this.created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String toString() {
        return "NrEventSubscrEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", eventType='" + this.eventType + '\'' + ", eventName='" + this.eventName + '\'' + ", executionId='" + this.executionId + '\'' + ", procInstId='" + this.procInstId + '\'' + ", activityId='" + this.activityId + '\'' + ", configuration='" + this.configuration + '\'' + ", created=" + this.created + ", procDefId='" + this.procDefId + '\'' + ", tenantId='" + this.tenantId + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrEventSubscrEntityImpl that = (NrEventSubscrEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

