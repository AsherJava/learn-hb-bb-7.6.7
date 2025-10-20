/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.va.formula.intf.ToJavaScript;

public class DataNodeToJS
implements ToJavaScript {
    @Override
    public void toJavaScript(IASTNode node, StringBuilder buffer, boolean containPts) {
        String dataStr = node.toString();
        if ("TRUE".equals(dataStr) || "FALSE".endsWith(dataStr)) {
            dataStr = dataStr.toLowerCase();
        }
        buffer.append(dataStr);
    }
}

