/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ToFilter;
import com.jiuqi.va.formula.provider.FilterNodeProvider;

public class IfThenElseNodeToFilter
implements ToFilter {
    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        IfThenElse ifThenElse = (IfThenElse)node;
        buffer.append("IF ");
        IASTNode node0 = ifThenElse.getChild(0);
        ToFilter toFilter0 = null;
        try {
            toFilter0 = FilterNodeProvider.get(node0.getNodeType());
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node0.toString()), e);
        }
        toFilter0.toFilter(context, node0, buffer, info);
        IASTNode node1 = ifThenElse.getChild(1);
        buffer.append(" THEN ");
        ToFilter toFilter1 = null;
        try {
            toFilter1 = FilterNodeProvider.get(node1.getNodeType());
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node1.toString()), e);
        }
        toFilter1.toFilter(context, node1, buffer, info);
        if (ifThenElse.childrenSize() > 2) {
            IASTNode node2 = ifThenElse.getChild(2);
            buffer.append(" ELSE ");
            ToFilter toFilter2 = null;
            try {
                toFilter2 = FilterNodeProvider.get(node2.getNodeType());
            }
            catch (ToFilterException e) {
                throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node2.toString()), e);
            }
            toFilter2.toFilter(context, node2, buffer, info);
        }
    }
}

