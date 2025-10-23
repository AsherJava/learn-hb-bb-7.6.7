/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.OrderSetter
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import java.io.Serializable;
import java.time.Instant;

public interface DataBasic
extends Ordered,
OrderSetter,
Cloneable,
Serializable {
    public String getKey();

    public void setKey(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getDesc();

    public Instant getUpdateTime();

    public void setDesc(String var1);

    public void setUpdateTime(Instant var1);
}

