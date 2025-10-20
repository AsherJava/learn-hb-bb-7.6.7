/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.textprocessor.parse.func.basic;

import com.jiuqi.bi.dataset.function.Top;
import com.jiuqi.bi.dataset.textprocessor.parse.func.basic.BasicDSFunction;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.List;

public class DS_Top
extends BasicDSFunction {
    private static final long serialVersionUID = 9217541146608382628L;

    public DS_Top() {
        super(new Top());
    }

    @Override
    protected List<IASTNode> getFilterParamList(IContext context, List<IASTNode> parameters) {
        return new ArrayList<IASTNode>(parameters.subList(3, parameters.size()));
    }

    @Override
    protected boolean isAutoAggr() {
        return false;
    }
}

