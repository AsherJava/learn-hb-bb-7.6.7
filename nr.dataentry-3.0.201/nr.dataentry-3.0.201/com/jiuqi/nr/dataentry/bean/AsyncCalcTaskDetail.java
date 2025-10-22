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

public class AsyncCalcTaskDetail
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String dataTimeTitle;
    private List<String> formulaNames;
    private String dwName;
    private List<FormDefine> formList;
    private List<Map<String, String>> dwList;
    private transient List<Map<String, Object>> dimensionNames = new ArrayList<Map<String, Object>>();

    public String getDataTimeTitle() {
        return this.dataTimeTitle;
    }

    public void setDataTimeTitle(String dataTimeTitle) {
        this.dataTimeTitle = dataTimeTitle;
    }

    public List<String> getFormulaNames() {
        return this.formulaNames;
    }

    public void setFormulaNames(List<String> formulaNames) {
        this.formulaNames = formulaNames;
    }

    public List<FormDefine> getFormList() {
        return this.formList;
    }

    public void setFormList(List<FormDefine> formList) {
        this.formList = formList;
    }

    public List<Map<String, String>> getDwList() {
        return this.dwList;
    }

    public void setDwList(List<Map<String, String>> dwList) {
        this.dwList = dwList;
    }

    public List<Map<String, Object>> getDimensionNames() {
        return this.dimensionNames;
    }

    public String getDwName() {
        return this.dwName;
    }

    public void setDwName(String dwName) {
        this.dwName = dwName;
    }

    public void setDimensionNames(List<Map<String, Object>> dimensionNames) {
        this.dimensionNames = dimensionNames;
    }
}

