/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
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
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.samecontrol.formula.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataSet;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.samecontrol.formula.GcSameCtrlFormulaEvalService;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcSameCtrlFormulaEvalServiceImpl
implements GcSameCtrlFormulaEvalService {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;

    @Override
    public double evaluateSameCtrlInvestData(SameCtrlOffsetCond cond, DimensionValueSet dset, String fetchFormula, Double phsValue, DefaultTableEntity mast) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return 0.0;
        }
        AbstractData data = this.getSameCtrlInvestData(cond, dset, fetchFormula, phsValue, mast);
        return GcAbstractData.getDoubleValue((AbstractData)data);
    }

    @Override
    public boolean checkSameCtrlInvestData(SameCtrlOffsetCond cond, DimensionValueSet dset, String fetchFormula, DefaultTableEntity mast) {
        if (StringUtils.isEmpty((CharSequence)fetchFormula)) {
            return true;
        }
        AbstractData data = this.getSameCtrlInvestData(cond, dset, fetchFormula, 0.0, mast);
        return GcAbstractData.getBooleanValue((AbstractData)data);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AbstractData getSameCtrlInvestData(SameCtrlOffsetCond cond, DimensionValueSet dset, String fetchFormula, Double phsValue, DefaultTableEntity mast) {
        GcReportDataSet dataSet = new GcReportDataSet(new String[]{"GC_INVESTBILL"});
        dataSet.add().setData(mast);
        DimensionValueSet investDs = this.getInvestDs(cond, dset);
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator((DataSet)dataSet);){
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            context.setDefaultGroupName("GC_INVESTBILL");
            context.setTaskId(cond.getTaskId());
            context.setSchemeId(cond.getSchemeId());
            context.setOrgId(cond.getChangedUnitCode());
            context.setData(mast);
            context.setPhsValue(phsValue);
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, cond.getSchemeId());
            context.setEnv((IFmlExecEnvironment)environment);
            evaluator.prepare((ExecutorContext)context, investDs, fetchFormula);
            GcReportDataRow row = new GcReportDataRow(dataSet.getMetadata());
            row.setData(mast);
            AbstractData abstractData = evaluator.evaluate((DataRow)row);
            return abstractData;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(fetchFormula + "\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    private DimensionValueSet getInvestDs(SameCtrlOffsetCond cond, DimensionValueSet dset) {
        DimensionValueSet investDs = new DimensionValueSet(dset);
        Object orgObj = investDs.getValue("MD_ORG");
        if (orgObj == null) {
            return investDs;
        }
        YearPeriodObject yp = new YearPeriodObject(null, cond.getPeriodStr());
        GcOrgCenterService instance = GcOrgPublicTool.getInstance((String)cond.getOrgType(), (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)yp);
        GcOrgCacheVO hbOrg = instance.getUnionUnitByGrade(orgObj.toString());
        investDs.setValue("MD_ORG", null == hbOrg ? orgObj : hbOrg.getId());
        investDs.setValue("MD_GCORGTYPE", (Object)(null == hbOrg ? cond.getOrgType() : hbOrg.getOrgTypeId()));
        return investDs;
    }

    private MemoryDataSet<FieldDefine> getDataSet(IDataRow dataRow) {
        DefinitionsCache definitionsCache;
        ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class));
        try {
            definitionsCache = new DefinitionsCache(executorContext);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        IFieldsInfo fieldsInfo = dataRow.getFieldsInfo();
        String tableId = fieldsInfo.getFieldDefine(0).getOwnerTableKey();
        MemoryDataSet memoryDataSet = new MemoryDataSet(FieldDefine.class);
        Metadata columns = memoryDataSet.getMetadata();
        IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DataTable dataTable = runtimeDataSchemeService.getDataTable(tableId);
        DataModelService dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefine = dataModelService.getTableModelDefineByCode(dataTable.getCode());
        TableModelRunInfo table = definitionsCache.getDataModelDefinitionsCache().getTableInfo(tableModelDefine.getName());
        table.getColumnFieldMap().forEach((column, fieldDefine) -> {
            int dataType = DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType());
            columns.addColumn(new Column(column.getName(), dataType, fieldDefine));
        });
        DataRow dataRow2 = memoryDataSet.add();
        int fieldCount = fieldsInfo.getFieldCount();
        for (int i = 0; i < fieldCount; ++i) {
            Column column2;
            Object value = null;
            FieldDefine fieldDefine2 = fieldsInfo.getFieldDefine(i);
            AbstractData data = dataRow.getValue(i);
            if (!data.isNull) {
                value = data.getAsObject();
            }
            if ((column2 = memoryDataSet.getMetadata().find(table.getDimensionField(fieldDefine2.getCode()).getName())) == null) continue;
            dataRow2.setValue(column2.getIndex(), value);
        }
        return memoryDataSet;
    }
}

