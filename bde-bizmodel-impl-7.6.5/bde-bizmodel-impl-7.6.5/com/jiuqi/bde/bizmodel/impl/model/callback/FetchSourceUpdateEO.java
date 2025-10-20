/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.model.callback;

import java.math.BigDecimal;

public class FetchSourceUpdateEO {
    public static final String TABLENAME = "BDE_FETCHSOURCE";
    private String code;
    private String name;
    private String bizModelCode;
    private String fetchTypes;
    private String dimensions;
    private Integer stopFlag;
    private BigDecimal ordinal;
    private String baseDataDefine;
    private String fixedCondition;
    private String customCondition;

    public String getBaseDataDefine() {
        return this.baseDataDefine;
    }

    public void setBaseDataDefine(String baseDataDefine) {
        this.baseDataDefine = baseDataDefine;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBizModelCode() {
        return this.bizModelCode;
    }

    public void setBizModelCode(String bizModelCode) {
        this.bizModelCode = bizModelCode;
    }

    public String getFetchTypes() {
        return this.fetchTypes;
    }

    public void setFetchTypes(String fetchTypes) {
        this.fetchTypes = fetchTypes;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String getFixedCondition() {
        return this.fixedCondition;
    }

    public void setFixedCondition(String fixedCondition) {
        this.fixedCondition = fixedCondition;
    }

    public String getCustomCondition() {
        return this.customCondition;
    }

    public void setCustomCondition(String customCondition) {
        this.customCondition = customCondition;
    }

    public String toString() {
        return "FetchSourceUpdateEO{code='" + this.code + '\'' + ", name='" + this.name + '\'' + ", bizModelCode='" + this.bizModelCode + '\'' + ", fetchTypes='" + this.fetchTypes + '\'' + ", dimensions='" + this.dimensions + '\'' + ", stopFlag=" + this.stopFlag + ", ordinal=" + this.ordinal + ", baseDataDefine='" + this.baseDataDefine + '\'' + ", fixedCondition='" + this.fixedCondition + '\'' + ", customCondition='" + this.customCondition + '\'' + '}';
    }
}

