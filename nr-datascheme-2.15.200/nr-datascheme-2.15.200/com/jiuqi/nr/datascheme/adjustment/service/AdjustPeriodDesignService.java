/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.adjustment.service;

import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import java.util.List;

public interface AdjustPeriodDesignService {
    public void add(List<DesignAdjustPeriodDTO> var1);

    public List<DesignAdjustPeriodDTO> query(String var1, String var2);

    public List<DesignAdjustPeriodDTO> query(String var1);

    public void updateByPeriod(String var1, String var2, List<DesignAdjustPeriodDTO> var3);

    public void deleteByDataScheme(String var1);

    public void updateAdjust(List<DesignAdjustPeriodDTO> var1);
}

