/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.configuration.internal.db;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.nr.configuration.common.SystemOptionType;
import com.jiuqi.nr.configuration.db.ISystemOptionDao;
import com.jiuqi.nr.configuration.facade.SystemOptionDefine;
import com.jiuqi.nr.configuration.internal.impl.SystemOptionDefineImpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class SystemOptionDao
implements ISystemOptionDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String TABLENAME = "SYS_SYSTEM_OPTION";
    private static final String KEY = "SO_KEY";
    private static final String TASKKEY = "SO_TASK_KEY";
    private static final String FORMSCHEMEKEY = "SO_FORM_SCHEME_KEY";
    private static final String VALUE = "SO_VALUE";
    private static final String PARENT = "SO_PARENT_KEY";
    private static final String DEFAULTTASK = "00000000-0000-0000-0000-000000000000";
    private static final String DEFAULTFORMSCHEME = "00000000-0000-0000-0000-000000000000";

    @Override
    public SystemOptionDefine getOptionByCode(String key) throws SQLException {
        return this.getOptionByCode(key, null, null);
    }

    @Override
    public SystemOptionDefine getOptionByCode(String key, String taskKey) throws SQLException {
        return this.getOptionByCode(key, taskKey, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public SystemOptionDefine getOptionByCode(String key, String taskKey, String formSchemeKey) throws SQLException {
        if (StringUtils.isEmpty((String)key)) {
            return null;
        }
        Connection connection = null;
        try {
            SystemOptionDefine optionDefine;
            connection = this.getConnection();
            SystemOptionDefine systemOptionDefine = optionDefine = this.getExistOption(connection, key, taskKey, formSchemeKey);
            return systemOptionDefine;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @Override
    public void setSystemOption(String key, Object value) throws SQLException {
        this.setSystemOption(key, value, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000");
    }

    @Override
    public void setSystemOption(String key, Object value, String taskKey) throws SQLException {
        this.setSystemOption(key, value, taskKey, "00000000-0000-0000-0000-000000000000");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setSystemOption(String key, Object value, String taskKey, String formSchemeKey) throws SQLException {
        Connection connection = null;
        try {
            connection = this.getConnection();
            SystemOptionDefine optionDefine = this.getExistOption(connection, key, taskKey, formSchemeKey);
            if (optionDefine == null) {
                this.insertSystemOption(connection, key, value, taskKey, formSchemeKey);
            } else {
                if (optionDefine.getValue() == value) {
                    return;
                }
                this.updateSystemOption(connection, key, value, taskKey, formSchemeKey);
            }
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @Override
    public void batchSetOptions(HashMap<String, Object> optionValues, boolean onlyInsert) throws SQLException {
        this.batchSetOptions(optionValues, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000", onlyInsert);
    }

    @Override
    public void batchSetOptions(HashMap<String, Object> optionValues, String taskKey, boolean onlyInsert) throws SQLException {
        this.batchSetOptions(optionValues, taskKey, "00000000-0000-0000-0000-000000000000", onlyInsert);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchSetOptions(HashMap<String, Object> optionValues, String taskKey, String formSchemeKey, boolean onlyInsert) throws SQLException {
        Connection connection = null;
        try {
            connection = this.getConnection();
            List<SystemOptionDefine> optionDefines = this.getExistOptions(connection, optionValues.keySet(), taskKey.toString(), formSchemeKey.toString());
            ArrayList<String> existKeys = new ArrayList<String>();
            List<Object[]> batchValues = this.getBatchUpdateValues(optionDefines, optionValues, existKeys, taskKey.toString(), formSchemeKey.toString());
            if (batchValues.size() > 0 && !onlyInsert) {
                this.batchUpdateOptions(connection, batchValues);
            }
            ArrayList<String> optionKeys = new ArrayList<String>(optionValues.keySet());
            optionKeys.removeAll(existKeys);
            batchValues = this.getBatchInsertValues(optionKeys, optionValues, taskKey.toString(), formSchemeKey.toString());
            if (batchValues.size() > 0) {
                this.batchInsertOptions(connection, batchValues);
            }
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @Override
    public List<SystemOptionDefine> getOptions() throws SQLException {
        List<SystemOptionDefine> optionDefines = this.getOptions("00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000", null);
        return optionDefines;
    }

    @Override
    public List<SystemOptionDefine> getOptionsByTask(String taskKey) throws SQLException {
        List<SystemOptionDefine> optionDefines = this.getOptions(taskKey.toString(), null, null);
        return optionDefines;
    }

    @Override
    public List<SystemOptionDefine> getOptionsByFormScheme(String taskKey, String formSchemeKey) throws SQLException {
        List<SystemOptionDefine> optionDefines = this.getOptions(taskKey.toString(), formSchemeKey.toString(), null);
        return optionDefines;
    }

    @Override
    public List<SystemOptionDefine> getAllOptions() throws SQLException {
        List<SystemOptionDefine> optionDefines = this.getOptions(null, null, null);
        return optionDefines;
    }

    @Override
    public void deleteSystemOption(String key) throws SQLException {
        this.deleteSystemOption(key, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000");
    }

    @Override
    public void deleteSystemOption(String key, String taskKey) throws SQLException {
        this.deleteSystemOption(key, taskKey, "00000000-0000-0000-0000-000000000000");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteSystemOption(String key, String taskKey, String formSchemeKey) throws SQLException {
        Connection connection = null;
        try {
            connection = this.getConnection();
            StringBuilder deleteSql = new StringBuilder();
            deleteSql.append("DELETE FROM ").append(TABLENAME);
            deleteSql.append(" WHERE ").append(KEY).append("=").append("?");
            deleteSql.append(" AND ").append(TASKKEY).append("=").append("?");
            deleteSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
            Object[] paramValues = new Object[]{key, taskKey == null ? "00000000-0000-0000-0000-000000000000" : taskKey.toString(), formSchemeKey == null ? "00000000-0000-0000-0000-000000000000" : formSchemeKey.toString()};
            DataEngineUtil.executeUpdate((Connection)connection, (String)deleteSql.toString(), (Object[])paramValues);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @Override
    public void batchDeleteOptions(List<String> keys) throws SQLException {
        this.batchDeleteOptions(keys, "00000000-0000-0000-0000-000000000000", "00000000-0000-0000-0000-000000000000");
    }

    @Override
    public void batchDeleteOptions(List<String> keys, String taskKey) throws SQLException {
        this.batchDeleteOptions(keys, taskKey, "00000000-0000-0000-0000-000000000000");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void batchDeleteOptions(List<String> keys, String taskKey, String formSchemeKey) throws SQLException {
        Connection connection = null;
        try {
            connection = this.getConnection();
            StringBuilder deleteSql = new StringBuilder();
            deleteSql.append("DELETE FROM ").append(TABLENAME);
            deleteSql.append(" WHERE ").append(KEY).append(" IN (");
            boolean addDot = false;
            for (String key : keys) {
                if (addDot) {
                    deleteSql.append(",");
                }
                addDot = true;
                deleteSql.append("'").append(key).append("'");
            }
            deleteSql.append(")");
            deleteSql.append(" AND ").append(TASKKEY).append("=").append("?");
            deleteSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
            Object[] paramValues = new Object[]{taskKey == null ? "00000000-0000-0000-0000-000000000000" : taskKey.toString(), formSchemeKey == null ? "00000000-0000-0000-0000-000000000000" : formSchemeKey.toString()};
            DataEngineUtil.executeUpdate((Connection)connection, (String)deleteSql.toString(), (Object[])paramValues);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    @Override
    public List<SystemOptionDefine> getOptionsByByGroup(String groupKey) throws SQLException {
        return this.getOptions(null, null, groupKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SystemOptionDefine getExistOption(Connection connection, String key, String taskKey, String formSchemeKey) throws SQLException {
        try (ResultSet resultSet = null;){
            StringBuilder selectSql = new StringBuilder();
            ArrayList<String> paramValues = new ArrayList<String>();
            selectSql.append("SELECT * FROM ").append(TABLENAME).append(" WHERE ");
            selectSql.append(KEY).append("=").append("?");
            paramValues.add(key);
            if (taskKey != null) {
                selectSql.append(" AND ").append(TASKKEY).append("=").append("?");
                paramValues.add(taskKey.toString());
            } else {
                selectSql.append(" AND ").append(TASKKEY).append("=").append("?");
                paramValues.add("00000000-0000-0000-0000-000000000000");
            }
            if (formSchemeKey != null) {
                selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                paramValues.add(formSchemeKey.toString());
            } else {
                selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                paramValues.add("00000000-0000-0000-0000-000000000000");
            }
            resultSet = DataEngineUtil.executeQuery((Connection)connection, (String)selectSql.toString(), (Object[])paramValues.toArray());
            if (resultSet.next()) {
                SystemOptionDefineImpl optionDefine;
                SystemOptionDefineImpl systemOptionDefineImpl = optionDefine = this.getSystemOption(resultSet);
                return systemOptionDefineImpl;
            }
        }
        return null;
    }

    private List<Object[]> getBatchInsertValues(List<String> optionKeys, HashMap<String, Object> optionValues, String taskKey, String formSchemeKey) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (String optionKey : optionKeys) {
            if (!optionValues.containsKey(optionKey)) continue;
            Object[] paramValues = new Object[]{optionKey, optionValues.get(optionKey), taskKey, formSchemeKey};
            batchValues.add(paramValues);
        }
        return batchValues;
    }

    private List<Object[]> getBatchUpdateValues(List<SystemOptionDefine> optionDefines, HashMap<String, Object> optionValues, List<String> existKeys, String taskKey, String formSchemeKey) {
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (SystemOptionDefine optionDefine : optionDefines) {
            if (!optionValues.containsKey(optionDefine.getKey())) continue;
            Object currentValue = optionValues.get(optionDefine.getKey());
            if (currentValue == optionDefine.getValue()) {
                existKeys.add(optionDefine.getKey());
                continue;
            }
            Object[] paramValues = new Object[]{currentValue, optionDefine.getKey(), taskKey, formSchemeKey};
            batchValues.add(paramValues);
            existKeys.add(optionDefine.getKey());
        }
        return batchValues;
    }

    private void batchInsertOptions(Connection connection, List<Object[]> batchValues) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append(TABLENAME);
        insertSql.append(" ").append(String.format("(%s,%s,%s,%s)", KEY, VALUE, TASKKEY, FORMSCHEMEKEY));
        insertSql.append(" VALUES ").append("(?,?,?,?)");
        DataEngineUtil.batchUpdate((Connection)connection, (String)insertSql.toString(), batchValues);
    }

    private void batchUpdateOptions(Connection connection, List<Object[]> batchValues) throws SQLException {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(TABLENAME);
        updateSql.append(" SET ").append(VALUE).append("=").append("?");
        updateSql.append(" WHERE ").append(KEY).append("=").append("?");
        updateSql.append(" AND ").append(TASKKEY).append("=").append("?");
        updateSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
        DataEngineUtil.batchUpdate((Connection)connection, (String)updateSql.toString(), batchValues);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SystemOptionDefine> getOptions(String taskKey, String formSchemeKey, String parent) throws SQLException {
        Connection connection = null;
        try {
            List<SystemOptionDefine> optionDefines;
            connection = this.getConnection();
            StringBuilder selectSql = new StringBuilder();
            ArrayList<Object> paramValues = new ArrayList<Object>();
            selectSql.append("SELECT * FROM ").append(TABLENAME);
            boolean where = false;
            if (!StringUtils.isEmpty((String)taskKey)) {
                selectSql.append(" WHERE ").append(TASKKEY).append("=").append("?");
                paramValues.add(taskKey);
                where = true;
                if (!StringUtils.isEmpty((String)formSchemeKey)) {
                    selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                    paramValues.add(formSchemeKey);
                }
            }
            if (!StringUtils.isEmpty((String)parent)) {
                if (where) {
                    selectSql.append(" AND ").append(PARENT).append("=").append("?");
                } else {
                    selectSql.append(" WHERE ").append(PARENT).append("=").append("?");
                }
                paramValues.add(parent);
            }
            List<SystemOptionDefine> list = optionDefines = this.getOptionsBySql(connection, selectSql, paramValues);
            return list;
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<SystemOptionDefine> getOptionsBySql(Connection connection, StringBuilder selectSql, List<Object> paramValues) throws SQLException {
        try (ResultSet resultSet = null;){
            ArrayList<SystemOptionDefine> optionDefines = new ArrayList<SystemOptionDefine>();
            resultSet = DataEngineUtil.executeQuery((Connection)connection, (String)selectSql.toString(), (Object[])paramValues.toArray());
            while (resultSet.next()) {
                SystemOptionDefineImpl optionDefine = this.getSystemOption(resultSet);
                optionDefines.add(optionDefine);
            }
            ArrayList<SystemOptionDefine> arrayList = optionDefines;
            return arrayList;
        }
    }

    private SystemOptionDefineImpl getSystemOption(ResultSet resultSet) throws SQLException {
        SystemOptionDefineImpl optionDefine = new SystemOptionDefineImpl();
        optionDefine.setKey(resultSet.getString(KEY));
        optionDefine.setValue(resultSet.getObject(VALUE));
        String taskInfo = resultSet.getString(TASKKEY);
        optionDefine.setTaskKey(StringUtils.isEmpty((String)taskInfo) || taskInfo.equals("00000000-0000-0000-0000-000000000000") ? null : taskInfo);
        String formSchemeInfo = resultSet.getString(FORMSCHEMEKEY);
        optionDefine.setFormSchemeKey(StringUtils.isEmpty((String)formSchemeInfo) || formSchemeInfo.equals("00000000-0000-0000-0000-000000000000") ? null : formSchemeInfo);
        SystemOptionType optionType = this.getOptionType(optionDefine);
        optionDefine.setOptionType(optionType);
        String parent = resultSet.getString(PARENT);
        optionDefine.setGroup(parent);
        return optionDefine;
    }

    private SystemOptionType getOptionType(SystemOptionDefineImpl optionDefine) {
        if (optionDefine == null) {
            return SystemOptionType.SYSTEM_OPTION;
        }
        if (optionDefine.getTaskKey() != null && optionDefine.getFormSchemeKey() != null) {
            return SystemOptionType.FORMSCHEME_OPTION;
        }
        if (optionDefine.getTaskKey() != null && optionDefine.getFormSchemeKey() == null) {
            return SystemOptionType.TASK_OPTION;
        }
        return SystemOptionType.SYSTEM_OPTION;
    }

    private void updateSystemOption(Connection connection, String key, Object value, String taskKey, String formSchemeKey) throws SQLException {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ").append(TABLENAME);
        updateSql.append(" SET ").append(VALUE).append("=").append("?");
        updateSql.append(" WHERE ").append(KEY).append("=").append("?");
        updateSql.append(" AND ").append(TASKKEY).append("=").append("?");
        updateSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
        Object[] paramValues = new Object[]{value, key, taskKey == null ? "00000000-0000-0000-0000-000000000000" : taskKey.toString(), formSchemeKey == null ? "00000000-0000-0000-0000-000000000000" : formSchemeKey.toString()};
        DataEngineUtil.executeUpdate((Connection)connection, (String)updateSql.toString(), (Object[])paramValues);
    }

    private void insertSystemOption(Connection connection, String key, Object value, String taskKey, String formSchemeKey) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append(TABLENAME);
        insertSql.append(" ").append(String.format("(%s,%s,%s,%s)", KEY, VALUE, TASKKEY, FORMSCHEMEKEY));
        insertSql.append(" VALUES ").append("(?,?,?,?)");
        Object[] paramValues = new Object[]{key, value, taskKey == null ? "00000000-0000-0000-0000-000000000000" : taskKey.toString(), formSchemeKey == null ? "00000000-0000-0000-0000-000000000000" : formSchemeKey.toString()};
        DataEngineUtil.executeUpdate((Connection)connection, (String)insertSql.toString(), (Object[])paramValues);
    }

    private List<SystemOptionDefine> getExistOptions(Connection connection, Set<String> optionKeys, String taskKey, String formSchemeKey) throws SQLException {
        List<SystemOptionDefine> optionDefines = new ArrayList<SystemOptionDefine>();
        if (optionKeys.size() <= 0) {
            return optionDefines;
        }
        ArrayList<Object> paramValues = new ArrayList<Object>();
        ArrayList<String> keyList = new ArrayList<String>(optionKeys);
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("SELECT * FROM ").append(TABLENAME);
        selectSql.append(" WHERE ").append(KEY).append(" IN (");
        boolean addDot = false;
        for (String key : keyList) {
            if (addDot) {
                selectSql.append(",");
            }
            addDot = true;
            selectSql.append("'").append(key).append("'");
        }
        selectSql.append(")");
        if (!StringUtils.isEmpty((String)taskKey)) {
            selectSql.append(" AND ").append(TASKKEY).append("=").append("?");
            paramValues.add(taskKey);
            if (!StringUtils.isEmpty((String)formSchemeKey)) {
                selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                paramValues.add(formSchemeKey);
            }
        }
        optionDefines = this.getOptionsBySql(connection, selectSql, paramValues);
        return optionDefines;
    }

    private Connection getConnection() throws SQLException {
        return DataSourceUtils.getConnection((DataSource)this.jdbcTemplate.getDataSource());
    }

    @Override
    public HashMap<String, Object> batchGetOptions(List<String> keys) throws SQLException {
        return this.batchGetOptions(keys, null, null);
    }

    @Override
    public HashMap<String, Object> batchGetOptions(List<String> keys, String taskKey) throws SQLException {
        return this.batchGetOptions(keys, taskKey, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public HashMap<String, Object> batchGetOptions(List<String> keys, String taskKey, String formSchemeKey) throws SQLException {
        Connection connection = this.getConnection();
        ResultSet resultSet = null;
        HashMap<String, Object> optionValues = new HashMap<String, Object>();
        try {
            StringBuilder selectSql = new StringBuilder();
            ArrayList<String> paramValues = new ArrayList<String>();
            selectSql.append("SELECT * FROM ").append(TABLENAME).append(" WHERE ");
            selectSql.append(KEY).append(" IN (");
            boolean addDot = false;
            for (String key : keys) {
                if (addDot) {
                    selectSql.append(",");
                }
                addDot = true;
                selectSql.append("'").append(key).append("'");
            }
            selectSql.append(")");
            if (taskKey != null) {
                selectSql.append(" AND ").append(TASKKEY).append("=").append("?");
                paramValues.add(taskKey.toString());
            } else {
                selectSql.append(" AND ").append(TASKKEY).append("=").append("?");
                paramValues.add("00000000-0000-0000-0000-000000000000");
            }
            if (formSchemeKey != null) {
                selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                paramValues.add(formSchemeKey.toString());
            } else {
                selectSql.append(" AND ").append(FORMSCHEMEKEY).append("=").append("?");
                paramValues.add("00000000-0000-0000-0000-000000000000");
            }
            resultSet = DataEngineUtil.executeQuery((Connection)connection, (String)selectSql.toString(), (Object[])paramValues.toArray());
            while (resultSet.next()) {
                String key = resultSet.getString(KEY);
                Object value = resultSet.getObject(VALUE);
                optionValues.put(key, value);
            }
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
        return optionValues;
    }
}

