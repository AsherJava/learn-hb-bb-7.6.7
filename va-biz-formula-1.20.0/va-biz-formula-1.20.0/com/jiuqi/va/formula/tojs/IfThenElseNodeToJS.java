/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;

public class IfThenElseNodeToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        IfThenElse ifThenElse = (IfThenElse)node;
        if (containPts) {
            buffer.append("(");
        }
        StringBuilder node0Str = new StringBuilder(16);
        IASTNode node0 = ifThenElse.getChild(0);
        ToJavaScript toJavaScript0 = null;
        try {
            toJavaScript0 = JavaScriptNodeProvider.get(node0.getNodeType());
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node0.toString()), e);
        }
        toJavaScript0.toJavaScript(node0, node0Str, FunctionUtils.containPts((OperatorNode)ifThenElse, node0));
        IASTNode node1 = ifThenElse.getChild(1);
        StringBuilder node1Str = new StringBuilder(16);
        ToJavaScript toJavaScript1 = null;
        try {
            toJavaScript1 = JavaScriptNodeProvider.get(node1.getNodeType());
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node1.toString()), e);
        }
        toJavaScript1.toJavaScript(node1, node1Str, FunctionUtils.containPts((OperatorNode)ifThenElse, node1));
        StringBuilder node2Str = null;
        if (ifThenElse.childrenSize() > 2) {
            IASTNode node2 = ifThenElse.getChild(2);
            node2Str = new StringBuilder(16);
            ToJavaScript toJavaScript2 = null;
            try {
                toJavaScript2 = JavaScriptNodeProvider.get(node2.getNodeType());
            }
            catch (ToJavaScriptException e) {
                throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node2.toString()), e);
            }
            toJavaScript2.toJavaScript(node2, node2Str, FunctionUtils.containPts((OperatorNode)ifThenElse, node2));
        }
        buffer.append(String.format("%s ? %s : %s", node0Str, node1Str, node2Str == null ? "undefined" : node2Str));
        if (containPts) {
            buffer.append(")");
        }
    }
}

