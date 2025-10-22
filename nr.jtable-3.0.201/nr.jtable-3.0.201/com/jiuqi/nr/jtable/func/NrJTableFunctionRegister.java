/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.jtable.func;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.jtable.func.GetIncSortedName;
import com.jiuqi.nr.jtable.func.GetIncSortedValue;
import com.jiuqi.nr.jtable.func.GetRank;
import com.jiuqi.nr.jtable.func.GetSortedName;
import com.jiuqi.nr.jtable.func.GetSortedValue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NrJTableFunctionRegister
implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetIncSortedName());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetIncSortedValue());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetRank());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetSortedName());
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)new GetSortedValue());
    }
}

