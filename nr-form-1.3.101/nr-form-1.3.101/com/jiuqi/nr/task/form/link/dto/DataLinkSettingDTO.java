/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.task.form.link.dto;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.task.form.ext.dto.ConfigDTO;
import com.jiuqi.nr.task.form.ext.face.ConfigExt;
import com.jiuqi.nr.task.form.link.dto.DataLinkDTO;
import com.jiuqi.nr.task.form.link.dto.FormatDTO;
import java.util.List;
import java.util.Map;

public class DataLinkSettingDTO
extends DataLinkDTO
implements ConfigExt {
    private Integer editMode = 0;
    private Integer displayMode = 0;
    @Deprecated
    private List<String> dataValidation;
    private String captionFieldsString;
    private String dropDownFieldsString;
    private Boolean allowUndefinedCode;
    private Boolean allowNullAble;
    @Deprecated
    private Boolean allowMultipleSelect = false;
    private Boolean allowNotLeafNodeRefer = false;
    private FormatDTO linkFormat;
    private String uniqueCode;
    private String enumShowFullPath;
    private String enumTitleField;
    private List<Map<String, String>> enumLinkage;
    private String enumLinkageMethod;
    private Map<String, String> enumLinkageData;
    private Integer enumCount = 0;
    @Deprecated
    private String enumPos;
    private Map<String, Object> enumPosMap;
    private Boolean enumLinkageStatus = false;
    private String filterExpression;
    private String filterTemplateKey;
    private FormatProperties formatProperties;
    private Boolean isIgnorePermissions = false;
    private List<ConfigDTO> configData;
    private String attachment;

    public String getAttachment() {
        return this.attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getEditMode() {
        return this.editMode;
    }

    public void setEditMode(Integer editMode) {
        this.editMode = editMode == null ? 0 : editMode;
    }

    public Integer getDisplayMode() {
        return this.displayMode;
    }

    public void setDisplayMode(Integer displayMode) {
        this.displayMode = displayMode == null ? 0 : displayMode;
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

    public Boolean getAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    public void setAllowNotLeafNodeRefer(Boolean allowNotLeafNodeRefer) {
        this.allowNotLeafNodeRefer = allowNotLeafNodeRefer != null && allowNotLeafNodeRefer != false;
    }

    public FormatDTO getLinkFormat() {
        return this.linkFormat;
    }

    public void setLinkFormat(FormatDTO linkFormat) {
        this.linkFormat = linkFormat;
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

    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    public void setEnumTitleField(String enumTitleField) {
        this.enumTitleField = enumTitleField;
    }

    public List<Map<String, String>> getEnumLinkage() {
        return this.enumLinkage;
    }

    public void setEnumLinkage(List<Map<String, String>> enumLinkage) {
        this.enumLinkage = enumLinkage;
    }

    public String getEnumLinkageMethod() {
        return this.enumLinkageMethod;
    }

    public void setEnumLinkageMethod(String enumLinkageMethod) {
        this.enumLinkageMethod = enumLinkageMethod;
    }

    public Map<String, String> getEnumLinkageData() {
        return this.enumLinkageData;
    }

    public void setEnumLinkageData(Map<String, String> enumLinkageData) {
        this.enumLinkageData = enumLinkageData;
    }

    public Integer getEnumCount() {
        return this.enumCount;
    }

    public void setEnumCount(Integer enumCount) {
        this.enumCount = enumCount;
    }

    public String getEnumPos() {
        return this.enumPos;
    }

    public void setEnumPos(String enumPos) {
        this.enumPos = enumPos;
    }

    public Map<String, Object> getEnumPosMap() {
        return this.enumPosMap;
    }

    public void setEnumPosMap(Map<String, Object> enumPosMap) {
        this.enumPosMap = enumPosMap;
    }

    public Boolean getEnumLinkageStatus() {
        return this.enumLinkageStatus;
    }

    public void setEnumLinkageStatus(Boolean enumLinkageStatus) {
        this.enumLinkageStatus = enumLinkageStatus != null && enumLinkageStatus != false;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public Boolean getIgnorePermissions() {
        return this.isIgnorePermissions;
    }

    public void setIgnorePermissions(Boolean ignorePermissions) {
        this.isIgnorePermissions = ignorePermissions != null && ignorePermissions != false;
    }

    @Override
    public List<ConfigDTO> getConfigData() {
        return this.configData;
    }

    public void setConfigData(List<ConfigDTO> configData) {
        this.configData = configData;
    }

    public String getFilterTemplateKey() {
        return this.filterTemplateKey;
    }

    public void setFilterTemplateKey(String filterTemplateKey) {
        this.filterTemplateKey = filterTemplateKey;
    }

    public FormatProperties getFormatProperties() {
        return this.formatProperties;
    }

    public void setFormatProperties(FormatProperties formatProperties) {
        this.formatProperties = formatProperties;
    }
}

