/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.ExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.executors.FmlExecRegionCreator
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.data.engine.fml.SimpleFormula;
import com.jiuqi.nr.data.engine.fml.SimpleMonitor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportExpressionEvalUtil {
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;

    public AbstractData eval(ExecutorContext context, IASTNode exp, DimensionValueSet masterKeys) throws Exception {
        ArrayList<IASTNode> exps = new ArrayList<IASTNode>(1);
        exps.add(exp);
        List<AbstractData> results = this.eval(context, exps, masterKeys);
        return results.get(0);
    }

    /*
     * Loose catch block
     */
    public List<AbstractData> eval(ExecutorContext context, List<IASTNode> exps, DimensionValueSet masterKeys) throws Exception {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        try {
            try (TempResource tempResource = new TempResource();){
                int i;
                SimpleMonitor monitor = new SimpleMonitor();
                QueryContext cContext = new QueryContext(context, queryParam, (IMonitor)monitor);
                cContext.setTempResource(tempResource);
                cContext.setMasterKeys(masterKeys);
                ArrayList<VariableDataNode> varNodes = new ArrayList<VariableDataNode>(exps.size());
                ArrayList<AbstractData> results = new ArrayList<AbstractData>(exps.size());
                FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(cContext, false, queryParam);
                ExprExecNetwork execNetwork = new ExprExecNetwork(cContext, (ExprExecRegionCreator)regionCreator);
                for (i = 0; i < exps.size(); ++i) {
                    IASTNode exp = exps.get(i);
                    SimpleFormula formula = new SimpleFormula(context.getDefaultGroupName(), "", i);
                    Variable var = new Variable("VAR_ASSIGN_" + formula.getId(), 0);
                    VariableDataNode dataNode = new VariableDataNode(null, var);
                    varNodes.add(dataNode);
                    Equal assignExp = new Equal(null, (IASTNode)dataNode, exp);
                    CalcExpression calcExp = new CalcExpression((IExpression)new Expression(null, (IASTNode)assignExp), (Formula)formula, null, i);
                    calcExp.setExtendAssignKey(var.getVarName());
                    execNetwork.arrangeCalcExpression((IExpression)calcExp);
                }
                execNetwork.initialize((Object)monitor);
                execNetwork.checkRunTask((Object)monitor);
                monitor.finish();
                for (i = 0; i < varNodes.size(); ++i) {
                    VariableDataNode varNode = (VariableDataNode)varNodes.get(i);
                    int dataType = exps.get(i).getType((IContext)cContext);
                    AbstractData value = AbstractData.valueOf((Object)varNode.evaluate((IContext)cContext), (int)dataType);
                    results.add(value);
                }
                ArrayList<AbstractData> arrayList = results;
                return arrayList;
            }
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
        }
        finally {
            queryParam.closeConnection();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean judge(ExecutorContext context, IASTNode condition, DimensionValueSet masterKeys) throws Exception {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController);
        SimpleMonitor monitor = new SimpleMonitor();
        try (TempResource tempResource = new TempResource();){
            Expression exp = new Expression(null, condition);
            SimpleFormula formula = new SimpleFormula(context.getDefaultGroupName(), condition.toString(), 0);
            CheckExpression checkExp = new CheckExpression((IExpression)exp, (Formula)formula);
            QueryContext cContext = new QueryContext(context, (IMonitor)monitor);
            cContext.setTempResource(tempResource);
            cContext.setMasterKeys(masterKeys);
            FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(cContext, false, queryParam);
            ExprExecNetwork execNetwork = new ExprExecNetwork(cContext, (ExprExecRegionCreator)regionCreator);
            execNetwork.arrangeCheckExpression((IExpression)checkExp);
            execNetwork.initialize((Object)monitor);
            execNetwork.checkRunTask((Object)monitor);
            monitor.finish();
        }
        finally {
            queryParam.closeConnection();
        }
        return monitor.hasError();
    }
}

