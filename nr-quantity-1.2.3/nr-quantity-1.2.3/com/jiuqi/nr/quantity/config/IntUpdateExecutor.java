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
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.quantity.config;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.AlterType;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntUpdateExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(IntUpdateExecutor.class);

    public void execute(DataSource dataSource) throws Exception {
        Connection connection = dataSource.getConnection();
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        ISQLInterpretor sqlInterpreter = database.createSQLInterpretor(connection);
        AlterColumnStatement modifyStatement = new AlterColumnStatement("NR_QUAN_UNIT", AlterType.MODIFY);
        modifyStatement.setColumnName("QU_RATE");
        LogicField newField = new LogicField();
        newField.setFieldName("QU_RATE");
        newField.setDataType(3);
        modifyStatement.setNewColumn(newField);
        List modifySqls = sqlInterpreter.alterColumn(modifyStatement);
        AlterColumnStatement modifyStatement2 = new AlterColumnStatement("NR_QUAN_CATEGORY", AlterType.MODIFY);
        modifyStatement2.setColumnName("QC_RATE");
        LogicField newField2 = new LogicField();
        newField2.setFieldName("QC_RATE");
        newField2.setDataType(3);
        modifyStatement2.setNewColumn(newField2);
        modifySqls.addAll(sqlInterpreter.alterColumn(modifyStatement2));
        for (String sql : modifySqls) {
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
                conn.close();
                throw throwable;
            }
        }
        conn.close();
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

