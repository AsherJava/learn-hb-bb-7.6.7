/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FieldObject {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="UserDefined")
    private boolean userDefined;
    @JsonProperty(value="Type")
    private int type;
    @JsonProperty(value="ValueType")
    private int valueType;
    @JsonProperty(value="GatherType")
    private int gatherType;
    @JsonProperty(value="Size")
    private int size;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Description")
    private String description;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="Nullable")
    private boolean nullable;
    @JsonProperty(value="FractionDigits")
    private int fractionDigits;
    @JsonProperty(value="FormatProperties")
    private FormatProperties formatProperties;
    @JsonProperty(value="DefaultValue")
    private String defaultValue;
    @JsonProperty(value="OwnerTableID")
    private String ownerTableID;
    @JsonProperty(value="ReferField")
    private String referField;
    @JsonProperty(value="ReferFieldName")
    private String referFieldName;
    @JsonProperty(value="ReferFieldCode")
    private String referFieldCode;
    @JsonProperty(value="AllowMultSelect")
    private boolean allowMultSelect;
    @JsonProperty(value="MultSelectLimit")
    private int multSelectLimit;
    @JsonProperty(value="AllowUndefinedCode")
    private boolean allowUndefinedCode;
    @JsonProperty(value="AllowNotLeafNodeRefer")
    private boolean allowNotLeafNodeRefer;
    @JsonProperty(value="Calculation")
    private String calculation;
    @JsonProperty(value="Verification")
    private String verification;
    @JsonProperty(value="DuplicateValidate")
    private boolean duplicateValidate;
    @JsonProperty(value="IsGroupField")
    private boolean isGroupField;
    @JsonProperty(value="AllowMultiMap")
    private boolean allowMultiMap;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;
    private String propertyType;
    @JsonProperty(value="UseAuthority")
    private boolean useAuthority;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="BizKeyOrder")
    private String bizKeyOrder;
    @JsonProperty(value="EntityKey")
    private String entityKey;

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getBizKeyOrder() {
        return this.bizKeyOrder;
    }

    public void setBizKeyOrder(String bizKeyOrder) {
        this.bizKeyOrder = bizKeyOrder;
    }

    public FieldObject() {
    }

    public FieldObject(FieldDefine field) {
        this.id = field.getKey();
        this.type = field.getType().getValue();
        this.valueType = field.getValueType().getValue();
        this.gatherType = field.getGatherType().getValue();
        this.size = field.getSize();
        this.title = field.getTitle();
        this.description = field.getDescription();
        this.code = field.getCode();
        this.nullable = field.getNullable();
        this.fractionDigits = field.getFractionDigits();
        this.formatProperties = field.getFormatProperties();
        this.defaultValue = field.getDefaultValue();
        this.ownerTableID = field.getOwnerTableKey();
        this.allowMultSelect = field.getAllowMultipleSelect();
        this.allowUndefinedCode = field.getAllowUndefinedCode();
        this.useAuthority = field.getUseAuthority();
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    @JsonIgnore
    public String getCalculation() {
        return this.calculation;
    }

    @JsonIgnore
    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    @JsonIgnore
    public String getID() {
        return this.id;
    }

    @JsonIgnore
    public void setID(String iD) {
        this.id = iD;
    }

    @JsonIgnore
    public boolean isIsNew() {
        return this.isNew;
    }

    @JsonIgnore
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @JsonIgnore
    public boolean isIsDirty() {
        return this.isDirty;
    }

    @JsonIgnore
    public void setIsDirty(boolean isDirty) {
        this.isDirty = isDirty;
    }

    @JsonIgnore
    public boolean isIsDeleted() {
        return this.isDeleted;
    }

    @JsonIgnore
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @JsonIgnore
    public boolean isUserDefined() {
        return this.userDefined;
    }

    @JsonIgnore
    public void setUserDefined(boolean userDefined) {
        this.userDefined = userDefined;
    }

    @JsonIgnore
    public int getType() {
        return this.type;
    }

    @JsonIgnore
    public void setType(int type) {
        this.type = type;
    }

    @JsonIgnore
    public int getValueType() {
        return this.valueType;
    }

    @JsonIgnore
    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    @JsonIgnore
    public int getGatherType() {
        return this.gatherType;
    }

    @JsonIgnore
    public void setGatherType(int gatherType) {
        this.gatherType = gatherType;
    }

    @JsonIgnore
    public int getSize() {
        return this.size;
    }

    @JsonIgnore
    public void setSize(int size) {
        this.size = size;
    }

    @JsonIgnore
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public String getDescription() {
        return this.description;
    }

    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonIgnore
    public String getCode() {
        return this.code;
    }

    @JsonIgnore
    public void setCode(String code) {
        this.code = code;
    }

    @JsonIgnore
    public boolean isNullable() {
        return this.nullable;
    }

    @JsonIgnore
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    @JsonIgnore
    public int getFractionDigits() {
        return this.fractionDigits;
    }

    @JsonIgnore
    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    @JsonIgnore
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @JsonIgnore
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @JsonIgnore
    public String getOwnerTableID() {
        return this.ownerTableID;
    }

    @JsonIgnore
    public void setOwnerTableID(String ownerTableID) {
        this.ownerTableID = ownerTableID;
    }

    @JsonIgnore
    public String getReferField() {
        return this.referField;
    }

    @JsonIgnore
    public void setReferField(String referField) {
        this.referField = referField;
    }

    @JsonIgnore
    public String getReferFieldName() {
        return this.referFieldName;
    }

    @JsonIgnore
    public void setReferFieldName(String referFieldName) {
        this.referFieldName = referFieldName;
    }

    @JsonIgnore
    public boolean isAllowMultSelect() {
        return this.allowMultSelect;
    }

    @JsonIgnore
    public void setAllowMultSelect(boolean allowMultSelect) {
        this.allowMultSelect = allowMultSelect;
    }

    @JsonIgnore
    public int getMultSelectLimit() {
        return this.multSelectLimit;
    }

    @JsonIgnore
    public void setMultSelectLimit(int multSelectLimit) {
        this.multSelectLimit = multSelectLimit;
    }

    @JsonIgnore
    public boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    @JsonIgnore
    public void setAllowUndefinedCode(boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    @JsonIgnore
    public boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    @JsonIgnore
    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    @JsonIgnore
    public String getVerification() {
        return this.verification;
    }

    @JsonIgnore
    public void setVerification(String verification) {
        this.verification = verification;
    }

    @JsonIgnore
    public boolean getDuplicateValidate() {
        return this.duplicateValidate;
    }

    @JsonIgnore
    public void setDuplicateValidate(boolean duplicateValidate) {
        this.duplicateValidate = duplicateValidate;
    }

    @JsonIgnore
    public String getReferFieldCode() {
        return this.referFieldCode;
    }

    @JsonIgnore
    public void setReferFieldCode(String referFieldCode) {
        this.referFieldCode = referFieldCode;
    }

    @JsonIgnore
    public boolean isAllowMultiMap() {
        return this.allowMultiMap;
    }

    @JsonIgnore
    public void setAllowMultiMap(boolean allowMultiMap) {
        this.allowMultiMap = allowMultiMap;
    }

    public DesignFieldDefine castToDefine(DesignFieldDefine fieldDefine) {
        fieldDefine.setKey(this.id);
        fieldDefine.setType(FieldType.forValue((int)this.type));
        fieldDefine.setValueType(FieldValueType.forValue((int)this.valueType));
        fieldDefine.setGatherType(FieldGatherType.forValue((int)this.gatherType));
        fieldDefine.setSize(this.size);
        fieldDefine.setTitle(this.title);
        fieldDefine.setDescription(this.description);
        fieldDefine.setCode(this.code);
        fieldDefine.setNullable(this.nullable);
        fieldDefine.setFractionDigits(this.fractionDigits);
        fieldDefine.setFormatProperties(this.formatProperties);
        fieldDefine.setDefaultValue(this.defaultValue);
        fieldDefine.setOwnerTableKey(this.ownerTableID);
        fieldDefine.setReferFieldKey(this.referField);
        fieldDefine.setAllowMultipleSelect(this.allowMultSelect);
        fieldDefine.setMaxMultipleSelectedCount(this.multSelectLimit);
        fieldDefine.setAllowUndefinedCode(this.allowUndefinedCode);
        fieldDefine.setAllowNotLeafNodeRefer(this.allowNotLeafNodeRefer);
        fieldDefine.setCalculation(this.calculation);
        fieldDefine.setVerification(this.verification);
        fieldDefine.setAllowMultipleMap(this.allowMultiMap);
        fieldDefine.setPropertyType(this.propertyType);
        fieldDefine.setUseAuthority(this.useAuthority);
        fieldDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        return fieldDefine;
    }

    @JsonIgnore
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @JsonIgnore
    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public boolean getUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    @JsonIgnore
    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    @JsonIgnore
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    @JsonIgnore
    public boolean getSameServeCode() {
        return this.sameServeCode;
    }

    @JsonIgnore
    public void setSameServeCode(boolean sameServeCode) {
        this.sameServeCode = sameServeCode;
    }
}

