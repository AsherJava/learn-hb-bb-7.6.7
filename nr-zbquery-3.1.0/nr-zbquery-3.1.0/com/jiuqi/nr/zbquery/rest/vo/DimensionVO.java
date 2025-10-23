/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.zbquery.rest.vo;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.zbquery.model.QueryDimensionType;
import com.jiuqi.nr.zbquery.model.QueryObject;
import java.util.List;

public class DimensionVO {
    private String fullName;
    private String title;
    private String value;
    private String entityId;
    private QueryDimensionType queryDimensionType;
    private PeriodType periodType;
    private boolean enableVersion;
    private String schemeName;
    private String minValue;
    private String maxValue;
    private boolean masterEntity = false;
    private String dimensionName;
    private String messageAlias;
    private int minFiscalMonth = -1;
    private int maxFiscalMonth = -1;
    private boolean orgDimension;
    private String id;
    private List<QueryObject> children;
    private boolean virtualDimension;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public QueryDimensionType getQueryDimensionType() {
        return this.queryDimensionType;
    }

    public void setQueryDimensionType(QueryDimensionType queryDimensionType) {
        this.queryDimensionType = queryDimensionType;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public boolean isEnableVersion() {
        return this.enableVersion;
    }

    public void setEnableVersion(boolean enableVersion) {
        this.enableVersion = enableVersion;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getMinValue() {
        return this.minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public boolean isMasterEntity() {
        return this.masterEntity;
    }

    public void setMasterEntity(boolean masterEntity) {
        this.masterEntity = masterEntity;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
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

    public boolean isOrgDimension() {
        return this.orgDimension;
    }

    public void setOrgDimension(boolean orgDimension) {
        this.orgDimension = orgDimension;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<QueryObject> getChildren() {
        return this.children;
    }

    public void setChildren(List<QueryObject> children) {
        this.children = children;
    }

    public boolean isVirtualDimension() {
        return this.virtualDimension;
    }

    public void setVirtualDimension(boolean virtualDimension) {
        this.virtualDimension = virtualDimension;
    }
}

