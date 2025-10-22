/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.math.BigDecimal;
import java.util.Objects;

public class NrVariableInstanceEntityImpl {
    private String id;
    private BigDecimal rev;
    private String type;
    private String name;
    private String executionId;
    private String procInstId;
    private String taskId;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String toString() {
        return "NrVariableInstanceEntityImpl{id='" + this.id + '\'' + ", rev=" + this.rev + ", type='" + this.type + '\'' + ", name='" + this.name + '\'' + ", executionId='" + this.executionId + '\'' + ", procInstId='" + this.procInstId + '\'' + ", taskId='" + this.taskId + '\'' + ", byteArrayId='" + this.byteArrayId + '\'' + ", doubleValue=" + this.doubleValue + ", longValue=" + this.longValue + ", text='" + this.text + '\'' + ", text2='" + this.text2 + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrVariableInstanceEntityImpl that = (NrVariableInstanceEntityImpl)o;
        return Objects.equals(this.id, that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

