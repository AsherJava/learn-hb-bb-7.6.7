/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import java.util.List;
import java.util.Map;

public class AnalysisVO {
    private String fileKey;
    private boolean flowType;
    private List<MappingSchemeVO> mappingSchemes;
    private String taskKey;
    private String taskTitle;
    private String taskDw;
    private String taskDwTitle;
    private String formSchemeKey;
    private String formSchemeTitle;
    private String periodValue;
    private String periodType;
    private String periodTitle;
    private String adjustPeriod;
    private String adjustTitle;
    private List<String> entitys;
    private List<String> forms;
    private Map<String, List<String>> dims;
    private Map<String, String> dimTitle;
    private List<String> dataMessages;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isFlowType() {
        return this.flowType;
    }

    public boolean getFlowType() {
        return this.flowType;
    }

    public void setFlowType(boolean flowType) {
        this.flowType = flowType;
    }

    public List<MappingSchemeVO> getMappingSchemes() {
        return this.mappingSchemes;
    }

    public void setMappingSchemes(List<MappingSchemeVO> mappingSchemes) {
        this.mappingSchemes = mappingSchemes;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public String getTaskDw() {
        return this.taskDw;
    }

    public void setTaskDw(String taskDw) {
        this.taskDw = taskDw;
    }

    public String getTaskDwTitle() {
        return this.taskDwTitle;
    }

    public void setTaskDwTitle(String taskDwTitle) {
        this.taskDwTitle = taskDwTitle;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    public String getAdjustTitle() {
        return this.adjustTitle;
    }

    public void setAdjustTitle(String adjustTitle) {
        this.adjustTitle = adjustTitle;
    }

    public List<String> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(List<String> entitys) {
        this.entitys = entitys;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public Map<String, List<String>> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, List<String>> dims) {
        this.dims = dims;
    }

    public Map<String, String> getDimTitle() {
        return this.dimTitle;
    }

    public void setDimTitle(Map<String, String> dimTitle) {
        this.dimTitle = dimTitle;
    }

    public List<String> getDataMessages() {
        return this.dataMessages;
    }

    public void setDataMessages(List<String> dataMessages) {
        this.dataMessages = dataMessages;
    }
}

