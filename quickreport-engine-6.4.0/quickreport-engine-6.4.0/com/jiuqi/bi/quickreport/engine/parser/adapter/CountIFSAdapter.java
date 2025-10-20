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

import com.jiuqi.bi.quickreport.engine.parser.cell.IExpandable;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.FunctionAdapter;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class CountIFSAdapter
extends FunctionAdapter {
    public CountIFSAdapter() {
        super(FunctionProvider.STAT_PROVIDER, "CountIFS");
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        for (int i = 0; i < parameters.size(); i += 2) {
            IASTNode param = parameters.get(i);
            if (!(param instanceof IExpandable)) continue;
            ((IExpandable)param).setColExpanding(true);
            ((IExpandable)param).setRowExpanding(true);
        }
        return super.evalute(context, parameters);
    }
}

