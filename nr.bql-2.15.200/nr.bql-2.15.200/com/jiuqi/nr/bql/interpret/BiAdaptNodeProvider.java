/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.nr.bql.interpret.BiAdaptContext;
import com.jiuqi.nr.bql.interpret.BiAdaptNode;
import java.util.List;

public class BiAdaptNodeProvider
implements IReportDynamicNodeProvider {
    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        try {
            BiAdaptContext aContext = (BiAdaptContext)context;
            return aContext.findNode(null, refName.toUpperCase());
        }
        catch (ParseException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        if (objPath.size() == 2) {
            try {
                BiAdaptContext aContext = (BiAdaptContext)context;
                String tableCode = objPath.get(0).toUpperCase();
                String fieldCode = objPath.get(1).toUpperCase();
                return aContext.findNode(tableCode, fieldCode);
            }
            catch (ParseException e) {
                throw new DynamicNodeException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        BiAdaptContext aContext = (BiAdaptContext)context;
        IASTNode node = this.find(context, token, objPath);
        if (node != null && node instanceof BiAdaptNode) {
            BiAdaptNode dataNode = (BiAdaptNode)node;
            RestrictInfo info = aContext.parseRestrictInfo(dataNode.getTable().getCode(), restrictItems);
            dataNode.bindRestriction(info);
        }
        return node;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        return this.find(context, token, refName);
    }
}

