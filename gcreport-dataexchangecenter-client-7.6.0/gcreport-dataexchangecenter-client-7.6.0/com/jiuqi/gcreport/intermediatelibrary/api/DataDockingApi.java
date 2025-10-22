/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.intermediatelibrary.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingQueryVO;
import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.intermediatelibrary.api.DataDockingApi", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface DataDockingApi {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/dataDocking";

    @PostMapping(value={"/api/gcreport/v1/dataDocking/saveData"})
    public BusinessResponseEntity<String> saveData(@RequestBody DataDockingVO var1);

    @PostMapping(value={"/api/gcreport/v1/dataDocking/queryData"})
    public BusinessResponseEntity<DataDockingVO> queryData(@RequestBody DataDockingQueryVO var1);
}

