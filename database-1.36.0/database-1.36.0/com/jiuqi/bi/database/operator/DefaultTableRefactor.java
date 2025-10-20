/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.database.operator;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.operator.ITableExecutableOperation;
import com.jiuqi.bi.database.operator.ITableOperation;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.bi.database.operator.SQLTableOperation;
import com.jiuqi.bi.database.sql.loader.ITableLoader;
import com.jiuqi.bi.database.sql.loader.LoadFieldMap;
import com.jiuqi.bi.database.sql.loader.TableLoaderException;
import com.jiuqi.bi.database.sql.model.fields.SQLField;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterCommentStatement;
import com.jiuqi.bi.database.statement.AlterIndexStatement;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultTableRefactor
implements ITableRefactor {
    private Connection conn;
    private String owner;

    @Override
    public final void setConnection(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<ITableOperation> restructure(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<LoadFieldMap> fieldMapList) throws Exception {
        return this.refactor(table, fields, primaryKey, indexList, fieldMapList, false);
    }

    @Override
    public List<ITableOperation> restructurePreview(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<LoadFieldMap> fieldMapList) throws Exception {
        return this.refactor(table, fields, primaryKey, indexList, fieldMapList, true);
    }

    private final synchronized List<ITableOperation> refactor(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<LoadFieldMap> fieldMapList, boolean previewMode) throws Exception {
        LogicTable destTable;
        ArrayList<ITableOperation> container = new ArrayList<ITableOperation>();
        ISQLMetadata sqlMetadata = DefaultTableRefactor.getSqlMetaData(this.conn);
        String destTableName = table.getName();
        String tableOwner = table.getOwner();
        if (StringUtils.isNotEmpty((String)tableOwner)) {
            this.owner = tableOwner;
        }
        if ((destTable = sqlMetadata.getTableByName(destTableName, this.owner)) == null) {
            this.doPublishNoTable(table, fields, primaryKey, indexList, container, previewMode);
        } else {
            boolean existsData = sqlMetadata.existsData(DefaultTableRefactor.getFullTableName(this.owner, destTableName));
            if (!existsData) {
                this.publishNoData(table, fields, primaryKey, indexList, container, previewMode);
            } else {
                this.publishHasData(table, fields, primaryKey, indexList, fieldMapList, container, previewMode);
            }
        }
        return container;
    }

    private void doPublishNoTable(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<ITableOperation> container, boolean previewMode) throws Exception {
        ISQLMetadata sqlMetadata = DefaultTableRefactor.getSqlMetaData(this.conn);
        String destTableName = table.getName();
        try {
            this.createTable(destTableName, fields, primaryKey, container, previewMode);
            this.createIndex(indexList, container, previewMode);
        }
        catch (Exception e) {
            LogicTable destTable = sqlMetadata.getTableByName(destTableName, this.owner);
            if (destTable != null) {
                this.dropTable(destTableName, container, previewMode);
            }
            throw e;
        }
    }

    private void publishNoData(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<ITableOperation> container, boolean previewMode) throws Exception {
        String destTableName = table.getName();
        String destTaleBakName = null;
        String tempTableName = DefaultTableRefactor.generateTempTableName();
        List<LogicIndex> srcLogicIndexList = null;
        try {
            srcLogicIndexList = DefaultTableRefactor.getSqlMetaData(this.conn).getIndexesByTableName(destTableName, this.owner);
            this.createTempTable(tempTableName, fields, primaryKey, container, previewMode);
            this.dropTableIndex(srcLogicIndexList, container, previewMode);
            this.createTempTableIndex(tempTableName, indexList, container, previewMode);
            destTaleBakName = DefaultTableRefactor.generateTempTableName();
            this.renameSrcTable(destTableName, destTaleBakName, container, previewMode);
            this.renameTempTable(tempTableName, destTableName, container, previewMode);
        }
        catch (Exception e) {
            if (DefaultTableRefactor.getSqlMetaData(this.conn).getTableByName(tempTableName, this.owner) != null) {
                this.dropTempTable(tempTableName, container, previewMode);
            }
            if (DefaultTableRefactor.isTableExist(this.owner, destTaleBakName, this.conn)) {
                this.renameTempTable(destTaleBakName, destTableName, container, previewMode);
            }
            if (DefaultTableRefactor.getSqlMetaData(this.conn).getIndexesByTableName(destTableName, this.owner).isEmpty()) {
                this.createIndex(srcLogicIndexList, container, previewMode);
            }
            throw e;
        }
        if (StringUtils.isNotEmpty((String)destTaleBakName)) {
            this.dropTable(destTaleBakName, container, previewMode);
        }
    }

    private static boolean isTableExist(String owner, String tableName, Connection conn) throws SQLException, SQLMetadataException {
        return StringUtils.isNotEmpty((String)tableName) && DefaultTableRefactor.getSqlMetaData(conn).getTableByName(tableName, owner) != null;
    }

    private void publishHasData(LogicTable table, List<LogicField> fields, LogicPrimaryKey primaryKey, List<LogicIndex> indexList, List<LoadFieldMap> fieldMapList, List<ITableOperation> container, boolean previewMode) throws Exception {
        String destTableName = table.getName();
        String destTaleBakName = null;
        String tempTableName = DefaultTableRefactor.generateTempTableName();
        List<LogicIndex> srcLogicIndexList = null;
        try {
            srcLogicIndexList = DefaultTableRefactor.getSqlMetaData(this.conn).getIndexesByTableName(destTableName, this.owner);
            this.createTable(tempTableName, fields, primaryKey, container, previewMode);
            if (fieldMapList != null) {
                this.copyTableData(destTableName, tempTableName, fieldMapList, container, previewMode);
            }
            this.dropTableIndex(srcLogicIndexList, container, previewMode);
            this.createTempTableIndex(tempTableName, indexList, container, previewMode);
            destTaleBakName = DefaultTableRefactor.generateTempTableName();
            this.renameSrcTable(destTableName, destTaleBakName, container, previewMode);
            this.renameTempTable(tempTableName, destTableName, container, previewMode);
        }
        catch (Exception e) {
            if (DefaultTableRefactor.getSqlMetaData(this.conn).getTableByName(tempTableName, this.owner) != null) {
                this.dropTempTable(tempTableName, container, previewMode);
            }
            if (DefaultTableRefactor.isTableExist(this.owner, destTaleBakName, this.conn)) {
                this.renameTempTable(destTaleBakName, destTableName, container, previewMode);
            }
            if (DefaultTableRefactor.getSqlMetaData(this.conn).getIndexesByTableName(destTableName, this.owner).isEmpty()) {
                this.createIndex(srcLogicIndexList, container, previewMode);
            }
            throw e;
        }
        if (StringUtils.isNotEmpty((String)destTaleBakName)) {
            this.dropTable(destTaleBakName, container, previewMode);
        }
    }

    protected void createTempTable(String tempTableName, List<LogicField> fields, LogicPrimaryKey primaryKey, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        this.createTable(tempTableName, fields, primaryKey, container, previewMode);
    }

    protected void createTempTableIndex(String tempTableName, List<LogicIndex> indexList, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException, SQLMetadataException {
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        ArrayList<String> sqlList = new ArrayList<String>();
        tempTableName = DefaultTableRefactor.getFullTableName(this.owner, tempTableName);
        for (LogicIndex logicIndex : indexList) {
            CreateIndexStatement indexStatement = new CreateIndexStatement("", logicIndex.getIndexName());
            indexStatement.setTableName(tempTableName);
            indexStatement.setUnique(logicIndex.isUnique());
            for (LogicIndexField logicIndexField : logicIndex.getIndexFields()) {
                indexStatement.getLogicIndex().addIndexField(logicIndexField);
            }
            List<String> indexSqlList = sqlInterpretor.createIndex(indexStatement);
            sqlList.addAll(indexSqlList);
        }
        if (!sqlList.isEmpty()) {
            SQLTableOperation sqlTableOperation = new SQLTableOperation("\u521b\u5efa\u4e34\u65f6\u8868\u7684\u7d22\u5f15", sqlList);
            this.execute(sqlTableOperation, container, previewMode);
        }
    }

    protected void dropTempTable(String tempTableName, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        tempTableName = DefaultTableRefactor.getFullTableName(this.owner, tempTableName);
        AlterTableStatement dropStatement = new AlterTableStatement(tempTableName, AlterType.DROP);
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        List<String> dropSqls = sqlInterpretor.alterTable(dropStatement);
        this.execute(new SQLTableOperation("\u5220\u9664\u4e34\u65f6\u8868\u3010" + tempTableName + "\u3011", dropSqls), container, previewMode);
    }

    protected void renameTempTable(String tempTableName, String destTableName, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        tempTableName = DefaultTableRefactor.getFullTableName(this.owner, tempTableName);
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        List<String> renameSqlList = sqlInterpretor.renameTable(tempTableName, destTableName);
        this.execute(new SQLTableOperation("\u4e34\u65f6\u8868\u3010" + tempTableName + "\u3011\u91cd\u547d\u540d\u4e3a\u3010" + destTableName + "\u3011", renameSqlList), container, previewMode);
    }

    protected void createTable(String tableName, List<LogicField> fields, LogicPrimaryKey logicPrimaryKey, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        tableName = DefaultTableRefactor.getFullTableName(this.owner, tableName);
        CreateTableStatement tableStatement = new CreateTableStatement("", tableName);
        tableStatement.getColumns().addAll(fields);
        if (logicPrimaryKey != null) {
            tableStatement.getPrimaryKeys().addAll(logicPrimaryKey.getFieldNames());
            tableStatement.setPkName(logicPrimaryKey.getPkName());
        }
        List<String> createTableSql = sqlInterpretor.createTable(tableStatement);
        for (LogicField logicField : fields) {
            if (!StringUtils.isNotEmpty((String)logicField.getFieldTitle())) continue;
            AlterCommentStatement alterCommentStatement = new AlterCommentStatement(tableName, logicField, logicField.getFieldTitle(), AlterType.MODIFY);
            createTableSql.addAll(sqlInterpretor.alterComment(alterCommentStatement));
        }
        this.execute(new SQLTableOperation("\u521b\u5efa\u8868\u3010" + tableName + "\u3011", createTableSql), container, previewMode);
    }

    private static final String getFullTableName(String owner, String tableName) {
        return StringUtils.isNotEmpty((String)owner) ? owner + "." + tableName : tableName;
    }

    public final void execute(ITableExecutableOperation operation, List<ITableOperation> container, boolean previewMode) throws SQLException {
        container.add(operation);
        if (!previewMode) {
            operation.execute(this.conn);
        }
    }

    protected void dropTableIndex(List<LogicIndex> logicIndexList, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        ArrayList<String> sqlList = new ArrayList<String>();
        SQLTableOperation sqlTableOperation = new SQLTableOperation("\u5220\u9664\u6e90\u8868\u7684\u7d22\u5f15", sqlList);
        for (LogicIndex logicIndex : logicIndexList) {
            AlterIndexStatement dropStatement = new AlterIndexStatement(logicIndex.getIndexName(), AlterType.DROP);
            dropStatement.setTableName(DefaultTableRefactor.getFullTableName(this.owner, logicIndex.getTableName()));
            ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
            List<String> dropSqls = sqlInterpretor.alterIndex(dropStatement);
            sqlList.addAll(dropSqls);
        }
        if (!sqlList.isEmpty()) {
            this.execute(sqlTableOperation, container, previewMode);
        }
    }

    protected void createIndex(List<LogicIndex> indexList, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException, SQLMetadataException {
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        ArrayList<String> sqlList = new ArrayList<String>();
        for (LogicIndex logicIndex : indexList) {
            CreateIndexStatement indexStatement = new CreateIndexStatement("", logicIndex.getIndexName());
            indexStatement.setTableName(DefaultTableRefactor.getFullTableName(this.owner, logicIndex.getTableName()));
            indexStatement.setUnique(logicIndex.isUnique());
            indexStatement.setLogicIndex(logicIndex);
            List<String> indexSqlList = sqlInterpretor.createIndex(indexStatement);
            sqlList.addAll(indexSqlList);
        }
        if (!sqlList.isEmpty()) {
            SQLTableOperation sqlTableOperation = new SQLTableOperation("\u521b\u5efa\u7d22\u5f15", sqlList);
            this.execute(sqlTableOperation, container, previewMode);
        }
    }

    protected void dropTable(String tableName, List<ITableOperation> container, boolean previewMode) throws SQLException, SQLInterpretException {
        tableName = DefaultTableRefactor.getFullTableName(this.owner, tableName);
        AlterTableStatement dropStatement = new AlterTableStatement(tableName, AlterType.DROP);
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        List<String> dropSqls = sqlInterpretor.alterTable(dropStatement);
        this.execute(new SQLTableOperation("\u5220\u9664\u8868\u3010" + tableName + "\u3011", dropSqls), container, previewMode);
    }

    protected void renameSrcTable(String srcTableName, String tempTableName, List<ITableOperation> container, boolean previewMode) throws SQLInterpretException, SQLException {
        ISQLInterpretor sqlInterpretor = DefaultTableRefactor.getSqlInterpretor(this.conn);
        srcTableName = DefaultTableRefactor.getFullTableName(this.owner, srcTableName);
        List<String> renameSqlList = sqlInterpretor.renameTable(srcTableName, tempTableName);
        this.execute(new SQLTableOperation("\u6e90\u8868\u3010" + srcTableName + "\u3011\u91cd\u547d\u540d\u4e3a\u3010" + tempTableName + "\u3011", renameSqlList), container, previewMode);
    }

    protected void copyTableData(String srcTableName, String destTableName, List<LoadFieldMap> fieldMapList, List<ITableOperation> container, boolean previewMode) throws SQLException, TableLoaderException {
        srcTableName = DefaultTableRefactor.getFullTableName(this.owner, srcTableName);
        destTableName = DefaultTableRefactor.getFullTableName(this.owner, destTableName);
        if (!previewMode) {
            IDatabase dataBase = DatabaseManager.getInstance().findDatabaseByConnection(this.conn);
            ITableLoader createInsertLoader = dataBase.createInsertLoader(this.conn);
            SimpleTable srcSimpleTable = new SimpleTable(srcTableName);
            SimpleTable destSimpleTable = new SimpleTable(destTableName);
            for (LoadFieldMap loadFieldMap : fieldMapList) {
                ((SQLField)loadFieldMap.getSourceField()).setOwner(srcSimpleTable);
                ((SQLField)loadFieldMap.getDestField()).setOwner(destSimpleTable);
                srcSimpleTable.addField(loadFieldMap.getSourceField());
                destSimpleTable.addField(loadFieldMap.getDestField());
            }
            createInsertLoader.setDestTable(destSimpleTable);
            createInsertLoader.setSourceTable(srcSimpleTable);
            createInsertLoader.getFieldMaps().addAll(fieldMapList);
            createInsertLoader.execute();
        }
        ArrayList<String> operations = new ArrayList<String>();
        operations.add("\u6839\u636e\u5b57\u6bb5\u6620\u5c04\u5173\u7cfb\uff0c\u62f7\u8d1d\u6e90\u8868\u3010" + srcTableName + "\u3011\u6570\u636e\u5230\u76ee\u6807\u8868\u3010" + destTableName + "\u3011");
        container.add(new SQLTableOperation("\u6570\u636e\u62f7\u8d1d", operations));
    }

    private static String generateTempTableName() {
        return "TEMP_" + OrderGenerator.newOrder();
    }

    private static ISQLInterpretor getSqlInterpretor(Connection conn) throws SQLException, SQLInterpretException {
        return DatabaseManager.getInstance().getSQLInterpretor(conn);
    }

    private static ISQLMetadata getSqlMetaData(Connection conn) throws SQLException {
        return DatabaseManager.getInstance().createMetadata(conn);
    }
}

