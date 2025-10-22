/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.ExecuteException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.CalcExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.executors.FmlExecRegionCreator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeCalcService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.zb.scheme.common.ZbType
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.engine.datacalc;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.CalcExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.datacalc.DataSchemeCalcContext;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeCalcService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.zb.scheme.common.ZbType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeCalcServiceImpl
implements IDataSchemeCalcService {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;

    public void executeCalc(DataSchemeCalcTask task, AsyncTaskMonitor monitor) throws Exception {
        QueryParam formulaParam = this.dataAccessProvider.getFormulaParam();
        try {
            DataSchemeCalcContext calcContext = this.prepare(task, monitor);
            ArrayList periods = PeriodUtil.getPeiodWrapperList((PeriodWrapper)new PeriodWrapper(calcContext.getCalcStartPeriod()), (PeriodWrapper)new PeriodWrapper(calcContext.getCalcEndPeriod()));
            double periodWight = 0.99 / (double)periods.size();
            for (PeriodWrapper period : periods) {
                DimensionValueSet masterKeys;
                if (monitor.isCancel()) {
                    return;
                }
                if (calcContext.getPriorityCalcUnits() != null) {
                    masterKeys = new DimensionValueSet();
                    masterKeys.setValue("DATATIME", (Object)period.toString());
                    masterKeys.setValue(calcContext.getExecutorContext().getUnitDimension(), calcContext.getPriorityCalcUnits());
                    this.calcByMasterKeys(formulaParam, calcContext, 0.0, masterKeys);
                }
                masterKeys = new DimensionValueSet();
                masterKeys.setValue("DATATIME", (Object)period.toString());
                this.calcByMasterKeys(formulaParam, calcContext, periodWight, masterKeys);
            }
            calcContext.getMonitor().finish();
        }
        catch (Exception e) {
            monitor.error(e.getMessage(), (Throwable)e);
            throw e;
        }
        finally {
            formulaParam.closeConnection();
        }
    }

    private void calcByMasterKeys(QueryParam formulaParam, DataSchemeCalcContext calcContext, double periodWight, DimensionValueSet masterKeys) throws ParseException, ExecuteException, Exception {
        QueryContext qContext = new QueryContext(calcContext.getExecutorContext(), formulaParam, (IMonitor)calcContext.getMonitor());
        qContext.setMasterKeys(masterKeys);
        qContext.setZbCalcMode(true);
        qContext.setBatch(true);
        FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(qContext, true, formulaParam);
        CalcExprExecNetwork execNetwork = new CalcExprExecNetwork(qContext, (ExprExecRegionCreator)regionCreator);
        calcContext.getCalcExpressions().forEach(exp -> {
            try {
                execNetwork.arrangeCalcExpression((IExpression)exp);
            }
            catch (Exception e) {
                calcContext.getMonitor().exception(e);
            }
        });
        execNetwork.initialize((Object)calcContext.getMonitor());
        calcContext.getMonitor().setStep(periodWight / (double)execNetwork.size());
        execNetwork.checkRunTask((Object)calcContext.getMonitor());
    }

    private DataSchemeCalcContext prepare(DataSchemeCalcTask task, AsyncTaskMonitor monitor) throws ParseException {
        DataSchemeCalcContext calcContext = this.createCalcContext(task, monitor);
        ExecutorContext executorContext = calcContext.getExecutorContext();
        List allDataFields = this.dataSchemeService.getAllDataField(task.getDataSchemeKey());
        QueryContext pContext = new QueryContext(calcContext.getExecutorContext(), (IMonitor)calcContext.getMonitor());
        ReportFormulaParser formulaParser = executorContext.getCache().getFormulaParser(true);
        FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
        allDataFields.forEach(field -> {
            if (field.getZbType() == ZbType.CALCULATE_ZB && StringUtils.isNotEmpty((String)field.getFormula())) {
                try {
                    CalcExpression calcExpression = this.getCalcExpression(calcContext, calcContext.getCalcExpressions().size(), executorContext, pContext, formulaParser, formulaShowInfo, (DataField)field);
                    calcContext.getCalcExpressions().add(calcExpression);
                }
                catch (Exception e) {
                    pContext.getMonitor().exception(e);
                }
            }
        });
        calcContext.getMonitor().prepare();
        return calcContext;
    }

    private CalcExpression getCalcExpression(DataSchemeCalcContext calcContext, int index, ExecutorContext executorContext, QueryContext pContext, ReportFormulaParser formulaParser, FormulaShowInfo formulaShowInfo, DataField field) throws ParseException, ExpressionException, InterpretException {
        IExpression exp = formulaParser.parseEval(field.getFormula(), (IContext)pContext);
        this.parsePriorityUnits(calcContext, executorContext, pContext, formulaParser, exp);
        QueryField queryField = executorContext.getCache().extractQueryField(executorContext, (FieldDefine)field, null, null);
        DynamicDataNode assignNode = new DynamicDataNode(queryField);
        NodeShowInfo nodeShowInfo = assignNode.getShowInfo();
        nodeShowInfo.setZBExpression(true);
        nodeShowInfo.setTableName(queryField.getTable().getTableName());
        nodeShowInfo.setHasBracket(true);
        exp.setChild(0, (IASTNode)new Equal(null, (IASTNode)assignNode, exp.getChild(0), true));
        Formula source = new Formula();
        source.setCode(field.getCode());
        source.setId(field.getKey());
        source.setReportName(calcContext.getTask().getDataSchemeKey());
        source.setFormula(exp.interpret((IContext)pContext, Language.FORMULA, (Object)formulaShowInfo));
        CalcExpression calcExpression = new CalcExpression(exp, source, assignNode, index);
        return calcExpression;
    }

    private void parsePriorityUnits(DataSchemeCalcContext calcContext, ExecutorContext executorContext, QueryContext pContext, ReportFormulaParser formulaParser, IExpression exp) {
        try {
            for (IASTNode node : exp) {
                if (node instanceof FunctionNode) {
                    String unitKey;
                    FunctionNode funcNode = (FunctionNode)node;
                    if (!funcNode.getDefine().name().equals("FetchOtherUnitDatas") || (unitKey = (String)((IASTNode)funcNode.getParameters().get(0)).evaluate((IContext)pContext)) == null) continue;
                    calcContext.addPriorityCalcUnit(unitKey);
                    continue;
                }
                if (node instanceof DynamicDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)node;
                    IASTNode funcNode = this.toFuncNode(calcContext, executorContext, pContext, formulaParser, dataNode);
                    if (funcNode == null) continue;
                    exp.setChild(0, funcNode);
                    continue;
                }
                for (int i = 0; i < node.childrenSize(); ++i) {
                    DynamicDataNode dataNode;
                    IASTNode funcNode;
                    IASTNode child = node.getChild(i);
                    if (!(child instanceof DynamicDataNode) || (funcNode = this.toFuncNode(calcContext, executorContext, pContext, formulaParser, dataNode = (DynamicDataNode)child)) == null) continue;
                    node.setChild(i, funcNode);
                }
            }
        }
        catch (Exception e) {
            pContext.getMonitor().exception(e);
        }
    }

    private IASTNode toFuncNode(DataSchemeCalcContext calcContext, ExecutorContext executorContext, QueryContext pContext, ReportFormulaParser formulaParser, DynamicDataNode dataNode) throws ParseException {
        String unitKey;
        IASTNode funcNode = null;
        DimensionValueSet dimensionRestriction = dataNode.getQueryField().getDimensionRestriction();
        if (dimensionRestriction != null && (unitKey = (String)dimensionRestriction.getValue(executorContext.getUnitDimension())) != null) {
            calcContext.addPriorityCalcUnit(unitKey);
            dimensionRestriction.clearValue(executorContext.getUnitDimension());
            if (dimensionRestriction.size() == 0) {
                dataNode.getQueryField().getTable().setDimensionRestriction(null);
            }
            String evalFormula = "FetchOtherUnitDatas('" + unitKey + "','" + this.getNodeFormula(dataNode) + "')";
            IExpression funcExp = formulaParser.parseEval(evalFormula, (IContext)pContext);
            funcNode = funcExp.getChild(0);
        }
        return funcNode;
    }

    private String getNodeFormula(DynamicDataNode dataNode) {
        QueryField queryField = dataNode.getQueryField();
        String tableCode = dataNode.getShowInfo().getTableName();
        String fieldCode = queryField.getFieldCode();
        if (tableCode == null) {
            tableCode = queryField.getTableName();
        }
        StringBuilder buff = new StringBuilder();
        buff.append(tableCode).append("[");
        buff.append(fieldCode);
        if (queryField.getPeriodModifier() != null) {
            buff.append(",").append(queryField.getPeriodModifier());
        }
        if (queryField.getIsLj()) {
            buff.append(",LJ");
        }
        buff.append("]");
        return buff.toString();
    }

    private DataSchemeCalcContext createCalcContext(DataSchemeCalcTask task, AsyncTaskMonitor monitor) {
        ExecutorContext eContext = new ExecutorContext(this.dataDefinitionController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.controller, this.dataDefinitionController, this.entityViewRunTimeController);
        env.setDataScehmeKey(task.getDataSchemeKey());
        eContext.setEnv((IFmlExecEnvironment)env);
        DataSchemeCalcContext calcContext = new DataSchemeCalcContext(task, eContext, monitor);
        if (calcContext.getCalcStartPeriod() == null || calcContext.getCalcEndPeriod() == null) {
            List allDeployInfos = this.dataSchemeService.getDeployInfoBySchemeKey(task.getDataSchemeKey());
            HashSet tableNames = new HashSet();
            allDeployInfos.forEach(info -> {
                if (!tableNames.contains(info.getTableName())) {
                    tableNames.add(info.getTableName());
                }
            });
            String sql = "select min(DATATIME),max(DATATIME) from ";
            tableNames.forEach(tableName -> this.jdbcTemplate.query(sql + tableName, rch -> {
                calcContext.setCalcStartPeriod(rch.getString(1));
                calcContext.setCalcEndPeriod(rch.getString(2));
            }));
        }
        return calcContext;
    }
}

