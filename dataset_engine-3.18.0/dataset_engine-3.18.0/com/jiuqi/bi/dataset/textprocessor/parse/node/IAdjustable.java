/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.bi.dataset.textprocessor.parse.node;

import com.jiuqi.bi.syntax.ast.IASTNode;

public interface IAdjustable {
    public boolean isAdjustable(String var1);

    public IASTNode adjust();
}

