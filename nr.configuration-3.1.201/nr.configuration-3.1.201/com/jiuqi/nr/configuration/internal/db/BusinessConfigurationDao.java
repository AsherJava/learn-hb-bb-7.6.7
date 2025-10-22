/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.configuration.internal.db;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.configuration.db.IBusinessConfigurationDao;
import com.jiuqi.nr.configuration.facade.BusinessConfigurationDefine;
import com.jiuqi.nr.configuration.internal.db.BusinessConfigurationMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BusinessConfigurationDao
implements IBusinessConfigurationDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static final String TABLENAME = "SYS_BIZ_CONFIGURATION";
    public static final String KEY = "BC_KEY";
    public static final String TASKKEY = "BC_TASK_KEY";
    public static final String FORMSCHEMEKEY = "BC_FORM_SCHEME_KEY";
    public static final String CODE = "BC_CODE";
    public static final String TITLE = "BC_TITLE";
    public static final String DESC = "BC_DESC";
    public static final String CATEGORY = "BC_CATEGORY";
    public static final String CONTENT = "BC_CONTENT";
    public static final String CONTENTTYPE = "BC_CONTENT_TYPE";
    public static final String FILENAME = "BC_FILE_NAME";

    @Override
    public void insertConfiguration(BusinessConfigurationDefine configuration) throws SQLException {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append(TABLENAME);
        insertSql.append(" ").append(String.format("(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)", KEY, TASKKEY, FORMSCHEMEKEY, CODE, TITLE, DESC, CATEGORY, CONTENT, CONTENTTYPE, FILENAME));
        insertSql.append(" VALUES ").append("(?,?,?,?,?,?,?,?,?,?)");
        ArrayList<Object> paramValues = new ArrayList<Object>();
        paramValues.add(configuration.getKey() == null ? UUIDUtils.getKey() : configuration.getKey());
        paramValues.add(configuration.getTaskKey());
        paramValues.add(configuration.getFormSchemeKey());
        paramValues.add(configuration.getCode());
        paramValues.add(configuration.getTitle());
        paramValues.add(configuration.getDescription());
        paramValues.add(configuration.getCategory());
        paramValues.add(configuration.getConfigurationContent());
        paramValues.add(configuration.getContentType().getValue());
        paramValues.add(configuration.getFileName());
        Object[] params = paramValues.toArray(new Object[0]);
        this.jdbcTemplate.update(insertSql.toString(), params);
    }

    @Override
    public void updateConfiguration(BusinessConfigurationDefine configuration) throws SQLException {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TABLENAME, CONTENT, KEY);
        Object[] arg = new Object[]{configuration.getConfigurationContent(), configuration.getKey()};
        this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public void updateConfiguration(String taskKey, String formSchemeKey, String code, String content) throws SQLException {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? AND %s = ?", TABLENAME, CONTENT, CODE, TASKKEY, FORMSCHEMEKEY);
        Object[] arg = new Object[]{content, code, taskKey, formSchemeKey};
        this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public void deleteConfiguration(String configKey) throws SQLException {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", TABLENAME, KEY);
        Object[] arg = new Object[]{configKey};
        this.jdbcTemplate.update(deleteSql, arg);
    }

    @Override
    public void deleteConfigurationByCode(String code, String taskKey) throws SQLException {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", TABLENAME, CODE, TASKKEY);
        Object[] arg = new Object[]{code, taskKey};
        this.jdbcTemplate.update(deleteSql, arg);
    }

    @Override
    public void deleteConfigurationByCode(String code, String taskKey, String formSchemeKey) throws SQLException {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ? AND %s = ?", TABLENAME, CODE, TASKKEY, FORMSCHEMEKEY);
        Object[] arg = new Object[]{code, taskKey, formSchemeKey};
        this.jdbcTemplate.update(deleteSql, arg);
    }

    @Override
    public BusinessConfigurationDefine getConfigurationByCode(String code, String taskKey) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? ", TABLENAME, CODE, TASKKEY);
        Object[] arg = new Object[]{code, taskKey};
        List results = this.jdbcTemplate.query(selectSql, (RowMapper)new BusinessConfigurationMapper(), arg);
        if (results.size() > 0) {
            return (BusinessConfigurationDefine)results.get(0);
        }
        return null;
    }

    @Override
    public BusinessConfigurationDefine getConfigurationByCode(String code, String taskKey, String formSchemeKey) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ? ", TABLENAME, CODE, TASKKEY, FORMSCHEMEKEY);
        Object[] arg = new Object[]{code, taskKey, formSchemeKey};
        List results = this.jdbcTemplate.query(selectSql, (RowMapper)new BusinessConfigurationMapper(), arg);
        if (results.size() > 0) {
            return (BusinessConfigurationDefine)results.get(0);
        }
        return null;
    }

    @Override
    public List<BusinessConfigurationDefine> getConfigurationsByTask(String taskKey, boolean getContent) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE %s = ? ", TABLENAME, TASKKEY);
        Object[] arg = new Object[]{taskKey};
        List results = this.jdbcTemplate.query(selectSql, (RowMapper)new BusinessConfigurationMapper(), arg);
        if (results.size() > 0) {
            if (!getContent) {
                results.forEach(a -> a.setConfigurationContent(null));
            }
            return results;
        }
        return null;
    }

    @Override
    public List<BusinessConfigurationDefine> getConfigurationsByTask(String taskKey, String formSchemeKey, boolean getContent) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", TABLENAME, TASKKEY, FORMSCHEMEKEY);
        Object[] arg = new Object[]{taskKey, formSchemeKey};
        List results = this.jdbcTemplate.query(selectSql, (RowMapper)new BusinessConfigurationMapper(), arg);
        if (results.size() > 0) {
            if (!getContent) {
                results.forEach(a -> a.setConfigurationContent(null));
            }
            return results;
        }
        return null;
    }

    @Override
    public List<BusinessConfigurationDefine> getConfigurationsByCategory(String category, String taskKey, String formSchemeKey, boolean getContent) throws SQLException {
        String selectSql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? AND %s = ?", TABLENAME, TASKKEY, FORMSCHEMEKEY, CATEGORY);
        Object[] arg = new Object[]{taskKey, formSchemeKey, category};
        List results = this.jdbcTemplate.query(selectSql, (RowMapper)new BusinessConfigurationMapper(), arg);
        if (results.size() > 0) {
            if (!getContent) {
                results.forEach(a -> a.setConfigurationContent(null));
            }
            return results;
        }
        return null;
    }
}

