/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;

public class DimensionVO {
    private String key;
    private String title;
    private String code;
    private Integer precision;
    private final DataFieldKind dataFieldKind = DataFieldKind.FIELD;
    private final DataFieldType dataFieldType = DataFieldType.STRING;
    private String refDataEntityTitle;
    private String refDataEntityKey;
    private String defaultValue;
    private String allowMultipleSelect;
    private String order;

    public String getTypeTitle() {
        return String.format("%s(%d)", this.dataFieldType.getTitle(), this.precision == null ? 0 : this.precision);
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public DataFieldKind getDataFieldKind() {
        return this.dataFieldKind;
    }

    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
    }

    public String getRefDataEntityTitle() {
        return this.refDataEntityTitle;
    }

    public void setRefDataEntityTitle(String refDataEntityTitle) {
        this.refDataEntityTitle = refDataEntityTitle;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(String allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}

