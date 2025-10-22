/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.sensitive.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.sensitive.bean.daoObject.SensitiveWordDaoObject;
import com.jiuqi.nr.sensitive.dao.SensitiveWordDao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SensitiveWordDaoImpl
implements SensitiveWordDao {
    private static final Logger logger = LogFactory.getLogger(SensitiveWordDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertSensitiveWord(SensitiveWordDaoObject sensitiveWordObject) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(this.jdbcTemplate);
        MapSqlParameterSource parameterSource = this.sqlParameterSource(sensitiveWordObject);
        insertActor.withTableName("NR_SENSITIVE_WORD");
        return insertActor.execute((SqlParameterSource)parameterSource);
    }

    @Override
    public void batchInsertSensitiveWord(List<SensitiveWordDaoObject> sensitiveWordObjectList) {
        try {
            String insertSql = String.format("INSERT INTO  %s (SENSITIVE_WORD_KEY ,SENSITIVE_CODE , SENSITIVE_TYPE , SENSITIVE_WORD_TYPE , SENSITIVE_WORD_INFO , SENSITIVE_DESCRIPTION , IS_EFFECTIVE , MODIFY_USER ,MODIFY_TIME )VALUES (?,?,?,?,?,?,?,?,?) ", "NR_SENSITIVE_WORD");
            ArrayList<Object[]> args = new ArrayList<Object[]>(sensitiveWordObjectList.size());
            for (SensitiveWordDaoObject sensitiveWordDaoObject : sensitiveWordObjectList) {
                Object[] arg = new Object[]{sensitiveWordDaoObject.getSensitiveWordKey(), sensitiveWordDaoObject.getSensitiveCode(), sensitiveWordDaoObject.getSensitiveType(), sensitiveWordDaoObject.getSensitiveWordType(), sensitiveWordDaoObject.getSensitiveInfo(), sensitiveWordDaoObject.getSensitiveDescription(), sensitiveWordDaoObject.getIsEffective(), sensitiveWordDaoObject.getModifyUser(), Timestamp.valueOf(sensitiveWordDaoObject.getModifyTime())};
                args.add(arg);
            }
            this.jdbcTemplate.batchUpdate(insertSql, args);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public int updateSensitiveWord(SensitiveWordDaoObject sensitiveWordObject) {
        String updateSql = String.format(" UPDATE %s SET  SENSITIVE_CODE = :%s, SENSITIVE_TYPE = :%s, SENSITIVE_WORD_INFO = :%s, SENSITIVE_DESCRIPTION = :%s, IS_EFFECTIVE = :%s, MODIFY_USER = :%s  WHERE SENSITIVE_WORD_KEY = :%s ", "NR_SENSITIVE_WORD", "SENSITIVE_CODE", "SENSITIVE_TYPE", "SENSITIVE_WORD_INFO", "SENSITIVE_DESCRIPTION", "IS_EFFECTIVE", "MODIFY_USER", "SENSITIVE_WORD_KEY");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(updateSql, (SqlParameterSource)this.sqlParameterSource(sensitiveWordObject));
    }

    @Override
    public List<SensitiveWordDaoObject> getSensitiveWordWithType(String sensitiveWordInfo, Integer sensitiveWordType, Integer pageNum, Integer pageRow) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List sensitiveWordDaoObjectList = new ArrayList();
        String querySql = "SELECT * FROM NR_SENSITIVE_WORD WHERE SENSITIVE_WORD_TYPE = :sensitiveWordType AND UPPER(SENSITIVE_WORD_INFO) LIKE :sensitiveWordInfo ORDER BY MODIFY_TIME DESC";
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("sensitiveWordType", (Object)sensitiveWordType);
        parameterSource.addValue("sensitiveWordInfo", (Object)("%" + sensitiveWordInfo + "%"));
        if (pageNum > 0 && pageRow > 0) {
            try (Connection connection = this.jdbcTemplate.getDataSource().getConnection();){
                IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                IPagingSQLBuilder pagingSQLBuilder = iDatabase.createPagingSQLBuilder();
                pagingSQLBuilder.setRawSQL(querySql);
                String pageingSql = pagingSQLBuilder.buildSQL((pageNum - 1) * pageRow, pageRow * pageNum);
                sensitiveWordDaoObjectList = template.query(pageingSql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        } else {
            sensitiveWordDaoObjectList = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        }
        return sensitiveWordDaoObjectList;
    }

    @Override
    public List<SensitiveWordDaoObject> queryAllSensitiveWordWithType(Integer pageNum, Integer pageRow, Integer sensitiveWordType) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List sensitiveWordDaoObjectList = new ArrayList();
        String querySql = String.format("SELECT * FROM %s WHERE SENSITIVE_WORD_TYPE =  " + sensitiveWordType + " ORDER BY MODIFY_TIME DESC", "NR_SENSITIVE_WORD", "SENSITIVE_TYPE");
        if (pageNum > 0 && pageRow > 0) {
            try (Connection connection = this.jdbcTemplate.getDataSource().getConnection();){
                IDatabase iDatabase = DatabaseManager.getInstance().findDatabaseByConnection(connection);
                IPagingSQLBuilder pagingSQLBuilder = iDatabase.createPagingSQLBuilder();
                pagingSQLBuilder.setRawSQL(querySql);
                String pageingSql = pagingSQLBuilder.buildSQL((pageNum - 1) * pageRow, pageRow * pageNum);
                sensitiveWordDaoObjectList = this.jdbcTemplate.query(pageingSql, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        } else {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            sensitiveWordDaoObjectList = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        }
        return sensitiveWordDaoObjectList;
    }

    @Override
    public List<SensitiveWordDaoObject> queryAllSensitiveWordByWordType(Integer sensitiveWordType) {
        String querySql = String.format("SELECT * FROM %s WHERE SENSITIVE_WORD_TYPE = :%s  ORDER BY MODIFY_TIME DESC", "NR_SENSITIVE_WORD", "SENSITIVE_WORD_TYPE");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("SENSITIVE_WORD_TYPE", (Object)sensitiveWordType);
        List records = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        return records;
    }

    @Override
    public List<SensitiveWordDaoObject> queryAllSensitiveWordByType(Integer sensitiveType) {
        String querySql = String.format("SELECT * FROM %s WHERE SENSITIVE_TYPE = :%s  ORDER BY MODIFY_TIME DESC", "NR_SENSITIVE_WORD", "SENSITIVE_TYPE");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("SENSITIVE_TYPE", (Object)sensitiveType);
        List records = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        return records;
    }

    @Override
    public SensitiveWordDaoObject getSensitiveWordByInfoAndWordType(String sensitiveWordInfo, Integer sensitiveWordType) {
        String querySql = String.format("SELECT * FROM %s WHERE SENSITIVE_WORD_TYPE = :%s  AND UPPER(SENSITIVE_WORD_INFO) = :%s  ORDER BY MODIFY_TIME DESC", "NR_SENSITIVE_WORD", "SENSITIVE_WORD_TYPE", "SENSITIVE_WORD_INFO");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("SENSITIVE_WORD_TYPE", (Object)sensitiveWordType);
        parameterSource.addValue("SENSITIVE_WORD_INFO", (Object)sensitiveWordInfo);
        List records = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        return records.size() > 0 ? (SensitiveWordDaoObject)records.get(0) : null;
    }

    @Override
    public SensitiveWordDaoObject getSensitiveWordBySensitiveInfo(String SensitiveWordInfo, Integer sensitiveType) {
        String querySql = String.format("SELECT * FROM %s WHERE SENSITIVE_WORD_INFO = :%s  AND  SENSITIVE_TYPE = :%s  ORDER BY MODIFY_TIME DESC", "NR_SENSITIVE_WORD", "SENSITIVE_WORD_INFO", "SENSITIVE_TYPE", "IS_EFFECTIVE", "SENSITIVE_DESCRIPTION");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("SENSITIVE_WORD_INFO", (Object)SensitiveWordInfo);
        parameterSource.addValue("SENSITIVE_TYPE", (Object)sensitiveType);
        List records = template.query(querySql, (SqlParameterSource)parameterSource, (rs, rowNum) -> this.buildCountRecord(rs, rowNum));
        return records.size() > 0 ? (SensitiveWordDaoObject)records.get(0) : null;
    }

    @Override
    public boolean deleteSensitiveWord(String sensitiveWordKey) {
        String querySql = String.format("DELETE FROM %s WHERE SENSITIVE_WORD_KEY = :%s ", "NR_SENSITIVE_WORD", "SENSITIVE_WORD_KEY");
        int update = this.jdbcTemplate.update(querySql, new Object[]{sensitiveWordKey});
        return update > 0;
    }

    @Override
    public boolean deleteSensitiveWordByType(Integer sensitiveWordType) {
        String querySql = String.format("DELETE FROM %s WHERE SENSITIVE_WORD_TYPE = :%s ", "NR_SENSITIVE_WORD", "SENSITIVE_WORD_TYPE");
        int update = this.jdbcTemplate.update(querySql, new Object[]{sensitiveWordType});
        return update > 0;
    }

    private MapSqlParameterSource sqlParameterSource(SensitiveWordDaoObject sensitiveWordObject) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("SENSITIVE_WORD_KEY", (Object)sensitiveWordObject.getSensitiveWordKey());
        source.addValue("SENSITIVE_CODE", (Object)sensitiveWordObject.getSensitiveCode());
        source.addValue("SENSITIVE_TYPE", (Object)sensitiveWordObject.getSensitiveType());
        source.addValue("SENSITIVE_WORD_TYPE", (Object)sensitiveWordObject.getSensitiveWordType());
        source.addValue("SENSITIVE_WORD_INFO", (Object)sensitiveWordObject.getSensitiveInfo());
        source.addValue("SENSITIVE_DESCRIPTION", (Object)sensitiveWordObject.getSensitiveDescription());
        source.addValue("IS_EFFECTIVE", (Object)sensitiveWordObject.getIsEffective());
        if (sensitiveWordObject.getModifyTime() != null) {
            source.addValue("MODIFY_TIME", (Object)Timestamp.valueOf(sensitiveWordObject.getModifyTime()));
        }
        source.addValue("MODIFY_USER", (Object)sensitiveWordObject.getModifyUser());
        return source;
    }

    private SensitiveWordDaoObject buildCountRecord(ResultSet rs, int rowNum) throws SQLException {
        SensitiveWordDaoObject sensitiveWordDaoObject = new SensitiveWordDaoObject();
        sensitiveWordDaoObject.setSensitiveWordKey(rs.getString("SENSITIVE_WORD_KEY"));
        sensitiveWordDaoObject.setIsEffective(rs.getInt("IS_EFFECTIVE"));
        sensitiveWordDaoObject.setModifyTime(rs.getTimestamp("MODIFY_TIME").toString());
        sensitiveWordDaoObject.setModifyUser(rs.getString("MODIFY_USER"));
        sensitiveWordDaoObject.setSensitiveInfo(rs.getString("SENSITIVE_WORD_INFO"));
        sensitiveWordDaoObject.setSensitiveDescription(rs.getString("SENSITIVE_DESCRIPTION"));
        sensitiveWordDaoObject.setSensitiveType(rs.getInt("SENSITIVE_TYPE"));
        sensitiveWordDaoObject.setSensitiveWordType(rs.getInt("SENSITIVE_WORD_TYPE"));
        sensitiveWordDaoObject.setSensitiveCode(rs.getString("SENSITIVE_CODE"));
        return sensitiveWordDaoObject;
    }
}

