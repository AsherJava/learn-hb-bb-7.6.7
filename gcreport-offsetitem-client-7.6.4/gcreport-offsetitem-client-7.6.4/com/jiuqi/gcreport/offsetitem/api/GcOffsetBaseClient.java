/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.offsetitem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.Button;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.offsetitem.api.GcOffsetBaseClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GcOffsetBaseClient {
    public static final String UNOFFSETOPTION_API_BASE_PATH = "/api/gcreport/v1/offsetOption";

    @GetMapping(value={"/api/gcreport/v1/offsetOption/listShowTypeConfigForTab/{pageType}/{dataSource}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listshowTypeConfigForTab(@PathVariable(value="pageType") String var1, @PathVariable(value="dataSource") String var2);

    @GetMapping(value={"/api/gcreport/v1/offsetOption/listShowTypeConfig/{dataSource}"})
    public BusinessResponseEntity<List<Map<String, Object>>> listShowTypeConfig(@PathVariable(value="dataSource") String var1);

    @GetMapping(value={"/api/gcreport/v1/offsetOption/listShowButton"})
    public List<Button> listShowButton();
}

