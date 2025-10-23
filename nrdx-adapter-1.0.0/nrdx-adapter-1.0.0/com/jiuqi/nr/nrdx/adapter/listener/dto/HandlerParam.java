/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.nrdx.adapter.listener.dto;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerParam {
    private String recordKey;
    private String taskKey;
    private String formSchemeKey;
    private boolean doUpload;
    private boolean allowForceUpload;
    private String uploadDes;
    private final Map<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
    private final List<String> forms = new ArrayList<String>();
    private IProgressMonitor progressMonitor;

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public boolean isDoUpload() {
        return this.doUpload;
    }

    public void setDoUpload(boolean doUpload) {
        this.doUpload = doUpload;
    }

    public boolean isAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(boolean allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    public String getUploadDes() {
        return this.uploadDes;
    }

    public void setUploadDes(String uploadDes) {
        this.uploadDes = uploadDes;
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public IProgressMonitor getProgressMonitor() {
        return this.progressMonitor;
    }

    public void setProgressMonitor(IProgressMonitor progressMonitor) {
        this.progressMonitor = progressMonitor;
    }
}

