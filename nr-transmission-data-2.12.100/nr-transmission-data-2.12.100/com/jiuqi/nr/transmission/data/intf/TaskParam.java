/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.transmission.data.intf;

import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.transmission.data.vo.MappingSchemeVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskParam {
    private String taskKey;
    private String taskTitle;
    private String taskDw;
    private String taskDwTitle;
    private Map<String, String> taskDimToTitle = new HashMap<String, String>();
    private String formSchemeKey;
    private String thenFormSchemeKey;
    private Map<String, List<MappingSchemeVO>> mappingSchemeMapsForTask;
    private List<MappingSchemeVO> mappingSchemes;
    private boolean isUndefinePeriod;
    private String fromPeriod;
    private String toPeriod;
    private String thenPeriod;
    private List<IPeriodRow> undefinePeriodValues;

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

    public Map<String, String> getTaskDimToTitle() {
        return this.taskDimToTitle;
    }

    public void setTaskDimToTitle(Map<String, String> taskDimToTitle) {
        this.taskDimToTitle = taskDimToTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getThenFormSchemeKey() {
        return this.thenFormSchemeKey;
    }

    public void setThenFormSchemeKey(String thenFormSchemeKey) {
        this.thenFormSchemeKey = thenFormSchemeKey;
    }

    public Map<String, List<MappingSchemeVO>> getMappingSchemeMapsForTask() {
        return this.mappingSchemeMapsForTask;
    }

    public void setMappingSchemeMapsForTask(Map<String, List<MappingSchemeVO>> mappingSchemeMapsForTask) {
        this.mappingSchemeMapsForTask = mappingSchemeMapsForTask;
    }

    public List<MappingSchemeVO> getMappingSchemes() {
        return this.mappingSchemes;
    }

    public void setMappingSchemes(List<MappingSchemeVO> mappingSchemes) {
        this.mappingSchemes = mappingSchemes;
    }

    public boolean isUndefinePeriod() {
        return this.isUndefinePeriod;
    }

    public void setUndefinePeriod(boolean undefinePeriod) {
        this.isUndefinePeriod = undefinePeriod;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public String getThenPeriod() {
        return this.thenPeriod;
    }

    public void setThenPeriod(String thenPeriod) {
        this.thenPeriod = thenPeriod;
    }

    public List<IPeriodRow> getUndefinePeriodValues() {
        return this.undefinePeriodValues;
    }

    public void setUndefinePeriodValues(List<IPeriodRow> undefinePeriodValues) {
        this.undefinePeriodValues = undefinePeriodValues;
    }
}

