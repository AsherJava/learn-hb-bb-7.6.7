/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.ParamConfig;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.util.List;

public class ParameterBuildInfo {
    private String solutionKey;
    private String taskKey;
    private String reportKey;
    private boolean withPeriod = true;
    private boolean withMaster = true;
    private boolean withScene = true;
    private ParamConfig periodParam;
    private ParamConfig masterDimParam;
    private List<ParamConfig> sceneDimParams;
    private ParameterSelectMode sceneSelectMode = ParameterSelectMode.SINGLE;
    private String sceneDefaultValueMode = "none";

    public String getSolutionKey() {
        return this.solutionKey;
    }

    public void setSolutionKey(String solutionKey) {
        this.solutionKey = solutionKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getReportKey() {
        return this.reportKey;
    }

    public void setReportKey(String reportKey) {
        this.reportKey = reportKey;
    }

    public boolean isWithPeriod() {
        return this.withPeriod;
    }

    public void setWithPeriod(boolean withPeriod) {
        this.withPeriod = withPeriod;
    }

    public boolean isWithMaster() {
        return this.withMaster;
    }

    public void setWithMaster(boolean withMaster) {
        this.withMaster = withMaster;
    }

    public boolean isWithScene() {
        return this.withScene;
    }

    public void setWithScene(boolean withScene) {
        this.withScene = withScene;
    }

    public ParamConfig getPeriodParam() {
        return this.periodParam;
    }

    public void setPeriodParam(ParamConfig periodParam) {
        this.periodParam = periodParam;
    }

    public ParamConfig getMasterDimParam() {
        return this.masterDimParam;
    }

    public void setMasterDimParam(ParamConfig masterDimParam) {
        this.masterDimParam = masterDimParam;
    }

    public List<ParamConfig> getSceneDimParams() {
        return this.sceneDimParams;
    }

    public void setSceneDimParams(List<ParamConfig> sceneDimParams) {
        this.sceneDimParams = sceneDimParams;
    }

    public ParameterSelectMode getSceneSelectMode() {
        return this.sceneSelectMode;
    }

    public void setSceneSelectMode(ParameterSelectMode sceneSelectMode) {
        this.sceneSelectMode = sceneSelectMode;
    }

    public String getSceneDefaultValueMode() {
        return this.sceneDefaultValueMode;
    }

    public void setSceneDefaultValueMode(String sceneDefaultValueMode) {
        this.sceneDefaultValueMode = sceneDefaultValueMode;
    }
}

