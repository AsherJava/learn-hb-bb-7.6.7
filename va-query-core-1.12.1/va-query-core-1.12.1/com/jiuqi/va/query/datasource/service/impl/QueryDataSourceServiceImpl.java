/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.common.ConnectionTypeEnum
 *  com.jiuqi.va.query.datacheck.DataCheckManage
 *  com.jiuqi.va.query.datacheck.InterceptorEnum
 *  com.jiuqi.va.query.datasource.database.QueryDataBaseHandler
 *  com.jiuqi.va.query.datasource.vo.DataSourceInfoVO
 *  com.jiuqi.va.query.domain.DataCheckParam
 *  com.jiuqi.va.query.domain.DataCheckResult
 *  com.jiuqi.va.query.exception.DefinedQueryRuntimeException
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.query.datasource.service.impl;

import com.jiuqi.va.query.common.ConnectionTypeEnum;
import com.jiuqi.va.query.datacheck.DataCheckManage;
import com.jiuqi.va.query.datacheck.InterceptorEnum;
import com.jiuqi.va.query.datasource.dao.DataSourceInfoDao;
import com.jiuqi.va.query.datasource.database.QueryDataBaseHandler;
import com.jiuqi.va.query.datasource.gather.DataBaseHandleGather;
import com.jiuqi.va.query.datasource.service.QueryDataSourceService;
import com.jiuqi.va.query.datasource.vo.DataSourceInfoVO;
import com.jiuqi.va.query.domain.DataCheckParam;
import com.jiuqi.va.query.domain.DataCheckResult;
import com.jiuqi.va.query.exception.DefinedQueryRuntimeException;
import com.jiuqi.va.query.util.DCQueryDES;
import com.jiuqi.va.query.util.DCQueryJdbcUtils;
import com.jiuqi.va.query.util.DCQueryStringHandle;
import com.jiuqi.va.query.util.DCQueryUUIDUtil;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QueryDataSourceServiceImpl
implements QueryDataSourceService {
    @Autowired
    private DataSourceInfoDao dataSourceInfoDao;
    @Autowired
    private DataBaseHandleGather dataBaseHandleGather;
    @Autowired
    private DataCheckManage dataCheckManage;

    @Override
    public List<DataSourceInfoVO> getAllDataSources() {
        return this.dataSourceInfoDao.getDataSources();
    }

    @Override
    public DataSourceInfoVO getDataSourceInfoByCode(String dataSourceCode) {
        return this.dataSourceInfoDao.getDataSourceInfoByCode(dataSourceCode);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateDataSource(DataSourceInfoVO dataSourceInfoParam) {
        DataSourceInfoVO dataSourceInfo = this.initDriverAndUrl(dataSourceInfoParam);
        dataSourceInfo.setUpdateTime(Long.valueOf(System.currentTimeMillis()));
        DataSourceInfoVO dbDataSourceInfo = this.dataSourceInfoDao.getDataSourceInfoByCodeAndNotId(dataSourceInfo.getCode(), dataSourceInfo.getId());
        if (dbDataSourceInfo != null) {
            throw new DefinedQueryRuntimeException("\u6570\u636e\u5e93\u4e2d\u5b58\u5728\u76f8\u540c\u6570\u636e\u6e90\u6807\u8bc6\u7684\u6570\u636e\uff0c\u8bf7\u4fee\u6539\u6570\u636e\u6e90\u6807\u8bc6");
        }
        if (DCQueryStringHandle.isEmpty(dataSourceInfo.getId())) {
            dataSourceInfo.setId(DCQueryUUIDUtil.getUUIDStr());
            this.dataSourceInfoDao.save(dataSourceInfo);
        } else {
            this.dataSourceInfoDao.updateById(dataSourceInfo);
        }
    }

    private DataSourceInfoVO initDriverAndUrl(DataSourceInfoVO dataSourceInfo) {
        QueryDataBaseHandler handleService = this.dataBaseHandleGather.getHandleServiceByTypeName(dataSourceInfo.getDataBaseType());
        dataSourceInfo.setDriver(handleService.getDriverName());
        String url = "";
        url = ConnectionTypeEnum.URLONLY.getName().equals(dataSourceInfo.getConnectionType()) ? dataSourceInfo.getUrl() : handleService.getUrl(dataSourceInfo);
        dataSourceInfo.setUrl(url);
        return dataSourceInfo;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteDataSourceByCode(List<String> codes) {
        if (codes == null || codes.isEmpty()) {
            throw new DefinedQueryRuntimeException("\u8bf7\u9009\u62e9\u8981\u5220\u9664\u7684\u6570\u636e");
        }
        List dataSourceInfoVOS = this.dataSourceInfoDao.getDataSources().stream().filter(dataSourceInfoVO -> codes.contains(dataSourceInfoVO.getCode())).collect(Collectors.toList());
        DataCheckParam dataCheckParam = new DataCheckParam();
        dataCheckParam.setDataSourceInfoVOS(dataSourceInfoVOS);
        DataCheckResult dataCheckResult = this.dataCheckManage.preHandlerByType(dataCheckParam, InterceptorEnum.DataSourceDelete);
        if (!dataCheckResult.isPass()) {
            throw new DefinedQueryRuntimeException("\u64cd\u4f5c\u5931\u8d25\uff1a" + dataCheckResult.getMessage());
        }
        this.dataSourceInfoDao.deleteDataSourceByCode(codes);
    }

    @Override
    public void testDataSource(DataSourceInfoVO dataSourceInfoParam) {
        Connection conn;
        block5: {
            DataSourceInfoVO dataSourceInfo = this.initDriverAndUrl(dataSourceInfoParam);
            conn = null;
            conn = this.getConnection(this.initDriverAndUrl(dataSourceInfo));
            DatabaseMetaData metaData = conn.getMetaData();
            if (metaData.supportsTransactions()) break block5;
            DCQueryJdbcUtils.closeConnection(conn);
            return;
        }
        try {
            conn.setAutoCommit(false);
        }
        catch (Exception e) {
            try {
                throw new DefinedQueryRuntimeException("\u6570\u636e\u5e93\u8fde\u63a5\u5931\u8d25", (Throwable)e);
            }
            catch (Throwable throwable) {
                DCQueryJdbcUtils.closeConnection(conn);
                throw throwable;
            }
        }
        DCQueryJdbcUtils.closeConnection(conn);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String enableTempTable(String datasourceCode) {
        String string;
        Statement stmt;
        Connection conn;
        DataSourceInfoVO dataSourceInfo;
        block5: {
            dataSourceInfo = this.dataSourceInfoDao.getDataSourceInfoByCode(datasourceCode);
            conn = null;
            stmt = null;
            conn = this.getConnection(dataSourceInfo);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "DC_QUERY_TEMPTABLE", null);
            if (!tables.next()) break block5;
            dataSourceInfo.setEnableTempTable(Boolean.valueOf(true));
            dataSourceInfo.setInParamValueMaxCount(Integer.valueOf(10));
            this.dataSourceInfoDao.updateById(dataSourceInfo);
            String string2 = "\u4e34\u65f6\u8868\u5df2\u7ecf\u5b58\u5728\u4e0d\u9700\u8981\u91cd\u590d\u521b\u5efa\uff0c\u8bf7\u5728\u6570\u636e\u6e90\u4fe1\u606f\u4fee\u6539\u754c\u9762\u4fee\u6539in\u53c2\u6570\u503c\u7684\u6700\u5927\u9608\u503c\uff0c\u8d85\u8fc7\u9608\u503c\u65f6\u4f7f\u7528\u4e34\u65f6\u8868\u4f18\u5316sql";
            DCQueryJdbcUtils.colseResource(conn, stmt, null);
            return string2;
        }
        try {
            QueryDataBaseHandler handleService = this.dataBaseHandleGather.getHandleServiceByTypeName(dataSourceInfo.getDataBaseType());
            stmt = conn.createStatement();
            stmt.execute(handleService.getCreateTempTableSql());
            dataSourceInfo.setEnableTempTable(Boolean.valueOf(true));
            dataSourceInfo.setInParamValueMaxCount(Integer.valueOf(10));
            this.dataSourceInfoDao.updateById(dataSourceInfo);
            string = "\u521b\u5efa\u4e34\u65f6\u8868\u6210\u529f\uff0c\u8bf7\u5728\u6570\u636e\u6e90\u4fe1\u606f\u4fee\u6539\u754c\u9762\u4fee\u6539in\u53c2\u6570\u503c\u7684\u6700\u5927\u9608\u503c\uff0c\u8d85\u8fc7\u9608\u503c\u65f6\u4f7f\u7528\u4e34\u65f6\u8868\u4f18\u5316sql";
        }
        catch (Exception e) {
            try {
                throw new DefinedQueryRuntimeException("\u521b\u5efa\u4e34\u65f6\u8868\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            catch (Throwable throwable) {
                DCQueryJdbcUtils.colseResource(conn, stmt, null);
                throw throwable;
            }
        }
        DCQueryJdbcUtils.colseResource(conn, stmt, null);
        return string;
    }

    private Connection getConnection(DataSourceInfoVO dataSourceInfo) throws ClassNotFoundException, SQLException {
        String jdbcUrl = dataSourceInfo.getUrl();
        Class.forName(dataSourceInfo.getDriver());
        String passWord = DCQueryDES.decrypt(dataSourceInfo.getPassWord());
        return DriverManager.getConnection(jdbcUrl, dataSourceInfo.getUserName(), passWord);
    }
}

