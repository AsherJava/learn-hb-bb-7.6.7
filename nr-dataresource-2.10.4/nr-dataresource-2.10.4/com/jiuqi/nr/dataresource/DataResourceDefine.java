/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.OrderSetter
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.dataresource.DataBasic;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;

public interface DataResourceDefine
extends DataBasic,
OrderSetter {
    public String getDimKey();

    public String getGroupKey();

    public String getPeriod();

    public void setGroupKey(String var1);

    public void setDimKey(String var1);

    public void setPeriod(String var1);
}

