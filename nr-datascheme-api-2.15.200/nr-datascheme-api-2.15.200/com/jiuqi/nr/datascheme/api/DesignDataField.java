/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.ValidationRule;
import com.jiuqi.nr.datascheme.api.core.BasicSetter;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldRestrictType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.util.List;

public interface DesignDataField
extends DataField,
BasicSetter,
OrderSetter {
    public void setAlias(String var1);

    public void setDataSchemeKey(String var1);

    public void setDataTableKey(String var1);

    public void setDataFieldApplyType(DataFieldApplyType var1);

    public void setDataFieldKind(DataFieldKind var1);

    public void setVersion(String var1);

    public void setLevel(String var1);

    public void setDefaultValue(String var1);

    public void setPrecision(Integer var1);

    public void setDataFieldType(DataFieldType var1);

    public void setDecimal(Integer var1);

    public void setNullable(Boolean var1);

    public void setRefDataEntityKey(String var1);

    public void setRefDataFieldKey(String var1);

    public void setValidationRules(List<ValidationRule> var1);

    public void setMeasureUnit(String var1);

    public void setDataFieldGatherType(DataFieldGatherType var1);

    public void setAllowMultipleSelect(Boolean var1);

    public void setOnlyLeaf(Boolean var1);

    public void setFormatProperties(FormatProperties var1);

    public void setSecretLevel(Integer var1);

    public void setUseAuthority(Boolean var1);

    public void setAllowUndefinedCode(Boolean var1);

    public void setChangeWithPeriod(Boolean var1);

    public void setGenerateVersion(Boolean var1);

    public void setAllowTreeSum(Boolean var1);

    public void setVisible(Boolean var1);

    public void setEncrypted(Boolean var1);

    public void setZbType(ZbType var1);

    public void setZbSchemeVersion(String var1);

    public void setFormula(String var1);

    public void setFormulaDesc(String var1);

    public void setDataMaskCode(String var1);

    public void setRestrictType(DataFieldRestrictType var1);

    public void setRefParameter(String var1);
}

