/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.onekeymerge.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.onekeymerge.vo.GcConfigVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeConfigClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OnekeyMergeConfigClient {
    public static final String CONFIG_API_BASE_PATH = "/api/gcreport/v1/onekeyMerge/config";

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/config/save"})
    public BusinessResponseEntity<Object> saveConfig(@RequestBody GcConfigVO var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/config/delete/{id}"})
    public BusinessResponseEntity<Object> deleteConfig(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/config/update"})
    public BusinessResponseEntity<Object> updateConfig(@RequestBody GcConfigVO var1);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/config/configAndNodes"})
    public BusinessResponseEntity<List<GcConfigVO>> getTaskResultAndLogs();
}

