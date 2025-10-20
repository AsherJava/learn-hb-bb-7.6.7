/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.caculate.service;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.List;

public interface GcCaculateOffsetItemService {
    public List<GcOffSetVchrItemDTO> getPreCalcOffSetItems(QueryParamsVO var1);
}

