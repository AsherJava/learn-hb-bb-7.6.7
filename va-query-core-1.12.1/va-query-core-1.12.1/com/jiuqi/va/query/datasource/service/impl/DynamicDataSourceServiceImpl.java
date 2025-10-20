/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  com.zaxxer.hikari.HikariDataSource
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.va.query.datasource.service.impl;

import com.jiuqi.va.query.common.DataSourcePoolProperties;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.gather.DataBaseHandleGather;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.datasource.service.QueryDataSourceService;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.datasource.vo.DataSourceTempInfo;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.util.DCQueryDES;
import com.jiuqi.va.query.util.DCQueryJdbcUtils;
import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.VAQueryI18nUtil;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class DynamicDataSourceServiceImpl
implements DynamicDataSourceService {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceServiceImpl.class);
    @Autowired
    private JdbcTemplate currentJdbcTemplate;
    @Autowired
    private DataBaseHandleGather handleGather;
    @Autowired
    private DataSourcePoolProperties poolConfig;
    private final Map<String, DataSourceTempInfo> dataSourceMap = new ConcurrentHashMap<String, DataSourceTempInfo>();
    private final Map<String, String> dataSourceCodeToDataBaseTypeMap = new ConcurrentHashMap<String, String>();
    private static final String DATA_SOURCE_NOT_FOUND = "\u6570\u636e\u5e93\u4e2d\u6ca1\u6709\u627e\u5230\u9009\u62e9\u7684\u6570\u636e\u6e90[%s]\uff0c\u8bf7\u68c0\u67e5\u6570\u636e\u6e90\u914d\u7f6e!";
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;

    @Override
    public Connection getConnection(String dataSourceCode) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
        if (jdbcTemplate == null) {
            throw new DefinedQueryRuntimeException(String.format(DATA_SOURCE_NOT_FOUND, dataSourceCode));
        }
        return DataSourceUtils.getConnection((DataSource)jdbcTemplate.getDataSource());
    }

    @Override
    public void closeConnection(String dataSourceCode, Connection conn) {
        if (DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.currentJdbcTemplate.getDataSource());
            return;
        }
        if (this.dataSourceMap.get(dataSourceCode) != null) {
            DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.dataSourceMap.get(dataSourceCode).getJdbcTemplate().getDataSource());
        } else {
            logger.warn("\u5728\u6570\u636e\u6e90\u8fde\u63a5\u6c60\u7684map\u4e2d\u6ca1\u6709\u627e\u5230dataSourceCode\u3010{}\u3011", (Object)dataSourceCode);
            DCQueryJdbcUtils.closeConnection(conn);
        }
    }

    @Override
    public <T> T query(String dataSourceCode, String sql, Object[] args, ResultSetExtractor<T> rse) {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
        try {
            return (T)jdbcTemplate.query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), rse, args);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9a\u4e49sql\u6267\u884c\u5f02\u5e38\uff1a", e);
            throw new DefinedQueryRuntimeException(VAQueryI18nUtil.getMessage("va.query.executeError"));
        }
    }

    @Override
    public <T> List<T> query(String dataSourceCode, String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(dataSourceCode);
        try {
            return jdbcTemplate.query(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), rowMapper, args);
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9a\u4e49sql\u6267\u884c\u5f02\u5e38\uff1a", e);
            throw new DefinedQueryRuntimeException(VAQueryI18nUtil.getMessage("va.query.executeError"));
        }
    }

    @Override
    public <T> T pageQuery(String dataSourceCode, String sql, int pageNumber, int pageSize, Object[] args, ResultSetExtractor<T> rse) {
        String pageSql = this.getPageSql(dataSourceCode, sql, pageNumber, pageSize);
        return this.query(dataSourceCode, pageSql, args, rse);
    }

    @Override
    public <T> List<T> pageQuery(String dataSourceCode, String sql, int pageNumber, int pageSize, Object[] args, RowMapper<T> rowMapper) {
        String pageSql = this.getPageSql(dataSourceCode, sql, pageNumber, pageSize);
        return this.query(dataSourceCode, pageSql, args, rowMapper);
    }

    @Override
    public int[] batchUpdate(String dataSourceCode, String sql, List<Object[]> batchArgs) {
        return this.getJdbcTemplate(dataSourceCode).batchUpdate(sql, batchArgs);
    }

    @Override
    public void execute(String dataSourceCode, String sql) {
        this.getJdbcTemplate(dataSourceCode).execute(sql);
    }

    @Override
    public String getPageSql(String dataSourceCode, String sql, int pageNumber, int pageSize) {
        String dataBaseType;
        QueryDataBaseHandler handle;
        if (!this.dataSourceCodeToDataBaseTypeMap.containsKey(dataSourceCode)) {
            if (DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
                this.dataSourceCodeToDataBaseTypeMap.put(DataSourceEnum.CURRENT.getName(), this.handleGather.getTypeNameByProductName(this.getDatabaseProductName()));
            } else {
                DataSourceInfoVO dto = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
                if (dto == null) {
                    throw new DefinedQueryRuntimeException(String.format(DATA_SOURCE_NOT_FOUND, dataSourceCode));
                }
                this.dataSourceCodeToDataBaseTypeMap.put(dto.getCode(), dto.getDataBaseType());
            }
        }
        if ((handle = this.handleGather.getHandleServiceByTypeName(dataBaseType = this.dataSourceCodeToDataBaseTypeMap.get(dataSourceCode))) == null) {
            throw new DefinedQueryRuntimeException("\u76ee\u524d\u5206\u9875\u67e5\u8be2\u6682\u4e0d\u652f\u6301\u60a8\u4f7f\u7528\u7684[" + dataBaseType + "]\u6570\u636e\u5e93\uff01");
        }
        return handle.getPageSql(sql, pageNumber, pageSize);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public JdbcTemplate getJdbcTemplate(String dataSourceCode) {
        if (DCQueryStringHandle.isEmpty(dataSourceCode)) {
            throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25\uff0c\u6570\u636e\u6e90\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        if (DataSourceEnum.CURRENT.getName().equals(dataSourceCode)) {
            return this.currentJdbcTemplate;
        }
        DataSourceInfoVO dto = this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
        if (dto == null) {
            throw new DefinedQueryRuntimeException(String.format(DATA_SOURCE_NOT_FOUND, dataSourceCode));
        }
        DynamicDataSourceServiceImpl dynamicDataSourceServiceImpl = this;
        synchronized (dynamicDataSourceServiceImpl) {
            if (this.needInitDataSource(dto)) {
                QueryDataSourceService queryDataSourceService = DCQuerySpringContextUtils.getBean(QueryDataSourceService.class);
                queryDataSourceService.testDataSource(dto);
                dto.setPassWord(DCQueryDES.decrypt(dto.getPassWord()));
                this.addDataSource(dto);
            }
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
            dataSource.close();
            this.dataSourceMap.remove(dto.getCode());
            return true;
        }
        return false;
    }

    public String getDatabaseProductName() {
        String string;
        Connection connection = null;
        try {
            connection = this.currentJdbcTemplate.getDataSource().getConnection();
            string = connection.getMetaData().getDatabaseProductName();
        }
        catch (SQLException e) {
            try {
                throw new DefinedQueryRuntimeException("\u83b7\u53d6\u6570\u636e\u5e93\u7c7b\u578b\u5931\u8d25\uff01", (Throwable)e);
            }
            catch (Throwable throwable) {
                DCQueryJdbcUtils.closeConnection(connection);
                throw throwable;
            }
        }
        DCQueryJdbcUtils.closeConnection(connection);
        return string;
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
}

