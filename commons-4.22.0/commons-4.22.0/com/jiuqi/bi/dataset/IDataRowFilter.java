/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSetException;

@FunctionalInterface
public interface IDataRowFilter {
    public boolean filter(DataRow var1) throws DataSetException;

    public boolean equals(Object var1);
}

