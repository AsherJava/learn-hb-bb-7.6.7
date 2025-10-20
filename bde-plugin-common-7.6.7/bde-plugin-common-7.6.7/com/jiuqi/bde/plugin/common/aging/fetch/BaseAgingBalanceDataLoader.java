/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 *  com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition
 *  com.jiuqi.bde.common.constant.AgingFetchTypeEnum
 *  com.jiuqi.bde.common.constant.FetchTypeEnum
 *  com.jiuqi.bde.common.intf.Dimension
 */
package com.jiuqi.bde.plugin.common.aging.fetch;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.datamodel.aging.AbstractAgingDataLoader;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import com.jiuqi.bde.bizmodel.execute.model.aging.AgingBalanceCondition;
import com.jiuqi.bde.common.constant.AgingFetchTypeEnum;
import com.jiuqi.bde.common.constant.FetchTypeEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.plugin.common.aging.fetch.AgingBalanceCacheService;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheKey;
import com.jiuqi.bde.plugin.common.cache.FetchDataCacheQueryCondi;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAgingBalanceDataLoader
extends AbstractAgingDataLoader {
    @Autowired
    private AgingBalanceCacheService cacheService;

    public FetchData loadData(AgingBalanceCondition condi) {
        FetchDataCacheKey cacheKey = new FetchDataCacheKey((BalanceCondition)condi);
        boolean cacheValid = this.cacheService.cacheValid(cacheKey);
        if (!cacheValid) {
            this.cacheService.removeCache(cacheKey);
            FetchData cache = this.queryData(condi);
            this.cacheService.putCache(cacheKey, cache);
        }
        FetchData cacheData = this.cacheService.getCache(cacheKey, this.buildQueryCondi(condi));
        return cacheData;
    }

    private FetchDataCacheQueryCondi buildQueryCondi(AgingBalanceCondition condi) {
        ArrayList<String> selectFields = new ArrayList<String>();
        selectFields.add("SUBJECTCODE");
        selectFields.add("CURRENCYCODE");
        if (condi.getAgingFetchType() == AgingFetchTypeEnum.NC) {
            selectFields.add(FetchTypeEnum.HXNC.name());
            selectFields.add(FetchTypeEnum.WHXNC.name());
        } else {
            selectFields.add(FetchTypeEnum.HXYE.name());
            selectFields.add(FetchTypeEnum.WHXYE.name());
        }
        ArrayList<String> groupFields = new ArrayList<String>();
        groupFields.add("SUBJECTCODE");
        groupFields.add("CURRENCYCODE");
        for (Dimension assType : condi.getAssTypeList()) {
            selectFields.add(assType.getDimCode());
            groupFields.add(assType.getDimCode());
        }
        return new FetchDataCacheQueryCondi(selectFields, groupFields);
    }

    protected abstract FetchData queryData(AgingBalanceCondition var1);
}

