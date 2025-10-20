/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model.tables;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.AbstractTable;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class RefTable
extends AbstractTable {
    private static final long serialVersionUID = 2673109760757791367L;
    private ISQLTable table;

    public RefTable() {
    }

    public RefTable(ISQLTable table) {
        this(table, null);
    }

    public RefTable(ISQLTable table, String alias) {
        super(null, alias);
        this.table = table;
    }

    public ISQLTable table() {
        return this.table;
    }

    public void setTable(ISQLTable table) {
        this.table = table;
    }

    @Override
    public String name() {
        return StringUtils.isEmpty((String)this.table.alias()) ? this.table.name() : this.table.alias();
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public boolean isSimpleMode() {
        return true;
    }

    @Override
    protected void toTableSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        if (StringUtils.isEmpty((String)this.table().alias())) {
            SQLHelper.printName(database, this.table().name(), buffer);
        } else {
            SQLHelper.printName(database, this.table().alias(), buffer);
        }
        SQLHelper.printAlias(database, this.alias(), options, buffer);
    }

    public String toString() {
        if (StringUtils.isEmpty((String)this.alias())) {
            return this.name();
        }
        return this.name() + " " + this.alias();
    }

    @Override
    public boolean containsParameter() {
        return false;
    }

    @Override
    public List<ISQLTable> getInnerTables() {
        ArrayList<ISQLTable> tables = new ArrayList<ISQLTable>(1);
        tables.add(this.table);
        return tables;
    }
}

