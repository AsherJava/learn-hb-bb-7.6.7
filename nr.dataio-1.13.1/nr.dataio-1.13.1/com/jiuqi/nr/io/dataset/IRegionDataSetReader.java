/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.io.dataset;

import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.List;

public interface IRegionDataSetReader {
    public boolean needRowKey();

    public void start(List<FieldDefine> var1);

    public void readRowData(List<Object> var1);

    public void finish();
}

