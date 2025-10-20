/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql
 *  com.jiuqi.bde.bizmodel.execute.model.assbalance.FetchFloatRowResultExtractor
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.utils.CollectionUtil
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.plugin.common.cache.memcache.service.impl;

import com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.FetchFloatRowResultExtractor;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.constant.MemoryBalanceTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.memcache.service.AbstractMemCacheService;
import com.jiuqi.bde.plugin.common.utils.AssistDimensionFetchUtil;
import com.jiuqi.bde.plugin.common.utils.entity.FetchDataJoinContext;
import com.jiuqi.bde.plugin.common.xjll.fetch.XjllBalanceCacheService;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.utils.CollectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class XjllBalanceMemTableCacheService
extends AbstractMemCacheService
implements XjllBalanceCacheService {
    @Autowired
    private AssistDimensionFetchUtil assistDimensionFetchUtil;

    @Override
    public String getCacheTableName() {
        return MemoryBalanceTypeEnum.XJLLBALANCE.getCode();
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
        block3: for (String fetchType : fetchFiledAndWhereSql.getFetchTypes()) {
            FetchTypeEnum fetchTypeEnum = FetchTypeEnum.getEnumByCode((String)fetchType);
            switch (fetchTypeEnum) {
                case NC: 
                case C: 
                case YE: 
                case WNC: 
                case WC: 
                case WYE: {
                    selectFields.add(String.format(" SUM(CASE WHEN FLOATRESULT.%1$s IS NULL THEN 0  ELSE FLOATRESULT.%1$s * FLOATRESULT.ORIENT END)  %1$s", fetchType));
                    continue block3;
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
    public String getBizDataModelEnumCode() {
        return BizDataModelEnum.XJLLMODEL.getCode();
    }
}

