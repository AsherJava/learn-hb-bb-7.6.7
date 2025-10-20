/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.budget.component.config;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.budget.component.func.IsMdTrueCommon;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BudCommonFunctionRegister
implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new IsMdTrueCommon());
    }
}

