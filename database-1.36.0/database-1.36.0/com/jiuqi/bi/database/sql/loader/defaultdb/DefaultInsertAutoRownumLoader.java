/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader.defaultdb;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.loader.AbstractTableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.loader.defaultdb.DefaultInsertLoader;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.EvalField;
import com.jiuqi.bi.database.sql.model.mysql.QueryTableWithRownum;
import java.sql.Connection;

public final class DefaultInsertAutoRownumLoader
extends AbstractTableLoader {
    private String rownumFieldName;

    public DefaultInsertAutoRownumLoader(Connection conn, IDatabase database, String rownumFieldName) {
        super(conn, database);
        this.rownumFieldName = rownumFieldName;
    }

    @Override
    public int execute() throws TableLoaderException {
        DefaultInsertLoader loader = new DefaultInsertLoader(this.conn, this.database);
        boolean addMySqlRownumInit = this.database.isDatabase("MYSQL");
        QueryTableWithRownum qryTable = new QueryTableWithRownum(this.sourceTable, "M", addMySqlRownumInit);
        ISQLField rowID = this.createRowIDField(qryTable);
        if (rowID != null) {
            qryTable.addField(rowID);
        }
        loader.setSourceTable(qryTable);
        loader.setDestTable(this.destTable);
        this.destTable.fields().clear();
        for (LoadFieldMap rawMap : this.fieldMaps) {
            ISQLField srcField = qryTable.findField(rawMap.getSourceField().fieldName());
            ISQLField destField = this.destTable.addField(rawMap.getDestField().name());
            LoadFieldMap newMap = new LoadFieldMap(srcField, destField, rawMap.isKey());
            loader.getFieldMaps().add(newMap);
        }
        if (rowID != null) {
            LoadFieldMap rowIDMap = new LoadFieldMap(rowID, this.destTable.addField(this.rownumFieldName));
            loader.getFieldMaps().add(rowIDMap);
        }
        loader.setTransactionSize(this.transactionSize);
        loader.setListener(this.listener());
        return loader.execute();
    }

    private ISQLField createRowIDField(ISQLTable qryTable) throws TableLoaderException {
        String rowIdName = "row_num";
        if (this.database.isDatabase("ORACLE") || this.database.isDatabase("DM") || this.database.isDatabase("KINGBASE") || this.database.isDatabase("Informix") || this.database.isDatabase("OSCAR") || this.database.isDatabase("GAUSSDB100") || this.database.isDatabase("KINGBASE8") || this.database.isDatabase("H2")) {
            return new EvalField(qryTable, "ROWNUM", rowIdName);
        }
        if (this.database.isDatabase("MSSQL")) {
            return null;
        }
        if (this.database.isDatabase("MYSQL") || this.database.isDatabase("GBASE")) {
            return new EvalField(qryTable, "@rownum:=@rownum+1", rowIdName);
        }
        if (this.database.isDatabase("HANA")) {
            StringBuilder sql = new StringBuilder("ROW_NUMBER() OVER (");
            boolean started = false;
            for (LoadFieldMap map : this.fieldMaps) {
                ISQLField field;
                if (!map.isKey() || (field = qryTable.findField(map.getSourceField().fieldName())) == null) continue;
                if (started) {
                    sql.append(',');
                } else {
                    started = true;
                    sql.append("ORDER BY ");
                }
                try {
                    field.toSQL(sql, this.database, 1);
                }
                catch (SQLModelException e) {
                    throw new TableLoaderException(e);
                }
            }
            sql.append(')');
            return new EvalField(qryTable, sql.toString(), rowIdName);
        }
        if (this.database.getDescriptor().supportWindowFunctions()) {
            return new EvalField(qryTable, "ROW_NUMBER() OVER()", rowIdName);
        }
        throw new UnsupportedOperationException("\u5c1a\u672a\u652f\u6301\u7684\u6570\u636e\u5e93\u7c7b\u578b\uff1a" + this.database);
    }
}

