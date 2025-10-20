/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.loader.AbstractTableLoader;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.ISQLField;
import com.jiuqi.bi.database.sql.model.ISQLTable;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.fields.RowNumField;
import com.jiuqi.bi.database.sql.model.tables.QueryTable;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.types.DataTypes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public abstract class AbstractMergeLoader
extends AbstractTableLoader {
    public static final String ROWID_NAME = "TMP_ROWID";
    protected static final String SRC_TABLE_NAME = "S";
    protected static final String SRC_QUERY_NAME = "M";
    protected static final String DEST_TABLE_NAME = "D";

    public AbstractMergeLoader(Connection conn, IDatabase database) {
        super(conn, database);
        this.option = 2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int execute() throws TableLoaderException {
        int n;
        this.validate();
        if (this.getSourceTable() instanceof SimpleTable) {
            int tableSize = this.getTableSize(this.getSourceTable().name());
            return this.mergeData(this.getSourceTable().name(), tableSize, false);
        }
        if (this.supportSubQuery() && (this.option & 2) == 0) {
            int recordSize = this.getRecordSize(this.getSourceTable());
            return this.mergeData(this.getSourceTable(), recordSize);
        }
        String tmpTableName = AbstractMergeLoader.createName();
        this.createTempTable(tmpTableName);
        try {
            int tableSize = this.fillData(tmpTableName);
            this.buildIndex(tmpTableName);
            n = this.mergeData(tmpTableName, tableSize, true);
        }
        catch (Throwable throwable) {
            try {
                this.deleteTable(tmpTableName);
                throw throwable;
            }
            catch (SQLException e) {
                throw new TableLoaderException(e);
            }
        }
        this.deleteTable(tmpTableName);
        return n;
    }

    private int getTableSize(String tableExpr) throws SQLException, TableLoaderException {
        try (PreparedStatement stmt = this.conn.prepareStatement("SELECT COUNT(1) RECSIZE FROM " + tableExpr);
             ResultSet rs = stmt.executeQuery();){
            if (rs.next()) {
                int n = rs.getInt(1);
                return n;
            }
            throw new TableLoaderException("\u65e0\u6cd5\u83b7\u53d6\u8868[" + tableExpr + "]\u7684\u8bb0\u5f55\u6570\u3002");
        }
    }

    private int getRecordSize(ISQLTable table) throws SQLException, TableLoaderException {
        StringBuilder sql = new StringBuilder("(");
        try {
            table.toSQL(sql, this.database, 1);
        }
        catch (SQLModelException e) {
            throw new TableLoaderException(e);
        }
        sql.append(") T");
        return this.getTableSize(sql.toString());
    }

    protected boolean supportSubQuery() {
        return false;
    }

    protected int mergeData(ISQLTable srcTable, int recordSize) throws SQLException, TableLoaderException {
        if (srcTable instanceof SimpleTable) {
            return this.mergeData(srcTable.name(), recordSize, false);
        }
        throw new UnsupportedOperationException("\u672a\u652f\u6301\u67e5\u8be2\u7ed3\u679c\u7684Merge\u64cd\u4f5c");
    }

    protected abstract int mergeData(String var1, int var2, boolean var3) throws SQLException, TableLoaderException;

    protected void createTempTable(String tmpTableName) throws SQLException {
        String createSQL = this.getTmpTableCreateSQL(tmpTableName);
        this.execSQL(createSQL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String getTmpTableCreateSQL(String tmpTableName) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE ");
        if ("col".equals(this.database.getDescriptor().storageMode())) {
            sql.append("COLUMN ");
        }
        sql.append("TABLE ");
        sql.append(tmpTableName).append('(').append(ROWID_NAME).append(' ').append(this.getRowNumFieldType());
        HashSet<String> destFields = new HashSet<String>();
        for (LoadFieldMap map : this.fieldMaps) {
            destFields.add(map.getDestField().name().toUpperCase());
        }
        try (Statement stmt = this.conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.destTable.name() + " WHERE 1=0");){
            ISQLInterpretor interpretor = this.database.createSQLInterpretor(this.conn);
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                String fieldName = rs.getMetaData().getColumnName(i);
                if (!destFields.contains(fieldName.toUpperCase())) continue;
                LogicField col = new LogicField();
                col.setFieldName(rs.getMetaData().getColumnName(i));
                col.setDataType(DataTypes.fromJavaSQLType((int)rs.getMetaData().getColumnType(i)));
                col.setDataTypeName(rs.getMetaData().getColumnTypeName(i));
                col.setSize(rs.getMetaData().getColumnDisplaySize(i));
                col.setScale(rs.getMetaData().getScale(i));
                col.setPrecision(rs.getMetaData().getPrecision(i));
                col.setRawType(rs.getMetaData().getColumnType(i));
                String typeSql = interpretor.getDataTypeSQL(col);
                sql.append(',').append(fieldName).append(' ').append(typeSql);
            }
        }
        sql.append(')');
        return sql.toString();
    }

    protected boolean genColumnFieldPrecision(ResultSet rs, int colIdx, StringBuilder sql) throws SQLException {
        int sqlType = rs.getMetaData().getColumnType(colIdx);
        if (sqlType == 6 || sqlType == 8 || sqlType == 4 || sqlType == 2004 || sqlType == 2005) {
            return false;
        }
        int precision = rs.getMetaData().getPrecision(colIdx);
        if (precision <= 0) {
            return false;
        }
        switch (DataTypes.fromJavaSQLType((ResultSetMetaData)rs.getMetaData(), (int)colIdx)) {
            case 6: {
                sql.append('(').append(precision).append(')');
                return true;
            }
            case 3: 
            case 5: 
            case 8: {
                int scale = rs.getMetaData().getScale(colIdx);
                if (precision > 0 && scale >= 0) {
                    sql.append('(').append(precision).append(',').append(scale).append(')');
                }
                return true;
            }
        }
        return false;
    }

    protected String getRowNumFieldType() {
        if (this.database.isDatabase("MSSQL")) {
            return "DECIMAL(20) IDENTITY(1,1)";
        }
        if (this.database.isDatabase("MYSQL")) {
            return "BIGINT PRIMARY KEY AUTO_INCREMENT";
        }
        return "DECIMAL(20)";
    }

    protected int fillData(String tmpTableName) throws TableLoaderException {
        ITableLoader loader = this.database.createInsertLoader(this.conn);
        QueryTable qryTable = QueryTable.wrapperTable(this.sourceTable, SRC_QUERY_NAME);
        ISQLField rowID = this.createRowIDField(qryTable);
        if (rowID != null) {
            qryTable.addField(rowID);
        }
        loader.setSourceTable(qryTable);
        SimpleTable tmpTable = new SimpleTable(tmpTableName);
        loader.setDestTable(tmpTable);
        for (LoadFieldMap rawMap : this.fieldMaps) {
            ISQLField srcField = qryTable.findField(rawMap.getSourceField().fieldName());
            ISQLField destField = tmpTable.addField(rawMap.getDestField().name());
            LoadFieldMap newMap = new LoadFieldMap(srcField, destField, rawMap.isKey());
            loader.getFieldMaps().add(newMap);
        }
        if (rowID != null) {
            LoadFieldMap rowIDMap = new LoadFieldMap(rowID, tmpTable.addField(ROWID_NAME));
            loader.getFieldMaps().add(rowIDMap);
        }
        loader.setTransactionSize(this.transactionSize);
        loader.setListener(this.listener());
        return loader.execute();
    }

    protected ISQLField createRowIDField(ISQLTable qryTable) throws TableLoaderException {
        return new RowNumField(qryTable, ROWID_NAME);
    }

    protected void buildIndex(String tmpTableName) throws SQLException, TableLoaderException {
        String pkSQL = this.getPKIndexSQL(tmpTableName);
        this.execSQL(pkSQL);
        String rowIDIndexSQL = "CREATE INDEX " + tmpTableName + "_IDX_ROWID ON " + tmpTableName + "(" + ROWID_NAME + ")";
        this.execSQL(rowIDIndexSQL);
    }

    private String getPKIndexSQL(String tmpTableName) throws TableLoaderException {
        StringBuilder sql = new StringBuilder("CREATE INDEX ");
        sql.append(tmpTableName).append("_IDX_PK ON ").append(tmpTableName).append('(');
        boolean started = false;
        for (LoadFieldMap map : this.fieldMaps) {
            if (!map.isKey()) continue;
            if (started) {
                sql.append(',');
            } else {
                started = true;
            }
            sql.append(map.getDestField().name());
        }
        sql.append(')');
        if (!started) {
            throw new TableLoaderException("\u672a\u6307\u5b9a\u6570\u636e\u8868\u4e3b\u952e\u5b57\u6bb5\u3002");
        }
        return sql.toString();
    }

    protected void deleteTable(String tableName) throws SQLException {
        String dropSQL = "DROP TABLE " + tableName;
        this.execSQL(dropSQL);
    }

    protected void buildCondition(StringBuilder sql) {
        boolean started = false;
        for (LoadFieldMap map : this.fieldMaps) {
            if (!map.isKey()) continue;
            if (started) {
                sql.append(" AND ");
            } else {
                started = true;
            }
            if (map.isNullable()) {
                sql.append("((D.").append(map.getDestField().name()).append(" IS NULL AND M.").append(map.getDestField().name()).append(" IS NULL) OR ");
            }
            sql.append("M.").append(map.getDestField().name()).append("=D.").append(map.getDestField().name());
            if (!map.isNullable()) continue;
            sql.append(')');
        }
    }

    protected void buildUpdate(StringBuilder sql) throws TableLoaderException {
        sql.append("UPDATE SET ");
        boolean started = false;
        for (LoadFieldMap map : this.fieldMaps) {
            if (map.isKey()) continue;
            if (started) {
                sql.append(',');
            } else {
                started = true;
            }
            sql.append("D.").append(map.getDestField().name()).append("=M.").append(map.getDestField().name());
        }
        if (!started) {
            throw new TableLoaderException("\u6ca1\u6709\u66f4\u65b0\u4efb\u4f55\u6570\u636e\u5b57\u6bb5\u3002");
        }
    }

    protected void buildInsert(StringBuilder sql) {
        sql.append("INSERT (");
        boolean started = false;
        for (LoadFieldMap map : this.fieldMaps) {
            if (started) {
                sql.append(',');
            } else {
                started = true;
            }
            sql.append("D.").append(map.getDestField().name());
        }
        sql.append(") VALUES(");
        started = false;
        for (LoadFieldMap map : this.fieldMaps) {
            if (started) {
                sql.append(',');
            } else {
                started = true;
            }
            sql.append("M.").append(map.getDestField().name());
        }
        sql.append(')');
    }
}

