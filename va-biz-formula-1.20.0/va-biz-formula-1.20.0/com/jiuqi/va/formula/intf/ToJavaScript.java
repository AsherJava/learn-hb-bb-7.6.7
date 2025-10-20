/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.va.formula.intf;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.va.formula.common.exception.ToJavaScriptException;

public interface ToJavaScript {
    public void toJavaScript(IASTNode var1, StringBuilder var2, boolean var3) throws ToJavaScriptException;
}

