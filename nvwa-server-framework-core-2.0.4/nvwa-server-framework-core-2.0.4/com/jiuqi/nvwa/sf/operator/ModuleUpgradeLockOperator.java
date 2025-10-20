/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nvwa.sf.operator;

import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.util.StringUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleUpgradeLockOperator {
    private static final String SQL_DO_LOG = String.format("INSERT INTO %s ( %s, %s, %s, %s) VALUES (?, ?, ?, ?)", "SF_UPGRADE_LOG", "LOG_SQL", "LOG_MACHINE_NAME", "LOG_TYPE", "LOG_TIMESTAMP");
    private static final String SQL_QUERY_LOG = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s > ? ORDER BY %s ASC", "LOG_SQL", "LOG_MACHINE_NAME", "LOG_TYPE", "LOG_TIMESTAMP", "SF_UPGRADE_LOG", "LOG_TIMESTAMP", "LOG_TIMESTAMP");
    private static final String SQL_LOCK = String.format("SELECT %s,%s FROM %s WHERE %s=? FOR UPDATE", "SL_MODULE_ID", "SL_MACHINE_NAME", "SF_LOCK", "SL_ID");

    private ModuleUpgradeLockOperator() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LockInfo acquireLock(String moduleId) {
        LockInfo lockInfo = new LockInfo();
        try (Connection connection = GlobalConnectionProviderManager.getConnection();){
            connection.setAutoCommit(false);
            ModuleUpgradeLockOperator.getLockInfo(lockInfo, connection);
            if (StringUtils.isNotEmpty((String)lockInfo.getModuleId())) {
                lockInfo.setLock(false);
                LockInfo lockInfo2 = lockInfo;
                return lockInfo2;
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE %s SET %s = ?,%s = ?,%s = ? WHERE %s = ?", "SF_LOCK", "SL_MODULE_ID", "SL_MACHINE_NAME", "SL_TIMESTAMP", "SL_ID"));){
                preparedStatement.setString(1, moduleId);
                preparedStatement.setString(2, DistributionManager.getInstance().self().getMachineName());
                preparedStatement.setLong(3, System.currentTimeMillis());
                preparedStatement.setString(4, "GLOBAL");
                preparedStatement.execute();
            }
            preparedStatement = connection.prepareStatement(String.format("DELETE FROM %s", "SF_UPGRADE_LOG"));
            var5_10 = null;
            try {
                preparedStatement.execute();
            }
            catch (Throwable throwable) {
                var5_10 = throwable;
                throw throwable;
            }
            finally {
                if (preparedStatement != null) {
                    if (var5_10 != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable) {
                            var5_10.addSuppressed(throwable);
                        }
                    } else {
                        preparedStatement.close();
                    }
                }
            }
            lockInfo.setLock(true);
            connection.commit();
            return lockInfo;
        }
        catch (SQLException e) {
            e.printStackTrace();
            lockInfo.setLock(false);
        }
        return lockInfo;
    }

    public static void releaseLock() {
        LockInfo lockInfo = new LockInfo();
        try (Connection connection = GlobalConnectionProviderManager.getConnection();){
            connection.setAutoCommit(false);
            ModuleUpgradeLockOperator.getLockInfo(lockInfo, connection);
            if (StringUtils.isNotEmpty((String)lockInfo.getModuleId())) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(String.format("UPDATE %s SET %s = ?,%s = ?,%s = ?  WHERE %s = ?", "SF_LOCK", "SL_MODULE_ID", "SL_MACHINE_NAME", "SL_TIMESTAMP", "SL_ID"));){
                    preparedStatement.setString(1, null);
                    preparedStatement.setString(2, null);
                    preparedStatement.setLong(3, System.currentTimeMillis());
                    preparedStatement.setString(4, "GLOBAL");
                    preparedStatement.executeUpdate();
                }
            }
            connection.commit();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void doLog(Connection connection, int logType, String sql) {
        List<String> strings = ModuleUpgradeLockOperator.stringSplit(sql, 500);
        for (String string : strings) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_DO_LOG);
                Throwable throwable = null;
                try {
                    preparedStatement.setString(1, string);
                    preparedStatement.setString(2, DistributionManager.getInstance().self().getMachineName());
                    preparedStatement.setInt(3, logType);
                    preparedStatement.setLong(4, System.currentTimeMillis());
                    preparedStatement.executeUpdate();
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (preparedStatement == null) continue;
                    if (throwable != null) {
                        try {
                            preparedStatement.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    preparedStatement.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<UpgradeLogInfo> queryLog(Connection connection, long lastTimestamp) throws SQLException {
        ArrayList<UpgradeLogInfo> result = new ArrayList<UpgradeLogInfo>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_LOG);){
            preparedStatement.setLong(1, lastTimestamp);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    UpgradeLogInfo lockLogInfo = new UpgradeLogInfo();
                    lockLogInfo.setMachineName(resultSet.getString("LOG_MACHINE_NAME"));
                    lockLogInfo.setSql(resultSet.getString("LOG_SQL"));
                    lockLogInfo.setTimestamp(resultSet.getLong("LOG_TIMESTAMP"));
                    lockLogInfo.setLogType(resultSet.getInt("LOG_TYPE"));
                    result.add(lockLogInfo);
                }
            }
        }
        return result;
    }

    private static void getLockInfo(LockInfo lockInfo, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_LOCK);){
            preparedStatement.setString(1, "GLOBAL");
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    lockInfo.setMachineName(resultSet.getString("SL_MACHINE_NAME"));
                    lockInfo.setModuleId(resultSet.getString("SL_MODULE_ID"));
                }
            }
        }
    }

    private static List<String> stringSplit(String src, int length) {
        if (src == null) {
            return Collections.emptyList();
        }
        ArrayList<String> splitList = new ArrayList<String>();
        int startIndex = 0;
        int endIndex = Math.min(length, src.length());
        while (startIndex < src.length()) {
            String subString = src.substring(startIndex, endIndex);
            while (subString.length() > length) {
                subString = src.substring(startIndex, --endIndex);
            }
            splitList.add(src.substring(startIndex, endIndex));
            startIndex = endIndex;
            endIndex = Math.min(startIndex + length, src.length());
        }
        return splitList;
    }

    public static class UpgradeLogInfo
    implements Serializable {
        public static final int TYPE_FILE = 0;
        public static final int TYPE_SQL = 1;
        public static final int TYPE_ERROR = 2;
        public static final int TYPE_INTERRUPT = 3;
        public static final int TYPE_END = 4;
        private String machineName;
        private String sql;
        private int logType;
        private long timestamp;

        public String getMachineName() {
            return this.machineName;
        }

        public void setMachineName(String machineName) {
            this.machineName = machineName;
        }

        public String getSql() {
            return this.sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public int getLogType() {
            return this.logType;
        }

        public void setLogType(int logType) {
            this.logType = logType;
        }

        public long getTimestamp() {
            return this.timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }

    public static class LockInfo {
        private boolean lock;
        private String moduleId;
        private String machineName;

        public String getModuleId() {
            return this.moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }

        public String getMachineName() {
            return this.machineName;
        }

        public void setMachineName(String machineName) {
            this.machineName = machineName;
        }

        public boolean isLock() {
            return this.lock;
        }

        public void setLock(boolean lock) {
            this.lock = lock;
        }
    }
}

