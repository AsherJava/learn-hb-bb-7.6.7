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
import java.util.ArrayList;
import java.util.List;

public class DeleteSql
implements EntDmlSql {
    private String tableName;
    private List<Object> values;
    private StringBuilder sqlWhere;

    public static DeleteSql newInstance(String tableName) {
        return new DeleteSql(tableName);
    }

    DeleteSql(String tableName) {
        this.tableName = tableName;
        this.values = new ArrayList<Object>();
        this.sqlWhere = new StringBuilder(" where 1 = 1");
    }

    @Override
    public String getSql() {
        return "DELETE FROM " + this.tableName + this.sqlWhere + " ";
    }

    @Override
    public EntPreparedStatementSetter getPreStatementSetter() {
        return PsSetterFactory.newSetter(this.values);
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

