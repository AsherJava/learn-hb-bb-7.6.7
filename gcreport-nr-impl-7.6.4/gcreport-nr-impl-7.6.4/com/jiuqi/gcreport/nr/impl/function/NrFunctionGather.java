/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.np.dataengine.IPreProcessingHandler
 *  com.jiuqi.np.dataengine.PreProcessingHandlerManager
 */
package com.jiuqi.gcreport.nr.impl.function;

import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.nr.impl.handler.IGcPreProcessingHandler;
import com.jiuqi.np.dataengine.IPreProcessingHandler;
import com.jiuqi.np.dataengine.PreProcessingHandlerManager;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrFunctionGather
implements InitializingBean {
    @Autowired(required=false)
    private List<INrFunction> gcFunctions;
    @Autowired(required=false)
    private List<IGcPreProcessingHandler> gcPreProcessingHandlers;

    @Override
    public void afterPropertiesSet() {
        if (!CollectionUtils.isEmpty(this.gcFunctions)) {
            this.gcFunctions.stream().forEach(gcFunction -> ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)gcFunction));
        }
        if (!CollectionUtils.isEmpty(this.gcPreProcessingHandlers)) {
            this.gcPreProcessingHandlers.stream().forEach(preProcessingHandler -> PreProcessingHandlerManager.getInstance().regHandler((IPreProcessingHandler)preProcessingHandler));
        }
    }
}

