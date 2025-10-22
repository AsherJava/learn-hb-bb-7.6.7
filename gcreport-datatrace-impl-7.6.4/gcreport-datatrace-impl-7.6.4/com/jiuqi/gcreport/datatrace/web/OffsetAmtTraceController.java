/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.datatrace.api.OffsetAmtTraceClient
 *  com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceConditionVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.datatrace.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.datatrace.api.OffsetAmtTraceClient;
import com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.service.OffsetAmtTraceService;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceConditionVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class OffsetAmtTraceController
implements OffsetAmtTraceClient {
    @Autowired
    OffsetAmtTraceService offsetAmtTraceService;

    public BusinessResponseEntity<Map<String, Object>> getCurrentPeriodStrByTaskId(String taskId, String periodStr) {
        Map<String, Object> periodInfo = this.offsetAmtTraceService.getCurrentPeriodStrByTaskId(taskId, periodStr);
        return BusinessResponseEntity.ok(periodInfo);
    }

    public BusinessResponseEntity<List<OffsetAmtTraceResultVO>> traceOffsetAmt(@RequestBody OffsetAmtTraceConditionVO condition) {
        return BusinessResponseEntity.ok(this.offsetAmtTraceService.traceOffsetAmt(condition.getOffsetItemId(), condition.getTaskArg()));
    }

    public BusinessResponseEntity<List<OffsetTraceResultVO>> traceOffsetGroupAmt(@RequestBody OffsetAmtTraceConditionVO condition) {
        return BusinessResponseEntity.ok(this.offsetAmtTraceService.traceOffsetGroupAmt(condition.getMrecid(), condition.getTaskArg()));
    }

    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryDataTraceOffsetEntry(GcDataTraceCondi condi) {
        Pagination<Map<String, Object>> offsetPage = this.offsetAmtTraceService.queryDataTraceOffsetEntry(condi);
        return BusinessResponseEntity.ok(offsetPage);
    }

    public BusinessResponseEntity<Pagination<DataTraceCheckInfoDTO>> dataTraceOffsetCheckList(GcDataTraceCondi condi) {
        Pagination<DataTraceCheckInfoDTO> offsetPage = this.offsetAmtTraceService.dataTraceOffsetCheckList(condi);
        return BusinessResponseEntity.ok(offsetPage);
    }

    public BusinessResponseEntity<GcDataTraceCondi> queryGcDataTraceCondi(GcDataTraceOffsetItemCondi condi) {
        GcDataTraceCondi gcDataTraceCondi = this.offsetAmtTraceService.queryGcDataTraceCondi(condi);
        return BusinessResponseEntity.ok((Object)gcDataTraceCondi);
    }
}

