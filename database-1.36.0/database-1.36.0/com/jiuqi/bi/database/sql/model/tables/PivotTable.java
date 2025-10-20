/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import java.util.ArrayList;
import java.util.List;

public final class PivotTable
extends AbstractTable {
    private static final long serialVersionUID = -7694340213059522280L;
    private ISQLTable innerTable;
    private ISQLField valueField;
    private ISQLField codeField;
    private List<ISQLField> pivotFields;

    public PivotTable() {
        this.pivotFields = new ArrayList<ISQLField>();
    }

    public PivotTable(String name) {
        this(name, null);
    }

    public PivotTable(String name, String alias) {
        this(new SimpleTable(name), alias);
        this.pivotFields = new ArrayList<ISQLField>();
    }

    public PivotTable(ISQLTable innerTable, String alias) {
        super(null, alias);
        this.innerTable = innerTable;
        this.pivotFields = new ArrayList<ISQLField>();
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

    public void setValueField(ISQLField valueField) {
        this.valueField = valueField;
    }

    public ISQLField valueField() {
        return this.valueField;
    }

    public void setCodeField(ISQLField codeField) {
        this.codeField = codeField;
    }

    public ISQLField codeField() {
        return this.codeField;
    }

    public List<ISQLField> pivotFields() {
        return this.pivotFields;
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
        buffer.append(" PIVOT (");
        this.valueField.toSQL(buffer, database, 3);
        buffer.append(" FOR ");
        this.codeField.toSQL(buffer, database, 3);
        buffer.append(" IN (");
        boolean started = false;
        for (ISQLField pivotField : this.pivotFields) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            this.printPivotField(buffer, database, pivotField);
        }
        buffer.append("))");
        SQLHelper.printAlias(database, this.alias(), 0, buffer);
    }

    private void printPivotField(StringBuilder buffer, IDatabase database, ISQLField pivotField) {
        if (database.isDatabase("MSSQL")) {
            buffer.append('[').append(pivotField.name()).append(']');
        } else {
            buffer.append('\'').append(pivotField.name()).append('\'');
        }
        SQLHelper.printAlias(database, pivotField.alias(), 0, buffer);
    }

    @Override
    public boolean isSimpleMode() {
        return false;
    }

    @Override
    public boolean containsParameter() {
        return this.innerTable.containsParameter();
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> innerTables = new ArrayList<ISQLTable>(1);
        innerTables.add(this.innerTable);
        return innerTables;
    }
}

