/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.common.PropDataType;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.setter.PropInfoSetter;

public interface PropInfo
extends PropInfoSetter,
MetaItem {
    default public String getCode() {
        return this.getFieldName();
    }

    public String getFieldName();

    public PropDataType getDataType();

    public Integer getDecimal();

    public Integer getPrecision();

    public Object getDefaultValue();

    public String getReferEntityId();

    default public boolean isMultiple() {
        return this.getMultiple() != null && this.getMultiple() != false;
    }

    public Boolean getMultiple();

    default public Object getValue() {
        return null;
    }
}

