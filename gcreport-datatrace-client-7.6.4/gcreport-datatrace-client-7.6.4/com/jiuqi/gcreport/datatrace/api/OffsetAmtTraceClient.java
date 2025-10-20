/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.datatrace.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.datatrace.dto.DataTraceCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceOffsetItemCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceConditionVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.api.OffsetAmtTraceClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OffsetAmtTraceClient {
    public static final String OFFSET_AMT_API_BASE_PATH = "/api/gcreport/v1/offsetAmt";

    @GetMapping(value={"/api/gcreport/v1/offsetAmt/getCurrentPeriodStrByTaskId/{taskId}/{periodStr}"})
    public BusinessResponseEntity<Map<String, Object>> getCurrentPeriodStrByTaskId(@PathVariable(value="taskId", required=false) String var1, @PathVariable(value="periodStr", required=false) String var2);

    @PostMapping(value={"/api/gcreport/v1/offsetAmt/traceOffsetAmt"})
    public BusinessResponseEntity<List<OffsetAmtTraceResultVO>> traceOffsetAmt(OffsetAmtTraceConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/offsetAmt/traceOffsetGroupAmt"})
    public BusinessResponseEntity<List<OffsetTraceResultVO>> traceOffsetGroupAmt(OffsetAmtTraceConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/offsetAmt/adjust/queryDataTraceOffsetEntry"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> queryDataTraceOffsetEntry(@RequestBody GcDataTraceCondi var1);

    @PostMapping(value={"/api/gcreport/v1/offsetAmt/adjust/dataTraceOffsetCheckList"})
    public BusinessResponseEntity<Pagination<DataTraceCheckInfoDTO>> dataTraceOffsetCheckList(@RequestBody GcDataTraceCondi var1);

    @PostMapping(value={"/api/gcreport/v1/offsetAmt/adjust/queryGcDataTraceCondi"})
    public BusinessResponseEntity<GcDataTraceCondi> queryGcDataTraceCondi(@RequestBody GcDataTraceOffsetItemCondi var1);
}

