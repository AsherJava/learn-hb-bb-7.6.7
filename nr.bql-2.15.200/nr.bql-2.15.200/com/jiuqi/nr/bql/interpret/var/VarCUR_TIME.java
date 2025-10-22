/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.bql.interpret.var;

import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.bql.interpret.var.BiAdaptVariable;

public class VarCUR_TIME
extends BiAdaptVariable {
    private static final long serialVersionUID = -9030841708267435524L;

    public VarCUR_TIME() {
        super("CUR_TIME", "\u5f53\u524d\u65f6\u671f", 3);
    }

    @Override
    public void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.getPeriodTableName(context)).append(".").append(this.getPeriodTypeField(context));
    }
}

