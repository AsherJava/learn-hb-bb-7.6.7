/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;

public class VarAdjust
extends Variable {
    private static final long serialVersionUID = -6198540494237809248L;

    public VarAdjust() {
        super("ADJUST", "\u8c03\u6574\u671f", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        return qContext.getDimensionValue("ADJUST");
    }
}

