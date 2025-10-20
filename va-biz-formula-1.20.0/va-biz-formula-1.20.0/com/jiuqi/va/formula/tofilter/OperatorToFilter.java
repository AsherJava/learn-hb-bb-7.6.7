/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ToFilter;
import com.jiuqi.va.formula.provider.FilterNodeProvider;

public class OperatorToFilter
implements ToFilter {
    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        ToFilter toFilter = null;
        try {
            toFilter = FilterNodeProvider.get(node.getNodeType(), node.getClass());
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node.toString()), e);
        }
        toFilter.toFilter(context, node, buffer, info);
    }
}

