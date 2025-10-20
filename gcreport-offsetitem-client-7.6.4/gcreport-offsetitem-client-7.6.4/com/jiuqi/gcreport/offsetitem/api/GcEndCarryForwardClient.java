/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardParamVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardParamVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.adjustingentry.api.GcEndCarryForwardClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcEndCarryForwardClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/adjustingEntry";

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/saveEndCarryForward"})
    public BusinessResponseEntity<Object> saveEndCarryForward(@RequestBody EndCarryForwardParamVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/queryEndCarryForwardResult"})
    public BusinessResponseEntity<EndCarryForwardResultVO> queryEndCarryForwardResult(@RequestBody QueryParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/adjustingEntry/listMinRecoveryPentrateDatas"})
    public BusinessResponseEntity<Pagination<Map<String, Object>>> listMinRecoveryPentrateDatas(@RequestBody MinorityRecoveryParamsVO var1);
}

