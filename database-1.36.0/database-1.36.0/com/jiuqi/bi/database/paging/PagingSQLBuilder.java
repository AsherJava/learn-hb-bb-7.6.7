/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.paging;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.database.paging.OrderField;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public abstract class PagingSQLBuilder
implements IPagingSQLBuilder {
    public static final String DEFAULT_INNER_TABLEALIAS = "T";
    private String withSQL;
    protected String innerTableAlias = "T";
    protected String rawSQL;
    protected String filter;
    protected List<OrderField> orderFields = new ArrayList<OrderField>();

    @Override
    public String getWithSQL() {
        return this.withSQL;
    }

    @Override
    public void setWithSQL(String withSQL) {
        this.withSQL = withSQL;
    }

    @Override
    public String getInnerTableAlias() {
        return this.innerTableAlias;
    }

    @Override
    public void setInnerTableAlias(String tableAlias) {
        this.innerTableAlias = tableAlias;
    }

    @Override
    public String getRawSQL() {
        return this.rawSQL;
    }

    @Override
    public void setRawSQL(String rawSQL) {
        this.rawSQL = rawSQL;
    }

    @Override
    public String getFilter() {
        return this.filter;
    }

    @Override
    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public List<OrderField> getOrderFields() {
        return this.orderFields;
    }

    @Override
    public String buildSQL(int startRow, int endRow) throws DBException {
        StringBuilder buffer = new StringBuilder();
        if (this.withSQL != null) {
            buffer.append(this.withSQL);
        }
        this.buildSQL(startRow, endRow, buffer);
        return buffer.toString();
    }

    protected abstract void buildSQL(int var1, int var2, StringBuilder var3) throws DBException;

    protected void appendOrderBy(StringBuilder buffer) {
        if (this.getOrderFields().isEmpty()) {
            return;
        }
        buffer.append(" ORDER BY ");
        boolean started = false;
        for (OrderField field : this.getOrderFields()) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            buffer.append(field.getFieldName());
            if (StringUtils.isEmpty((String)field.getOrderMode())) continue;
            buffer.append(' ').append(field.getOrderMode());
        }
    }

    protected void appendOrderBy(StringBuilder sql, String tableName) {
        if (this.getOrderFields().isEmpty()) {
            return;
        }
        sql.append(" ORDER BY ");
        boolean started = false;
        for (OrderField field : this.getOrderFields()) {
            if (started) {
                sql.append(',');
            } else {
                started = true;
            }
            sql.append(tableName).append('.');
            int p = field.getFieldName().indexOf(46);
            if (p == -1) {
                sql.append(field.getFieldName());
            } else {
                sql.append(field.getFieldName().substring(p + 1));
            }
            if (StringUtils.isEmpty((String)field.getOrderMode())) continue;
            sql.append(' ').append(field.getOrderMode());
        }
    }
}

