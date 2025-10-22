/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;
import org.springframework.util.StringUtils;

public class UploadRecordNew
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String formKey;
    private String action;
    private String returnType;
    private String cmt;
    private String time;
    private String operator;
    private String operationid;
    private String taskId;
    @JsonIgnore
    public String entities;
    private String unitKey;
    private String roleKey;

    public UploadRecordNew(String formKey, String action, String returnType, String cmt, String time, String operator, String operationid, String taskId, DimensionValueSet entities, String unitKey) {
        this.formKey = formKey;
        this.action = action;
        this.returnType = returnType;
        this.cmt = cmt;
        this.time = time;
        this.operator = operator;
        this.operationid = operationid;
        this.taskId = taskId;
        this.entities = entities.toString();
        this.unitKey = unitKey;
    }

    public UploadRecordNew() {
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getCmt() {
        return this.cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperationid() {
        return this.operationid;
    }

    public void setOperationid(String operationid) {
        this.operationid = operationid;
    }

    public DimensionValueSet getEntities() {
        if (StringUtils.hasText(this.entities)) {
            DimensionValueSet dimension = new DimensionValueSet();
            dimension.parseString(this.entities);
            return dimension;
        }
        return null;
    }

    public void setEntities(DimensionValueSet entities) {
        this.entities = entities.toString();
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getRoleKey() {
        return this.roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }
}

