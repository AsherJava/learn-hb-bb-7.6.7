/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.nr.data.engine.fml.var.ContextVariableManager;
import java.util.List;

public class PriorityContextVariableManager
extends ContextVariableManager
implements IReportDynamicNodeProvider {
    @Override
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        return null;
    }

    @Override
    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        return null;
    }

    @Override
    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        return null;
    }

    @Override
    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        VariableDataNode node = (VariableDataNode)super.find(context, token, refName);
        if (node != null) {
            node.setSpecial(true);
        }
        return node;
    }

    @Override
    public IASTNode findSpec(IContext arg0, Token arg1, String arg2, String arg3) throws DynamicNodeException {
        return null;
    }
}

