/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.nr.single.core.task.service;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.nr.single.core.task.SingleTaskException;
import java.util.List;

public interface ISingleTableDataWriter {
    public Metadata getMetaData();

    public void setTextFieldValue(DataRow var1, int var2, String var3) throws SingleTaskException;

    public void setDocFieldValue(DataRow var1, int var2, List<String> var3) throws SingleTaskException;

    public void insert(DataRow var1) throws SingleTaskException;

    public void insert(List<DataRow> var1) throws SingleTaskException;

    public void insert(Object[] var1) throws SingleTaskException;

    public void close() throws SingleTaskException;
}

