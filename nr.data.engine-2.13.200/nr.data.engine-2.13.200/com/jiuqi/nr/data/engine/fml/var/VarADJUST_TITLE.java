/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.data.engine.fml.var;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.nr.data.engine.fml.var.AbstractContextVar;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;

public class VarADJUST_TITLE
extends AbstractContextVar {
    private static final long serialVersionUID = 7731609925281593690L;

    public VarADJUST_TITLE() {
        super("ADJUST_TITLE", "\u5f53\u524d\u8c03\u6574\u671f\u6807\u9898", 6);
    }

    public Object getVarValue(IContext context) {
        ReportFmlExecEnvironment env;
        IAdjustPeriodService adjustPeriodService;
        AdjustPeriod adjustPeriod;
        QueryContext qContext = (QueryContext)context;
        String adustCode = (String)qContext.getDimensionValue("ADJUST");
        if (qContext.getExeContext().getEnv() != null && (adjustPeriod = (adjustPeriodService = (IAdjustPeriodService)SpringBeanProvider.getBean(IAdjustPeriodService.class)).queryAdjustPeriods((env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv()).getDataScehmeKey(), qContext.getPeriodWrapper().toString(), adustCode)) != null) {
            return adjustPeriod.getTitle();
        }
        return "\u4e0d\u8c03\u6574";
    }

    public void setVarValue(Object value) {
    }
}

