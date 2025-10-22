/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionrate.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ConversionRateService {
    public Map<String, BigDecimal> getRateInfos(String var1, String var2, String var3, String var4, String var5);

    public Map<String, BigDecimal> getRateInfosByRateTypeCode(String var1, String var2, String var3, String var4, String var5, String var6);

    public BigDecimal getSumAvgRateValueByRateTypeCode(String var1, String var2, String var3, String var4, String var5, String var6);
}

