/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.CostCalculator
 *  com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.CostCalculator;
import com.jiuqi.bi.syntax.reportparser.exception.DivideZeroException;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.CalcRunException;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.util.CalcExpressionSortUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public final class CalcExecutor
extends ExecutorBase {
    private boolean hasExtendAssignKey = false;
    private boolean sorted = true;
    private ArrayList<CalcExpression> expressions = new ArrayList();

    public CalcExecutor(QueryContext context) {
        super(context);
    }

    public int size() {
        return this.expressions.size();
    }

    public IExpression get(int index) {
        return this.expressions.get(index);
    }

    public void add(CalcExpression expression) {
        this.expressions.add(expression);
        this.sorted = false;
    }

    public void remove(IExpression expression) {
        this.expressions.remove(expression);
    }

    public void combine(CalcExecutor another) {
        this.expressions.addAll(another.expressions);
        this.sorted = false;
    }

    @Override
    protected void doPreparation(Object taskInfo) throws Exception {
        super.doPreparation(taskInfo);
        this.sort();
    }

    public void sort() {
        if (!this.sorted) {
            this.sorted = true;
            CalcExpressionSortUtil.sort(this.expressions, this.context.getMonitor());
        }
    }

    @Override
    protected boolean doExecution(Object taskInfo) throws Exception {
        this.calc();
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void calc() throws SyntaxException {
        int count = this.expressions.size();
        for (int i = 0; i < count; ++i) {
            CalcExpression expression = this.expressions.get(i);
            CostCalculator expCostCalculator = null;
            try {
                this.context.setDefaultGroupName(expression.getSource().getReportName());
                if (this.executeCollector != null) {
                    expCostCalculator = this.executeCollector.getNodeCostCollector().getExpCostCalculator(expression.getKey());
                    expCostCalculator.start();
                }
                expression.execute((IContext)this.context);
                continue;
            }
            catch (DivideZeroException dze) {
                DynamicDataNode assignNode = expression.getAssignNode();
                if (assignNode == null) continue;
                assignNode.setValue((IContext)this.context, null);
                continue;
            }
            catch (Exception ex) {
                this.doException(expression, ex);
                continue;
            }
            finally {
                if (expCostCalculator != null) {
                    expCostCalculator.end();
                }
            }
        }
    }

    private void doException(CalcExpression expression, Exception ex) {
        StringBuilder msg = new StringBuilder();
        Formula source = expression.getSource();
        msg.append("\u8fd0\u7b97\u6267\u884c\u9519\u8bef\uff1a").append(source).append("\n");
        msg.append(expression).append(":").append(ex.getMessage());
        CalcRunException ce = new CalcRunException(msg.toString(), ex);
        this.context.getMonitor().exception(ce);
    }

    public void getReadQueryFields(Set<QueryField> readQueryFields) {
        this.getReadQueryFields(readQueryFields, null);
    }

    public void getReadQueryFields(Set<QueryField> readFields, Set<QueryTable> readTables) {
        for (CalcExpression expression : this.expressions) {
            QueryFields queryFields = expression.getQueryFields();
            for (QueryField queryField : queryFields) {
                readFields.add(queryField);
                if (readTables == null) continue;
                readTables.add(queryField.getTable());
            }
            if (expression.getExtendReadKeys() == null || expression.getExtendAssignKey() == null) continue;
            for (String key : expression.getExtendReadKeys()) {
                QueryTable table = new QueryTable("EXT", "EXT");
                QueryField extQueryField = new QueryField(key, key, table);
                readFields.add(extQueryField);
            }
        }
    }

    public void getWriteQueryFields(Set<QueryField> writeQueryFields) {
        this.getWriteQueryFields(writeQueryFields, null);
    }

    public void getWriteQueryFields(Set<QueryField> writeQueryFields, Set<QueryTable> writeTables) {
        for (CalcExpression expression : this.expressions) {
            if (expression.getExtendAssignKey() != null) {
                this.hasExtendAssignKey = true;
                QueryTable table = new QueryTable("EXT", "EXT");
                QueryField extQueryField = new QueryField(expression.getExtendAssignKey(), expression.getExtendAssignKey(), table);
                writeQueryFields.add(extQueryField);
                if (writeTables == null) continue;
                writeTables.add(extQueryField.getTable());
                continue;
            }
            QueryFields queryFields = expression.getParsedWriteFields();
            for (QueryField queryField : queryFields) {
                writeQueryFields.add(queryField);
                if (writeTables == null) continue;
                writeTables.add(queryField.getTable());
            }
        }
    }

    public boolean hasExtendAssignKey() {
        return this.hasExtendAssignKey;
    }

    public void print(StringBuilder buff) {
        for (CalcExpression exp : this.expressions) {
            exp.print(this.context, buff);
        }
    }

    public void cloneExpressions(QueryContext context) {
        ArrayList<CalcExpression> newExps = new ArrayList<CalcExpression>();
        for (CalcExpression exp : this.expressions) {
            CalcExpression newExp = (CalcExpression)exp.clone();
            Iterator<IASTNode> iterator = newExp.iterator();
            while (iterator.hasNext()) {
                IASTNode node = iterator.next();
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

