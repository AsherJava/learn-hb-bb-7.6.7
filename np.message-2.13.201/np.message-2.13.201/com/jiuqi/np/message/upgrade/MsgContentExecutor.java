/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.np.message.upgrade;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.message.upgrade.TodoParamExecutor;
import com.jiuqi.np.sql.CustomClassExecutor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class MsgContentExecutor
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(TodoParamExecutor.class);
    private static final String QUERY_PARAMCLOB_SQL = "SELECT msgid,content FROM MSG_MAIN";
    private static final String UPDATE_PARACLOB_SQL = "update MSG_MAIN set content_clob=? where msgid=?";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        block17: {
            Connection connection = null;
            try {
                connection = DataSourceUtils.getConnection((DataSource)dataSource);
                Map<String, String> queryAllActionParam = this.queryAllActionParam(dataSource);
                if (queryAllActionParam.size() <= 0) break block17;
                for (Map.Entry<String, String> actionParam : queryAllActionParam.entrySet()) {
                    if (actionParam == null || !StringUtils.isNotEmpty((String)actionParam.getKey()) || !StringUtils.isNotEmpty((String)actionParam.getValue())) continue;
                    PreparedStatement ps3 = connection.prepareStatement(UPDATE_PARACLOB_SQL);
                    Throwable throwable = null;
                    try {
                        ps3.setString(1, actionParam.getValue());
                        ps3.setString(2, actionParam.getKey());
                        ps3.executeUpdate();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (ps3 == null) continue;
                        if (throwable != null) {
                            try {
                                ps3.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        ps3.close();
                    }
                }
            }
            catch (Exception e) {
                this.logger.info("\u811a\u672c\u6267\u884c\u51fa\u9519: " + e.getMessage());
                this.logger.error(e.getMessage(), e);
            }
            finally {
                if (connection != null) {
                    DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map<String, String> queryAllActionParam(DataSource dataSource) throws SQLException {
        HashMap<String, String> actionParamMap = new HashMap<String, String>();
        Statement ps = null;
        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection((DataSource)dataSource);
            ps = connection.prepareStatement(QUERY_PARAMCLOB_SQL);
            try (ResultSet rs = ps.executeQuery();){
                while (rs.next()) {
                    String msgId = rs.getString("msgid");
                    String content = rs.getString("content");
                    actionParamMap.put(msgId, content);
                }
            }
        }
        catch (Exception e) {
            this.logger.info("\u811a\u672c\u6267\u884c\u51fa\u9519: " + e.getMessage());
            this.logger.error(e.getMessage(), e);
        }
        finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)dataSource);
            }
        }
        return actionParamMap;
    }
}

