/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

public class VarSYS_SRC_TQRQ
extends AbstractContextVar {
    private static final long serialVersionUID = -4831035251643363088L;

    public VarSYS_SRC_TQRQ() {
        super("SYS_SRC_TQRQ", "\u5f53\u524d\u65f6\u671f\u6700\u540e\u4e00\u5929", 2);
    }

    public Object getVarValue(IContext context) {
        try {
            PeriodWrapper periodWrapper = this.getPeriod(context);
            QueryContext qContext = (QueryContext)context;
            Date[] dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(periodWrapper);
            if (dateRegion == null || dateRegion.length != 2) {
                return null;
            }
            Date periodDate = dateRegion[1];
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(periodDate);
            return calendar;
        }
        catch (ParseException e) {
            return null;
        }
    }

    public void setVarValue(Object value) {
    }
}

