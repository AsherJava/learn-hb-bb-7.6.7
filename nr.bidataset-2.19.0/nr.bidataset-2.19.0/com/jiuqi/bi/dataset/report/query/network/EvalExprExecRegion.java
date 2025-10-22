/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.BoolValue
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataRegion
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.executors.FormulaExecRegion
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.bi.dataset.report.query.network;

import com.jiuqi.bi.dataset.report.query.ReportQueryContext;
import com.jiuqi.bi.util.BoolValue;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.executors.FormulaExecRegion;
import com.jiuqi.np.dataengine.query.QueryContext;

public class EvalExprExecRegion
extends FormulaExecRegion {
    public EvalExprExecRegion(QueryContext context, DimensionSet dimensions, DataRegion scope, QueryParam queryParam) {
        super(context, dimensions, scope, queryParam);
    }

    protected boolean executeChildren(Object subTask, BoolValue somethingDone) throws Exception {
        if (this.findStatExecutor() == null) {
            ReportQueryContext qContext = (ReportQueryContext)this.context;
            qContext.rowEval();
            return true;
        }
        return super.executeChildren(subTask, somethingDone);
    }

    protected void resetChildren() throws ExecuteException {
        if (this.findStatExecutor() != null) {
            super.resetChildren();
        }
    }

    protected boolean assignChildren(Object taskInfo) throws ExecuteException {
        if (this.findStatExecutor() != null) {
            return super.assignChildren(taskInfo);
        }
        return true;
    }
}

