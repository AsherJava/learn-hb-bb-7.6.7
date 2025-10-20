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
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrDataQueryClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrDataQueryClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/clbr/query/all/";

    @PostMapping(value={"/api/gcreport/v1/clbr/query/all/master"})
    public BusinessResponseEntity<ClbrOverViewVO> queryClbrOverView(@RequestBody ClbrDataQueryConditon var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/query/all/details/pandect"})
    public BusinessResponseEntity<List<ClbrOverViewVO>> listClbrOverView(@RequestBody ClbrDataQueryConditon var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/query/all/details/queryClbrBillDetails"})
    public BusinessResponseEntity<PageInfo<ClbrBillVO>> queryClbrBillDetails(@RequestBody ClbrDataQueryConditon var1);
}

