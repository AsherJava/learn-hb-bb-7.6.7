/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class Filter
extends DSFunction {
    private static final long serialVersionUID = 1L;

    public Filter() {
        this.parameters().add(new Parameter("filterExpr", 1, "\u8fc7\u6ee4\u8868\u8fbe\u5f0f", false));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        try {
            DSFormulaContext dsFormulaContext = (DSFormulaContext)context;
            BIDataSetImpl dataSet = dsFormulaContext.getDataSet();
            if (paramNodes.size() > 0) {
                ArrayList<IASTNode> filterNodes = new ArrayList<IASTNode>(paramNodes);
                dataSet = (BIDataSetImpl)dataSet.doFilter(filterNodes, dsFormulaContext);
            }
            return dataSet;
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public boolean isNeedOptimize() {
        return false;
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 5100;
    }

    public String name() {
        return "DS_FILTER";
    }

    public String title() {
        return "\u901a\u8fc7\u8fc7\u6ee4\u8868\u8fbe\u5f0f\u5bf9\u6570\u636e\u96c6\u8fdb\u884c\u8fc7\u6ee4\uff0c\u83b7\u53d6\u8fc7\u6ee4\u540e\u7684\u7ed3\u679c\u96c6";
    }
}

