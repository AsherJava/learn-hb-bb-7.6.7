/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.archive.api.scheme;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveConfigVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveContext;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveInfoVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveLogVO;
import com.jiuqi.gcreport.archive.api.scheme.vo.ArchiveQueryParam;
import com.jiuqi.gcreport.archive.api.scheme.vo.EFSResponseData;
import com.jiuqi.gcreport.archive.api.scheme.vo.SendArchiveVO;
import com.jiuqi.nr.dataentry.tree.FormTree;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.archive.api.GCArchiveClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface GCArchiveClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/archive/";

    @PostMapping(value={"/api/gcreport/v1/archive/queryFormTree/{schemeId}/{dataTime}"})
    public BusinessResponseEntity<FormTree> queryFormTree(@PathVariable(value="schemeId") String var1, @PathVariable(value="dataTime") String var2);

    @PostMapping(value={"/api/gcreport/v1/archive/cancel"})
    public BusinessResponseEntity<List<EFSResponseData>> cancelArchive(@RequestBody SendArchiveVO var1);

    @PostMapping(value={"/api/gcreport/v1/archive/batch/doAction/save"})
    public BusinessResponseEntity<Object> batchDoActionSave(@RequestBody ArchiveContext var1);

    @PostMapping(value={"/api/gcreport/v1/archive/batch/doAction/start"})
    public BusinessResponseEntity<Object> batchDoActionStart(@RequestBody String var1);

    @PostMapping(value={"/api/gcreport/v1/archive/batch/log"})
    public BusinessResponseEntity<PageInfo<ArchiveLogVO>> batchLogQuery(@RequestBody ArchiveQueryParam var1);

    @PostMapping(value={"/api/gcreport/v1/archive/details"})
    public BusinessResponseEntity<PageInfo<ArchiveInfoVO>> detailsLogQuery(@RequestBody ArchiveQueryParam var1);

    @PostMapping(value={"/api/gcreport/v1/archive/details/delete"})
    public BusinessResponseEntity<PageInfo<ArchiveInfoVO>> detailsLogDelete(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/archive/{taskId}/archiveConfig"})
    public BusinessResponseEntity<List<ArchiveConfigVO>> getArchiveConfig(@PathVariable(value="taskId") String var1);

    @GetMapping(value={"/api/gcreport/v1/archive/{taskId}/{orgType}/archiveConfig"})
    public BusinessResponseEntity<List<ArchiveConfigVO>> getArchiveConfigWithOrgType(@PathVariable(value="taskId") String var1, @PathVariable(value="orgType") String var2);

    @PostMapping(value={"/api/gcreport/v1/archive/{taskId}/archiveConfig"})
    public BusinessResponseEntity<String> saveArchiveConfig(@PathVariable(value="taskId") String var1, @RequestBody List<ArchiveConfigVO> var2);

    @GetMapping(value={"/api/gcreport/v1/archive/archivePlugin/list"})
    public BusinessResponseEntity<List<Map<String, String>>> listArchivePlugin();
}

