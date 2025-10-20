/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import java.util.List;

public abstract class AggrFunction
extends DSFunction {
    private static final long serialVersionUID = 2448082377026159292L;

    public abstract List<AggrMeasureItem> getAggrMeasureItem(DSFormulaContext var1, List<IASTNode> var2) throws SyntaxException;

    public abstract Object evalute(DSFormulaContext var1, BIDataSetImpl var2, StatInfo var3, List<IASTNode> var4) throws SyntaxException;
}

