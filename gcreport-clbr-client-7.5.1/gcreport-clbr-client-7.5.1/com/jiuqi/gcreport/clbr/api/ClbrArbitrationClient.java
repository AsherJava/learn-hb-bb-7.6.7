/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationCancel;
import com.jiuqi.gcreport.clbr.dto.ClbrArbitrationQueryParamDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrBillRejectDTO;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrTodoCountVO;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrArbitrationClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrArbitrationClient {
    public static final String API_ARBITRATION_PATH = "/api/gcreport/v1/clbr/arbitration/";

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/query/list"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryArbitrationList(@RequestBody ClbrArbitrationQueryParamDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/singleQuery/list"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> singleQueryArbitrationList(@RequestBody ClbrArbitrationQueryParamDTO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/reject/consent"})
    public BusinessResponseEntity<Object> rejectClbrArbitrationConsent(@RequestBody Set<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/reject/cancel"})
    public BusinessResponseEntity<Object> rejectClbrArbitrationCancel(@RequestBody ClbrArbitrationCancel var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/reject/todoNum"})
    public BusinessResponseEntity<Integer> getArbitrationBacklogNum(@RequestBody ClbrTodoCountVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/arbitration/synergy/cancel"})
    public BusinessResponseEntity<Object> synergyCancel(@RequestBody ClbrBillRejectDTO var1);
}

