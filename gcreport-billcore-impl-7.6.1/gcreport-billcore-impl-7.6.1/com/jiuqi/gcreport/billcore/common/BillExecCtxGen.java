/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;

public class BillExecCtxGen {
    public static ExecutorContext createExecutorContext(String groupName) {
        GcReportExceutorContext context = new GcReportExceutorContext();
        try {
            context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u6267\u884c\u73af\u5883\u51c6\u5907\u51fa\u9519\u3002", (Throwable)e);
        }
        context.setDefaultGroupName(groupName);
        return context;
    }
}

