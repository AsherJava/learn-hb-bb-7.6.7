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
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.tojs.UnaryOperatorToJS;

public class NotNodeToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        UnaryOperatorToJS.toJavaScript((OperatorNode)node, buffer, "!", containPts);
    }
}

