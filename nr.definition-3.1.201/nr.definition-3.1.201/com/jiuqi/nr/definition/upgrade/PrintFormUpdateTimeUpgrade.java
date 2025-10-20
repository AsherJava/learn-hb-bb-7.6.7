/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class PrintFormUpdateTimeUpgrade
implements CustomClassExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintFormUpdateTimeUpgrade.class);
    private static final String SQL = "UPDATE NR_PARAM_PRINTTEMPLATE_DES SET PT_FORM_UPDATETIME = ? WHERE PT_FORM_UPDATETIME IS NULL";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(SQL);){
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                statement.executeUpdate();
                connection.commit();
            }
            catch (Exception e) {
                connection.rollback();
                LOGGER.error("\u6253\u5370\u6a21\u677f\u8868\u6837\u66f4\u65b0\u65f6\u95f4\u5347\u7ea7\u5f02\u5e38", e);
            }
        }
        catch (Exception e) {
            if (null != connection) {
                connection.rollback();
            }
            LOGGER.error("\u6253\u5370\u6a21\u677f\u8868\u6837\u66f4\u65b0\u65f6\u95f4\u5347\u7ea7\u5f02\u5e38", e);
        }
        finally {
            if (null != connection) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }
}

