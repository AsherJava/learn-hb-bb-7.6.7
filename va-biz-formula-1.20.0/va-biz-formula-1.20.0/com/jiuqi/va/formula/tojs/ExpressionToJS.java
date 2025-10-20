/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;
import com.jiuqi.va.formula.intf.ToJavaScript;
import com.jiuqi.va.formula.provider.JavaScriptNodeProvider;

public class ExpressionToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) throws ToJavaScriptException {
        ToJavaScript toJavaScript = null;
        try {
            toJavaScript = JavaScriptNodeProvider.get(node.getChild(0).getNodeType(), node.getChild(0).getClass());
        }
        catch (ToJavaScriptException e) {
            throw new ToJavaScriptException(String.format("\u4e0d\u652f\u6301\u5f53\u524d\u8bed\u6cd5\u3010%s\u3011", node.toString()), e);
        }
        toJavaScript.toJavaScript(node.getChild(0), buffer, containPts);
    }
}

