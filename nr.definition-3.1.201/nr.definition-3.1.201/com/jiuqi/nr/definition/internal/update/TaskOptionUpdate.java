/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.definition.internal.update;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class TaskOptionUpdate
implements CustomClassExecutor {
    private Logger logger = LoggerFactory.getLogger(TaskOptionUpdate.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) {
        String selectSql = "SELECT SO_KEY,SO_TASK_KEY,SO_VALUE FROM NR_TASK_DEFINE_OPTION WHERE SO_KEY = 'DATAENTRY_STATUS'";
        String updateSql = "UPDATE NR_TASK_DEFINE_OPTION SET SO_VALUE = ? WHERE SO_KEY = ? AND SO_TASK_KEY = ?";
        Connection connection2 = null;
        try (Connection connection = DataSourceUtils.getConnection((DataSource)dataSource);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
             ResultSet resultSet = preparedStatement.executeQuery();
             PreparedStatement statement = connection.prepareStatement(updateSql);){
            connection2 = connection;
            connection.setAutoCommit(false);
            ObjectMapper mapper = new ObjectMapper();
            while (resultSet.next()) {
                String value = resultSet.getString("SO_VALUE");
                if (value == null || value.isEmpty()) continue;
                String taskKey = resultSet.getString("SO_TASK_KEY");
                ArrayList<String> newValues = new ArrayList<String>();
                try {
                    ArrayList values = (ArrayList)mapper.readValue(value, (TypeReference)new TypeReference<ArrayList<Integer>>(){});
                    for (Integer v : values) {
                        if (v == null) continue;
                        newValues.add(v.toString());
                    }
                }
                catch (Exception e) {
                    this.logger.error("\u5347\u7ea7\u4efb\u52a1\u9009\u9879\uff1a\u5f55\u5165\u72b6\u6001\u663e\u793a \u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u5230\u4efb\u52a1\u8bbe\u7f6e\u754c\u9762\u624b\u52a8\u8bbe\u7f6e,\u4efb\u52a1 key = " + taskKey);
                }
                statement.setString(1, mapper.writeValueAsString(newValues));
                statement.setString(2, resultSet.getString("SO_KEY"));
                statement.setString(3, taskKey);
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        }
        catch (Exception e) {
            this.logger.error("\u5347\u7ea7\u4efb\u52a1\u9009\u9879\uff1a\u5f55\u5165\u72b6\u6001\u663e\u793a \u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u5230\u4efb\u52a1\u8bbe\u7f6e\u754c\u9762\u624b\u52a8\u8bbe\u7f6e", e);
        }
        finally {
            try {
                connection2.setAutoCommit(true);
            }
            catch (Exception ignored) {
                this.logger.error(ignored.getMessage(), ignored);
            }
        }
    }
}

