/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;

public class UnaryOperatorToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        OperatorNode operatorNode = (OperatorNode)node;
        UnaryOperatorToJS.toJavaScript(operatorNode, buffer, operatorNode.sign(), containPts);
    }

    public static final void toJavaScript(OperatorNode node, StringBuilder buffer, String sign, boolean containPts) throws ToJavaScriptException {
        ToJavaScript rightToJavaScript;
        if (containPts) {
            buffer.append("(");
        }
        buffer.append(" ").append(sign);
        IASTNode right = node.getChild(0);
        try {
            rightToJavaScript = JavaScriptNodeProvider.get(right.getNodeType());
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", right.toString()), e);
        }
        rightToJavaScript.toJavaScript(right, buffer, right.getNodeType().equals((Object)ASTNodeType.OPERATOR));
        if (containPts) {
            buffer.append(")");
        }
    }
}

