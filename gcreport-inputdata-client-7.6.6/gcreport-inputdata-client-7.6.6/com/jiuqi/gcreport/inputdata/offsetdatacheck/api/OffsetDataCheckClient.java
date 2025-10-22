/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.inputdata.offsetdatacheck.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.CheckFailedInputDataVO;
import com.jiuqi.gcreport.inputdata.offsetdatacheck.vo.OffsetDataCheckVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.offsetdatacheck.api.OffsetDataCheckClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OffsetDataCheckClient {
    public static final String OFFSETCHECK_API_BASE_PATH = "/api/gcreport/v1/offsetdatacheck";

    @PostMapping(value={"/api/gcreport/v1/offsetdatacheck"})
    public BusinessResponseEntity<List<CheckFailedInputDataVO>> check(@RequestBody OffsetDataCheckVO var1);

    @PostMapping(value={"/api/gcreport/v1/offsetdatacheck/cancelOffset"})
    public BusinessResponseEntity<List<CheckFailedInputDataVO>> cancel(@RequestBody Map<String, Object> var1);
}

