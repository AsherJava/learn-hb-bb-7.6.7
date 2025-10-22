/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.datatrace.service;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;

public interface OffsetAmtTraceService {
    public List<OffsetAmtTraceResultVO> traceOffsetAmt(String var1, GcTaskBaseArguments var2);

    public List<OffsetTraceResultVO> traceOffsetGroupAmt(String var1, GcTaskBaseArguments var2);

    public List<OffsetTraceResultVO> listOffsetTraceResultVOS(GcTaskBaseArguments var1, List<GcOffSetVchrItemAdjustEO> var2);

    public Pagination<Map<String, Object>> queryDataTraceOffsetEntry(GcDataTraceCondi var1);

    public Pagination<DataTraceCheckInfoDTO> dataTraceOffsetCheckList(GcDataTraceCondi var1);

    public GcDataTraceCondi queryGcDataTraceCondi(GcDataTraceOffsetItemCondi var1);

    public Map<String, Object> getCurrentPeriodStrByTaskId(String var1, String var2);
}

