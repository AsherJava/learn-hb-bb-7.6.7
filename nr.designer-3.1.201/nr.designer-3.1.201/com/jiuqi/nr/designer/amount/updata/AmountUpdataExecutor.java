/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.AlterType
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.designer.amount.updata;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.designer.amount.updata.RatioData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class AmountUpdataExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(AmountUpdataExecutor.class);

    public void execute(DataSource dataSource) throws Exception {
        this.logger.info("\u91cf\u7eb2\u7c7b\u578b\u8f6c\u6362\u5f00\u59cb");
        Connection connection = dataSource.getConnection();
        Object preparedStatement = null;
        try {
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ISQLMetadata metadata = database.createMetadata(connection);
            ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
            this.dropColumn(connection, sqlInterpreter);
            this.addColumn(connection, sqlInterpreter);
            this.updateData(connection, sqlInterpreter);
            this.addData(connection, sqlInterpreter);
            this.logger.info("\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u5931\u8d25", e);
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }

    private void addData(Connection connection, ISQLInterpretor sqlInterpreter) throws Exception {
        String insertSql = "INSERT INTO NR_MEASURE_UNIT (MN_ID,MN_CODE,MN_TITLE,MN_PARENT,MN_RATIO,MN_BASEUNIT,MN_ORDERL) VALUES('5a7fc581-94fa-4dda-8a48-2beb25dc50e8','WANYI','\u4e07\u4ebf','RENMINBI','1000000000000',0,'K4T71T7W')";
        this.executeSql(connection, insertSql);
    }

    private void updateData(Connection connection, ISQLInterpretor sqlInterpreter) throws Exception {
        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append("UPDATE ").append("NR_MEASURE_UNIT").append(" SET ").append("MN_RATIO").append("= ? WHERE ").append("MN_CODE").append("= ?");
        ArrayList<RatioData> data = new ArrayList<RatioData>();
        data.add(new RatioData("YUAN", "1"));
        data.add(new RatioData("BAIYUAN", "100"));
        data.add(new RatioData("QIANYUAN", "1000"));
        data.add(new RatioData("WANYUAN", "10000"));
        data.add(new RatioData("BAIWAN", "1000000"));
        data.add(new RatioData("QIANWAN", "10000000"));
        data.add(new RatioData("YIYUAN", "100000000"));
        PreparedStatement preparedStatement = connection.prepareStatement(updateSQL.toString());
        this.setParams(preparedStatement, data);
        preparedStatement.executeBatch();
        preparedStatement.close();
    }

    private void setParams(PreparedStatement preparedStatement, List<RatioData> data) throws SQLException {
        for (RatioData columnData : data) {
            preparedStatement.setString(1, columnData.getRatio());
            preparedStatement.setString(2, columnData.getTitle());
            preparedStatement.addBatch();
        }
    }

    private void addColumn(Connection connection, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement addStatement = new AlterColumnStatement("NR_MEASURE_UNIT", AlterType.ADD);
        addStatement.setColumnName("MN_RATIO");
        LogicField newField = new LogicField();
        newField.setFieldName("MN_RATIO");
        newField.setDataType(6);
        newField.setNullable(true);
        newField.setSize(28);
        addStatement.setNewColumn(newField);
        List addSql = sqlInterpreter.alterColumn(addStatement);
        for (String sql : addSql) {
            this.executeSql(connection, sql);
        }
    }

    private void dropColumn(Connection connection, ISQLInterpretor sqlInterpreter) throws Exception {
        AlterColumnStatement delStatement = new AlterColumnStatement("NR_MEASURE_UNIT", AlterType.DROP);
        delStatement.setColumnName("MN_RATIO");
        List delSql = sqlInterpreter.alterColumn(delStatement);
        for (String sql : delSql) {
            this.executeSql(connection, sql);
        }
    }

    private void executeSql(Connection conn, String sql) throws SQLException {
        PreparedStatement prep = null;
        try {
            this.logger.debug(sql);
            prep = conn.prepareStatement(sql);
            prep.execute();
            this.close(prep);
        }
        catch (Exception e) {
            try {
                this.logger.error(e.getMessage(), e);
                throw e;
            }
            catch (Throwable throwable) {
                this.close(prep);
                throw throwable;
            }
        }
    }

    private void close(Statement prep) {
        try {
            if (prep != null) {
                prep.close();
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }
}

