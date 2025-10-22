/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.EndCarryForwardDataSourceAdapter;
import com.jiuqi.gcreport.offsetitem.service.GcEndCarryForwardService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OffsetAppAdapter
implements EndCarryForwardDataSourceAdapter {
    @Autowired
    private GcOffSetItemAdjustCoreService adjustingEntryCoreService;
    @Autowired
    private GcOffSetAppOffsetService offSetItemAdjustService;
    @Autowired
    private GcOffSetItemAdjustCoreServiceImpl offsetCoreService;
    @Autowired
    private GcEndCarryForwardService endCarryForwardService;

    @Override
    public String getName() {
        return "offsetApp";
    }

    @Override
    public boolean match(QueryParamsVO queryParamsVO) {
        return !Boolean.TRUE.equals(queryParamsVO.getArbitrarilyMerge());
    }

    @Override
    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO queryParamsDTO) {
        return this.adjustingEntryCoreService.listWithOnlyItems(queryParamsDTO);
    }

    @Override
    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO queryParamsDTO) {
        return this.adjustingEntryCoreService.listEOWithFullGroup(queryParamsDTO);
    }

    @Override
    public Set<String> deleteOffsetEntrys(QueryParamsVO queryParamsVO) {
        return this.offSetItemAdjustService.deleteOffsetEntrys(queryParamsVO);
    }

    @Override
    public Boolean saveEndCarryForward(QueryParamsVO queryParamsVO, LossGainOffsetVO lossGainOffsetVO) {
        return this.endCarryForwardService.saveEndCarryForward(queryParamsVO, lossGainOffsetVO);
    }
}

