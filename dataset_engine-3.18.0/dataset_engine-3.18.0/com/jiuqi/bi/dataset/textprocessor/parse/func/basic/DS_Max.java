/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.function.Max;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class DS_Max
extends BasicDSFunction {
    private static final long serialVersionUID = -2294720721862549898L;

    public DS_Max() {
        super(new Max());
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(2, parameters.size()));
    }

    @Override
    protected boolean isTreatALLRstAsAggr() {
        return false;
    }
}

