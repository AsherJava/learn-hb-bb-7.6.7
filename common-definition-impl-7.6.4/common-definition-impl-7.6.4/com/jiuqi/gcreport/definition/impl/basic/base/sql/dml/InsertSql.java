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

public class InsertSql
implements EntDmlSql {
    private String tableName;
    private List<Object> values;
    private StringBuilder sqlColumn;
    private StringBuilder sqlValue;

    public static InsertSql newInstance(String tableName) {
        return new InsertSql(tableName);
    }

    InsertSql(String tableName) {
        this.tableName = tableName;
        this.sqlColumn = new StringBuilder();
        this.sqlValue = new StringBuilder();
    }

    @Override
    public String getSql() {
        return "INSERT INTO " + this.tableName + " (" + this.sqlColumn + ") VALUES (" + this.sqlValue + ") ";
    }

    @Override
    public EntPreparedStatementSetter getPreStatementSetter() {
        return PsSetterFactory.newSetter(this.values);
    }

    public void setColumns(List<String> fields) {
        this.sqlColumn = new StringBuilder();
        this.sqlValue = new StringBuilder();
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        this.sqlColumn.append(fields.get(0));
        this.sqlValue.append("?");
        for (int index = 1; index < fields.size(); ++index) {
            this.sqlColumn.append(",").append(fields.get(index));
            this.sqlValue.append(",?");
        }
    }

    public void addRowValues(List<Object> values) {
        this.values = values;
    }
}

