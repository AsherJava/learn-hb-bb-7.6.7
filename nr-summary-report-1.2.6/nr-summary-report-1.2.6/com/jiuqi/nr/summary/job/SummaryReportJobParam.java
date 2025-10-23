/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.job;

import com.jiuqi.nr.summary.common.bean.FSumParam;
import com.jiuqi.nr.summary.vo.SumUnit;
import java.util.List;
import java.util.Map;

public class SummaryReportJobParam
implements FSumParam {
    private String solutionKey;
    private List<String> reportKeys;
    private List<String> periods;
    private Map<String, String[]> scenes;
    private boolean afterCalculate;

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
    public List<String> getPeriods() {
        return this.periods;
    }

    @Override
    public SumUnit getUnit() {
        return null;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }

    @Override
    public Map<String, String[]> getScenes() {
        return this.scenes;
    }

    public void setScenes(Map<String, String[]> scenes) {
        this.scenes = scenes;
    }

    @Override
    public boolean isAfterCalculate() {
        return this.afterCalculate;
    }

    public void setAfterCalculate(boolean afterCalculate) {
        this.afterCalculate = afterCalculate;
    }
}

