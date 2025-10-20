/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.model.tfv.intf;

import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.SelectColumnLoc;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvExecuteSettingVO;
import com.jiuqi.bde.bizmodel.execute.model.tfv.intf.TfvOptimKey;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TfvDataCondi {
    private FetchTaskContext fetchTaskContext;
    private String sql;
    private TfvOptimKey optimKey;
    private Set<String> mainDimValueSet;
    private Set<String> secondDimValueSet;
    private LinkedHashMap<String, SelectColumnLoc> selectColumnLocMap;
    private Map<String, List<TfvExecuteSettingVO>> executeSettingMap;

    public TfvDataCondi() {
    }

    public TfvDataCondi(FetchTaskContext fetchTaskContext) {
        this.fetchTaskContext = fetchTaskContext;
    }

    public FetchTaskContext getFetchTaskContext() {
        return this.fetchTaskContext;
    }

    public void setFetchTaskContext(FetchTaskContext fetchTaskContext) {
        this.fetchTaskContext = fetchTaskContext;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public TfvOptimKey getOptimKey() {
        return this.optimKey;
    }

    public void setOptimKey(TfvOptimKey optimKey) {
        this.optimKey = optimKey;
    }

    public Set<String> getMainDimValueSet() {
        return this.mainDimValueSet;
    }

    public void setMainDimValueSet(Set<String> mainDimValueSet) {
        this.mainDimValueSet = mainDimValueSet;
    }

    public Set<String> getSecondDimValueSet() {
        return this.secondDimValueSet;
    }

    public void setSecondDimValueSet(Set<String> secondDimValueSet) {
        this.secondDimValueSet = secondDimValueSet;
    }

    public Map<String, SelectColumnLoc> getSelectColumnLocMap() {
        return this.selectColumnLocMap;
    }

    public void setSelectColumnLocMap(LinkedHashMap<String, SelectColumnLoc> selectColumnLocMap) {
        this.selectColumnLocMap = selectColumnLocMap;
    }

    public Map<String, List<TfvExecuteSettingVO>> getExecuteSettingMap() {
        return this.executeSettingMap;
    }

    public void setExecuteSettingMap(Map<String, List<TfvExecuteSettingVO>> executeSettingMap) {
        this.executeSettingMap = executeSettingMap;
    }

    public String toString() {
        return "TfvDataCondi [fetchTaskContext=" + this.fetchTaskContext + ", sql=" + this.sql + ", optimKey=" + this.optimKey + ", mainDimValueSet=" + this.mainDimValueSet + ", secondDimValueSet=" + this.secondDimValueSet + ", selectColumnLocMap=" + this.selectColumnLocMap + ", executeSettingMap=" + this.executeSettingMap + "]";
    }
}

