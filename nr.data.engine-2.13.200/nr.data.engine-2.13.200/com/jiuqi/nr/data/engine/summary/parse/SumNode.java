/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.data.engine.summary.parse.SumContext;
import com.jiuqi.nr.data.engine.summary.parse.SumDBSQLInfo;
import com.jiuqi.nr.data.engine.summary.parse.SumTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public class SumNode
extends DynamicNode {
    private static final long serialVersionUID = -7534417862670040414L;
    private ColumnModelDefine columnModel;
    private String tableAlias = "t";
    private SumTable table;
    private int statKind;
    private IASTNode conditionNode;

    public SumNode(Token paramToken, ColumnModelDefine columnModel) {
        super(paramToken);
        this.columnModel = columnModel;
    }

    public int getType(IContext paramIContext) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext paramIContext) throws SyntaxException {
        SumContext context = (SumContext)paramIContext;
        return context.getConditionFieldValue();
    }

    public void toString(StringBuilder paramStringBuilder) {
        paramStringBuilder.append(this.table.getTableName()).append(".").append(this.columnModel.getName());
    }

    public boolean support(Language lang) {
        return lang == Language.SQL;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        SumDBSQLInfo sqlInfo = (SumDBSQLInfo)info;
        if (sqlInfo.hasAlias()) {
            buffer.append(this.tableAlias).append(".").append(this.getZBAlias());
        } else {
            buffer.append(this.table.getTableName()).append(".").append(this.columnModel.getName());
        }
    }

    public SumTable getTable() {
        return this.table;
    }

    public void setTable(SumTable table) {
        this.table = table;
    }

    public String getTableAlias() {
        return this.tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getZBAlias() {
        return this.columnModel.getName() + "_" + this.table.getTableOrder();
    }

    public boolean equals(Object obj) {
        SumNode otherNode = (SumNode)((Object)obj);
        return this.getColumnModel().getID().equals(otherNode.getColumnModel().getID());
    }

    public int hashCode() {
        return this.getColumnModel().getID().hashCode();
    }

    public void setStatistic(IASTNode conditionNode, int statKind) {
        this.conditionNode = conditionNode;
        this.statKind = statKind;
    }

    public ColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    public void setColumnModel(ColumnModelDefine columnModel) {
        this.columnModel = columnModel;
    }
}

