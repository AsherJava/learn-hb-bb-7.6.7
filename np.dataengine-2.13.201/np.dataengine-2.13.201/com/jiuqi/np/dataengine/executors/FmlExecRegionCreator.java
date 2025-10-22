/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FormulaExecRegion;
import com.jiuqi.np.dataengine.query.QueryContext;

public class FmlExecRegionCreator
implements ExprExecRegionCreator {
    private QueryContext qContext;
    private QueryParam queryParam;

    public FmlExecRegionCreator(QueryContext qContext, QueryParam queryParam) {
        this.qContext = qContext;
        this.queryParam = queryParam;
    }

    @Deprecated
    public FmlExecRegionCreator(QueryContext qContext, boolean isCalc, QueryParam queryParam) {
        this(qContext, queryParam);
    }

    @Override
    public ExprExecRegion createExecRegion(DimensionSet masterDimensions, DataRegion scope) {
        FormulaExecRegion region = new FormulaExecRegion(this.qContext, masterDimensions, scope, this.queryParam);
        return region;
    }
}

