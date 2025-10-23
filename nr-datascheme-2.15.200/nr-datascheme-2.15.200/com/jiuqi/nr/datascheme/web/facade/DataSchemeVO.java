/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.param.AdjustPeriodVO;
import com.jiuqi.nr.datascheme.web.param.ReportDimVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataSchemeVO
extends BaseDataSchemeVO {
    private String orgDimKey;
    private List<String> orgDimScope;
    private String periodDimKey;
    private int periodType;
    private List<String> dimKeys;
    private Map<String, ReportDimVO> reportDimMap;
    private Boolean enableAdjustmentPeriod;
    private List<AdjustPeriodVO> adjustPeriodVOList;
    private boolean deployed;

    public String getOrgDimKey() {
        return this.orgDimKey;
    }

    public void setOrgDimKey(String orgDimKey) {
        this.orgDimKey = orgDimKey;
    }

    public int getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(int periodType) {
        this.periodType = periodType;
    }

    public String getPeriodDimKey() {
        return this.periodDimKey;
    }

    public void setPeriodDimKey(String periodDimKey) {
        this.periodDimKey = periodDimKey;
    }

    public List<String> getDimKeys() {
        if (null == this.dimKeys) {
            this.dimKeys = new ArrayList<String>();
        }
        return this.dimKeys;
    }

    public void setDimKeys(List<String> dimKeys) {
        this.dimKeys = dimKeys;
    }

    public List<String> getOrgDimScope() {
        return this.orgDimScope;
    }

    public void setOrgDimScope(List<String> orgDimScope) {
        this.orgDimScope = orgDimScope;
    }

    public Map<String, ReportDimVO> getReportDimMap() {
        if (this.reportDimMap == null) {
            this.reportDimMap = new HashMap<String, ReportDimVO>();
        }
        return this.reportDimMap;
    }

    public void setReportDimMap(Map<String, ReportDimVO> reportDimMap) {
        this.reportDimMap = reportDimMap;
    }

    public Boolean getEnableAdjustmentPeriod() {
        return this.enableAdjustmentPeriod;
    }

    public void setEnableAdjustmentPeriod(Boolean enableAdjustmentPeriod) {
        this.enableAdjustmentPeriod = enableAdjustmentPeriod;
    }

    public List<AdjustPeriodVO> getAdjustPeriodVOList() {
        if (this.adjustPeriodVOList == null) {
            this.adjustPeriodVOList = new ArrayList<AdjustPeriodVO>();
        }
        return this.adjustPeriodVOList;
    }

    public void setAdjustPeriodVOList(List<AdjustPeriodVO> adjustPeriodVOList) {
        this.adjustPeriodVOList = adjustPeriodVOList;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }
}

