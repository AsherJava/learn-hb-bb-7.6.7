/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 */
package com.jiuqi.nr.definition.internal.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

@Deprecated
public class DesignTableDefineImpl
implements DesignTableDefine {
    private static final long serialVersionUID = 8405247848425694708L;
    private String key;
    private String code;
    private String title;
    private String description;
    private String tableName;
    private TableKind kind = TableKind.TABLE_KIND_UNDEFINED;
    private String dataAreaSetting;
    private boolean hugeRecord;
    private boolean supportDatedVersion;
    private String ownerGroupID;
    private TableGatherType gatherType = TableGatherType.TABLE_GATHER_NONE;
    private String periodFieldID;
    private String bizKeyFieldsID;
    private boolean needSynchronize = true;
    private String dictTreeStruct;
    private Date updateTime;
    private String dimensionName;
    private String dimensionList;
    private String order;
    private String version;
    private String ownerLevelAndId;
    private String indexKeysStr;
    private String entityMasterKeys;
    private String titleAbbreviation;
    private String tableType;
    private Boolean isAuto = false;
    private String gatherFields;

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

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableKind getKind() {
        return this.kind;
    }

    public void setKind(TableKind kind) {
        this.kind = kind;
    }

    public String getDataAreaSetting() {
        return this.dataAreaSetting;
    }

    public void setDataAreaSetting(String dataAreaSetting) {
        this.dataAreaSetting = dataAreaSetting;
    }

    public boolean getHugeRecord() {
        return this.hugeRecord;
    }

    public void setHugeRecord(boolean hugeRecord) {
        this.hugeRecord = hugeRecord;
    }

    public boolean getSupportDatedVersion() {
        return this.supportDatedVersion;
    }

    public void setSupportDatedVersion(boolean supportDatedVersion) {
        this.supportDatedVersion = supportDatedVersion;
    }

    public String getOwnerGroupID() {
        return this.ownerGroupID;
    }

    public void setOwnerGroupID(String ownerGroupID) {
        this.ownerGroupID = ownerGroupID;
    }

    public TableGatherType getGatherType() {
        return this.gatherType;
    }

    public void setGatherType(TableGatherType gatherType) {
        this.gatherType = gatherType;
    }

    public String getPeriodFieldID() {
        return this.periodFieldID;
    }

    public void setPeriodFieldID(String periodFieldID) {
        this.periodFieldID = periodFieldID;
    }

    public String getBizKeyFieldsStr() {
        return this.bizKeyFieldsID;
    }

    public void setBizKeyFields(String bizKeyFieldsID) {
        this.bizKeyFieldsID = bizKeyFieldsID;
    }

    public void setBizKeyFields(String[] bizKeysArray) {
        this.bizKeyFieldsID = bizKeysArray == null ? "" : Arrays.stream(bizKeysArray).collect(Collectors.joining(";"));
    }

    public String[] getBizKeyFieldsID() {
        if (StringUtils.isEmpty((String)this.bizKeyFieldsID)) {
            return null;
        }
        return this.bizKeyFieldsID.split(";");
    }

    public boolean getNeedSynchronize() {
        return this.needSynchronize;
    }

    public void setNeedSynchronize(boolean needSynchronize) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndexKeysStr() {
        return this.indexKeysStr;
    }

    @JsonIgnore
    public String[] getIndexKeys() {
        if (StringUtils.isEmpty((String)this.indexKeysStr)) {
            return new String[0];
        }
        return this.indexKeysStr.split(";");
    }

    public void setIndexKeys(String[] keysArray) {
        this.indexKeysStr = keysArray == null ? "" : Arrays.stream(keysArray).collect(Collectors.joining(";"));
    }

    public void setIndexKeys(String keysStr) {
        this.indexKeysStr = keysStr;
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (obj instanceof TableDefine) {
            return ((TableDefine)obj).getKey().equals(this.key);
        }
        return false;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String getEntityMasterKeys() {
        return this.entityMasterKeys;
    }

    public void setEntityMasterKeys(String enityMasterKeys) {
        this.entityMasterKeys = enityMasterKeys;
    }

    public String getTitleAbbreviation() {
        return this.titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
    }

    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void setGatherFields(String gatherFields) {
        this.gatherFields = gatherFields;
    }

    public void setIsAuTo(boolean isAuto) {
        this.isAuto = isAuto;
    }

    public boolean getIsAuto() {
        return this.isAuto;
    }

    public String getGatherFields() {
        return this.gatherFields;
    }

    public String getBizAndGatherKey() {
        String[] gatherFields;
        String[] bizKeyFields;
        HashSet<String> bizAndGatherKeySet = new HashSet<String>();
        if (StringUtils.isNotEmpty((String)this.bizKeyFieldsID) && (bizKeyFields = this.bizKeyFieldsID.split(";")) != null) {
            for (String key : bizKeyFields) {
                if (!StringUtils.isNotEmpty((String)key)) continue;
                bizAndGatherKeySet.add(key);
            }
        }
        if (StringUtils.isNotEmpty((String)this.gatherFields) && (gatherFields = this.gatherFields.split(";")) != null) {
            for (String key : gatherFields) {
                if (!StringUtils.isNotEmpty((String)key)) continue;
                bizAndGatherKeySet.add(key);
            }
        }
        if (bizAndGatherKeySet.size() > 0) {
            return String.join((CharSequence)";", bizAndGatherKeySet);
        }
        return null;
    }
}

