/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.validation.Valid
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgVerApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeTreeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgVerFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgVerClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgVerApi";

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/get"})
    public BusinessResponseEntity<OrgVersionVO> findOrgVerByName(@RequestBody GcOrgVerApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/list"})
    public BusinessResponseEntity<List<OrgVersionVO>> queryAllOrgVersion(@RequestBody GcOrgVerApiParam var1);

    @GetMapping(value={"/api/gcreport/v1/gcOrgVerApi/orgTypeTree"})
    public BusinessResponseEntity<List<OrgTypeTreeVO>> orgTypeTree();

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/update"})
    public BusinessResponseEntity<String> updateOrgVersion(@Valid @RequestBody OrgVersionVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/add"})
    public BusinessResponseEntity<OrgVersionVO> addOrgVersion(@Valid @RequestBody OrgVersionVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/remove"})
    public BusinessResponseEntity<String> removeOrgVersion(@Valid @RequestBody OrgVersionVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgVerApi/split"})
    public BusinessResponseEntity<String> splitOrgVersion(@Valid @RequestBody OrgVersionVO var1);
}

