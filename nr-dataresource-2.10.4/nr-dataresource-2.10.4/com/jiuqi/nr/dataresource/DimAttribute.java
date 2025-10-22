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

public interface DimAttribute
extends Ordered,
OrderSetter,
Serializable,
Cloneable {
    public String getCode();

    public void setCode(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public boolean isHidden();

    public void setHidden(boolean var1);

    public String getDimKey();

    public void setDimKey(String var1);

    public String getKey();

    public void setKey(String var1);

    public String getResourceDefineKey();

    public void setResourceDefineKey(String var1);
}

