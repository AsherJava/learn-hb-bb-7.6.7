/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.dataentry.func;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.dataentry.func.GetEntityGroupCode;
import com.jiuqi.nr.dataentry.func.GetSecretLevel;
import com.jiuqi.nr.dataentry.func.GetUserSecretLevel;
import com.jiuqi.nr.dataentry.func.SwitchEnumShow;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value=false)
@Component
public class NrDataentryFunctionRegister
implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetSecretLevel());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetEntityGroupCode());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetUserSecretLevel());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new SwitchEnumShow());
    }
}

