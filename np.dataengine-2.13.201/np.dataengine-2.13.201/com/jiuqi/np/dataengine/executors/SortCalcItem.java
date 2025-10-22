/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SortCalcItem {
    private QueryField writeQueryField;
    private Set<QueryField> readQueryFields = new HashSet<QueryField>();
    private List<CalcExpression> expressions = null;
    private CalcExpression expression;
    public int tag;
    private boolean visited = false;
    private List<SortCalcItem> nextCalcItems;

    private List<CalcExpression> getExpressions() {
        if (this.expressions == null) {
            this.expressions = new ArrayList<CalcExpression>();
            if (this.expression != null) {
                this.expressions.add(this.expression);
            }
        }
        return this.expressions;
    }

    public void addExpression(CalcExpression expression) {
        if (this.expression != null) {
            this.getExpressions().add(expression);
        }
        this.expression = expression;
    }

    public void putToSortedExpressions(List<CalcExpression> expressions) {
        if (this.expressions == null) {
            expressions.add(this.expression);
        } else {
            expressions.addAll(this.expressions);
        }
    }

    public List<SortCalcItem> getNextCalcItems() {
        if (this.nextCalcItems == null) {
            this.nextCalcItems = new ArrayList<SortCalcItem>();
        }
        return this.nextCalcItems;
    }

    public String toString() {
        DynamicDataNode assignNode = this.expression.getAssignNode();
        DataLinkColumn dataLink = assignNode.getDataLink();
        if (dataLink != null) {
            return dataLink.toString();
        }
        return assignNode.toString();
    }

    public void printRelying(StringBuilder buff, Map<QueryField, SortCalcItem> sortExpressions) {
        buff.append("\n[");
        for (QueryField readKey : this.readQueryFields) {
            SortCalcItem item = sortExpressions.get(readKey);
            if (item == null) continue;
            buff.append(item.getExpression().getSource().getCode()).append("->");
            item.printRelying(buff, sortExpressions);
            buff.append(",\n");
        }
        buff.append("]");
    }

    public void printExpressions(QueryContext qContext, StringBuilder buff) {
        if (this.expressions == null) {
            Formula source = this.expression.getSource();
            try {
                buff.append(source.getReportName()).append("[").append(source.getCode()).append("]:");
                buff.append(this.expression.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ)));
                buff.append("\n");
            }
            catch (InterpretException interpretException) {
                qContext.getMonitor().exception((Exception)((Object)interpretException));
            }
        } else {
            for (IParsedExpression iParsedExpression : this.expressions) {
                Formula source = iParsedExpression.getSource();
                try {
                    buff.append(source.getReportName()).append("[").append(source.getCode()).append("]:");
                    buff.append(iParsedExpression.getFormula(qContext, new FormulaShowInfo(DataEngineConsts.FormulaShowType.JQ)));
                    buff.append("\n");
                }
                catch (InterpretException e) {
                    qContext.getMonitor().exception((Exception)((Object)e));
                }
            }
        }
    }

    public CalcExpression getExpression() {
        return this.expression;
    }

    public QueryField getWriteQueryField() {
        return this.writeQueryField;
    }

    public void setWriteQueryField(QueryField writeQueryField) {
        this.writeQueryField = writeQueryField;
    }

    public Set<QueryField> getReadQueryFields() {
        return this.readQueryFields;
    }

    public int size() {
        if (this.expressions == null) {
            return 1;
        }
        return this.expressions.size();
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.writeQueryField == null ? 0 : this.writeQueryField.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SortCalcItem other = (SortCalcItem)obj;
        return !(this.writeQueryField == null ? other.writeQueryField != null : !this.writeQueryField.equals(other.writeQueryField));
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}

