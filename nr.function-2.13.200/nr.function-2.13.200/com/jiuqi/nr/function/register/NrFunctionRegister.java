/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nvwa.dataextract.DataExtractRequestManager
 *  com.jiuqi.nvwa.dataextract.IDataExtractRequest
 */
package com.jiuqi.nr.function.register;

import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.function.func.extract.CopyFromQuery;
import com.jiuqi.nr.function.func.extract.GetFromQuery;
import com.jiuqi.nr.function.func.extract.GetStrsFromQuery;
import com.jiuqi.nr.function.provider.NrFunctionProvider;
import com.jiuqi.nvwa.dataextract.DataExtractRequestManager;
import com.jiuqi.nvwa.dataextract.IDataExtractRequest;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NrFunctionRegister
implements InitializingBean {
    private static final Logger logger = LogFactory.getLogger(NrFunctionRegister.class);
    @Autowired
    private List<IDataExtractRequest> requests;

    @Override
    public void afterPropertiesSet() throws Exception {
        NrFunctionProvider provider = new NrFunctionProvider();
        Iterator<IFunction> iterator = provider.iterator();
        while (iterator.hasNext()) {
            IFunction func = iterator.next();
            this.addfunction(func);
        }
        for (IDataExtractRequest request : this.requests) {
            DataExtractRequestManager.getInstance().regRequest(request);
        }
        List allRequests = DataExtractRequestManager.getInstance().getAllRequests();
        for (IDataExtractRequest request : allRequests) {
            this.addfunction((IFunction)new GetFromQuery(request.getType(), request.getTitle()));
            this.addfunction((IFunction)new CopyFromQuery(request.getType(), request.getTitle()));
            this.addfunction((IFunction)new GetStrsFromQuery(request.getType(), request.getTitle()));
        }
    }

    private void addfunction(IFunction func) {
        try {
            IFunction sameNameFunc = ReportFunctionProvider.GLOBAL_PROVIDER.find(null, func.name());
            if (sameNameFunc != null) {
                ReportFunctionProvider.GLOBAL_PROVIDER.remove(sameNameFunc);
            }
            ReportFunctionProvider.GLOBAL_PROVIDER.add(func);
        }
        catch (FunctionException e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }
}

