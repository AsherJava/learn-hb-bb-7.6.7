/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.data.engine.fml.var.VarCUR_TIMEKEY
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.bi.dataset.report.provider;

import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.exception.ExpParseError;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpFieldVo;
import com.jiuqi.bi.dataset.report.remote.controller.vo.ExpParsedFieldVo;
import com.jiuqi.bi.helper.PeriodHelper;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.data.engine.fml.var.VarCUR_TIMEKEY;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpParseProvider {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodHelper periodHelper;
    private static final Logger logger = LoggerFactory.getLogger(ExpParseProvider.class);
    private static Set<String> keyFieldSet = new HashSet<String>(Arrays.asList("CODE", "NAME", "PARENTCODE", "OBJECTCODE", "ORGCODE"));

    public List<ExpParsedFieldVo> doParse(String taskId, List<ExpFieldVo> fields, List<ReportDsParameter> params) throws JQException {
        ArrayList<ExpParsedFieldVo> parsedFields = new ArrayList<ExpParsedFieldVo>();
        ArrayList<QueryField> queryFields = new ArrayList<QueryField>();
        try {
            ExecutorContext executorContext = this.createExecutorContext(taskId, params);
            QueryContext qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(true);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            HashSet<QueryTable> queryTables = new HashSet<QueryTable>();
            for (ExpFieldVo field : fields) {
                ExpParsedFieldVo parsedField = new ExpParsedFieldVo();
                parsedFields.add(parsedField);
                parsedField.setKey(field.getKey());
                try {
                    IExpression exp = parser.parseEval(field.getExpresion(), (IContext)qContext);
                    IASTNode root = exp.getChild(0);
                    if (root instanceof DynamicDataNode) {
                        DynamicDataNode dataNode = (DynamicDataNode)root;
                        queryFields.add(dataNode.getQueryField());
                    } else {
                        queryFields.add(null);
                    }
                    for (IASTNode node : exp) {
                        if (!(node instanceof DynamicDataNode)) continue;
                        DynamicDataNode dataNode = (DynamicDataNode)node;
                        queryTables.add(dataNode.getQueryField().getTable());
                    }
                    int dataType = exp.getType((IContext)qContext);
                    if (dataType == 3) {
                        dataType = 10;
                    }
                    parsedField.setDatatype(dataType);
                    parsedField.setFieldType(this.getFieldType(taskDefine, exp, dataType));
                }
                catch (Exception e) {
                    logger.error("\u8868\u8fbe\u5f0f " + field.getExpresion() + " \u89e3\u6790\u51fa\u9519\uff1a" + e.getMessage(), e);
                    parsedField.setErrorMsg(e.getMessage());
                }
            }
            this.adjustFieldType(taskDefine, parsedFields, queryFields, executorContext, queryTables);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)new ExpParseError(e.getMessage()));
        }
        return parsedFields;
    }

    protected void adjustFieldType(TaskDefine taskDefine, List<ExpParsedFieldVo> parsedFields, List<QueryField> queryFields, ExecutorContext executorContext, Set<QueryTable> queryTables) throws ParseException {
        if (queryTables.size() > 0) {
            HashSet<String> entityTables = new HashSet<String>();
            TableModelRunInfo mainTableInfo = this.findDimTables(taskDefine, executorContext, queryTables, entityTables);
            for (int i = 0; i < queryFields.size(); ++i) {
                QueryField queryField = queryFields.get(i);
                ExpParsedFieldVo expParsedFieldVo = parsedFields.get(i);
                if (queryField == null || expParsedFieldVo.getFieldType() != FieldType.DESCRIPTION) continue;
                if (mainTableInfo != null && mainTableInfo.isKeyField(queryField.getFieldCode())) {
                    expParsedFieldVo.setFieldType(FieldType.GENERAL_DIM);
                    continue;
                }
                if (!entityTables.contains(queryField.getTableName()) || !keyFieldSet.contains(queryField.getFieldCode())) continue;
                expParsedFieldVo.setFieldType(FieldType.GENERAL_DIM);
            }
        }
    }

    protected TableModelRunInfo findDimTables(TaskDefine taskDefine, ExecutorContext executorContext, Set<QueryTable> queryTables, Set<String> entityTables) throws ParseException {
        TableModelRunInfo mainTableInfo = null;
        QueryTable mainTable = null;
        for (QueryTable table : queryTables) {
            if (table.isDimensionTable()) continue;
            if (mainTable == null) {
                mainTable = table;
                continue;
            }
            if (table.getTableDimensions().size() <= mainTable.getTableDimensions().size()) continue;
            mainTable = table;
        }
        if (mainTable != null) {
            DataModelDefinitionsCache dataModelDefinitionsCache = executorContext.getCache().getDataModelDefinitionsCache();
            mainTableInfo = dataModelDefinitionsCache.getTableInfo(mainTable.getTableName());
            List dimFields = mainTableInfo.getDimFields();
            for (ColumnModelDefine dimField : dimFields) {
                ColumnModelDefine refField;
                if (dimField.getReferColumnID() == null || (refField = dataModelDefinitionsCache.findField(dimField.getReferColumnID())) == null) continue;
                entityTables.add(dataModelDefinitionsCache.getTableName(refField));
            }
        } else {
            List dataDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
            for (DataDimension dataDimension : dataDimensions) {
                IEntityDefine entityDefine;
                DimensionType dimensionType = dataDimension.getDimensionType();
                if (dimensionType != DimensionType.UNIT && dimensionType != DimensionType.DIMENSION || dataDimension.getDimKey() == null || (entityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey())) == null) continue;
                entityTables.add(entityDefine.getCode());
            }
        }
        return mainTableInfo;
    }

    private FieldType getFieldType(TaskDefine taskDefine, IExpression exp, int dataType) {
        if (DataType.isNumberType((int)dataType)) {
            return FieldType.MEASURE;
        }
        if (dataType == 6) {
            VariableDataNode varNode;
            IASTNode root = exp.getChild(0);
            if (root instanceof VariableDataNode && (varNode = (VariableDataNode)root).getVariable() instanceof VarCUR_TIMEKEY) {
                PeriodType periodType = taskDefine.getPeriodType();
                if (periodType == PeriodType.CUSTOM || periodType == PeriodType.WEEK) {
                    return FieldType.GENERAL_DIM;
                }
                return FieldType.TIME_DIM;
            }
            return FieldType.DESCRIPTION;
        }
        return FieldType.DESCRIPTION;
    }

    private ExecutorContext createExecutorContext(String taskId, List<ReportDsParameter> params) throws Exception {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
        String fromSchemeKey = null;
        IPeriodEntity periodEntity = null;
        List dataDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dataDimension : dataDimensions) {
            DimensionType dimensionType = dataDimension.getDimensionType();
            if (dimensionType != DimensionType.PERIOD) continue;
            periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dataDimension.getDimKey());
        }
        String period = this.periodHelper.getCurrentPeriod(taskDefine, periodEntity);
        List schemePeriodLinks = this.runTimeViewController.querySchemePeriodLinkByTask(taskDefine.getKey());
        if (schemePeriodLinks != null && schemePeriodLinks.size() > 0) {
            fromSchemeKey = schemePeriodLinks.stream().filter(link -> link.getPeriodKey().equals(period)).findFirst().get().getSchemeKey();
        }
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, fromSchemeKey);
        env.setDataScehmeKey(taskDefine.getDataScheme());
        executorContext.setEnv((IFmlExecEnvironment)env);
        return executorContext;
    }
}

