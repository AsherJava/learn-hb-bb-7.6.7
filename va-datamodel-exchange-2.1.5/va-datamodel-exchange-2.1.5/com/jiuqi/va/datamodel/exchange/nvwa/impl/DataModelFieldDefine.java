/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.impl;

import java.util.Date;
import java.util.UUID;

public class DataModelFieldDefine {
    private String key;
    private String code;
    private String description;
    private DbType dbType;
    private Integer type;
    private Integer size;
    private Integer fractionDigits;
    private Boolean nullable;
    private String defaultValue;
    private String ownerTableID;
    private String refTabField;
    private String tableName;
    private String fieldName;
    private String order;
    private String title;
    private Boolean multival;
    private String showFormat;
    private String measureUnit;
    private Boolean fixedSize;
    private Boolean globalUnique;
    private Boolean isMoneyMeasure;
    private Integer fieldValueType;
    private Integer gatherType;
    private Boolean allowUndefinedCode;
    private Integer maxCount;
    private Boolean allowNotLeafNodeRefer;
    private Boolean allowMultiMap;
    private Boolean canModifyByMap;
    private String propertyType;
    private Integer secretLevel;
    private String measureUnitKey;
    private Boolean needSynchronize;
    private Boolean temporary;
    private String verification;
    private Boolean useAuthority;
    private String ownerLevelAndId;
    private Date updateTime;
    private String version;
    private String calculation;
    private String catagory;
    private String filter;
    private Integer aggrType;
    private Integer applyType;
    private Integer kind;
    private Boolean pkey;

