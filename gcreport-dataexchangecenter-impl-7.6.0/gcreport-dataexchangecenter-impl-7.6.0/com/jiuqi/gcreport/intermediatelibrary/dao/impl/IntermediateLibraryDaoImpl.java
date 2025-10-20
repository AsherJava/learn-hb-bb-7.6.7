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
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition
 *  com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.va.query.common.DataSourcePoolProperties
 *  com.jiuqi.va.query.datasource.dao.DataSourceInfoDao
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.datasource.vo.DataSourceTempInfo
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.jiuqi.va.query.util.DCQueryDES
 *  com.jiuqi.va.query.util.DCQueryStringHandle
 *  com.zaxxer.hikari.HikariDataSource
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.gcreport.intermediatelibrary.dao.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILClearCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILFieldCondition;
import com.jiuqi.gcreport.intermediatelibrary.dao.IntermediateLibraryDao;
import com.jiuqi.gcreport.intermediatelibrary.entity.FieldDataRegionEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILOrgEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSetupCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILSyncCondition;
import com.jiuqi.gcreport.intermediatelibrary.entity.MdZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.MetaDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.ZbDataEntity;
import com.jiuqi.gcreport.intermediatelibrary.vo.ILFieldVO;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.va.query.common.DataSourcePoolProperties;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.vo.DataSourceTempInfo;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.util.DCQueryDES;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

