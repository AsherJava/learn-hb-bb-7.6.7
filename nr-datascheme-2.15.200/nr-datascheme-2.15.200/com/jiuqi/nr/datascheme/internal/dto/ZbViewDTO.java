/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.dto;

import java.util.Map;

public class ZbViewDTO {
    private String key;
    private String title;
    private String code;
    private String tableCode;
    private String zbType;
    private String dataFieldType;
    private Integer precision;
    private Integer decimal;
    private String nullable;
    private String defaultValue;
    private String desc;
    private String refEntityId;
    private String refEntityTitle;
    private String dataFieldGatherType;
    private String measureType;
    private String measureUnit;
    private Map<String, String> extProp;
    private String formula;
    private String formulaDesc;

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

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getZbType() {
        return this.zbType;
    }

    public void setZbType(String zbType) {
        this.zbType = zbType;
    }

    public String getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(String dataFieldType) {
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

    public String getNullable() {
        return this.nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRefEntityId() {
        return this.refEntityId;
    }

    public void setRefEntityId(String refEntityId) {
        this.refEntityId = refEntityId;
    }

    public String getRefEntityTitle() {
        return this.refEntityTitle;
    }

    public void setRefEntityTitle(String refEntityTitle) {
        this.refEntityTitle = refEntityTitle;
    }

    public String getDataFieldGatherType() {
        return this.dataFieldGatherType;
    }

    public void setDataFieldGatherType(String dataFieldGatherType) {
        this.dataFieldGatherType = dataFieldGatherType;
    }

    public String getMeasureType() {
        return this.measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Map<String, String> getExtProp() {
        return this.extProp;
    }

    public void setExtProp(Map<String, String> extProp) {
        this.extProp = extProp;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormulaDesc() {
        return this.formulaDesc;
    }

    public void setFormulaDesc(String formulaDesc) {
        this.formulaDesc = formulaDesc;
    }
}

