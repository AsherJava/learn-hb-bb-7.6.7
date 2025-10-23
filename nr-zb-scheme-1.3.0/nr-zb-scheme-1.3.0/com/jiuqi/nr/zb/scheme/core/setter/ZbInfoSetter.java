/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.core.setter;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ZbInfoSetter {
    public void setKey(String var1);

    public void setSchemeKey(String var1);

    public void setVersionKey(String var1);

    public void setTitle(String var1);

    public void setCode(String var1);

    public void setParentKey(String var1);

    public void setDesc(String var1);

    public void setDataType(ZbDataType var1);

    public void setType(ZbType var1);

    public void setGatherType(ZbGatherType var1);

    public void setFormula(String var1);

    public void setFormulaDesc(String var1);

    public void setDefaultValue(String var1);

    public void setMeasureUnit(String var1);

    public void setValidationRules(List<ValidationRule> var1);

    public void setFormatProperties(FormatProperties var1);

    public void setRefEntityId(String var1);

    public void setPrecision(Integer var1);

    public void setDecimal(Integer var1);

    public void setNullable(Boolean var1);

    public void setApplyType(ZbApplyType var1);

    public void setAllowUndefinedCode(Boolean var1);

    public void setUpdateTime(Instant var1);

    public void setLevel(String var1);

    public void setOrder(String var1);

    public void setAllowMultipleSelect(Boolean var1);

    default public void setExtProp(List<PropInfo> extProp) {
    }

    default public void setExtPropMap(Map<String, Object> extProp) {
    }
}

