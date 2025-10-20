/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.validation.Valid
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgTypeCopyApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgTypeFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgTypeClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgTypeApi";

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/delete"})
    public BusinessResponseEntity<String> deleteUnitType(@RequestBody OrgTypeVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/find"})
    public BusinessResponseEntity<OrgTypeVO> findUnitType(@RequestBody GcOrgTypeApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/list"})
    public BusinessResponseEntity<List<OrgTypeVO>> queryAllUnitType();

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/update"})
    public BusinessResponseEntity<String> updateUnitType(@Valid @RequestBody OrgTypeVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/add"})
    public BusinessResponseEntity<OrgTypeVO> addUnitType(@Valid @RequestBody OrgTypeVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgTypeApi/copy"})
    public BusinessResponseEntity<OrgTypeVO> copyUnitType(@Valid @RequestBody GcOrgTypeCopyApiParam var1);
}

