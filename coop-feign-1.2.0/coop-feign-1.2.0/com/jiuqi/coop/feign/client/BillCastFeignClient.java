/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.coop.feign.client;

import com.jiuqi.coop.feign.config.domain.BillCreateClbrDTO;
import com.jiuqi.coop.feign.config.domain.ClbrBillPullDTO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="billCastFeignClient", name="${feignClient.coop.name}", path="${feignClient.coop.path}", url="${feignClient.coop.url}")
public interface BillCastFeignClient {
    @PostMapping(value={"cast/collaborative/billCreateCollaborative"})
    public R billCreateCollaborative(@RequestBody BillCreateClbrDTO var1);

    @PostMapping(value={"cast/collaborative/matchTemplate"})
    public R matchTemplate(@RequestBody List<ClbrBillPullDTO> var1);

    @PostMapping(value={"cast/collaborative/clbrCreateBillData/{defineCode}"})
    public R clbrCreateBillData(@PathVariable(value="defineCode") String var1, @RequestBody List<ClbrBillPullDTO> var2);

    @PostMapping(value={"cast/collaborative/getClbrCreateBillTemplate/{defineCode}"})
    public R getClbrCreateBillTemplate(@PathVariable(value="defineCode") String var1);
}

