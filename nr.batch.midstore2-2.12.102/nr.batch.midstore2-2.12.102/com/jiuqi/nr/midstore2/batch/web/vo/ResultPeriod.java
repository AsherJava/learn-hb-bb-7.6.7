/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.jiuqi.nr.midstore2.batch.web.vo.ResultColumn;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnitInfo;
import com.jiuqi.nr.midstore2.batch.web.vo.ResultFailUnitVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultPeriod
implements Serializable {
    private String period;
    private int selectUnitsCount;
    private List<String> failSchemes;
    private int successCount;
    private int ignoreCount;
    private int failCount;
    private List<ResultColumn> successUnits;
    private List<ResultColumn> ignoreUnits;
    private List<ResultFailUnitVO> failUnits;
    private Map<String, List<ResultFailUnitInfo>> failUnitInfos;

    public void addFailScheme(String failScheme) {
        if (this.failSchemes == null) {
            this.failSchemes = new ArrayList<String>();
        }
        this.failSchemes.add(failScheme);
    }

    public void addSuccessUnit(ResultColumn successUnit) {
        if (this.successUnits == null) {
            this.successUnits = new ArrayList<ResultColumn>();
        }
        this.successUnits.add(successUnit);
    }

    public void addIgnoreUnit(ResultColumn ignoreUnit) {
        if (this.ignoreUnits == null) {
            this.ignoreUnits = new ArrayList<ResultColumn>();
        }
        this.ignoreUnits.add(ignoreUnit);
    }

    public void addFailUnitInfo(String unitCode, ResultFailUnitInfo info) {
        List<ResultFailUnitInfo> resultFailUnitInfos;
        if (this.failUnitInfos == null) {
            this.failUnitInfos = new HashMap<String, List<ResultFailUnitInfo>>();
        }
        if ((resultFailUnitInfos = this.failUnitInfos.get(unitCode)) == null) {
            resultFailUnitInfos = new ArrayList<ResultFailUnitInfo>();
            this.failUnitInfos.put(unitCode, resultFailUnitInfos);
        }
        resultFailUnitInfos.add(info);
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getFailSchemes() {
        return this.failSchemes;
    }

    public void setFailSchemes(List<String> failSchemes) {
        this.failSchemes = failSchemes;
    }

    public int getSelectUnitsCount() {
        return this.selectUnitsCount;
    }

    public void setSelectUnitsCount(int selectUnitsCount) {
        this.selectUnitsCount = selectUnitsCount;
    }

    public int getSuccessCount() {
        return this.successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getIgnoreCount() {
        return this.ignoreCount;
    }

    public void setIgnoreCount(int ignoreCount) {
        this.ignoreCount = ignoreCount;
    }

    public int getFailCount() {
        return this.failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public List<ResultColumn> getSuccessUnits() {
        return this.successUnits;
    }

    public void setSuccessUnits(List<ResultColumn> successUnits) {
        this.successUnits = successUnits;
    }

    public List<ResultColumn> getIgnoreUnits() {
        return this.ignoreUnits;
    }

    public void setIgnoreUnits(List<ResultColumn> ignoreUnits) {
        this.ignoreUnits = ignoreUnits;
    }

    public List<ResultFailUnitVO> getFailUnits() {
        return this.failUnits;
    }

    public void setFailUnits(List<ResultFailUnitVO> failUnits) {
        this.failUnits = failUnits;
    }

    public Map<String, List<ResultFailUnitInfo>> getFailUnitInfos() {
        return this.failUnitInfos;
    }

    public void setFailUnitInfos(Map<String, List<ResultFailUnitInfo>> failUnitInfos) {
        this.failUnitInfos = failUnitInfos;
    }
}

