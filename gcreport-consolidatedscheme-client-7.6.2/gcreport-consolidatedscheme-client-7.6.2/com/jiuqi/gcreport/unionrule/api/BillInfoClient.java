/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gcreport.unionrule.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gcreport.unionrule.api.BillInfoClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface BillInfoClient {
    public static final String API_BASE_PATH = "/api/gcreport/v1/gcBillInfo";

    @GetMapping(value={"/api/gcreport/v1/gcBillInfo/billDefineTree"})
    public BusinessResponseEntity<List<SelectFloatLineOptionTreeVO>> getBillDefineTree();

    @GetMapping(value={"/api/gcreport/v1/gcBillInfo/billDefines"})
    public BusinessResponseEntity<List<SelectOptionVO>> getBillDefines();
}

