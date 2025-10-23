/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.formtype.common.UnitNature
 *  com.jiuqi.nr.formtype.facade.FormTypeDataDefine
 *  com.jiuqi.nr.formtype.internal.bean.FormTypeDataDefineImpl
 */
package com.jiuqi.nr.param.transfer.formtype.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nr.formtype.internal.bean.FormTypeDataDefineImpl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormTypeDataDTO {
    private UUID id;
    private String code;
    private String name;
    private String nameEnUS;
    private String shortname;
    private UnitNature unitNatrue;
    private String icon;
    private String formTypeCode;
    private BigDecimal ordinal;
    private Date updateTime;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getNameEnUS() {
        return this.nameEnUS;
    }

    public void setNameEnUS(String nameEnUS) {
        this.nameEnUS = nameEnUS;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public UnitNature getUnitNatrue() {
        return this.unitNatrue;
    }

    public void setUnitNatrue(UnitNature unitNatrue) {
        this.unitNatrue = unitNatrue;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFormTypeCode() {
        return this.formTypeCode;
    }

    public void setFormTypeCode(String formTypeCode) {
        this.formTypeCode = formTypeCode;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public FormTypeDataDefine convertDefine() {
        FormTypeDataDefineImpl formTypeDataDefine = new FormTypeDataDefineImpl();
        formTypeDataDefine.setId(this.getId());
        formTypeDataDefine.setCode(this.getCode());
        formTypeDataDefine.setName(this.getName());
        formTypeDataDefine.setNameEnUS(this.getNameEnUS());
        formTypeDataDefine.setShortname(this.getShortname());
        formTypeDataDefine.setUnitNatrue(this.getUnitNatrue());
        formTypeDataDefine.setIcon(this.getIcon());
        formTypeDataDefine.setFormTypeCode(this.getFormTypeCode());
        formTypeDataDefine.setOrdinal(this.getOrdinal());
        formTypeDataDefine.setUpdateTime(this.getUpdateTime());
        return formTypeDataDefine;
    }
}

