/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarYF
extends AbstractContextVar {
    private static final long serialVersionUID = -6355792877256949800L;

    public VarYF() {
        super("YF", "\u6708\u4efd", 6);
    }

    public Object getVarValue(IContext context) {
        PeriodWrapper period = this.getPeriod(context);
        if (period == null || period.getType() != 4) {
            return null;
        }
        String periodStr = period.toString();
        String yf = periodStr.substring(periodStr.length() - 4, periodStr.length());
        return yf;
    }

    public void setVarValue(Object value) {
    }
}

