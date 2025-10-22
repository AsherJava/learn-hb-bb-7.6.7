/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.io.Closeable;

public interface IDataSetExprEvaluator
extends Closeable {
    public AbstractData evaluate(DataRow var1) throws Exception;

    public boolean judge(DataRow var1) throws Exception;

    public void prepare(ExecutorContext var1, DimensionValueSet var2, String var3) throws Exception;
}

