/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  com.jiuqi.va.query.datasource.dao.DataSourceInfoDao
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  com.jiuqi.va.query.datasource.service.DynamicDataSourceService
 *  com.jiuqi.va.query.util.DCQueryJdbcUtils
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.bde.bizmodel.impl.adaptor;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import com.jiuqi.va.query.datasource.service.DynamicDataSourceService;
import com.jiuqi.va.query.util.DCQueryJdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BdeDataSourceServiceImpl
implements DataSourceService {
    @Value(value="${spring.datasource.dbType}")
    private String dbType;
    @Autowired
    private DynamicDataSourceService dataSourceService;
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;

    public String getDbType(String dataSourceCode) {
        if (StringUtils.isEmpty((String)dataSourceCode) || CURRENT.equals(dataSourceCode)) {
            return this.dbType;
        }
        return this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode).getDataBaseType();
    }

    public Connection getConnection(String dataSourceCode) {
        return this.dataSourceService.getConnection(this.getDataSourceCodeByDefault(dataSourceCode));
    }

    public void closeConnection(String dataSourceCode, Connection conn) {
        this.dataSourceService.closeConnection(this.getDataSourceCodeByDefault(dataSourceCode), conn);
    }

    public void releaseResource(String dataSourceCode, Connection conn, Statement st, ResultSet rs) {
        DCQueryJdbcUtils.colseResource(null, (Statement)st, (ResultSet)rs);
        this.dataSourceService.closeConnection(this.getDataSourceCodeByDefault(dataSourceCode), conn);
    }

    public <T> T query(String dataSourceCode, String sql, Object[] args, ResultSetExtractor<T> rse) {
        return (T)this.dataSourceService.query(dataSourceCode, sql, args, rse);
    }

    public <T> List<T> query(String dataSourceCode, String sql, Object[] args, RowMapper<T> rowMapper) {
        return this.dataSourceService.query(dataSourceCode, sql, args, rowMapper);
    }

    public <T> T pageQuery(String dataSourceCode, String sql, int pageNumber, int pageSize, Object[] args, ResultSetExtractor<T> rse) {
        String pageSql = this.dataSourceService.getPageSql(dataSourceCode, sql, pageNumber, pageSize);
        return this.query(dataSourceCode, pageSql, args, rse);
    }

    public <T> List<T> pageQuery(String dataSourceCode, String sql, int pageNumber, int pageSize, Object[] args, RowMapper<T> rowMapper) {
        String pageSql = this.dataSourceService.getPageSql(dataSourceCode, sql, pageNumber, pageSize);
        return this.query(dataSourceCode, pageSql, args, rowMapper);
    }

    public int[] batchUpdate(String dataSourceCode, String sql, List<Object[]> batchArgs) {
        return this.dataSourceService.batchUpdate(dataSourceCode, sql, batchArgs);
    }

    public void execute(String dataSourceCode, String sql) {
        this.dataSourceService.execute(dataSourceCode, sql);
    }

    private String getDataSourceCodeByDefault(String dataSourceCode) {
        return StringUtils.isEmpty((String)dataSourceCode) ? DataSourceEnum.CURRENT.getName() : dataSourceCode;
    }
}

