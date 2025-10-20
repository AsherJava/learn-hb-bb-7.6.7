/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuerySQLModel
implements Cloneable,
Serializable,
ISQLPrintable {
    private static final long serialVersionUID = -4212313908644693679L;
    private ISQLTable table;
    private List<ISQLTable> refTables;

    public QuerySQLModel() {
        this(null);
    }

    public QuerySQLModel(ISQLTable table) {
        this.table = table;
        this.refTables = new ArrayList<ISQLTable>();
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.genWithTables(buffer, database, options);
        this.genMainTable(buffer, database, options);
    }

    private void genWithTables(StringBuilder buffer, IDatabase databse, int options) throws SQLModelException {
        if (this.refTables.isEmpty()) {
            return;
        }
        buffer.append("WITH ");
        boolean started = false;
        for (ISQLTable refTable : this.refTables) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            buffer.append(refTable.alias()).append(" AS (");
            refTable.toSQL(buffer, databse, 1);
            buffer.append(')');
        }
        buffer.append(" /*END WITH*/ ");
    }

    private void genMainTable(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        this.table().toSQL(buffer, database, 1);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public ISQLTable table() {
        return this.table;
    }

    public void setTable(ISQLTable table) {
        this.table = table;
    }

    public List<ISQLTable> refTables() {
        return this.refTables;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("with: ").append(this.refTables).append(StringUtils.LINE_SEPARATOR);
        buffer.append("table: ").append(this.table);
        return buffer.toString();
    }
}

