/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.datamodel.balance.AbstractBalanceDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.model.assbalance.SeniorFetchFloatRowResultExtractor
 *  com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil
 *  com.jiuqi.bde.bizmodel.impl.adaptor.service.OuterDataSourceService
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.FetchFloatRowResult
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.dc.base.common.annotation.CustDSTransaction
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.datamodel.balance;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.balance.AbstractBalanceDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.assbalance.SeniorFetchFloatRowResultExtractor;
import com.jiuqi.bde.bizmodel.execute.util.ModelExecuteUtil;
import com.jiuqi.bde.bizmodel.impl.adaptor.service.OuterDataSourceService;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.FetchFloatRowResult;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.bde.plugin.common.balance.fetch.BalanceCacheService;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;
import com.jiuqi.bde.plugin.common.service.AdjustVchrDataService;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.dc.base.common.annotation.CustDSTransaction;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseBalanceDataLoader
extends AbstractBalanceDataLoader {
    @Autowired
    private BalanceCacheService cacheService;
    @Autowired
    protected INvwaSystemOptionService optionService;
    @Autowired
    private FetchResultConvert fetchResultConvert;
    @Autowired
    private AdjustVchrDataService adjustVchrDataService;
    @Autowired
    private OuterDataSourceService outerDataSourceService;

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public FetchData loadData(BalanceCondition condi) {
        if (Long.parseLong(this.optionService.findValueById("BDE_TABLEDATA_EXPIRATION")) == 0L || condi.isEnableDirectFilter()) {
            FetchData fetchData = ((BaseBalanceDataLoader)((Object)ApplicationContextRegister.getBean((String)((Object)((Object)this)).getClass().getName(), ((Object)((Object)this)).getClass()))).queryFetchData(condi, condi.getOrgMapping().getDataSourceCode());
            if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
                FetchData adjustVchrData = this.adjustVchrDataService.getAdjustVchrFetchData(condi);
                List<Object[]> reorderList = this.adjustVchrDataService.reorderAdjustVchrList(fetchData.getColumns(), adjustVchrData);
                fetchData.getRowDatas().addAll(reorderList);
            }
            this.fetchResultConvert.covertFetchData(fetchData, condi);
            return fetchData;
        }
        this.refreshCatchData(condi);
        FetchData cacheData = this.cacheService.getCache(new FetchDataCacheKey(condi), this.buildQueryCondi(condi));
        return cacheData;
    }

    private void refreshCatchData(BalanceCondition condi) {
        FetchDataCacheKey cacheKey = new FetchDataCacheKey(condi);
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = ((BaseBalanceDataLoader)((Object)ApplicationContextRegister.getBean((String)((Object)((Object)this)).getClass().getName(), ((Object)((Object)this)).getClass()))).queryFetchData(condi, condi.getOrgMapping().getDataSourceCode());
            if (Boolean.TRUE.equals(condi.getIncludeAdjustVchr())) {
                FetchData adjustVchrData = this.adjustVchrDataService.getAdjustVchrFetchData(condi);
                List<Object[]> reorderList = this.adjustVchrDataService.reorderAdjustVchrList(cache.getColumns(), adjustVchrData);
                cache.getRowDatas().addAll(reorderList);
            }
            this.fetchResultConvert.covertFetchData(cache, condi);
            this.cacheService.putCache(cacheKey, cache);
        }
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRED)
    public void prepareCacheData(BalanceCondition condi, FetchDataCacheKey cacheKey) {
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = this.queryData(condi);
            this.cacheService.putCache(cacheKey, cache);
        }
    }

    private FetchDataCacheQueryCondi buildQueryCondi(BalanceCondition condi) {
        ArrayList<String> selectFields = new ArrayList<String>();
        selectFields.add("SUBJECTCODE");
        selectFields.add("CURRENCYCODE");
        selectFields.add("ORIENT");
        selectFields.add(FetchTypeEnum.NC.getCode());
        selectFields.add(FetchTypeEnum.C.getCode());
        selectFields.add(FetchTypeEnum.JF.getCode());
        selectFields.add(FetchTypeEnum.DF.getCode());
        selectFields.add(FetchTypeEnum.JL.getCode());
        selectFields.add(FetchTypeEnum.DL.getCode());
        selectFields.add(FetchTypeEnum.YE.getCode());
        selectFields.add(FetchTypeEnum.WNC.getCode());
        selectFields.add(FetchTypeEnum.WC.getCode());
        selectFields.add(FetchTypeEnum.WJF.getCode());
        selectFields.add(FetchTypeEnum.WDF.getCode());
        selectFields.add(FetchTypeEnum.WJL.getCode());
        selectFields.add(FetchTypeEnum.WDL.getCode());
        selectFields.add(FetchTypeEnum.WYE.getCode());
        ArrayList<String> groupFields = new ArrayList<String>();
        groupFields.add("SUBJECTCODE");
        groupFields.add("ORIENT");
        groupFields.add("CURRENCYCODE");
        return new FetchDataCacheQueryCondi(selectFields, groupFields);
    }

    @CustDSTransaction
    public FetchData queryFetchData(BalanceCondition condi, String dataSourceCode) {
        return this.queryData(condi);
    }

    public abstract FetchData queryData(BalanceCondition var1);

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult seniorFloatQuery(BalanceCondition condi, String seniorSql) {
        this.refreshCatchData(condi);
        String replaceSql = String.format("(SELECT * FROM %s WHERE 1=1 AND BIZCOMBID = ?) \n", this.cacheService.getCacheTableName());
        int tableNameCount = ModelExecuteUtil.getTableNameCount((String)seniorSql);
        Object[] params = new Object[tableNameCount];
        Arrays.fill(params, condi.getBizCombId());
        seniorSql = seniorSql.replace("#TABLENAME#", replaceSql);
        BdeLogUtil.recordLog((String)condi.getRequestTaskId(), (String)"\u70ed\u70b9\u6570\u636e\u67e5\u8be2-\u6d6e\u52a8\u884c\u89e3\u6790-\u9ad8\u7ea7\u4e1a\u52a1\u6a21\u578b", (Object)condi.getBizCombId(), (String)seniorSql);
        return (FetchFloatRowResult)this.outerDataSourceService.query(seniorSql, params, (ResultSetExtractor)new SeniorFetchFloatRowResultExtractor());
    }
}

