/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO
 *  com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider
 *  com.jiuqi.gcreport.calculate.env.GcCalcEnvContext
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet
 *  com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
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
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  javax.validation.constraints.NotNull
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.asset.formula.service.impl;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.asset.assetbill.dto.GcAssetBillGroupDTO;
import com.jiuqi.gcreport.asset.formula.service.GcBillFormulaEvalService;
import com.jiuqi.gcreport.billcore.dto.GcBillGroupDTO;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.calculate.env.GcCalcEnvContext;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
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
    public boolean checkAssetBillData(@NotNull GcCalcEnvContext env, @NotNull DimensionValueSet dset, String fetchFormula, GcAssetBillGroupDTO assetBillGroupDTO) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return true;
        }
        AbstractData data = this.getAssetBillData(env, dset, fetchFormula, assetBillGroupDTO);
        return GcAbstractData.getBooleanValue((AbstractData)data);
    }

    @Override
    public double evaluateAssetBillData(@NotNull GcCalcEnvContext env, @NotNull DimensionValueSet dset, String fetchFormula, @NotNull GcAssetBillGroupDTO assetBillGroupDTO) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return 0.0;
        }
        AbstractData data = this.getAssetBillData(env, dset, fetchFormula, assetBillGroupDTO);
        return GcAbstractData.getDoubleValue((AbstractData)data);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData getBillData(GcCalcEnvContext env, DimensionValueSet dset, String fetchFormula, GcBillGroupDTO billGroupDTO, List<String> tableNames) {
        String orgId = String.valueOf(billGroupDTO.getMaster().getFieldValue("UNITCODE"));
        DimensionValueSet investDs = this.getInvestDs(env, dset, orgId);
        GcReportDataSet dataSet = new GcReportDataSet(tableNames.toArray(new String[0]));
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.setData(billGroupDTO.getMaster());
            context.setItems(billGroupDTO.getItems());
            context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
            context.setDefaultGroupName(tableNames.get(0));
            investDs.setValue("subTableName", (Object)tableNames.get(1));
            context.setTaskId(env.getCalcArgments().getTaskId());
            context.setOrgId(env.getCalcArgments().getOrgId());
            context.setSchemeId(env.getCalcArgments().getSchemeId());
            context.setPhsValue(env.getCalcContextExpandVariableCenter().getPhsValue());
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, env.getCalcArgments().getSchemeId());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, investDs, fetchFormula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(billGroupDTO.getMaster());
            row.setDatas(billGroupDTO.getItems());
            AbstractData abstractData = evaluator.evaluate((DataRow)row);
            return abstractData;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BusinessRuntimeException(fetchFormula + "\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public String evaluateAssetBillDataGetSubject(@NotNull GcCalcEnvContext env, @NotNull DimensionValueSet dset, String fetchFormula, @NotNull GcAssetBillGroupDTO assetBillGroupDTO) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return null;
        }
        AbstractData data = this.getAssetBillData(env, dset, fetchFormula, assetBillGroupDTO);
        return GcAbstractData.getStringValue((AbstractData)data);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public AbstractData getAssetBillData(GcCalcEnvContext env, DimensionValueSet dset, String fetchFormula, GcAssetBillGroupDTO assetBillGroupDTO) {
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_COMBINEDASSETBILL", "GC_COMBINEDASSETBILLITEM", "GC_COMMONASSETBILL"});
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            String orgId = String.valueOf(assetBillGroupDTO.getMaster().getFieldValue("UNITCODE"));
            DimensionValueSet investDs = this.getInvestDs(env, dset, orgId);
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.setData(assetBillGroupDTO.getMaster());
            context.setItems(assetBillGroupDTO.getItems());
            context.registerFunctionProvider((IFunctionProvider)new GcReportBillFunctionProvider());
            context.setDefaultGroupName("GC_COMBINEDASSETBILL");
            context.setTaskId(env.getCalcArgments().getTaskId());
            context.setOrgId(env.getCalcArgments().getOrgId());
            context.setSchemeId(env.getCalcArgments().getSchemeId());
            context.setPhsValue(env.getCalcContextExpandVariableCenter().getPhsValue());
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, env.getCalcArgments().getSchemeId());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, investDs, fetchFormula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(assetBillGroupDTO.getMaster());
            AbstractData abstractData = evaluator.evaluate((DataRow)row);
            return abstractData;
        }
        catch (Exception e) {
            e.printStackTrace();
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
}

