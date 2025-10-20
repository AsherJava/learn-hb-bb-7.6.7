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
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.fields.SQLField;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.sql.model.tables.UnionMode;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class UnionedTable
extends AbstractTable {
    private static final long serialVersionUID = -2260389411992112047L;
    private List<ISQLTable> tables = new ArrayList<ISQLTable>();
    private UnionMode unionMode = UnionMode.UNIONALL;
    private ISQLTable innerTable = new SimpleTable("U");
    private static final String INNER_TABLENAME = "U";

    public UnionedTable() {
        this(null);
    }

    public UnionedTable(String alias) {
        super(null, alias);
    }

    @Override
    public String name() {
        return null;
    }

    public List<ISQLTable> tables() {
        return this.tables;
    }

    public UnionMode unionMode() {
        return this.unionMode;
    }

    public void setUnionMode(UnionMode unionMode) {
        this.unionMode = unionMode;
    }

    public void createDefaultFields() {
        this.fields().clear();
        if (this.tables.isEmpty()) {
            return;
        }
        for (ISQLField field : this.tables.get(0).fields()) {
            if (!field.isVisible()) continue;
            ISQLField innerField = this.innerTable.createField(field.fieldName(), null);
            RefField newField = new RefField(this, innerField);
            this.fields().add(newField);
        }
    }

    @Override
    public boolean isSimpleMode() {
        return false;
    }

    @Override
    protected void genSelectSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("SELECT ");
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

    @Override
    protected void genFromSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append(" FROM (");
        boolean started = false;
        for (ISQLTable table : this.tables) {
            if (started) {
                buffer.append(' ').append(UnionMode.toSQL(this.unionMode, database)).append(' ');
            } else {
                started = true;
            }
            buffer.append('(');
            table.toSQL(buffer, database, 1);
            buffer.append(')');
        }
        buffer.append(')');
        SQLHelper.printAlias(database, this.innerTable.tableName(), 0, buffer);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("alias: ").append(this.alias()).append(StringUtils.LINE_SEPARATOR).append("Union Mode: ").append((Object)this.unionMode).append(StringUtils.LINE_SEPARATOR).append("tables: ").append(this.tables);
        return buffer.toString();
    }

    @Override
    public boolean containsParameter() {
        for (ISQLTable table : this.tables) {
            if (!table.containsParameter()) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        return new ArrayList<ISQLTable>(this.tables);
    }

    public ISQLTable innerTable() {
        return this.innerTable;
    }

    @Override
    public ISQLField createField(String name, String alias) {
        for (ISQLField field : this.fields()) {
            if (!StringUtils.equalsIgnoreCase((String)field.name(), (String)name) || !StringUtils.equalsIgnoreCase((String)field.alias(), (String)alias)) continue;
            return field;
        }
        SQLField innerField = new SQLField(this.innerTable, name);
        return new RefField(this, innerField, alias);
    }
}

