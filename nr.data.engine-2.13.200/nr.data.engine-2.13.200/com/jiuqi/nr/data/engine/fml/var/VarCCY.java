/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import java.util.Objects;

public class VarCCY
extends Variable {
    public VarCCY() {
        super("CCY", "\u5e01\u79cd\u53d8\u91cf", 6);
    }

    public Object getVarValue(IContext context) throws Exception {
        QueryContext qContext = (QueryContext)context;
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        if (Objects.nonNull(masterKeys)) {
            return masterKeys.getValue("MD_CURRENCY");
        }
        return null;
    }
}

