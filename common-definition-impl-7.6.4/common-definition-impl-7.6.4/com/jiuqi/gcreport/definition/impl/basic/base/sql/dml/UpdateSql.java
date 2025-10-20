/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql.dml;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.PsSetterFactory;
import java.util.List;

public class UpdateSql
implements EntDmlSql {
    private String tableName;
    private List<Object> values;
    private StringBuilder sqlColumn;
    private StringBuilder sqlWhere;

    public static UpdateSql newInstance(String tableName) {
        return new UpdateSql(tableName);
    }

    UpdateSql(String tableName) {
        this.tableName = tableName;
        this.sqlColumn = new StringBuilder();
        this.sqlWhere = new StringBuilder(" where 1 = 1");
    }

    @Override
    public String getSql() {
        return " UPDATE " + this.tableName + " SET " + this.sqlColumn + this.sqlWhere + " ";
    }

    @Override
    public EntPreparedStatementSetter getPreStatementSetter() {
        return PsSetterFactory.newSetter(this.values);
    }

    public void setColumns(List<String> fields) {
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        this.sqlColumn = new StringBuilder();
        this.sqlColumn.append(fields.get(0)).append(" = ? ");
        for (int index = 1; index < fields.size(); ++index) {
            this.sqlColumn.append(",").append(fields.get(index)).append(" = ? ");
        }
    }

    public void setConditionFields(List<String> fields) {
        this.sqlWhere = new StringBuilder(" where 1 = 1");
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        fields.forEach(field -> this.sqlWhere.append(" AND ").append((String)field).append(" = ? "));
    }

    public void addRowValues(List<Object> values) {
        this.values = values;
    }
}

