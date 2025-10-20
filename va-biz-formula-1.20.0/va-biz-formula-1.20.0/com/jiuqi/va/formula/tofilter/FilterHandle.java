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
import java.util.Map;

public class FilterHandle {
    public static final String toFilter(IContext context, IASTNode node, Object info) throws ToFilterException {
        Object transforModelNode;
        ToFilter toFilter;
        try {
            toFilter = FilterNodeProvider.get(node.getNodeType());
        }
        catch (ToFilterException e) {
            throw new ToFilterException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node.toString()), e);
        }
        Map param = (Map)info;
        boolean isTransforModelNode = true;
        if (param != null && (transforModelNode = param.get("transforModelNode")) != null) {
            isTransforModelNode = (Boolean)transforModelNode;
        }
        StringBuilder builder = new StringBuilder(64);
        if (isTransforModelNode) {
            builder.append("\"");
        }
        toFilter.toFilter(context, node, builder, info);
        if (isTransforModelNode) {
            builder.append("\"");
        }
        return builder.toString();
    }
}

