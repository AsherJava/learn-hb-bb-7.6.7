/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 */
package com.jiuqi.gcreport.conversion.conversionrate.service.impl;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.conversion.conversionrate.service.ConversionRateCurrencyCacheService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ConversionRateCurrencyCacheServiceImpl
implements ConversionRateCurrencyCacheService {
    private Map<String, GcBaseData> currencyCodeMap = new ConcurrentHashMap<String, GcBaseData>();
    private Map<String, GcBaseData> currencyTitleMap = new ConcurrentHashMap<String, GcBaseData>();

    @Override
    public GcBaseData getCurrencyBaseDataByCode(String currencyCode) {
        if (currencyCode == null) {
            return null;
        }
        GcBaseData iBaseData = this.currencyCodeMap.get(currencyCode);
        if (iBaseData == null) {
            this.initMap();
        }
        return this.currencyCodeMap.get(currencyCode);
    }

    @Override
    public GcBaseData getCurrencyBaseDataByTitle(String currencyTitle) {
        if (currencyTitle == null) {
            return null;
        }
        GcBaseData iBaseData = this.currencyTitleMap.get(currencyTitle);
        if (iBaseData == null) {
            this.initMap();
        }
        return this.currencyTitleMap.get(currencyTitle);
    }

    @Override
    public String getCurrencyTitle(String code) {
        GcBaseData currencyBaseData = this.getCurrencyBaseDataByCode(code);
        return currencyBaseData == null ? null : currencyBaseData.getTitle();
    }

    @Override
    public String getCurrencyCode(String title) {
        GcBaseData currencyBaseData = this.getCurrencyBaseDataByTitle(title);
        return currencyBaseData == null ? null : currencyBaseData.getCode();
    }

    public void initMap() {
        List baseDataList = GcBaseDataCenterTool.getInstance().queryBasedataItems("MD_CURRENCY");
        if (baseDataList != null) {
            for (GcBaseData baseData : baseDataList) {
                this.currencyCodeMap.put(baseData.getCode(), baseData);
                this.currencyTitleMap.put(baseData.getTitle(), baseData);
            }
        }
    }
}

