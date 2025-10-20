/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.ModelHelper;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.fields.SQLField;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryTable
extends AbstractTable {
    private static final long serialVersionUID = -1403780938301122333L;
    private ISQLTable innerTable;

    public QueryTable() {
    }

    public QueryTable(String name) {
        this(name, null);
    }

    public QueryTable(String name, String alias) {
        this(new SimpleTable(name), alias);
    }

    public QueryTable(ISQLTable innerTable, String alias) {
        super(null, alias);
        this.innerTable = innerTable;
    }

    public ISQLTable innerTable() {
        return this.innerTable;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public ISQLField createField(String name, String alias) {
        SQLField innerField = new SQLField(this.innerTable, name);
        this.innerTable.fields().add(innerField);
        RefField refField = new RefField(this, innerField, alias);
        return refField;
    }

    @Override
    public ISQLField addField(String fieldName, String fieldAlias) {
        ISQLField field = this.createField(fieldName, fieldAlias);
        this.addField(field);
        return field;
    }

    @Override
    public boolean isSimpleMode() {
        return false;
    }

    @Override
    protected void toTableSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.innerTable.isSimpleMode()) {
            this.innerTable.toSQL(buffer, database, 0);
        } else {
            buffer.append('(');
            this.innerTable.toSQL(buffer, database, 1);
            buffer.append(')');
            SQLHelper.printAlias(database, this.innerTable.alias(), 0, buffer);
        }
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.isSimpleMode()) {
            super.toSQL(buffer, database, options);
        } else {
            boolean needAlias;
            boolean bl = needAlias = (options & 1) == 0 && !StringUtils.isEmpty((String)this.alias());
            if (needAlias) {
                buffer.append('(');
            }
            super.toSQL(buffer, database, 1);
            if (needAlias) {
                buffer.append(") ");
                SQLHelper.printName(database, this.alias(), buffer);
            }
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("name: ").append(this.name()).append(StringUtils.LINE_SEPARATOR);
        buffer.append("alias: ").append(this.alias()).append(StringUtils.LINE_SEPARATOR);
        buffer.append("inner table: ").append(this.innerTable).append(StringUtils.LINE_SEPARATOR);
        buffer.append("fields: ");
        ModelHelper.printFieldNames(buffer, this.fields());
        buffer.append(StringUtils.LINE_SEPARATOR);
        buffer.append("order by: ").append(this.sortFields()).append(StringUtils.LINE_SEPARATOR);
        return buffer.toString();
    }

    public static QueryTable wrapperTable(ISQLTable innerTable, String alias, Map<ISQLField, ISQLField> fieldMap) {
        QueryTable table = new QueryTable(innerTable, alias);
        for (ISQLField field : innerTable.fields()) {
            if (!field.isVisible()) continue;
            RefField refField = new RefField(table, field);
            table.fields().add(refField);
            fieldMap.put(field, refField);
        }
        for (SortField innerSortField : innerTable.sortFields()) {
            ISQLField field = fieldMap.get(innerSortField.field());
            if (field == null) {
                RefField innerField = new RefField(innerTable, innerSortField.field());
                innerField.setAlias(QueryTable.getFieldAlias(innerTable));
                innerField.setGroupMode(innerSortField.field().groupMode());
                innerTable.fields().add(innerField);
                field = innerField;
            }
            SortField outerSortField = new SortField(table, field, innerSortField.sortMode());
            outerSortField.setNullsMode(innerSortField.nullsMode());
            table.sortFields().add(outerSortField);
        }
        innerTable.sortFields().clear();
        return table;
    }

    protected static String getFieldAlias(ISQLTable table) {
        int i = 1;
        String alias = "F_" + i;
        while (table.findByFieldName(alias) != null) {
            alias = "F_" + ++i;
        }
        return alias;
    }

    public static QueryTable wrapperTable(ISQLTable innerTable, String alias) {
        return QueryTable.wrapperTable(innerTable, alias, new HashMap<ISQLField, ISQLField>());
    }

    public static QueryTable wrapperTable(ISQLTable innerTable) {
        return QueryTable.wrapperTable(innerTable, null);
    }

    @Override
    public boolean containsParameter() {
        return this.innerTable.containsParameter();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> tables = new ArrayList<ISQLTable>(1);
        tables.add(this.innerTable);
        return tables;
    }
}

