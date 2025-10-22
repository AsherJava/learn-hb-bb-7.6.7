/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO
 *  com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
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
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.financialcheckImpl.offset.rule.service.GcFinancialCheckFormulaEvalService;
import com.jiuqi.gcreport.financialcheckcore.item.dto.GcFcRuleUnOffsetDataDTO;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemEO;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
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
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFinancialCheckFormulaEvalServiceImpl
implements GcFinancialCheckFormulaEvalService {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;

    @Override
    public boolean checkMxUnOffsetData(@NotNull DimensionValueSet ds, String formula, @NotNull GcRelatedItemEO relatedItemEO, String taskId, String dataTime) {
        return this.getBooleanValueByUnOffsetData(relatedItemEO, ds, formula, taskId, dataTime);
    }

    @Override
    public boolean checkSumUnOffsetData(@NotNull DimensionValueSet ds, String formula, @NotNull List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        return this.getBooleanValueByUnOffsetDatas(unOffsetDatas, ds, formula);
    }

    @Override
    public double evaluateMxUnOffsetData(@NotNull DimensionValueSet ds, String formula, @NotNull GcFcRuleUnOffsetDataDTO unOffsetData, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        return GcAbstractData.getDoubleValue((AbstractData)this.evaluateDoubleData(ds, formula, unOffsetData, unOffsetDatas));
    }

    @Override
    public AbstractData evaluateMxUnOffsetAbstractData(@NotNull DimensionValueSet ds, String formula, @NotNull GcFcRuleUnOffsetDataDTO unOffsetData, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        return this.evaluateDoubleData(ds, formula, unOffsetData, unOffsetDatas);
    }

    @Override
    public double evaluateSumUnOffsetData(@NotNull DimensionValueSet ds, String formula, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        return GcAbstractData.getDoubleValue((AbstractData)this.evaluateDoubleDataByUnOffsetDatas(ds, formula, unOffsetDatas));
    }

    @Override
    public AbstractData evaluateSumUnOffsetAbstractData(@NotNull DimensionValueSet ds, String formula, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        return this.evaluateDoubleDataByUnOffsetDatas(ds, formula, unOffsetDatas);
    }

    /*
     * Could not resolve type clashes
     * Loose catch block
     */
    @Override
    public double evaluateUnOffsetDataPHS(@NotNull DimensionValueSet ds, String formula, @NotNull List<GcFcRuleUnOffsetDataDTO> unOffsetDatas, @NotNull double phsValue) {
        GcReportExceutorContext context = this.createExecutorContext();
        GcReportDataSet dataSet = context.getDataSet();
        if (unOffsetDatas != null) {
            for (DefaultTableEntity data : unOffsetDatas) {
                GcReportDataRow row = dataSet.add();
                row.setData(data);
            }
        }
        context.setInputDatas(unOffsetDatas);
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setPhsValue(Double.valueOf(phsValue));
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
     * Enabled aggressive exception aggregation
     */
    private boolean getBooleanValueByUnOffsetData(GcRelatedItemEO relatedItemEO, DimensionValueSet ds, String formula, String taskId, String dataTime) {
        GcOrgCacheVO oppUnitOrg;
        GcOrgCacheVO unitOrg;
        GcReportExceutorContext context = this.createExecutorContext();
        String cateGory = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)taskId);
        YearPeriodObject yp = new YearPeriodObject(null, String.valueOf(dataTime));
        GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)Objects.requireNonNull(cateGory), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO commonUnit = orgTool.getCommonUnit(unitOrg = orgTool.getOrgByCode(relatedItemEO.getUnitId()), oppUnitOrg = orgTool.getOrgByCode(relatedItemEO.getOppUnitId()));
        if (Objects.isNull(commonUnit)) {
            return false;
        }
        context.setOrgId(commonUnit.getCode());
        context.setTaskId(taskId);
        GcReportDataSet dataSet = context.getDataSet();
        GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
        row.setData((DefaultTableEntity)relatedItemEO);
        context.setData((DefaultTableEntity)relatedItemEO);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                try {
                    evaluator.prepare((ExecutorContext)context, ds, formula);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
                }
                try {
                    boolean e = evaluator.judge((DataRow)row);
                    return e;
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
    private boolean getBooleanValueByUnOffsetDatas(List<GcFcRuleUnOffsetDataDTO> unOffsetDatas, DimensionValueSet ds, String formula) {
        try (IDataSetExprEvaluator evaluator = this.getIDataSetExprEvaluator(unOffsetDatas, ds, formula);){
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
    private AbstractData evaluateDoubleData(@NotNull DimensionValueSet ds, String formula, @NotNull GcFcRuleUnOffsetDataDTO unOffsetData, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        GcReportDataRow row;
        GcReportExceutorContext context = this.createExecutorContext();
        GcReportDataSet dataSet = context.getDataSet();
        if (unOffsetDatas != null) {
            for (DefaultTableEntity defaultTableEntity : unOffsetDatas) {
                row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        context.setData((DefaultTableEntity)unOffsetData);
        context.setInputDatas(unOffsetDatas);
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                AbstractData data;
                evaluator.prepare((ExecutorContext)context, ds, formula);
                row = new GcReportDataRow(dataSet.getMetadata());
                row.setData((DefaultTableEntity)unOffsetData);
                AbstractData abstractData = data = evaluator.evaluate((DataRow)row);
                return abstractData;
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData evaluateDoubleDataByUnOffsetDatas(@NotNull DimensionValueSet ds, String formula, List<GcFcRuleUnOffsetDataDTO> unOffsetDatas) {
        try (IDataSetExprEvaluator evaluator = this.getIDataSetExprEvaluator(unOffsetDatas, ds, formula);){
            AbstractData data;
            AbstractData abstractData = data = evaluator.evaluate(null);
            return abstractData;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    private IDataSetExprEvaluator getIDataSetExprEvaluator(List<? extends GcRelatedItemEO> relatedItems, DimensionValueSet ds, String formula) {
        GcReportExceutorContext context = this.createExecutorContext();
        GcReportDataSet dataSet = context.getDataSet();
        if (relatedItems != null) {
            for (DefaultTableEntity defaultTableEntity : relatedItems) {
                GcReportDataRow row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);
        context.setInputDatas(relatedItems);
        try {
            evaluator.prepare((ExecutorContext)context, ds, formula);
        }
        catch (Exception exception) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff0c" + exception.getMessage(), (Throwable)exception);
        }
        return evaluator;
    }

    private GcReportExceutorContext createExecutorContext() {
        GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
        try {
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName("GC_RELATED_ITEM");
            context.setDataSet(new GcReportDataSet(new String[]{"GC_RELATED_ITEM"}));
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return context;
    }
}

