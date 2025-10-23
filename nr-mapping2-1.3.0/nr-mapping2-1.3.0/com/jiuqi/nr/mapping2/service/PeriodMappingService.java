/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.bean.PeriodMapping;
import java.util.List;

public interface PeriodMappingService {
    public List<PeriodMapping> findByMS(String var1);

    public void clearByMS(String var1);

    public void deleteByMS(String var1);

    public void saveByMS(String var1, List<PeriodMapping> var2);

    public void batchAdd(List<PeriodMapping> var1);
}

