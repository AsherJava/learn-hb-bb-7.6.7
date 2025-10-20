/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.datamodel.xjll.AbstractXjllDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchDataAssQueryCondi
 *  com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql
 *  com.jiuqi.bde.bizmodel.execute.model.assbalance.SeniorFetchFloatRowResultExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.impl.adaptor.service.OuterDataSourceService
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.dto.SimpleComposeDateDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.base.common.utils.SqlUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.datamodel.xjll;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.xjll.AbstractXjllDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.intf.FetchDataAssQueryCondi;
import com.jiuqi.bde.bizmodel.execute.intf.FetchFieldAndWhereSql;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.SeniorFetchFloatRowResultExtractor;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.adaptor.service.OuterDataSourceService;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.dto.SimpleComposeDateDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;
import com.jiuqi.bde.plugin.common.service.AdjustVchrDataService;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.bde.plugin.common.utils.AssistDimensionFetchUtil;
import com.jiuqi.bde.plugin.common.utils.entity.FetchDataJoinContext;
import com.jiuqi.bde.plugin.common.xjll.fetch.XjllBalanceCacheService;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.base.common.utils.SqlUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseXjllDataLoader
extends AbstractXjllDataLoader {
    @Autowired
    private XjllBalanceCacheService cacheService;
    @Autowired
    private FetchResultConvert fetchResultConvert;
    @Autowired
    private AdjustVchrDataService adjustVchrDataService;
    @Autowired
    protected INvwaSystemOptionService optionService;
    @Autowired
    private AssistDimensionFetchUtil assistDimensionFetchUtil;
    @Autowired
    private OuterDataSourceService outerDataSourceService;

    @Transactional(rollbackFor={Exception.class})
    public FetchData loadData(BalanceCondition condi) {
        FetchDataCacheKey cacheKey = new FetchDataCacheKey(condi);
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = this.queryData(condi);
            if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
                FetchData adjustVchrData = this.adjustVchrDataService.getAdjustVchrXjllFetchData(condi);
                List<Object[]> reorderList = this.adjustVchrDataService.reorderAdjustVchrList(cache.getColumns(), adjustVchrData);
                cache.getRowDatas().addAll(reorderList);
            }
            this.fetchResultConvert.covertFetchData(cache, condi);
            this.cacheService.putCache(cacheKey, cache);
        }
        return this.cacheService.getCache(cacheKey, this.buildQueryCondi(condi));
    }

    private FetchDataCacheQueryCondi buildQueryCondi(BalanceCondition condi) {
        ArrayList<String> selectFields = new ArrayList<String>();
        selectFields.add("CFITEMCODE");
        selectFields.add("SUBJECTCODE");
        selectFields.add("CURRENCYCODE");
        selectFields.add(FetchTypeEnum.BQNUM.getCode());
        selectFields.add(FetchTypeEnum.LJNUM.getCode());
        selectFields.add(FetchTypeEnum.WBQNUM.getCode());
        selectFields.add(FetchTypeEnum.WLJNUM.getCode());
        ArrayList<String> groupFields = new ArrayList<String>();
        groupFields.add("CFITEMCODE");
        groupFields.add("SUBJECTCODE");
        groupFields.add("CURRENCYCODE");
        for (Dimension assType : condi.getAssTypeList()) {
            selectFields.add(assType.getDimCode());
            groupFields.add(assType.getDimCode());
        }
        String orgCode = condi.getOrgMapping() == null ? null : condi.getOrgMapping().getReportOrgCode();
        String orgVer = this.getOrgVersionCode(condi);
        FetchDataCacheQueryCondi fetchDataCacheQueryCondi = new FetchDataCacheQueryCondi(selectFields, groupFields, orgCode);
        fetchDataCacheQueryCondi.setOrgVer(orgVer);
        return fetchDataCacheQueryCondi;
    }

    protected abstract FetchData queryData(BalanceCondition var1);

    private void refreshCatchData(BalanceCondition condi) {
        FetchDataCacheKey cacheKey = new FetchDataCacheKey(condi);
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = ((BaseXjllDataLoader)((Object)ApplicationContextRegister.getBean((String)((Object)((Object)this)).getClass().getName(), ((Object)((Object)this)).getClass()))).queryData(condi);
            if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
                FetchData adjustVchrData = this.adjustVchrDataService.getAdjustVchrXjllFetchData(condi);
                List<Object[]> reorderList = this.adjustVchrDataService.reorderAdjustVchrList(cache.getColumns(), adjustVchrData);
                cache.getRowDatas().addAll(reorderList);
            }
            this.fetchResultConvert.covertFetchData(cache, condi);
            this.cacheService.putCache(cacheKey, cache);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult simpleFloatQuery(BalanceCondition balanceCondition, SimpleComposeDateDTO simpleComposeDateDTO) {
        this.refreshCatchData(balanceCondition);
        FetchDataAssQueryCondi fetchDataAssQueryCondi = this.buildSimpleFloatQueryCondi(simpleComposeDateDTO);
        FetchFieldAndWhereSql fetchFiledAndWhereSql = this.buildCacheFetchFieldAndWhereSql(balanceCondition, fetchDataAssQueryCondi);
        fetchFiledAndWhereSql.setCleanZeroRecords(simpleComposeDateDTO.getSimpleCustomComposePluginDataVO().isCleanZeroRecords());
        if (balanceCondition.getOrgMapping() != null) {
            fetchFiledAndWhereSql.setOrgCode(balanceCondition.getOrgMapping().getReportOrgCode());
        }
        String orgVer = this.getOrgVersionCode(balanceCondition);
        fetchFiledAndWhereSql.setOrgVer(orgVer);
        fetchFiledAndWhereSql.setOrgType(balanceCondition.getRpUnitType());
        FetchFloatRowResult fetchFloatRowResult = this.cacheService.getCacheByAss(new FetchDataCacheKey(balanceCondition), fetchFiledAndWhereSql);
        fetchFloatRowResult.setFloatColumnsType(fetchDataAssQueryCondi.getFloatColumnsType());
        return fetchFloatRowResult;
    }

    protected String buildWhereSql(String whereValue, String dimCode) {
        if (whereValue.contains(",")) {
            String[] dimValues = whereValue.split(",");
            ArrayList<String> likeSqlList = new ArrayList<String>();
            for (String likeSql : dimValues) {
                likeSqlList.add(" FLOATRESULT.%1$s LIKE '" + likeSql + "%%' ");
            }
            return SqlUtil.concatCondi(likeSqlList, (boolean)false);
        }
        if (whereValue.contains(":")) {
            String[] values = whereValue.split(":");
            if ("CFITEMCODE".equals(dimCode)) {
                return " AND FLOATRESULT.%1$s >='" + values[0] + "' AND FLOATRESULT.%1$s <'" + values[1] + "' ";
            }
            return " AND FLOATRESULT.%1$s >= '" + values[0] + "' AND FLOATRESULT.%1$s <= '" + values[1] + "ZZ' ";
        }
        return " AND FLOATRESULT.%1$s LIKE '" + whereValue + "%%' ";
    }

    protected String buildExcludeSql(String excludeValue, String dimCode) {
        if (excludeValue.contains(",")) {
            String[] dimValues = excludeValue.split(",");
            StringBuilder stringBuilder = new StringBuilder();
            for (String value : dimValues) {
                stringBuilder.append(" AND FLOATRESULT.%1$s NOT LIKE '" + value + "%%' ");
            }
            return stringBuilder.toString();
        }
        if (excludeValue.contains(":")) {
            String[] values = excludeValue.split(":");
            if ("CFITEMCODE".equals(dimCode)) {
                return " AND ( FLOATRESULT.%1$s <'" + values[0] + "' OR FLOATRESULT.%1$s >='" + values[1] + "' )";
            }
            return " AND ( FLOATRESULT.%1$s <'" + values[0] + "' OR FLOATRESULT.%1$s >'" + values[1] + "ZZ' )";
        }
        return " AND FLOATRESULT.%1$s NOT LIKE '" + excludeValue + "%%' ";
    }

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult seniorFloatQuery(BalanceCondition condi, String seniorSql) {
        String cacheTableName;
        this.refreshCatchData(condi);
        String acctOrgCode = condi.getOrgMapping().getReportOrgCode();
        String subSql = cacheTableName = this.cacheService.getCacheTableName();
        this.assistDimensionFetchUtil.setBizDataModelEnumCode(this.getBizDataModelCode());
        Set<String> queriedFields = this.assistDimensionFetchUtil.parseQueriedDimCodesFromSql(seniorSql);
        FetchDataJoinContext.QueriedDimensionHolder holder = this.assistDimensionFetchUtil.separateAssistDimAndAssistExtendDim(queriedFields);
        if (!CollectionUtils.isEmpty(holder.getQueriedAssistExtendDimensions())) {
            String orgVer = this.getOrgVersionCode(condi);
            subSql = this.assistDimensionFetchUtil.getJoinSql(queriedFields, acctOrgCode, cacheTableName, condi.getRpUnitType(), orgVer);
        }
        String replaceSql = String.format("(SELECT * FROM %s A WHERE 1=1 AND BIZCOMBID = ?) \n", subSql);
        int tableNameCount = ModelExecuteUtil.getTableNameCount((String)seniorSql);
        Object[] params = new Object[tableNameCount];
        Arrays.fill(params, condi.getBizCombId());
        seniorSql = seniorSql.replace("#TABLENAME#", replaceSql);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u70ed\u70b9\u6570\u636e\u67e5\u8be2-\u6d6e\u52a8\u884c\u89e3\u6790-\u9ad8\u7ea7\u4e1a\u52a1\u6a21\u578b", (Object)condi.getBizCombId(), (String)seniorSql);
        return (FetchFloatRowResult)this.outerDataSourceService.query(seniorSql, params, (ResultSetExtractor)new SeniorFetchFloatRowResultExtractor());
    }
}

