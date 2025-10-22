/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.executors.EvalExecutor
 *  com.jiuqi.np.dataengine.executors.ExecutorBase
 *  com.jiuqi.np.dataengine.executors.ExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExprExecRegion
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.bi.dataset.report.query.network;

import com.jiuqi.bi.dataset.report.query.network.EvalExprExecRegion;
import com.jiuqi.bi.dataset.report.query.network.EvalExprRegionCreator;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.query.QueryContext;

public class EvalExprExecNetwork
extends ExprExecNetwork {
    private EvalExecutor evalExecutor;

    public EvalExprExecNetwork(QueryContext context, EvalExprRegionCreator exprExecCreator) {
        super(context, (ExprExecRegionCreator)exprExecCreator);
    }

    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        boolean result = super.doInitialization(initInfo);
        if (this.size() > 1) {
            this.combineExecCenters();
        }
        for (int i = 0; i < this.size(); ++i) {
            EvalExprExecRegion execRegion = (EvalExprExecRegion)this.getCenter(i);
            if (execRegion.findStatExecutor() != null) continue;
            this.evalExecutor = execRegion.findEvalExecutor();
            if (this.evalExecutor != null) break;
        }
        return result;
    }

    public void combineExecCenters() {
        for (int li = this.size() - 1; li >= 0; --li) {
            EvalExprExecRegion largeCenter = (EvalExprExecRegion)((Object)this.scopeList.getValue(li));
            for (int i = li - 1; i >= 0; --i) {
                EvalExprExecRegion center = (EvalExprExecRegion)((Object)this.scopeList.getValue(i));
                if (largeCenter.findStatExecutor() != null || center.findStatExecutor() != null) continue;
                largeCenter.combine((ExprExecRegion)center);
                for (int c = 0; c < this.executors.size(); ++c) {
                    ExecutorBase executor = (ExecutorBase)this.executors.get(c);
                    executor.replacePrecursor((ExecutorBase)center, (ExecutorBase)largeCenter);
                }
                this.remove((ExecutorBase)center);
                this.scopeList.remove(i);
                --li;
            }
        }
    }

    public EvalExecutor getEvalExecutor() {
        return this.evalExecutor;
    }
}

