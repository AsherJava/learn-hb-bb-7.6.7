/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class FieldExecInfo {
    private DynamicDataNode dataNode;
    private List<IParsedExpression> linkedExpressions = new ArrayList<IParsedExpression>();
    private List<LogRow> sqlLogs = new ArrayList<LogRow>();

    public FieldExecInfo(DynamicDataNode dataNode) {
        this.dataNode = dataNode;
    }

    public DynamicDataNode getDataNode() {
        return this.dataNode;
    }

    public List<IParsedExpression> getLinkedExpressions() {
        return this.linkedExpressions;
    }

    public List<LogRow> getSqlLogs() {
        return this.sqlLogs;
    }

    public void setDataNode(DynamicDataNode dataNode) {
        this.dataNode = dataNode;
    }

    public void collectExpression(IParsedExpression exp) {
        for (IASTNode node : exp.getRealExpression()) {
            if (!(node instanceof DynamicDataNode)) continue;
            DynamicDataNode fmlDataNode = (DynamicDataNode)node;
            QueryField fmlQueryField = fmlDataNode.getQueryField();
            QueryField queryField = this.dataNode.getQueryField();
            if (!fmlQueryField.getTableName().equals(queryField.getTableName()) || !fmlQueryField.getFieldName().equals(queryField.getFieldName())) continue;
            this.linkedExpressions.add(exp);
        }
    }

    public void collectSqlLogRow(LogRow logRow) {
        String sql = logRow.getSql();
        QueryField queryField = this.dataNode.getQueryField();
        if (logRow.getTableName().equals(queryField.getTableName()) && sql.indexOf(queryField.getFieldName()) >= 0) {
            this.sqlLogs.add(logRow);
        }
    }
}

