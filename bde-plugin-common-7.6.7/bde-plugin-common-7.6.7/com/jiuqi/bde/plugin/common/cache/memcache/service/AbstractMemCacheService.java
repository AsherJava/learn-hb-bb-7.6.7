/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql
 *  com.jiuqi.bde.bizmodel.execute.model.assbalance.FetchFloatRowResultExtractor
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.datasource.DataSourceUtils
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.cache.memcache.service;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.FetchFloatRowResultExtractor;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheService;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.domain.MemoryTableDataDO;
import com.jiuqi.bde.plugin.common.cache.memcache.memtable.service.MemoryTableDataService;
import com.jiuqi.bde.plugin.common.utils.AssistDimensionFetchUtil;
import com.jiuqi.bde.plugin.common.utils.entity.FetchDataJoinContext;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractMemCacheService
implements FetchDataCacheService {
    @Autowired
    private MemoryTableDataService service;
    @Autowired
    protected DataSourceService dataSourceService;
    @Autowired
    private AssistDimensionFetchUtil assistDimensionFetchUtil;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMemCacheService.class);
    private static final String INSERT_SQL = "INSERT INTO %1$s (%2$s) VALUES (%3$s)";
    private static final String SELECT_SQL = "SELECT %1$s FROM %2$s A WHERE 1=1 AND A.BIZCOMBID = ? GROUP BY %3$s ";
    private static final String CLEAR_SQL = "DELETE FROM %1$s WHERE 1 = 1 AND BIZCOMBID = ? ";

    public JdbcTemplate getJdbcTemplate() {
        return OuterDataSourceUtils.getJdbcTemplate();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean cacheValid(FetchDataCacheKey cacheKey) {
        MemoryTableDataDO memoryTableData = new MemoryTableDataDO();
        memoryTableData.setBizCombId(cacheKey.getBizCombId());
        memoryTableData.setTableName(this.getCacheTableName());
        this.service.insert(memoryTableData);
        boolean acquired = this.service.acquireLock(memoryTableData);
        return !acquired;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void putCache(FetchDataCacheKey cacheKey, FetchData cache) {
        ArrayList<String> fields = new ArrayList<String>(cache.getColumns().size() + 2);
        ArrayList<String> values = new ArrayList<String>(cache.getColumns().size() + 2);
        fields.add("ID");
        values.add("?");
        fields.add("BIZCOMBID");
        values.add("?");
        for (Map.Entry columnEntry : cache.getColumns().entrySet()) {
            fields.add((String)columnEntry.getKey());
            values.add("?");
        }
        int count = cache.getRowDatas().size();
        int threshold = 1000;
        int group = count % 1000 == 0 ? count / 1000 : count / 1000 + 1;
        String SQL = String.format(INSERT_SQL, this.getCacheTableName(), CollectionUtil.join(fields, (String)","), CollectionUtil.join(values, (String)","));
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            conn = this.getJdbcTemplate().getDataSource().getConnection();
            pstmt = conn.prepareStatement(SQL);
            for (int j = 0; j < group; ++j) {
                for (int i = j * 1000; i < count && i < 1000 * (j + 1); ++i) {
                    pstmt.setString(1, UUIDUtils.newHalfGUIDStr());
                    pstmt.setString(2, cacheKey.getBizCombId());
                    Object val = null;
                    for (int index = 3; index < fields.size() + 1; ++index) {
                        String field = (String)fields.get(index - 1);
                        int columnIdxInRowData = (Integer)cache.getColumns().get(field);
                        val = ((Object[])cache.getRowDatas().get(i))[columnIdxInRowData];
                        if (val == null) {
                            pstmt.setObject(index, null);
                            continue;
                        }
                        if (val instanceof BigDecimal) {
                            pstmt.setBigDecimal(index, (BigDecimal)val);
                            continue;
                        }
                        if (val instanceof Integer) {
                            pstmt.setInt(index, (Integer)val);
                            continue;
                        }
                        if (val instanceof String) {
                            pstmt.setString(index, (String)val);
                            continue;
                        }
                        pstmt.setString(index, val.toString());
                    }
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
            }
        }
        catch (SQLException e) {
            try {
                LOGGER.error("\u70ed\u70b9\u8868\u5199\u5165\u6570\u636e\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
                throw new RuntimeException(e);
            }
            catch (Throwable throwable) {
                JdbcUtils.closeStatement(pstmt);
                DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.getJdbcTemplate().getDataSource());
                throw throwable;
            }
        }
        JdbcUtils.closeStatement((Statement)pstmt);
        DataSourceUtils.releaseConnection((Connection)conn, (DataSource)this.getJdbcTemplate().getDataSource());
        MemoryTableDataDO memoryTableData = new MemoryTableDataDO();
        memoryTableData.setBizCombId(cacheKey.getBizCombId());
        memoryTableData.setTableName(this.getCacheTableName());
        this.service.updateCacheTime(memoryTableData);
    }

    @Override
    public FetchData getCache(FetchDataCacheKey cacheKey, FetchDataCacheQueryCondi queryCondi) {
        String cacheTableName;
        String subSql = cacheTableName = this.getCacheTableName();
        if (!StringUtils.isEmpty((String)this.getBizDataModelEnumCode())) {
            this.assistDimensionFetchUtil.setBizDataModelEnumCode(this.getBizDataModelEnumCode());
            FetchDataJoinContext.QueriedDimensionHolder holder = this.assistDimensionFetchUtil.separateAssistDimAndAssistExtendDim(queryCondi.getSelectFields());
            if (!CollectionUtils.isEmpty(holder.getQueriedAssistExtendDimensions())) {
                subSql = this.assistDimensionFetchUtil.getJoinSql(CollectionUtils.newHashSet(queryCondi.getSelectFields()), queryCondi.getOrgCode(), queryCondi.getOrgType(), queryCondi.getOrgVer(), cacheTableName);
            }
        }
        Assert.isNotEmpty(queryCondi.getSelectFields());
        Assert.isNotEmpty(queryCondi.getGroupFields());
        HashSet<String> groupSet = new HashSet<String>(queryCondi.getGroupFields());
        final ArrayList<String> selectFields = new ArrayList<String>(queryCondi.getSelectFields().size());
        ArrayList<String> groupFields = new ArrayList<String>(queryCondi.getGroupFields().size());
        String FIELD_TEMPLATE = " A.%s";
        for (String selectField : queryCondi.getSelectFields()) {
            if (groupSet.contains(selectField)) {
                selectFields.add(String.format(" A.%s", selectField));
                continue;
            }
            selectFields.add(String.format(" SUM(A.%s)", selectField));
        }
        for (String groupField : queryCondi.getGroupFields()) {
            groupFields.add(String.format(" A.%s", groupField));
        }
        String querySql = String.format(SELECT_SQL, CollectionUtil.join(selectFields, (String)","), subSql, CollectionUtil.join(groupFields, (String)","));
        BdeLogUtil.recordLog((String)cacheKey.getRequestTaskId(), (String)"\u70ed\u70b9\u6570\u636e\u67e5\u8be2-\u53d6\u6570\u6a21\u578b", null, (String)querySql);
        List rowDatas = (List)this.getJdbcTemplate().query(querySql, (ResultSetExtractor)new ResultSetExtractor<List<Object[]>>(){

            public List<Object[]> extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList<Object[]> rowDatas = new ArrayList<Object[]>(rs.getRow());
                Object[] rowData = null;
                while (rs.next()) {
                    rowData = new Object[selectFields.size()];
                    for (int i = 0; i < selectFields.size(); ++i) {
                        rowData[i] = rs.getObject(i + 1);
                    }
                    rowDatas.add(rowData);
                }
                return rowDatas;
            }
        }, new Object[]{cacheKey.getBizCombId()});
        int idx = 0;
        HashMap<String, Integer> columns = new HashMap<String, Integer>(queryCondi.getSelectFields().size());
        for (String field : queryCondi.getSelectFields()) {
            columns.put(field, idx++);
        }
        return new FetchData(columns, rowDatas);
    }

    @Override
    public FetchFloatRowResult getCacheByAss(FetchDataCacheKey cacheKey, FetchFieldAndWhereSql fetchFiledAndWhereSql) {
        String cacheTableName;
        String subSql = cacheTableName = this.getCacheTableName();
        List<String> queriedDimensions = fetchFiledAndWhereSql.getFetchDataAssQueryCondi().getWhereFields().stream().map(Dimension::getDimCode).collect(Collectors.toList());
        queriedDimensions.addAll(fetchFiledAndWhereSql.getFetchDataAssQueryCondi().getGroupFields());
        if (!StringUtils.isEmpty((String)this.getBizDataModelEnumCode())) {
            this.assistDimensionFetchUtil.setBizDataModelEnumCode(this.getBizDataModelEnumCode());
            FetchDataJoinContext.QueriedDimensionHolder holder = this.assistDimensionFetchUtil.separateAssistDimAndAssistExtendDim(queriedDimensions);
            if (!CollectionUtils.isEmpty(holder.getQueriedAssistExtendDimensions())) {
                subSql = this.assistDimensionFetchUtil.getJoinSql(CollectionUtils.newHashSet(queriedDimensions), fetchFiledAndWhereSql.getOrgCode(), fetchFiledAndWhereSql.getOrgType(), fetchFiledAndWhereSql.getOrgVer(), cacheTableName);
            }
        }
        String sqlTemplate = !fetchFiledAndWhereSql.getGroupFieldSql().isEmpty() ? "SELECT %1$s FROM (SELECT %2$s FROM %3$s FLOATRESULT WHERE 1=1 AND FLOATRESULT.BIZCOMBID = ? %4$s GROUP BY %5$s ) T WHERE 1=1 " : "SELECT %1$s FROM (SELECT %2$s FROM %3$s FLOATRESULT WHERE 1=1 AND FLOATRESULT.BIZCOMBID = ? %4$s %5$s ) T WHERE 1=1 ";
        ArrayList<String> selectFields = new ArrayList<String>();
        block7: for (String fetchType : fetchFiledAndWhereSql.getFetchTypes()) {
            FetchTypeEnum fetchTypeEnum = FetchTypeEnum.getEnumByCode((String)fetchType);
            switch (fetchTypeEnum) {
                case NC: 
                case C: 
                case YE: 
                case WNC: 
                case WC: 
                case WYE: {
                    selectFields.add(String.format(" SUM(CASE WHEN FLOATRESULT.%1$s IS NULL THEN 0  ELSE FLOATRESULT.%1$s * FLOATRESULT.ORIENT END)  %1$s", fetchType));
                    continue block7;
                }
                case BQNUM: {
                    selectFields.add(String.format(" SUM((FLOATRESULT.JF - FLOATRESULT.DF) * FLOATRESULT.ORIENT)  %1$s", fetchType));
                    continue block7;
                }
                case LJNUM: {
                    selectFields.add(String.format(" SUM((FLOATRESULT.JL - FLOATRESULT.DL) * FLOATRESULT.ORIENT)  %1$s", fetchType));
                    continue block7;
                }
                case WBQNUM: {
                    selectFields.add(String.format(" SUM((FLOATRESULT.WJF - FLOATRESULT.WDF) * FLOATRESULT.ORIENT)  %1$s", fetchType));
                    continue block7;
                }
                case WLJNUM: {
                    selectFields.add(String.format(" SUM((FLOATRESULT.WJL - FLOATRESULT.WDL) * FLOATRESULT.ORIENT)  %1$s", fetchType));
                    continue block7;
                }
            }
            selectFields.add(String.format(" SUM(CASE WHEN FLOATRESULT.%1$s IS NULL THEN 0  ELSE FLOATRESULT.%1$s END) %1$s", fetchType));
        }
        selectFields.addAll(fetchFiledAndWhereSql.getGroupFieldSql());
        String querySql = String.format(sqlTemplate, CollectionUtil.join((List)fetchFiledAndWhereSql.getSelectFieldSql(), (String)","), CollectionUtil.join(selectFields, (String)","), subSql, CollectionUtil.join((List)fetchFiledAndWhereSql.getWhereFieldSql(), (String)" "), CollectionUtil.join((List)fetchFiledAndWhereSql.getGroupFieldSql(), (String)","));
        if (fetchFiledAndWhereSql.isCleanZeroRecords()) {
            StringBuilder sql = new StringBuilder(querySql);
            sql.append(" AND ( 1<>1");
            for (String fetchType : fetchFiledAndWhereSql.getFetchTypes()) {
                sql.append(String.format(" OR %1$s <>0", fetchType));
            }
            sql.append(")");
            querySql = sql.toString();
        }
        BdeLogUtil.recordLog((String)cacheKey.getRequestTaskId(), (String)"\u70ed\u70b9\u6570\u636e\u67e5\u8be2-\u6d6e\u52a8\u884c\u89e3\u6790-\u4e1a\u52a1\u6a21\u578b", (Object)cacheKey.getBizCombId(), (String)querySql);
        return (FetchFloatRowResult)OuterDataSourceUtils.getJdbcTemplate().query(querySql, (ResultSetExtractor)new FetchFloatRowResultExtractor(), new Object[]{cacheKey.getBizCombId()});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void removeCache(FetchDataCacheKey cacheKey) {
        Assert.isNotEmpty((String)this.getCacheTableName());
        Assert.isNotEmpty((String)cacheKey.getBizCombId());
        this.getJdbcTemplate().update(String.format(CLEAR_SQL, this.getCacheTableName()), new Object[]{cacheKey.getBizCombId()});
    }
}

