/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExternalExecHandler;
import com.jiuqi.np.dataengine.query.QueryContext;

public class ExternalExecutor
extends ExecutorBase {
    private ExternalExecHandler _preparation;
    private ExternalExecHandler _execution;

    public ExternalExecutor(QueryContext context, ExternalExecHandler preparation, ExternalExecHandler execution) {
        super(context);
        this._preparation = preparation;
        this._execution = execution;
    }

    @Override
    protected void doPreparation(Object taskInfo) throws Exception {
        if (this.state == 2 && this._preparation != null) {
            this._preparation.invoke(this.context, taskInfo);
        }
        super.doPreparation(taskInfo);
    }

    @Override
    protected boolean doExecution(Object taskInfo) {
        if (this._execution != null) {
            this._execution.invoke(this.context, taskInfo);
        }
        return true;
    }
}

