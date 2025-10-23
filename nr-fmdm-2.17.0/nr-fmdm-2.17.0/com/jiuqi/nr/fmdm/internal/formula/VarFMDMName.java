/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.fmdm.internal.formula;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;

public class VarFMDMName
extends Variable {
    private static final long serialVersionUID = 6750721255654637363L;

    public VarFMDMName() {
        super("DWMC", "\u5c01\u9762\u4ee3\u7801\u540d\u79f0", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        return qContext.getCache().get("NAME");
    }
}

