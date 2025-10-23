/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.table.dto;

import com.jiuqi.nr.task.form.dto.DataCore;

public class DataTableDTO
extends DataCore {
    private String dataSchemeKey;
    private String dataGroupKey;
    private String code;
    private Integer dataTableType = 0;
    private String[] bizKeys;
    private Integer gatherType = 0;
    private Boolean repeatCode = false;
    private Boolean hasData = false;

    public Boolean getRepeatCode() {
        return this.repeatCode;
    }

    public void setRepeatCode(Boolean repeatCode) {
        this.repeatCode = repeatCode != null && repeatCode != false;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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

    public Integer getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(Integer dataTableType) {
        this.dataTableType = dataTableType == null ? 0 : dataTableType;
    }

    public String[] getBizKeys() {
        return this.bizKeys;
    }

    public void setBizKeys(String[] bizKeys) {
        this.bizKeys = bizKeys;
    }

    public Integer getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(Integer gatherType) {
        this.gatherType = gatherType == null ? 0 : gatherType;
    }

    public Boolean getHasData() {
        return this.hasData;
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData != null && hasData != false;
    }
}

