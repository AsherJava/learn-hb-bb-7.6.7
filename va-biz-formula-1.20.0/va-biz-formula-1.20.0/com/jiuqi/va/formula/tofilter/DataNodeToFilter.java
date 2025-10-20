/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.intf.ToFilter;
import java.util.Map;

public class DataNodeToFilter
implements ToFilter {
    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) throws ToFilterException {
        try {
            Object transforModelNode;
            Map param = (Map)info;
            boolean isTransforModelNode = true;
            if (param != null && (transforModelNode = param.get("transforModelNode")) != null) {
                isTransforModelNode = (Boolean)transforModelNode;
            }
            if (6 == node.getType(context)) {
                if (isTransforModelNode) {
                    buffer.append(String.format("\\\"%s\\\"", String.valueOf(node.evaluate(context)).replace("\\", "\\\\\\\\")));
                } else {
                    buffer.append(String.format("\"%s\"", String.valueOf(node.evaluate(context)).replace("\\", "\\\\")));
                }
            } else {
                buffer.append(node.evaluate(context));
            }
        }
        catch (SyntaxException e) {
            throw new ToFilterException(String.format("\u5e38\u91cf%s\u8f6c\u6362\u5f02\u5e38", node.toString()), e);
        }
    }
}

