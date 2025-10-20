/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.coop.feign.client;

import com.jiuqi.coop.feign.config.domain.ClbrBillPullDTO;
import com.jiuqi.coop.feign.config.domain.ClbrOperateDTO;
import com.jiuqi.va.domain.common.R;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(primary=false, contextId="externalClient", name="${feignClient.coop.name}", path="${feignClient.coop.path}", url="${feignClient.coop.url}")
public interface ExternalClient {
    @PostMapping(value={"coop/external/operate"})
    public R clbrOperate(@RequestBody ClbrOperateDTO var1);

    @PostMapping(value={"/confirm"})
    public R clbrConfirm(@RequestBody ClbrOperateDTO var1);

    @PostMapping(value={"clbr/anon/openBill"})
    public R openBill(@RequestBody List<ClbrBillPullDTO> var1, @RequestParam(value="username") String var2);
}

