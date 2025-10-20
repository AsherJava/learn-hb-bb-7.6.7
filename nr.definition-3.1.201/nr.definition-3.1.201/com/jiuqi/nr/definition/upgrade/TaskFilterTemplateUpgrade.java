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
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.StringUtils;

public class TaskFilterTemplateUpgrade
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TaskFilterTemplateUpgrade.class);
    private final String TASK_TABLE_NAME = "NR_PARAM_TASK";
    private final String TK_KEY = "TK_KEY";
    private final String TK_FILTER_EXPRESSION = "TK_FILTER_EXPRESSION";
    private final String TK_FILTER_TEMPLATE = "TK_FILTER_TEMPLATE";
    private final String FILTER_TEMPLATE_TABLE = "NR_PARAM_FILTERTEMPLATE";
    private final String FT_KEY = "FT_KEY";
    private final String FT_FILTER_EXPRESSION = "FT_FILTER_EXPRESSION";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Map<String, String> taskAndFilterTeplateMap = this.upgradePreheat(connection);
            if (!taskAndFilterTeplateMap.isEmpty()) {
                Map<String, String> filterTemplateMap = this.buildFilterTeplateMap(connection, taskAndFilterTeplateMap.values());
                this.doUpgrade(connection, taskAndFilterTeplateMap, filterTemplateMap);
            } else {
                this.logger.info("\u5f53\u524d\u7cfb\u7edf\u5185\u672a\u4f7f\u7528\u8fc7\u6ee4\u6a21\u677f\u4f5c\u4e3a\u4efb\u52a1\u4e3b\u7ef4\u5ea6\u7684\u8fc7\u6ee4\u65b9\u5f0f,\u65e0\u9700\u5347\u7ea7");
            }
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6a21\u677f\u4e3a\u8fc7\u6ee4\u516c\u5f0f\u5931\u8d25", e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
    }

    private Map<String, String> upgradePreheat(Connection connection) {
        HashMap<String, String> taskAndFilterTeplateMap = new HashMap<String, String>();
        String QUERY_TASK_WITH_FILTER_TEMPLATE = "SELECT TK_KEY,TK_FILTER_EXPRESSION,TK_FILTER_TEMPLATE FROM NR_PARAM_TASK WHERE TK_FILTER_TEMPLATE IS NOT NULL OR TK_FILTER_TEMPLATE <> '' ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_TASK_WITH_FILTER_TEMPLATE);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                String filterExpression = resultSet.getString("TK_FILTER_EXPRESSION");
                if (StringUtils.hasText(filterExpression)) continue;
                String taskKey = resultSet.getString("TK_KEY");
                String filterTemplate = resultSet.getString("TK_FILTER_TEMPLATE");
                taskAndFilterTeplateMap.put(taskKey, filterTemplate);
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u627e\u4f7f\u7528\u8fc7\u6ee4\u6a21\u677f\u7684\u4efb\u52a1\u5931\u8d25", e);
        }
        return taskAndFilterTeplateMap;
    }

    private Map<String, String> buildFilterTeplateMap(Connection connection, Collection<String> filterTemplateList) {
        HashMap<String, String> filterTeplateMap = new HashMap<String, String>();
        String condition = filterTemplateList.stream().distinct().map(v -> "'" + v + "'").collect(Collectors.joining(","));
        String QUERY_FILTER_TEMPLATE = "SELECT * FROM NR_PARAM_FILTERTEMPLATE WHERE FT_KEY IN ( " + condition + " )";
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FILTER_TEMPLATE);
             ResultSet resultSet = preparedStatement.executeQuery();){
            while (resultSet.next()) {
                filterTeplateMap.put(resultSet.getString("FT_KEY"), resultSet.getString("FT_FILTER_EXPRESSION"));
            }
        }
        catch (Exception e) {
            this.logger.error("\u67e5\u627e\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25", e);
        }
        return filterTeplateMap;
    }

    private void doUpgrade(Connection connection, Map<String, String> taskAndFilterTeplateMap, Map<String, String> filterTeplateMap) {
        if (filterTeplateMap.isEmpty()) {
            return;
        }
        String UPDATE_TASK = "UPDATE NR_PARAM_TASK SET TK_FILTER_EXPRESSION = ? , TK_FILTER_TEMPLATE = '' WHERE TK_KEY = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TASK);){
            for (String task : taskAndFilterTeplateMap.keySet()) {
                String filterTemplate = taskAndFilterTeplateMap.get(task);
                String filterTemplateContent = filterTeplateMap.get(filterTemplate);
                this.logger.info("\u66f4\u65b0\u4efb\u52a1\u4e3a{},\u8fc7\u6ee4\u6a21\u677fID\u4e3a{}", (Object)task, (Object)filterTemplate);
                preparedStatement.setString(1, filterTemplateContent);
                preparedStatement.setString(2, task);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
        catch (Exception e) {
            this.logger.error("\u66f4\u65b0\u4efb\u52a1\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25", e);
        }
    }
}

