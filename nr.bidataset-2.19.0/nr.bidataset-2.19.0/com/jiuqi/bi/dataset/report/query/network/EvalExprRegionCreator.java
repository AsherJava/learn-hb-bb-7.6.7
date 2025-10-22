/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataRegion
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.executors.ExprExecRegion
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.bi.dataset.report.query.network;

import com.jiuqi.bi.dataset.report.query.network.EvalExprExecRegion;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.query.QueryContext;

public class EvalExprRegionCreator
implements ExprExecRegionCreator {
    private QueryContext qContext;
    private QueryParam queryParam;

    public EvalExprRegionCreator(QueryContext qContext, boolean isCalc, QueryParam queryParam) {
        this.qContext = qContext;
        this.queryParam = queryParam;
    }

    public ExprExecRegion createExecRegion(DimensionSet masterDimensions, DataRegion scope) {
        EvalExprExecRegion region = new EvalExprExecRegion(this.qContext, masterDimensions, scope, this.queryParam);
        return region;
    }
}

