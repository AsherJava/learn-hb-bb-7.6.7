/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;

public class B2CUp {
    private static final Logger logger = LoggerFactory.getLogger(B2CUp.class);
    private final String tableName;
    private final String fieldName;
    private final String[] pkFiledNames;
    private final DataSource dataSource;

    public B2CUp(String tableName, String fieldName, String[] pkFiledNames, DataSource dataSource) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.pkFiledNames = pkFiledNames;
        this.dataSource = dataSource;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void up() throws Exception {
        if (!StringUtils.hasText(this.fieldName) || this.pkFiledNames == null || this.pkFiledNames.length == 0) {
            return;
        }
        logger.info("{}BLOB\u5b57\u6bb5\u8f6cCLOB\u5347\u7ea7\u5f00\u59cb", (Object)this.tableName);
        Connection connection = this.dataSource.getConnection();
        try {
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            DataEngineUtil.execute((Connection)connection, (String)"TRUNCATE TABLE NR_PARAM_REVIEW_INFO", null);
            ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
            this.addColumn(connection, sqlInterpreter);
            this.dropColumn(connection, sqlInterpreter);
            this.renameColumn(connection, sqlInterpreter);
            logger.info("{}BLOB\u5b57\u6bb5\u8f6cCLOB\u5347\u7ea7\u6210\u529f", (Object)this.tableName);
        }
        catch (Exception e) {
            logger.error(this.tableName + "BLOB\u5b57\u6bb5\u8f6cCLOB\u5347\u7ea7\u5f02\u5e38:" + e.getMessage(), e);
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.dataSource);
        }
    }

    private void addColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement addStatement = new AlterColumnStatement(this.tableName, AlterType.ADD);
        addStatement.setColumnName(this.fieldName);
        LogicField newField = new LogicField();
        newField.setFieldName(this.getTmpFieldName());
        newField.setDataType(12);
        newField.setNullable(true);
        addStatement.setNewColumn(newField);
        List addSql = sqlInterpreter.alterColumn(addStatement);
        for (String sql : addSql) {
            this.executeSql(conn, sql);
        }
    }

    private void dropColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement(this.tableName, AlterType.DROP);
        delStatement.setColumnName(this.fieldName);
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(conn, sql);
        }
    }

    private void renameColumn(Connection conn, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement renameStatement = new AlterColumnStatement(this.tableName, AlterType.RENAME);
        LogicField newField = new LogicField();
        newField.setFieldName(this.fieldName);
        newField.setDataType(12);
        newField.setNullable(true);
        renameStatement.setColumnName(this.getTmpFieldName());
        renameStatement.setReColumnName(this.fieldName);
        renameStatement.setNewColumn(newField);
        List renameSql = sqlInterpreter.alterColumn(renameStatement);
        for (String sql : renameSql) {
            this.executeSql(conn, sql);
        }
    }

    private void executeSql(Connection conn, String sql) throws SQLException {
        logger.debug(sql);
        try (PreparedStatement prep = conn.prepareStatement(sql);){
            prep.execute();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private String getTmpFieldName() {
        return this.fieldName + "_";
    }
}

