/*
 * Decompiled with CFR 0.152.
 */
package nr.single.para.parain.bean;

import java.util.ArrayList;
import java.util.List;
import nr.single.para.parain.bean.SingleQueryFieldDefine;

public class SingleQueryTemplateDefine {
    private String code;
    private String title;
    private String groupNames;
    private String condition;
    private String conditionTitle;
    private byte[] ReportData;
    private List<SingleQueryFieldDefine> queryFields;
    private String taskKey;
    private String formSchemeKey;
    private boolean doSumary;
    private int colNum;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<SingleQueryFieldDefine> getQueryFields() {
        if (this.queryFields == null) {
            this.queryFields = new ArrayList<SingleQueryFieldDefine>();
        }
        return this.queryFields;
    }

    public void setQueryFields(List<SingleQueryFieldDefine> queryFields) {
        this.queryFields = queryFields;
    }

    public String getGroupNames() {
        return this.groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public byte[] getReportData() {
        return this.ReportData;
    }

    public void setReportData(byte[] reportData) {
        this.ReportData = reportData;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionTitle() {
        return this.conditionTitle;
    }

    public void setConditionTitle(String conditionTitle) {
        this.conditionTitle = conditionTitle;
    }

    public boolean isDoSumary() {
        return this.doSumary;
    }

    public void setDoSumary(boolean doSumary) {
        this.doSumary = doSumary;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }
}

