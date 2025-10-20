/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.FunctionAdapter
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.adapter;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.parser.cell.IExpandable;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionAdapter;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class HRegionLookUpAdapter
extends FunctionAdapter {
    public HRegionLookUpAdapter() throws QuickReportError {
        super(HRegionLookUpAdapter.findHRLookUp());
    }

    private static IFunction findHRLookUp() throws QuickReportError {
        try {
            return FunctionProvider.LOOKUP_PROVIDER.find(null, "HRegionLookUp");
        }
        catch (FunctionException e) {
            throw new QuickReportError(e);
        }
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        for (IASTNode param : parameters) {
            if (!(param instanceof IExpandable)) continue;
            ((IExpandable)param).setColExpanding(true);
        }
        return super.evalute(context, parameters);
    }
}

