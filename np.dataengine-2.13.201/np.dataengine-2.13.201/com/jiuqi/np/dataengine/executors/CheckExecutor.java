/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 *  com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.CheckRunException;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;

public final class CheckExecutor
extends ExecutorBase {
    private ExprExecContext execContext;
    private ArrayList<IExpression> expressions = new ArrayList();

    public CheckExecutor(ExprExecContext context) {
        super(context.GlobalContext);
        this.execContext = context;
    }

    public int size() {
        return this.expressions.size();
    }

    public IExpression get(int index) {
        return this.expressions.get(index);
    }

    public void add(IExpression expression) {
        this.expressions.add(expression);
    }

    public void remove(Expression expression) {
        this.expressions.remove(expression);
    }

    public void combine(CheckExecutor another) {
        this.expressions.addAll(another.expressions);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        QueryContext qContext = this.execContext.GlobalContext;
        IMonitor monitor = qContext.getMonitor();
        CostCalculator expCostCalculator = null;
        int count = this.expressions.size();
        for (int i = 0; i < count; ++i) {
            CheckExpression expression = (CheckExpression)this.expressions.get(i);
            try {
                boolean result;
                this.context.setDefaultGroupName(expression.getSource().getReportName());
                if (this.executeCollector != null) {
                    expCostCalculator = this.executeCollector.getNodeCostCollector().getExpCostCalculator(expression.getKey());
                    expCostCalculator.start();
                }
                if (result = expression.judge((IContext)qContext)) continue;
                monitor.error(expression, this.context);
                continue;
            }
            catch (DivideZeroException result) {
                continue;
            }
            catch (Exception ex) {
                if (qContext.getRunnerType() == DataEngineConsts.DataEngineRunType.JUDGE) {
                    monitor.error(expression, this.context);
                }
                this.doException(expression, ex);
                continue;
            }
            finally {
                if (expCostCalculator != null) {
                    expCostCalculator.end();
                }
            }
        }
        return true;
    }

    private void doException(CheckExpression expression, Exception ex) {
        StringBuilder msg = new StringBuilder();
        CheckExpression checkExpression = expression;
        Formula source = checkExpression.getSource();
        msg.append("\u5ba1\u6838\u6267\u884c\u9519\u8bef\uff1a").append(source).append("\n");
        msg.append(expression).append(":").append(ex.getMessage());
        CheckRunException ce = new CheckRunException(msg.toString(), ex);
        this.context.getMonitor().exception(ce);
    }

    public void print(StringBuilder buff) {
        for (int i = 0; i < this.size(); ++i) {
            CheckExpression exp = (CheckExpression)this.get(i);
            exp.print(this.context, buff);
        }
    }

    public void cloneExpressions(QueryContext context) {
        ArrayList<IExpression> newExps = new ArrayList<IExpression>();
        for (IExpression exp : this.expressions) {
            IExpression newExp = (IExpression)exp.clone();
            for (IASTNode node : newExp) {
                if (!(node instanceof DynamicDataNode)) continue;
                DynamicDataNode dataNode = (DynamicDataNode)node;
                if (dataNode.isReadValueByIndex()) {
                    context.getMonitor().message("\u6d6e\u52a8\u516c\u5f0f\u8282\u70b9\u514b\u9686\u5931\u8d25\uff0c\u653e\u5f03\u4f18\u5316", this);
                    return;
                }
                dataNode.setReadValueByIndex(true);
                dataNode.setQueryFieldInfo(context.getDataReader().findQueryFieldInfo(dataNode.getQueryField()));
            }
            newExps.add(newExp);
        }
        this.expressions = newExps;
    }
}

