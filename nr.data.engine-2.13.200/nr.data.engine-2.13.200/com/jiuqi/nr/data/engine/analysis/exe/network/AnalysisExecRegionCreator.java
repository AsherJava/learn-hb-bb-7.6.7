/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataRegion
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.executors.ExprExecRegion
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.analysis.exe.network;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.analysis.exe.network.AnalysisExprExecRegion;

public class AnalysisExecRegionCreator {
    private QueryContext qContext;
    private QueryParam queryParam;

    public AnalysisExecRegionCreator(QueryContext qContext, QueryParam queryParam) {
        this.qContext = qContext;
        this.queryParam = queryParam;
    }

    public ExprExecRegion createExecRegion(DimensionSet masterDimensions, DataRegion scope) {
        ExprExecRegion result = null;
        return result;
    }

    public AnalysisExprExecRegion createExecRegion(DimensionSet masterDimensions, DimensionSet loopDimensions) {
        AnalysisExprExecRegion region = new AnalysisExprExecRegion(this.qContext, masterDimensions, loopDimensions, this.queryParam);
        return region;
    }
}

