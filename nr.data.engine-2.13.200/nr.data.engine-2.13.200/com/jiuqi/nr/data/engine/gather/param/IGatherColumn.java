/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 */
package com.jiuqi.nr.data.engine.gather.param;

import com.jiuqi.np.dataengine.data.AbstractData;

public interface IGatherColumn {
    public Object readValue();

    public void writeValue(Object var1);

    public int getDataType();

    public AbstractData getAbstractData();
}

