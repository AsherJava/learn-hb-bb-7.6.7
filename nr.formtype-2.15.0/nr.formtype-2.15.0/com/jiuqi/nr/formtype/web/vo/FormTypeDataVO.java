/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonGetter
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonSetter
 *  com.jiuqi.nvwa.definition.common.UUIDUtils
 */
package com.jiuqi.nr.formtype.web.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jiuqi.nr.formtype.common.UnitNature;
import com.jiuqi.nr.formtype.facade.FormTypeDataDefine;
import com.jiuqi.nvwa.definition.common.UUIDUtils;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.util.StringUtils;

public class FormTypeDataVO {
    private String key;
    private String code;
    private String name;
    private String nameEnUs;
    private String shortname;
    private UnitNature unitNature;
    private String icon;
    private String showIcon;
    private String formTypeCode;
    private BigDecimal ordinal;

    public FormTypeDataVO() {
    }

    public FormTypeDataVO(FormTypeDataDefine define) {
        this.key = define.getId().toString();
        this.code = define.getCode();
        this.name = define.getName();
        this.nameEnUs = define.getNameEnUS();
        this.shortname = define.getShortname();
        this.unitNature = define.getUnitNatrue();
        this.icon = define.getIcon();
        this.formTypeCode = define.getFormTypeCode();
        this.ordinal = define.getOrdinal();
    }

    public void toDefine(FormTypeDataDefine define) {
        if (StringUtils.hasText(this.key) && UUIDUtils.isUUID((String)this.key)) {
            define.setId(UUID.fromString(this.key));
        } else {
            define.setId(UUID.randomUUID());
        }
        define.setCode(this.code);
        define.setName(this.name);
        define.setNameEnUS(this.nameEnUs);
        define.setShortname(this.shortname);
        define.setUnitNatrue(this.unitNature);
        define.setFormTypeCode(this.formTypeCode);
        define.setIcon(this.icon);
        define.setOrdinal(define.getOrdinal());
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getNameEnUs() {
        return this.nameEnUs;
    }

    public void setNameEnUs(String nameEnUs) {
        this.nameEnUs = nameEnUs;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @JsonIgnore
    public UnitNature getUnitNature() {
        return this.unitNature;
    }

    @JsonIgnore
    public void setUnitNature(UnitNature unitNature) {
        this.unitNature = unitNature;
    }

    @JsonGetter(value="unitNature")
    public int getUnitNatureValue() {
        return null == this.unitNature ? 0 : this.unitNature.getValue();
    }

    @JsonSetter(value="unitNature")
    public void setUnitNatureValue(int unitNature) {
        this.unitNature = UnitNature.valueOf(unitNature);
    }

    public String getUnitNatureTitle() {
        return null == this.unitNature ? UnitNature.valueOf(0).getTitle() : this.unitNature.getTitle();
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

    public String getShowIcon() {
        return this.showIcon;
    }

    public void setShowIcon(String showIcon) {
        this.showIcon = showIcon;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

