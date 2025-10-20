/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.sql.DataTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicForeignKey;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicProcedure;
import com.jiuqi.bi.database.metadata.LogicProcedureParameter;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.ProcedureParameterMode;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.metadata.TableComparator;
import com.jiuqi.bi.database.sql.util.SQLPrinter;
import com.jiuqi.bi.sql.DataTypes;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class DefaultSQLMetadata
implements ISQLMetadata {
    protected Connection conn;
    protected Properties props;
    private static final String[] TABLE_TYPES = new String[]{"TABLE", "VIEW", "SYNONYM"};

    public DefaultSQLMetadata(Connection conn) {
        if (conn == null) {
            throw new NullPointerException("\u6570\u636e\u5e93\u8fde\u63a5\u4e3a\u7a7a");
        }
        this.conn = conn;
    }

    @Override
    public void setProperty(String key, String value) {
        if (this.props == null) {
            this.props = new Properties();
        }
        this.props.put(key, value);
    }

    public String getProperty(String key) {
        return this.props == null ? null : (String)this.props.get(key);
    }

    @Override
    public String getUser() throws SQLException {
        return this.conn.getMetaData().getUserName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getSchemas() throws SQLException, SQLMetadataException {
        try (ResultSet rs = this.conn.getMetaData().getSchemas();){
            ArrayList<String> list = new ArrayList<String>();
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            ArrayList<String> arrayList = list;
            return arrayList;
        }
    }

    @Override
    public String getDefaultSchema() throws SQLException {
        return this.conn.getMetaData().getUserName();
    }

    @Override
    public List<LogicTable> getUserTables() throws SQLException, SQLMetadataException {
        int type = 7;
        return this.getUserTables(type);
    }

    @Override
    public List<LogicTable> getOtherTables() throws SQLException, SQLMetadataException {
        int type = 7;
        return this.getOtherTables(type);
    }

    @Override
    public List<LogicTable> getUserTables(int type) throws SQLException, SQLMetadataException {
        TableComparator ct = new TableComparator();
        ArrayList<LogicTable> result = new ArrayList<LogicTable>();
        if ((1 & type) != 0) {
            List<LogicTable> databaseTables = this.getTables(new String[]{"TABLE"}, null, null);
            Collections.sort(databaseTables, ct);
            result.addAll(databaseTables);
        }
        if ((2 & type) != 0) {
            List<LogicTable> viewTables = this.getTables(new String[]{"VIEW"}, null, null);
            Collections.sort(viewTables, ct);
            result.addAll(viewTables);
        }
        if ((4 & type) != 0) {
            List<LogicTable> synonymTables = this.getTables(new String[]{"SYNONYM"}, null, null);
            Collections.sort(synonymTables, ct);
            result.addAll(synonymTables);
        }
        return result;
    }

    @Override
    public List<LogicTable> getOtherTables(int type) throws SQLException, SQLMetadataException {
        String defaultSchema;
        ArrayList<LogicTable> result = new ArrayList<LogicTable>();
        TableComparator ct = new TableComparator();
        if ((1 & type) != 0) {
            List<LogicTable> databaseTables = this.getTables(new String[]{"TABLE"}, null, null, false);
            Collections.sort(databaseTables, ct);
            result.addAll(databaseTables);
        }
        if ((2 & type) != 0) {
            List<LogicTable> viewTables = this.getTables(new String[]{"VIEW"}, null, null, false);
            Collections.sort(viewTables, ct);
            result.addAll(viewTables);
        }
        if ((4 & type) != 0) {
            List<LogicTable> synonymTables = this.getTables(new String[]{"SYNONYM"}, null, null, false);
            Collections.sort(synonymTables, ct);
            result.addAll(synonymTables);
        }
        if ((defaultSchema = this.getDefaultSchema()) != null) {
            ArrayList<LogicTable> nr = new ArrayList<LogicTable>();
            for (LogicTable lt : result) {
                if (defaultSchema.equals(lt.getOwner())) continue;
                nr.add(lt);
            }
            result = nr;
        }
        return result;
    }

    @Override
    public final LogicTable getTableByName(String tableName) throws SQLException, SQLMetadataException {
        return this.getTableByName(tableName, null);
    }

    @Override
    public LogicTable getTableByName(String tableName, String owner) throws SQLException, SQLMetadataException {
        List<Object> result = new ArrayList();
        result = this.getTables(new String[]{"TABLE"}, tableName, owner);
        if (result != null && result.size() > 0) {
            return (LogicTable)result.get(0);
        }
        result = this.getTables(new String[]{"VIEW"}, tableName, owner);
        if (result != null && result.size() > 0) {
            return (LogicTable)result.get(0);
        }
        result = this.getTables(new String[]{"SYNONYM"}, tableName, owner);
        if (result != null && result.size() > 0) {
            return (LogicTable)result.get(0);
        }
        return null;
    }

    @Override
    public final List<LogicField> getFieldsByTableName(String tableName) throws SQLException, SQLMetadataException {
        return this.getFieldsByTableName(tableName, null);
    }

    @Override
    public List<LogicField> getFieldsByTableName(String tableName, String owner) throws SQLException, SQLMetadataException {
        return this.getFields(tableName, owner, null);
    }

    @Override
    public final List<LogicIndex> getIndexesByTableName(String tableName) throws SQLException, SQLMetadataException {
        return this.getIndexesByTableName(tableName, null);
    }

    @Override
    public LogicIndex getIndexByName(String name) throws SQLException, SQLMetadataException {
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<LogicIndex> getIndexesByTableName(String tableName, String owner) throws SQLException, SQLMetadataException {
        LogicPrimaryKey pk = this.getPrimaryKeyByTableName(tableName, owner);
        DatabaseMetaData dbmd = this.conn.getMetaData();
        String schema = owner;
        if (schema == null) {
            schema = this.getDefaultSchema();
        }
        try (ResultSet rs = dbmd.getIndexInfo(null, schema, tableName, false, false);){
            ArrayList<LogicIndex> indexes = new ArrayList<LogicIndex>();
            while (rs.next()) {
                LogicIndex index = new LogicIndex();
                String table_name = rs.getString("TABLE_NAME");
                String index_name = rs.getString("INDEX_NAME");
                if (pk != null && pk.getPkName().equals(index_name)) continue;
                String column_name = rs.getString("COLUMN_NAME");
                boolean unique = !rs.getBoolean("NON_UNIQUE");
                String sort = rs.getString("ASC_OR_DESC");
                if (table_name == null || index_name == null || column_name == null) continue;
                boolean indexExsit = false;
                for (int i = 0; i < indexes.size(); ++i) {
                    if (!index_name.equals(((LogicIndex)indexes.get(i)).getIndexName())) continue;
                    index = (LogicIndex)indexes.get(i);
                    indexExsit = true;
                    break;
                }
                index.setTableName(tableName);
                index.setIndexName(index_name);
                index.setUnique(unique);
                LogicIndexField indexField = new LogicIndexField();
                indexField.setFieldName(column_name);
                if (sort != null) {
                    if (sort.equals("A")) {
                        indexField.setSortType(1);
                    } else if (sort.equals("D")) {
                        indexField.setSortType(-1);
                    }
                }
                index.getIndexFields().add(indexField);
                if (indexExsit) continue;
                indexes.add(index);
            }
            ArrayList<LogicIndex> arrayList = indexes;
            return arrayList;
        }
    }

    @Override
    public final LogicPrimaryKey getPrimaryKeyByTableName(String tableName) throws SQLException, SQLMetadataException {
        return this.getPrimaryKeyByTableName(tableName, null);
    }

    @Override
    public LogicPrimaryKey getPrimaryKeyByName(String name) throws SQLException, SQLMetadataException {
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public LogicPrimaryKey getPrimaryKeyByTableName(String tableName, String owner) throws SQLException, SQLMetadataException {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        String schema = owner;
        if (schema == null) {
            schema = this.getDefaultSchema();
        }
        try (ResultSet rs = dbmd.getPrimaryKeys(null, schema, tableName);){
            LogicPrimaryKey logicPrimaryKey;
            LogicPrimaryKey pk = new LogicPrimaryKey();
            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                String pk_name = rs.getString("PK_NAME");
                String column_name = rs.getString("COLUMN_NAME");
                if (table_name == null || pk_name == null || column_name == null) continue;
                pk.setPkName(pk_name);
                pk.setTableName(table_name);
                pk.getFieldNames().add(column_name);
            }
            if (StringUtils.isEmpty((String)pk.getPkName())) {
                logicPrimaryKey = null;
                return logicPrimaryKey;
            }
            logicPrimaryKey = pk;
            return logicPrimaryKey;
        }
    }

    @Override
    public List<LogicForeignKey> getLogicForeignKeyBySchemaName(String schemaName) throws SQLException, SQLMetadataException {
        return new ArrayList<LogicForeignKey>(0);
    }

    @Override
    public List<LogicForeignKey> getLogicForeignKeyByTableName(String tableName) throws SQLException, SQLMetadataException {
        return new ArrayList<LogicForeignKey>(0);
    }

    @Override
    public List<LogicForeignKey> getLogicForeignKeyByTableName(String tableName, String schema) throws SQLException, SQLMetadataException {
        return new ArrayList<LogicForeignKey>(0);
    }

    @Override
    public List<LogicProcedure> getUserProcedures() throws SQLException, SQLMetadataException {
        return this.getProcedures(null, null);
    }

    @Override
    public List<LogicProcedure> getOtherProcedures() throws SQLException, SQLMetadataException {
        return new ArrayList<LogicProcedure>();
    }

    @Override
    public final LogicProcedure getProcedureByName(String name) throws SQLException, SQLMetadataException {
        if (StringUtils.isNotEmpty((String)name)) {
            return this.getProcedureByName(name, null);
        }
        return null;
    }

    @Override
    public LogicProcedure getProcedureByName(String name, String owner) throws SQLException, SQLMetadataException {
        List<LogicProcedure> lps;
        if (StringUtils.isNotEmpty((String)name) && (lps = this.getProcedures(name, owner)).size() > 0) {
            return lps.get(0);
        }
        return null;
    }

    @Override
    public final List<LogicProcedureParameter> getParametersByProcedureName(String procedureName) throws SQLException, SQLMetadataException {
        if (StringUtils.isEmpty((String)procedureName)) {
            return null;
        }
        return this.getParametersByProcedureName(procedureName, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<LogicProcedureParameter> getParametersByProcedureName(String procedureName, String owner) throws SQLException, SQLMetadataException {
        if (StringUtils.isNotEmpty((String)procedureName)) {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String dbname = dbmd.getDatabaseProductName().toLowerCase();
            ResultSet rs = null;
            String userName = owner;
            if (dbname.indexOf("db2") != -1 && owner == null) {
                userName = dbmd.getUserName().toUpperCase();
            }
            rs = dbmd.getProcedureColumns(null, userName, procedureName, null);
            try {
                ArrayList<LogicProcedureParameter> lpps = new ArrayList<LogicProcedureParameter>();
                while (rs.next()) {
                    LogicProcedureParameter lpp = new LogicProcedureParameter();
                    int column_type = rs.getInt("COLUMN_TYPE");
                    if (1 == column_type) {
                        lpp.setMode(ProcedureParameterMode.IN);
                    } else if (2 == column_type) {
                        lpp.setMode(ProcedureParameterMode.INOUT);
                    } else {
                        if (4 != column_type) continue;
                        lpp.setMode(ProcedureParameterMode.OUT);
                    }
                    lpp.setDataType(DataTypes.fromJavaSQLType((int)rs.getInt("DATA_TYPE")));
                    lpp.setName(procedureName);
                    lpps.add(lpp);
                }
                ArrayList<LogicProcedureParameter> arrayList = lpps;
                return arrayList;
            }
            finally {
                rs.close();
            }
        }
        return null;
    }

    public List<LogicTable> getTables(String[] types, String name, String schema) throws SQLException {
        return this.getTables(types, name, schema, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<LogicTable> getTables(String[] types, String name, String schema, boolean useDefaultSchema) throws SQLException {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        if (schema == null && useDefaultSchema) {
            schema = this.getDefaultSchema();
        }
        if (schema != null && schema.contains("_")) {
            schema = schema.replaceAll("_", "\\\\_");
        }
        if (name != null && name.contains("_")) {
            name = name.replaceAll("_", "\\\\_");
        }
        try (ResultSet rs = dbmd.getTables(null, schema, name, types);){
            ArrayList<LogicTable> tables = new ArrayList<LogicTable>();
            while (rs.next()) {
                LogicTable table = new LogicTable();
                String schemeName = rs.getString(2);
                String tableName = rs.getString(3);
                if (this.isRecyclebinTable(tableName)) continue;
                table.setOwner(schemeName);
                table.setName(tableName.toUpperCase());
                table.setDescription(rs.getString(5));
                String tableType = rs.getString(4);
                if (TABLE_TYPES[0].equalsIgnoreCase(tableType)) {
                    table.setType(1);
                } else if (TABLE_TYPES[1].equalsIgnoreCase(tableType)) {
                    table.setType(2);
                } else {
                    table.setType(4);
                }
                tables.add(table);
            }
            ArrayList<LogicTable> arrayList = tables;
            return arrayList;
        }
    }

    protected int transtoBIDataType(String dataTypeName, int dataType, int dataSize, int scale) {
        if (2005 == dataType) {
            return 12;
        }
        return DataTypes.fromJavaSQLType((int)dataType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<LogicField> getFields(String tableName, String schema, String fieldName) throws SQLException {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        if (schema == null) {
            schema = this.getDefaultSchema();
        }
        try (ResultSet rs = dbmd.getColumns(null, schema, tableName, fieldName);){
            ArrayList<LogicField> fields = new ArrayList<LogicField>();
            while (rs.next()) {
                LogicField field = new LogicField();
                String fildName = rs.getString("COLUMN_NAME");
                field.setFieldName(fildName.toUpperCase());
                field.setFieldTitle(rs.getString("REMARKS"));
                int nullAble = rs.getInt("NULLABLE");
                if (nullAble == 0) {
                    field.setNullable(false);
                } else {
                    field.setNullable(true);
                }
                field.setPrecision(rs.getInt("COLUMN_SIZE"));
                field.setScale(rs.getInt("DECIMAL_DIGITS"));
                field.setSize(rs.getInt("COLUMN_SIZE"));
                field.setDefaultValue(rs.getString("COLUMN_DEF"));
                field.setRawType(rs.getInt("DATA_TYPE"));
                field.setDataTypeName(rs.getString("TYPE_NAME"));
                field.setDataType(this.transtoBIDataType(rs.getString("TYPE_NAME"), rs.getInt("DATA_TYPE"), rs.getInt("COLUMN_SIZE"), rs.getInt("DECIMAL_DIGITS")));
                fields.add(field);
            }
            ArrayList<LogicField> arrayList = fields;
            return arrayList;
        }
    }

    private boolean isRecyclebinTable(String tableName) {
        return tableName.startsWith("BIN$");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<LogicProcedure> getProcedures(String name, String schema) throws SQLException {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        if (schema == null) {
            schema = this.getDefaultSchema();
        }
        try (ResultSet rs = dbmd.getProcedures(null, schema, name);){
            ArrayList<LogicProcedure> procedures = new ArrayList<LogicProcedure>();
            while (rs.next()) {
                LogicProcedure procedure = new LogicProcedure();
                procedure.setDescription(rs.getString("REMARKS"));
                String pName = rs.getString("PROCEDURE_NAME");
                int index = pName.indexOf(";");
                if (index > 0) {
                    procedure.setName(pName.substring(0, index));
                } else {
                    procedure.setName(pName);
                }
                procedure.setOwner(rs.getString("PROCEDURE_SCHEM"));
                procedures.add(procedure);
            }
            ArrayList<LogicProcedure> arrayList = procedures;
            return arrayList;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean existsData(String tableName) throws SQLException {
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(this.conn);
        StringBuilder buf = new StringBuilder("SELECT 1 FROM ");
        SQLPrinter.printName(db, tableName, buf);
        String sql = buf.toString();
        try (PreparedStatement stmt = this.conn.prepareStatement(sql);){
            boolean bl;
            ResultSet rs = stmt.executeQuery();
            try {
                bl = rs.next();
            }
            catch (Throwable throwable) {
                rs.close();
                throw throwable;
            }
            rs.close();
            return bl;
        }
    }

    @Override
    public List<LogicTable> searchUserTables(String tableName) throws SQLException, SQLMetadataException {
        return this.searchUserTables(tableName, 7);
    }

    @Override
    public List<LogicTable> searchOtherTables(String tableName) throws SQLException, SQLMetadataException {
        return this.searchOtherTables(tableName, 7);
    }

    @Override
    public List<LogicTable> getAllTables() throws SQLException, SQLMetadataException {
        return this.getAllTable(7);
    }

    @Override
    public List<LogicTable> getAllTable(int type) throws SQLException, SQLMetadataException {
        List<LogicTable> result = this.getUserTables(type);
        result.addAll(this.getOtherTables(type));
        return result;
    }

    @Override
    public List<LogicTable> searchAllTables(String tableName, String owner) throws SQLException, SQLMetadataException {
        return this.searchAllTables(tableName, owner, 7);
    }

    @Override
    public final List<LogicTable> searchAllTables(String tableName) throws SQLException, SQLMetadataException {
        return this.searchAllTables(tableName, null, 7);
    }

    @Override
    public List<LogicTable> searchAllTables(String tableName, String schemaName, int type) throws SQLException, SQLMetadataException {
        ArrayList<LogicTable> result = new ArrayList<LogicTable>();
        if (StringUtils.isEmpty((String)schemaName)) {
            result.addAll(this.searchUserTables(tableName, type));
            result.addAll(this.searchOtherTables(tableName, type));
        } else {
            if (this.getUser().toUpperCase().contains(schemaName.toUpperCase())) {
                result.addAll(this.searchUserTables(tableName, type));
            }
            result.addAll(this.searchOtherTables(tableName, schemaName, type));
        }
        return result;
    }

    private List<LogicTable> searchOtherTables(String tableName, String schemaName, int type) throws SQLException, SQLMetadataException {
        List<LogicTable> userTables = this.getOtherTables(type);
        Iterator<LogicTable> iterator = userTables.iterator();
        while (iterator.hasNext()) {
            LogicTable lt = iterator.next();
            if (lt.getOwner().toUpperCase().contains(schemaName.toUpperCase()) && lt.getName().toUpperCase().contains(tableName.toUpperCase())) continue;
            iterator.remove();
        }
        return userTables;
    }

    @Override
    public List<LogicTable> searchUserTables(String tableName, int type) throws SQLException, SQLMetadataException {
        List<LogicTable> userTables = this.getUserTables(type);
        if (StringUtils.isEmpty((String)tableName)) {
            return userTables;
        }
        Iterator<LogicTable> iterator = userTables.iterator();
        while (iterator.hasNext()) {
            LogicTable lt = iterator.next();
            if (lt.getName().toUpperCase().contains(tableName.toUpperCase())) continue;
            iterator.remove();
        }
        return userTables;
    }

    @Override
    public List<LogicTable> searchOtherTables(String tableName, int type) throws SQLException, SQLMetadataException {
        List<LogicTable> userTables = this.getOtherTables(type);
        if (StringUtils.isEmpty((String)tableName)) {
            return userTables;
        }
        Iterator<LogicTable> iterator = userTables.iterator();
        while (iterator.hasNext()) {
            LogicTable lt = iterator.next();
            if (lt.getName().toUpperCase().contains(tableName.toUpperCase())) continue;
            iterator.remove();
        }
        return userTables;
    }

    @Override
    public long getDatabaseTimestamp() throws SQLException {
        return -1L;
    }

    @Override
    public long compareTimestamp(long currentTimestamp) throws SQLException {
        long databaseTimestamp = this.getDatabaseTimestamp();
        if (databaseTimestamp == -1L) {
            return -1L;
        }
        return Math.abs(currentTimestamp - databaseTimestamp);
    }

    @Override
    public List<LogicTable> batchQueryTablesByName(List<String> tableNames, String onwer) throws SQLException, SQLMetadataException {
        ArrayList<LogicTable> tables = new ArrayList<LogicTable>();
        for (String name : tableNames) {
            tables.add(this.getTableByName(name, onwer));
        }
        return tables;
    }

    @Override
    public List<List<LogicField>> batchQueryFieldsByTableName(List<String> tableNames, String onwer) throws SQLException, SQLMetadataException {
        ArrayList<List<LogicField>> list = new ArrayList<List<LogicField>>();
        for (String name : tableNames) {
            list.add(this.getFieldsByTableName(name, onwer));
        }
        return list;
    }

    @Override
    public List<LogicPrimaryKey> batchQueryPrimaryKeysByTableName(List<String> tableNames, String onwer) throws SQLException, SQLMetadataException {
        ArrayList<LogicPrimaryKey> keys = new ArrayList<LogicPrimaryKey>();
        for (String name : tableNames) {
            keys.add(this.getPrimaryKeyByTableName(name, onwer));
        }
        return keys;
    }

    @Override
    public List<List<LogicIndex>> batchQueryIndexesByTableName(List<String> tableNames, String onwer) throws SQLException, SQLMetadataException {
        ArrayList<List<LogicIndex>> list = new ArrayList<List<LogicIndex>>();
        for (String name : tableNames) {
            list.add(this.getIndexesByTableName(name, onwer));
        }
        return list;
    }

    @Override
    public boolean existsTable(String tableName, String onwer) throws SQLException, SQLMetadataException {
        return this.getTableByName(tableName, onwer) != null;
    }

    @Override
    public List<LogicTable> getAllTables(boolean containTitle, int start, int count) throws SQLException, SQLMetadataException {
        List<LogicTable> all = this.getAllTables();
        int end = Math.min(count + start, all.size());
        return all.subList(start, end);
    }
}

