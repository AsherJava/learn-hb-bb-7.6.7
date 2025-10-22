/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.definition.impl.internal;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.common.DefinitionTransUtils;
import com.jiuqi.np.definition.impl.internal.service.DefinitionImplHelper;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import org.springframework.util.StringUtils;

public class FieldDefineImpl
extends DesignFieldDefineImpl
implements FieldDefine {
    private static final long serialVersionUID = 5781774773161148133L;
    private String key;
    private String fieldName;
    private String ownerTableKey;
    private String tableName;
    private DataField dataField;
    private ColumnModelDefine columnModel;

    public FieldDefineImpl(DataField dataField) {
        this.key = dataField.getKey();
        this.ownerTableKey = dataField.getDataTableKey();
        this.dataField = dataField;
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public FieldDefineImpl(String fieldKey, DataField dataField, DataFieldDeployInfo deployInfo) {
        this.key = fieldKey;
        if (null != deployInfo) {
            this.fieldName = deployInfo.getFieldName();
            this.tableName = deployInfo.getTableName();
            this.ownerTableKey = deployInfo.getTableModelKey();
        } else {
            this.ownerTableKey = dataField.getDataTableKey();
        }
        this.dataField = dataField;
    }

    public FieldDefineImpl(ColumnModelDefine columnModel, String tableName) {
        this.key = columnModel.getID();
        this.fieldName = StringUtils.hasLength(columnModel.getName()) ? columnModel.getName() : columnModel.getCode();
        this.ownerTableKey = columnModel.getTableID();
        this.tableName = tableName;
        this.columnModel = columnModel;
    }

    public String getCode() {
        if (null != this.columnModel) {
            return this.columnModel.getCode();
        }
        return this.dataField.getCode();
    }

    public String getDescription() {
        if (null != this.columnModel) {
            return this.columnModel.getDesc();
        }
        return this.dataField.getDesc();
    }

    public String getDefaultValue() {
        if (null != this.dataField) {
            return this.dataField.getDefaultValue();
        }
        return null;
    }

    public String getDBDefaultValue() {
        if (null != this.columnModel) {
            return this.columnModel.getDefaultValue();
        }
        return null;
    }

    public String getCalculation() {
        return null;
    }

    public Integer getFractionDigits() {
        if (null != this.columnModel) {
            return this.columnModel.getDecimal();
        }
        return null == this.dataField.getDecimal() ? 0 : this.dataField.getDecimal();
    }

    public FieldType getType() {
        if (null != this.columnModel) {
            return DefinitionTransUtils.valueOf(this.columnModel.getColumnType());
        }
        return DefinitionTransUtils.valueOf(this.dataField.getDataFieldType());
    }

    public Integer getSize() {
        if (null != this.columnModel) {
            return this.columnModel.getPrecision();
        }
        return null == this.dataField.getPrecision() ? 0 : this.dataField.getPrecision();
    }

    public Boolean getNeedSynchronize() {
        return false;
    }

    public Boolean getNullable() {
        if (null != this.columnModel) {
            return this.columnModel.isNullAble();
        }
        return this.dataField.isNullable();
    }

    public String getOwnerTableKey() {
        return this.ownerTableKey;
    }

    public String getReferFieldKey() {
        if (null != this.columnModel) {
            return this.columnModel.getReferColumnID();
        }
        return this.dataField.getRefDataFieldKey();
    }

    public Boolean getTemporary() {
        return false;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getVerification() {
        return null;
    }

    public Boolean isDbNullAble() {
        return false;
    }

    public Boolean getUseAuthority() {
        if (null != this.dataField) {
            return this.dataField.isUseAuthority();
        }
        return false;
    }

    public String getKey() {
        return this.key;
    }

    public String getTitle() {
        if (null != this.columnModel) {
            return this.columnModel.getTitle();
        }
        return this.dataField.getTitle();
    }

    public String getOrder() {
        if (null != this.dataField) {
            return this.dataField.getOrder();
        }
        return String.valueOf(this.columnModel.getOrder());
    }

    public String getVersion() {
        if (null != this.dataField) {
            return this.dataField.getVersion();
        }
        return null;
    }

    public String getOwnerLevelAndId() {
        if (null != this.dataField) {
            return this.dataField.getLevel();
        }
        return null;
    }

    public Boolean getFixedSize() {
        return false;
    }

    public Boolean getGlobalUnique() {
        return false;
    }

    public Boolean getIsMoneyMeasure() {
        return false;
    }

    public FieldValueType getValueType() {
        if (null != this.dataField) {
            if (this.dataField.getCode().equals("BIZKEYORDER")) {
                return FieldValueType.FIELD_VALUE_BIZKEY_ORDER;
            }
            if (this.dataField.getCode().equals("FLOATORDER")) {
                return FieldValueType.FIELD_VALUE_INPUT_ORDER;
            }
            if (this.dataField.getCode().equals("DATATIME")) {
                return FieldValueType.FIELD_VALUE_PERIOD_VALUE;
            }
            return FieldValueType.FIELD_VALUE_DEFALUT;
        }
        if (this.columnModel.getCode().equals("BIZKEYORDER")) {
            return FieldValueType.FIELD_VALUE_BIZKEY_ORDER;
        }
        if (this.columnModel.getCode().equals("FLOATORDER")) {
            return FieldValueType.FIELD_VALUE_INPUT_ORDER;
        }
        if (this.columnModel.getCode().equals("DATATIME")) {
            return FieldValueType.FIELD_VALUE_PERIOD_VALUE;
        }
        return DefinitionTransUtils.valueOf(this.columnModel.getApplyType());
    }

    public FieldGatherType getGatherType() {
        if (null != this.columnModel) {
            return this.columnModel.getAggrType() == null ? FieldGatherType.FIELD_GATHER_NONE : FieldGatherType.forValue((int)this.columnModel.getAggrType().getValue());
        }
        return null == this.dataField.getDataFieldGatherType() ? FieldGatherType.FIELD_GATHER_NONE : FieldGatherType.forValue((int)this.dataField.getDataFieldGatherType().getValue());
    }

    public Boolean getAllowUndefinedCode() {
        if (this.dataField == null) {
            return false;
        }
        return this.dataField.isAllowUndefinedCode();
    }

    public Boolean getAllowMultipleSelect() {
        if (null != this.dataField) {
            return this.dataField.isAllowMultipleSelect();
        }
        return this.columnModel.isMultival();
    }

    public Integer getMaxMultipleSelectedCount() {
        return 0;
    }

    public Boolean getAllowNotLeafNodeRefer() {
        return false;
    }

    public String getShowFormat() {
        if (null != this.columnModel) {
            return this.columnModel.getShowFormat();
        }
        return null;
    }

    public FormatProperties getFormatProperties() {
        if (null != this.dataField) {
            return this.dataField.getFormatProperties();
        }
        return null;
    }

    public Integer getSecretLevel() {
        return this.dataField.getSecretLevel();
    }

    public String getMeasureUnit() {
        if (null != this.columnModel) {
            return this.columnModel.getMeasureUnit();
        }
        return this.dataField.getMeasureUnit();
    }

    public Boolean getAllowMultipleMap() {
        return false;
    }

    public Boolean getCanModifyByMapTarget() {
        return false;
    }

    public String getPropertyType() {
        return null;
    }

    public String getEntityKey() {
        if (null != this.dataField) {
            return this.dataField.getRefDataEntityKey();
        }
        if (StringUtils.hasLength(this.columnModel.getReferTableID())) {
            return DefinitionImplHelper.getEntityIdByTable(this.columnModel.getReferTableID());
        }
        return null;
    }

    public String getAlias() {
        if (null != this.columnModel) {
            return null;
        }
        return this.dataField.getAlias();
    }
}

