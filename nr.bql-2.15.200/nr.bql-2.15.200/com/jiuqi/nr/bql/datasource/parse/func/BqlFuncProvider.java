/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.bql.datasource.parse.func;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.bql.datasource.parse.func.GetEntityField;
import com.jiuqi.nr.bql.datasource.parse.func.GetPeriodField;

public class BqlFuncProvider
extends ReportFunctionProvider {
    public BqlFuncProvider() {
        this.add((IFunction)new GetPeriodField());
        this.add((IFunction)new GetEntityField());
    }
}

