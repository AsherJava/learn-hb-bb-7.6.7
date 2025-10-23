/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecuteParam
implements IExecuteParam {
    private String taskKey;
    private String dateTime;
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private List<String> forms;
    private int doUpload;
    private int allowForceUpload;
    private String uploadDesc;
    private String extendParam;
    private Map<String, String> uploadDimMap = new HashMap<String, String>();
    private List<String> adjustPeriod = new ArrayList<String>();

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getDateTime() {
        return this.dateTime;
    }

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    @Override
    public List<String> getForms() {
        return this.forms;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    @Override
    public int getDoUpload() {
        return this.doUpload;
    }

    public void setDoUpload(int doUpload) {
        this.doUpload = doUpload;
    }

    @Override
    public int getAllowForceUpload() {
        return this.allowForceUpload;
    }

    public void setAllowForceUpload(int allowForceUpload) {
        this.allowForceUpload = allowForceUpload;
    }

    @Override
    public String getUploadDesc() {
        return this.uploadDesc;
    }

    public void setUploadDesc(String uploadDesc) {
        this.uploadDesc = uploadDesc;
    }

    @Override
    public String getExtendParam() {
        return this.extendParam;
    }

    public void setExtendParam(String extendParam) {
        this.extendParam = extendParam;
    }

    @Override
    public Map<String, String> getUploadDimMap() {
        return this.uploadDimMap;
    }

    public void setUploadDimMap(Map<String, String> uploadDimMap) {
        this.uploadDimMap = uploadDimMap;
    }

    @Override
    public List<String> getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(List<String> adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }
}

