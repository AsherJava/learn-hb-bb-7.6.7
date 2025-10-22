/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.parser.SQLParser
 *  com.jiuqi.bi.database.statement.SqlStatement
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  com.jiuqi.nvwa.dataengine.util.DataValueUtils
 */
package com.jiuqi.nr.io.tz;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.parser.SQLParser;
import com.jiuqi.bi.database.statement.SqlStatement;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.nvwa.dataengine.util.DataEngineUtil;
import com.jiuqi.nvwa.dataengine.util.DataValueUtils;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempTable {
    public static final String COLUMN_NAME = "ID";
    private final List<?> filterValues;
    private final int dataType;
    private final String tableName;

    public TempTable(List<?> filterValues, int dataType) {
        this.filterValues = filterValues;
        this.dataType = dataType;
        this.tableName = this.getTempTable();
    }

    public String getTempTable() {
        SecureRandom rand = new SecureRandom();
        int tableIndex = rand.nextInt(10000);
        String tableName = OrderGenerator.newOrder();
        return String.format("TMP_%s_%s", tableName, tableIndex);
    }

    public void dropTempTable(Connection connection) throws SQLException {
        DataEngineUtil.execute((Connection)connection, (String)("DROP TABLE " + this.tableName), null);
    }

    public void createTempTable(Connection connection) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE ").append(this.tableName).append(" (");
        sqlBuilder.append(COLUMN_NAME).append(" ");
        switch (this.dataType) {
            case 33: {
                sqlBuilder.append("RAW(16)");
                break;
            }
            case 3: 
            case 10: {
                sqlBuilder.append("NUMBER(28,10)");
                break;
            }
            case 5: {
                sqlBuilder.append("NUMBER(10)");
                break;
            }
            case 1: {
                sqlBuilder.append("NUMBER(1)");
                break;
            }
            default: {
                sqlBuilder.append("VARCHAR(100)");
            }
        }
        sqlBuilder.append(" NOT NULL,");
        sqlBuilder.append("CONSTRAINT PK_").append(this.tableName).append(" PRIMARY KEY (ID)");
        sqlBuilder.append(");");
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        SQLParser sqlParser = new SQLParser();
        List statements = sqlParser.parse(sqlBuilder.toString());
        for (SqlStatement statement : statements) {
            List sqls = statement.interpret(database, connection);
            for (String sql : sqls) {
                DataEngineUtil.execute((Connection)connection, (String)sql, null);
            }
        }
    }

    public void insertIntoTempTable(Connection connection) throws SQLException {
        String insertSql = " Insert Into " + this.tableName + "(" + COLUMN_NAME + ") values (?)";
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (Object filterValue : this.filterValues) {
            Object resultValue = DataValueUtils.formatData((int)this.dataType, filterValue);
            Object[] batchArray = new Object[]{resultValue};
            batchValues.add(batchArray);
        }
        DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql, batchValues);
    }

    public String getSelectSql() {
        return "Select ID From " + this.tableName;
    }

    public String getExistsSelectSql(String relationCol) {
        return "(Select 1 From " + this.tableName + " where " + COLUMN_NAME + "=" + relationCol + ")";
    }

    public String getTableName() {
        return this.tableName;
    }
}

