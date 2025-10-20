/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import java.util.Date;

@Deprecated
public class DesignFieldDefineImpl
implements DesignFieldDefine {
    private static final long serialVersionUID = 352081403657864448L;
    private String code;
    private String description;
    private FieldType type;
    private int size;
    private boolean nullable = true;
    private boolean fixedSize;
    private boolean globalUnique;
    private boolean temporary;
    private boolean isMoneyMeasure;
    private boolean needSynchronize;
    private int fractionDigits;
    private FieldValueType fieldValueType;
    private FieldGatherType gatherType;
    private boolean allowUndefinedCode;
    private boolean allowMultipleSelect;
    private boolean allowNotLeafNodeRefer;
    private String showFormat;
    private int maxCount = 0;
    private String defaultValue;
    private String dbDefaultValue;
    private String verification;
    private String calculation;
    private String ownerTableID;
    private String referField;
    private String tableName;
    private String fieldName;
    private Date updateTime;
    private String key;
    private String order = OrderGenerator.newOrder();
    private String version;
    private String ownerLevelAndId;
    private String title;
    private int secretLevel;
    private String measureUnitKey;
    private boolean allowMultiMap = true;
    private boolean canModifyByMap;
    private String propertyType;
    private boolean dbNullAble = true;
    private boolean useAuthority;
    private FormatProperties formatProperties;
    private String entityKey;
    private String alias;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public DesignFieldDefineImpl() {
        this.type = FieldType.FIELD_TYPE_STRING;
        this.fieldValueType = FieldValueType.FIELD_VALUE_DEFALUT;
        this.gatherType = FieldGatherType.FIELD_GATHER_NONE;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.toUpperCase();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FieldType getType() {
        return this.type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public int getTypeInt() {
        return this.type.getValue();
    }

    public void setTypeInt(int type) {
        this.type = FieldType.forValue((int)type);
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getFixedSize() {
        return this.fixedSize;
    }

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public Boolean getGlobalUnique() {
        return this.globalUnique;
    }

    public void setGlobalUnique(boolean globalUnique) {
        this.globalUnique = globalUnique;
    }

    public Boolean getTemporary() {
        return this.temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public Boolean getIsMoneyMeasure() {
        return this.isMoneyMeasure;
    }

    public void setIsMoneyMeasure(boolean isMoneyMeasure) {
        this.isMoneyMeasure = isMoneyMeasure;
    }

    public Boolean getNeedSynchronize() {
        return this.needSynchronize;
    }

    public void setNeedSynchronize(boolean needSynchronize) {
        this.needSynchronize = needSynchronize;
    }

    public Integer getFractionDigits() {
        return this.fractionDigits;
    }

    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public FieldValueType getValueType() {
        return this.fieldValueType;
    }

    public void setValueType(FieldValueType fieldValueType) {
        this.fieldValueType = fieldValueType;
    }

    public FieldGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(FieldGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Boolean getAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public void setAllowMultipleSelect(boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public Boolean getAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getVerification() {
        return this.verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getCalculation() {
        return this.calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public String getOwnerTableKey() {
        return this.ownerTableID;
    }

    public void setOwnerTableKey(String ownerTableID) {
        this.ownerTableID = ownerTableID;
    }

    public String getReferFieldKey() {
        return this.referField;
    }

    public void setReferFieldKey(String referField) {
        this.referField = referField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        if (this.fieldName == null) {
            return this.getCode();
        }
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.toUpperCase();
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public String getTitle() {
        return this.title;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean isDbNullAble() {
        return this.dbNullAble;
    }

    public void setDbNullAble(boolean dbNullAble) {
        this.dbNullAble = dbNullAble;
    }

    public Integer getMaxMultipleSelectedCount() {
        return this.maxCount;
    }

    public void setMaxMultipleSelectedCount(int count) {
        this.maxCount = count;
    }

    public Integer getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getMeasureUnit() {
        return this.measureUnitKey;
    }

    public void setMeasureUnit(String measureUnitKey) {
        this.measureUnitKey = measureUnitKey;
    }

    public Boolean getAllowMultipleMap() {
        return this.allowMultiMap;
    }

    public void setAllowMultipleMap(boolean allowMultipleMap) {
        this.allowMultiMap = allowMultipleMap;
    }

    public String getDBDefaultValue() {
        return this.dbDefaultValue;
    }

    public void setDBDefaultValue(String dbDefaultValue) {
        this.dbDefaultValue = dbDefaultValue;
    }

    public Boolean getCanModifyByMapTarget() {
        return this.canModifyByMap;
    }

    public void setCanModifyByMapTarget(boolean canModifyByTarget) {
        this.canModifyByMap = canModifyByTarget;
    }

    public String getRealFieldName() {
        if (this.fieldName == null) {
            return null;
        }
        return this.fieldName;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Boolean getUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(boolean useAuthority) {
        this.useAuthority = useAuthority;
    }
}

