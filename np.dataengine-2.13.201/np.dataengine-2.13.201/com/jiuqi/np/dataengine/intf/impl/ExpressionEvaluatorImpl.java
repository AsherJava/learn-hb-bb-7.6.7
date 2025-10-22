/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DetailCollectMonitor;
import com.jiuqi.np.dataengine.multi.IMultiDimAdapter;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressionEvaluatorImpl
implements IExpressionEvaluator {
    private QueryParam queryParam;
    private boolean multiDimModule = false;

    public ExpressionEvaluatorImpl(QueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public Object evalValue(IDataDefinitionRuntimeController runtimeController, String expression, DimensionValueSet dimensionValueSet) throws ExpressionException {
        try {
            ExecutorContext context = new ExecutorContext(runtimeController);
            return this.evalValue(expression, context, dimensionValueSet);
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    @Override
    public Object evalValue(String expression, ExecutorContext context, DimensionValueSet masterKeys) throws ExpressionException {
        try {
            Object[] values;
            Map<String, Object[]> result = this.evalBatch(Collections.singletonList(expression), context, masterKeys);
            if (result.size() > 0 && (values = result.values().iterator().next()).length > 0) {
                return values[0];
            }
            return null;
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    /*
     * Loose catch block
     */
    @Override
    public Object evalValueWithDetail(String expression, ExecutorContext context, DimensionValueSet masterKeys, DetailCollectMonitor monitor) throws Exception {
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext cContext = new QueryContext(context, this.queryParam, monitor);
                cContext.setTempResource(tempResource);
                cContext.setMasterKeys(masterKeys);
                IExpression exp = context.getCache().getFormulaParser(context).parseEval(expression, (IContext)cContext);
                Object object = this.evalSingleNode(expression, masterKeys, monitor, cContext, (IASTNode)exp);
                return object;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    /*
     * Loose catch block
     */
    @Override
    public Object evalValueWithDetail(IASTNode node, ExecutorContext context, DimensionValueSet masterKeys, DetailCollectMonitor monitor) throws Exception {
        try {
            try (TempResource tempResource = new TempResource();){
                QueryContext cContext = new QueryContext(context, this.queryParam, monitor);
                cContext.setTempResource(tempResource);
                cContext.setMasterKeys(masterKeys);
                Object object = this.evalSingleNode(node.interpret((IContext)cContext, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA)), masterKeys, monitor, cContext, node);
                return object;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            this.queryParam.closeConnection();
        }
    }

    @Override
    public Object[] evalValues(List<String> expressions, ExecutorContext context, DimensionValueSet masterKeys) throws ExpressionException {
        try {
            Map<String, Object[]> result = this.evalBatch(expressions, context, masterKeys);
            if (result.size() > 0) {
                return result.values().iterator().next();
            }
            return null;
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, Object[]> evalBatch(List<String> expressions, ExecutorContext context, DimensionValueSet masterKeys) throws Exception {
        HashMap<String, Object[]> result = new HashMap<String, Object[]>();
        try (TempResource tempResource = new TempResource();){
            AbstractMonitor monitor = new AbstractMonitor();
            QueryContext cContext = new QueryContext(context, this.queryParam, monitor);
            cContext.setTempResource(tempResource);
            cContext.setMasterKeys(masterKeys);
            cContext.setBatch(true);
            IMultiDimAdapter multiDimAdapter = this.multiDimModule ? this.queryParam.getMultiDimAdapter() : null;
            ExprExecRegionCreator regionCreator = multiDimAdapter == null ? new FmlExecRegionCreator(cContext, this.queryParam) : multiDimAdapter.getFmlExecRegionCreator(cContext, this.queryParam);
            ExprExecNetwork execNetwork = multiDimAdapter == null ? new ExprExecNetwork(cContext, regionCreator) : multiDimAdapter.createNetwork(cContext, regionCreator, false);
            String reportName = "BatchEval";
            if (cContext.getDefaultGroupName() != null) {
                reportName = cContext.getDefaultGroupName();
            }
            for (int i = 0; i < expressions.size(); ++i) {
                try {
                    String id = reportName + i;
                    String evalFormula = expressions.get(i);
                    IExpression exp = context.getCache().getFormulaParser(context).parseEval(evalFormula, (IContext)cContext);
                    Formula formula = new Formula();
                    formula.setId(id);
                    formula.setCode(id);
                    formula.setFormula(evalFormula);
                    formula.setReportName(reportName);
                    BatchVariable var = new BatchVariable(formula.getId(), 0, evalFormula, i);
                    String unitDim = this.findMainDimension(context, masterKeys);
                    BatchVariableDataNode varDataNode = new BatchVariableDataNode(var, unitDim, result, expressions.size());
                    Equal assignExp = new Equal(null, (IASTNode)varDataNode, (IASTNode)exp);
                    CalcExpression calcExp = new CalcExpression((IExpression)new Expression(null, (IASTNode)assignExp), formula, null, i);
                    if (this.multiDimModule) {
                        this.expandMultiDim(masterKeys, monitor, evalFormula, calcExp);
                    }
                    calcExp.setExtendAssignKey(var.getVarName());
                    execNetwork.arrangeCalcExpression(calcExp);
                    continue;
                }
                catch (Exception e) {
                    monitor.exception(e);
                }
            }
            execNetwork.initialize(monitor);
            execNetwork.checkRunTask(monitor);
            monitor.finish();
        }
        finally {
            this.queryParam.closeConnection();
        }
        return result;
    }

    private Object evalSingleNode(String expression, DimensionValueSet masterKeys, AbstractMonitor monitor, QueryContext cContext, IASTNode exp) throws Exception {
        IMultiDimAdapter multiDimAdapter = this.multiDimModule ? this.queryParam.getMultiDimAdapter() : null;
        ExprExecRegionCreator regionCreator = multiDimAdapter == null ? new FmlExecRegionCreator(cContext, this.queryParam) : multiDimAdapter.getFmlExecRegionCreator(cContext, this.queryParam);
        ExprExecNetwork execNetwork = multiDimAdapter == null ? new ExprExecNetwork(cContext, regionCreator) : multiDimAdapter.createNetwork(cContext, regionCreator, false);
        String reportName = "evalValueWithDetail";
        if (cContext.getDefaultGroupName() != null) {
            reportName = cContext.getDefaultGroupName();
        }
        String id = reportName;
        Formula formula = new Formula();
        formula.setFormula(expression);
        formula.setId(id);
        formula.setReportName(reportName);
        Variable var = new Variable(formula.getId(), 0);
        VariableDataNode varDataNode = new VariableDataNode(null, var);
        Equal assignExp = new Equal(null, (IASTNode)varDataNode, exp);
        CalcExpression calcExp = new CalcExpression((IExpression)new Expression(null, (IASTNode)assignExp), formula, null, 0);
        if (this.multiDimModule) {
            this.expandMultiDim(masterKeys, monitor, expression, calcExp);
        }
        calcExp.setExtendAssignKey(var.getVarName());
        execNetwork.arrangeCalcExpression(calcExp);
        execNetwork.initialize(monitor);
        execNetwork.checkRunTask(monitor);
        monitor.finish();
        return cContext.getVarValue(id);
    }

    private boolean expandMultiDim(DimensionValueSet masterKeys, AbstractMonitor monitor, String evalFormula, CalcExpression calcExp) {
        boolean dimLacked = false;
        for (IASTNode node : calcExp.getRealExpression()) {
            String dimName;
            int index;
            DynamicDataNode dataNode;
            if (!(node instanceof DynamicDataNode) || (dataNode = (DynamicDataNode)node).getStatisticInfo() != null) continue;
            QueryField queryField = dataNode.getQueryField();
            DimensionValueSet dimensionRestriction = queryField.getDimensionRestriction();
            if (dimensionRestriction == null) {
                dimensionRestriction = new DimensionValueSet();
                queryField.getTable().setDimensionRestriction(dimensionRestriction);
            }
            if (queryField.getPeriodModifier() != null) {
                String period = (String)masterKeys.getValue("DATATIME");
                String modifiedPeriod = queryField.getPeriodModifier().modify(period);
                dimensionRestriction.setValue("DATATIME", modifiedPeriod);
                queryField.getTable().setPeriodModifier(null);
            }
            for (index = 0; index < masterKeys.size(); ++index) {
                dimName = masterKeys.getName(index);
                if (!queryField.getTableDimensions().contains(dimName) || dimensionRestriction.hasValue(dimName)) continue;
                dimensionRestriction.setValue(dimName, masterKeys.getValue(index));
            }
            for (index = 0; index < queryField.getTableDimensions().size(); ++index) {
                dimName = queryField.getTableDimensions().get(index);
                if (dimensionRestriction.hasValue(dimName)) continue;
                dimLacked = true;
                break;
            }
            queryField.resetHashCode();
        }
        if (dimLacked) {
            monitor.message("\u591a\u7ef4\u5ea6\u516c\u5f0f\u5b58\u5728\u7ef4\u5ea6\u9650\u5b9a\u7f3a\u5931\u7684\u60c5\u51b5:" + evalFormula, this);
        }
        return dimLacked;
    }

    private String findMainDimension(ExecutorContext executorContext, DimensionValueSet masterKeys) {
        String mainDimension = null;
        IFmlExecEnvironment env = executorContext.getEnv();
        if (env != null) {
            mainDimension = env.getUnitDimesion(executorContext);
        }
        if (mainDimension == null) {
            for (int i = 0; i < masterKeys.size(); ++i) {
                Object dimValue = masterKeys.getValue(i);
                if (!(dimValue instanceof List)) continue;
                mainDimension = masterKeys.getName(i);
            }
        }
        return mainDimension;
    }

    @Override
    public AbstractData eval(IDataDefinitionRuntimeController runtimeController, String expression, DimensionValueSet dimensionValueSet) throws ExpressionException {
        try {
            ExecutorContext context = new ExecutorContext(runtimeController);
            return this.eval(expression, context, dimensionValueSet);
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    @Override
    public AbstractData eval(String expression, ExecutorContext context, DimensionValueSet dimensionValueSet) throws ExpressionException {
        try {
            DataQueryImpl dataQuery = new DataQueryImpl();
            dataQuery.setQueryParam(this.queryParam);
            dataQuery.addExpressionColumn(expression);
            dataQuery.setMasterKeys(dimensionValueSet);
            dataQuery.setStatic(true);
            IDataTable dataTable = dataQuery.executeQuery(context);
            if (dataTable.getCount() == 1) {
                return dataTable.getItem(0).getValue(0);
            }
            return null;
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    @Override
    public AbstractData eval(String expression, ExecutorContext context, QueryEnvironment queryEnvironment, DimensionValueSet dimensionValueSet) throws ExpressionException {
        try {
            IDataTable dataTable;
            IDataAccessProvider dataAccessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
            IDataQuery dataQuery = dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.addExpressionColumn(expression);
            dataQuery.setMasterKeys(dimensionValueSet);
            if (dataQuery instanceof DataQueryImpl) {
                DataQueryImpl dataQueryImpl = (DataQueryImpl)dataQuery;
                dataQueryImpl.setStatic(true);
            }
            if ((dataTable = dataQuery.executeQuery(context)).getCount() == 1) {
                return dataTable.getItem(0).getValue(0);
            }
            return null;
        }
        catch (Exception e) {
            throw new ExpressionException(e);
        }
    }

    @Override
    public void setMultiDimModule(boolean multiDimModule) {
        this.multiDimModule = multiDimModule;
    }

    private class BatchVariableDataNode
    extends ASTNode
    implements IAssignable {
        private static final long serialVersionUID = 4460515066209713106L;
        private BatchVariable var;
        private String unitDim;
        private Map<String, Object[]> result;
        private int varCount;

        public BatchVariableDataNode(BatchVariable var, String unitDim, Map<String, Object[]> result, int varCount) {
            super(null);
            this.var = var;
            this.unitDim = unitDim;
            this.result = result;
            this.varCount = varCount;
        }

        public int setValue(IContext context, Object value) throws SyntaxException {
            QueryContext qContext = (QueryContext)context;
            DimensionValueSet rowKey = qContext.getRowKey();
            String unitId = this.unitDim == null ? "defaultUnit" : (String)rowKey.getValue(this.unitDim);
            Object[] values = this.result.get(unitId);
            if (values == null) {
                values = new Object[this.varCount];
                this.result.put(unitId, values);
            }
            values[((BatchVariable)this.var).expIndex] = value;
            return 1;
        }

        public ASTNodeType getNodeType() {
            return ASTNodeType.DYNAMICDATA;
        }

        public int getType(IContext context) throws SyntaxException {
            return this.var.getDataType();
        }

        public Object evaluate(IContext context) throws SyntaxException {
            return null;
        }

        public boolean isStatic(IContext context) {
            return false;
        }

        public void toString(StringBuilder buffer) {
            buffer.append(this.var.getVarName());
        }

        public boolean support(Language lang) {
            return lang == Language.FORMULA;
        }

        protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
            buffer.append(this.var.getVarName());
        }

        public String toString() {
            return "BatchVariableDataNode [var=" + this.var + "]";
        }
    }

    private class BatchVariable
    extends Variable {
        private static final long serialVersionUID = -3040404886896326952L;
        private int expIndex;
        private String evalFormula;

        public BatchVariable(String varName, int dataType, String evalFormula, int expIndex) {
            super(varName, dataType);
            this.evalFormula = evalFormula;
            this.expIndex = expIndex;
        }

        @Override
        public String toString() {
            return "BatchVariable [expIndex=" + this.expIndex + ", evalFormula=" + this.evalFormula + "]";
        }
    }
}

