/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.i18n.dto.DesignDataSchemeI18nDTO;
import com.jiuqi.nr.datascheme.i18n.entity.DesignDataSchemeI18nDO;

public class DataSchemeI18nVO {
    private String key;
    private String title;
    private String desc;
    private String fieldCode;
    private String fieldTitle;
    private String fieldDesc;
    private String dataSchemeKey;
    private String type;

    public DataSchemeI18nVO() {
    }

    public DataSchemeI18nVO(DesignDataSchemeI18nDTO dto) {
        this.key = dto.getKey();
        this.title = dto.getTitle();
        this.desc = dto.getDesc();
        this.fieldCode = dto.getFieldCode();
        this.fieldTitle = dto.getFieldTitle();
        this.fieldDesc = dto.getFieldDesc();
        this.dataSchemeKey = dto.getDataSchemeKey();
        this.type = dto.getType();
    }

    public DesignDataSchemeI18nDO toDO() {
        DesignDataSchemeI18nDO d = new DesignDataSchemeI18nDO();
        d.setKey(this.key);
        d.setType(this.type);
        d.setTitle(this.title);
        d.setDesc(this.desc);
        d.setDataSchemeKey(this.dataSchemeKey);
        return d;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldDesc() {
        return this.fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }
}

