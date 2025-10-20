/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLHelper;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import java.io.Serializable;

public class CreateSelectModel
implements Cloneable,
Serializable,
ISQLPrintable {
    private static final long serialVersionUID = 4765366968645959600L;
    private String destTableName;
    private ISQLTable srcTable;

    public CreateSelectModel() {
    }

    public CreateSelectModel(String destTableName, ISQLTable srcTable) {
        this.destTableName = destTableName;
        this.srcTable = srcTable;
    }

    public String destTableName() {
        return this.destTableName;
    }

    public void setDestTableName(String destTableName) {
        this.destTableName = destTableName;
    }

    public ISQLTable srcTable() {
        return this.srcTable;
    }

    public void setSrcTable(ISQLTable srcTable) {
        this.srcTable = srcTable;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        ISQLModelPrinter printer = database.createModelPrinter(this);
        if (printer == null) {
            this.printDefault(buffer, database);
        } else {
            printer.printSQL(buffer, database, this, options);
        }
    }

    public void printDefault(StringBuilder buffer, IDatabase database) throws SQLModelException {
        ISQLTable printTable = this.srcTable.isSimpleMode() ? QueryTable.wrapperTable(this.srcTable) : this.srcTable;
        buffer.append("CREATE TABLE ");
        SQLHelper.printName(database, this.destTableName, buffer);
        buffer.append(" AS ");
        printTable.toSQL(buffer, database, 0);
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("CREATE TABLE ").append(this.destTableName).append(" AS ").append(this.srcTable);
        return buffer.toString();
    }
}

