/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncUploadClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/datasync/uploadscheme/";

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/add"})
    public BusinessResponseEntity<String> saveUploadScheme(@RequestBody ReportDataSyncUploadSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/delete/{id}"})
    public BusinessResponseEntity<Boolean> deleteUploadScheme(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/update"})
    public BusinessResponseEntity<String> updateUploadScheme(@RequestBody ReportDataSyncUploadSchemeVO var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/queryUploadSchemePeriodTitle/{uploadSchemeId}"})
    public BusinessResponseEntity<String> queryUploadSchemePeriodTitle(@PathVariable(value="uploadSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/dataentry/sync"})
    public BusinessResponseEntity<Boolean> uploadReportData(@RequestBody ReportDataParam var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/checkInputTable/{uploadSchemeId}"})
    public BusinessResponseEntity<Boolean> isExistInputTable(@PathVariable(value="uploadSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/reject"})
    public BusinessResponseEntity<Boolean> rejectReportData(@RequestBody ReportDataSyncUploadDataTaskVO var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/query/{id}"})
    public BusinessResponseEntity<ReportDataSyncUploadSchemeVO> getUploadSchemeById(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/list"})
    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllUploadScheme();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/listTree"})
    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllUploadSchemeTree();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/listGroupTree"})
    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllSchemeGroupTree();

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/log"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadLogVO>> listUploadLogsBySchemeId(@RequestBody ReportDataSyncUploadLogQueryParamVO var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/getUploadDataGroups"})
    public String getUploadDataGroups();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/getUploadDataFileFormat"})
    public String getUploadDataFileFormat();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/isParamAllModal"})
    public BusinessResponseEntity<Boolean> isParamAllModal();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/showAllowForceUpload/{taskId}"})
    public BusinessResponseEntity<Boolean> enableDataImport(@PathVariable(value="taskId") String var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/getSchemeIdByPeriodAndTask/{taskId}/{periodStr}"})
    public BusinessResponseEntity<String> getSchemeIdByPeriodAndTask(@PathVariable(value="taskId") String var1, @PathVariable(value="periodStr") String var2);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/getSchemeIdByPeriodOffsetAndTask/{taskId}/{periodOffset}"})
    public BusinessResponseEntity<String> getSchemeIdByPeriodOffsetAndTask(@PathVariable(value="taskId") String var1, @PathVariable(value="periodOffset") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/modifyLoadingResults"})
    public BusinessResponseEntity<Boolean> modifyLoadingResults(@RequestBody ReportsyncDataLoadParam var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/uploadscheme/uploadToFtp"})
    public BusinessResponseEntity<Boolean> uploadToFtp(@RequestBody ReportDataUploadToFtpVO var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/getFileFormat"})
    public BusinessResponseEntity<String> getFileFormat();

    @GetMapping(value={"/api/gcreport/v1/datasync/uploadscheme/listAllMappingScheme"})
    public BusinessResponseEntity<List<Map<String, String>>> listAllMappingScheme();
}

