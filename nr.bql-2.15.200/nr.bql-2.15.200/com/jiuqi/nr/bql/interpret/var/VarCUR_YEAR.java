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

public class VarCUR_YEAR
extends BiAdaptVariable {
    private static final long serialVersionUID = 6518491888653473469L;

    public VarCUR_YEAR() {
        super("CUR_YEAR", "\u5f53\u524d\u5e74\u5ea6", 6);
    }

    public VarCUR_YEAR(String varName, String varTitle, int dataType) {
        super(varName, varTitle, dataType);
    }

    @Override
    public void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("str(").append(this.getPeriodTableName(context)).append(".").append(PeriodTableColumn.YEAR.getCode()).append(")");
    }
}

