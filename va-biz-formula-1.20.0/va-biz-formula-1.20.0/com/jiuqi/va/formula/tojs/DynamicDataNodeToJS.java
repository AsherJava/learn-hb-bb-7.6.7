/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.formula.intf.ToJavaScript;

public class DynamicDataNodeToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) {
        if (node instanceof TableFieldNode) {
            TableFieldNode modelNode = (TableFieldNode)node;
            buffer.append(DynamicDataNodeToJS.toJavaScript(modelNode.getTableName(), modelNode.getFieldName()));
        } else if (node instanceof FieldNode) {
            String name = ((FieldNode)node).getName();
            buffer.append(String.format("this.GetParamValue('%s')", name));
        }
    }

    public static String toJavaScript(String tableName, String fieldName) {
        return String.format("this.GetValue('%s', '%s')", tableName, fieldName);
    }
}

