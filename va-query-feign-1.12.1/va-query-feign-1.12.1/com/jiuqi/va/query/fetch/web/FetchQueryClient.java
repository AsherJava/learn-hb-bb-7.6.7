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
package com.jiuqi.va.query.fetch.web;

import com.jiuqi.va.query.common.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.fetch.vo.FetchQueryResultVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="fetchQueryClient", name="${feignClient.queryMgr.name}", path="${feignClient.queryMgr.path}", url="${feignClient.queryMgr.url}")
public interface FetchQueryClient {
    public static final String FETCH_QUERY_BASEAPI = "/api/gcreport/v1/fetch/userDefined";

    @PostMapping(value={"/api/gcreport/v1/fetch/userDefined/execSql/{templateCode}"})
    public BusinessResponseEntity<FetchQueryResultVO> execSql(@PathVariable(value="templateCode") String var1, @RequestBody Map<String, Object> var2);

    @GetMapping(value={"/api/gcreport/v1/fetch/userDefined/getQueryParams/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/userDefined/getQueryFields/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryFields(@PathVariable(value="templateCode") String var1);
}

