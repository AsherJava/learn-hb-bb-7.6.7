/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.PaginationDto
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.dto.PaginationDto;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;
import java.util.Set;

public interface EndCarryForwardDataSourceAdapter {
    public String getName();

    public boolean match(QueryParamsVO var1);

    public List<GcOffSetVchrItemAdjustEO> listWithOnlyItems(QueryParamsDTO var1);

    public PaginationDto<GcOffSetVchrItemAdjustEO> listEOWithFullGroup(QueryParamsDTO var1);

    public Set<String> deleteOffsetEntrys(QueryParamsVO var1);

    public Boolean saveEndCarryForward(QueryParamsVO var1, LossGainOffsetVO var2);
}

