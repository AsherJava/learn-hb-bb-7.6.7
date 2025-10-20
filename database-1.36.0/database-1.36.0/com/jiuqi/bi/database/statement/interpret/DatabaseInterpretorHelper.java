/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.statement.interpret;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.metadata.SQLMetadataException;
import com.jiuqi.bi.database.sql.model.ISQLModelPrinter;
import com.jiuqi.bi.database.sql.model.InsertSQLModel;
import com.jiuqi.bi.database.sql.model.SQLModelException;
import com.jiuqi.bi.database.sql.model.tables.SimpleTable;
import com.jiuqi.bi.database.sql.parser.SQLInterpretException;
import com.jiuqi.bi.database.statement.AlterTableStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.CreateIndexStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInterpretorHelper {
    public static final List<String> createBakTableByCopy(Connection conn, String owner, String tableName, String newTableName) throws SQLException, SQLMetadataException, SQLInterpretException {
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        ISQLMetadata metadata = db.createMetadata(conn);
        ISQLInterpretor sqlInterpretor = db.createSQLInterpretor(conn);
        LogicTable table = metadata.getTableByName(tableName, owner);
        if (table == null) {
            throw new SQLInterpretException("\u6570\u636e\u5e93\u4e2d\u4e0d\u5b58\u5728\u8868\uff1a" + tableName);
        }
        ArrayList<String> sqls = new ArrayList<String>();
        List<LogicField> fields = metadata.getFieldsByTableName(tableName, owner);
        CreateTableStatement createStatement = new CreateTableStatement(null, newTableName);
        createStatement.getColumns().addAll(fields);
        LogicPrimaryKey pk = metadata.getPrimaryKeyByTableName(tableName, owner);
        if (pk != null) {
            createStatement.setPkName(pk.getPkName() + "_N");
            createStatement.getPrimaryKeys().addAll(pk.getFieldNames());
        }
        List<String> createTableSqls = sqlInterpretor.createTable(createStatement);
        sqls.addAll(createTableSqls);
        List<LogicIndex> idxes = metadata.getIndexesByTableName(tableName, owner);
        for (LogicIndex idx : idxes) {
            idx.setIndexName(idx.getIndexName() + "_N");
            CreateIndexStatement statement = new CreateIndexStatement(null, idx.getIndexName() + "_N");
            statement.setLogicIndex(idx);
            List<String> createIdxSqls = sqlInterpretor.createIndex(statement);
            sqls.addAll(createIdxSqls);
        }
        SimpleTable srcTable = new SimpleTable(tableName);
        SimpleTable destTable = new SimpleTable(newTableName);
        InsertSQLModel model = new InsertSQLModel(srcTable, destTable);
        try {
            StringBuilder buffer = new StringBuilder();
            ISQLModelPrinter printer = db.createModelPrinter(model);
            if (printer != null) {
                printer.printSQL(buffer, db, model, 0);
            } else {
                buffer.append("insert into ").append(newTableName).append(" select * from ").append(tableName);
            }
            sqls.add(buffer.toString());
        }
        catch (SQLModelException e) {
            throw new SQLInterpretException(e.getMessage(), e);
        }
        return sqls;
    }

    public static final List<String> renameTableByReCreate(Connection conn, String owner, String tableName, String newTableName) throws SQLException, SQLMetadataException, SQLInterpretException {
        ArrayList<String> sqls = new ArrayList<String>();
        List<String> tmp = DatabaseInterpretorHelper.createBakTableByCopy(conn, owner, tableName, newTableName);
        sqls.addAll(tmp);
        AlterTableStatement dropStatement = new AlterTableStatement(tableName, AlterType.DROP);
        IDatabase db = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        ISQLInterpretor sqlInterpretor = db.createSQLInterpretor(conn);
        List<String> dropSqls = sqlInterpretor.alterTable(dropStatement);
        sqls.addAll(dropSqls);
        return sqls;
    }
}

