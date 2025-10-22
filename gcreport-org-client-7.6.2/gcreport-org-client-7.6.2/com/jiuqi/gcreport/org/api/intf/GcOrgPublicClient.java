/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgBusFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgPublicClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgPublicApi";

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/get"})
    public BusinessResponseEntity<GcOrgCacheVO> getOrgByCode(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/singleOrg"})
    public BusinessResponseEntity<GcOrgCacheVO> getSingleOrgByCode(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/orgsWithCondition"})
    public BusinessResponseEntity<Object> listOrgBySearch(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/orgLevelTree"})
    public BusinessResponseEntity<Object> listOrgByParent(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/allOrgLevelTree"})
    public BusinessResponseEntity<Object> listAllChildrenByParent(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/getParentOrg"})
    public BusinessResponseEntity<Object> getParentOrgByOrg(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/orgAllTree"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> getOrgTree(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/orgCollection"})
    public BusinessResponseEntity<List<GcOrgCacheVO>> listOrg(@RequestBody GcOrgPublicApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgPublicApi/singleOrgMap"})
    public BusinessResponseEntity<Map<String, String>> getSingleOrgMapByCode(@RequestBody GcOrgPublicApiParam var1);
}

