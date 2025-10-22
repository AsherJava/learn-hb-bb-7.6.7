/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;

public class FormulaRunContext
implements IContext {
    private final ExecutorContext executorContext;

    public FormulaRunContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }
}

