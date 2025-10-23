/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.util.List;

public interface DataField
extends Basic,
Ordered {
    public String getAlias();

    public String getDataSchemeKey();

    public String getDataTableKey();

    public DataFieldKind getDataFieldKind();

    public String getVersion();

    public String getLevel();

    public String getDefaultValue();

    public Integer getPrecision();

    public DataFieldApplyType getDataFieldApplyType();

    public DataFieldType getDataFieldType();

    public Integer getDecimal();

    public Boolean getNullable();

    default public boolean isNullable() {
        return null != this.getNullable() && this.getNullable() != false;
    }

    public String getRefDataEntityKey();

    @Deprecated
    public String getRefDataFieldKey();

    public List<ValidationRule> getValidationRules();

    public String getMeasureUnit();

    public DataFieldGatherType getDataFieldGatherType();

    public Boolean getAllowMultipleSelect();

    default public boolean isAllowMultipleSelect() {
        return null != this.getAllowMultipleSelect() && this.getAllowMultipleSelect() != false;
    }

    public Boolean getOnlyLeaf();

    default public boolean isOnlyLeaf() {
        return null != this.getOnlyLeaf() && this.getOnlyLeaf() != false;
    }

    public FormatProperties getFormatProperties();

    public Integer getSecretLevel();

    public Boolean getUseAuthority();

    default public boolean isUseAuthority() {
        return null != this.getUseAuthority() && this.getUseAuthority() != false;
    }

    public Boolean getAllowUndefinedCode();

    default public boolean isAllowUndefinedCode() {
        return null != this.getAllowUndefinedCode() && this.getAllowUndefinedCode() != false;
    }

    public Boolean getChangeWithPeriod();

    default public boolean isChangeWithPeriod() {
        return null != this.getChangeWithPeriod() && this.getChangeWithPeriod() != false;
    }

    public Boolean getGenerateVersion();

    default public boolean isGenerateVersion() {
        return null != this.getGenerateVersion() && this.getGenerateVersion() != false;
    }

    public Boolean getAllowTreeSum();

    default public boolean isAllowTreeSum() {
        return null != this.getAllowTreeSum() && this.getAllowTreeSum() != false;
    }

    @Deprecated
    default public FieldValueType getValueType() {
        if ("BIZKEYORDER".equals(this.getCode())) {
            return FieldValueType.FIELD_VALUE_BIZKEY_ORDER;
        }
        if ("FLOATORDER".equals(this.getCode())) {
            return FieldValueType.FIELD_VALUE_INPUT_ORDER;
        }
        return FieldValueType.FIELD_VALUE_DEFALUT;
    }

    @Deprecated
    default public FieldGatherType getGatherType() {
        if (null == this.getDataFieldGatherType()) {
            return FieldGatherType.FIELD_GATHER_NONE;
        }
        return FieldGatherType.forValue((int)this.getDataFieldGatherType().getValue());
    }

    @Deprecated
    default public String getEntityKey() {
        return this.getRefDataEntityKey();
    }

    @Deprecated
    default public String getDescription() {
        return this.getDesc();
    }

    @Deprecated
    default public Integer getFractionDigits() {
        return null == this.getDecimal() ? 0 : this.getDecimal();
    }

    @Deprecated
    default public FieldType getType() {
        return EnumTransUtils.valueOf(this.getDataFieldType());
    }

    @Deprecated
    default public Integer getSize() {
        return null == this.getPrecision() ? 0 : this.getPrecision();
    }

    @Deprecated
    default public String getOwnerTableKey() {
        return this.getDataTableKey();
    }

    @Deprecated
    default public String getOwnerLevelAndId() {
        return this.getLevel();
    }

    public Boolean getVisible();

    default public boolean isVisible() {
        return null != this.getVisible() && this.getVisible() != false;
    }

    public Boolean getEncrypted();

    default public boolean isEncrypted() {
        return null != this.getEncrypted() && this.getEncrypted() != false;
    }

    public ZbType getZbType();

    public String getZbSchemeVersion();

    public String getFormula();

    public String getFormulaDesc();

    public String getDataMaskCode();

    public DataFieldRestrictType getRestrictType();

    public String getRefParameter();
}

