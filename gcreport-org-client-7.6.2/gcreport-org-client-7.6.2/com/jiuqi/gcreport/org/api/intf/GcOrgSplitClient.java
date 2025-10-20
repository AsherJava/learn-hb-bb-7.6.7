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
import com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgSplitedVO;
import com.jiuqi.gcreport.org.api.vo.OrgSplitVO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgSplitFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgSplitClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgSplitedApi";

    @PostMapping(value={"/api/gcreport/v1/gcOrgSplitedApi/list"})
    public BusinessResponseEntity<List<OrgSplitVO>> getSplitedOgrList(@RequestBody GcOrgSplitedParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgSplitedApi/save"})
    public BusinessResponseEntity<List<OrgSplitVO>> saveSplitOrgs(@RequestBody GcOrgSplitedVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgSplitedApi/delete"})
    public BusinessResponseEntity<String> deleteOrgSplit(@RequestBody GcOrgSplitedVO var1);
}

