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
package com.jiuqi.gcreport.organization.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.organization.api.vo.OrgDataParam;
import com.jiuqi.gcreport.organization.api.vo.OrgDataVO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.basedata.client.feign.OrganizationFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgDataClient {
    public static final String API_PATH = "/api/gcreport/v1/orgdata";

    @PostMapping(value={"/api/gcreport/v1/orgdata/get"})
    public BusinessResponseEntity<OrgDataVO> getOrgByCode(@RequestBody OrgDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgdata/orgsWithCondition"})
    public BusinessResponseEntity<Object> listOrgBySearch(@RequestBody OrgDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgdata/orgLevelTree"})
    public BusinessResponseEntity<Object> listOrgByParent(@RequestBody OrgDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgdata/allOrgLevelTree"})
    public BusinessResponseEntity<Object> listAllChildrenByParent(@RequestBody OrgDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgdata/getParentOrg"})
    public BusinessResponseEntity<Object> getParentOrgByOrg(@RequestBody OrgDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/orgdata/loadStaticResource"})
    public BusinessResponseEntity<Object> loadStaticResource(@RequestBody OrgDataParam var1);
}

