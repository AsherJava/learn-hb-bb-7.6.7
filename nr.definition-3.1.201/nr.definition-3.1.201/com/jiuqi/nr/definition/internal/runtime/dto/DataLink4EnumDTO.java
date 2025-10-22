/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.dto.DataLinkDTO;
import com.jiuqi.nr.definition.util.DataLinkHelper;

public class DataLink4EnumDTO
extends DataLinkDTO {
    private final EnumDisplayMode displayMode;
    private final String captionFieldsString;
    private final String dropDownFieldsString;
    private final boolean allowNotLeafNodeRefer;
    private final String isEnumShowFullPath;
    private final String enumTitleField;
    private final String enumLinkage;
    private final int enumCount;
    private final String enumPos;
    private final boolean enumLinkageStatus;
    private final String filterExpression;
    private final String filterTemplateID;
    private final boolean ignorePermissions;
    private byte allowUndefinedCode;
    private byte allowMultipleSelect;
    private byte extendedTags;

    public DataLink4EnumDTO(DataLinkDefine define) {
        super(define);
        this.displayMode = define.getDisplayMode();
        this.captionFieldsString = define.getCaptionFieldsString();
        this.dropDownFieldsString = define.getDropDownFieldsString();
        this.allowNotLeafNodeRefer = define.getAllowNotLeafNodeRefer();
        this.isEnumShowFullPath = define.getEnumShowFullPath();
        this.enumTitleField = define.getEnumTitleField();
        this.enumLinkage = define.getEnumLinkage();
        this.enumCount = define.getEnumCount();
        this.enumPos = define.getEnumPos();
        this.enumLinkageStatus = define.getEnumLinkageStatus();
        this.filterExpression = define.getFilterExpression();
        this.filterTemplateID = define.getFilterTemplate();
        this.ignorePermissions = define.isIgnorePermissions();
        Boolean allowUndefinedCode = null;
        if (define instanceof RunTimeDataLinkDefineImpl) {
            RunTimeDataLinkDefineImpl impl = (RunTimeDataLinkDefineImpl)define;
            allowUndefinedCode = impl.getSelfAllowUndefinedCode();
        } else {
            allowUndefinedCode = define.getAllowUndefinedCode();
        }
        this.allowUndefinedCode = null == allowUndefinedCode ? (byte)-1 : (allowUndefinedCode != false ? (byte)1 : 0);
        this.allowMultipleSelect = (byte)-1;
    }

    @Override
    public EnumDisplayMode getDisplayMode() {
        return this.displayMode;
    }

    @Override
    public String getCaptionFieldsString() {
        return this.captionFieldsString;
    }

    @Override
    public String getDropDownFieldsString() {
        return this.dropDownFieldsString;
    }

    @Override
    public boolean getAllowNotLeafNodeRefer() {
        return this.allowNotLeafNodeRefer;
    }

    @Override
    public String getEnumShowFullPath() {
        return this.isEnumShowFullPath;
    }

    @Override
    public String getEnumTitleField() {
        return this.enumTitleField;
    }

    @Override
    public String getEnumLinkage() {
        return this.enumLinkage;
    }

    @Override
    public int getEnumCount() {
        return this.enumCount;
    }

    @Override
    public String getEnumPos() {
        return this.enumPos;
    }

    @Override
    public boolean getEnumLinkageStatus() {
        return this.enumLinkageStatus;
    }

    @Override
    public String getFilterExpression() {
        return this.filterExpression;
    }

    @Override
    public boolean isIgnorePermissions() {
        return this.ignorePermissions;
    }

    @Override
    public String getFilterTemplate() {
        return this.filterTemplateID;
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        if (-1 == this.allowUndefinedCode) {
            this.initFromDataField();
        }
        return this.allowUndefinedCode > 0;
    }

    @Override
    public boolean getAllowMultipleSelect() {
        if (-1 == this.allowMultipleSelect) {
            this.initFromDataField();
        }
        return this.allowMultipleSelect > 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initFromDataField() {
        if (0 != this.extendedTags) {
            return;
        }
        DataLink4EnumDTO dataLink4EnumDTO = this;
        synchronized (dataLink4EnumDTO) {
            if (0 != this.extendedTags) {
                return;
            }
            if (-1 == this.allowUndefinedCode || -1 == this.allowMultipleSelect) {
                DataField dataField = DataLinkHelper.getDataField(this);
                super.initFromDataField(dataField);
                if (null != dataField) {
                    if (-1 == this.allowUndefinedCode) {
                        this.allowUndefinedCode = (byte)(dataField.isAllowUndefinedCode() ? 1 : 0);
                    }
                    if (-1 == this.allowMultipleSelect) {
                        this.allowMultipleSelect = (byte)(dataField.isAllowMultipleSelect() ? 1 : 0);
                    }
                }
            }
            this.extendedTags = (byte)(this.extendedTags | 1);
        }
    }

    @Override
    public void resetForDataField() {
        super.resetForDataField();
        if ((this.extendedTags & 2) > 0) {
            this.allowUndefinedCode = (byte)-1;
        }
        if ((this.extendedTags & 4) > 0) {
            this.allowUndefinedCode = (byte)-1;
        }
        this.extendedTags = 0;
    }
}

