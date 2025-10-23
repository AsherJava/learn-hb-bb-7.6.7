/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.core.MetaGroup;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import com.jiuqi.nr.zb.scheme.core.setter.ZbSchemeGroupSetter;

public interface ZbSchemeGroup
extends ZbSchemeGroupSetter,
MetaGroup,
Ordered {
    public String getDesc();
}

