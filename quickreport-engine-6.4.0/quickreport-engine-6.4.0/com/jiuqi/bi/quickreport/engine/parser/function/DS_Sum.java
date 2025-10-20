/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.quickreport.engine.context.ReportContext;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public final class DS_Sum
extends DataSetFunction {
    private static final long serialVersionUID = 1L;

    public DS_Sum() {
        this.parameters().add(new Parameter("dataset", 5050, "\u6570\u636e\u96c6"));
        this.parameters().add(new Parameter("field", 3, "\u6570\u636e\u96c6\u5b57\u6bb5"));
        this.parameters().add(new Parameter("filter", 0, "\u8fc7\u6ee4\u6761\u4ef6", true));
    }

    public String name() {
        return "DS_SUM";
    }

    public String title() {
        return "\u5bf9\u6570\u636e\u96c6\u5b57\u6bb5\u6c42\u548c";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        Double result;
        List<IASTNode> restrictions = parameters.subList(2, parameters.size());
        BIDataSet dataSet = this.openDataSet(context, parameters.get(0), parameters.get(1), restrictions);
        int colIndex = DS_Sum.findColumn(context, dataSet, parameters.get(1));
        try {
            result = dataSet.sum(colIndex);
        }
        catch (BIDataSetException e) {
            throw new SyntaxException((Throwable)e);
        }
        this.evalTracer((ReportContext)context, parameters.get(0), parameters.get(1), restrictions, result);
        return result;
    }
}

