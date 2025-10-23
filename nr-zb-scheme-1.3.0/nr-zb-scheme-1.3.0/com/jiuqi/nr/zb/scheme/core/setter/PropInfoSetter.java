/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core.setter;

import com.jiuqi.nr.zb.scheme.common.PropDataType;
import java.time.Instant;

public interface PropInfoSetter {
    public void setKey(String var1);

    public void setTitle(String var1);

    public void setOrder(String var1);

    public void setFieldName(String var1);

    default public void setCode(String code) {
        this.setFieldName(code);
    }

    public void setDecimal(Integer var1);

    public void setPrecision(Integer var1);

    public void setDefaultValue(Object var1);

    public void setReferEntityId(String var1);

    public void setDataType(PropDataType var1);

    public void setLevel(String var1);

    public void setUpdateTime(Instant var1);

    public void setMultiple(Boolean var1);

    default public void setValue(Object value) {
    }
}

