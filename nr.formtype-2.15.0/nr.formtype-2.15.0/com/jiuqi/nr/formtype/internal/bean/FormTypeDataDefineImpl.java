/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.internal.bean;

import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class FormTypeDataDefineImpl
implements FormTypeDataDefine {
    private static final long serialVersionUID = 3058947768554261282L;
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

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getNameEnUS() {
        return this.nameEnUS;
    }

    @Override
    public void setNameEnUS(String nameEnUS) {
        this.nameEnUS = nameEnUS;
    }

    @Override
    public String getShortname() {
        return this.shortname;
    }

    @Override
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Override
    public UnitNature getUnitNatrue() {
        return this.unitNatrue;
    }

    @Override
    public void setUnitNatrue(UnitNature unitNatrue) {
        this.unitNatrue = unitNatrue;
    }

    @Override
    public String getIcon() {
        return this.icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getFormTypeCode() {
        return this.formTypeCode;
    }

    @Override
    public void setFormTypeCode(String formTypeCode) {
        this.formTypeCode = formTypeCode;
    }

    @Override
    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    @Override
    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

