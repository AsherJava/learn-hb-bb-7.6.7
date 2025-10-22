/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datascheme.api.DataField;

public interface IDataValueProcessor {
    public AbstractData processValue(IMetaData var1, AbstractData var2);

    public AbstractData processValue(DataField var1, AbstractData var2);
}

