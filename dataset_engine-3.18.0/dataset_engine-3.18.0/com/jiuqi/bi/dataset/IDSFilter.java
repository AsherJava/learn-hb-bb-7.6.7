/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;

public interface IDSFilter {
    public boolean judge(DataRow var1) throws BIDataSetException;
}

