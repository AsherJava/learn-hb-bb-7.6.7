/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;

public class VarYF
extends Variable {
    private static final long serialVersionUID = -6355792877256949800L;

    public VarYF() {
        super("YF", "\u6708\u4efd", 6);
    }

    public Object getVarValue(IContext context) {
        INvwaSystemOptionService nvwaSystemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
        int valueLength = "1".equals(nvwaSystemOptionService.findValueById("@nr/logic/compatibility-mode")) && "1".equals(nvwaSystemOptionService.findValueById("@nr/var/yf-format")) ? 2 : 4;
        QueryContext qContext = (QueryContext)context;
        PeriodWrapper period = qContext.getPeriodWrapper();
        if (period == null || period.getType() != 4) {
            return null;
        }
        String periodStr = period.toString();
        return periodStr.substring(periodStr.length() - valueLength, periodStr.length());
    }

    public void setVarValue(Object value) {
    }
}

