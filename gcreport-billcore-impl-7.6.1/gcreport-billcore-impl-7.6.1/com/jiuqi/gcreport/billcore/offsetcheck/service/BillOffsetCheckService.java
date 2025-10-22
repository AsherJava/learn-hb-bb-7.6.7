/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.billcore.offsetcheck.service;

import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;

public interface BillOffsetCheckService {
    public Pagination<BillOffsetCheckInfoDTO> dataTraceOffsetCheckList(GcDataTraceCondi var1);

    public List<OffsetTraceResultVO> offsetCheckAmtTrace(GcDataTraceCondi var1);
}

