/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.util.OrderGenerator
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.upgrade;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TaskOrgUpdate
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TaskOrgUpdate.class);
    private static final String TASK_TABLE = "NR_PARAM_TASK";
    private static final String TASK_KEY = "TK_KEY";
    private static final String TASK_DW = "TK_DW";
    private static final String TASK_QUERY_SQL = "SELECT TK_KEY,TK_DW FROM NR_PARAM_TASK";
    private static final String TASK_ORG_LINK_TABLE = "NR_PARAM_TASK_ORG_LINK";
    private static final String TASK_ORG_LINK_TASK = "TO_TASK";
    private static final String TASK_ORG_LINK_ENTITY = "TO_ENTITY";
    private static final String TASK_ORG_LINK_QUERY_SQL = "SELECT TO_TASK,TO_ENTITY FROM NR_PARAM_TASK_ORG_LINK";
    private static final String TASK_ORG_LINK_INSERT_SQL = "INSERT INTO NR_PARAM_TASK_ORG_LINK values (?,?,?,?,?)";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        this.logger.info("\u5f00\u59cb\u5347\u7ea7\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u81f3\u4efb\u52a1-\u5b9e\u4f53\u5173\u8054\u5b50\u8868");
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Map<String, String> taskAndDwMap = this.queryTaskInfo(connection);
            this.filterTaskInfo(connection, taskAndDwMap);
            if (!taskAndDwMap.isEmpty()) {
                this.doInsert(connection, taskAndDwMap);
            }
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u81f3\u4efb\u52a1-\u5b9e\u4f53\u5173\u8054\u5b50\u8868\u5931\u8d25", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
        this.logger.info("\u5347\u7ea7\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u81f3\u4efb\u52a1-\u5b9e\u4f53\u5173\u8054\u5b50\u8868\u5b8c\u6210");
    }

    private Map<String, String> queryTaskInfo(Connection connection) {
        HashMap<String, String> result = new HashMap<String, String>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(TASK_QUERY_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                result.put(resultSet.getString(TASK_KEY), resultSet.getString(TASK_DW));
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u4efb\u52a1\u4e3b\u952e\u53ca\u4e3b\u7ef4\u5ea6\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5\u53c2\u6570", e);
        }
        return result;
    }

    private void filterTaskInfo(Connection connection, Map<String, String> taskInfoMap) {
        HashMap<String, HashSet<String>> taskOrgLinkMap = new HashMap<String, HashSet<String>>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(TASK_ORG_LINK_QUERY_SQL);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                HashSet<String> entitys = (HashSet<String>)taskOrgLinkMap.get(resultSet.getString(TASK_ORG_LINK_TASK));
                if (entitys == null) {
                    entitys = new HashSet<String>();
                    entitys.add(resultSet.getString(TASK_ORG_LINK_ENTITY));
                    taskOrgLinkMap.put(resultSet.getString(TASK_ORG_LINK_TASK), entitys);
                    continue;
                }
                entitys.add(resultSet.getString(TASK_ORG_LINK_ENTITY));
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u8be2\u4efb\u52a1-\u5b9e\u4f53\u94fe\u63a5\u5173\u8054\u8868\u5931\u8d25");
        }
        if (taskOrgLinkMap.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<String, String>> iterator = taskInfoMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String taskKey = entry.getKey();
            if (!taskOrgLinkMap.containsKey(taskKey) || !((Set)taskOrgLinkMap.get(taskKey)).contains(entry.getValue())) continue;
            iterator.remove();
        }
    }

    private void doInsert(Connection connection, Map<String, String> taskInfoMap) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(TASK_ORG_LINK_INSERT_SQL);){
            for (Map.Entry<String, String> taskInfo : taskInfoMap.entrySet()) {
                this.logger.info("\u5347\u7ea7\u4efb\u52a1[" + taskInfo.getKey() + "]");
                this.logger.info("\u4e3b\u7ef4\u5ea6\u4e3a[" + taskInfo.getValue() + "]");
                preparedStatement.setString(1, UUIDUtils.getKey());
                preparedStatement.setString(2, taskInfo.getKey());
                preparedStatement.setString(3, taskInfo.getValue());
                preparedStatement.setString(4, null);
                preparedStatement.setString(5, OrderGenerator.newOrder());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        catch (Exception e) {
            this.logger.error("\u65b0\u589e\u4efb\u52a1-\u5b9e\u4f53\u8bb0\u5f55\u5931\u8d25", e);
            this.logger.info("\u9700\u8981\u65b0\u589e\u7684\u8bb0\u5f55\u6709:" + taskInfoMap.values().toString());
        }
    }
}

