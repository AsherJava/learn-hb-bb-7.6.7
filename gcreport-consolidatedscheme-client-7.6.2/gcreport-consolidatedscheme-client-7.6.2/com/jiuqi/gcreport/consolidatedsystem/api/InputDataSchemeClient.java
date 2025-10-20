/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.consolidatedsystem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.vo.InputDataSchemeVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.InputDataSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InputDataSchemeClient {
    public static final String API_PATH = "/api/gcreport/v1/dataScheme/";

    @GetMapping(value={"/api/gcreport/v1/dataScheme//listInputDataScheme"})
    public BusinessResponseEntity<List<InputDataSchemeVO>> listInputDataScheme();

    @GetMapping(value={"/api/gcreport/v1/dataScheme//getInputDataSchemeByDataSchemeKey/{dataSchemeKey}"})
    public BusinessResponseEntity<InputDataSchemeVO> getInputDataSchemeByDataSchemeKey(@PathVariable(value="dataSchemeKey") String var1);
}

