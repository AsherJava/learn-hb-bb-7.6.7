/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgBaseApiParam;
import com.jiuqi.gcreport.org.api.vo.OrgToJsonVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgBaseFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgBaseClient {
    public static final String API_PATH = "/api/gcreport/v1/gcBaseOrgApi";

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/tree"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgTree(@RequestBody(required=false) GcOrgBaseApiParam var1);

    @GetMapping(value={"/api/gcreport/v1/gcBaseOrgApi/withAuth"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgTreeWithAuth(boolean var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/orgList/allChildren"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgByByName(@RequestBody GcOrgBaseApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/current"})
    public BusinessResponseEntity<OrgToJsonVO> getUnitById(@RequestBody GcOrgBaseApiParam var1);

    @GetMapping(value={"/api/gcreport/v1/gcBaseOrgApi/orgList/{searchText}"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listAllChildrenBaseOrg(@PathVariable(value="searchText") String var1);

    @GetMapping(value={"/api/gcreport/v1/gcBaseOrgApi/listAllBaseOrg"})
    public BusinessResponseEntity<Map<String, String>> listAllBaseOrg();

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/listchildren"})
    public BusinessResponseEntity<List<OrgToJsonVO>> listBaseOrgByParent(@RequestBody GcOrgBaseApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/add"})
    public BusinessResponseEntity<String> addBaseUnit(@RequestBody OrgToJsonVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/update"})
    public BusinessResponseEntity<?> updateBaseUnit(@RequestBody OrgToJsonVO var1);

    @Transactional(rollbackFor={Exception.class})
    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/batchUpdate"})
    public BusinessResponseEntity<String> batchUpdateBaseUnit(@RequestBody List<OrgToJsonVO> var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/delete"})
    public BusinessResponseEntity<?> deleteBaseUnit(@RequestBody GcOrgBaseApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/moveto"})
    public BusinessResponseEntity<String> moverOrder(@RequestBody GcOrgBaseApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/move"})
    public BusinessResponseEntity<String> moverUpOrDown(@RequestBody GcOrgBaseApiParam var1);

    @Transactional(rollbackFor={Exception.class})
    @PostMapping(value={"/api/gcreport/v1/gcBaseOrgApi/modifySort"})
    public BusinessResponseEntity<String> modifySortBaseUnit(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/baseOrgTree/auth/first"})
    public OrgToJsonVO getAuthFirst();
}

