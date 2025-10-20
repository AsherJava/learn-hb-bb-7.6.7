/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import java.util.List;

public class DataSetFunctionNode
extends FunctionNode {
    private static final long serialVersionUID = 3093234711124496751L;

    public DataSetFunctionNode(Token token, IFunction function, IASTNode[] params) {
        super(token, function, params);
    }

    public DataSetFunctionNode(Token token, IFunction function, List<IASTNode> params) {
        super(token, function, params);
    }

    public DataSetFunctionNode(Token token, IFunction function) {
        super(token, function);
    }
}

