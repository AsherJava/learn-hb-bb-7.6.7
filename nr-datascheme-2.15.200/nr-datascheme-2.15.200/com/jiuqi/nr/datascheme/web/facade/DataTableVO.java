/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonGetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;

public class DataTableVO
extends BaseDataVO {
    private String dataSchemeKey;
    private String dataGroupKey;
    private DataTableType dataTableType;
    private DataTableGatherType dataTableGatherType;
    private Boolean repeatCode;
    private String owner;
    private Boolean trackHistory;
    private Boolean syncError;
    private String expression;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }

    @JsonIgnore
    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    @JsonGetter(value="dataTableType")
    public int getDataTableTypeValue() {
        return null == this.dataTableType ? DataTableType.TABLE.getValue() : this.dataTableType.getValue();
    }

    @JsonSetter(value="dataTableType")
    public void setDataTableType(int dataTableType) {
        this.dataTableType = DataTableType.valueOf((int)dataTableType);
    }

    @JsonIgnore
    public DataTableGatherType getDataTableGatherType() {
        return this.dataTableGatherType;
    }

    @JsonGetter(value="dataTableGatherType")
    public int getDataTableGatherTypeValue() {
        if (null == this.dataTableGatherType) {
            if (DataTableType.TABLE.getValue() == this.getDataTableType().getValue()) {
                return DataTableGatherType.CLASSIFY.getValue();
            }
            return DataTableGatherType.NONE.getValue();
        }
        return this.dataTableGatherType.getValue();
    }

    @JsonSetter(value="dataTableGatherType")
    public void setDataTableGatherType(int dataTableGatherType) {
        this.dataTableGatherType = DataTableGatherType.valueOf((int)dataTableGatherType);
    }

    public Boolean getRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(Boolean repeatCode) {
        this.repeatCode = repeatCode;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }

    public void setDataTableGatherType(DataTableGatherType dataTableGatherType) {
        this.dataTableGatherType = dataTableGatherType;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Boolean getTrackHistory() {
        return this.trackHistory;
    }

    public void setTrackHistory(Boolean trackHistory) {
        this.trackHistory = trackHistory;
    }

    public Boolean getSyncError() {
        return this.syncError;
    }

    public void setSyncError(Boolean syncError) {
        this.syncError = syncError;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}

