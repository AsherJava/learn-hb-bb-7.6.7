/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.service;

import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import java.util.List;

public interface DimensionCustomPublishService {
    public List<String> getTableNames();

    public void publish(String var1, DimensionDTO var2);

    public DimensionPublishInfoVO publishCheck(DimTableRelDTO var1, DimensionDTO var2);

    public DimensionPublishInfoVO publishCheckUnPublished(DimTableRelDTO var1, DimensionDTO var2);
}

