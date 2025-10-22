/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.clbr.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeBatchQueryDTO;
import com.jiuqi.gcreport.clbr.dto.ClbrSchemeDTO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeTreeVO;
import com.jiuqi.gcreport.clbr.vo.ClbrSchemeVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.clbr.api.ClbrSchemeClient", name="${feignClient.gcreportClbr.name:gcreport-clbr-service}", url="${feignClient.gcreportClbr.url:}", path="${feignClient.gcreportClbr.path:}", primary=false)
public interface ClbrSchemeClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/clbr/scheme/";

    @PostMapping(value={"/api/gcreport/v1/clbr/scheme/add"})
    public BusinessResponseEntity<ClbrSchemeVO> add(@RequestBody ClbrSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/scheme/edit"})
    public BusinessResponseEntity<Boolean> edit(@RequestBody ClbrSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/scheme/query"})
    public BusinessResponseEntity<PageInfo<ClbrSchemeVO>> query(@RequestBody ClbrSchemeCondition var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/scheme/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/clbr/scheme/queryScheme"})
    public BusinessResponseEntity<Map<String, List<ClbrSchemeDTO>>> queryScheme(@RequestBody ClbrSchemeBatchQueryDTO var1);

    @GetMapping(value={"/api/gcreport/v1/clbr/scheme/tree"})
    public BusinessResponseEntity<List<ClbrSchemeTreeVO>> listTree();
}

