/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.MeasureData
 *  com.jiuqi.nr.jtable.params.output.SecretLevelItem
 *  com.jiuqi.nr.period.common.rest.PeriodDataSelectObject
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.bean.ExecuteFuncParam;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.PeriodRegion;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.params.output.SecretLevelItem;
import com.jiuqi.nr.period.common.rest.PeriodDataSelectObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncExecResult {
    private ExecuteFuncParam funcParam;
    private TaskData task;
    private List<FormSchemeResult> schemes;
    private Map<String, List<EntityData>> entityDatas = new HashMap<String, List<EntityData>>();
    private List<MeasureData> measureDatas;
    private String formSchemeKey;
    private String currentPeriodInfo;
    private String currentPeriodTitle;
    private List<CustomPeriodData> customPeriodDatas;
    private Map<String, Integer> periodSchemeMap;
    private List<PeriodRegion> periodRangeList;
    private List<String> schemeKeyList;
    private List<PeriodDataSelectObject> periodModifyTitles;
    private List<SecretLevelItem> secretLevelItems;

    public List<String> getSchemeKeyList() {
        return this.schemeKeyList;
    }

    public void setSchemeKeyList(List<String> schemeKeyList) {
        this.schemeKeyList = schemeKeyList;
    }

    public FuncExecResult() {
        this.funcParam = new ExecuteFuncParam();
    }

    public List<CustomPeriodData> getCustomPeriodDatas() {
        return this.customPeriodDatas;
    }

    public void setCustomPeriodDatas(List<CustomPeriodData> customPeriodDatas) {
        this.customPeriodDatas = customPeriodDatas;
    }

    public List<FormSchemeResult> getSchemes() {
        return this.schemes;
    }

    public void setSchemes(List<FormSchemeResult> schemes) {
        this.schemes = schemes;
    }

    public ExecuteFuncParam getFuncParam() {
        return this.funcParam;
    }

    public void setFuncParam(ExecuteFuncParam funcParam) {
        this.funcParam = funcParam;
    }

    public List<MeasureData> getMeasureDatas() {
        return this.measureDatas;
    }

    public void setMeasureDatas(List<MeasureData> measureDatas) {
        this.measureDatas = measureDatas;
    }

    public Map<String, List<EntityData>> getEntityDatas() {
        return this.entityDatas;
    }

    public void setEntityDatas(Map<String, List<EntityData>> entityDatas) {
        this.entityDatas = entityDatas;
    }

    public TaskData getTask() {
        return this.task;
    }

    public void setTask(TaskData task) {
        this.task = task;
    }

    public List<SecretLevelItem> getSecretLevelItems() {
        return this.secretLevelItems;
    }

    public void setSecretLevelItems(List<SecretLevelItem> secretLevelItems) {
        this.secretLevelItems = secretLevelItems;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getCurrentPeriodInfo() {
        return this.currentPeriodInfo;
    }

    public void setCurrentPeriodInfo(String currentPeriodInfo) {
        this.currentPeriodInfo = currentPeriodInfo;
    }

    public Map<String, Integer> getPeriodSchemeMap() {
        return this.periodSchemeMap;
    }

    public void setPeriodSchemeMap(Map<String, Integer> periodSchemeMap) {
        this.periodSchemeMap = periodSchemeMap;
    }

    public List<PeriodRegion> getPeriodRangeList() {
        return this.periodRangeList;
    }

    public void setPeriodRangeList(List<PeriodRegion> periodRangeList) {
        this.periodRangeList = periodRangeList;
    }

    public List<PeriodDataSelectObject> getPeriodModifyTitles() {
        return this.periodModifyTitles;
    }

    public void setPeriodModifyTitles(List<PeriodDataSelectObject> periodModifyTitles) {
        this.periodModifyTitles = periodModifyTitles;
    }

    public String getCurrentPeriodTitle() {
        return this.currentPeriodTitle;
    }

    public void setCurrentPeriodTitle(String currentPeriodTitle) {
        this.currentPeriodTitle = currentPeriodTitle;
    }
}

