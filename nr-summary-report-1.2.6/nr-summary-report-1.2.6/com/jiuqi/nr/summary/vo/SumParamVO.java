/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.common.bean.FSumParam;
import com.jiuqi.nr.summary.vo.SumUnit;
import java.util.List;
import java.util.Map;

public class SumParamVO
implements FSumParam {
    private String solutionKey;
    private List<String> reportKeys;
    private SumUnit unit;
    private List<String> periods;
    private Map<String, String[]> scenes;

    @Override
    public String getSolutionKey() {
        return this.solutionKey;
    }

    public void setSolutionKey(String solutionKey) {
        this.solutionKey = solutionKey;
    }

    @Override
    public List<String> getReportKeys() {
        return this.reportKeys;
    }

    public void setReportKeys(List<String> reportKeys) {
        this.reportKeys = reportKeys;
    }

    @Override
    public SumUnit getUnit() {
        return this.unit;
    }

    public void setUnit(SumUnit unit) {
        this.unit = unit;
    }

    @Override
    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    @Override
    public Map<String, String[]> getScenes() {
        return this.scenes;
    }

    @Override
    public boolean isAfterCalculate() {
        return true;
    }

    public void setScenes(Map<String, String[]> scenes) {
        this.scenes = scenes;
    }
}

