/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.jtable.filter;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IFormulaFilter {
    public List<IParsedExpression> filterParsedExpression(JtableContext var1, List<IParsedExpression> var2);
}