    public DataModelFieldDefine(String fieldName, String fieldTitle) {
        this.setFieldName(fieldName);
        this.setTitle(fieldTitle);
        this.setNullable(true);
        this.setKey(UUID.randomUUID().toString());
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public DbType getDbType() {
        return this.dbType;
    }

    public Integer getType() {
        return this.type;
    }

    public Integer getSize() {
        return this.size;
    }

    public Boolean isNullable() {
        return this.nullable;
    }

    public Integer getFractionDigits() {
        return this.fractionDigits;
    }

    public Integer getFieldValueType() {
        return this.fieldValueType;
    }

    public Integer getMaxCount() {
        return this.maxCount;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public String getOwnerTableID() {
        return this.ownerTableID;
    }

    public String getRefTabField() {
        return this.refTabField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getKey() {
        return this.key;
    }

    public String getOrder() {
        return this.order;
    }

    public String getVersion() {
        return this.version;
    }

    public String getTitle() {
        return this.title;
    }

    public DataModelFieldDefine Description(String description) {
        this.description = description;
        return this;
    }

    private DataModelFieldDefine setDbType(DbType dbType) {
        this.dbType = dbType;
        return this;
    }

    private DataModelFieldDefine setType(Integer type) {
        this.type = type;
        return this;
    }

    private DataModelFieldDefine setSize(Integer size) {
        this.size = size;
        return this;
    }

    private DataModelFieldDefine setFractionDigits(Integer fractionDigits) {
        this.fractionDigits = fractionDigits;
        return this;
    }

    public DataModelFieldDefine setNullable(Boolean nullable) {
        this.nullable = nullable;
        return this;
    }

    public DataModelFieldDefine setFieldValueType(Integer fieldValueType) {
        this.fieldValueType = fieldValueType;
        return this;
    }

    public DataModelFieldDefine setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    private DataModelFieldDefine setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public DataModelFieldDefine setOwnerTableID(String ownerTableID) {
        this.ownerTableID = ownerTableID;
        return this;
    }

    public DataModelFieldDefine setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    private DataModelFieldDefine setRefTabField(String refTabField) {
        this.refTabField = refTabField;
        return this;
    }

    public DataModelFieldDefine setFieldName(String fieldName) {
        this.fieldName = fieldName;
        this.code = fieldName;
        return this;
    }

    public DataModelFieldDefine setKey(String key) {
        this.key = key;
        return this;
    }

    public DataModelFieldDefine setOrder(String order) {
        this.order = order;
        return this;
    }

    public DataModelFieldDefine setVersion(String version) {
        this.version = version;
        return this;
    }

    public DataModelFieldDefine setTitle(String title) {
        this.title = title;
        return this;
    }

    public void setPkey(Boolean pkey) {
        this.pkey = pkey;
    }

    public Boolean isPkey() {
        return this.pkey;
    }

    public DataModelFieldDefine MAPPING(String tablefield, int mappingType) {
        if (0 == mappingType) {
            this.BOOLEAN();
        } else if (2 != mappingType) {
            this.setRefTabField(tablefield);
        }
        return this;
    }

    public Boolean isMultival() {
        return this.multival;
    }

    public void setMultival(Boolean multival) {
        this.multival = multival;
    }

    public String getShowFormat() {
        return this.showFormat;
    }

    public void setShowFormat(String showFormat) {
        this.showFormat = showFormat;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public Boolean isFixedSize() {
        return this.fixedSize;
    }

    public void setFixedSize(Boolean fixedSize) {
        this.fixedSize = fixedSize;
    }

    public Boolean isGlobalUnique() {
        return this.globalUnique;
    }

    public void setGlobalUnique(Boolean globalUnique) {
        this.globalUnique = globalUnique;
    }

    public Boolean isMoneyMeasure() {
        return this.isMoneyMeasure;
    }

    public void setMoneyMeasure(Boolean isMoneyMeasure) {
        this.isMoneyMeasure = isMoneyMeasure;
    }

    public Integer getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(Integer gatherType) {
        this.gatherType = gatherType;
    }

    public Boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(Boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    public Boolean isAllowMultiMap() {
        return this.allowMultiMap;
    }

    public void setAllowMultiMap(Boolean allowMultiMap) {
        this.allowMultiMap = allowMultiMap;
    }

    public Boolean isCanModifyByMap() {
        return this.canModifyByMap;
    }

    public void setCanModifyByMap(Boolean canModifyByMap) {
        this.canModifyByMap = canModifyByMap;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getSecretLevel() {
        return this.secretLevel;
    }

    public void setSecretLevel(Integer secretLevel) {
        this.secretLevel = secretLevel;
    }

    public String getMeasureUnitKey() {
        return this.measureUnitKey;
    }

    public void setMeasureUnitKey(String measureUnitKey) {
        this.measureUnitKey = measureUnitKey;
    }

    public Boolean isNeedSynchronize() {
        return this.needSynchronize;
    }

    public void setNeedSynchronize(Boolean needSynchronize) {
        this.needSynchronize = needSynchronize;
    }

    public Boolean isTemporary() {
        return this.temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public String getVerification() {
        return this.verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public Boolean isUseAuthority() {
        return this.useAuthority;
    }

    public void setUseAuthority(Boolean useAuthority) {
        this.useAuthority = useAuthority;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCatagory() {
        return this.catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getAggrType() {
        return this.aggrType;
    }

    public void setAggrType(Integer aggrType) {
        this.aggrType = aggrType;
    }

    public Integer getApplyType() {
        return this.applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getKind() {
        return this.kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public String getCalculation() {
        return this.calculation;
    }

    public DataModelFieldDefine DEFAULT(String value) {
        this.setDefaultValue(value);
        return this;
    }

    public DataModelFieldDefine INTEGER() {
        return this.INTEGER(10);
    }

    public DataModelFieldDefine INTEGER(Integer size) {
        this.setDbType(DbType.Int);
        this.setType(this.getDbType().getIntValue());
        this.setSize(size == null ? 10 : size);
        return this;
    }

    public DataModelFieldDefine BOOLEAN() {
        this.setDbType(DbType.Boolean);
        this.setSize(1);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine NUMERIC(Integer size, Integer digit) {
        this.setDbType(DbType.Numeric);
        this.setSize(size);
        this.setFractionDigits(digit);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine DATE() {
        this.setDbType(DbType.Date);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine TIMESTAMP() {
        this.setDbType(DbType.DateTime);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine CLOB() {
        this.setDbType(DbType.Text);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine NVARCHAR(Integer size) {
        this.setDbType(DbType.NVarchar);
        this.setSize(size);
        this.setType(this.getDbType().getIntValue());
        return this;
    }

    public DataModelFieldDefine PKEY() {
        this.setPkey(true);
        this.setNullable(false);
        this.setFieldValueType(27);
        return this;
    }

    public static enum DbType {
        Numeric(8, 10),
        UUID(7, 6),
        Int(3, 5),
        NVarchar(2, 6),
        Text(16, 12),
        Date(5, 2),
        DateTime(6, 2),
        Boolean(4, 1);

        private Integer intValue;
        private Integer biValue;

        private DbType(Integer intValue, Integer biValue) {
            this.setIntValue(intValue);
            this.setBiValue(biValue);
        }

        private void setBiValue(Integer biValue) {
            this.biValue = biValue;
        }

        private void setIntValue(Integer intValue) {
            this.intValue = intValue;
        }

        public Integer getBiValue() {
            return this.biValue;
        }

        public Integer getIntValue() {
            return this.intValue;
        }

        public static DbType findByDM1(int val) {
            for (DbType type : DbType.values()) {
                if (type.getIntValue() != val) continue;
                return type;
            }
            return NVarchar;
        }

        public static DbType findByDM2(int val) {
            for (DbType type : DbType.values()) {
                if (type.getBiValue() != val) continue;
                return type;
            }
            return NVarchar;
        }
    }
}

