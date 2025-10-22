/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.period.util.JacksonUtils
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum2;
import com.jiuqi.nr.definition.exception.NrDefinitionRuntimeException;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.service.DesignBigDataService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.period.util.JacksonUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DesignDataLinkDefineData
implements DesignDataLinkDefine {
    private DesignDataLinkDefine designDataLinkDefine;
    private DesignBigDataService designBigDataService;
    private AttachmentObj attachmentObj;

    public DesignDataLinkDefineData(DesignDataLinkDefine designDataLinkDefine, DesignBigDataService designBigDataService) {
        this.designDataLinkDefine = designDataLinkDefine;
        this.designBigDataService = designBigDataService;
    }

    public DesignDataLinkDefine getDesignDataLinkDefine() {
        return this.designDataLinkDefine;
    }

    public AttachmentObj getAttachment() {
        if (null != this.attachmentObj) {
            return this.attachmentObj;
        }
        try {
            byte[] bigData = this.designBigDataService.getBigData(this.designDataLinkDefine.getKey(), "ATTACHMENT");
            if (null == bigData) {
                return null;
            }
            String attachment = DesignFormDefineBigDataUtil.bytesToString(bigData);
            if (!"".equals(attachment)) {
                this.attachmentObj = (AttachmentObj)JacksonUtils.jsonToObject((String)attachment, AttachmentObj.class);
            }
            return this.attachmentObj;
        }
        catch (Exception e) {
            throw new NrDefinitionRuntimeException((ErrorEnum)NrDefinitionErrorEnum2.DATA_LINK_QUERY, (Throwable)e);
        }
    }

    @Override
    public void setKey(String key) {
        this.designDataLinkDefine.setKey(key);
    }

    @Override
    public void setOrder(String order) {
        this.designDataLinkDefine.setOrder(order);
    }

    @Override
    public void setVersion(String version) {
        this.designDataLinkDefine.setVersion(version);
    }

    @Override
    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.designDataLinkDefine.setOwnerLevelAndId(ownerLevelAndId);
    }

    @Override
    public void setTitle(String title) {
        this.designDataLinkDefine.setTitle(title);
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.designDataLinkDefine.setUpdateTime(updateTime);
    }

    @Override
    public void setRegionKey(String regionKey) {
        this.designDataLinkDefine.setRegionKey(regionKey);
    }

    @Override
    public void setLinkExpression(String expression) {
        this.designDataLinkDefine.setLinkExpression(expression);
    }

    @Override
    public void setBindingExpression(String bindingExpression) {
        this.designDataLinkDefine.setBindingExpression(bindingExpression);
    }

    @Override
    public void setPosX(int posX) {
        this.designDataLinkDefine.setPosX(posX);
    }

    @Override
    public void setPosY(int posY) {
        this.designDataLinkDefine.setPosY(posY);
    }

    @Override
    public void setColNum(int colNum) {
        this.designDataLinkDefine.setColNum(colNum);
    }

    @Override
    public void setRowNum(int rowNum) {
        this.designDataLinkDefine.setRowNum(rowNum);
    }

    @Override
    public void setEditMode(DataLinkEditMode editMode) {
        this.designDataLinkDefine.setEditMode(editMode);
    }

    @Override
    public void setDisplayMode(EnumDisplayMode displayMode) {
        this.designDataLinkDefine.setDisplayMode(displayMode);
    }

    @Override
    public void setDataValidation(List<String> expression) {
        this.designDataLinkDefine.setDataValidation(expression);
    }

    @Override
    public void setCaptionFieldsString(String keys) {
        this.designDataLinkDefine.setCaptionFieldsString(keys);
    }

    @Override
    public void setDropDownFieldsString(String keys) {
        this.designDataLinkDefine.setDropDownFieldsString(keys);
    }

    @Override
    public void setAllowUndefinedCode(Boolean allowUndefinedCode) {
        this.designDataLinkDefine.setAllowUndefinedCode(allowUndefinedCode);
    }

    @Override
    public void setAllowNullAble(Boolean allowNullAble) {
        this.designDataLinkDefine.setAllowNullAble(allowNullAble);
    }

    @Override
    public void setAllowNotLeafNodeRefer(boolean allowNotLeafNodeRefer) {
        this.designDataLinkDefine.setAllowNotLeafNodeRefer(allowNotLeafNodeRefer);
    }

    @Override
    public void setFormatProperties(FormatProperties formatProperties) {
        this.designDataLinkDefine.setFormatProperties(formatProperties);
    }

    @Override
    public void setUniqueCode(String uniqueCode) {
        this.designDataLinkDefine.setUniqueCode(uniqueCode);
    }

    @Override
    public void setEnumShowFullPath(String showFullPath) {
        this.designDataLinkDefine.setEnumShowFullPath(showFullPath);
    }

    @Override
    public void setEnumTitleField(String enumTitleField) {
        this.designDataLinkDefine.setEnumTitleField(enumTitleField);
    }

    @Override
    public void setType(DataLinkType type) {
        this.designDataLinkDefine.setType(type);
    }

    @Override
    public void setEnumLinkage(String enumLinkage) {
        this.designDataLinkDefine.setEnumLinkage(enumLinkage);
    }

    @Override
    public void setEnumCount(int enumCount) {
        this.designDataLinkDefine.setEnumCount(enumCount);
    }

    @Override
    public void setEnumPos(String enumPos) {
        this.designDataLinkDefine.setEnumPos(enumPos);
    }

    @Override
    public void setEnumLinkageStatus(boolean enumLinkageStatus) {
        this.designDataLinkDefine.setEnumLinkageStatus(enumLinkageStatus);
    }

    @Override
    public void setFilterExpression(String expression) {
        this.designDataLinkDefine.setFilterExpression(expression);
    }

    @Override
    public void setIgnorePermissions(boolean ignore) {
        this.designDataLinkDefine.setIgnorePermissions(ignore);
    }

    @Override
    public String setFilterTemplate(String filterTemplateID) {
        return this.designDataLinkDefine.setFilterTemplate(filterTemplateID);
    }

    @Override
    public void setMeasureUnit(String measureUnit) {
        this.designDataLinkDefine.setMeasureUnit(measureUnit);
    }

    @Override
    public String getRegionKey() {
        return this.designDataLinkDefine.getRegionKey();
    }

    @Override
    public String getLinkExpression() {
        return this.designDataLinkDefine.getLinkExpression();
    }

    @Override
    public String getBindingExpression() {
        return this.designDataLinkDefine.getBindingExpression();
    }

    @Override
    public int getPosX() {
        return this.designDataLinkDefine.getPosX();
    }

    @Override
    public int getPosY() {
        return this.designDataLinkDefine.getPosY();
    }

    @Override
    public int getColNum() {
        return this.designDataLinkDefine.getColNum();
    }

    @Override
    public int getRowNum() {
        return this.designDataLinkDefine.getRowNum();
    }

    @Override
    public DataLinkEditMode getEditMode() {
        return this.designDataLinkDefine.getEditMode();
    }

    @Override
    public EnumDisplayMode getDisplayMode() {
        return this.designDataLinkDefine.getDisplayMode();
    }

    @Override
    public List<String> getDataValidation() {
        return this.designDataLinkDefine.getDataValidation();
    }

    @Override
    public String getCaptionFieldsString() {
        return this.designDataLinkDefine.getCaptionFieldsString();
    }

    @Override
    public String getDropDownFieldsString() {
        return this.designDataLinkDefine.getDropDownFieldsString();
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        return this.designDataLinkDefine.getAllowUndefinedCode();
    }

    @Override
    public Boolean getAllowNullAble() {
        return this.designDataLinkDefine.getAllowNullAble();
    }

    @Override
    public boolean getAllowMultipleSelect() {
        return this.designDataLinkDefine.getAllowMultipleSelect();
    }

    @Override
    public boolean getAllowNotLeafNodeRefer() {
        return this.designDataLinkDefine.getAllowNotLeafNodeRefer();
    }

    @Override
    public FormatProperties getFormatProperties() {
        return this.designDataLinkDefine.getFormatProperties();
    }

    @Override
    public String getUniqueCode() {
        return this.designDataLinkDefine.getUniqueCode();
    }

    @Override
    public String getEnumShowFullPath() {
        return this.designDataLinkDefine.getEnumShowFullPath();
    }

    @Override
    public String getEnumTitleField() {
        return this.designDataLinkDefine.getEnumTitleField();
    }

    @Override
    public DataLinkType getType() {
        return this.designDataLinkDefine.getType();
    }

    @Override
    public String getEnumLinkage() {
        return this.designDataLinkDefine.getEnumLinkage();
    }

    @Override
    public String getEnumLinkageMethod() {
        return this.designDataLinkDefine.getEnumLinkageMethod();
    }

    @Override
    public Map<String, String> getEnumLinkageData() {
        return this.designDataLinkDefine.getEnumLinkageData();
    }

    @Override
    public int getEnumCount() {
        return this.designDataLinkDefine.getEnumCount();
    }

    @Override
    public String getEnumPos() {
        return this.designDataLinkDefine.getEnumPos();
    }

    @Override
    public boolean getEnumLinkageStatus() {
        return this.designDataLinkDefine.getEnumLinkageStatus();
    }

    @Override
    public String getFilterExpression() {
        return this.designDataLinkDefine.getFilterExpression();
    }

    @Override
    public boolean isIgnorePermissions() {
        return this.designDataLinkDefine.isIgnorePermissions();
    }

    @Override
    public String getFilterTemplate() {
        return this.designDataLinkDefine.getFilterTemplate();
    }

    @Override
    public String getMeasureUnit() {
        return this.designDataLinkDefine.getMeasureUnit();
    }

    public Date getUpdateTime() {
        return this.designDataLinkDefine.getUpdateTime();
    }

    public String getKey() {
        return this.designDataLinkDefine.getKey();
    }

    public String getTitle() {
        return this.designDataLinkDefine.getTitle();
    }

    public String getOrder() {
        return this.designDataLinkDefine.getOrder();
    }

    public String getVersion() {
        return this.designDataLinkDefine.getVersion();
    }

    public String getOwnerLevelAndId() {
        return this.designDataLinkDefine.getOwnerLevelAndId();
    }
}

