/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 */
package com.jiuqi.nr.bql.interpret.var;

import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.bql.interpret.var.BiAdaptVariable;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;

public class VarCUR_TIMEKEY
extends BiAdaptVariable {
    private static final long serialVersionUID = -5767641218949009018L;

    public VarCUR_TIMEKEY() {
        super("CUR_TIMEKEY", "\u5f53\u524d\u65f6\u671f\u7684timekey", 6);
    }

    @Override
    public void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.getPeriodTableName(context)).append(".").append(PeriodTableColumn.TIMEKEY.getCode());
    }
}

