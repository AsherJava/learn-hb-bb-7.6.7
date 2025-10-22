/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.org.api.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgImportParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgManageApiParam;
import com.jiuqi.gcreport.org.api.intf.base.GcOrgPublicApiParam;
import com.jiuqi.gcreport.org.api.vo.field.ExportConditionVO;
import com.jiuqi.gcreport.org.api.vo.field.GcOrgFieldVO;
import com.jiuqi.gcreport.org.api.vo.field.OrgFiledComponentVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.org.client.feign.GcOrgManageFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcOrgManageClient {
    public static final String API_PATH = "/api/gcreport/v1/gcOrgManage/";

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/table"})
    public BusinessResponseEntity<List<OrgFiledComponentVO>> queryTableDefine(@RequestBody GcOrgManageApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/fieldsManager"})
    public BusinessResponseEntity<List<GcOrgFieldVO>> queryFieldsManager(@RequestBody GcOrgManageApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/update/fieldsManager/batch"})
    public BusinessResponseEntity<List<GcOrgFieldVO>> batchUpdate(@RequestBody GcOrgManageApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/export2Excel"})
    public BusinessResponseEntity<Object> exportExcel(@RequestBody ExportConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/upload"})
    public BusinessResponseEntity<Object> uploadData(@RequestParam(value="file") MultipartFile var1, GcOrgImportParam var2);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/uploadBase"})
    public BusinessResponseEntity<Object> uploadBaseOrgData(@RequestParam(value="file") MultipartFile var1, GcOrgImportParam var2);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/orgList/allChildren"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listBaseOrgValusForPage(@RequestBody GcOrgImportParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/formData"})
    public BusinessResponseEntity<Map<String, Object>> getOrgFormData(@RequestBody GcOrgApiParam var1);

    @PostMapping(value={"/api/gcreport/v1/gcOrgManage/flexibleSearch"})
    public BusinessResponseEntity<Object> listOrgByFlexibleSearch(@RequestBody GcOrgPublicApiParam var1);

    @GetMapping(value={"/api/gcreport/v1/gcOrgManage/enableMergeUnit"})
    public boolean getEnableMergeUnit();
}

