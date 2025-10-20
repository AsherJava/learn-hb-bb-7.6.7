/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.dimension.internal.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.dimension.dto.DimTableRelDTO;
import com.jiuqi.gcreport.dimension.dto.DimensionDTO;
import com.jiuqi.gcreport.dimension.dto.EffectScopeDTO;
import com.jiuqi.gcreport.dimension.internal.service.DimensionCustomPublishService;
import com.jiuqi.gcreport.dimension.internal.service.DimensionEffectScopeGather;
import com.jiuqi.gcreport.dimension.internal.service.DimensionIntervalService;
import com.jiuqi.gcreport.dimension.vo.DimensionPublishInfoVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DimensionIntervalServiceImpl
implements DimensionIntervalService {
    private final List<DimensionEffectScopeGather> dimensionEffectScopeGathers;
    private final List<DimensionCustomPublishService> dimensionCustomPublishServices;

    @Autowired(required=false)
    public DimensionIntervalServiceImpl(List<DimensionEffectScopeGather> dimensionEffectScopeGatherServices, List<DimensionCustomPublishService> dimensionCustomPublishServices) {
        this.dimensionEffectScopeGathers = dimensionEffectScopeGatherServices;
        this.dimensionCustomPublishServices = dimensionCustomPublishServices;
    }

    @Override
    public List<EffectScopeDTO> getEffectTable() {
        throw new BusinessRuntimeException("getEffectTable\u7981\u7528");
    }

    @Override
    public List<DimTableRelDTO> publish(DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("publish\u7981\u7528");
    }

    @Override
    public List<DimensionPublishInfoVO> publishCheck(DimensionDTO dimensionDTO) {
        throw new BusinessRuntimeException("publishCheck\u7981\u7528");
    }
}

