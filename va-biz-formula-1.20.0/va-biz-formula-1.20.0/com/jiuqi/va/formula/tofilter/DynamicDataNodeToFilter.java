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
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.formula.intf.ToFilter;
import java.util.Map;

public class DynamicDataNodeToFilter
implements ToFilter {
    @Override
    public void toFilter(IContext context, IASTNode node, StringBuilder buffer, Object info) {
        if (node instanceof TableFieldNode) {
            Object transforModelNode;
            Map param = (Map)info;
            boolean isTransforModelNode = true;
            if (param != null && (transforModelNode = param.get("transforModelNode")) != null) {
                isTransforModelNode = (Boolean)transforModelNode;
            }
            if (isTransforModelNode) {
                TableFieldNode modelNode = (TableFieldNode)node;
                if (modelNode.isMultiChoice()) {
                    buffer.append(String.format("\" + this.GetMultiText(\"%s\", \"%s\") + \"", modelNode.getTableName(), modelNode.getFieldName()));
                } else {
                    buffer.append(String.format("\" + this.GetText(\"%s\", \"%s\") + \"", modelNode.getTableName(), modelNode.getFieldName()));
                }
            } else {
                node.toString(buffer);
            }
        } else if (node instanceof FieldNode) {
            node.toString(buffer);
        }
    }
}

