/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.definition.common.DataLinkEditMode
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.common.EnumDisplayMode
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NrdxDataLinkDTO {
    private String regionKey;
    private String linkExpression;
    private int posX;
    private int posY;
    private int colNum;
    private int rowNum;
    private DataLinkEditMode editMode = DataLinkEditMode.DATA_LINK_DEFAULT;
    private EnumDisplayMode displayMode = EnumDisplayMode.DISPLAY_MODE_DEFAULT;
    private List<String> dataValidation;
    private String captionFieldsString;
    private String dropDownFieldsString;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private Boolean allowUndefinedCode;
    private Boolean allowNullAble;
    private boolean allowNotLeafNodeRefer;
    private String uniqueCode;
    private String enumShowFullPath;
    private DataLinkType type;
    private String enumTitleField;
    private String enumLinkage;
    private int enumCount;
    private FormatProperties formatProperties;
    private String enumPos;
    private boolean enumLinkageStatus;
    private String entityViewID;
    private String filterExpression;
    private boolean ignorePermissions;
    private String measureUnit;
    private EntityViewDTO entityView;

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getLinkExpression() {
        return this.linkExpression;
    }

    public void setLinkExpression(String linkExpression) {
        this.linkExpression = linkExpression;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getColNum() {
        return this.colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getRowNum() {
        return this.rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public DataLinkEditMode getEditMode() {
        return this.editMode;
    }

    public void setEditMode(DataLinkEditMode editMode) {
        this.editMode = editMode;
    }

    public EnumDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(EnumDisplayMode displayMode) {
        this.displayMode = displayMode;
    }

    public List<String> getDataValidation() {
        return this.dataValidation;
    }

    public void setDataValidation(List<String> dataValidation) {
        this.dataValidation = dataValidation;
    }

    public String getCaptionFieldsString() {
        return this.captionFieldsString;
    }

    public void setCaptionFieldsString(String captionFieldsString) {
        this.captionFieldsString = captionFieldsString;
    }

    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    public void setDropDownFieldsString(String dropDownFieldsString) {
        this.dropDownFieldsString = dropDownFieldsString;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getAllowUndefinedCode() {
        return this.allowUndefinedCode;
    }

    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.allowUndefinedCode = allowUndefinedCode;
    }

    public Boolean getAllowNullAble() {
        return this.allowNullAble;
    }

    public void setAllowNullAble(Boolean allowNullAble) {
        this.allowNullAble = allowNullAble;
    }

    public boolean isAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getEnumShowFullPath() {
        return this.enumShowFullPath;
    }

    public void setEnumShowFullPath(String enumShowFullPath) {
        this.enumShowFullPath = enumShowFullPath;
    }

    public DataLinkType getType() {
        return this.type;
    }

    public void setType(DataLinkType type) {
        this.type = type;
    }

    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    public void setEnumTitleField(String enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    public String getEnumLinkage() {
        return this.enumLinkage;
    }

    public void setEnumLinkage(String enumLinkage) {
        this.enumLinkage = enumLinkage;
    }

    public int getEnumCount() {
        return this.enumCount;
    }

    public void setEnumCount(int enumCount) {
        this.enumCount = enumCount;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }

    public String getEnumPos() {
        return this.enumPos;
    }

    public void setEnumPos(String enumPos) {
        this.enumPos = enumPos;
    }

    public boolean isEnumLinkageStatus() {
        return this.enumLinkageStatus;
    }

    public void setEnumLinkageStatus(boolean enumLinkageStatus) {
        this.enumLinkageStatus = enumLinkageStatus;
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

    public String getEntityViewID() {
        return this.entityViewID;
    }

    public void setEntityViewID(String entityViewID) {
        this.entityViewID = entityViewID;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public void value2Define(DesignDataLinkDefine linkParam) {
        linkParam.setKey(this.getKey());
        linkParam.setOrder(this.getOrder());
        linkParam.setVersion(this.getVersion());
        linkParam.setTitle(this.getTitle());
        linkParam.setUpdateTime(this.getUpdateTime());
        linkParam.setRegionKey(this.getRegionKey());
        linkParam.setLinkExpression(this.getLinkExpression());
        linkParam.setPosX(this.getPosX());
        linkParam.setPosY(this.getPosY());
        linkParam.setColNum(this.getColNum());
        linkParam.setRowNum(this.getRowNum());
        linkParam.setEditMode(this.getEditMode());
        linkParam.setDisplayMode(this.getDisplayMode());
        linkParam.setDataValidation(this.getDataValidation());
        linkParam.setCaptionFieldsString(this.getCaptionFieldsString());
        linkParam.setDropDownFieldsString(this.getDropDownFieldsString());
        linkParam.setAllowUndefinedCode(this.getAllowUndefinedCode());
        linkParam.setAllowNullAble(this.getAllowNullAble());
        linkParam.setAllowNotLeafNodeRefer(this.isAllowNotLeafNodeRefer());
        linkParam.setUniqueCode(this.getUniqueCode());
        linkParam.setEnumShowFullPath(this.getEnumShowFullPath());
        linkParam.setType(this.getType());
        linkParam.setEnumCount(this.getEnumCount());
        linkParam.setEnumTitleField(this.getEnumTitleField());
        linkParam.setEnumLinkage(this.getEnumLinkage());
        linkParam.setOwnerLevelAndId(this.getOwnerLevelAndId());
        linkParam.setEnumPos(this.getEnumPos());
        linkParam.setEnumLinkageStatus(this.isEnumLinkageStatus());
        linkParam.setIgnorePermissions(this.isIgnorePermissions());
        if (this.getEntityViewID() != null) {
            linkParam.setFilterTemplate(this.getEntityViewID());
            linkParam.setFilterExpression(null);
        } else {
            linkParam.setFilterExpression(this.getFilterExpression());
        }
        linkParam.setFormatProperties(this.getFormatProperties());
        linkParam.setMeasureUnit(this.getMeasureUnit());
    }

    public static NrdxDataLinkDTO valueOf(DataLinkDefine srcLinkDefine) {
        NrdxDataLinkDTO linkParam = new NrdxDataLinkDTO();
        linkParam.setKey(srcLinkDefine.getKey());
        linkParam.setOrder(srcLinkDefine.getOrder());
        linkParam.setVersion(srcLinkDefine.getVersion());
        linkParam.setTitle(srcLinkDefine.getTitle());
        linkParam.setUpdateTime(srcLinkDefine.getUpdateTime());
        linkParam.setRegionKey(srcLinkDefine.getRegionKey());
        linkParam.setLinkExpression(srcLinkDefine.getLinkExpression());
        linkParam.setPosX(srcLinkDefine.getPosX());
        linkParam.setPosY(srcLinkDefine.getPosY());
        linkParam.setColNum(srcLinkDefine.getColNum());
        linkParam.setRowNum(srcLinkDefine.getRowNum());
        linkParam.setEditMode(srcLinkDefine.getEditMode());
        linkParam.setDisplayMode(srcLinkDefine.getDisplayMode());
        linkParam.setDataValidation(srcLinkDefine.getDataValidation());
        linkParam.setCaptionFieldsString(srcLinkDefine.getCaptionFieldsString());
        linkParam.setDropDownFieldsString(srcLinkDefine.getDropDownFieldsString());
        linkParam.setAllowUndefinedCode(srcLinkDefine.getAllowUndefinedCode());
        linkParam.setAllowNullAble(srcLinkDefine.getAllowNullAble());
        linkParam.setAllowNotLeafNodeRefer(srcLinkDefine.getAllowNotLeafNodeRefer());
        linkParam.setUniqueCode(srcLinkDefine.getUniqueCode());
        linkParam.setEnumShowFullPath(srcLinkDefine.getEnumShowFullPath());
        linkParam.setType(srcLinkDefine.getType());
        linkParam.setEnumCount(srcLinkDefine.getEnumCount());
        linkParam.setEnumTitleField(srcLinkDefine.getEnumTitleField());
        linkParam.setEnumLinkage(srcLinkDefine.getEnumLinkage());
        linkParam.setOwnerLevelAndId(srcLinkDefine.getOwnerLevelAndId());
        linkParam.setEnumPos(srcLinkDefine.getEnumPos());
        linkParam.setEnumLinkageStatus(srcLinkDefine.getEnumLinkageStatus());
        linkParam.setEntityViewID(srcLinkDefine.getFilterTemplate());
        linkParam.setFilterExpression(srcLinkDefine.getFilterExpression());
        linkParam.setIgnorePermissions(srcLinkDefine.isIgnorePermissions());
        linkParam.setFormatProperties(srcLinkDefine.getFormatProperties());
        linkParam.setMeasureUnit(srcLinkDefine.getMeasureUnit());
        return linkParam;
    }

    public EntityViewDTO getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityViewDTO entityView) {
        this.entityView = entityView;
    }
}

