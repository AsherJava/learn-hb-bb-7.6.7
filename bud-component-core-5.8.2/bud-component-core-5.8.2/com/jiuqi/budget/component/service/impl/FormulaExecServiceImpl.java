/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.budget.component.service.impl;

import com.jiuqi.budget.component.domain.FormulaExeParam;
import com.jiuqi.budget.component.service.FormulaExecService;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class FormulaExecServiceImpl
implements FormulaExecService {
    private static final Logger logger = LoggerFactory.getLogger(FormulaExecServiceImpl.class);
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    IDataAccessProvider dataAccessProvider;

    @Override
    public boolean getAdaptVal(FormulaExeParam formulaExeParam) {
        Assert.notNull((Object)formulaExeParam.getFormulaExpress(), "\u516c\u5f0f\u8868\u8fbe\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
        AbstractData formulaResult = this.getFormulaExecResult(formulaExeParam);
        if (formulaResult == null || formulaResult.isNull || formulaResult.dataType == -1) {
            logger.error("\u9002\u5e94\u6761\u4ef6\uff1a[{}] \u6267\u884c\u5f02\u5e38\uff0c\u5df2\u8fd4\u56de\u9ed8\u8ba4\u503cfalse", (Object)formulaExeParam.getFormulaExpress());
            return false;
        }
        try {
            return formulaResult.getAsBool();
        }
        catch (DataTypeException e) {
            logger.error("\u9002\u5e94\u6761\u4ef6\uff1a[{}] \u6267\u884c\u5f02\u5e38\uff0c\u5df2\u8fd4\u56de\u9ed8\u8ba4\u503cfalse", (Object)formulaExeParam.getFormulaExpress());
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private AbstractData getFormulaExecResult(FormulaExeParam formulaExeParam) {
        ExecutorContext executorContext = this.createFormulaExecutorContext(formulaExeParam);
        IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
        String formulaExpress = formulaExeParam.getFormulaExpress();
        LocalDateTime startTime = LocalDateTime.now();
        try {
            AbstractData abstractData = expressionEvaluator.eval(formulaExpress, executorContext, executorContext.getVarDimensionValueSet());
            return abstractData;
        }
        catch (ExpressionException e) {
            logger.error("\u516c\u5f0f\u6267\u884c\u5f02\u5e38\uff1a" + formulaExpress + " \u5f02\u5e38\u4fe1\u606f\uff1a" + e.getMessage(), e);
        }
        finally {
            LocalDateTime endTime;
            Duration between;
            if (logger.isDebugEnabled() && (between = Duration.between(startTime, endTime = LocalDateTime.now())).toMillis() > 500L) {
                logger.error("\u516c\u5f0f\u3010{}\u3011\u6267\u884c\u65f6\u95f4\u8fc7\u957f\uff0c\u53ef\u80fd\u5b58\u5728\u6548\u7387\u95ee\u9898\u3002\u6267\u884c\u8017\u65f6\uff1a{}", (Object)formulaExpress, (Object)between);
            }
        }
        return null;
    }

    public ExecutorContext createFormulaExecutorContext(FormulaExeParam formulaExeParam) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        Map<String, String> dimValMap = formulaExeParam.getDimValMap();
        for (Map.Entry<String, String> entry : dimValMap.entrySet()) {
            dimensionValueSet.setValue(entry.getKey(), (Object)entry.getValue());
        }
        executorContext.setVarDimensionValueSet(dimensionValueSet);
        return executorContext;
    }
}

