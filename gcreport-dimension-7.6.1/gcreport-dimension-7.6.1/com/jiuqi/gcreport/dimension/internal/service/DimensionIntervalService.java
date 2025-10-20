/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.dimension.internal.service;

import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.dto.EffectScopeDTO;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import java.util.List;

public interface DimensionIntervalService {
    public List<EffectScopeDTO> getEffectTable();

    public List<DimTableRelDTO> publish(DimensionDTO var1);

    public List<DimensionPublishInfoVO> publishCheck(DimensionDTO var1);
}

