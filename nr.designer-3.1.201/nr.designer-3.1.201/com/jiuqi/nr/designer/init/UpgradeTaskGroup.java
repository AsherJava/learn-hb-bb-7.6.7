/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.designer.init;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpgradeTaskGroup
implements CustomClassExecutor {
    private final Logger logger = LoggerFactory.getLogger(UpgradeTaskGroup.class);
    private static final String QUERY_SQL = "SELECT FL_KEY,FL_TITLE, FL_ORDER FROM NR_PARAM_TASKGROUP_DES";
    private static final String UPDATE_SQL = "UPDATE NR_PARAM_TASKGROUP_DES SET FL_ORDER  = ? where FL_KEY = ?";
    Connection connection = null;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(DataSource dataSource) throws Exception {
        this.connection = dataSource.getConnection();
        try {
            List<GroupDefine> groupDefines = this.queryGroups();
            List<GroupDefine> sortGroups = this.sortByChina(groupDefines);
            this.executeUpdate(sortGroups);
        }
        catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            this.connection.rollback();
        }
        finally {
            this.closeConnection(this.connection);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeUpdate(List<GroupDefine> sortGroups) {
        try (PreparedStatement pstmt = this.connection.prepareStatement(UPDATE_SQL);){
            this.connection.setAutoCommit(false);
            for (GroupDefine sortGroup : sortGroups) {
                int i = 1;
                pstmt.setString(i++, OrderGenerator.newOrder());
                pstmt.setString(i++, sortGroup.getKey());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            this.connection.commit();
        }
        catch (SQLException throwables) {
            this.logger.error(throwables.getMessage(), throwables);
        }
        finally {
            try {
                this.connection.setAutoCommit(false);
            }
            catch (SQLException throwables) {
                this.logger.error(throwables.getMessage(), throwables);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private List<GroupDefine> queryGroups() throws SQLException {
        ArrayList<GroupDefine> groups;
        block24: {
            ResultSet resultSet = null;
            groups = new ArrayList<GroupDefine>();
            try (PreparedStatement pstmt = this.connection.prepareStatement(QUERY_SQL);){
                resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    String key = resultSet.getString("FL_KEY");
                    String title = resultSet.getString("FL_TITLE");
                    String order = resultSet.getString("FL_ORDER");
                    GroupDefine groupDefine = new GroupDefine(key, title, order);
                    groups.add(groupDefine);
                }
            }
            try {
                this.closeResultSet(resultSet);
            }
            catch (SQLException e) {
                this.logger.error(e.getMessage(), e);
            }
            break block24;
            catch (Exception e) {
                try {
                    this.logger.error(e.getMessage(), e);
                }
                catch (Throwable throwable) {
                    try {
                        this.closeResultSet(resultSet);
                    }
                    catch (SQLException e2) {
                        this.logger.error(e2.getMessage(), e2);
                    }
                    throw throwable;
                }
                try {
                    this.closeResultSet(resultSet);
                }
                catch (SQLException e3) {
                    this.logger.error(e3.getMessage(), e3);
                }
            }
        }
        return groups;
    }

    private List<GroupDefine> sortByChina(List<GroupDefine> list) {
        return list.stream().sorted((o1, o2) -> {
            Collator comparator = Collator.getInstance(Locale.CHINA);
            return comparator.compare(o1.getTitle(), o2.getTitle());
        }).collect(Collectors.toList());
    }

    private void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    private void closeStatement(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    private void closeResultSet(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    class GroupDefine {
        private String key;
        private String title;
        private String order;

        public GroupDefine() {
        }

        public GroupDefine(String key, String title, String order) {
            this.key = key;
            this.title = title;
            this.order = order;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrder() {
            return this.order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }
}

