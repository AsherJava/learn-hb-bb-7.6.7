/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
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
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  javax.validation.constraints.NotNull
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.invest.formula.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.invest.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
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
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcBillFormulaEvalServiceImpl
implements GcBillFormulaEvalService {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;

    @Override
    public boolean checkInvestBillData(@NotNull GcCalcEnvContext env, @NotNull DimensionValueSet dset, String fetchFormula, @NotNull GcInvestBillGroupDTO investmentBillGroupDTO) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return true;
        }
        AbstractData data = this.getInvestBillData(env, dset, fetchFormula, investmentBillGroupDTO);
        return GcAbstractData.getBooleanValue((AbstractData)data);
    }

    @Override
    public double evaluateInvestBillData(@NotNull GcCalcEnvContext env, @NotNull DimensionValueSet dset, String fetchFormula, @NotNull GcInvestBillGroupDTO investmentBillGroupDTO, Integer scale) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return 0.0;
        }
        AbstractData data = this.getInvestBillData(env, dset, fetchFormula, investmentBillGroupDTO);
        return null == scale ? GcAbstractData.getDoubleValue((AbstractData)data) : GcAbstractData.getDoubleValue((AbstractData)data, (int)scale);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AbstractData getInvestBillData(GcCalcEnvContext env, DimensionValueSet dset, String fetchFormula, GcInvestBillGroupDTO investmentBillGroupDTO) {
        String orgId = String.valueOf(investmentBillGroupDTO.getMaster().getFieldValue("INVESTEDUNIT"));
        DimensionValueSet investDs = this.getInvestDs(env, dset, orgId);
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_INVESTBILL", "GC_INVESTBILLITEM"});
        if (!CollectionUtils.isEmpty(investmentBillGroupDTO.getCurrYearItems())) {
            for (DefaultTableEntity data : investmentBillGroupDTO.getCurrYearItems()) {
                GcReportDataRow row = dataSet.add();
                row.setData(data);
            }
        }
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.setData(investmentBillGroupDTO.getMaster());
            context.setItems(investmentBillGroupDTO.getItems());
            context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
            context.setDefaultGroupName("GC_INVESTBILL");
            context.setTaskId(env.getCalcArgments().getTaskId());
            context.setSchemeId(env.getCalcArgments().getSchemeId());
            context.setOrgId(env.getCalcArgments().getOrgId());
            context.setPhsValue(env.getCalcContextExpandVariableCenter().getPhsValue());
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, env.getCalcArgments().getSchemeId());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, investDs, fetchFormula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(investmentBillGroupDTO.getMaster());
            row.setDatas(investmentBillGroupDTO.getCurrYearItems());
            AbstractData abstractData = evaluator.evaluate((DataRow)row);
            return abstractData;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(fetchFormula + "\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    private DimensionValueSet getInvestDs(GcCalcEnvContext env, DimensionValueSet dset, String investedUnit) {
        DimensionValueSet investDs = new DimensionValueSet(dset);
        if (investedUnit == null) {
            return investDs;
        }
        YearPeriodObject yp = new YearPeriodObject(null, env.getCalcArgments().getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)env.getCalcArgments().getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = instance.getUnionUnitByGrade(investedUnit);
        investDs.setValue("MD_ORG", (Object)(null == hbOrg ? investedUnit : hbOrg.getId()));
        investDs.setValue("MD_GCORGTYPE", (Object)(null == hbOrg ? env.getCalcArgments().getOrgType() : hbOrg.getOrgTypeId()));
        return investDs;
    }

    @Override
    public boolean checkFvchBillData(GcCalcArgmentsDTO arg, @NotNull DimensionValueSet ds, String formula, @NotNull DefaultTableEntity fairValueChangeEO) {
        return this.getBooleanValue(arg, ds, formula, fairValueChangeEO, null);
    }

    @Override
    public double evaluateFvchBillData(GcCalcEnvContext env, @NotNull DimensionValueSet ds, String formula, @NotNull DefaultTableEntity fairValueChangeEO) {
        return this.evaluateDoubleData(env, ds, formula, fairValueChangeEO, null);
    }

    @Override
    public AbstractData evaluateFvchBillAbstractData(GcCalcEnvContext env, @NotNull DimensionValueSet ds, String formula, @NotNull DefaultTableEntity fairValueChangeEO) {
        return this.evaluateData(env, ds, formula, fairValueChangeEO, null);
    }

    private double evaluateDoubleData(GcCalcEnvContext env, @NotNull DimensionValueSet ds, String formula, DefaultTableEntity inputData, List<? extends DefaultTableEntity> inputDatas) {
        if (StringUtils.isEmpty((CharSequence)formula)) {
            return 0.0;
        }
        AbstractData data = this.evaluateData(env, ds, formula, inputData, inputDatas);
        return GcAbstractData.getDoubleValue((AbstractData)data);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData evaluateData(GcCalcEnvContext env, @NotNull DimensionValueSet ds, String formula, DefaultTableEntity inputData, List<? extends DefaultTableEntity> inputDatas) {
        String fvchItemType = (String)inputData.getFields().get("fvchItemType");
        GcReportDataSet dataSet = "GC_FVCH_OTHERITEM".equals(fvchItemType) ? new GcReportDataSet(new String[]{"GC_FVCH_OTHERITEM"}) : new GcReportDataSet(new String[]{"GC_FVCH_FIXEDITEM"});
        if (inputDatas != null) {
            for (DefaultTableEntity defaultTableEntity : inputDatas) {
                GcReportDataRow row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
                context.setData(inputData);
                context.setInputDatas(inputDatas);
                context.setTaskId(env.getCalcArgments().getTaskId());
                context.setSchemeId(env.getCalcArgments().getSchemeId());
                context.setDefaultGroupName("GC_FVCHBILL");
                context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
                context.setPhsValue(env.getCalcContextExpandVariableCenter().getPhsValue());
                evaluator.prepare((ExecutorContext)context, ds, formula);
                AbstractData abstractData = this.getResultData((Metadata<FieldDefine>)dataSet.getMetadata(), evaluator, inputData);
                return abstractData;
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    private AbstractData getResultData(Metadata<FieldDefine> metadatas, IDataSetExprEvaluator evaluator, DefaultTableEntity inputData) throws Exception {
        AbstractData result;
        if (inputData == null) {
            result = evaluator.evaluate(null);
        } else {
            GcReportDataRow row = new GcReportDataRow(metadatas);
            row.setData(inputData);
            result = evaluator.evaluate((DataRow)row);
        }
        return result;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean getBooleanValue(GcCalcArgmentsDTO arg, @NotNull DimensionValueSet ds, String formula, DefaultTableEntity inputData, List<? extends DefaultTableEntity> inputDatas) {
        if (StringUtils.isEmpty((CharSequence)formula)) {
            return true;
        }
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_FVCH_FIXEDITEM"});
        if (inputDatas != null) {
            for (DefaultTableEntity defaultTableEntity : inputDatas) {
                GcReportDataRow row = dataSet.add();
                row.setData(defaultTableEntity);
            }
        }
        try {
            Throwable throwable = null;
            try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
                GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
                context.setOrgId(arg.getOrgId());
                context.setTaskId(arg.getTaskId());
                context.setData(inputData);
                context.setInputDatas(inputDatas);
                context.setDefaultGroupName("GC_FVCHBILL");
                context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
                evaluator.prepare((ExecutorContext)context, ds, formula);
                if (inputData == null) {
                    boolean bl = evaluator.judge(null);
                    return bl;
                }
                GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
                row.setData(inputData);
                boolean bl = evaluator.judge((DataRow)row);
                return bl;
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }
}

