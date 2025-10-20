/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.dimension.internal.entity;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

public class DimTableRelEO
extends DefaultTableEntity {
    private String dimensionId;
    private String sysCode;
    private String sysTitle;
    private String serverCode;
    private String effectScope;
    private String effectScopeTitle;
    private String effectTableName;
    private String effectTableTitle;
    private Integer state;
    @Deprecated
    private String influenceTableId;

    public String getDimensionId() {
        return this.dimensionId;
    }

    public void setDimensionId(String dimensionId) {
        this.dimensionId = dimensionId;
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

    public String getEffectTableName() {
        return this.effectTableName;
    }

    public void setEffectTableName(String effectTableName) {
        this.effectTableName = effectTableName;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEffectScopeTitle() {
        return this.effectScopeTitle;
    }

    public void setEffectScopeTitle(String effectScopeTitle) {
        this.effectScopeTitle = effectScopeTitle;
    }

    public String getEffectTableTitle() {
        return this.effectTableTitle;
    }

    public void setEffectTableTitle(String effectTableTitle) {
        this.effectTableTitle = effectTableTitle;
    }

    public String getSysTitle() {
        return this.sysTitle;
    }

    public void setSysTitle(String sysTitle) {
        this.sysTitle = sysTitle;
    }

    public String getInfluenceTableId() {
        return this.influenceTableId;
    }

    public void setInfluenceTableId(String influenceTableId) {
        this.influenceTableId = influenceTableId;
    }

    public String getServerCode() {
        return this.serverCode;
    }

    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
    }
}

