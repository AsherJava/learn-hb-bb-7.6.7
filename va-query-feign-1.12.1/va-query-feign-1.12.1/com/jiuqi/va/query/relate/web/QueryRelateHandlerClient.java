/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.query.relate.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.relate.vo.QueryRelateHandlerVO;
import com.jiuqi.va.query.relate.vo.QueryRelateParamVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="queryRelateHandlerClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface QueryRelateHandlerClient {
    public static final String FETCH_QUERY_BASE_API = "/api/datacenter/v1/userDefined";

    @GetMapping(value={"/api/datacenter/v1/userDefined/getRelateQueryHandlers"})
    public BusinessResponseEntity<List<QueryRelateHandlerVO>> getFetchSchemes();

    @PostMapping(value={"/api/datacenter/v1/userDefined/getRelateQueryParams/{queryHandlerName}"})
    public BusinessResponseEntity<List<QueryRelateParamVO>> getQueryParams(@PathVariable(value="queryHandlerName") String var1, @RequestBody String var2);
}

