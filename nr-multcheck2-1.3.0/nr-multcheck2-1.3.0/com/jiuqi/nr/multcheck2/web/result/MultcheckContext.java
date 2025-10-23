/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.web.result;

import com.jiuqi.nr.multcheck2.web.result.ResultParam;
import com.jiuqi.nr.multcheck2.web.vo.MCLabel;
import java.util.List;
import java.util.Map;

public class MultcheckContext {
    private String runId;
    private String task;
    private String period;
    private String formScheme;
    private String mcScheme;
    private Map<String, String> dimSet;
    private ResultParam resultParam;
    private List<MCLabel> unboundList;
    private String time;
    private String totalTime;
    private boolean enableDim;
    private String periodEntity;
    private String orgEntity;
    private Map<String, String> entitys;
    private Map<String, Map<String, String>> entityValues;

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(String periodEntity) {
        this.periodEntity = periodEntity;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getMcScheme() {
        return this.mcScheme;
    }

    public void setMcScheme(String mcScheme) {
        this.mcScheme = mcScheme;
    }

    public Map<String, String> getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(Map<String, String> dimSet) {
        this.dimSet = dimSet;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isEnableDim() {
        return this.enableDim;
    }

    public void setEnableDim(boolean enableDim) {
        this.enableDim = enableDim;
    }

    public Map<String, String> getEntitys() {
        return this.entitys;
    }

    public void setEntitys(Map<String, String> entitys) {
        this.entitys = entitys;
    }

    public Map<String, Map<String, String>> getEntityValues() {
        return this.entityValues;
    }

    public void setEntityValues(Map<String, Map<String, String>> entityValues) {
        this.entityValues = entityValues;
    }

    public ResultParam getResultParam() {
        return this.resultParam;
    }

    public void setResultParam(ResultParam resultParam) {
        this.resultParam = resultParam;
    }

    public List<MCLabel> getUnboundList() {
        return this.unboundList;
    }

    public void setUnboundList(List<MCLabel> unboundList) {
        this.unboundList = unboundList;
    }

    public String getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}

