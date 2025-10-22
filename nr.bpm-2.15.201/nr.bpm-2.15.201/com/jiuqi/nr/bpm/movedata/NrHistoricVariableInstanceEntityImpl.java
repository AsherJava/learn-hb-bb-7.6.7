/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.util.Date;

public class NrHistoricVariableInstanceEntityImpl {
    private String id;
    private String processInstanceId;
    private String taskId;
    private String executionId;
    private String name;
    private String varType;
    private BigDecimal rev;
    private String byteArrayId;
    private Double doubleValue;
    private Long longValue;
    private String text;
    private String text2;
    private Date createTime;
    private Date lastUpdatedTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVarType() {
        return this.varType;
    }

    public void setVarType(String varType) {
        this.varType = varType;
    }

    public BigDecimal getRev() {
        return this.rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public String getByteArrayId() {
        return this.byteArrayId;
    }

    public void setByteArrayId(String byteArrayId) {
        this.byteArrayId = byteArrayId;
    }

    public Double getDoubleValue() {
        return this.doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Long getLongValue() {
        return this.longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return this.text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdatedTime() {
        return this.lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}

