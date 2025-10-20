/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.nr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.vo.FormTreeVo;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.nr.client.feign.GCEfdcDataCheckFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GCFormTabSelectClient {
    public static final String API_PATH = "/api/gcreport/v1/formTabSelect/";

    @GetMapping(value={"/api/gcreport/v1/formTabSelect/queryFormTree/{formSchemeKey}/{dataTime}"})
    public BusinessResponseEntity<List<FormTreeVo>> queryFormTree(@PathVariable(value="formSchemeKey") String var1, @PathVariable(value="dataTime") String var2) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/formTabSelect/queryFormTree/{formSchemeKey}"})
    public BusinessResponseEntity<List<FormTreeVo>> queryFormTree(@PathVariable(value="formSchemeKey") String var1) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/formTabSelect/queryFormData/{formKey}"})
    public BusinessResponseEntity<String> queryFormData(@PathVariable(value="formKey") String var1);
}

