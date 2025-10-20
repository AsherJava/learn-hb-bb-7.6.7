/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.service.impl;

import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.gcreport.inputdata.function.sumhb.SumHbBatchProcessor;
import com.jiuqi.gcreport.inputdata.function.sumhb.service.SumHbService;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class SumHbServiceImpl
implements SumHbService {
    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchCalc(QueryContext queryContext, String funcName, List<FunctionNode> functions) {
        if (CollectionUtils.isEmpty(functions) || functions.size() == 1) {
            return;
        }
        SumHbBatchProcessor.newInstance(queryContext, funcName, functions).batchCalc();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchFilterCalc(QueryContext queryContext, String funcName, List<FunctionNode> functions) {
        if (CollectionUtils.isEmpty(functions) || functions.size() == 1) {
            return;
        }
        SumHbBatchProcessor.newInstance(queryContext, funcName, functions).batchCalc();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void sumHbZbFunctionCalc(QueryContext queryContext, String funcName, List<FunctionNode> functions) {
        SumHbBatchProcessor.newInstance(queryContext, funcName, functions).batchCalc();
    }
}

