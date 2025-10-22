/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.va.domain.org.OrgDO
 *  feign.Request$Options
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RequestPart
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.va.domain.org.OrgDO;
import feign.Request;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncUploadDataClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/datasync/receive/";

    @GetMapping(value={"/api/gcreport/v1/datasync/receive/list/unfinished"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadDataTaskVO>> listAllExecutingTask(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/sync/{id}"})
    public BusinessResponseEntity<Boolean> dataLoading(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/stopsync/{id}"})
    public BusinessResponseEntity<Boolean> stopTask(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/receive/list/finished"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadDataTaskVO>> listAllFinishedTask(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/reject"})
    public BusinessResponseEntity<Boolean> rejectTask(@RequestBody ReportDataSyncRejectParams var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/uploadReportDataSyncUploadDataTask"}, consumes={"multipart/form-data"})
    public BusinessResponseEntity<Boolean> uploadReportDataSyncUploadDataTask(Request.Options var1, @RequestPart(value="uploadDataTaskVOJson") String var2, @RequestPart(value="uploadDataFiles") MultipartFile[] var3);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/checkUploadReportData"})
    public BusinessResponseEntity<Boolean> checkUploadReportData(@RequestBody ReportDataSyncUploadCheckParamVO var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/uploadJqrReportData"}, consumes={"multipart/form-data"})
    public BusinessResponseEntity<String> uploadJioReportData(@RequestParam(value="uploadDataTaskVOJson") String var1, @RequestPart(value="uploadDataFiles") MultipartFile var2);

    default public Request.Options getOptions() {
        Request.Options options = new Request.Options(60L, TimeUnit.SECONDS, 1800L, TimeUnit.SECONDS, true);
        return options;
    }

    @GetMapping(value={"/api/gcreport/v1/datasync/receive/showAllowForceUpload/check/{taskId}"})
    public BusinessResponseEntity<Boolean> enableDataImportCheck(@PathVariable(value="taskId") String var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/checkOrgCodes"})
    public BusinessResponseEntity<List<OrgDO>> getOrgDO(@RequestBody ReportDataCheckParam var1);

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/importFromFtp"})
    public BusinessResponseEntity<Boolean> importFromFtp(@RequestBody ReportDataUploadToFtpVO var1);

    @GetMapping(value={"/api/gcreport/v1/datasync/receive/allowFetchReportData"})
    public BusinessResponseEntity<Boolean> allowFetchReportData();

    @PostMapping(value={"/api/gcreport/v1/datasync/receive/fetchReportData"})
    public BusinessResponseEntity<Boolean> fetchReportData(@RequestBody Map<String, String> var1);
}

