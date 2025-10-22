/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.impl.DetailCollectMonitor;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.List;
import java.util.Map;

public interface IExpressionEvaluator {
    @Deprecated
    public AbstractData eval(IDataDefinitionRuntimeController var1, String var2, DimensionValueSet var3) throws ExpressionException;

    @Deprecated
    public AbstractData eval(String var1, ExecutorContext var2, DimensionValueSet var3) throws ExpressionException;

    public AbstractData eval(String var1, ExecutorContext var2, QueryEnvironment var3, DimensionValueSet var4) throws ExpressionException;

    public Object evalValue(IDataDefinitionRuntimeController var1, String var2, DimensionValueSet var3) throws ExpressionException;

    public Object evalValue(String var1, ExecutorContext var2, DimensionValueSet var3) throws ExpressionException;

    public Object evalValueWithDetail(String var1, ExecutorContext var2, DimensionValueSet var3, DetailCollectMonitor var4) throws Exception;

    public Object evalValueWithDetail(IASTNode var1, ExecutorContext var2, DimensionValueSet var3, DetailCollectMonitor var4) throws Exception;

    public Object[] evalValues(List<String> var1, ExecutorContext var2, DimensionValueSet var3) throws ExpressionException;

    public Map<String, Object[]> evalBatch(List<String> var1, ExecutorContext var2, DimensionValueSet var3) throws Exception;

    public void setMultiDimModule(boolean var1);
}

