/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.field.dto;

import com.jiuqi.nr.task.form.dto.DataCore;
import java.util.StringJoiner;

public class DataFieldDTO
extends DataCore {
    private String code;
    private String dataTableKey;
    private String dataSchemeKey;
    private Integer dataFieldKind = 0;
    private Integer dataFieldType = 0;
    private Integer precision = 0;
    private Integer decimal = 0;
    private String refDataEntityKey;
    private String refDataEntityTitle;
    private Integer dataFieldGatherType = 0;
    private String desc;
    private String defaultValue;
    protected Boolean nullable = false;
    protected Boolean allowUndefinedCode = false;
    private Boolean allowMultipleSelect = false;
    private String dataMaskCode;

    public String getRefDataEntityTitle() {
        return this.refDataEntityTitle;
    }

    public void setRefDataEntityTitle(String refDataEntityTitle) {
        this.refDataEntityTitle = refDataEntityTitle;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(Integer dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public Integer getPrecision() {
        return this.precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    public Integer getDecimal() {
        return this.decimal;
    }

    public void setDecimal(Integer decimal) {
        this.decimal = decimal;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public Integer getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(Integer dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public Integer getDataFieldKind() {
        return this.dataFieldKind;
    }

    public void setDataFieldKind(Integer dataFieldKind) {
        this.dataFieldKind = dataFieldKind;
    }

    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(Boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public String getDataMaskCode() {
        return this.dataMaskCode;
    }

    public void setDataMaskCode(String dataMaskCode) {
        this.dataMaskCode = dataMaskCode;
    }

    public String toString() {
        return new StringJoiner(", ", DataFieldDTO.class.getSimpleName() + "[", "]").add("title='" + this.getTitle() + "'").add("code='" + this.code + "'").add("dataTableKey='" + this.dataTableKey + "'").add("dataSchemeKey='" + this.dataSchemeKey + "'").add("dataFieldKind=" + this.dataFieldKind).add("dataFieldType=" + this.dataFieldType).add("precision=" + this.precision).add("decimal=" + this.decimal).add("refDataEntityKey='" + this.refDataEntityKey + "'").add("refDataEntityTitle='" + this.refDataEntityTitle + "'").add("dataFieldGatherType=" + this.dataFieldGatherType).add("desc='" + this.desc + "'").add("defaultValue='" + this.defaultValue + "'").add("nullable=" + this.nullable).add("allowUndefinedCode=" + this.allowUndefinedCode).add("allowMultipleSelect=" + this.allowMultipleSelect).add("dataMaskCode='" + this.dataMaskCode + "'").toString();
    }
}

