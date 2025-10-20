/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.nr.impl.handler.IGcPreProcessingHandler
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.gcreport.inputdata.function;

import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbService;
import com.jiuqi.gcreport.nr.impl.handler.IGcPreProcessingHandler;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SumHbZbFunctionHandler
implements IGcPreProcessingHandler {
    private final SumHbService sumHbService = (SumHbService)SpringContextUtils.getBean(SumHbService.class);

    public void preProcessing(QueryContext queryContext, List<FunctionNode> functions) {
        this.sumHbService.batchCalc(queryContext, this.funcName(), functions);
    }

    public String funcName() {
        return "SUMHBZB";
    }
}

