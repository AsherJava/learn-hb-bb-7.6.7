/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.AggregationType
 *  com.jiuqi.bi.dataset.model.field.ApplyType
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy
 *  com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 */
package com.jiuqi.bi.dataset.calibersum;

import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSField;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSFieldType;
import com.jiuqi.bi.dataset.calibersum.model.CaliberSumDSModel;
import com.jiuqi.bi.dataset.model.field.AggregationType;
import com.jiuqi.bi.dataset.model.field.ApplyType;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchy;
import com.jiuqi.bi.dataset.model.hierarchy.DSHierarchyType;
import com.jiuqi.bi.dataset.remote.model.CaliberSumDSDefine;
import com.jiuqi.bi.publicparam.datasource.entity.NrEntityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CaliberSumDSModelBuilder {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    public void buildModel(CaliberSumDSModel dsModel) throws Exception {
        List fields = dsModel.getCommonFields();
        fields.clear();
        CaliberSumDSDefine sumDSDefine = dsModel.getCaliberSumDSDefine();
        String fromSchemeKey = null;
        List schemePeriodLinks = this.runTimeViewController.querySchemePeriodLinkByTask(sumDSDefine.getTaskId());
        if (schemePeriodLinks != null && schemePeriodLinks.size() > 0) {
            fromSchemeKey = ((SchemePeriodLinkDefine)schemePeriodLinks.get(0)).getSchemeKey();
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, fromSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)env);
        QueryContext context = new QueryContext(executorContext, null);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(sumDSDefine.getTaskId());
        List dims = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        CaliberSumDSField keyField = null;
        CaliberSumDSField nameField = null;
        CaliberSumDSField parentField = null;
        for (DataDimension dim : dims) {
            DimensionType dimensionType = dim.getDimensionType();
            if (dimensionType != DimensionType.UNIT) continue;
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
            keyField = this.addKeyField(fields, entityDefine);
            nameField = this.addNameField(fields, entityDefine);
            parentField = this.addParentField(fields, entityDefine);
        }
        this.addDataFields(context, fields, sumDSDefine, keyField, nameField);
        this.addOtherFields(fields, sumDSDefine);
        for (DSField field : fields) {
            field.setKeyField(keyField.getName());
            field.setNameField(nameField.getName());
        }
        this.addParameters(dsModel, dims);
        this.addHierarchys(dsModel, keyField, parentField);
    }

    private void addDataFields(QueryContext context, List<DSField> fields, CaliberSumDSDefine sumDSDefine, CaliberSumDSField keyField, CaliberSumDSField nameField) throws SyntaxException {
        List sumZbs = sumDSDefine.getSumZbs();
        for (LinkedHashMap v : sumZbs) {
            String fieldKey = (String)v.get("zbId");
            String adjustExpression = (String)v.get("zbMapRule");
            List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldKey});
            if (deployInfos != null && !deployInfos.isEmpty()) {
                ColumnModelDefine columnModel = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
                CaliberSumDSField dsField = this.addDSField(fields, columnModel);
                dsField.setAdjustExpression(adjustExpression);
                continue;
            }
            String exp = (String)v.get("express");
            String name = (String)v.get("zbCode");
            String title = (String)v.get("zbTitle");
            CaliberSumDSField dsField = this.addExpDSField(context, fields, name, title, exp);
            dsField.setAdjustExpression(adjustExpression);
        }
    }

    private void addOtherFields(List<DSField> fields, CaliberSumDSDefine sumDSDefine) {
        this.addOtherField(fields, 6, CaliberSumDSFieldType.MDCODE);
        if (sumDSDefine.getAuditErr().booleanValue()) {
            this.addOtherField(fields, 5, CaliberSumDSFieldType.CHECK_HINT);
            this.addOtherField(fields, 5, CaliberSumDSFieldType.CHECK_WARNNING);
            this.addOtherField(fields, 5, CaliberSumDSFieldType.CHECK_ERROR);
        }
        if (sumDSDefine.getReportStatus().booleanValue()) {
            this.addOtherField(fields, 6, CaliberSumDSFieldType.UNITSTATE);
        }
    }

    private void addParameters(CaliberSumDSModel dsModel, List<DataDimension> dims) throws Exception {
        dsModel.getParameterModels().clear();
        for (DataDimension dim : dims) {
            boolean isUnit;
            ParameterModel parameterModel = new ParameterModel();
            DimensionType dimensionType = dim.getDimensionType();
            ParameterValueConfig valueConfig = new ParameterValueConfig();
            boolean bl = isUnit = dimensionType == DimensionType.UNIT;
            if (isUnit || dimensionType == DimensionType.DIMENSION) {
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(dim.getDimKey());
                parameterModel.setGuid(entityDefine.getId());
                if (isUnit) {
                    parameterModel.setName("MD_ORG");
                } else {
                    parameterModel.setName(entityDefine.getDimensionName());
                }
                parameterModel.setTitle(entityDefine.getTitle());
                valueConfig.setDefaultValueMode("first");
                parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
                NrEntityDataSourceModel dataSourceModel = new NrEntityDataSourceModel();
                dataSourceModel.setEntityViewId(entityDefine.getId());
                dataSourceModel.setDataType(6);
                dataSourceModel.setBusinessType(isUnit ? DataBusinessType.UNIT_DIM : DataBusinessType.GENERAL_DIM);
                parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
            } else if (dimensionType == DimensionType.PERIOD) {
                IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
                IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(dim.getDimKey());
                parameterModel.setGuid(periodEntity.getKey());
                parameterModel.setName("MD_PERIOD");
                parameterModel.setTitle("\u65f6\u671f");
                valueConfig.setDefaultValueMode("expr");
                ExpressionParameterValue expValue = new ExpressionParameterValue("-0N");
                valueConfig.setDefaultValue((AbstractParameterValue)expValue);
                parameterModel.setValueConfig((AbstractParameterValueConfig)valueConfig);
                NrPeriodDataSourceModel dataSourceModel = new NrPeriodDataSourceModel();
                dataSourceModel.setEntityViewId(periodEntity.getKey());
                dataSourceModel.setDataType(6);
                dataSourceModel.setPeriodType(periodEntity.getPeriodType().type());
                TimeGranularity timeGranularity = TimeDimUtils.periodTypeToTimeGranularity((PeriodType)periodEntity.getPeriodType());
                if (timeGranularity != null) {
                    dataSourceModel.setTimegranularity(timeGranularity.value());
                }
                dataSourceModel.setTimekey(true);
                dataSourceModel.setRemote(false);
                dataSourceModel.setBusinessType(DataBusinessType.TIME_DIM);
                parameterModel.setDatasource((AbstractParameterDataSourceModel)dataSourceModel);
            }
            parameterModel.setSelectMode(ParameterSelectMode.SINGLE);
            dsModel.getParameterModels().add(parameterModel);
        }
    }

    protected void addHierarchys(CaliberSumDSModel dsModel, CaliberSumDSField keyField, CaliberSumDSField parentField) {
        DSHierarchy hierarchy = new DSHierarchy();
        hierarchy.setName("UNIT_TREE");
        hierarchy.setTitle("\u5355\u4f4d\u7ea7\u6b21");
        hierarchy.setType(DSHierarchyType.PARENT_HIERARCHY);
        hierarchy.setParentFieldName(parentField.getName());
        hierarchy.getLevels().add(keyField.getName());
        List hiers = dsModel.getHiers();
        hiers.clear();
        hiers.add(hierarchy);
    }

    private CaliberSumDSField addKeyField(List<DSField> fields, IEntityDefine entityDefine) {
        CaliberSumDSFieldType type = CaliberSumDSFieldType.CALIBER_CODE;
        CaliberSumDSField keyField = new CaliberSumDSField();
        keyField.setName(entityDefine.getDimensionName() + "_CODE");
        keyField.setTitle(type.getTitle());
        keyField.setValType(6);
        keyField.setAggregation(AggregationType.MIN);
        keyField.setApplyType(ApplyType.PERIOD);
        keyField.setType(type);
        keyField.setFieldType(FieldType.GENERAL_DIM);
        keyField.setIndex(fields.size());
        fields.add(keyField);
        return keyField;
    }

    private CaliberSumDSField addNameField(List<DSField> fields, IEntityDefine entityDefine) {
        CaliberSumDSFieldType type = CaliberSumDSFieldType.CALIBER_TITLE;
        CaliberSumDSField nameField = new CaliberSumDSField();
        nameField.setName(entityDefine.getDimensionName() + "_TITLE");
        nameField.setTitle(type.getTitle());
        nameField.setValType(6);
        nameField.setAggregation(AggregationType.MIN);
        nameField.setApplyType(ApplyType.PERIOD);
        nameField.setType(type);
        nameField.setFieldType(FieldType.GENERAL_DIM);
        nameField.setIndex(fields.size());
        fields.add(nameField);
        return nameField;
    }

    private CaliberSumDSField addParentField(List<DSField> fields, IEntityDefine entityDefine) {
        CaliberSumDSFieldType type = CaliberSumDSFieldType.CALIBER_PARENT;
        CaliberSumDSField parentField = new CaliberSumDSField();
        parentField.setName(entityDefine.getDimensionName() + "_PARENT");
        parentField.setTitle(type.getTitle());
        parentField.setValType(6);
        parentField.setAggregation(AggregationType.MIN);
        parentField.setApplyType(ApplyType.PERIOD);
        parentField.setType(type);
        parentField.setFieldType(FieldType.GENERAL_DIM);
        parentField.setIndex(fields.size());
        fields.add(parentField);
        return parentField;
    }

    private int getValType(ColumnModelDefine columnModel) {
        ColumnModelType columnType = columnModel.getColumnType();
        if (columnType == ColumnModelType.BIGDECIMAL) {
            return ColumnModelType.DOUBLE.getValue();
        }
        return columnType.getValue();
    }

    private CaliberSumDSField addDSField(List<DSField> fields, ColumnModelDefine columnModel) {
        CaliberSumDSField dsField = new CaliberSumDSField();
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineById(columnModel.getTableID());
        dsField.setColumnModel(columnModel);
        String tableModelCode = tableModel.getCode();
        dsField.setTableModelCode(tableModelCode);
        dsField.setName(tableModelCode + "_" + columnModel.getCode());
        dsField.setTitle(columnModel.getTitle());
        dsField.setValType(this.getValType(columnModel));
        dsField.setFieldType(this.getDSFieldType(columnModel));
        dsField.setAggregation(this.getAggregation(columnModel));
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setMessageAlias(dsField.getName());
        dsField.setIndex(fields.size());
        fields.add(dsField);
        return dsField;
    }

    private CaliberSumDSField addExpDSField(QueryContext context, List<DSField> fields, String name, String title, String exp) throws SyntaxException {
        CaliberSumDSField dsField = new CaliberSumDSField();
        dsField.setEvalExpression(exp);
        int fieldIndex = fields.size();
        dsField.setName(name);
        dsField.setTitle(title);
        dsField.setValType(3);
        dsField.setFieldType(FieldType.MEASURE);
        dsField.setAggregation(AggregationType.SUM);
        dsField.setApplyType(ApplyType.PERIOD);
        dsField.setMessageAlias(dsField.getName());
        dsField.setIndex(fieldIndex);
        IExpression expression = context.getExeContext().getCache().getFormulaParser(true).parseEval(exp, (IContext)context);
        boolean allFMDM = true;
        for (IASTNode node : expression) {
            DynamicDataNode dataNode;
            if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getQueryField().getTable().isDimensionTable()) continue;
            allFMDM = false;
        }
        int dataType = expression.getType((IContext)context);
        if (!DataTypes.isNumber((int)dataType)) {
            dsField.setAggregation(AggregationType.MIN);
            dsField.setFieldType(FieldType.DESCRIPTION);
        }
        dsField.setValType(dataType);
        if (allFMDM) {
            dsField.setDestEval(true);
        }
        fields.add(dsField);
        return dsField;
    }

    private CaliberSumDSField addOtherField(List<DSField> fields, int dataType, CaliberSumDSFieldType type) {
        CaliberSumDSField field = new CaliberSumDSField();
        field.setName(type.name().toUpperCase());
        field.setTitle(type.getTitle());
        field.setValType(dataType);
        field.setAggregation(AggregationType.MIN);
        field.setApplyType(ApplyType.PERIOD);
        field.setType(type);
        if (dataType == 6) {
            field.setFieldType(FieldType.DESCRIPTION);
        } else {
            field.setFieldType(FieldType.MEASURE);
        }
        field.setIndex(fields.size());
        fields.add(field);
        return field;
    }

    private FieldType getDSFieldType(ColumnModelDefine columnModel) {
        switch (columnModel.getColumnType()) {
            case BOOLEAN: 
            case DOUBLE: 
            case BIGDECIMAL: 
            case INTEGER: {
                return FieldType.MEASURE;
            }
        }
        return FieldType.DESCRIPTION;
    }

    private AggregationType getAggregation(ColumnModelDefine columnModel) {
        switch (columnModel.getColumnType()) {
            case DOUBLE: 
            case BIGDECIMAL: 
            case INTEGER: {
                return AggregationType.SUM;
            }
        }
        return AggregationType.MIN;
    }
}

