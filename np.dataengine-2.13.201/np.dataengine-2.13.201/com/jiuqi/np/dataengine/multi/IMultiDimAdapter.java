/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.multi;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public interface IMultiDimAdapter {
    public ExprExecRegionCreator getFmlExecRegionCreator(QueryContext var1, QueryParam var2);

    public ExprExecNetwork createNetwork(QueryContext var1, ExprExecRegionCreator var2, boolean var3);

    public void parseMultiDimExpressions(ExecutorContext var1, DimensionValueSet var2, List<IParsedExpression> var3, IMonitor var4);
}

