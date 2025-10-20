/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.bi.dataset.scroll;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;

public interface IDataProcessor {
    public void process(DataRow var1) throws BIDataSetException;

    public void complete(boolean var1) throws BIDataSetException;
}

