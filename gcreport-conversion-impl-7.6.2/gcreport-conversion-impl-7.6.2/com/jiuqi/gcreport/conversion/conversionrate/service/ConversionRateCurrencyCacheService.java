/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 */
package com.jiuqi.gcreport.conversion.conversionrate.service;

import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;

public interface ConversionRateCurrencyCacheService {
    public GcBaseData getCurrencyBaseDataByCode(String var1);

    public GcBaseData getCurrencyBaseDataByTitle(String var1);

    public String getCurrencyTitle(String var1);

    public String getCurrencyCode(String var1);
}