@Repository
public class IntermediateLibraryDaoImpl
implements IntermediateLibraryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;
    @Autowired
    private JdbcTemplate currentJdbcTemplate;
    @Autowired
    private DataSourcePoolProperties poolConfig;
    private final Map<String, DataSourceTempInfo> dataSourceMap = new ConcurrentHashMap<String, DataSourceTempInfo>();
    private static final String fieldSaveTable = "ZBDATA";
    private static final String floatFieldTable = "MDZBDATA";
    private static final String fileTable = "ATTACHMENT";
    private static final String causeTable = "METADATA";
    private static final Logger logger = LoggerFactory.getLogger(IntermediateLibraryDaoImpl.class);

    @Override
    public List<ILEntity> getAllProgramme() {
        String sql = "SELECT ID,PROGRAMMENAME,LIBRARYDATASOURCE,TABLEPREFIX,SORT,CREATETIME,STARTTIME,ENDTIME,TASKID,SOURCETYPE,SOURCEPARAM,EXTRACTSIMPLEPLOY FROM GC_INTERMEDIATE_LIBRARY ORDER BY CREATETIME  ";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToILEntity(rs));
    }

    @Override
    public List<ILOrgEntity> getAllOrgIdForProgrammeId(String programmeId) {
        String sql = "SELECT ID,PROGRAMMEID,ORGID FROM GC_INTERMEDIATE_LIBRARY_ORG WHERE PROGRAMMEID = ?";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToILOrgIdEntity(rs), new Object[]{programmeId});
    }

    @Override
    public ILEntity getProgrammeForId(String programmeId) {
        String sql = "SELECT ID,PROGRAMMENAME,LIBRARYDATASOURCE,TABLEPREFIX,SORT,CREATETIME,STARTTIME,ENDTIME,TASKID,SOURCETYPE,SOURCEPARAM,EXTRACTSIMPLEPLOY FROM GC_INTERMEDIATE_LIBRARY WHERE ID = ? ORDER BY CREATETIME";
        List iLEntityList = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToILEntity(rs), new Object[]{programmeId});
        if (!CollectionUtils.isEmpty((Collection)iLEntityList)) {
            return (ILEntity)iLEntityList.get(0);
        }
        return null;
    }

    @Override
    public String addProgramme(ILCondition iLCondition) {
        String sql = "INSERT INTO GC_INTERMEDIATE_LIBRARY (ID,PROGRAMMENAME,LIBRARYDATASOURCE,TABLEPREFIX,SORT,CREATETIME,STARTTIME,ENDTIME,TASKID,SOURCETYPE,SOURCEPARAM,EXTRACTSIMPLEPLOY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        String id = UUIDUtils.newUUIDStr();
        int insert = this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, id);
            ps.setString(2, iLCondition.getProgrammeName());
            ps.setString(3, iLCondition.getLibraryDataSource());
            ps.setString(4, iLCondition.getTablePrefix());
            ps.setString(5, iLCondition.getSort());
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(7, iLCondition.getStartTime());
            ps.setTimestamp(8, iLCondition.getEndTime());
            ps.setString(9, iLCondition.getTaskId());
            ps.setString(10, iLCondition.getSourceType());
            ps.setString(11, iLCondition.getSourceParam());
            ps.setString(12, iLCondition.getExtractSimplePloy());
        });
        if (insert != 1) {
            throw new RuntimeException("\u65b0\u589e\u6570\u636e\u5931\u8d25");
        }
        return id;
    }

    @Override
    public void addProgrammeOfOrgId(final ILCondition iLCondition) {
        String sql = "INSERT INTO GC_INTERMEDIATE_LIBRARY_ORG (ID,PROGRAMMEID,ORGID) VALUES (?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setString(2, iLCondition.getId());
                ps.setString(3, (String)iLCondition.getOrgIdList().get(i));
            }

            public int getBatchSize() {
                return iLCondition.getOrgIdList().size();
            }
        });
    }

    @Override
    public void deleteProgrammeOfOrgId(ILCondition iLCondition) {
        String sql = "DELETE FROM GC_INTERMEDIATE_LIBRARY_ORG WHERE PROGRAMMEID = ? ";
        this.jdbcTemplate.update(sql, ps -> ps.setString(1, iLCondition.getId()));
    }

    @Override
    public void addProgrammeOfField(final ILCondition iLCondition) {
        String sql = "INSERT INTO GC_INTERMEDIATE_LIBRARY_FIELD (ID,PROGRAMMEID,FIELDID) VALUES (?,?,?)";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setString(2, iLCondition.getId());
                ps.setString(3, (String)iLCondition.getFieldIdList().get(i));
            }

            public int getBatchSize() {
                return iLCondition.getFieldIdList().size();
            }
        });
    }

    @Override
    public ILEntity getProgrammeForName(String programmeName) {
        String sql = "SELECT ID,PROGRAMMENAME,LIBRARYDATASOURCE,TABLEPREFIX,SORT,CREATETIME,STARTTIME,ENDTIME,TASKID,SOURCETYPE,SOURCEPARAM,EXTRACTSIMPLEPLOY FROM GC_INTERMEDIATE_LIBRARY WHERE PROGRAMMENAME = ? ORDER BY CREATETIME";
        List iLEntityList = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToILEntity(rs), new Object[]{programmeName});
        if (!CollectionUtils.isEmpty((Collection)iLEntityList)) {
            return (ILEntity)iLEntityList.get(0);
        }
        return null;
    }

    @Override
    public void deleteFieldOfProgrammeId(final ILCondition iLCondition) {
        String sql = "DELETE FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ? AND FIELDID = ? ";
        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, iLCondition.getId());
                ps.setString(2, (String)iLCondition.getFieldIdList().get(i));
            }

            public int getBatchSize() {
                return iLCondition.getFieldIdList().size();
            }
        });
    }

    @Override
    public void deleteProgrammeOfField(ILCondition iLCondition) {
        String sql = "DELETE FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE ID = ? ";
        ArrayList batchArgs = new ArrayList();
        iLCondition.getFieldIdList().forEach(id -> batchArgs.add(new Object[]{id}));
        this.jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public void deleteAllProgrammeOfField(ILCondition iLCondition) {
        String sql = "DELETE FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ? ";
        this.jdbcTemplate.update(sql, ps -> ps.setString(1, iLCondition.getProgrammeId()));
    }

    @Override
    public void updateProgramme(ILCondition iLCondition) {
        String sql = "UPDATE GC_INTERMEDIATE_LIBRARY SET PROGRAMMENAME = ?,LIBRARYDATASOURCE = ?,TABLEPREFIX = ?,SORT = ? ,STARTTIME = ?,ENDTIME = ?,TASKID = ?,SOURCETYPE=?,SOURCEPARAM=?,EXTRACTSIMPLEPLOY=? WHERE ID = ?";
        int update = this.jdbcTemplate.update(sql, ps -> {
            ps.setString(1, iLCondition.getProgrammeName());
            ps.setString(2, iLCondition.getLibraryDataSource());
            ps.setString(3, iLCondition.getTablePrefix());
            ps.setString(4, iLCondition.getSort());
            ps.setTimestamp(5, iLCondition.getStartTime());
            ps.setTimestamp(6, iLCondition.getEndTime());
            ps.setString(7, iLCondition.getTaskId());
            ps.setString(8, iLCondition.getSourceType());
            ps.setString(9, iLCondition.getSourceParam());
            ps.setString(10, iLCondition.getExtractSimplePloy());
            ps.setString(11, iLCondition.getId());
        });
        if (update != 1) {
            throw new RuntimeException("\u4fee\u6539\u6570\u636e\u5931\u8d25");
        }
    }

    @Override
    public void deleteProgramme(ILCondition iLCondition) {
        String sql = "DELETE FROM GC_INTERMEDIATE_LIBRARY WHERE ID = ? ";
        this.jdbcTemplate.update(sql, ps -> ps.setString(1, iLCondition.getId()));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteZbDataByWhere(List<ZbDataEntity> zbDataEntityList, ILEntity iLEntity) throws SQLException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLEntity.getLibraryDataSource());
        Connection connection = null;
        Statement pstmt = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM " + iLEntity.getTablePrefix() + "_" + zbDataEntityList.get(0).getDataType() + "_" + fieldSaveTable + " WHERE YEAR = ? AND PERIOD = ? AND UNITCODE = ? AND ZBCODE = ? AND CURRENCYCODE = ?";
            pstmt = connection.prepareStatement(sql);
            for (ZbDataEntity entity : zbDataEntityList) {
                pstmt.setInt(1, entity.getYear());
                pstmt.setInt(2, entity.getPeriod());
                pstmt.setString(3, entity.getUnitCode());
                pstmt.setString(4, entity.getZbCode());
                pstmt.setString(5, !StringUtils.isEmpty((String)entity.getCurrencyCode()) ? entity.getCurrencyCode() : "CNY");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
        }
        catch (SQLException se) {
            if (connection != null) {
                connection.rollback();
            }
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u5220\u9664\u6570\u636e\u5f02\u5e38", se);
        }
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void deleteMdZbDataByWhere(List<MdZbDataEntity> mdZbDataEntityList, ILEntity iLEntity) throws SQLException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLEntity.getLibraryDataSource());
        Connection connection = null;
        Statement pstmt = null;
        try {
            connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            connection.setAutoCommit(false);
            String sql = "DELETE FROM " + iLEntity.getTablePrefix() + "_" + mdZbDataEntityList.get(0).getDataType() + "_" + floatFieldTable + " WHERE YEAR = ? AND PERIOD = ? AND UNITCODE = ? AND ZBCODE = ? AND CURRENCYCODE = ? AND FLOATAREA = ?";
            pstmt = connection.prepareStatement(sql);
            for (MdZbDataEntity entity : mdZbDataEntityList) {
                pstmt.setInt(1, entity.getYear());
                pstmt.setInt(2, entity.getPeriod());
                pstmt.setString(3, entity.getUnitCode());
                pstmt.setString(4, entity.getZbCode());
                pstmt.setString(5, !StringUtils.isEmpty((String)entity.getCurrencyCode()) ? entity.getCurrencyCode() : "CNY");
                pstmt.setString(6, entity.getFloatArea());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            connection.commit();
        }
        catch (SQLException se) {
            if (connection != null) {
                connection.rollback();
            }
            logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u5220\u9664\u6570\u636e\u5f02\u5e38", se);
        }
        finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    @Override
    public void pushZbData(final List<ZbDataEntity> zbDataEntityList, ILEntity iLEntity) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLEntity.getLibraryDataSource());
        String sql = "INSERT INTO " + iLEntity.getTablePrefix() + "_" + zbDataEntityList.get(0).getDataType() + "_" + fieldSaveTable + " (ID,YEAR,PERIOD,UNITCODE,CURRENCYCODE,ZBCODE,ZBVALUE_T,ZBVALUE_N,CREATETIME,UPTIMESTAMP) VALUES(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(String.valueOf(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setInt(2, ((ZbDataEntity)zbDataEntityList.get(i)).getYear());
                ps.setInt(3, ((ZbDataEntity)zbDataEntityList.get(i)).getPeriod());
                ps.setString(4, ((ZbDataEntity)zbDataEntityList.get(i)).getUnitCode());
                ps.setString(5, ((ZbDataEntity)zbDataEntityList.get(i)).getCurrencyCode());
                ps.setString(6, ((ZbDataEntity)zbDataEntityList.get(i)).getZbCode());
                ps.setString(7, ((ZbDataEntity)zbDataEntityList.get(i)).getZbValue_T());
                ps.setBigDecimal(8, ((ZbDataEntity)zbDataEntityList.get(i)).getZbValue_N());
                ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
            }

            public int getBatchSize() {
                return zbDataEntityList.size();
            }
        });
    }

    @Override
    public void pushMdZbData(final List<MdZbDataEntity> mdZbDataEntityList, ILEntity iLEntity) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLEntity.getLibraryDataSource());
        String sql = "INSERT INTO " + iLEntity.getTablePrefix() + "_" + mdZbDataEntityList.get(0).getDataType() + "_" + floatFieldTable + " (ID,YEAR,PERIOD,UNITCODE,CURRENCYCODE,ZBCODE,ZBVALUE_T,ZBVALUE_N,FLOATAREA,AREA_ROWID,CREATETIME,UPTIMESTAMP) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(String.valueOf(sql), new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setInt(2, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getYear());
                ps.setInt(3, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getPeriod());
                ps.setString(4, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getUnitCode());
                ps.setString(5, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getCurrencyCode());
                ps.setString(6, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getZbCode());
                ps.setString(7, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getZbValue_T());
                ps.setBigDecimal(8, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getZbValue_N());
                ps.setString(9, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getFloatArea());
                ps.setBigDecimal(10, ((MdZbDataEntity)mdZbDataEntityList.get(i)).getAreaRowId());
                ps.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
            }

            public int getBatchSize() {
                return mdZbDataEntityList.size();
            }
        });
    }

    @Override
    public List<ZbDataEntity> extractZbData(ZbDataEntity zbDataEntity, ILEntity iLEntity, JdbcTemplate jdbcTemplate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,YEAR,PERIOD,UNITCODE,CURRENCYCODE,ZBCODE,ZBVALUE_T,ZBVALUE_N,CREATETIME,UPTIMESTAMP ");
        sql.append("FROM ");
        sql.append(iLEntity.getTablePrefix());
        sql.append("_");
        sql.append(zbDataEntity.getDataType());
        sql.append("_ZBDATA WHERE YEAR = :YEAR AND PERIOD = :PERIOD AND UNITCODE = :UNITCODE AND " + SqlUtils.getConditionOfMulStrUseOr(zbDataEntity.getZbCodeList(), (String)"ZBCODE") + " AND CURRENCYCODE = :CURRENCYCODE");
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("YEAR", zbDataEntity.getYear());
        paramMap.put("PERIOD", zbDataEntity.getPeriod());
        paramMap.put("UNITCODE", zbDataEntity.getUnitCode());
        if (!StringUtils.isEmpty((String)zbDataEntity.getCurrencyCode())) {
            paramMap.put("CURRENCYCODE", zbDataEntity.getCurrencyCode());
        } else {
            paramMap.put("CURRENCYCODE", "CNY");
        }
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)jdbcTemplate);
        return jdbc.query(sql.toString(), paramMap, (rs, i) -> this.extractedRsToZbDataEntity(rs));
    }

    @Override
    public List<MdZbDataEntity> extractMdZbData(MdZbDataEntity mdZbDataEntity, ILEntity iLEntity, JdbcTemplate jdbcTemplate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,YEAR,PERIOD,UNITCODE,CURRENCYCODE,ZBCODE,ZBVALUE_T,ZBVALUE_N,FLOATAREA,AREA_ROWID,CREATETIME,UPTIMESTAMP ");
        sql.append("FROM ");
        sql.append(iLEntity.getTablePrefix());
        sql.append("_");
        sql.append(mdZbDataEntity.getDataType());
        sql.append("_MDZBDATA WHERE YEAR = :YEAR AND PERIOD = :PERIOD AND UNITCODE = :UNITCODE AND " + SqlUtils.getConditionOfMulStrUseOr(mdZbDataEntity.getZbCodeList(), (String)"ZBCODE") + " AND CURRENCYCODE = :CURRENCYCODE ");
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("YEAR", mdZbDataEntity.getYear());
        paramMap.put("PERIOD", mdZbDataEntity.getPeriod());
        paramMap.put("UNITCODE", mdZbDataEntity.getUnitCode());
        if (!StringUtils.isEmpty((String)mdZbDataEntity.getFloatArea())) {
            sql.append("AND FLOATAREA = :FLOATAREA ");
            paramMap.put("FLOATAREA", mdZbDataEntity.getFloatArea());
        }
        if (!StringUtils.isEmpty((String)mdZbDataEntity.getCurrencyCode())) {
            paramMap.put("CURRENCYCODE", mdZbDataEntity.getCurrencyCode());
        } else {
            paramMap.put("CURRENCYCODE", "CNY");
        }
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)jdbcTemplate);
        List zbDataEntityList = jdbc.query(sql.toString(), paramMap, (rs, i) -> {
            MdZbDataEntity mdZbData = this.extractedRsToMdZbDataEntity(rs);
            mdZbData.setDataType(mdZbDataEntity.getDataType());
            return mdZbData;
        });
        return zbDataEntityList;
    }

    @Override
    public List<MdZbDataEntity> extractAllMdZbData(MdZbDataEntity mdZbDataEntity, ILEntity iLEntity, JdbcTemplate jdbcTemplate) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ID,YEAR,PERIOD,UNITCODE,CURRENCYCODE,ZBCODE,ZBVALUE_T,ZBVALUE_N,FLOATAREA,AREA_ROWID,CREATETIME,UPTIMESTAMP ");
        sql.append("FROM ");
        sql.append(iLEntity.getTablePrefix());
        sql.append("_");
        sql.append(mdZbDataEntity.getDataType());
        sql.append("_MDZBDATA WHERE YEAR = :YEAR AND PERIOD = :PERIOD AND UNITCODE = :UNITCODE AND ZBCODE = :ZBCODE AND FLOATAREA = :FLOATAREA AND CURRENCYCODE = :CURRENCYCODE  ");
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("YEAR", mdZbDataEntity.getYear());
        paramMap.put("PERIOD", mdZbDataEntity.getPeriod());
        paramMap.put("UNITCODE", mdZbDataEntity.getUnitCode());
        paramMap.put("ZBCODE", mdZbDataEntity.getZbCode());
        paramMap.put("FLOATAREA", mdZbDataEntity.getFloatArea());
        if (!StringUtils.isEmpty((String)mdZbDataEntity.getCurrencyCode())) {
            paramMap.put("CURRENCYCODE", mdZbDataEntity.getCurrencyCode());
        } else {
            paramMap.put("CURRENCYCODE", "CNY");
        }
        sql.append(" ORDER BY AREA_ROWID DESC ");
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate((JdbcOperations)jdbcTemplate);
        return jdbc.query(sql.toString(), paramMap, (rs, i) -> this.extractedRsToMdZbDataEntity(rs));
    }

    @Override
    public MetaDataEntity extractMetaData(ILSyncCondition iLSyncCondition, String dataSource, String tablePrefix) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSource);
        String sql = "SELECT ID,CODE,TITLE,DESCR,DATATYPE,ACCURACY,FRACTIONDIGITS,FLOATAREA,CREATETIME,UPTIMESTAMP FROM " + tablePrefix + "_METADATA WHERE CODE = ?";
        List metaDataEntityList = jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToMetaDataEntity(rs), new Object[]{iLSyncCondition.getCode()});
        if (!CollectionUtils.isEmpty((Collection)metaDataEntityList)) {
            return (MetaDataEntity)metaDataEntityList.get(0);
        }
        return null;
    }

    @Override
    public void synchroProgramme(ILSetupCondition iLSetupCondition) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLSetupCondition.getLibraryDataSource());
        String sql = "INSERT INTO " + iLSetupCondition.getTablePrefix() + "_METADATA (ID,CODE,TITLE,DESCR,DATATYPE,ACCURACY,FRACTIONDIGITS,FLOATAREA,CREATETIME,UPTIMESTAMP,BELONGFORM) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
        final List<ILSyncCondition> iLSyncConditionList = iLSetupCondition.getILSyncConditionList();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setString(2, ((ILSyncCondition)iLSyncConditionList.get(i)).getCode());
                ps.setString(3, ((ILSyncCondition)iLSyncConditionList.get(i)).getTitle());
                ps.setString(4, ((ILSyncCondition)iLSyncConditionList.get(i)).getDescr());
                ps.setInt(5, ((ILSyncCondition)iLSyncConditionList.get(i)).getDataType());
                ps.setInt(6, ((ILSyncCondition)iLSyncConditionList.get(i)).getAccuracy());
                ps.setInt(7, ((ILSyncCondition)iLSyncConditionList.get(i)).getDecimal());
                ps.setString(8, ((ILSyncCondition)iLSyncConditionList.get(i)).getFloatarea());
                ps.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                ps.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                ps.setString(11, ((ILSyncCondition)iLSyncConditionList.get(i)).getBelongForm());
            }

            public int getBatchSize() {
                return iLSyncConditionList.size();
            }
        });
    }

    @Override
    public void clearProgramme(ILSetupCondition iLSetupCondition) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(iLSetupCondition.getLibraryDataSource());
        String sql = "DELETE FROM " + iLSetupCondition.getTablePrefix() + "_METADATA WHERE CODE = ? ";
        List<ILSyncCondition> iLSyncConditionList = iLSetupCondition.getILSyncConditionList();
        ArrayList batchArgs = new ArrayList();
        iLSyncConditionList.forEach(syncCondition -> batchArgs.add(new Object[]{syncCondition.getCode()}));
        int[] counts = jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    @Override
    public void clearProgrammeForYear(ILSetupCondition setupCondition, ILClearCondition clearCondition, String tablePrefix) {
        JdbcTemplate jdbcTemplateProgramme = this.getJdbcTemplate(setupCondition.getLibraryDataSource());
        String[] dataType = new String[]{"Y", "J", "H", "N"};
        String[] tables = new String[]{fieldSaveTable, floatFieldTable};
        for (String type : dataType) {
            for (int year = clearCondition.getStartYear(); year <= clearCondition.getEndYear(); ++year) {
                double currentProgress = clearCondition.getCurrentProgress() + clearCondition.getStepProgress();
                clearCondition.setCurrentProgress(currentProgress);
                clearCondition.getAsyncTaskMonitor().progressAndMessage(currentProgress, "");
                for (String tableName : tables) {
                    String sql = "DELETE FROM " + tablePrefix + "_" + type + "_" + tableName + " WHERE YEAR = ?";
                    final List<ILSyncCondition> syncConditionList = setupCondition.getILSyncConditionList();
                    final int finalYear = year;
                    jdbcTemplateProgramme.batchUpdate(String.valueOf(sql), new BatchPreparedStatementSetter(){

                        public void setValues(PreparedStatement ps, int i) throws SQLException {
                            ps.setInt(1, finalYear);
                        }

                        public int getBatchSize() {
                            return syncConditionList.size();
                        }
                    });
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ILFieldVO> getFieldOfProgrammeIdPage(ILFieldCondition iLFieldCondition, int totalCount) {
        String sql = "SELECT ID,PROGRAMMEID,FIELDID FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ?";
        List ilFieldVOList = new ArrayList();
        if (iLFieldCondition.getPageCurrent() != -1 && iLFieldCondition.getPageSize() != -1) {
            int startIdx = (iLFieldCondition.getPageCurrent() - 1) * iLFieldCondition.getPageSize();
            int endIndex = Math.min(startIdx + iLFieldCondition.getPageSize(), totalCount);
            Connection conn = null;
            try {
                conn = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
                IDatabase idatabase = DatabaseManager.getInstance().findDatabaseByConnection(conn);
                IPagingSQLBuilder pageBuilder = idatabase.createPagingSQLBuilder();
                pageBuilder.setRawSQL(sql);
                String pageSql = pageBuilder.buildSQL(startIdx, endIndex);
                ilFieldVOList = this.jdbcTemplate.query(pageSql, (rs, rowNum) -> this.extractedRsToIntermediateLibraryFieldEntity(rs), new Object[]{iLFieldCondition.getProgrammeId()});
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u4ea4\u6362\u4e2d\u5fc3\u83b7\u53d6\u5206\u9875\u8bb0\u5f55\u5f02\u5e38", e);
            }
            finally {
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.jdbcTemplate.getDataSource());
            }
        } else {
            ilFieldVOList = this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToIntermediateLibraryFieldEntity(rs), new Object[]{iLFieldCondition.getProgrammeId()});
        }
        return ilFieldVOList;
    }

    @Override
    public List<ILFieldVO> getFieldOfProgrammeId(String programmeId) {
        String sql = "SELECT ID,PROGRAMMEID,FIELDID FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ?";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedRsToIntermediateLibraryFieldEntity(rs), new Object[]{programmeId});
    }

    @Override
    public List<String> getFieldIdOfProgrammeId(String programmeId) {
        String sql = "SELECT FIELDID FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ?";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("FIELDID"), new Object[]{programmeId});
    }

    @Override
    public int getFieldCouontOfProgrammeId(ILFieldCondition iLFieldCondition) {
        String sql = "SELECT COUNT(*) AS FIELDCOUNT FROM GC_INTERMEDIATE_LIBRARY_FIELD WHERE PROGRAMMEID = ?";
        List countList = this.jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("FIELDCOUNT"), new Object[]{iLFieldCondition.getProgrammeId()});
        if (!CollectionUtils.isEmpty((Collection)countList)) {
            return (Integer)countList.get(0);
        }
        return 0;
    }

    @Override
    public List<String> getDataRegionKey(String fieldId) {
        String sql = "SELECT DL_REGION_KEY FROM NR_PARAM_DATALINK WHERE DL_FIELD_KEY = ?";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("DL_REGION_KEY"), new Object[]{fieldId});
    }

    @Override
    public List<FieldDataRegionEntity> getDataRegionKeyForId(String programmeId) {
        String sql = "SELECT DATALINKTEMP.DL_REGION_KEY,FIELDTEMP.FIELDID FROM GC_INTERMEDIATE_LIBRARY_FIELD FIELDTEMP JOIN NR_PARAM_DATALINK DATALINKTEMP ON FIELDTEMP.FIELDID = DATALINKTEMP.DL_FIELD_KEY WHERE FIELDTEMP.PROGRAMMEID = ? ";
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> this.extractedFieldDataRegion(rs), new Object[]{programmeId});
    }

    @Override
    public JdbcTemplate getJdbcTemplate(String dataSourceCode) {
        if (DCQueryStringHandle.isEmpty((String)dataSourceCode)) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25\uff0c\u6570\u636e\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
            return this.currentJdbcTemplate;
        }
        DataSourceInfoVO dto = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
        if (dto == null) {
            throw new DefinedQueryRuntimeException("\u6570\u636e\u5e93\u4e2d\u6ca1\u6709\u627e\u5230\u9009\u62e9\u7684\u6570\u636e\u6e90[" + dataSourceCode + "]\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u6e90\u914d\u7f6e!");
        }
        if (this.needInitDataSource(dto)) {
            dto.setPassWord(DCQueryDES.decrypt((String)dto.getPassWord()));
            this.addDataSource(dto);
        }
        return this.dataSourceMap.get(dataSourceCode).getJdbcTemplate();
    }

    private boolean needInitDataSource(DataSourceInfoVO dto) {
        if (!this.dataSourceMap.containsKey(dto.getCode())) {
            return true;
        }
        DataSourceTempInfo dataSourceTempInfo = this.dataSourceMap.get(dto.getCode());
        if (dataSourceTempInfo.getCreateTime() < dto.getUpdateTime()) {
            HikariDataSource dataSource = (HikariDataSource)dataSourceTempInfo.getJdbcTemplate().getDataSource();
            if (dataSource != null) {
                dataSource.close();
            }
            this.dataSourceMap.remove(dto.getCode());
            return true;
        }
        return false;
    }

    private void addDataSource(DataSourceInfoVO dto) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(dto.getDriver());
        hikariDataSource.setPoolName(dto.getCode() + "Pool");
        hikariDataSource.setJdbcUrl(dto.getUrl());
        hikariDataSource.setUsername(dto.getUserName());
        hikariDataSource.setPassword(dto.getPassWord());
        hikariDataSource.setMaximumPoolSize(this.poolConfig.getMaxPoolSize());
        if (this.poolConfig.getMinIdle() > -1) {
            hikariDataSource.setMinimumIdle(this.poolConfig.getMinIdle());
        }
        if (this.poolConfig.getMaxLifeTime() > -1L) {
            hikariDataSource.setMaxLifetime(this.poolConfig.getMaxLifeTime());
        }
        if (this.poolConfig.getConnectionTimeout() > -1L) {
            hikariDataSource.setConnectionTimeout(this.poolConfig.getConnectionTimeout());
        }
        if (this.poolConfig.getIdleTimeout() > -1L) {
            hikariDataSource.setIdleTimeout(this.poolConfig.getIdleTimeout());
        }
        this.dataSourceMap.put(dto.getCode(), new DataSourceTempInfo(hikariDataSource, dto.getUpdateTime()));
    }

    private MetaDataEntity extractedRsToMetaDataEntity(ResultSet rs) throws SQLException {
        MetaDataEntity metaDataEntity = new MetaDataEntity();
        metaDataEntity.setId(rs.getString("ID"));
        metaDataEntity.setCode(rs.getString("CODE"));
        metaDataEntity.setTitle(rs.getString("TITLE"));
        metaDataEntity.setDescr(rs.getString("DESCR"));
        metaDataEntity.setDataType(rs.getInt("DATATYPE"));
        metaDataEntity.setAccuracy(rs.getInt("ACCURACY"));
        metaDataEntity.setDecimal(rs.getInt("FRACTIONDIGITS"));
        metaDataEntity.setFloatArea(rs.getString("FLOATAREA"));
        metaDataEntity.setCreateTime(rs.getTimestamp("CREATETIME"));
        metaDataEntity.setUpTimeStamp(rs.getTimestamp("UPTIMESTAMP"));
        metaDataEntity.setBelongForm(rs.getString("BELONGFORM"));
        return metaDataEntity;
    }

    private MdZbDataEntity extractedRsToMdZbDataEntity(ResultSet rs) throws SQLException {
        MdZbDataEntity mdZbDataEntity = new MdZbDataEntity();
        mdZbDataEntity.setId(rs.getString("ID"));
        mdZbDataEntity.setYear(rs.getInt("YEAR"));
        mdZbDataEntity.setPeriod(rs.getInt("PERIOD"));
        mdZbDataEntity.setUnitCode(rs.getString("UNITCODE"));
        mdZbDataEntity.setCurrencyCode(rs.getString("CURRENCYCODE"));
        mdZbDataEntity.setZbCode(rs.getString("ZBCODE"));
        mdZbDataEntity.setZbValue_T(rs.getString("ZBVALUE_T"));
        mdZbDataEntity.setZbValue_N(rs.getBigDecimal("ZBVALUE_N"));
        mdZbDataEntity.setFloatArea(rs.getString("FLOATAREA"));
        mdZbDataEntity.setAreaRowId(rs.getBigDecimal("AREA_ROWID"));
        mdZbDataEntity.setCreateTime(rs.getTimestamp("CREATETIME"));
        mdZbDataEntity.setUpTimeStamp(rs.getTimestamp("UPTIMESTAMP"));
        return mdZbDataEntity;
    }

    private ZbDataEntity extractedRsToZbDataEntity(ResultSet rs) throws SQLException {
        ZbDataEntity zbDataEntity = new ZbDataEntity();
        zbDataEntity.setId(rs.getString("ID"));
        zbDataEntity.setYear(rs.getInt("YEAR"));
        zbDataEntity.setPeriod(rs.getInt("PERIOD"));
        zbDataEntity.setUnitCode(rs.getString("UNITCODE"));
        zbDataEntity.setCurrencyCode(rs.getString("CURRENCYCODE"));
        zbDataEntity.setZbCode(rs.getString("ZBCODE"));
        zbDataEntity.setZbValue_T(rs.getString("ZBVALUE_T"));
        zbDataEntity.setZbValue_N(rs.getBigDecimal("ZBVALUE_N"));
        zbDataEntity.setCreateTime(rs.getTimestamp("CREATETIME"));
        zbDataEntity.setUpTimeStamp(rs.getTimestamp("UPTIMESTAMP"));
        return zbDataEntity;
    }

    private ILEntity extractedRsToILEntity(ResultSet rs) throws SQLException {
        ILEntity iLEntity = new ILEntity();
        iLEntity.setId(rs.getString("ID"));
        iLEntity.setProgrammeName(rs.getString("PROGRAMMENAME"));
        iLEntity.setLibraryDataSource(rs.getString("LIBRARYDATASOURCE"));
        iLEntity.setTablePrefix(rs.getString("TABLEPREFIX"));
        iLEntity.setSort(rs.getString("SORT"));
        iLEntity.setCreateTime(rs.getTimestamp("CREATETIME"));
        iLEntity.setStartTime(rs.getTimestamp("STARTTIME"));
        iLEntity.setEndTime(rs.getTimestamp("ENDTIME"));
        iLEntity.setTaskId(rs.getString("TASKID"));
        iLEntity.setSourceType(rs.getString("SOURCETYPE"));
        iLEntity.setSourceParam(rs.getString("SOURCEPARAM"));
        iLEntity.setExtractSimplePloy(rs.getString("EXTRACTSIMPLEPLOY"));
        return iLEntity;
    }

    private ILOrgEntity extractedRsToILOrgIdEntity(ResultSet rs) throws SQLException {
        ILOrgEntity iLOrgEntity = new ILOrgEntity();
        iLOrgEntity.setId(rs.getString("ID"));
        iLOrgEntity.setProgrammeId(rs.getString("PROGRAMMEID"));
        iLOrgEntity.setOrgId(rs.getString("ORGID"));
        return iLOrgEntity;
    }

    private ILFieldVO extractedRsToIntermediateLibraryFieldEntity(ResultSet rs) throws SQLException {
        ILFieldVO ILFieldVO2 = new ILFieldVO();
        ILFieldVO2.setId(rs.getString("ID"));
        ILFieldVO2.setProgrammeId(rs.getString("PROGRAMMEID"));
        ILFieldVO2.setFieldId(rs.getString("FIELDID"));
        return ILFieldVO2;
    }

    private FieldDataRegionEntity extractedFieldDataRegion(ResultSet rs) throws SQLException {
        FieldDataRegionEntity fieldDataRegionEntity = new FieldDataRegionEntity();
        fieldDataRegionEntity.setDataRegionKey(rs.getString("DL_REGION_KEY"));
        fieldDataRegionEntity.setFieldId(rs.getString("FIELDID"));
        return fieldDataRegionEntity;
    }

    @Override
    public void addOrgInfo(final List<GcOrgCacheVO> gcOrgCacheVOList, ILCondition iLCondition) {
        if (CollectionUtils.isEmpty(gcOrgCacheVOList)) {
            return;
        }
        JdbcTemplate jdbcTemp = this.getJdbcTemplate(iLCondition.getLibraryDataSource());
        String deleteSql = "DELETE FROM " + iLCondition.getTablePrefix() + "_MD_ORG WHERE CODE = ? ";
        ArrayList batchArgs = new ArrayList();
        gcOrgCacheVOList.forEach(syncCondition -> batchArgs.add(new Object[]{syncCondition.getCode()}));
        jdbcTemp.batchUpdate(deleteSql, batchArgs);
        String sql = "INSERT INTO " + iLCondition.getTablePrefix() + "_MD_ORG (ID,CODE,NAME,PARENTCODE,CREATETIME) VALUES (?,?,?,?,?)";
        jdbcTemp.batchUpdate(sql, new BatchPreparedStatementSetter(){

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, UUIDUtils.newUUIDStr());
                ps.setString(2, ((GcOrgCacheVO)gcOrgCacheVOList.get(i)).getCode());
                ps.setString(3, ((GcOrgCacheVO)gcOrgCacheVOList.get(i)).getTitle());
                ps.setString(4, ((GcOrgCacheVO)gcOrgCacheVOList.get(i)).getParentId());
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            public int getBatchSize() {
                return gcOrgCacheVOList.size();
            }
        });
    }

    @Override
    public void createOrgInfo(ILCondition iLCondition) {
        String tableName = iLCondition.getTablePrefix().toUpperCase() + "_MD_ORG";
        String indexName = iLCondition.getTablePrefix().toUpperCase() + "_INDEX_ORG";
        String pkName = iLCondition.getTablePrefix().toUpperCase() + "_MD_ORG_PK";
        JdbcTemplate jdbcTemp = this.getJdbcTemplate(iLCondition.getLibraryDataSource());
        String createSql = "CREATE TABLE " + tableName + "(ID VARCHAR(50) NOT NULL, CODE VARCHAR(100), NAME VARCHAR(200), PARENTCODE VARCHAR(100), CREATETIME TIMESTAMP, UPTIMESTAMP TIMESTAMP, CONSTRAINT " + pkName + " PRIMARY KEY (ID))";
        String createIndexSql = "CREATE INDEX " + indexName + " ON " + tableName + " (CODE, PARENTCODE)";
        jdbcTemp.execute(createSql);
        jdbcTemp.execute(createIndexSql);
    }

    @Override
    public Map<String, List<String>> getDataRegionKeyMap() {
        String sql = "SELECT DL_FIELD_KEY,DL_REGION_KEY FROM NR_PARAM_DATALINK ";
        HashMap<String, List<String>> fieldId2DataRegionKeys = new HashMap<String, List<String>>();
        this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            List dataRegionKeys = fieldId2DataRegionKeys.getOrDefault(rs.getString("DL_FIELD_KEY"), new ArrayList());
            dataRegionKeys.add(rs.getString("DL_REGION_KEY"));
            fieldId2DataRegionKeys.put(rs.getString("DL_FIELD_KEY"), dataRegionKeys);
            return null;
        });
        return fieldId2DataRegionKeys;
    }
}

