/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.facade.param.input.FormulaFilterEnv;
import java.util.List;

public interface IFormulaFilter {
    public List<IParsedExpression> filterParsedExpression(FormulaFilterEnv var1, List<IParsedExpression> var2);
}

