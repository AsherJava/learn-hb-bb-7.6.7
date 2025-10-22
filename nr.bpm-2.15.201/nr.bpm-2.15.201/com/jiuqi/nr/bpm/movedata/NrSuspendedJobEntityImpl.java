/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class NrSuspendedJobEntityImpl {
    private String id;
    private BigDecimal rev;
    private String type;
    private BigDecimal exclusive;
    private String executionId;
    private String processInstanceId;
    private String procDefId;
    private BigDecimal retries;
    private String exceptionStackId;
    private String exceptionMsg;
    private Timestamp dueDate;
    private String repeat;
    private String handlerType;
    private String handlerCfg;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getExclusive() {
        return this.exclusive;
    }

    public void setExclusive(BigDecimal exclusive) {
        this.exclusive = exclusive;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public BigDecimal getRetries() {
        return this.retries;
    }

    public void setRetries(BigDecimal retries) {
        this.retries = retries;
    }

    public String getExceptionStackId() {
        return this.exceptionStackId;
    }

    public void setExceptionStackId(String exceptionStackId) {
        this.exceptionStackId = exceptionStackId;
    }

    public String getExceptionMsg() {
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public Timestamp getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public String getRepeat() {
        return this.repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getHandlerType() {
        return this.handlerType;
    }

    public void setHandlerType(String handlerType) {
        this.handlerType = handlerType;
    }

    public String getHandlerCfg() {
        return this.handlerCfg;
    }

    public void setHandlerCfg(String handlerCfg) {
        this.handlerCfg = handlerCfg;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String toString() {
        return "NrSuspendedJobEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", type='" + this.type + '\'' + ", exclusive=" + this.exclusive + ", excutionId='" + this.executionId + '\'' + ", processInstanceid='" + this.processInstanceId + '\'' + ", procDefId='" + this.procDefId + '\'' + ", retries=" + this.retries + ", exceptionStackId='" + this.exceptionStackId + '\'' + ", exceptionMsg='" + this.exceptionMsg + '\'' + ", dueDate=" + this.dueDate + ", repeat='" + this.repeat + '\'' + ", handlerType='" + this.handlerType + '\'' + ", handlerCfg='" + this.handlerCfg + '\'' + ", tenantId='" + this.tenantId + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrSuspendedJobEntityImpl that = (NrSuspendedJobEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

