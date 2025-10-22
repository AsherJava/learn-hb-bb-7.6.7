/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 */
package com.jiuqi.nr.designer.web.treebean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TableObject {
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="IsNew")
    private boolean isNew;
    @JsonProperty(value="IsDirty")
    private boolean isDirty;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted;
    @JsonProperty(value="OwnerGroupID")
    private String ownerGroupID;
    @JsonProperty(value="Code")
    private String code;
    @JsonProperty(value="PeriodFieldID")
    private String periodFieldID;
    @JsonProperty(value="BizKeyFieldsStr")
    private String bizKeyFieldsStr;
    @JsonProperty(value="RowKeyFieldsStr")
    private String rowKeyFieldsStr;
    @JsonProperty(value="SupportDatedVersion")
    private boolean supportDatedVersion;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="Kind")
    private int kind;
    @JsonProperty(value="Fields")
    private Map<String, FieldObject> fields;
    @JsonProperty(value="IsAuto")
    private boolean isAuto;
    @JsonProperty(value="IdRecord")
    private boolean idRecord;
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="TableType")
    private String tableType;

    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public TableObject() {
    }

    public TableObject(DesignTableDefine tableDefine) {
        this.id = tableDefine.getKey();
        this.code = tableDefine.getCode();
        this.bizKeyFieldsStr = tableDefine.getBizKeyFieldsStr();
        this.title = tableDefine.getTitle();
        this.kind = tableDefine.getKind().getValue();
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
    public String getOwnerGroupID() {
        return this.ownerGroupID;
    }

    @JsonIgnore
    public void setOwnerGroupID(String ownerGroupID) {
        this.ownerGroupID = ownerGroupID;
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
    public String getPeriodFieldID() {
        return this.periodFieldID;
    }

    @JsonIgnore
    public void setPeriodFieldID(String periodFieldID) {
        this.periodFieldID = periodFieldID;
    }

    @JsonIgnore
    public String getBizKeyFieldsStr() {
        return this.bizKeyFieldsStr;
    }

    @JsonIgnore
    public void setBizKeyFieldsStr(String bizKeyFieldsStr) {
        this.bizKeyFieldsStr = bizKeyFieldsStr;
    }

    @JsonIgnore
    public String getRowKeyFieldsStr() {
        return this.rowKeyFieldsStr;
    }

    @JsonIgnore
    public void setRowKeyFieldsStr(String rowKeyFieldsStr) {
        this.rowKeyFieldsStr = rowKeyFieldsStr;
    }

    @JsonIgnore
    public boolean isSupportDatedVersion() {
        return this.supportDatedVersion;
    }

    @JsonIgnore
    public void setSupportDatedVersion(boolean supportDatedVersion) {
        this.supportDatedVersion = supportDatedVersion;
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
    public int getKind() {
        return this.kind;
    }

    @JsonIgnore
    public void setKind(int kind) {
        this.kind = kind;
    }

    @JsonIgnore
    public Map<String, FieldObject> getFields() {
        return this.fields;
    }

    @JsonIgnore
    public void setFields(Map<String, FieldObject> fields) {
        this.fields = fields;
    }

    @JsonIgnore
    public boolean getIsAuto() {
        return this.isAuto;
    }

    @JsonIgnore
    public void setAuto(boolean isAuto) {
        this.isAuto = isAuto;
    }

    @JsonIgnore
    public boolean isIdRecord() {
        return this.idRecord;
    }

    @JsonIgnore
    public void setIdRecord(boolean idRecord) {
        this.idRecord = idRecord;
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

    public DesignTableDefine castToDefine(DesignTableDefine tableDefine) {
        tableDefine.setKey(this.id);
        tableDefine.setCode(this.code);
        tableDefine.setOwnerGroupID(this.ownerGroupID);
        tableDefine.setPeriodFieldID(this.periodFieldID);
        tableDefine.setBizKeyFields(this.bizKeyFieldsStr);
        tableDefine.setTitle(this.title);
        tableDefine.setKind(TableKind.forValue((int)this.kind));
        tableDefine.setIsAuTo(this.isAuto);
        tableDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        return tableDefine;
    }
}

