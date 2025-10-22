/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class NrExecutionEntityImpl {
    private String id;
    private BigDecimal rev;
    private String procInstId;
    private String businessKey;
    private String parentId;
    private String procDefId;
    private String superExec;
    private String rootProcInstId;
    private String actId;
    private BigDecimal isActive;
    private BigDecimal isConcurrent;
    private BigDecimal isScope;
    private BigDecimal isEventScope;
    private BigDecimal isMiRoot;
    private BigDecimal suspensionState;
    private BigDecimal cachedEntState;
    private String tenantId;
    private String name;
    private Timestamp startTime;
    private String startUserId;
    private Timestamp lockTime;
    private BigDecimal isCountEnabled;
    private BigDecimal evtSubscrCount;
    private BigDecimal taskCount;
    private BigDecimal jobCount;
    private BigDecimal timerJobCount;
    private BigDecimal suspJobCount;
    private BigDecimal deadletterJobCount;
    private BigDecimal varCount;
    private BigDecimal idLinkCount;

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

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getSuperExec() {
        return this.superExec;
    }

    public void setSuperExec(String superExec) {
        this.superExec = superExec;
    }

    public String getRootProcInstId() {
        return this.rootProcInstId;
    }

    public void setRootProcInstId(String rootProcInstId) {
        this.rootProcInstId = rootProcInstId;
    }

    public String getActId() {
        return this.actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public BigDecimal getActive() {
        return this.isActive;
    }

    public void setActive(BigDecimal active) {
        this.isActive = active;
    }

    public BigDecimal getConcurrent() {
        return this.isConcurrent;
    }

    public void setConcurrent(BigDecimal concurrent) {
        this.isConcurrent = concurrent;
    }

    public BigDecimal getScope() {
        return this.isScope;
    }

    public void setScope(BigDecimal scope) {
        this.isScope = scope;
    }

    public BigDecimal getEventScope() {
        return this.isEventScope;
    }

    public void setEventScope(BigDecimal eventScope) {
        this.isEventScope = eventScope;
    }

    public BigDecimal getMiRoot() {
        return this.isMiRoot;
    }

    public void setMiRoot(BigDecimal miRoot) {
        this.isMiRoot = miRoot;
    }

    public BigDecimal getSuspensionState() {
        return this.suspensionState;
    }

    public void setSuspensionState(BigDecimal suspensionState) {
        this.suspensionState = suspensionState;
    }

    public BigDecimal getCachedEntState() {
        return this.cachedEntState;
    }

    public void setCachedEntState(BigDecimal cachedEntState) {
        this.cachedEntState = cachedEntState;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public String getStartUserId() {
        return this.startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public Timestamp getLockTime() {
        return this.lockTime;
    }

    public void setLockTime(Timestamp lockTime) {
        this.lockTime = lockTime;
    }

    public BigDecimal getCountEnabled() {
        return this.isCountEnabled;
    }

    public void setCountEnabled(BigDecimal countEnabled) {
        this.isCountEnabled = countEnabled;
    }

    public BigDecimal getEvtSubscrCount() {
        return this.evtSubscrCount;
    }

    public void setEvtSubscrCount(BigDecimal evtSubscrCount) {
        this.evtSubscrCount = evtSubscrCount;
    }

    public BigDecimal getTaskCount() {
        return this.taskCount;
    }

    public void setTaskCount(BigDecimal taskCount) {
        this.taskCount = taskCount;
    }

    public BigDecimal getJobCount() {
        return this.jobCount;
    }

    public void setJobCount(BigDecimal jobCount) {
        this.jobCount = jobCount;
    }

    public BigDecimal getTimerJobCount() {
        return this.timerJobCount;
    }

    public void setTimerJobCount(BigDecimal timerJobCount) {
        this.timerJobCount = timerJobCount;
    }

    public BigDecimal getSuspJobCount() {
        return this.suspJobCount;
    }

    public void setSuspJobCount(BigDecimal suspJobCount) {
        this.suspJobCount = suspJobCount;
    }

    public BigDecimal getDeadletterJobCount() {
        return this.deadletterJobCount;
    }

    public void setDeadletterJobCount(BigDecimal deadletterJobCount) {
        this.deadletterJobCount = deadletterJobCount;
    }

    public BigDecimal getVarCount() {
        return this.varCount;
    }

    public void setVarCount(BigDecimal varCount) {
        this.varCount = varCount;
    }

    public BigDecimal getIdLinkCount() {
        return this.idLinkCount;
    }

    public void setIdLinkCount(BigDecimal idLinkCount) {
        this.idLinkCount = idLinkCount;
    }

    public String toString() {
        return "NrExecutionEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", procInstId='" + this.procInstId + '\'' + ", businessKey='" + this.businessKey + '\'' + ", parentId='" + this.parentId + '\'' + ", procDefId='" + this.procDefId + '\'' + ", superExec='" + this.superExec + '\'' + ", rootProcInstId='" + this.rootProcInstId + '\'' + ", actId='" + this.actId + '\'' + ", isActive=" + this.isActive + ", isConcurrent=" + this.isConcurrent + ", isScope=" + this.isScope + ", isEventScope=" + this.isEventScope + ", isMiRoot=" + this.isMiRoot + ", suspensionState=" + this.suspensionState + ", cachedEntState=" + this.cachedEntState + ", tenantId='" + this.tenantId + '\'' + ", name='" + this.name + '\'' + ", startTime=" + this.startTime + ", startUserId='" + this.startUserId + '\'' + ", lockTime=" + this.lockTime + ", isCountEnabled=" + this.isCountEnabled + ", evtSubscrCount=" + this.evtSubscrCount + ", taskCount=" + this.taskCount + ", jobCount=" + this.jobCount + ", timerJobCount=" + this.timerJobCount + ", suspJobCount=" + this.suspJobCount + ", deadletterJobCount=" + this.deadletterJobCount + ", varCount=" + this.varCount + ", idLinkCount=" + this.idLinkCount + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrExecutionEntityImpl that = (NrExecutionEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

