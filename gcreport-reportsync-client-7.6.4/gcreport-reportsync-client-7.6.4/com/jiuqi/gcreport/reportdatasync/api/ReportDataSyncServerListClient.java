/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.np.user.dto.PasswordDTO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelTemplateVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.user.dto.PasswordDTO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerListClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncServerListClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/paramsync/serverlist/";

    @GetMapping(value={"/api/gcreport/v1/paramsync/serverlist/list"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncServerInfoVO>> listServerInfos(@RequestParam(value="keywords", required=false) String var1, @RequestParam(value="pageSize") Integer var2, @RequestParam(value="pageNum") Integer var3);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/update/startflag"})
    public BusinessResponseEntity<Boolean> updateServerInfoState(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/updateSyncModifyFlag/startflag"})
    public BusinessResponseEntity<String> updateSyncModifyFlag(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/update/manageuserids"})
    public BusinessResponseEntity<Boolean> updateManageUser(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/register"})
    public BusinessResponseEntity<Boolean> registerServerInfo(@RequestBody ReportDataSyncServerInfoVO var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/updateServerInfo"})
    public BusinessResponseEntity<Boolean> updateServerInfo(@RequestBody ReportDataSyncServerInfoVO var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/deleteServer"})
    public BusinessResponseEntity<Boolean> deleteServerInfo(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/queryPasswordDTOByUsername"})
    public BusinessResponseEntity<PasswordDTO> queryPasswordDTOByUsername(@RequestBody String var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverlist/getServerInfoByOrgCode/{orgType}/{periodStr}/{orgCode}"})
    public BusinessResponseEntity<ReportDataSyncServerInfoVO> getServerInfoByOrgCode(@PathVariable(value="orgType") String var1, @PathVariable(value="periodStr") String var2, @PathVariable(value="orgCode") String var3);

    @GetMapping(value={"/api/gcreport/v1/paramsync/serverlist/getSyncMethod"})
    public BusinessResponseEntity<List<MultilevelTemplateVO>> getSyncMethod();

    @GetMapping(value={"/api/gcreport/v1/paramsync/serverlist/getSyncType"})
    public BusinessResponseEntity<List<MultilevelTemplateVO>> getSyncType();

    @GetMapping(value={"/api/gcreport/v1/paramsync/serverlist/listAllFileFormat"})
    public BusinessResponseEntity<List<MultilevelTemplateVO>> listAllFileFormat();
}

