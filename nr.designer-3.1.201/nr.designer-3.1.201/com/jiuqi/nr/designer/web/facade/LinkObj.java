/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.designer.web.facade.ValidationObj;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LinkObj {
    @JsonProperty(value="PosX")
    private int posX;
    @JsonProperty(value="PosY")
    private int posY;
    @JsonProperty(value="LinkExpression")
    private String linkExpression;
    @JsonProperty(value="ColNum")
    private int colNum;
    @JsonProperty(value="RowNum")
    private int rowNum;
    @JsonProperty(value="DataRegionID")
    private String dataRegionID;
    @JsonProperty(value="EditMode")
    private int editMode;
    @JsonProperty(value="EnumStyle")
    private int enumStyle;
    @JsonProperty(value="UniqueCode")
    private String uniqueCode;
    private int enumDefineID;
    @JsonProperty(value="EnumSourceType")
    private int enumSourceType;
    @JsonProperty(value="EnumExpression")
    private String enumExpression;
    @JsonProperty(value="DataValidation")
    private List<ValidationObj> dataValidation;
    @JsonProperty(value="LinkExtension")
    private String linkExtension;
    @JsonProperty(value="Title")
    private String title;
    @JsonProperty(value="ID")
    private String id;
    @JsonProperty(value="Order")
    private String order;
    @JsonProperty(value="IsNew")
    private boolean isNew = false;
    @JsonProperty(value="IsDirty")
    private boolean isDirty = false;
    @JsonProperty(value="IsDeleted")
    private boolean isDeleted = false;
    @JsonProperty(value="DisplayMode")
    private int displayMode;
    @JsonProperty(value="EnumCount")
    private int enumCount;
    @JsonProperty(value="CaptionFieldsKeys")
    private String captionFieldsKeys;
    @JsonProperty(value="DropDownFieldsString")
    private String dropDownFieldsString;
    @JsonProperty(value="AllowNotLeafNodeRefer")
    private boolean allowNotLeafNodeRefer;
    @JsonProperty(value="AllowUndefinedCode")
    private Boolean allowUndefinedCode;
    @JsonProperty(value="AllowNullAble")
    private Boolean allowNullAble;
    @JsonProperty(value="ParentField")
    private String parentField;
    @JsonProperty(value="RowFilterExpression")
    private String rowFilterExpression;
    @JsonProperty(value="ShowFullPath")
    private String showFullPath;
    @JsonProperty(value="FormatProperties")
    private FormatProperties formatProperties;
    @JsonProperty(value="IsFormulaOrField")
    private Integer isFormulaOrField;
    @JsonProperty(value="enumTitleField")
    private String enumTitleField;
    @JsonProperty(value="enumLinkage")
    private String enumLinkage;
    @JsonProperty(value="attachment")
    private String attachment = "";
    @JsonProperty(value="OwnerLevelAndId")
    private String ownerLevelAndId;
    @JsonProperty(value="enumPosMap")
    private Map<String, Object> enumPosMap;
    @JsonProperty(value="SameServeCode")
    private boolean sameServeCode;
    @JsonProperty(value="EnumLinkageStatus")
    private boolean enumLinkageStatus;
    @JsonProperty(value="FilterSettingType")
    private Integer filterSettingType;
    @JsonProperty(value="FilterTemplate")
    private String filterTemplate;
    @JsonProperty(value="FilterExpression")
    private String filterExpression;
    @JsonProperty(value="IgnorePermissions")
    private boolean ignorePermissions;
    @JsonProperty(value="MeasureUnit")
    private String measureUnit;

    @JsonIgnore
    public boolean getEnumLinkageStatus() {
        return this.enumLinkageStatus;
    }

    @JsonIgnore
    public void setEnumLinkageStatus(boolean enumLinkageStatus) {
        this.enumLinkageStatus = enumLinkageStatus;
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
    public String getOrder() {
        return this.order;
    }

    @JsonIgnore
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonIgnore
    public int getColNum() {
        return this.colNum;
    }

    @JsonIgnore
    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    @JsonIgnore
    public int getRowNum() {
        return this.rowNum;
    }

    @JsonIgnore
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @JsonIgnore
    public int getEnumDefineID() {
        return this.enumDefineID;
    }

    @JsonIgnore
    public void setEnumDefineID(int enumDefineID) {
        this.enumDefineID = enumDefineID;
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
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonIgnore
    public int getPosX() {
        return this.posX;
    }

    @JsonIgnore
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @JsonIgnore
    public int getPosY() {
        return this.posY;
    }

    @JsonIgnore
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @JsonIgnore
    public String getDataRegionID() {
        return this.dataRegionID;
    }

    @JsonIgnore
    public void setDataRegionID(String dataRegionID) {
        this.dataRegionID = dataRegionID;
    }

    @JsonIgnore
    public String getLinkExpression() {
        return this.linkExpression;
    }

    @JsonIgnore
    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    @JsonIgnore
    public int getEditMode() {
        return this.editMode;
    }

    @JsonIgnore
    public void setEditMode(int editMode) {
        this.editMode = editMode;
    }

    @JsonIgnore
    public int getEnumStyle() {
        return this.enumStyle;
    }

    @JsonIgnore
    public void setEnumStyle(int enumStyle) {
        this.enumStyle = enumStyle;
    }

    @JsonIgnore
    public String getUniqueCode() {
        return this.uniqueCode;
    }

    @JsonIgnore
    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    @JsonIgnore
    public int getEnumSourceType() {
        return this.enumSourceType;
    }

    @JsonIgnore
    public void setEnumSourceType(int enumSourceType) {
        this.enumSourceType = enumSourceType;
    }

    @JsonIgnore
    public String getEnumExpression() {
        return this.enumExpression;
    }

    @JsonIgnore
    public void setEnumExpression(String enumExpression) {
        this.enumExpression = enumExpression;
    }

    @JsonIgnore
    public List<ValidationObj> getDataValidation() {
        return this.dataValidation;
    }

    @JsonIgnore
    public void setDataValidation(List<ValidationObj> dataValidation) {
        this.dataValidation = dataValidation;
    }

    @JsonIgnore
    public String getLinkExtension() {
        return this.linkExtension;
    }

    @JsonIgnore
    public void setLinkExtension(String linkExtension) {
        this.linkExtension = linkExtension;
    }

    @JsonIgnore
    public int getDisplayMode() {
        return this.displayMode;
    }

    @JsonIgnore
    public void setDisplayMode(int displayMode) {
        this.displayMode = displayMode;
    }

    @JsonIgnore
    public int getEnumCount() {
        return this.enumCount;
    }

    @JsonIgnore
    public void setEnumCount(int enumCount) {
        this.enumCount = enumCount;
    }

    @JsonIgnore
    public String getCaptionFieldsKeys() {
        return this.captionFieldsKeys;
    }

    @JsonIgnore
    public void setCaptionFieldsKeys(String captionFieldsKeys) {
        this.captionFieldsKeys = captionFieldsKeys;
    }

    @JsonIgnore
    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    @JsonIgnore
    public void setDropDownFieldsString(String dropDownFieldsString) {
        this.dropDownFieldsString = dropDownFieldsString;
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
    public Boolean isAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    @JsonIgnore
    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    @JsonIgnore
    public Boolean isAllowNullAble() {
        return this.allowNullAble;
    }

    @JsonIgnore
    public void setAllowNullAble(Boolean allowNullAble) {
        this.allowNullAble = allowNullAble;
    }

    @JsonIgnore
    public String getParentField() {
        return this.parentField;
    }

    @JsonIgnore
    public void setParentField(String parentField) {
        this.parentField = parentField;
    }

    @JsonIgnore
    public String getRowFilterExpression() {
        return this.rowFilterExpression;
    }

    @JsonIgnore
    public void setRowFilterExpression(String rowFilterExpression) {
        this.rowFilterExpression = rowFilterExpression;
    }

    @JsonIgnore
    public String getShowFullPath() {
        return this.showFullPath;
    }

    @JsonIgnore
    public void setShowFullPath(String showFullPath) {
        this.showFullPath = showFullPath;
    }

    @JsonIgnore
    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    @JsonIgnore
    public Integer getIsFormulaOrField() {
        return this.isFormulaOrField;
    }

    @JsonIgnore
    public void setIsFormulaOrField(Integer isFormulaOrField) {
        this.isFormulaOrField = isFormulaOrField;
    }

    @JsonIgnore
    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    @JsonIgnore
    public void setEnumTitleField(String enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    @JsonIgnore
    public String getEnumLinkage() {
        return this.enumLinkage;
    }

    @JsonIgnore
    public void setEnumLinkage(String enumLinkage) {
        this.enumLinkage = enumLinkage;
    }

    @JsonIgnore
    public String getAttachment() {
        return this.attachment;
    }

    @JsonIgnore
    public void setAttachment(String attachment) {
        this.attachment = attachment;
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

    @JsonIgnore
    public void setEnumPosMap(Map<String, Object> enumPosMap) {
        this.enumPosMap = enumPosMap;
    }

    @JsonIgnore
    public Map<String, Object> getEnumPosMap() {
        return this.enumPosMap;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public boolean isIgnorePermissions() {
        return this.ignorePermissions;
    }

    public void setIgnorePermissions(boolean ignorePermissions) {
        this.ignorePermissions = ignorePermissions;
    }

    public Integer getFilterSettingType() {
        return this.filterSettingType;
    }

    public void setFilterSettingType(Integer filterSettingType) {
        this.filterSettingType = filterSettingType;
    }

    public String getFilterTemplate() {
        return this.filterTemplate;
    }

    public void setFilterTemplate(String filterTemplate) {
        this.filterTemplate = filterTemplate;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

