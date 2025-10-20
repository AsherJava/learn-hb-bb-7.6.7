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

public class BinaryOperatorToFilter
implements ToFilter {
    public final void buildLeftAndRight(IContext context, OperatorNode node, StringBuilder left, StringBuilder right, Object info) throws ToFilterException {
        IASTNode lNode = node.getChild(0);
        try {
            ToFilter toFilter = FilterNodeProvider.get(lNode.getNodeType());
            if (lNode instanceof OperatorNode) {
                left.append("(");
                toFilter.toFilter(context, lNode, left, info);
                left.append(")");
            } else {
                toFilter.toFilter(context, lNode, left, info);
            }
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", lNode.toString()), e);
        }
        IASTNode rNode = node.getChild(1);
        try {
            ToFilter toFilter = FilterNodeProvider.get(rNode.getNodeType());
            if (rNode instanceof OperatorNode) {
                right.append("(");
                toFilter.toFilter(context, rNode, right, info);
                right.append(")");
            } else {
                toFilter.toFilter(context, rNode, right, info);
            }
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", rNode.toString()), e);
        }
    }

    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        OperatorNode operatorNode = (OperatorNode)node;
        StringBuilder leftStr = new StringBuilder(16);
        StringBuilder rightStr = new StringBuilder(16);
        this.buildLeftAndRight(context, operatorNode, leftStr, rightStr, info);
        buffer.append(String.format("%s %s %s", leftStr, this.getSign(operatorNode), rightStr));
    }

    public String getSign(OperatorNode operatorNode) {
        return operatorNode.sign();
    }
}

