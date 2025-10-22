/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Iterator;

public interface IExecutorContextFactory {
    public ExecutorContext getExecutorContext(ParamRelation var1, DimensionValueSet var2);

    public ExecutorContext getExecutorContext(ParamRelation var1, DimensionCombination var2);

    public ExecutorContext getExecutorContext(ParamRelation var1, DimensionValueSet var2, Iterator<Variable> var3);

    public ExecutorContext getExecutorContext(ParamRelation var1, DimensionCombination var2, Iterator<Variable> var3);
}

