/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.core.Level;
import com.jiuqi.nr.zb.scheme.core.Ordered;
import java.io.Serializable;
import java.time.Instant;

public interface MetaItem
extends Ordered,
Level,
Serializable,
Cloneable {
    public String getKey();

    public void setKey(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public Instant getUpdateTime();

    public void setUpdateTime(Instant var1);
}

