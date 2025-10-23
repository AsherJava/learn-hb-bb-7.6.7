/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.zb.scheme.common.ZbApplyType;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.common.ZbGatherType;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import com.jiuqi.nr.zb.scheme.core.Level;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import com.jiuqi.nr.zb.scheme.core.PropInfo;
import com.jiuqi.nr.zb.scheme.core.ValidationRule;
import com.jiuqi.nr.zb.scheme.core.setter.ZbInfoSetter;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ZbInfo
extends ZbInfoSetter,
Level,
Ordered {
    public String getKey();

    public String getSchemeKey();

    public String getVersionKey();

    public String getTitle();

    public String getCode();

    public String getParentKey();

    public ZbDataType getDataType();

    public ZbType getType();

    public String getDesc();

    public String getFormula();

    public String getFormulaDesc();

    public Instant getUpdateTime();

    public String getDefaultValue();

    public Integer getPrecision();

    public Integer getDecimal();

    public Boolean getNullable();

    default public boolean isNullable() {
        return this.getNullable() != null && this.getNullable() != false;
    }

    public String getRefEntityId();

    public ZbApplyType getApplyType();

    public String getMeasureUnit();

    public ZbGatherType getGatherType();

    public FormatProperties getFormatProperties();

    public List<ValidationRule> getValidationRules();

    public Boolean getAllowUndefinedCode();

    default public boolean isAllowUndefinedCode() {
        return this.getAllowUndefinedCode() != null && this.getAllowUndefinedCode() != false;
    }

    public Boolean getAllowMultipleSelect();

    default public boolean isAllowMultipleSelect() {
        return this.getAllowMultipleSelect() != null && this.getAllowMultipleSelect() != false;
    }

    default public List<PropInfo> getExtProp() {
        return Collections.emptyList();
    }

    default public Map<String, Object> getExtPropMap() {
        return Collections.emptyMap();
    }
}

