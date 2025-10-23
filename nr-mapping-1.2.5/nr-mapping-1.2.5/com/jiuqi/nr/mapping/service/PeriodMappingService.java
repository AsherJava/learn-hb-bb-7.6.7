/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.PeriodMapping;
import java.util.List;

public interface PeriodMappingService {
    public List<PeriodMapping> findByMS(String var1);

    public void clearByMS(String var1);

    public void saveByMS(String var1, List<PeriodMapping> var2);
}

