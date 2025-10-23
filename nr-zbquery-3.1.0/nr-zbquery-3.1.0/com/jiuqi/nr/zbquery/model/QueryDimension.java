/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import com.jiuqi.nr.zbquery.model.QueryObjectType;

public class QueryDimension
extends QueryObject {
    private QueryDimensionType dimensionType = QueryDimensionType.MASTER;
    private PeriodType periodType = PeriodType.DEFAULT;
    private boolean specialPeriodType;
    private int minFiscalMonth = -1;
    private int maxFiscalMonth = -1;
    private boolean orgDimension;
    private boolean calibreDimension;
    private boolean virtualDimension;
    private String bizKey;
    private boolean enableVersion;
    private boolean enableAdjust;
    private boolean enableStandardCurrency;
    private boolean treeStructure;
    private String entityId;
    private int isolation = 0;

    public QueryDimension() {
        this.setType(QueryObjectType.DIMENSION);
    }

    public QueryDimensionType getDimensionType() {
        return this.dimensionType;
    }

    public void setDimensionType(QueryDimensionType dimensionType) {
        this.dimensionType = dimensionType;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public boolean isSpecialPeriodType() {
        return this.specialPeriodType || this.periodType == PeriodType.CUSTOM;
    }

    public void setSpecialPeriodType(boolean specialPeriodType) {
        this.specialPeriodType = specialPeriodType;
    }

    public boolean isOrgDimension() {
        return this.orgDimension;
    }

    public void setOrgDimension(boolean orgDimension) {
        this.orgDimension = orgDimension;
    }

    public boolean isCalibreDimension() {
        return this.calibreDimension;
    }

    public void setCalibreDimension(boolean calibreDimension) {
        this.calibreDimension = calibreDimension;
    }

    public boolean isVirtualDimension() {
        return this.virtualDimension;
    }

    public void setVirtualDimension(boolean virtualDimension) {
        this.virtualDimension = virtualDimension;
    }

    public String getBizKey() {
        return this.bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public boolean isEnableVersion() {
        return this.enableVersion;
    }

    public void setEnableVersion(boolean enableVersion) {
        this.enableVersion = enableVersion;
    }

    public boolean isEnableAdjust() {
        return this.enableAdjust;
    }

    public void setEnableAdjust(boolean enableAdjust) {
        this.enableAdjust = enableAdjust;
    }

    public boolean isTreeStructure() {
        return this.treeStructure;
    }

    public void setTreeStructure(boolean treeStructure) {
        this.treeStructure = treeStructure;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public boolean isEnableStandardCurrency() {
        return this.enableStandardCurrency;
    }

    public void setEnableStandardCurrency(boolean enableStandardCurrency) {
        this.enableStandardCurrency = enableStandardCurrency;
    }

    public int getIsolation() {
        return this.isolation;
    }

    public void setIsolation(int isolation) {
        this.isolation = isolation;
    }

    public int getMinFiscalMonth() {
        return this.minFiscalMonth;
    }

    public void setMinFiscalMonth(int minFiscalMonth) {
        this.minFiscalMonth = minFiscalMonth;
    }

    public int getMaxFiscalMonth() {
        return this.maxFiscalMonth;
    }

    public void setMaxFiscalMonth(int maxFiscalMonth) {
        this.maxFiscalMonth = maxFiscalMonth;
    }
}

