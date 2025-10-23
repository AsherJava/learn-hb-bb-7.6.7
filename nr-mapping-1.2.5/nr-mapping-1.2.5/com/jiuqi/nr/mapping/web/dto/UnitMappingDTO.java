/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 */
package com.jiuqi.nr.mapping.web.dto;

import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.mapping.bean.UnitMapping;

public class UnitMappingDTO {
    private String key;
    private String msKey;
    private String orgCode;
    private String unitTitle;
    private String unitCode;
    private String mapping;
    private boolean isCurrPeriod;

    public UnitMappingDTO() {
    }

    public UnitMappingDTO(UnitMapping unitMapping) {
        this.key = unitMapping.getKey();
        this.msKey = unitMapping.getMsKey();
        this.unitCode = unitMapping.getUnitCode();
        this.mapping = unitMapping.getMapping();
    }

    public UnitMappingDTO(IEntityItem iEntityItem) {
        this.unitCode = iEntityItem.getEntityKeyData();
        this.unitTitle = iEntityItem.getTitle();
        this.orgCode = iEntityItem.getCode();
    }

    public String getKey() {
        return this.key;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public boolean isCurrPeriod() {
        return this.isCurrPeriod;
    }

    public void setCurrPeriod(boolean currPeriod) {
        this.isCurrPeriod = currPeriod;
    }
}

