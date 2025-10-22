/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser
 */
package com.jiuqi.gcreport.inputdata.function.util;

import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;

public class GcFloatCopyExecutor
extends FloatCopyExecutor {
    public GcFloatCopyExecutor(FloatCopyParaParser parser, IReportFunction function) {
        super(parser, function);
    }

    public boolean execute(QueryContext qContext) throws Exception {
        return super.execute(qContext);
    }
}

