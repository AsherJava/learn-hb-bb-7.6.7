/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.nr.calibre2;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.calibre2.domain.CalibreDataRegion;
import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import java.util.List;

public interface ICalibreDataRegionService {
    public List<CalibreDataRegion> getList(ExecutorContext var1, CalibreDefineDO var2, DimensionValueSet var3) throws Exception;

    public List<CalibreDataRegion> getList(ExecutorContext var1, CalibreDefineDO var2, DimensionValueSet var3, TempAssistantTable var4) throws Exception;
}

