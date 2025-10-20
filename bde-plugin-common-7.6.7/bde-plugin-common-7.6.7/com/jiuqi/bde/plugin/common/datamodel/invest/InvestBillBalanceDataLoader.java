/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader
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
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.bde.plugin.common.datamodel.invest;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.AbstractFinBizDataModelLoader;
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
import com.jiuqi.bde.plugin.common.assbalance.fetch.AssBalanceCacheService;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;
import com.jiuqi.bde.plugin.common.service.FetchResultConvert;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class InvestBillBalanceDataLoader
extends AbstractFinBizDataModelLoader<BalanceCondition, FetchData> {
    @Autowired
    private AssBalanceCacheService cacheService;
    @Autowired
    protected INvwaSystemOptionService optionService;
    @Autowired
    private FetchResultConvert fetchResultConvert;
    @Autowired
    private OuterDataSourceService outerDataSourceService;

    @Transactional(rollbackFor={Exception.class})
    public FetchData loadData(BalanceCondition condi) {
        if (Long.parseLong(this.optionService.findValueById("BDE_TABLEDATA_EXPIRATION")) == 0L || condi.isEnableDirectFilter()) {
            FetchData cache = ((InvestBillBalanceDataLoader)((Object)ApplicationContextRegister.getBean((String)((Object)((Object)this)).getClass().getName(), ((Object)((Object)this)).getClass()))).queryData(condi);
            this.fetchResultConvert.covertFetchData(cache, condi);
            return cache;
        }
        this.refreshCatchData(condi);
        return this.cacheService.getCache(new FetchDataCacheKey(condi), this.buildQueryCondi(condi));
    }

    private void refreshCatchData(BalanceCondition condi) {
        FetchDataCacheKey cacheKey = new FetchDataCacheKey(condi);
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = ((InvestBillBalanceDataLoader)((Object)ApplicationContextRegister.getBean((String)((Object)((Object)this)).getClass().getName(), ((Object)((Object)this)).getClass()))).queryData(condi);
            this.fetchResultConvert.covertFetchData(cache, condi);
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
        for (Dimension assType : condi.getAssTypeList()) {
            selectFields.add(assType.getDimCode());
            groupFields.add(assType.getDimCode());
        }
        return new FetchDataCacheQueryCondi(selectFields, groupFields);
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public abstract FetchData queryData(BalanceCondition var1);

    @Transactional(rollbackFor={Exception.class})
    public FetchFloatRowResult simpleFloatQuery(BalanceCondition balanceCondition, SimpleComposeDateDTO simpleComposeDateDTO) {
        this.refreshCatchData(balanceCondition);
        FetchDataAssQueryCondi fetchDataAssQueryCondi = this.buildSimpleFloatQueryCondi(simpleComposeDateDTO);
        FetchFieldAndWhereSql fetchFiledAndWhereSql = this.buildCacheFetchFieldAndWhereSql(balanceCondition, fetchDataAssQueryCondi);
        fetchFiledAndWhereSql.setCleanZeroRecords(simpleComposeDateDTO.getSimpleCustomComposePluginDataVO().isCleanZeroRecords());
        FetchFloatRowResult fetchFloatRowResult = this.cacheService.getCacheByAss(new FetchDataCacheKey(balanceCondition), fetchFiledAndWhereSql);
        fetchFloatRowResult.setFloatColumnsType(fetchDataAssQueryCondi.getFloatColumnsType());
        return fetchFloatRowResult;
    }

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

