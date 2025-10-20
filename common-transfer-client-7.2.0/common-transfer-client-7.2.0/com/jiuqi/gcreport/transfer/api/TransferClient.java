/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.validation.Valid
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.transfer.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.transfer.vo.TransferColumnVo;
import com.jiuqi.gcreport.transfer.vo.TransferVo;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.client.transfer.feign.TransferFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface TransferClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/transfer";

    @PostMapping(value={"/api/gcreport/v1/transfer/save"})
    public BusinessResponseEntity<Object> save(@RequestBody @Valid TransferVo var1);

    @PostMapping(value={"/api/gcreport/v1/transfer/get"})
    public BusinessResponseEntity<List<String>> getSelectColumnsByPath(@RequestBody TransferVo var1);

    @PostMapping(value={"/api/gcreport/v1/transfer/getNoUser"})
    public BusinessResponseEntity<List<String>> getSelectColumnsByPathNoUser(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/transfer/getColumns/{tableName}"})
    public BusinessResponseEntity<List<TransferColumnVo>> getColumns(@PathVariable String var1);
}

