/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.nr.datascheme.update;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DataSchemeBizCodeUpgrade
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(DataSchemeBizCodeUpgrade.class);

    public void execute(DataSource dataSource) throws Exception {
        this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e1a\u52a1\u6807\u8bc6\u5347\u7ea7\u5f00\u59cb");
        String querySQL = "SELECT %s, %s FROM %s ";
        String updateSQL = "UPDATE %s SET %s = ? WHERE %s = ? ";
        Connection connectionU = null;
        try (Connection connection = dataSource.getConnection();){
            Throwable throwable;
            ResultSet resultSet;
            connectionU = connection;
            connectionU.setAutoCommit(false);
            HashMap<String, String> map = new HashMap<String, String>();
            try (PreparedStatement statement = connection.prepareStatement(String.format(querySQL, "DS_KEY", "DS_BIZCODE", "NR_DATASCHEME_SCHEME_DES"));){
                resultSet = statement.executeQuery();
                throwable = null;
                try {
                    DataSchemeBizCodeUpgrade.getData(resultSet, map);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (resultSet != null) {
                        if (throwable != null) {
                            try {
                                resultSet.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                        } else {
                            resultSet.close();
                        }
                    }
                }
            }
            statement = connection.prepareStatement(String.format(querySQL, "DS_KEY", "DS_BIZCODE", "NR_DATASCHEME_SCHEME"));
            var9_12 = null;
            try {
                resultSet = statement.executeQuery();
                throwable = null;
                try {
                    DataSchemeBizCodeUpgrade.getData(resultSet, map);
                }
                catch (Throwable throwable4) {
                    throwable = throwable4;
                    throw throwable4;
                }
                finally {
                    if (resultSet != null) {
                        if (throwable != null) {
                            try {
                                resultSet.close();
                            }
                            catch (Throwable throwable5) {
                                throwable.addSuppressed(throwable5);
                            }
                        } else {
                            resultSet.close();
                        }
                    }
                }
            }
            catch (Throwable throwable6) {
                var9_12 = throwable6;
                throw throwable6;
            }
            finally {
                if (statement != null) {
                    if (var9_12 != null) {
                        try {
                            statement.close();
                        }
                        catch (Throwable throwable7) {
                            var9_12.addSuppressed(throwable7);
                        }
                    } else {
                        statement.close();
                    }
                }
            }
            if (!map.isEmpty()) {
                var9_12 = null;
                try (PreparedStatement preparedStatement = connection.prepareStatement(String.format(updateSQL, "NR_DATASCHEME_SCHEME_DES", "DS_BIZCODE", "DS_KEY"));){
                    DataSchemeBizCodeUpgrade.setParams(preparedStatement, map);
                    preparedStatement.executeBatch();
                }
                catch (Throwable throwable8) {
                    var9_12 = throwable8;
                    throw throwable8;
                }
                preparedStatement = connection.prepareStatement(String.format(updateSQL, "NR_DATASCHEME_SCHEME", "DS_BIZCODE", "DS_KEY"));
                var9_12 = null;
                try {
                    DataSchemeBizCodeUpgrade.setParams(preparedStatement, map);
                    preparedStatement.executeBatch();
                }
                catch (Throwable throwable9) {
                    var9_12 = throwable9;
                    throw throwable9;
                }
                finally {
                    if (preparedStatement != null) {
                        if (var9_12 != null) {
                            try {
                                preparedStatement.close();
                            }
                            catch (Throwable throwable10) {
                                var9_12.addSuppressed(throwable10);
                            }
                        } else {
                            preparedStatement.close();
                        }
                    }
                }
            }
            connection.commit();
            this.logger.info("\u6570\u636e\u65b9\u6848\uff1a\u4e1a\u52a1\u6807\u8bc6\u5347\u7ea7\u7ed3\u675f");
        }
        catch (Exception e) {
            if (connectionU != null) {
                connectionU.rollback();
            }
            this.logger.error("\u6570\u636e\u65b9\u6848\uff1a\u4e1a\u52a1\u6807\u8bc6\u5347\u7ea7\u5931\u8d25", e);
        }
    }

    private static void getData(ResultSet resultSet, Map<String, String> map) throws SQLException {
        while (resultSet.next()) {
            String dataSchemeKey = resultSet.getString("DS_KEY");
            String bizCode = resultSet.getString("DS_BIZCODE");
            if (StringUtils.hasLength(bizCode) || map.containsKey(dataSchemeKey)) continue;
            map.put(dataSchemeKey, OrderGenerator.newOrder());
        }
    }

    private static void setParams(PreparedStatement preparedStatement, Map<String, String> map) throws SQLException {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            preparedStatement.setString(1, entry.getValue());
            preparedStatement.setString(2, entry.getKey());
            preparedStatement.addBatch();
        }
    }
}

