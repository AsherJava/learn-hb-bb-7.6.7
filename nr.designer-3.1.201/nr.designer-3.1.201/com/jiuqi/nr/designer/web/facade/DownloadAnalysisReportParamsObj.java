/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.designer.web.facade.InjectContext;
import java.util.Map;

public class DownloadAnalysisReportParamsObj {
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private Map<String, DimensionValue> dimensionSet;
    private String dwmc;
    private String datatime;
    private InjectContext injectContext;

    public String getTaskKey() {
        return this.taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public String getDwmc() {
        return this.dwmc;
    }

    public String getDatatime() {
        return this.datatime;
    }

    public InjectContext getInjectContext() {
        return this.injectContext;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public void setInjectContext(InjectContext injectContext) {
        this.injectContext = injectContext;
    }
}

