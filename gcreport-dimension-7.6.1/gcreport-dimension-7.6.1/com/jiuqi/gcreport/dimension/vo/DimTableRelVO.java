/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.vo;

import java.io.Serializable;

public class DimTableRelVO
implements Serializable,
Cloneable {
    private String id;
    private String dimensionId;
    private String influenceTableId;
    private String sysCode;
    private String sysTitle;
    private String effectScope;
    private String effectScopeTitle;
    private String effectTableName;
    private String effectTableTitle;
    private Integer state;
    private String publishInfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDimensionId() {
        return this.dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
    }

    public String getInfluenceTableId() {
        return this.influenceTableId;
    }

    public void setInfluenceTableId(String influenceTableId) {
        this.influenceTableId = influenceTableId;
    }

    public String getSysCode() {
        return this.sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getEffectScope() {
        return this.effectScope;
    }

    public void setEffectScope(String effectScope) {
        this.effectScope = effectScope;
    }

    public String getEffectScopeTitle() {
        return this.effectScopeTitle;
    }

    public void setEffectScopeTitle(String effectScopeTitle) {
        this.effectScopeTitle = effectScopeTitle;
    }

    public String getEffectTableName() {
        return this.effectTableName;
    }

    public void setEffectTableName(String effectTableName) {
        this.effectTableName = effectTableName;
    }

    public String getEffectTableTitle() {
        return this.effectTableTitle;
    }

    public void setEffectTableTitle(String effectTableTitle) {
        this.effectTableTitle = effectTableTitle;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPublishInfo() {
        return this.publishInfo;
    }

    public void setPublishInfo(String publishInfo) {
        this.publishInfo = publishInfo;
    }

    public String getSysTitle() {
        return this.sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle;
    }

    public DimTableRelVO clone() {
        try {
            DimTableRelVO clone = (DimTableRelVO)super.clone();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

