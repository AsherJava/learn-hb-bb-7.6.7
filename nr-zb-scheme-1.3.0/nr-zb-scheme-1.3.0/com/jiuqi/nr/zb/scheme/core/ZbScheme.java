/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import com.jiuqi.nr.zb.scheme.core.setter.ZbSchemeSetter;

public interface ZbScheme
extends ZbSchemeSetter,
MetaItem,
Ordered {
    public String getCode();

    public String getParentKey();

    public String getDesc();
}

