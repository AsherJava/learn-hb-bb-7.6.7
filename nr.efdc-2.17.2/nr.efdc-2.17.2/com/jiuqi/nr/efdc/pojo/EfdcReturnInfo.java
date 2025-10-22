/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.EntityData
 */
package com.jiuqi.nr.efdc.pojo;

import com.jiuqi.nr.jtable.params.output.EntityData;
import java.io.Serializable;
import java.util.Map;

public class EfdcReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String updateTime;
    private int status;
    private EntityData entity;
    private Map<String, String> dimensionTitle;
    private Map<String, String> formMessage;
    private String message;

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public EntityData getEntity() {
        return this.entity;
    }

    public void setEntity(EntityData entityData) {
        this.entity = entityData;
    }

    public Map<String, String> getDimensionTitle() {
        return this.dimensionTitle;
    }

    public void setDimensionTitle(Map<String, String> dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    public Map<String, String> getFormMessage() {
        return this.formMessage;
    }

    public void setFormMessage(Map<String, String> formMessage) {
        this.formMessage = formMessage;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

