/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;

public class InsertSQLModel
implements Cloneable,
Serializable,
ISQLPrintable {
    private static final long serialVersionUID = 3870517142707726057L;
    private ISQLTable srcTable;
    private ISQLTable destTable;

    public InsertSQLModel(ISQLTable srcTable, ISQLTable destTable) {
        this.srcTable = srcTable;
        this.destTable = destTable;
    }

    public InsertSQLModel() {
        this(null, null);
    }

    public ISQLTable srcTable() {
        return this.srcTable;
    }

    public void setSrcTable(ISQLTable srcTable) {
        this.srcTable = srcTable;
    }

    public ISQLTable destTable() {
        return this.destTable;
    }

    public void setDestTable(ISQLTable destTable) {
        this.destTable = destTable;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("INSERT INTO ").append(this.destTable.name()).append("(");
        boolean started = false;
        for (ISQLField field : this.destTable.fields()) {
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            buffer.append(field.name());
        }
        buffer.append(") ");
        ISQLTable printTable = this.srcTable.isSimpleMode() ? QueryTable.wrapperTable(this.srcTable) : this.srcTable;
        printTable.toSQL(buffer, database, 1);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("src table: ").append(this.srcTable).append(StringUtils.LINE_SEPARATOR);
        buffer.append("dest table: ").append(this.destTable);
        return buffer.toString();
    }
}

