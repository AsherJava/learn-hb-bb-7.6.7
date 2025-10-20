/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.metadata;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicForeignKey;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicProcedure;
import com.jiuqi.bi.database.metadata.LogicProcedureParameter;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import java.sql.SQLException;
import java.util.List;

public interface ISQLMetadata {
    public void setProperty(String var1, String var2);

    public String getUser() throws SQLException, SQLMetadataException;

    public List<String> getSchemas() throws SQLException, SQLMetadataException;

    public String getDefaultSchema() throws SQLException, SQLMetadataException;

    public List<LogicTable> getUserTables() throws SQLException, SQLMetadataException;

    public List<LogicTable> searchUserTables(String var1) throws SQLException, SQLMetadataException;

    public List<LogicTable> searchUserTables(String var1, int var2) throws SQLException, SQLMetadataException;

    public List<LogicTable> getOtherTables() throws SQLException, SQLMetadataException;

    public List<LogicTable> searchOtherTables(String var1) throws SQLException, SQLMetadataException;

    public List<LogicTable> searchOtherTables(String var1, int var2) throws SQLException, SQLMetadataException;

    public List<LogicTable> getAllTables() throws SQLException, SQLMetadataException;

    public List<LogicTable> searchAllTables(String var1, String var2, int var3) throws SQLException, SQLMetadataException;

    public List<LogicTable> searchAllTables(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicTable> searchAllTables(String var1) throws SQLException, SQLMetadataException;

    public List<LogicTable> getUserTables(int var1) throws SQLException, SQLMetadataException;

    public List<LogicTable> getOtherTables(int var1) throws SQLException, SQLMetadataException;

    public List<LogicTable> getAllTable(int var1) throws SQLException, SQLMetadataException;

    public LogicTable getTableByName(String var1) throws SQLException, SQLMetadataException;

    public LogicTable getTableByName(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicField> getFieldsByTableName(String var1) throws SQLException, SQLMetadataException;

    public List<LogicField> getFieldsByTableName(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicIndex> getIndexesByTableName(String var1) throws SQLException, SQLMetadataException;

    public LogicIndex getIndexByName(String var1) throws SQLException, SQLMetadataException;

    public List<LogicIndex> getIndexesByTableName(String var1, String var2) throws SQLException, SQLMetadataException;

    public LogicPrimaryKey getPrimaryKeyByTableName(String var1) throws SQLException, SQLMetadataException;

    public LogicPrimaryKey getPrimaryKeyByName(String var1) throws SQLException, SQLMetadataException;

    public LogicPrimaryKey getPrimaryKeyByTableName(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicForeignKey> getLogicForeignKeyBySchemaName(String var1) throws SQLException, SQLMetadataException;

    public List<LogicForeignKey> getLogicForeignKeyByTableName(String var1) throws SQLException, SQLMetadataException;

    public List<LogicForeignKey> getLogicForeignKeyByTableName(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicProcedure> getUserProcedures() throws SQLException, SQLMetadataException;

    public List<LogicProcedure> getOtherProcedures() throws SQLException, SQLMetadataException;

    public LogicProcedure getProcedureByName(String var1) throws SQLException, SQLMetadataException;

    public LogicProcedure getProcedureByName(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicProcedureParameter> getParametersByProcedureName(String var1) throws SQLException, SQLMetadataException;

    public List<LogicProcedureParameter> getParametersByProcedureName(String var1, String var2) throws SQLException, SQLMetadataException;

    public boolean existsData(String var1) throws SQLException;

    public long getDatabaseTimestamp() throws SQLException;

    public long compareTimestamp(long var1) throws SQLException;

    public List<LogicTable> batchQueryTablesByName(List<String> var1, String var2) throws SQLException, SQLMetadataException;

    public List<List<LogicField>> batchQueryFieldsByTableName(List<String> var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicPrimaryKey> batchQueryPrimaryKeysByTableName(List<String> var1, String var2) throws SQLException, SQLMetadataException;

    public List<List<LogicIndex>> batchQueryIndexesByTableName(List<String> var1, String var2) throws SQLException, SQLMetadataException;

    public boolean existsTable(String var1, String var2) throws SQLException, SQLMetadataException;

    public List<LogicTable> getAllTables(boolean var1, int var2, int var3) throws SQLException, SQLMetadataException;

    default public List<LogicTable> getUserTables(boolean containTitle, int start, int count) throws SQLException, SQLMetadataException {
        List<LogicTable> all = this.getUserTables();
        int end = Math.min(count + start, all.size());
        return all.subList(start, end);
    }
}

