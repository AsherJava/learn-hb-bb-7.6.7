/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.conversion.batch.audit.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.conversion.batch.audit.dao.ConversionBatchAuditDao;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditFileEntity;
import com.jiuqi.gcreport.conversion.batch.audit.entity.ConversionBatchAuditRunnerEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ConversionBatchAuditDaoImpl
implements ConversionBatchAuditDao {
    private static final Logger logger = LoggerFactory.getLogger(ConversionBatchAuditDaoImpl.class);
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public String addBatchAudit(ConversionBatchAuditRunnerEntity entity) {
        String sql = "INSERT INTO GC_CONVERSION_BATCH_AUDIT (ID,ACCT_YEAR,ACCT_PERIOD,TASK_ID,DATA_TYPE,CREATE_TIME,FILE_NAME,CREATE_USER,EXPORT_TYPE,SCHEME_ID,FILE_DATA) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        String id = UUIDUtils.newUUIDStr();
        int insert = this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, id);
            ps.setString(2, entity.getAcctYear());
            ps.setString(3, entity.getAcctPeriod());
            ps.setString(4, entity.getTaskId());
            ps.setString(5, entity.getDataType());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setString(7, entity.getFileName());
            ps.setString(8, entity.getCreateUser());
            ps.setString(9, entity.getExportType());
            ps.setString(10, entity.getSchemeId());
            ps.setBlob(11, entity.getFileData());
        });
        if (insert != 1) {
            throw new RuntimeException("\u65b0\u589e\u6570\u636e\u5931\u8d25");
        }
        return id;
    }

    @Override
    public void deleteSelectBatchAudit(List<String> idList) {
        String sql = "DELETE FROM GC_CONVERSION_BATCH_AUDIT WHERE " + SqlUtils.getConditionOfIdsUseByPlaceholder(idList, (String)"ID");
        this.jdbcTemplate.update(sql, idList.toArray());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ConversionBatchAuditRunnerEntity> getAllBatchAudit(ConversionBatchAuditRunnerEntity entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,ACCT_YEAR,ACCT_PERIOD,TASK_ID,DATA_TYPE,CREATE_TIME,FILE_NAME,CREATE_USER,EXPORT_TYPE,SCHEME_ID ");
        sql.append("FROM GC_CONVERSION_BATCH_AUDIT WHERE 1 = 1 ");
        Map<String, Object> paramMap = this.getBatchAuditParam(sql, entity);
        sql.append("ORDER BY CREATE_TIME DESC");
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List entityList = jdbc.query(sql.toString(), paramMap, (rs, rowNum) -> this.extractedRsToAuditEntity(rs));
        if (entity.getPageSize() != null && entity.getCurrentPageNum() != null) {
            int startIdx = (entity.getCurrentPageNum() - 1) * entity.getPageSize();
            int endIndex = Math.min(startIdx + entity.getPageSize(), entityList.size());
            Connection conn = null;
            List<ConversionBatchAuditRunnerEntity> entityListPage = new ArrayList<ConversionBatchAuditRunnerEntity>();
            try {
                conn = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
                IDatabase idatabase = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                IPagingSQLBuilder pageBuilder = idatabase.createPagingSQLBuilder();
                pageBuilder.setRawSQL(sql.toString());
                String pageSql = pageBuilder.buildSQL(startIdx, endIndex);
                entityListPage = jdbc.query(pageSql, paramMap, (rs, rowNum) -> this.extractedRsToAuditEntity(rs));
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u83b7\u53d6\u5206\u9875\u8bb0\u5f55\u5f02\u5e38", e);
            }
            finally {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
            return entityListPage;
        }
        return entityList;
    }

    @Override
    public List<ConversionBatchAuditFileEntity> getFileListForId(ConversionBatchAuditFileEntity entity) {
        String sql = "SELECT FILE_DATA,FILE_NAME FROM GC_CONVERSION_BATCH_AUDIT WHERE " + SqlUtils.getConditionOfIdsUseByPlaceholder(entity.getIdList(), (String)"ID");
        List entityList = this.jdbcTemplate.query(sql, entity.getIdList().toArray(), (rs, rowNum) -> this.extractedRsToFileEntity(rs));
        return entityList;
    }

    private ConversionBatchAuditFileEntity extractedRsToFileEntity(ResultSet rs) throws SQLException {
        ConversionBatchAuditFileEntity entity = new ConversionBatchAuditFileEntity();
        entity.setFileData(rs.getBlob("FILE_DATA"));
        entity.setFileName(rs.getString("FILE_NAME"));
        return entity;
    }

    private Map<String, Object> getBatchAuditParam(StringBuilder sql, ConversionBatchAuditRunnerEntity entity) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        if (!StringUtils.isNull((String)entity.getTaskId())) {
            sql.append("AND TASK_ID = :TASK_ID ");
            paramMap.put("TASK_ID", entity.getTaskId());
        }
        if (!StringUtils.isNull((String)entity.getAcctYear())) {
            sql.append("AND ACCT_YEAR = :ACCT_YEAR ");
            paramMap.put("ACCT_YEAR", entity.getAcctYear());
        }
        if (!StringUtils.isNull((String)entity.getAcctPeriod())) {
            sql.append("AND ACCT_PERIOD = :ACCT_PERIOD ");
            paramMap.put("ACCT_PERIOD", entity.getAcctPeriod());
        }
        return paramMap;
    }

    @Override
    public int getAllBatchAuditCount(ConversionBatchAuditRunnerEntity entity) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS BATCH_AUDIT_COUNT FROM GC_CONVERSION_BATCH_AUDIT WHERE 1 = 1 ");
        Map<String, Object> paramMap = this.getBatchAuditParam(sql, entity);
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List countList = jdbc.query(sql.toString(), paramMap, (rs, i) -> rs.getInt("BATCH_AUDIT_COUNT"));
        if (!CollectionUtils.isEmpty((Collection)countList)) {
            return (Integer)countList.get(0);
        }
        return 0;
    }

    private ConversionBatchAuditRunnerEntity extractedRsToAuditEntity(ResultSet rs) throws SQLException {
        ConversionBatchAuditRunnerEntity entity = new ConversionBatchAuditRunnerEntity();
        entity.setId(rs.getString("ID"));
        entity.setAcctYear(rs.getString("ACCT_YEAR"));
        entity.setAcctPeriod(rs.getString("ACCT_PERIOD"));
        entity.setTaskId(rs.getString("TASK_ID"));
        entity.setDataType(rs.getString("DATA_TYPE"));
        entity.setCreateTime(rs.getTimestamp("CREATE_TIME"));
        entity.setFileName(rs.getString("FILE_NAME"));
        entity.setCreateUser(rs.getString("CREATE_USER"));
        entity.setExportType(rs.getString("EXPORT_TYPE"));
        entity.setSchemeId(rs.getString("SCHEME_ID"));
        return entity;
    }
}

