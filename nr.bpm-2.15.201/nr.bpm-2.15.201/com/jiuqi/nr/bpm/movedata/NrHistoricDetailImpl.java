/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.util.Date;

public class NrHistoricDetailImpl {
    private String id;
    private String type;
    private String processInstanceId;
    private String executionId;
    private String taskId;
    private String activityInstanceId;
    private String name;
    private String varType;
    private BigDecimal rev;
    private Date time;
    private String byteArrayId;
    private BigDecimal doubleValue;
    private BigDecimal longValue;
    private String text;
    private String text2;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getExecutionId() {
        return this.executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getActivityInstanceId() {
        return this.activityInstanceId;
    }

    public void setActivityInstanceId(String activityInstanceId) {
        this.activityInstanceId = activityInstanceId;
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

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getByteArrayId() {
        return this.byteArrayId;
    }

    public void setByteArrayId(String byteArrayId) {
        this.byteArrayId = byteArrayId;
    }

    public BigDecimal getDoubleValue() {
        return this.doubleValue;
    }

    public void setDoubleValue(BigDecimal doubleValue) {
        this.doubleValue = doubleValue;
    }

    public BigDecimal getLongValue() {
        return this.longValue;
    }

    public void setLongValue(BigDecimal longValue) {
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
}

