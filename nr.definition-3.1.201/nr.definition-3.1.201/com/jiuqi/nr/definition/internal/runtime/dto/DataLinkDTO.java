/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeDataLinkDefineImpl;
import com.jiuqi.nr.definition.util.DataLinkHelper;
import java.util.Date;
import java.util.List;

public class DataLinkDTO
implements DataLinkDefine {
    private final String key;
    private final String uniqueCode;
    private final DataLinkType type;
    private final String linkExpression;
    private final String bindingExpression;
    private final int posX;
    private final int posY;
    private final int colNum;
    private final int rowNum;
    private final DataLinkEditMode editMode;
    private String regionKey;
    private String measureUnit;
    private byte allowNullAble;
    private FormatProperties formatProperties;
    private List<String> dataValidation;
    private byte extendedTags;

    public DataLinkDTO(DataLinkDefine dataLinkDefine) {
        this.key = dataLinkDefine.getKey();
        this.uniqueCode = dataLinkDefine.getUniqueCode();
        this.type = dataLinkDefine.getType();
        this.regionKey = dataLinkDefine.getRegionKey();
        this.linkExpression = dataLinkDefine.getLinkExpression();
        this.bindingExpression = dataLinkDefine.getBindingExpression();
        this.posX = dataLinkDefine.getPosX();
        this.posY = dataLinkDefine.getPosY();
        this.colNum = dataLinkDefine.getColNum();
        this.rowNum = dataLinkDefine.getRowNum();
        this.editMode = dataLinkDefine.getEditMode();
        this.measureUnit = dataLinkDefine.getMeasureUnit();
        Boolean allowNullAble = null;
        if (dataLinkDefine instanceof RunTimeDataLinkDefineImpl) {
            RunTimeDataLinkDefineImpl impl = (RunTimeDataLinkDefineImpl)dataLinkDefine;
            allowNullAble = impl.getSelfAllowNullAble();
            this.formatProperties = impl.getSelfFormatProperties();
            this.dataValidation = impl.getSelfDataValidation();
        } else {
            allowNullAble = dataLinkDefine.getAllowNullAble();
            this.formatProperties = dataLinkDefine.getFormatProperties();
            this.dataValidation = dataLinkDefine.getDataValidation();
        }
        this.allowNullAble = null == allowNullAble ? (byte)-1 : (allowNullAble != false ? (byte)1 : 0);
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public String getUniqueCode() {
        return this.uniqueCode;
    }

    @Override
    public DataLinkType getType() {
        return this.type;
    }

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    @Override
    public String getLinkExpression() {
        return this.linkExpression;
    }

    @Override
    public String getBindingExpression() {
        return this.bindingExpression;
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public int getColNum() {
        return this.colNum;
    }

    @Override
    public int getRowNum() {
        return this.rowNum;
    }

    @Override
    public DataLinkEditMode getEditMode() {
        return this.editMode;
    }

    @Override
    public String getMeasureUnit() {
        return this.measureUnit;
    }

    @Override
    public Boolean getAllowNullAble() {
        if (-1 == this.allowNullAble) {
            this.initFromDataField();
        }
        return this.allowNullAble > 0;
    }

    @Override
    public List<String> getDataValidation() {
        if (null == this.dataValidation) {
            this.initFromDataField();
        }
        return this.dataValidation;
    }

    @Override
    public FormatProperties getFormatProperties() {
        if (null == this.formatProperties) {
            this.initFromDataField();
        }
        return this.formatProperties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initFromDataField() {
        if (0 != this.extendedTags) {
            return;
        }
        DataLinkDTO dataLinkDTO = this;
        synchronized (dataLinkDTO) {
            if (0 != this.extendedTags) {
                return;
            }
            if (-1 == this.allowNullAble || null == this.dataValidation || null == this.formatProperties) {
                DataField dataField = DataLinkHelper.getDataField(this);
                this.initFromDataField(dataField);
            }
            this.extendedTags = (byte)(this.extendedTags | 1);
        }
    }

    protected void initFromDataField(DataField dataField) {
        if (null != dataField) {
            if (-1 == this.allowNullAble) {
                this.allowNullAble = (byte)(dataField.isNullable() ? 1 : 0);
                this.extendedTags = (byte)(this.extendedTags | 2);
            }
            if (null == this.formatProperties) {
                this.formatProperties = dataField.getFormatProperties();
                this.extendedTags = (byte)(this.extendedTags | 4);
            }
            if (null == this.dataValidation) {
                this.dataValidation = DataSchemeUtils.getValidationRulesStr((DataField)dataField);
                this.extendedTags = (byte)(this.extendedTags | 8);
            }
        }
    }

    public void resetForDataField() {
        if ((this.extendedTags & 2) > 0) {
            this.allowNullAble = (byte)-1;
        }
        if ((this.extendedTags & 4) > 0) {
            this.dataValidation = null;
        }
        if ((this.extendedTags & 8) > 0) {
            this.formatProperties = null;
        }
        this.extendedTags = 0;
    }

    public String getTitle() {
        return null;
    }

    public String getOrder() {
        return null;
    }

    public String getVersion() {
        return null;
    }

    public String getOwnerLevelAndId() {
        return null;
    }

    public Date getUpdateTime() {
        return null;
    }

    @Override
    public EnumDisplayMode getDisplayMode() {
        return EnumDisplayMode.DISPLAY_MODE_DEFAULT;
    }

    @Override
    public String getCaptionFieldsString() {
        return null;
    }

    @Override
    public String getDropDownFieldsString() {
        return null;
    }

    @Override
    public Boolean getAllowUndefinedCode() {
        return Boolean.FALSE;
    }

    @Override
    public boolean getAllowMultipleSelect() {
        return false;
    }

    @Override
    public boolean getAllowNotLeafNodeRefer() {
        return false;
    }

    @Override
    public String getEnumShowFullPath() {
        return null;
    }

    @Override
    public String getEnumTitleField() {
        return null;
    }

    @Override
    public String getEnumLinkage() {
        return null;
    }

    @Override
    public int getEnumCount() {
        return 0;
    }

    @Override
    public String getEnumPos() {
        return null;
    }

    @Override
    public boolean getEnumLinkageStatus() {
        return false;
    }

    @Override
    public String getFilterExpression() {
        return null;
    }

    @Override
    public boolean isIgnorePermissions() {
        return false;
    }

    @Override
    public String getFilterTemplate() {
        return null;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }
}

