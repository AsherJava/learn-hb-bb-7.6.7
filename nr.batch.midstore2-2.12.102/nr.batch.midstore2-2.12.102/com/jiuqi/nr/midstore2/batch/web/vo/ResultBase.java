/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.midstore2.batch.web.vo.ResultPeriod;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResultBase {
    private String task;
    private String schemes;
    private String exchangeMode;
    private boolean single;
    private boolean allUnit;
    private List<String> failSchemes;
    private boolean isDeleteEmpty;
    private Map<String, String> dimTitleMap;
    private List<ResultPeriod> resultPeriods;
    private String beginTime;
    private String endTime;

    public void addFailScheme(String schemeMsg) {
        if (this.failSchemes == null) {
            this.failSchemes = new ArrayList<String>();
        }
        this.failSchemes.add(schemeMsg);
    }

    public void addResultPeriod(ResultPeriod resultPeriod) {
        if (this.resultPeriods == null) {
            this.resultPeriods = new ArrayList<ResultPeriod>();
        }
        this.resultPeriods.add(resultPeriod);
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getSchemes() {
        return this.schemes;
    }

    public void setSchemes(String schemes) {
        this.schemes = schemes;
    }

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public boolean isSingle() {
        return this.single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public boolean isAllUnit() {
        return this.allUnit;
    }

    public void setAllUnit(boolean allUnit) {
        this.allUnit = allUnit;
    }

    public List<String> getFailSchemes() {
        return this.failSchemes;
    }

    public void setFailSchemes(List<String> failSchemes) {
        this.failSchemes = failSchemes;
    }

    public boolean isDeleteEmpty() {
        return this.isDeleteEmpty;
    }

    public void setDeleteEmpty(boolean deleteEmpty) {
        this.isDeleteEmpty = deleteEmpty;
    }

    public Map<String, String> getDimTitleMap() {
        return this.dimTitleMap;
    }

    public void setDimTitleMap(Map<String, String> dimTitleMap) {
        this.dimTitleMap = dimTitleMap;
    }

    public List<ResultPeriod> getResultPeriods() {
        return this.resultPeriods;
    }

    public void setResultPeriods(List<ResultPeriod> resultPeriods) {
        this.resultPeriods = resultPeriods;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

