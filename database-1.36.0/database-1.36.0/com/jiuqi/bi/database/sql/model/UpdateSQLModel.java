/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.sql.model;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.ISQLPrintable;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.FieldMap;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UpdateSQLModel
implements Cloneable,
Serializable,
ISQLPrintable {
    private static final long serialVersionUID = -4299463318077414119L;
    private ISQLTable srcTable;
    private SimpleTable destTable;
    private List<FieldMap> keyMaps;

    public UpdateSQLModel(ISQLTable srcTable, SimpleTable destTable) {
        this.srcTable = srcTable;
        this.destTable = destTable;
        this.keyMaps = new ArrayList<FieldMap>();
    }

    public UpdateSQLModel() {
        this(null, null);
    }

    public ISQLTable srcTable() {
        return this.srcTable;
    }

    public void setSrcTable(ISQLTable srcTable) {
        this.srcTable = srcTable;
    }

    public SimpleTable destTable() {
        return this.destTable;
    }

    public void setDestTable(SimpleTable destTable) {
        this.destTable = destTable;
    }

    public List<FieldMap> keyMaps() {
        return this.keyMaps;
    }

    @Override
    public void toSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        ISQLModelPrinter printer = database.createModelPrinter(this);
        if (printer != null) {
            printer.printSQL(buffer, database, this, options);
        } else if ("Oracle".equalsIgnoreCase(database.getName()) || database.isDatabase("DB2")) {
            this.toOracle(buffer, database, options);
        } else if ("MSSQL".equalsIgnoreCase(database.getName())) {
            this.toMSSQL(buffer, database, options);
        } else if ("MYSQL".equalsIgnoreCase(database.getName())) {
            this.toMySQLSQL(buffer, database, options);
        } else if (database.isDatabase("HANA") || database.isDatabase("DM")) {
            this.toHANA(buffer, database, options);
        } else {
            throw new SQLModelException("\u9047\u5230\u5c1a\u672a\u652f\u6301\u7684\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + database.getName());
        }
    }

    private void toMySQLSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        IDatabase mysql = DatabaseManager.getInstance().findDatabaseByName("MYSQL");
        buffer.append("UPDATE ");
        this.destTable.toSQL(buffer, mysql, 0);
        buffer.append(" INNER JOIN ");
        this.srcTable.toSQL(buffer, mysql, 0);
        buffer.append(" ON ");
        this._printJoinList(buffer, mysql);
        buffer.append(" SET ");
        this._printSetList(buffer, mysql);
    }

    private void toHANA(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("UPDATE ");
        this.destTable.toSQL(buffer, database, 0);
        buffer.append(" SET ");
        this._printSetList(buffer, database);
        buffer.append(" FROM ");
        this.destTable.toSQL(buffer, database, 0);
        buffer.append(" INNER JOIN ");
        this.srcTable.toSQL(buffer, database, 0);
        buffer.append(" ON ");
        this._printJoinList(buffer, database);
    }

    private void toOracle(StringBuilder buffer, IDatabase database, int options) {
        buffer.append("UPDATE ");
        buffer.append(this.destTable.name()).append(" dt ");
        buffer.append(" SET (");
        for (ISQLField sqlField : this.destTable.fields()) {
            buffer.append(sqlField.fieldName()).append(",");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append(") = (");
        buffer.append(" SELECT ");
        for (ISQLField sqlField : this.srcTable.fields()) {
            buffer.append(sqlField.fieldName()).append(",");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append(" FROM ");
        buffer.append(this.srcTable.name()).append(" st ");
        buffer.append(" WHERE ");
        boolean andDot = false;
        for (FieldMap fieldMap : this.keyMaps) {
            if (andDot) {
                buffer.append(" and ");
            }
            andDot = true;
            buffer.append("st.").append(fieldMap.left().fieldName()).append("=").append("dt.").append(fieldMap.right().fieldName());
        }
        buffer.append(")");
        buffer.append(" WHERE EXISTS (SELECT 1 FROM ");
        buffer.append(this.srcTable.name()).append(" it").append(" WHERE ");
        andDot = false;
        for (FieldMap fieldMap : this.keyMaps) {
            if (andDot) {
                buffer.append(" and ");
            }
            andDot = true;
            buffer.append("dt.").append(fieldMap.left().fieldName()).append("=").append("it.").append(fieldMap.right().fieldName());
        }
        buffer.append(")");
    }

    private void toMSSQL(StringBuilder buffer, IDatabase database, int options) throws SQLModelException {
        buffer.append("UPDATE ");
        if (!StringUtils.isEmpty((String)this.destTable.alias()) && (options & 1) == 0) {
            buffer.append(this.destTable.alias());
        } else {
            buffer.append(this.destTable.name());
        }
        buffer.append(" SET ");
        this._printSetList(buffer, database);
        buffer.append(" FROM ");
        this.destTable.toSQL(buffer, database, 0);
        buffer.append(" INNER JOIN ");
        this.srcTable.toSQL(buffer, database, 0);
        buffer.append(" ON ");
        this._printJoinList(buffer, database);
    }

    public void _printJoinList(StringBuilder buffer, IDatabase database) throws SQLModelException {
        boolean started = false;
        for (FieldMap map : this.keyMaps) {
            if (started) {
                buffer.append(" AND ");
            } else {
                started = true;
            }
            map.toSQL(buffer, database, 0);
        }
        if (!started) {
            throw new SQLModelException("\u672a\u6307\u5b9a\u4efb\u4f55\u5173\u8054\u4e3b\u952e\u3002");
        }
    }

    public void _printSetList(StringBuilder buffer, IDatabase database) throws SQLModelException {
        HashSet<ISQLField> keyFields = new HashSet<ISQLField>();
        for (FieldMap map : this.keyMaps) {
            keyFields.add(map.left());
            keyFields.add(map.right());
        }
        boolean started = false;
        for (int i = 0; i < this.srcTable.fields().size(); ++i) {
            ISQLField srcField = this.srcTable.fields().get(i);
            ISQLField destField = this.destTable.fields().get(i);
            if (keyFields.contains(srcField) || keyFields.contains(destField)) continue;
            if (started) {
                buffer.append(',');
            } else {
                started = true;
            }
            destField.toSQL(buffer, database, 1);
            buffer.append('=');
            srcField.toSQL(buffer, database, 1);
        }
        if (!started) {
            throw new SQLModelException("\u6ca1\u6709\u9700\u8981\u66f4\u65b0\u7684\u5b57\u6bb5\u3002");
        }
    }

    @Override
    public String toSQL(IDatabase database, int options) throws SQLModelException {
        StringBuilder buffer = new StringBuilder();
        this.toSQL(buffer, database, options);
        return buffer.toString();
    }
}

