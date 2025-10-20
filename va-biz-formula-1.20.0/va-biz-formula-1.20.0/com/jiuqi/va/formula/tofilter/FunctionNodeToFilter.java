/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.ToFilter;

public class FunctionNodeToFilter
implements ToFilter {
    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        FunctionNode functionNode = (FunctionNode)node;
        IFunction function = functionNode.getDefine();
        if (function instanceof ModelFunction) {
            ModelFunction modelFunction = (ModelFunction)function;
            modelFunction.toFilter(context, functionNode.getParameters(), buffer, function.name(), info);
        } else {
            ModelFunction.baseFunctionToFilter(context, functionNode.getParameters(), buffer, function.name(), info);
        }
    }
}

