/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionAdapter
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.adapter;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionAdapter;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class SumAdapter
extends FunctionAdapter {
    public SumAdapter(String funcName) throws QuickReportError {
        super(FunctionProvider.STAT_PROVIDER, funcName);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Number sumValue = (Number)this.getRawFunction().evalute(context, parameters);
        if (sumValue == null && ((ReportContext)context).isExcelMode()) {
            return 0;
        }
        return sumValue;
    }
}

