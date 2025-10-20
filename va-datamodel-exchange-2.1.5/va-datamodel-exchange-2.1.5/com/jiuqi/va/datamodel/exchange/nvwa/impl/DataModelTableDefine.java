/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelType$BizType
 */
package com.jiuqi.va.datamodel.exchange.nvwa.impl;

import com.jiuqi.va.datamodel.exchange.nvwa.base.TableType;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelFieldDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelGroup;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelIndexDefine;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataModelTableDefine {
    private String key;
    private String code;
    private String title;
    private String description;
    private String tableName;
    private Integer kind = 2;
    private DataModelGroup ownerGroupID;
    private String bizKeyFieldsID;
    private String[] bizKeyFields;
    private Boolean supportDatedVersion;
    private String indexKeysStr;
    private String entityMasterKeys;
    private String tableType = "OTHER_TABLE";
    private String dataAreaSetting;
    private Boolean hugeRecord;
    private Integer gatherType;
    private String periodFieldID;
    private Boolean needSynchronize;
    private String dictTreeStruct;
    private Date updateTime;
    private String dimensionName;
    private String dimensionList;
    private String order;
    private String version;
    private String ownerLevelAndId;
    private String titleAbbreviation;
    private Boolean isAuto = false;
    private int dictType = 0;
    private Date createTime;
    private String option;
    private String owner;
    private String keys;
    private List<DataModelFieldDefine> fields;
    private List<DataModelIndexDefine> indexs;
    private TableType vaTableType;
    private DataModelDO vdata;

    public DataModelTableDefine(DataModelDO vdata, String[] bizKeyFields) {
        this.vdata = vdata;
        this.fields = new ArrayList<DataModelFieldDefine>();
        this.indexs = new ArrayList<DataModelIndexDefine>();
        this.setTableName(vdata.getName().trim());
        this.setTitle(vdata.getTitle());
        this.setKey(UUID.randomUUID().toString());
        this.setKind(vdata.getBiztype() == DataModelType.BizType.BASEDATA ? 2 : 0);
        this.setTableType("OTHER_TABLE");
        this.setBizKeyFields(bizKeyFields);
        this.setSupportDatedVersion(false);
        this.setVaTableType(TableType.formatTableType(vdata.getBiztype(), this.getTableName()));
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTableName() {
        return this.tableName;
    }

    public DataModelGroup getOwnerGroupID() {
        return this.ownerGroupID;
    }

    public Boolean isSupportDatedVersion() {
        return this.supportDatedVersion;
    }

    public String getBizKeyFieldsID() {
        return this.bizKeyFieldsID;
    }

    public String getIndexKeysStr() {
        return this.indexKeysStr;
    }

    public String getEntityMasterKeys() {
        return this.entityMasterKeys;
    }

    public List<DataModelFieldDefine> getFields() {
        return this.fields;
    }

    public List<DataModelIndexDefine> getIndexs() {
        return this.indexs;
    }

    public String getTableType() {
        return this.tableType;
    }

    public Integer getKind() {
        return this.kind;
    }

    public String[] getBizKeyFields() {
        if (this.bizKeyFields == null) {
            this.bizKeyFields = new String[0];
        }
        return this.bizKeyFields;
    }

    public DataModelDO getVdata() {
        return this.vdata;
    }

    private void setBizKeyFields(String[] bizKeyFields) {
        this.bizKeyFields = bizKeyFields;
    }

    public DataModelTableDefine setKind(Integer kind) {
        this.kind = kind;
        return this;
    }

    public DataModelTableDefine setKey(String key) {
        this.key = key;
        return this;
    }

    public DataModelTableDefine setTitle(String title) {
        this.title = title;
        return this;
    }

    public DataModelTableDefine setDescription(String description) {
        this.description = description;
        return this;
    }

    public DataModelTableDefine setTableName(String tableName) {
        this.code = tableName;
        this.tableName = tableName;
        return this;
    }

    public DataModelTableDefine setOwnerGroupID(DataModelGroup groupkey) {
        this.ownerGroupID = groupkey;
        return this;
    }

    public DataModelTableDefine setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public DataModelTableDefine setIndexKeysStr(String indexKeysStr) {
        this.indexKeysStr = indexKeysStr;
        return this;
    }

    public DataModelTableDefine setBizKeyFieldsID(String bizKeyFieldsID) {
        this.bizKeyFieldsID = bizKeyFieldsID;
        return this;
    }

    public DataModelTableDefine setEntityMasterKeys(String entityMasterKeys) {
        this.entityMasterKeys = entityMasterKeys;
        return this;
    }

    public DataModelTableDefine setSupportDatedVersion(Boolean supportDatedVersion) {
        this.supportDatedVersion = supportDatedVersion;
        return this;
    }

    private void setFields(List<DataModelFieldDefine> fields) {
        this.fields = fields;
    }

    private void setIndexs(List<DataModelIndexDefine> indexs) {
        this.indexs = indexs;
    }

    public void addField(DataModelFieldDefine field) {
        if (this.getFields() == null) {
            this.setFields(new ArrayList<DataModelFieldDefine>());
        }
        if (field != null) {
            this.fields.add(field);
        }
    }

    public void addPkField(DataModelFieldDefine field) {
        if (field != null) {
            field.PKEY();
            this.addField(field);
            this.addIndex(new DataModelIndexDefine("PK1_" + this.getTableName()).Columns(field.getFieldName()).setIndexType(3));
        }
    }

    public void addIndex(DataModelIndexDefine index) {
        if (this.getIndexs() == null) {
            this.setIndexs(new ArrayList<DataModelIndexDefine>());
        }
        if (index != null && !this.indexs.contains(index)) {
            this.indexs.add(index);
        }
    }

    public TableType getVaTableType() {
        return this.vaTableType;
    }

    public void setVaTableType(TableType vaTableType) {
        this.vaTableType = vaTableType;
        this.setOwnerGroupID(new DataModelGroup(vaTableType));
    }

    public String getDataAreaSetting() {
        return this.dataAreaSetting;
    }

    public void setDataAreaSetting(String dataAreaSetting) {
        this.dataAreaSetting = dataAreaSetting;
    }

    public Boolean isHugeRecord() {
        return this.hugeRecord;
    }

    public void setHugeRecord(Boolean hugeRecord) {
        this.hugeRecord = hugeRecord;
    }

    public Integer getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(Integer gatherType) {
        this.gatherType = gatherType;
    }

    public String getPeriodFieldID() {
        return this.periodFieldID;
    }

    public void setPeriodFieldID(String periodFieldID) {
        this.periodFieldID = periodFieldID;
    }

    public Boolean isNeedSynchronize() {
        return this.needSynchronize;
    }

    public void setNeedSynchronize(Boolean needSynchronize) {
        this.needSynchronize = needSynchronize;
    }

    public String getDictTreeStruct() {
        return this.dictTreeStruct;
    }

    public void setDictTreeStruct(String dictTreeStruct) {
        this.dictTreeStruct = dictTreeStruct;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getDimensionList() {
        return this.dimensionList;
    }

    public void setDimensionList(String dimensionList) {
        this.dimensionList = dimensionList;
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

    public String getTitleAbbreviation() {
        return this.titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }

    public Boolean getIsAuto() {
        return this.isAuto;
    }

    public void setIsAuto(Boolean isAuto) {
        this.isAuto = isAuto;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getKeys() {
        return this.keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setVdata(DataModelDO vdata) {
        this.vdata = vdata;
    }

    public int getDictType() {
        return this.dictType;
    }

    public void setDictType(int dictType) {
        this.dictType = dictType;
    }
}

