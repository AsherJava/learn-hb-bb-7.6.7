/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.OrderSetter
 *  com.jiuqi.nr.datascheme.api.core.Ordered
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.dataresource.DataLinkKind;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import java.io.Serializable;
import java.time.Instant;

public interface DataResourceLink
extends Ordered,
OrderSetter,
Serializable,
Cloneable {
    public String getGroupKey();

    public String getDataFieldKey();

    public String getResourceDefineKey();

    public DataLinkKind getKind();

    public Instant getUpdateTime();

    public void setGroupKey(String var1);

    public void setDataFieldKey(String var1);

    public void setUpdateTime(Instant var1);

    public void setKind(DataLinkKind var1);

    public void setResourceDefineKey(String var1);
}

