/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.ConnectionTypeEnum
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 */
package com.jiuqi.va.query.datasource.dao.impl;

import com.jiuqi.va.query.common.ConnectionTypeEnum;
import com.jiuqi.va.query.common.dao.impl.UserDefinedBaseDaoImpl;
import com.jiuqi.va.query.common.service.QuerySqlInterceptorUtil;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.util.DCQuerySqlBuildUtil;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class DataSourceInfoDaoImpl
extends UserDefinedBaseDaoImpl
implements DataSourceInfoDao {
    @Override
    public DataSourceInfoVO getDataSourceInfoByCode(String dataSourceCode) {
        String sql = "select id,name,driver,url,userName,pwd,ip,port,dataBaseType,dataBaseName,updateTime,enableTempTable,inParamValueMaxCount,dataBaseParam, connectionType from DC_Query_DataSourceInfo where code = ? ";
        sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sql);
        return (DataSourceInfoVO)this.getJdbcTemplate().query(sql, ps -> ps.setString(1, dataSourceCode), rs -> {
            if (rs.next()) {
                DataSourceInfoVO dataSource = new DataSourceInfoVO();
                dataSource.setCode(dataSourceCode);
                dataSource.setId(rs.getString(1));
                dataSource.setName(rs.getString(2));
                dataSource.setDriver(rs.getString(3));
                dataSource.setUrl(rs.getString(4));
                dataSource.setUserName(rs.getString(5));
                dataSource.setPassWord(rs.getString(6));
                dataSource.setIp(rs.getString(7));
                dataSource.setPort(rs.getString(8));
                dataSource.setDataBaseType(rs.getString(9));
                dataSource.setDataBaseName(rs.getString(10));
                dataSource.setUpdateTime(Long.valueOf(rs.getTimestamp(11).getTime()));
                dataSource.setEnableTempTable(Boolean.valueOf(rs.getInt(12) == 1));
                dataSource.setInParamValueMaxCount(Integer.valueOf(rs.getInt(13)));
                dataSource.setDataBaseParam(rs.getString(14));
                dataSource.setConnectionType(StringUtils.hasText(rs.getString(15)) ? rs.getString(15) : ConnectionTypeEnum.NORMAL.getName());
                return dataSource;
            }
            return null;
        });
    }

    @Override
    public List<DataSourceInfoVO> getDataSources() {
        String sql = "select id,code,name,driver,url,userName,pwd,ip,port,dataBaseType,dataBaseName,updateTime,enableTempTable,inParamValueMaxCount,dataBaseParam, connectionType from DC_Query_DataSourceInfo where 1 = 1 order by updateTime desc ";
        sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sql);
        return this.getJdbcTemplate().query(sql, (rs, rowNum) -> {
            DataSourceInfoVO dataSource = new DataSourceInfoVO();
            dataSource.setId(rs.getString(1));
            dataSource.setCode(rs.getString(2));
            dataSource.setName(rs.getString(3));
            dataSource.setDriver(rs.getString(4));
            dataSource.setUrl(rs.getString(5));
            dataSource.setUserName(rs.getString(6));
            dataSource.setPassWord(rs.getString(7));
            dataSource.setIp(rs.getString(8));
            dataSource.setPort(rs.getString(9));
            dataSource.setDataBaseType(rs.getString(10));
            dataSource.setDataBaseName(rs.getString(11));
            dataSource.setUpdateTime(Long.valueOf(rs.getTimestamp(12).getTime()));
            dataSource.setEnableTempTable(Boolean.valueOf(rs.getInt(13) == 1));
            dataSource.setInParamValueMaxCount(Integer.valueOf(rs.getInt(14)));
            dataSource.setDataBaseParam(rs.getString(15));
            dataSource.setConnectionType(StringUtils.hasText(rs.getString(16)) ? rs.getString(16) : ConnectionTypeEnum.NORMAL.getName());
            return dataSource;
        });
    }

    @Override
    public DataSourceInfoVO getDataSourceInfoByCodeAndNotId(String dataSourceCode, String id) {
        String sql = "select driver,pwd,url,userName,updateTime from DC_Query_DataSourceInfo where code = ? ";
        if (!DCQueryStringHandle.isEmpty(id)) {
            sql = sql + " and id <> '" + id + "'";
        }
        sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sql);
        return (DataSourceInfoVO)this.getJdbcTemplate().query(sql, ps -> ps.setString(1, dataSourceCode), rs -> {
            if (rs.next()) {
                DataSourceInfoVO dataSource = new DataSourceInfoVO();
                dataSource.setCode(dataSourceCode);
                dataSource.setDriver(rs.getString(1));
                dataSource.setPassWord(rs.getString(2));
                dataSource.setUrl(rs.getString(3));
                dataSource.setUserName(rs.getString(4));
                dataSource.setUpdateTime(Long.valueOf(rs.getTimestamp(5).getTime()));
                return dataSource;
            }
            return null;
        });
    }

    @Override
    public void deleteDataSourceByCode(List<String> codes) {
        String sql = "delete from DC_Query_DataSourceInfo where 1 = 1 and  \n" + DCQuerySqlBuildUtil.getStrInCondi("code", codes.size());
        sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sql);
        this.getJdbcTemplate().update(sql, codes.toArray());
    }

    @Override
    public void save(DataSourceInfoVO dataSourceInfo) {
        String sql = "insert into DC_Query_DataSourceInfo \n  (id,code,name,driver,url,userName,pwd,ip,port,dataBaseType,dataBaseName,updateTime,enableTempTable,inParamValueMaxCount,dataBaseParam,connectionType) \n  values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) \n";
        Object[] args = new Object[]{dataSourceInfo.getId(), dataSourceInfo.getCode(), dataSourceInfo.getName(), dataSourceInfo.getDriver(), dataSourceInfo.getUrl(), dataSourceInfo.getUserName(), dataSourceInfo.getPassWord(), dataSourceInfo.getIp(), dataSourceInfo.getPort(), dataSourceInfo.getDataBaseType(), dataSourceInfo.getDataBaseName(), new Timestamp(dataSourceInfo.getUpdateTime()), Boolean.TRUE.equals(dataSourceInfo.getEnableTempTable()) ? 1 : 0, dataSourceInfo.getInParamValueMaxCount(), dataSourceInfo.getDataBaseParam(), dataSourceInfo.getConnectionType()};
        this.getJdbcTemplate().update(QuerySqlInterceptorUtil.getInterceptorSqlString(sql), args);
    }

    @Override
    public void updateById(DataSourceInfoVO dataSourceInfo) {
        Object[] args = new Object[]{dataSourceInfo.getCode(), dataSourceInfo.getName(), dataSourceInfo.getDriver(), dataSourceInfo.getUrl(), dataSourceInfo.getUserName(), dataSourceInfo.getPassWord(), dataSourceInfo.getIp(), dataSourceInfo.getPort(), dataSourceInfo.getDataBaseType(), new Timestamp(dataSourceInfo.getUpdateTime()), dataSourceInfo.getDataBaseName(), Boolean.TRUE.equals(dataSourceInfo.getEnableTempTable()) ? 1 : 0, dataSourceInfo.getInParamValueMaxCount(), dataSourceInfo.getDataBaseParam(), dataSourceInfo.getConnectionType(), dataSourceInfo.getId()};
        String sql = "update DC_Query_DataSourceInfo \n   set code = ?, name = ?, driver = ?, url = ?, userName = ?, pwd = ?, ip = ?, port = ?,  \n       dataBaseType = ?, updateTime = ?,dataBaseName = ?,enableTempTable = ?,inParamValueMaxCount = ? ,dataBaseParam = ?, connectionType = ? \n where id = ? \n";
        sql = QuerySqlInterceptorUtil.getInterceptorSqlString(sql);
        this.getJdbcTemplate().update(sql, args);
    }
}

