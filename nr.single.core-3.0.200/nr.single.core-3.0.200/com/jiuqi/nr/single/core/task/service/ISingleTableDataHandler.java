/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 */
package com.jiuqi.nr.single.core.task.service;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.nr.single.core.task.SingleTaskException;

public interface ISingleTableDataHandler {
    public void start(Metadata<Column> var1) throws SingleTaskException;

    public void handle(DataRow var1) throws SingleTaskException;

    public void finish() throws SingleTaskException;
}

