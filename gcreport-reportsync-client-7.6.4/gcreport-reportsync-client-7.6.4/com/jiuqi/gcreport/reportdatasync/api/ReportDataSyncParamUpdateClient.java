/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
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
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO;
import feign.Request;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamUpdateClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncParamUpdateClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/paramsync/receivetask/";

    default public Request.Options getOptions() {
        Request.Options options = new Request.Options(60L, TimeUnit.SECONDS, 600L, TimeUnit.SECONDS, true);
        return options;
    }

    @PostMapping(value={"/api/gcreport/v1/paramsync/receivetask/addTask"}, consumes={"multipart/form-data"})
    public BusinessResponseEntity<Boolean> addTask(Request.Options var1, @RequestPart(value="receiveTaskVOJson") String var2, @RequestPart(value="syncAttachFiles") MultipartFile[] var3);

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/list"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncReceiveTaskVO>> listAllTasks(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2);

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/fetchTargetSyncParamTaskInfos"})
    public BusinessResponseEntity<List<ReportDataSyncIssuedLogVO>> fetchTargetSyncParamTaskInfos();

    @PostMapping(value={"/api/gcreport/v1/paramsync/receivetask/fetchTargetSyncParam"})
    public BusinessResponseEntity<Boolean> fetchTargetSyncParam(@RequestBody ReportDataSyncIssuedLogVO var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/receivetask/sync/{id}"})
    public BusinessResponseEntity<Boolean> updateReportParamsData(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/logs"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncReceiveLogVO>> listReceiveTaskLogs(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2);

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/logitem/{syncLogInfoId}"})
    public BusinessResponseEntity<List<ReportDataSyncReceiveLogItemVO>> listReceiveLogItemsByLogId(@PathVariable(value="syncLogInfoId") String var1);

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/listAllParamSyncTasks"})
    public BusinessResponseEntity<List<ReportParamSyncTaskOptionVO>> listAllParamSyncTasks();

    @GetMapping(value={"/api/gcreport/v1/paramsync/receivetask/listComparisonResults/{syncLogInfoId}"})
    public BusinessResponseEntity<List<Object>> listComparisonResults(@PathVariable(value="syncLogInfoId") String var1);
}

