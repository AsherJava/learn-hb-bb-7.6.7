/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.util.TimeDimUtils
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;

public class VarCUR_TIMEKEY
extends AbstractContextVar {
    private static final long serialVersionUID = 693785127386043403L;

    public VarCUR_TIMEKEY() {
        super("CUR_TIMEKEY", "\u5f53\u524d\u65f6\u671f\u7684timekey", 6);
    }

    public Object getVarValue(IContext context) {
        PeriodWrapper period = this.getPeriod(context);
        if (period == null) {
            return null;
        }
        return TimeDimUtils.periodWrapperToTimeKey((PeriodWrapper)period);
    }

    public void setVarValue(Object value) {
    }
}

