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

public class Group
extends DSFunction {
    private static final long serialVersionUID = 1L;

    public Group() {
        this.parameters().add(new Parameter("field", 0, "\u4fdd\u7559\u5b57\u6bb5"));
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes, BIDataSet filterDs) throws SyntaxException {
        return null;
    }

    @Override
    public boolean isNeedOptimize() {
        return false;
    }

    @Override
    public Object evalute(IContext context, List<IASTNode> paramNodes) throws SyntaxException {
        try {
            DSFormulaContext dsFormulaContext = (DSFormulaContext)context;
            BIDataSetImpl dataSet = dsFormulaContext.getDataSet();
            ArrayList<String> dimList = new ArrayList<String>();
            dimList.add(paramNodes.get(0).toString());
            int size = paramNodes.size();
            for (int i = 1; i < size; ++i) {
                dimList.add(paramNodes.get(i).toString());
            }
            return dataSet.aggregate(dimList.toArray(new String[dimList.size()]));
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
    }

    public int getResultType(IContext arg0, List<IASTNode> arg1) throws SyntaxException {
        return 5100;
    }

    public String name() {
        return "DS_GROUP";
    }

    public String title() {
        return "\u6d88\u7ef4\uff0c\u4fdd\u7559\u6307\u5b9a\u5b57\u6bb5\uff0c\u8fd4\u56de\u6d88\u7ef4\u540e\u7684\u7ed3\u679c\u96c6";
    }
}

