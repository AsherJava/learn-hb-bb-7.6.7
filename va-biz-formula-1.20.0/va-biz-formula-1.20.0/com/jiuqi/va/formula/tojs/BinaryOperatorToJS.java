/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;

public class BinaryOperatorToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        OperatorNode operatorNode = (OperatorNode)node;
        if (containPts) {
            buffer.append("(");
        }
        StringBuilder leftStr = new StringBuilder(16);
        StringBuilder rightStr = new StringBuilder(16);
        this.buildLeftAndRight(operatorNode, leftStr, rightStr);
        leftStr.insert(0, "(").append(")");
        rightStr.insert(0, "(").append(")");
        buffer.append(String.format("%s %s %s", leftStr, this.getSign(operatorNode), rightStr));
        if (containPts) {
            buffer.append(")");
        }
    }

    public void buildLeftAndRight(OperatorNode node, StringBuilder left, StringBuilder right) throws ToJavaScriptException {
        IASTNode lNode = node.getChild(0);
        try {
            ToJavaScript toJavaScript = JavaScriptNodeProvider.get(lNode.getNodeType());
            toJavaScript.toJavaScript(lNode, left, FunctionUtils.containPts(node, lNode));
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", lNode.toString()), e);
        }
        IASTNode rNode = node.getChild(1);
        try {
            ToJavaScript rightToJavaScript = JavaScriptNodeProvider.get(rNode.getNodeType());
            rightToJavaScript.toJavaScript(rNode, right, FunctionUtils.containPts(node, rNode));
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", rNode.toString()), e);
        }
    }

    public String getSign(OperatorNode operatorNode) {
        return operatorNode.sign();
    }
}

