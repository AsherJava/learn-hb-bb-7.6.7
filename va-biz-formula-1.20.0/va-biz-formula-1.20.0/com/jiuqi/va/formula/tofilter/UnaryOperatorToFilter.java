/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ToFilter;
import com.jiuqi.va.formula.provider.FilterNodeProvider;

public class UnaryOperatorToFilter
implements ToFilter {
    public static final void toFilter(IContext context, OperatorNode node, StringBuilder buffer, String sign, Object info) throws ToFilterException {
        ToFilter toFilter;
        buffer.append(" ").append(sign);
        IASTNode right = node.getChild(0);
        try {
            toFilter = FilterNodeProvider.get(right.getNodeType());
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", right.toString()), e);
        }
        toFilter.toFilter(context, right, buffer, info);
    }

    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        OperatorNode operatorNode = (OperatorNode)node;
        UnaryOperatorToFilter.toFilter(context, operatorNode, buffer, operatorNode.sign(), info);
    }
}

