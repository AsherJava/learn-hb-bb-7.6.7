/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.asynctask.ext;

import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class AsyncTaskTaskIDAddPrefixUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(AsyncTaskTaskIDAddPrefixUpdate.class);
    private static final String NP_ASYNCTASK_TABLE_NAME = "NP_ASYNCTASK";
    private static final String NP_ASYNCTASK_HISTORY_TABLE_NAME = "NP_ASYNCTASK_HISTORY";
    private static final String NP_ASYNCTASK_SIMPLE_QUEUE_TABLE_NAME = "NP_ASYNCTASK_SIMPLE_QUEUE";
    private static final String NP_ASYNCTASK_SPLIT_QUEUE_TABLE_NAME = "NP_ASYNCTASK_SPLIT_QUEUE";

    public void execute(DataSource dataSource) throws Exception {
        this.addTaskIDPrefix(dataSource, NP_ASYNCTASK_TABLE_NAME);
        this.addTaskIDPrefix(dataSource, NP_ASYNCTASK_HISTORY_TABLE_NAME);
        this.addTaskIDPrefix(dataSource, NP_ASYNCTASK_SIMPLE_QUEUE_TABLE_NAME);
        this.addTaskIDPrefix(dataSource, NP_ASYNCTASK_SPLIT_QUEUE_TABLE_NAME);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void addTaskIDPrefix(DataSource dataSource, String tableName) {
        if (tableName == null) {
            return;
        }
        this.logger.info("\u5f02\u6b65\u4efb\u52a1" + tableName + "\u8868\u5386\u53f2\u6570\u636eTASK_ID\u589e\u52a0\u201cnr-\u201d\u524d\u7f00\uff0c\u5347\u7ea7\u5f00\u59cb\uff01");
        Connection connection = null;
        Statement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            preparedStatement = connection.prepareStatement("SELECT TASK_ID FROM " + tableName);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> taskIdList = new ArrayList<String>();
            while (resultSet.next()) {
                taskIdList.add(resultSet.getString("TASK_ID"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("UPDATE " + tableName + " SET TASK_ID = ? WHERE TASK_ID = ?");
            for (String taskId : taskIdList) {
                if (taskId == null || taskId.isEmpty()) continue;
                preparedStatement.setString(1, "nr-" + taskId);
                preparedStatement.setString(2, taskId);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            preparedStatement.close();
            connection.commit();
            this.logger.info("\u5f02\u6b65\u4efb\u52a1" + tableName + "\u8868\u5386\u53f2\u6570\u636eTASK_ID\u589e\u52a0\u201cnr-\u201d\u524d\u7f00\uff0c\u5347\u7ea7\u5b8c\u6210\uff01");
        }
        catch (Exception e) {
            this.logger.error("\u5f02\u6b65\u4efb\u52a1" + tableName + "\u8868\u5386\u53f2\u6570\u636eTASK_ID\u589e\u52a0\u201cnr-\u201d\u524d\u7f00\uff0c\u5347\u7ea7\u5931\u8d25\uff01", e);
        }
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                }
                catch (Exception e) {
                    this.logger.error(e.getMessage(), e);
                }
            }
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
        }
    }
}

