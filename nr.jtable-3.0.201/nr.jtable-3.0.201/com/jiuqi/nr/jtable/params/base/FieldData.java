/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.jtable.util.LinkTypeUtil;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="FieldData", description="\u6307\u6807\u5c5e\u6027")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FieldData {
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey")
    private String fieldKey;
    @ApiModelProperty(value="\u6307\u6807code", name="fieldCode")
    private String fieldCode;
    @ApiModelProperty(value="\u6307\u6807\u540d\u79f0", name="fieldName")
    private String fieldName;
    @ApiModelProperty(value="\u6307\u6807\u6807\u9898", name="fieldTitle")
    private String fieldTitle;
    @ApiModelProperty(value="\u6307\u6807\u63cf\u8ff0", name="fieldDescription")
    private String fieldDescription;
    @ApiModelProperty(value="\u6307\u6807\u7c7b\u578b", name="fieldType")
    private int fieldType;
    @ApiModelProperty(value="\u6307\u6807\u7cbe\u5ea6\u6216\u8005\u957f\u5ea6", name="fieldSize")
    private int fieldSize;
    @ApiModelProperty(value="\u6307\u6807\u6c47\u603b\u7c7b\u578b", name="fieldGather")
    private int fieldGather;
    @ApiModelProperty(value="\u6307\u6807\u5173\u8054\u8fde\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u6307\u6807\u5173\u8054\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u6307\u6807\u5173\u8054\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u6307\u6807\u5173\u8054\u62a5\u8868\u6807\u9898", name="formTitle")
    private String formTitle;
    @ApiModelProperty(value="\u6307\u6807\u5168\u8def\u5f84", name="fullPath")
    private Boolean fullPath = false;
    @ApiModelProperty(value="\u6307\u6807\u5c0f\u6570\u90e8\u4f4d\u957f\u5ea6", name="fractionDigits")
    private int fractionDigits;
    @ApiModelProperty(value="\u6307\u6807\u6240\u5c5e\u5b58\u50a8\u8868key", name="ownerTableKey")
    private String ownerTableKey;
    @ApiModelProperty(value="\u6307\u6807\u503c\u7c7b\u578b", name="fieldValueType")
    private int fieldValueType;
    @ApiModelProperty(value="\u6307\u6807\u6240\u5c5e\u5b58\u50a8\u8868\u540d\u79f0", name="tableName")
    private String tableName;
    @ApiModelProperty(value="\u6307\u6807\u662f\u5426\u4e3a\u7a7a", name="nullable")
    private boolean nullable;
    @ApiModelProperty(value="\u6307\u6807\u9ed8\u8ba4\u503c", name="defaultValue")
    private String defaultValue;
    @ApiModelProperty(value="\u6307\u6807\u9ed8\u8ba4\u91cf\u7eb2", name="measureUnit")
    private String measureUnit;
    @ApiModelProperty(value="\u5173\u8054\u5b9e\u4f53ID", name="entityKey")
    private String entityKey;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public int getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldSize() {
        return this.fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public int getFieldGather() {
        return this.fieldGather;
    }

    public void setFieldGather(int fieldGather) {
        this.fieldGather = fieldGather;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public Boolean getFullPath() {
        return this.fullPath;
    }

    public void setFullPath(Boolean fullPath) {
        this.fullPath = fullPath;
    }

    public int getFractionDigits() {
        return this.fractionDigits;
    }

    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public String getOwnerTableKey() {
        return this.ownerTableKey;
    }

    public void setOwnerTableKey(String ownerTableKey) {
        this.ownerTableKey = ownerTableKey;
    }

    public int getFieldValueType() {
        return this.fieldValueType;
    }

    public void setFieldValueType(int fieldValueType) {
        this.fieldValueType = fieldValueType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getFieldDescription() {
        return this.fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public void init(FieldDefine fieldDefine) {
        this.fieldKey = fieldDefine.getKey();
        this.fieldTitle = fieldDefine.getTitle();
        this.fieldCode = fieldDefine.getCode();
        this.fieldDescription = fieldDefine.getDescription();
        this.fieldType = LinkTypeUtil.getType(fieldDefine.getType()).getValue();
        this.fieldSize = fieldDefine.getSize();
        this.fieldGather = fieldDefine.getGatherType().getValue();
        this.fractionDigits = fieldDefine.getFractionDigits();
        this.ownerTableKey = fieldDefine.getOwnerTableKey();
        this.fieldValueType = fieldDefine.getValueType().getValue();
        this.nullable = fieldDefine.getNullable();
        this.defaultValue = fieldDefine.getDefaultValue();
        this.measureUnit = fieldDefine.getMeasureUnit();
        this.entityKey = fieldDefine.getEntityKey();
    }

    public void init(ColumnModelDefine columnModelDefine) {
        this.fieldKey = columnModelDefine.getID();
        this.fieldName = columnModelDefine.getName();
        this.fieldTitle = columnModelDefine.getTitle();
        this.fieldCode = columnModelDefine.getCode();
        this.fieldDescription = columnModelDefine.getDesc();
        this.fieldType = LinkTypeUtil.getType(columnModelDefine.getColumnType()).getValue();
        this.fieldSize = columnModelDefine.getPrecision();
        this.fieldGather = null != columnModelDefine.getAggrType() ? columnModelDefine.getAggrType().getValue() : AggrType.NONE.getValue();
        this.fractionDigits = columnModelDefine.getDecimal();
        this.ownerTableKey = columnModelDefine.getTableID();
        this.fieldValueType = null != columnModelDefine.getApplyType() ? columnModelDefine.getApplyType().getValue() : ApplyType.NONE.getValue();
        this.nullable = columnModelDefine.isNullAble();
        this.defaultValue = columnModelDefine.getDefaultValue();
        this.measureUnit = columnModelDefine.getMeasureUnit();
    }

    public void init(FieldDefine fieldDefine, DataRegionDefine regionDefine, DataLinkDefine linkDefine) {
        this.init(fieldDefine);
        this.dataLinkKey = linkDefine.getKey();
        this.regionKey = regionDefine.getKey();
    }
}

