/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.data.AbstractData;

public class EvalItem {
    private AbstractData reault;
    private IASTNode expression;

    public EvalItem(IASTNode expression) {
        this.expression = expression;
    }

    public AbstractData getReault() {
        return this.reault;
    }

    public void setReault(AbstractData reault) {
        this.reault = reault;
    }

    public IASTNode getExpression() {
        return this.expression;
    }

    public void setExpression(IASTNode expression) {
        this.expression = expression;
    }
}

