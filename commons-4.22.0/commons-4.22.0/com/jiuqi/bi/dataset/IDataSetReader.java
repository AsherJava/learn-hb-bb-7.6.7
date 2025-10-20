/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.Metadata;

public interface IDataSetReader<T> {
    public void start(Metadata<T> var1) throws DataSetException;

    public boolean process(DataRow var1) throws DataSetException;

    public void finish() throws DataSetException;
}

