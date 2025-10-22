/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.service.EndCarryForwardDataSourceAdapter;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EndCarryForwardDataSourceServiceImpl {
    @Autowired
    List<EndCarryForwardDataSourceAdapter> adapters;

    public EndCarryForwardDataSourceAdapter getAdapter(QueryParamsVO queryParamsVO) {
        List filterAdapter = this.adapters.stream().filter(adapter -> adapter.match(queryParamsVO)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filterAdapter)) {
            throw new BusinessRuntimeException("\u672a\u5339\u914d\u5230\u9002\u914d\u5668");
        }
        if (filterAdapter.size() > 1) {
            throw new BusinessRuntimeException("\u5339\u914d\u5230\u591a\u4e2a\u9002\u914d\u5668");
        }
        return (EndCarryForwardDataSourceAdapter)filterAdapter.get(0);
    }

    public Boolean saveEndCarryForward(QueryParamsVO queryParamsVO, LossGainOffsetVO lossGainOffsetVO) {
        return this.getAdapter(queryParamsVO).saveEndCarryForward(queryParamsVO, lossGainOffsetVO);
    }

    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsVO queryParamsVO, boolean isQueryAllColumns) {
        return this.getAdapter(queryParamsVO).listWithOnlyItems(this.convertQueryVO2DTO(queryParamsVO, isQueryAllColumns));
    }

    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsVO queryParamsVO, boolean isQueryAllColumns) {
        return this.getAdapter(queryParamsVO).listEOWithFullGroup(this.convertQueryVO2DTO(queryParamsVO, isQueryAllColumns));
    }

    public Set<String> deleteOffsetEntrys(QueryParamsVO queryParamsVO) {
        return this.getAdapter(queryParamsVO).deleteOffsetEntrys(queryParamsVO);
    }

    private QueryParamsDTO convertQueryVO2DTO(QueryParamsVO queryParamsVO, boolean isQueryAllColumns) {
        QueryParamsDTO queryDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(queryParamsVO, queryDTO);
        if (isQueryAllColumns) {
            queryDTO.setQueryAllColumns(true);
        }
        return queryDTO;
    }
}

