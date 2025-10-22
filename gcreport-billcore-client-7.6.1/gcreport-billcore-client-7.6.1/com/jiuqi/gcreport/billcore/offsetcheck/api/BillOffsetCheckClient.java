/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 *  com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.billcore.offsetcheck.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.billcore.offsetcheck.dto.BillOffsetCheckInfoDTO;
import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import com.jiuqi.gcreport.datatrace.vo.OffsetTraceResultVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.billcore.offsetcheck.api.BillOffsetCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface BillOffsetCheckClient {
    public static final String API_PATH = "/api/gcreport/v1/billOffsetCheck/";

    @PostMapping(value={"/api/gcreport/v1/billOffsetCheck/listOffsetCheckInfos"})
    public BusinessResponseEntity<Pagination<BillOffsetCheckInfoDTO>> listOffsetCheckInfos(@RequestBody GcDataTraceCondi var1);

    @PostMapping(value={"/api/gcreport/v1/billOffsetCheck/offsetCheckAmtTrace"})
    public BusinessResponseEntity<List<OffsetTraceResultVO>> offsetCheckAmtTrace(@RequestBody GcDataTraceCondi var1);
}

