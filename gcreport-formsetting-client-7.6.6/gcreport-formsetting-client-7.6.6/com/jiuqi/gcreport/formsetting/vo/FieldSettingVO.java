/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.formsetting.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FieldSettingVO {
    private String id;
    @NotNull(message="\u5b57\u6bb5\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5b57\u6bb5\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a") String code;
    @NotNull(message="\u5b57\u6bb5\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5b57\u6bb5\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") String title;
    private DataFieldType dataFieldType;
    private Integer valueType;
    private Integer gatherType;
    private Integer size;
    private Integer fractionDigits;
    private String referField;
    private String description;
    private String defaultValue;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getFractionDigits() {
        return this.fractionDigits;
    }

    public void setFractionDigits(Integer fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public String getReferField() {
        return this.referField;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(Integer gatherType) {
        this.gatherType = gatherType;
    }

    public DataFieldType getDataFieldType() {
        return this.dataFieldType;
    }

    public void setDataFieldType(DataFieldType dataFieldType) {
        this.dataFieldType = dataFieldType;
    }

    public String toString() {
        return "FieldSettingVO{id=" + this.id + ", code='" + this.code + '\'' + ", title='" + this.title + '\'' + ", type=" + this.dataFieldType + ", valueType=" + this.valueType + ", gatherType=" + this.gatherType + ", size=" + this.size + ", fractionDigits=" + this.fractionDigits + ", referField=" + this.referField + ", description='" + this.description + '\'' + ", defaultValue='" + this.defaultValue + '\'' + '}';
    }

    public static class DataFieldType {
        private Integer value;

        public Integer getValue() {
            return this.value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}

