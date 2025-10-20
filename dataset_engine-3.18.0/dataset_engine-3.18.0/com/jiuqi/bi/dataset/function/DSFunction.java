/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.format.IFormatable
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.format.IFormatable;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public abstract class DSFunction
extends Function
implements IFormatable {
    private static final long serialVersionUID = 3593391533432247654L;

    public String category() {
        return "\u6570\u636e\u96c6\u51fd\u6570";
    }

    public boolean isInfiniteParameter() {
        return true;
    }

    public IDataFormator getDataFormator(IContext context) {
        return null;
    }

    public abstract Object evalute(IContext var1, List<IASTNode> var2, BIDataSet var3) throws SyntaxException;

    public boolean isNeedOptimize() {
        return true;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        BIDataSetImpl dataset = null;
        if (context instanceof DSFormulaContext) {
            dataset = ((DSFormulaContext)context).getDataSet();
        }
        return this.evalute(context, parameters, dataset);
    }

    public List<IASTNode> getFilterItems(List<IASTNode> params) {
        IParameter pm;
        int size = this.parameters().size();
        if (size > 0 && (pm = (IParameter)this.parameters().get(size - 1)).isOmitable() && pm.dataType() == 1 && params.size() >= size) {
            return new ArrayList<IASTNode>(params.subList(size - 1, params.size()));
        }
        return new ArrayList<IASTNode>(0);
    }
}

