/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.tree;

import com.jiuqi.nr.dataentry.bean.MeasureViewData;
import java.util.List;
import java.util.Map;

public class FormTreeItem {
    private String key;
    private String code;
    private String title;
    private String type;
    private String serialNumber;
    private String formType;
    private boolean dataSum;
    private String groupKey;
    private List<MeasureViewData> measures;
    private Map<String, String> measureValues;
    private boolean analysisForm;
    private boolean needReload;
    private String analysisReportKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public boolean isDataSum() {
        return this.dataSum;
    }

    public void setDataSum(boolean dataSum) {
        this.dataSum = dataSum;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<MeasureViewData> getMeasures() {
        return this.measures;
    }

    public void setMeasures(List<MeasureViewData> measures) {
        this.measures = measures;
    }

    public Map<String, String> getMeasureValues() {
        return this.measureValues;
    }

    public void setMeasureValues(Map<String, String> measureValues) {
        this.measureValues = measureValues;
    }

    public boolean isAnalysisForm() {
        return this.analysisForm;
    }

    public void setAnalysisForm(boolean analysisForm) {
        this.analysisForm = analysisForm;
    }

    public boolean isNeedReload() {
        return this.needReload;
    }

    public void setNeedReload(boolean needReload) {
        this.needReload = needReload;
    }

    public String getAnalysisReportKey() {
        return this.analysisReportKey;
    }

    public void setAnalysisReportKey(String analysisReportKey) {
        this.analysisReportKey = analysisReportKey;
    }
}

