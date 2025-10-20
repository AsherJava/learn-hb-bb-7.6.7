/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.mysql;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.SortField;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import java.util.HashMap;

public class QueryTableWithRownum
extends QueryTable {
    private static final long serialVersionUID = 7906886948172217974L;
    private boolean addMySqlRownumInit = false;

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        super.toSQL(buffer, database, options);
    }

    @Override
    protected void toTableSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (this.addMySqlRownumInit) {
            buffer.append("(SELECT @rownum:=0) r,");
        }
        super.toTableSQL(buffer, database, options);
    }

    public QueryTableWithRownum(ISQLTable innerTable, String alias, boolean addMySqlRownumInit) {
        super(innerTable, alias);
        HashMap<ISQLField, RefField> fieldMap = new HashMap<ISQLField, RefField>();
        for (ISQLField field : innerTable.fields()) {
            if (!field.isVisible()) continue;
            RefField refField = new RefField(this, field);
            this.fields().add(refField);
            fieldMap.put(field, refField);
        }
        for (SortField innerSortField : innerTable.sortFields()) {
            ISQLField field = (ISQLField)fieldMap.get(innerSortField.field());
            if (field == null) {
                RefField innerField = new RefField(innerTable, innerSortField.field());
                innerField.setAlias(QueryTableWithRownum.getFieldAlias(innerTable));
                innerField.setGroupMode(innerSortField.field().groupMode());
                innerTable.fields().add(innerField);
                field = innerField;
            }
            SortField outerSortField = new SortField(this, field, innerSortField.sortMode());
            outerSortField.setNullsMode(innerSortField.nullsMode());
            this.sortFields().add(outerSortField);
        }
        innerTable.sortFields().clear();
        this.addMySqlRownumInit = addMySqlRownumInit;
    }
}

