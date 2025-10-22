/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.bpm.func;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.bpm.func.GetUploadState;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class NrBPMFunctionRegister
implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetUploadState());
    }
}

