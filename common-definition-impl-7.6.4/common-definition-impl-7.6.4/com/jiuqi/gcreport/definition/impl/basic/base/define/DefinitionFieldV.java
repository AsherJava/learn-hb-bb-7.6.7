/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.base.define;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.basic.base.define.DefinitionTableV;
import com.jiuqi.gcreport.definition.impl.basic.base.util.UUIDTool;
import java.util.Date;

public class DefinitionFieldV {
    private boolean primary;
    private String entityFieldName;
    private String code;
    private String description;
    private DBColumn.DBType dbType;
    private int size;
    private boolean nullable = true;
    private boolean fixedSize;
    private boolean globalUnique;
    private boolean temporary;
    private boolean isMoneyMeasure;
    private boolean needSynchronize;
    private int fractionDigits;
    private int fieldValueType;
    private boolean allowUndefinedCode;
    private boolean allowMultipleSelect;
    private boolean allowNotLeafNodeRefer;
    private String showFormat;
    private int maxCount = 0;
    private String defaultValue;
    private String verification;
    private String calculation;
    private String ownerTableID;
    private String referField;
    private String referTable;
    private String refTabField;
    private String tableName;
    private String fieldName;
    private Date updateTime;
    private String key;
    private double order;
    private String version;
    private String ownerLevelAndId;
    private String title;
    private int secretLevel;
    private String measureUnitKey;

    public DefinitionFieldV(DefinitionTableV table, DBColumn column, String entityFieldName) {
        this.setNullable(true);
        this.setKey(UUIDTool.newUUIDString36());
        this.setEntityFieldName(entityFieldName);
        this.setCode(entityFieldName);
        this.setFieldName(entityFieldName);
        this.setTitle(column.title());
        this.setRefTabField(column.refTabField());
        this.setFieldValueType(column.fieldValueType());
        this.setDbType(column.dbType());
        this.setDescription(column.description());
        this.setTableName(table.getTableName());
        this.setOwnerTableID(table.getKey());
        this.setSize(column.dbType().getType() == 8 ? (column.dbType() == DBColumn.DBType.Long ? 17 : column.precision()) : column.length());
        this.setNullable(!column.isRequired());
        this.setFractionDigits(column.dbType() == DBColumn.DBType.Long ? 0 : column.scale());
        this.setPrimary(column.isRecid());
        if (!StringUtils.isEmpty((String)column.defaultValue())) {
            this.setDefaultValue(column.defaultValue());
        }
        this.setOrder(column.order());
    }

    public DefinitionFieldV() {
    }

    public String getRefTabField() {
        return this.refTabField;
    }

    public void setRefTabField(String refTabField) {
        this.refTabField = refTabField;
    }

    public String getEntityFieldName() {
        return this.entityFieldName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public DBColumn.DBType getDbType() {
        return this.dbType;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public boolean isFixedSize() {
        return this.fixedSize;
    }

    public boolean isGlobalUnique() {
        return this.globalUnique;
    }

    public boolean isTemporary() {
        return this.temporary;
    }

    public boolean isMoneyMeasure() {
        return this.isMoneyMeasure;
    }

    public boolean isNeedSynchronize() {
        return this.needSynchronize;
    }

    public int getFractionDigits() {
        return this.fractionDigits;
    }

    public int getFieldValueType() {
        return this.fieldValueType;
    }

    public boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public boolean isAllowMultipleSelect() {
        return this.allowMultipleSelect;
    }

    public boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getVerification() {
        return this.verification;
    }

    public String getCalculation() {
        return this.calculation;
    }

    public String getOwnerTableID() {
        return this.ownerTableID;
    }

    public String getReferField() {
        return this.referField;
    }

    public String getReferTable() {
        return this.referTable;
    }

    public void setReferTable(String referTable) {
        this.referTable = referTable;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getKey() {
        return this.key;
    }

    public double getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public String getTitle() {
        return this.title;
    }

    public int getSecretLevel() {
        return this.secretLevel;
    }

    public String getMeasureUnitKey() {
        return this.measureUnitKey;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDbType(DBColumn.DBType dbType) {
        this.dbType = dbType;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public void setFixedSize(boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public void setGlobalUnique(boolean globalUnique) {
        this.globalUnique = globalUnique;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public void setMoneyMeasure(boolean isMoneyMeasure) {
        this.isMoneyMeasure = isMoneyMeasure;
    }

    public void setNeedSynchronize(boolean needSynchronize) {
        this.needSynchronize = needSynchronize;
    }

    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    public void setFieldValueType(int fieldValueType) {
        this.fieldValueType = fieldValueType;
    }

    public void setAllowUndefinedCode(boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public void setAllowMultipleSelect(boolean allowMultipleSelect) {
        this.allowMultipleSelect = allowMultipleSelect;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public void setOwnerTableID(String ownerTableID) {
        this.ownerTableID = ownerTableID;
    }

    public void setReferField(String referField) {
        this.referField = referField;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOrder(double order) {
        this.order = order;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSecretLevel(int secretLevel) {
        this.secretLevel = secretLevel;
    }

    public void setMeasureUnitKey(String measureUnitKey) {
        this.measureUnitKey = measureUnitKey;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isPrimary() {
        return this.primary;
    }
}

