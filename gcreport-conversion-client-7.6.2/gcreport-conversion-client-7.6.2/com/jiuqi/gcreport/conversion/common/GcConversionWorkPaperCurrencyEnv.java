/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IDataTable
 */
package com.jiuqi.gcreport.conversion.common;

import com.jiuqi.gcreport.conversion.common.GcConversionIndexRateInfo;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.np.dataengine.intf.IDataTable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class GcConversionWorkPaperCurrencyEnv {
    private List<GcConversionIndexRateInfo> indexRateInfos;
    private IDataTable priorBeforeQueryDataTable;
    private IDataTable priorAfterQueryDataTable;
    private IDataTable beforeDataTable;
    private IDataTable afterDataTable;
    private Map<String, BigDecimal> rateInfos;
    private GcConversionOrgAndFormContextEnv gcConversionOrgAndFormContextEnv;
    private String afterCurrencyCode;
    private String periodStr;

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getAfterCurrencyCode() {
        return this.afterCurrencyCode;
    }

    public void setAfterCurrencyCode(String afterCurrencyCode) {
        this.afterCurrencyCode = afterCurrencyCode;
    }

    public GcConversionOrgAndFormContextEnv getGcConversionOrgAndFormContextEnv() {
        return this.gcConversionOrgAndFormContextEnv;
    }

    public void setGcConversionOrgAndFormContextEnv(GcConversionOrgAndFormContextEnv gcConversionOrgAndFormContextEnv) {
        this.gcConversionOrgAndFormContextEnv = gcConversionOrgAndFormContextEnv;
    }

    public List<GcConversionIndexRateInfo> getIndexRateInfos() {
        return this.indexRateInfos;
    }

    public void setIndexRateInfos(List<GcConversionIndexRateInfo> indexRateInfos) {
        this.indexRateInfos = indexRateInfos;
    }

    public IDataTable getPriorBeforeQueryDataTable() {
        return this.priorBeforeQueryDataTable;
    }

    public void setPriorBeforeQueryDataTable(IDataTable priorBeforeQueryDataTable) {
        this.priorBeforeQueryDataTable = priorBeforeQueryDataTable;
    }

    public IDataTable getPriorAfterQueryDataTable() {
        return this.priorAfterQueryDataTable;
    }

    public void setPriorAfterQueryDataTable(IDataTable priorAfterQueryDataTable) {
        this.priorAfterQueryDataTable = priorAfterQueryDataTable;
    }

    public IDataTable getBeforeDataTable() {
        return this.beforeDataTable;
    }

    public void setBeforeDataTable(IDataTable beforeDataTable) {
        this.beforeDataTable = beforeDataTable;
    }

    public IDataTable getAfterDataTable() {
        return this.afterDataTable;
    }

    public void setAfterDataTable(IDataTable afterDataTable) {
        this.afterDataTable = afterDataTable;
    }

    public Map<String, BigDecimal> getRateInfos() {
        return this.rateInfos;
    }

    public void setRateInfos(Map<String, BigDecimal> rateInfos) {
        this.rateInfos = rateInfos;
    }
}

