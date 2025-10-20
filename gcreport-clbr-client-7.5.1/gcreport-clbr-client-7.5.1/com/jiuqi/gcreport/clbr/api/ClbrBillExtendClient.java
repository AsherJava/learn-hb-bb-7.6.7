/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.clbr.dto.ClbrBillCancelMsgDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillPushResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="clbrBillExtendClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}")
public interface ClbrBillExtendClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/clbr/bill/extend/";

    @PostMapping(value={"/api/gcreport/v1/clbr/bill/extend/cancel/part"})
    public BusinessResponseEntity<ClbrBillPushResultDTO> partCancelClbrBill(@RequestBody ClbrBillCancelMsgDTO var1);
}

