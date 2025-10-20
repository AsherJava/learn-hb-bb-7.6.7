/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO
 *  com.jiuqi.va.query.tree.vo.MenuTreeVO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.bde.floatmodel.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.va.query.fetch.vo.FetchQueryFiledVO;
import com.jiuqi.va.query.tree.vo.MenuTreeVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.bde.floatmodel.client.FloatRegionConfigClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
@RefreshScope
public interface FloatRegionConfigClient {
    public static final String API_PREFIX = "/api/gcreport/v1//floatRegion";

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/tree/init"})
    public BusinessResponseEntity<List<MenuTreeVO>> treeInit();

    @GetMapping(value={"/api/gcreport/v1//floatRegion/vaQuery/getQueryParams/{templateCode}"})
    public BusinessResponseEntity<List<FetchQueryFiledVO>> getQueryParams(@PathVariable(value="templateCode") String var1);
}

