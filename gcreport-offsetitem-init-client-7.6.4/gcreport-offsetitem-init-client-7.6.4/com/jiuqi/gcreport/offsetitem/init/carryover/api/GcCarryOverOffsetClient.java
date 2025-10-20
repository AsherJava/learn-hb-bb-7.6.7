/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.init.carryover.vo.CarryOverOffsetSubjectMappingVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.init.carryover.api.GcCarryOverOffsetClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcCarryOverOffsetClient {
    public static final String CARRYOVER_OFFSET_API_BASE_PATH = "/api/gcreport/v1/offsetitems/carryover";

    @PostMapping(value={"/api/gcreport/v1/offsetitems/carryover/checkSubjectMapping/{systemId}"})
    public BusinessResponseEntity<String> checkSubjectMapping(@PathVariable String var1, @RequestBody Map<String, List<CarryOverOffsetSubjectMappingVO>> var2);
}

