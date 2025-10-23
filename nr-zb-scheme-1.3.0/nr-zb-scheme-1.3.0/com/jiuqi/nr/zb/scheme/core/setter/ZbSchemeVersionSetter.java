/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core.setter;

import com.jiuqi.nr.zb.scheme.common.VersionStatus;
import java.time.Instant;

public interface ZbSchemeVersionSetter {
    public void setKey(String var1);

    public void setSchemeKey(String var1);

    public void setCode(String var1);

    public void setOrder(String var1);

    public void setTitle(String var1);

    public void setStartPeriod(String var1);

    public void setEndPeriod(String var1);

    public void setLevel(String var1);

    public void setUpdateTime(Instant var1);

    public void setStatus(VersionStatus var1);
}

