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
import com.jiuqi.va.formula.tojs.BinaryOperatorToJS;

public class InNodeToJS
extends BinaryOperatorToJS {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        OperatorNode operatorNode = (OperatorNode)node;
        StringBuilder leftStr = new StringBuilder(16);
        StringBuilder rightStr = new StringBuilder(16);
        this.buildLeftAndRight(operatorNode, leftStr, rightStr);
        if (containPts) {
            buffer.append("(");
        }
        if (rightStr.length() > 2 && rightStr.charAt(0) == '{') {
            rightStr.replace(0, 1, "[");
            rightStr.replace(rightStr.length() - 1, rightStr.length(), "]");
        }
        buffer.append(String.format("this.inOperator(%s, %s)", rightStr, leftStr));
        if (containPts) {
            buffer.append(")");
        }
    }
}

