/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dql;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.WhereSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.EntDqlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.PsSetterFactory;
import java.util.ArrayList;
import java.util.List;

public class SelectSql<R>
implements EntDqlSql<R>,
WhereSql {
    private String tableName;
    private List<Object> values;
    private StringBuilder sqlColumn;
    private StringBuilder sqlWhere;
    private EntResultSetExtractor<R> resultSetExtractor;

    public SelectSql(String tableName, EntResultSetExtractor<R> resultSetExtractor) {
        this.tableName = tableName;
        this.resultSetExtractor = resultSetExtractor;
        this.values = new ArrayList<Object>();
        this.sqlColumn = new StringBuilder();
        this.sqlWhere = new StringBuilder(" where 1 = 1");
    }

    @Override
    public String getSql() {
        return " SELECT " + this.sqlColumn + " FROM " + this.tableName + this.sqlWhere + " ";
    }

    @Override
    public EntPreparedStatementSetter getPreStatementSetter() {
        return PsSetterFactory.newSetter(this.values);
    }

    @Override
    public void addCondition(String field, Object value) {
        this.sqlWhere.append(" AND ").append(field).append(" = ? ");
        this.values.add(value);
    }

    @Override
    public EntResultSetExtractor<R> getResultSetExtractor() {
        return this.resultSetExtractor;
    }

    public void addColumn(String field, String asName) {
        if (this.sqlColumn.length() > 0) {
            this.sqlColumn.append(",");
        }
        this.sqlColumn.append(field).append(" AS ").append(asName).append(" ");
    }
}

