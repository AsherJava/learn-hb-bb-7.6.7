/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.definition.facade.FormDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AsyncDatasumTaskDetail
implements Serializable {
    private String dataTimeTitle;
    private String targetDwName;
    private List<Map<String, String>> dwList;
    private List<FormDefine> formList;
    private String sumRange;
    private String dwName;
    private transient List<Map<String, Object>> dimensionNames = new ArrayList<Map<String, Object>>();

    public String getTargetDwName() {
        return this.targetDwName;
    }

    public void setTargetDwName(String targetDwName) {
        this.targetDwName = targetDwName;
    }

    public List<Map<String, String>> getDwList() {
        return this.dwList;
    }

    public void setDwList(List<Map<String, String>> dwList) {
        this.dwList = dwList;
    }

    public List<FormDefine> getFormList() {
        return this.formList;
    }

    public void setFormList(List<FormDefine> formList) {
        this.formList = formList;
    }

    public String getSumRange() {
        return this.sumRange;
    }

    public void setSumRange(String sumRange) {
        this.sumRange = sumRange;
    }

    public List<Map<String, Object>> getDimensionNames() {
        return this.dimensionNames;
    }

    public void setDimensionNames(List<Map<String, Object>> dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public String getDataTimeTitle() {
        return this.dataTimeTitle;
    }

    public void setDataTimeTitle(String dataTimeTitle) {
        this.dataTimeTitle = dataTimeTitle;
    }

    public String getDwName() {
        return this.dwName;
    }

    public void setDwName(String dwName) {
        this.dwName = dwName;
    }
}

