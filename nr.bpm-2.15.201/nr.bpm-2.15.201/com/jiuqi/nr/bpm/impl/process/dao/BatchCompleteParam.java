/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.impl.process.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public class BatchCompleteParam {
    private List<DimensionValueSet> masterKeysList;
    private DimensionValueSet masterKey;
    private String actionId;
    private String taskId;
    private String formSchemeKey;
    private Boolean isForceUpload;

    public Boolean getForceUpload() {
        return this.isForceUpload;
    }

    public void setForceUpload(Boolean forceUpload) {
        this.isForceUpload = forceUpload;
    }

    public List<DimensionValueSet> getMasterKeysList() {
        return this.masterKeysList;
    }

    public void setMasterKeysList(List<DimensionValueSet> masterKeysList) {
        this.masterKeysList = masterKeysList;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

