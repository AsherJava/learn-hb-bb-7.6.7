/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import com.jiuqi.nr.zb.scheme.core.Level;
import com.jiuqi.nr.zb.scheme.core.MetaItem;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import com.jiuqi.nr.zb.scheme.core.setter.ZbSchemeVersionSetter;

public interface ZbSchemeVersion
extends ZbSchemeVersionSetter,
MetaItem,
Ordered,
Level {
    public String getSchemeKey();

    public String getCode();

    public String getStartPeriod();

    public String getEndPeriod();

    public VersionStatus getStatus();
}

