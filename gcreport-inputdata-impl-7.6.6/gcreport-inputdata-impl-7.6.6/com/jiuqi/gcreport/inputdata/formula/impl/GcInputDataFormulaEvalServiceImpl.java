/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.common.cache.formula.IFunctionCache
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.util.ReflectionUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.inputdata.formula.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.common.cache.formula.IFunctionCache;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.util.ReflectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.formula.GcInputDataFormulaEvalService;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataExceutorContext;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataRow;
import com.jiuqi.gcreport.inputdata.formula.GcReportInputDataSet;
import com.jiuqi.gcreport.inputdata.inputdata.entity.InputDataEO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcInputDataFormulaEvalServiceImpl
implements GcInputDataFormulaEvalService,
IFunctionCache {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;
    @Autowired
    private InputDataNameProvider inputDataNameProvider;
    private static ThreadLocal<Map<String, GcReportInputDataExceutorContext>> tableName2ContextThreadLocal = new ThreadLocal();

    @Override
    public boolean checkMxInputData(@NotNull DimensionValueSet ds, String formula, @NotNull InputDataEO inputData) {
        return this.getBooleanValueByInputData(inputData, ds, formula);
    }

    @Override
    public boolean checkSumInputData(@NotNull DimensionValueSet ds, String formula, @NotNull List<InputDataEO> inputDatas) {
        return this.getBooleanValueByInputDatas(inputDatas, ds, formula);
    }

    @Override
    public double evaluateMxInputData(@NotNull DimensionValueSet ds, String formula, @NotNull InputDataEO inputData, List<InputDataEO> inputDatas, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        return GcAbstractData.getDoubleValue((AbstractData)this.evaluateDoubleData(ds, formula, inputData, inputDatas, offSetVchrItems));
    }

    @Override
    public AbstractData evaluateMxInputAbstractData(@NotNull DimensionValueSet ds, String formula, @NotNull InputDataEO inputData, List<InputDataEO> inputDatas) {
        return this.evaluateDoubleData(ds, formula, inputData, inputDatas, null);
    }

    @Override
    public double evaluateSumInputData(@NotNull DimensionValueSet ds, String formula, @NotNull List<InputDataEO> inputDatas, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        return GcAbstractData.getDoubleValue((AbstractData)this.evaluateDoubleDataByInputDatas(ds, formula, inputDatas, offSetVchrItems));
    }

    @Override
    public AbstractData evaluateSumInputAbstractData(@NotNull DimensionValueSet ds, String formula, @NotNull List<InputDataEO> inputDatas, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        return this.evaluateDoubleDataByInputDatas(ds, formula, inputDatas, offSetVchrItems);
    }

    /*
     * Could not resolve type clashes
     * Loose catch block
     */
    @Override
    public double evaluateInputDataPHS(@NotNull DimensionValueSet ds, String formula, @NotNull List<InputDataEO> inputDatas, @NotNull double phsValue) {
        GcReportInputDataExceutorContext context = this.getExceutorContext(inputDatas.get(0));
        GcReportInputDataSet dataSet = context.getDataSet();
        if (inputDatas != null) {
            for (DefaultTableEntity data : inputDatas) {
                GcReportInputDataRow row = dataSet.add();
                row.setData(data);
            }
        }
        context.setInputDatas(inputDatas);
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setPhsValue(phsValue);
            evaluator.prepare((ExecutorContext)context, ds, formula);
            AbstractData data = evaluator.evaluate(null);
            double d = GcAbstractData.getDoubleValue((AbstractData)data);
            return d;
        }
        {
            catch (Exception e) {
                throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public String evaluateInputDataSubjectAllocation(DimensionValueSet ds, String formula, List<InputDataEO> inputDatas) {
        GcReportInputDataExceutorContext context = this.getExceutorContext(inputDatas.get(0));
        GcReportInputDataSet dataSet = context.getDataSet();
        if (inputDatas != null) {
            for (DefaultTableEntity defaultTableEntity : inputDatas) {
                GcReportInputDataRow row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        context.setInputDatas(inputDatas);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
                evaluator.prepare((ExecutorContext)context, ds, formula);
                AbstractData data = evaluator.evaluate(null);
                String string = GcAbstractData.getStringValue((AbstractData)data);
                return string;
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    public void enableCache() {
        tableName2ContextThreadLocal.set(new ConcurrentHashMap());
    }

    public void releaseCache() {
        tableName2ContextThreadLocal.remove();
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private boolean getBooleanValueByInputData(InputDataEO inputData, DimensionValueSet ds, String formula) {
        GcOrgCacheVO oppUnitOrg;
        GcOrgCacheVO unitOrg;
        GcReportInputDataExceutorContext context = this.getExceutorContext(inputData);
        String cateGory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)inputData.getTaskId());
        YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(inputData.getPeriod()));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(cateGory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitOrg = orgTool.getOrgByCode(inputData.getOrgCode()), oppUnitOrg = orgTool.getOrgByCode(inputData.getOppUnitId()));
        if (Objects.isNull(commonUnit)) {
            return false;
        }
        context.setOrgId(commonUnit.getCode());
        context.setTaskId(inputData.getTaskId());
        GcReportDataSet dataSet = context.getDataSet();
        context.setData(inputData);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                try {
                    evaluator.prepare((ExecutorContext)context, ds, formula);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                }
                GcReportInputDataRow row = new GcReportInputDataRow((Metadata<FieldDefine>)dataSet.getMetadata());
                row.setData(inputData);
                try {
                    boolean bl = evaluator.judge((DataRow)row);
                    return bl;
                }
                catch (Exception e) {
                    try {
                        throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                }
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u521b\u5efa\u6570\u636e\u96c6\u8868\u8fbe\u5f0f\u53d6\u6570\u63a5\u53e3\u5b9e\u4f8b\u5931\u8d25", (Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean getBooleanValueByInputDatas(List<InputDataEO> inputDatas, DimensionValueSet ds, String formula) {
        try (IDataSetExprEvaluator evaluator = this.getIDataSetExprEvaluator(inputDatas, ds, formula, null);){
            boolean bl = evaluator.judge(null);
            return bl;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData evaluateDoubleData(@NotNull DimensionValueSet ds, String formula, @NotNull InputDataEO inputData, List<InputDataEO> inputDatas, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        GcReportInputDataRow row;
        GcReportInputDataExceutorContext context = this.getExceutorContext(inputData);
        GcReportInputDataSet dataSet = context.getDataSet();
        if (inputDatas != null) {
            for (DefaultTableEntity throwable2 : inputDatas) {
                row = dataSet.add();
                row.setData(throwable2);
            }
        }
        context.setData(inputData);
        context.setInputDatas(inputDatas);
        context.setOffSetVchrItems(offSetVchrItems);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                AbstractData data;
                evaluator.prepare((ExecutorContext)context, ds, formula);
                row = new GcReportInputDataRow((Metadata<FieldDefine>)dataSet.getMetadata());
                row.setData(inputData);
                AbstractData abstractData = data = evaluator.evaluate((DataRow)row);
                return abstractData;
            }
            catch (Throwable throwable4) {
                Throwable throwable5 = throwable4;
                throw throwable4;
            }
        }
        catch (DivideZeroException e) {
            return AbstractData.valueOf((int)0);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData evaluateDoubleDataByInputDatas(@NotNull DimensionValueSet ds, String formula, @NotNull List<InputDataEO> inputDatas, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        try (IDataSetExprEvaluator evaluator = this.getIDataSetExprEvaluator(inputDatas, ds, formula, offSetVchrItems);){
            AbstractData data;
            AbstractData abstractData = data = evaluator.evaluate(null);
            return abstractData;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    private IDataSetExprEvaluator getIDataSetExprEvaluator(List<InputDataEO> inputDatas, DimensionValueSet ds, String formula, List<GcOffSetVchrItemDTO> offSetVchrItems) {
        GcReportInputDataExceutorContext context = this.getExceutorContext(inputDatas.get(0));
        GcReportInputDataSet dataSet = context.getDataSet();
        if (inputDatas != null) {
            for (DefaultTableEntity defaultTableEntity : inputDatas) {
                GcReportInputDataRow row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);
        context.setInputDatas(inputDatas);
        context.setOffSetVchrItems(offSetVchrItems);
        try {
            evaluator.prepare((ExecutorContext)context, ds, formula);
        }
        catch (Exception exception) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + exception.getMessage(), (Throwable)exception);
        }
        return evaluator;
    }

    private String getDefaultGroupName(DefaultTableEntity inputData) {
        if (inputData instanceof InputDataEO) {
            String taskId = ((InputDataEO)inputData).getTaskId();
            if (null == taskId) {
                return "GC_INPUTDATATEMPLATE";
            }
            return this.inputDataNameProvider.getTableNameByTaskId(((InputDataEO)inputData).getTaskId());
        }
        return (String)ReflectionUtils.getFieldValue((Object)inputData, (String)"TABLENAME");
    }

    private GcReportInputDataSet getDataSet(DefaultTableEntity inputData) {
        String tableName = this.getDefaultGroupName(inputData);
        return new GcReportInputDataSet(tableName);
    }

    private GcReportInputDataExceutorContext getExceutorContext(DefaultTableEntity entity) {
        GcReportInputDataExceutorContext context;
        String tableName = this.getDefaultGroupName(entity);
        Map<String, GcReportInputDataExceutorContext> tableName2ContextMap = tableName2ContextThreadLocal.get();
        if (null == tableName2ContextMap || !tableName2ContextMap.containsKey(tableName)) {
            context = this.newExceutorContext(entity);
            tableName2ContextMap = new HashMap<String, GcReportInputDataExceutorContext>();
            tableName2ContextMap.put(tableName, context);
            tableName2ContextThreadLocal.set(tableName2ContextMap);
        } else {
            context = tableName2ContextMap.get(tableName);
        }
        context.reset();
        return context;
    }

    private GcReportInputDataExceutorContext newExceutorContext(DefaultTableEntity entity) {
        GcReportInputDataExceutorContext context = new GcReportInputDataExceutorContext(this.runtimeController);
        try {
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName(this.getDefaultGroupName(entity));
            context.setDataSet(this.getDataSet(entity));
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return context;
    }
}

