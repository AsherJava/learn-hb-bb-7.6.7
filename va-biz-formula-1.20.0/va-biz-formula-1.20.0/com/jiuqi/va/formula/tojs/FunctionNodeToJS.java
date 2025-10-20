/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.ToJavaScript;

public class FunctionNodeToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        FunctionNode functionNode = (FunctionNode)node;
        IFunction function = functionNode.getDefine();
        if (function instanceof ModelFunction) {
            ModelFunction modelFunction = (ModelFunction)function;
            modelFunction.toJavaScript(buffer, functionNode.getParameters());
        } else {
            ModelFunction.toJavaScript(buffer, functionNode.getParameters().stream(), function.name());
        }
    }
}

