/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
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
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.calculate.formula.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.calculate.dto.GcCalcArgmentsDTO;
import com.jiuqi.gcreport.calculate.formula.service.GcFormulaEvalService;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportDataRow;
import com.jiuqi.gcreport.calculate.formula.service.impl.gcformula.GcReportExceutorContext;
import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.nr.impl.function.GcReportFunctionProvider;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
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
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFormulaEvalServiceImpl
implements GcFormulaEvalService {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;

    @Override
    public boolean checkUnitData(GcCalcArgmentsDTO argmentsDTO, @NotNull DimensionValueSet ds, String formula, String schemeId) {
        DimensionValueSet manageMentDS = this.doManagementDS(ds);
        return this.evaluateBoolean(argmentsDTO, manageMentDS, formula, schemeId);
    }

    @Override
    public double evaluateUnitData(@NotNull DimensionValueSet ds, String formula, String schemeId) {
        DimensionValueSet manageMentDS = this.doManagementDS(ds);
        return this.evaluate(manageMentDS, formula, schemeId);
    }

    @Override
    public AbstractData evaluateUnitDataFormula(@NotNull DimensionValueSet ds, String formula, String schemeId) {
        DimensionValueSet manageMentDS = this.doManagementDS(ds);
        AbstractData data = this.ordinaryFormulaEvaluate(manageMentDS, formula, schemeId);
        return data;
    }

    public DimensionValueSet doManagementDS(@NotNull DimensionValueSet ds) {
        YearPeriodObject yp;
        GcOrgCenterService instance;
        GcOrgCacheVO unitOrg;
        Object orgType = ds.getValue("MD_GCORGTYPE");
        if (GCOrgTypeEnum.getEnumByID((String)String.valueOf(orgType)) == GCOrgTypeEnum.MANAGEMENT && (unitOrg = (instance = GcOrgPublicTool.getInstance((String)"MD_ORG_MANAGEMENT", (GcAuthorityType)GcAuthorityType.ACCESS, (YearPeriodObject)(yp = new YearPeriodObject(null, ds.getValue("DATATIME").toString())))).getOrgByCode((String)ds.getValue("MD_ORG"))) != null) {
            DimensionValueSet managementDS = new DimensionValueSet(ds);
            managementDS.setValue("MD_GCORGTYPE", (Object)unitOrg.getOrgTypeId());
            return managementDS;
        }
        return ds;
    }

    @Override
    public double evaluate(@NotNull DimensionValueSet ds, String formula, String schemeId) {
        if (StringUtils.isEmpty((String)formula)) {
            return 0.0;
        }
        try {
            AbstractData data = this.ordinaryFormulaEvaluate(ds, formula, schemeId);
            return GcAbstractData.getDoubleValue((AbstractData)data);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public AbstractData ordinaryFormulaEvaluate(DimensionValueSet ds, String formula, String schemeId) {
        if (StringUtils.isEmpty((String)formula)) {
            return AbstractData.valueOf((int)0);
        }
        try {
            IExpressionEvaluator evaluator = this.provider.newExpressionEvaluator();
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, schemeId);
            context.setEnv((IFmlExecEnvironment)environment);
            return evaluator.eval(formula, (ExecutorContext)context, ds);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    public boolean evaluateBoolean(GcCalcArgmentsDTO argmentsDTO, @NotNull DimensionValueSet ds, String formula, String schemeId) {
        if (StringUtils.isEmpty((String)formula)) {
            return true;
        }
        try {
            IExpressionEvaluator evaluator = this.provider.newExpressionEvaluator();
            GcReportExceutorContext context = new GcReportExceutorContext(this.runtimeController);
            context.registerFunctionProvider((IFunctionProvider)new GcReportFunctionProvider());
            IRunTimeViewController iRunTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(iRunTimeViewController, this.runtimeController, entityViewRunTimeController, schemeId);
            context.setEnv((IFmlExecEnvironment)environment);
            context.setTaskId(argmentsDTO.getTaskId());
            context.setOrgId(argmentsDTO.getOrgId());
            AbstractData data = evaluator.eval(formula, (ExecutorContext)context, ds);
            return GcAbstractData.getBooleanValue((AbstractData)data);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + " \u516c\u5f0f\u6267\u884c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
        }
    }

    private AbstractData getResultData(Metadata<FieldDefine> metadatas, IDataSetExprEvaluator evaluator, DefaultTableEntity inputData) throws Exception {
        AbstractData result = null;
        if (inputData == null) {
            result = evaluator.evaluate(null);
        } else {
            GcReportDataRow row = new GcReportDataRow(metadatas);
            row.setData(inputData);
            result = evaluator.evaluate((DataRow)row);
        }
        return result;
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

