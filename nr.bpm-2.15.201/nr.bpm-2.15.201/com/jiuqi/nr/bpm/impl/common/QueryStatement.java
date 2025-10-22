/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.def.exp.ConditionalExpression
 *  com.jiuqi.np.sql.def.query.RelationColumnDefine
 *  com.jiuqi.np.sql.def.table.TableFieldDefine
 *  com.jiuqi.np.sql.internal.def.exp.ConditionalExpr
 *  com.jiuqi.np.sql.internal.def.query.QueryStatementImpl
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.sql.def.exp.ConditionalExpression;
import com.jiuqi.np.sql.def.query.RelationColumnDefine;
import com.jiuqi.np.sql.def.table.TableFieldDefine;
import com.jiuqi.np.sql.internal.def.exp.ConditionalExpr;
import com.jiuqi.np.sql.internal.def.query.QueryStatementImpl;

public class QueryStatement {
    private QueryStatementImpl statement;

    public QueryStatementImpl getStatement() {
        return this.statement;
    }

    public void setStatement(QueryStatementImpl statement) {
        this.statement = statement;
    }

    public QueryStatement(String name) {
        this.statement = new QueryStatementImpl(name);
    }

    public void addCondition(ConditionalExpression condition, boolean isAnd) {
        ConditionalExpr oldCondition = this.statement.getCondition();
        if (oldCondition == null) {
            this.statement.setCondition(condition);
        } else {
            this.statement.setCondition(isAnd ? oldCondition.and(condition, new ConditionalExpression[0]) : oldCondition.or(condition, new ConditionalExpression[0]));
        }
    }

    public void addOrderBy(TableFieldDefine field, boolean isDesc) {
        this.statement.newOrderBy((RelationColumnDefine)field, isDesc);
    }
}

