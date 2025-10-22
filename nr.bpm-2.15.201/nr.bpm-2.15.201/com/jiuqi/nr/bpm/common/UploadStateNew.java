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
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import java.io.Serializable;
import java.util.Date;
import org.springframework.util.StringUtils;

public class UploadStateNew
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String preEvent;
    private String taskId;
    private String formId;
    private ActionStateBean actionStateBean;
    @JsonIgnore
    private String entities;
    private Date startTime;
    private Date updateTime;
    private String force;

    public String getPreEvent() {
        return this.preEvent;
    }

    public void setPreEvent(String preEvent) {
        this.preEvent = preEvent;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public ActionStateBean getActionStateBean() {
        return this.actionStateBean;
    }

    public void setActionStateBean(ActionStateBean actionStateBean) {
        this.actionStateBean = actionStateBean;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getForce() {
        return this.force;
    }

    public void setForce(String force) {
        this.force = force;
    }
}

