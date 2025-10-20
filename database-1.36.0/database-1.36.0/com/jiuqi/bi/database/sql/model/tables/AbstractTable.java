/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.GroupMode;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLFilter;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.ISQLTableListener;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SQLTableListener;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.SQLField;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTable
implements Serializable,
Cloneable,
ISQLTable {
    private static final long serialVersionUID = -4657303580189996294L;
    private static final ISQLTableListener DUMMY_LISTENER = new SQLTableListener();
    private String name;
    private String alias;
    private List<ISQLField> fields;
    private Set<ISQLFilter> whereFilters;
    private Set<ISQLFilter> havingFilters;
    private List<SortField> sortFields;
    private boolean distinct;

    public AbstractTable() {
        this(null, null);
    }

    public AbstractTable(String name) {
        this(name, null);
    }

    public AbstractTable(String name, String alias) {
        this.name = name;
        this.alias = alias;
        this.fields = new ArrayList<ISQLField>();
        this.whereFilters = new HashSet<ISQLFilter>();
        this.havingFilters = new HashSet<ISQLFilter>();
        this.sortFields = new ArrayList<SortField>();
        this.distinct = false;
    }

    @Override
    public String name() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String alias() {
        return this.alias;
    }

    public boolean isDistinct() {
        return this.distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public List<ISQLField> fields() {
        return this.fields;
    }

    @Override
    public Collection<ISQLFilter> whereFilters() {
        return this.whereFilters;
    }

    @Override
    public Collection<ISQLFilter> havingFilters() {
        return this.havingFilters;
    }

    @Override
    public List<SortField> sortFields() {
        return this.sortFields;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.isSimpleMode()) {
            this.toTableSQL(buffer, database, options);
        } else {
            this.toSQL(buffer, database, options, DUMMY_LISTENER);
        }
    }

    public void toSQL(StringBuilder buffer, IDatabase database, int options, ISQLTableListener listener) throws SQLModelException {
        listener.beginPrint(buffer, database, options);
        this.genSelectSQL(buffer, database, options);
        listener.afterSelect(buffer, database, options);
        this.genFromSQL(buffer, database, options);
        listener.afterFrom(buffer, database, options);
        this.genWhereSQL(buffer, database, options);
        listener.afterWhere(buffer, database, options);
        this.genGroupSQL(buffer, database, options);
        listener.afterGroupBy(buffer, database, options);
        this.genHavingSQL(buffer, database, options);
        listener.afterHaving(buffer, database, options);
        this.genOrderSQL(buffer, database, options);
        listener.endPrint(buffer, database, options);
    }

    @Override
    public abstract boolean isSimpleMode();

    @Override
    public boolean isGroupMode() {
        for (ISQLField field : this.fields) {
            if (field.groupMode() == null || field.groupMode() == GroupMode.NONE) continue;
            return true;
        }
        return false;
    }

    protected void toTableSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        SQLHelper.printName(database, this.name(), buffer);
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    protected void genSelectSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("SELECT ");
        if (this.distinct) {
            buffer.append("DISTINCT ");
        }
        boolean started = false;
        for (ISQLField field : this.fields()) {
            if (!field.isVisible()) continue;
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            field.toSQL(buffer, database, 0);
        }
    }

    protected void genFromSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append(" FROM ");
        this.toTableSQL(buffer, database, options);
    }

    protected void genWhereSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.whereFilters().isEmpty()) {
            return;
        }
        buffer.append(" WHERE ");
        boolean started = false;
        for (ISQLFilter filter : this.whereFilters) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            filter.toSQL(buffer, database, 1);
        }
    }

    protected void genGroupSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        List groupFields = this.fields().stream().filter(f -> f.groupMode() == GroupMode.GROUP).collect(Collectors.toList());
        if (groupFields.isEmpty()) {
            return;
        }
        buffer.append(" GROUP BY ");
        boolean started = false;
        for (ISQLField field : groupFields) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            field.toSQL(buffer, database, 1);
        }
    }

    protected void genHavingSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.havingFilters().isEmpty()) {
            return;
        }
        buffer.append(" HAVING ");
        boolean started = false;
        for (ISQLFilter filter : this.havingFilters()) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            filter.toSQL(buffer, database, 1);
        }
    }

    protected void genOrderSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.sortFields.isEmpty()) {
            return;
        }
        buffer.append(" ORDER BY ");
        boolean started = false;
        for (SortField field : this.sortFields()) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            field.toSQL(buffer, database, options & 0xFFFFFFFE);
        }
    }

    @Override
    public final String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder sql = new StringBuilder();
        this.toSQL(sql, database, options);
        return sql.toString();
    }

    public ISQLField addField(String fieldName) {
        return this.addField(fieldName, null);
    }

    public ISQLField addField(String fieldName, String fieldAlias) {
        SQLField field = new SQLField(this, fieldName, fieldAlias);
        this.addField(field);
        return field;
    }

    public void addField(ISQLField field) {
        this.fields.add(field);
    }

    @Override
    public ISQLField createField(String name, String alias) {
        ISQLField field = this.findField(name, alias);
        return field == null ? new SQLField(this, name, alias) : field;
    }

    protected ISQLField findField(String name, String alias) {
        for (ISQLField field : this.fields()) {
            if (!StringUtils.equalsIgnoreCase((String)field.name(), (String)name) || !StringUtils.equalsIgnoreCase((String)field.alias(), (String)alias)) continue;
            return field;
        }
        return null;
    }

    @Override
    public final ISQLField createField(String name) {
        return this.createField(name, null);
    }

    public boolean contains(ISQLField field, Class<? extends ISQLField> fieldClass) {
        if (!this.fields.contains(field)) {
            return false;
        }
        return fieldClass.isAssignableFrom(field.getClass());
    }

    @Override
    public ISQLField findField(String name) {
        for (ISQLField field : this.fields()) {
            if (!name.equalsIgnoreCase(field.name())) continue;
            return field;
        }
        return null;
    }

    @Override
    public ISQLField findByFieldName(String fieldName) {
        for (ISQLField field : this.fields()) {
            if (!fieldName.equalsIgnoreCase(field.fieldName())) continue;
            return field;
        }
        return null;
    }

    @Override
    public String tableName() {
        return StringUtils.isEmpty((String)this.alias()) ? this.name() : this.alias();
    }
}

