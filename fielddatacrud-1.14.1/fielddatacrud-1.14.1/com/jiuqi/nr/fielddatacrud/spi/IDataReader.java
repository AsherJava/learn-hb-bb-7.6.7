/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRowData
 */
package com.jiuqi.nr.fielddatacrud.spi;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import java.util.List;

public interface IDataReader {
    public void start(List<IMetaData> var1, long var2);

    public void readRow(IRowData var1);

    public void finish();
}

