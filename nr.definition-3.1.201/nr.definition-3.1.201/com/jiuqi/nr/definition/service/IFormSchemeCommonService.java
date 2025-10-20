/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.definition.service;

import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface IFormSchemeCommonService {
    public boolean enableAdjustPeriod(String var1);

    public boolean isTaskEnableAdjustPeriod(String var1);

    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String var1);

    @NonNull
    public <E extends AdjustPeriod> List<E> queryAdjustPeriods(String var1, String var2);

    @Nullable
    public <E extends AdjustPeriod> E queryAdjustPeriods(String var1, String var2, String var3);

    @NonNull
    public List<String> getReportDimensionKey(String var1);

    @NonNull
    public List<String> getReportEntityKeys(String var1);

    @NonNull
    public Boolean isReportDimension(String var1, String var2);

    @Nullable
    public String getDimAttributeByReportDim(String var1, String var2);

    public Boolean existCurrencyAttributes(String var1);

    @NonNull
    public Boolean existCurrencyDim(String var1);

    default public <T, E> List<T> castList(List<E> list) {
        List<E> result = list;
        return result;
    }
}

