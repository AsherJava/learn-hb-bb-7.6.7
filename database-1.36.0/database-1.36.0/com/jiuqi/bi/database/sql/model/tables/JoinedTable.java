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
import com.jiuqi.bi.database.sql.model.fields.RefField;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.database.sql.model.tables.FullJoinConvertor;
import com.jiuqi.bi.database.sql.model.tables.SubTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinedTable
extends AbstractTable
implements Serializable,
Cloneable,
ISQLTable {
    private static final long serialVersionUID = 4841459922315991206L;
    private ISQLTable mainTable;
    private List<SubTable> subTables = new ArrayList<SubTable>();

    public JoinedTable() {
        this(null);
    }

    public JoinedTable(String alias) {
        super(null, alias);
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public void setName(String name) {
    }

    public ISQLTable mainTable() {
        return this.mainTable;
    }

    public void setMainTable(ISQLTable mainTable) {
        this.mainTable = mainTable;
    }

    public List<SubTable> subTables() {
        return this.subTables;
    }

    @Override
    public boolean isSimpleMode() {
        return false;
    }

    @Override
    protected void genFromSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append(" FROM ");
        if (this.mainTable.isSimpleMode()) {
            this.mainTable.toSQL(buffer, database, 0);
        } else {
            buffer.append('(');
            this.mainTable.toSQL(buffer, database, 1);
            buffer.append(')');
            SQLHelper.printAlias(database, this.mainTable.alias(), 0, buffer);
        }
        for (SubTable subTable : this.subTables) {
            subTable.toSQL(buffer, database, this);
        }
    }

    @Override
    public ISQLField createField(String name, String alias) {
        ISQLField field = this.mainTable.createField(name, null);
        RefField refField = new RefField(this, field, alias);
        return refField;
    }

    @Override
    public ISQLField addField(String fieldName, String fieldAlias) {
        ISQLField field = this.createField(fieldName, fieldAlias);
        this.addField(field);
        return field;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("alias: ").append(this.alias()).append(StringUtils.LINE_SEPARATOR);
        buffer.append("fields: ");
        ModelHelper.printFieldNames(buffer, this.fields());
        buffer.append(StringUtils.LINE_SEPARATOR);
        buffer.append("main table: ");
        this.printTableName(buffer, this.mainTable);
        buffer.append(StringUtils.LINE_SEPARATOR);
        buffer.append("sub tables:");
        boolean started = false;
        for (SubTable subTable : this.subTables) {
            if (started) {
                buffer.append(", ");
            } else {
                started = false;
            }
            buffer.append(' ').append((Object)subTable.joinMode()).append(" JOIN ");
            this.printTableName(buffer, subTable.table());
        }
        buffer.append(StringUtils.LINE_SEPARATOR);
        buffer.append("where: ").append(this.whereFilters()).append(StringUtils.LINE_SEPARATOR);
        buffer.append("having: ").append(this.havingFilters()).append(StringUtils.LINE_SEPARATOR);
        buffer.append("order by: ").append(this.sortFields());
        return buffer.toString();
    }

    private void printTableName(StringBuilder buffer, ISQLTable table) {
        if (table == null) {
            return;
        }
        if (!StringUtils.isEmpty((String)table.name())) {
            buffer.append(table.name());
        }
        if (!StringUtils.isEmpty((String)table.alias())) {
            if (!StringUtils.isEmpty((String)table.name())) {
                buffer.append(' ');
            }
            buffer.append(table.alias());
        }
    }

    public SubTable findSubTable(String subTableName) {
        for (SubTable sub : this.subTables) {
            if (!subTableName.equalsIgnoreCase(sub.table().name())) continue;
            return sub;
        }
        return null;
    }

    @Override
    public boolean containsParameter() {
        if (this.mainTable.containsParameter()) {
            return true;
        }
        for (SubTable subTalble : this.subTables) {
            if (!subTalble.table().containsParameter()) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> innerTables = new ArrayList<ISQLTable>();
        innerTables.add(this.mainTable);
        for (SubTable subTable : this.subTables) {
            innerTables.add(subTable.table());
        }
        return innerTables;
    }

    public List<ISQLTable> getJoinedTables() {
        ArrayList<ISQLTable> joinTables = new ArrayList<ISQLTable>(this.subTables.size() + 1);
        joinTables.add(this.mainTable);
        for (SubTable subTable : this.subTables) {
            joinTables.add(subTable.table());
        }
        return joinTables;
    }

    public Map<ISQLField, ISQLField> fullJoinToUnion() throws SQLModelException {
        FullJoinConvertor convertor = new FullJoinConvertor(this);
        convertor.convert();
        return convertor.getSubFieldMaps();
    }
}

