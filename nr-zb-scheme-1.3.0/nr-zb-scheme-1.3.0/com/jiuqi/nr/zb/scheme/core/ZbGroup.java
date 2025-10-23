/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.core.MetaGroup;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import com.jiuqi.nr.zb.scheme.core.setter.ZbGroupSetter;

public interface ZbGroup
extends ZbGroupSetter,
MetaGroup,
Ordered {
    public String getVersionKey();

    public String getSchemeKey();
}

