/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO
 *  feign.Request$Options
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.task.vo.TaskConditionBoxVO;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import feign.Request;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamSyncClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncParamSyncClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/paramsync/dispatchtask/";

    default public Request.Options getOptions() {
        Request.Options options = new Request.Options(60L, TimeUnit.SECONDS, 600L, TimeUnit.SECONDS, true);
        return options;
    }

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/add/{paramSyncSchemeId}"})
    public BusinessResponseEntity<Boolean> uploadParamsUpdateInstructions(@PathVariable(value="paramSyncSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/xf"})
    public BusinessResponseEntity<Boolean> paramSync(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/list/{paramSyncSchemeId}"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncIssuedLogVO>> listXfLogs(@PathVariable(value="paramSyncSchemeId") String var1, @RequestParam(value="pageSize") Integer var2, @RequestParam(value="pageNum") Integer var3);

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/fetchSyncParamTaskInfos"})
    public BusinessResponseEntity<List<ReportDataSyncIssuedLogVO>> fetchSyncParamTaskInfos();

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/fetchSyncParamFiles"})
    public BusinessResponseEntity<ReportDataSyncParamFileDTO> fetchSyncParamFiles(Request.Options var1, @RequestBody ReportDataSyncIssuedLogVO var2);

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/log"})
    public BusinessResponseEntity<PageInfo<ReportDataSyncIssuedLogItemVO>> listXfLogItemsByLogId(@RequestParam(value="id") String var1, @RequestParam(value="pageSize") Integer var2, @RequestParam(value="pageNum") Integer var3);

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/log/retryParamSyncByLogItemIds"})
    public BusinessResponseEntity<Boolean> retryParamSyncByLogItemIds(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/log/retryParamSyncByLogIdAndBatchType/{logId}/{batchType}"})
    public BusinessResponseEntity<Boolean> retryParamSyncByLogIdAndBatchType(@PathVariable(value="logId") String var1, @PathVariable(value="batchType") String var2);

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/listAllParamSyncScheme"})
    public BusinessResponseEntity<List<ReportDataSyncParams>> listAllParamSyncScheme();

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/addParamSyncScheme"})
    public BusinessResponseEntity<Boolean> addParamSyncScheme(@RequestParam(value="syncDesFile", required=false) MultipartFile var1, @RequestParam(value="syncParam") String var2);

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/updateParamSyncScheme"})
    public BusinessResponseEntity<Boolean> updateParamSyncScheme(@RequestParam(value="syncDesFile", required=false) MultipartFile var1, @RequestParam(value="syncParam") String var2);

    @PostMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/deleteParamSyncScheme/{id}"})
    public BusinessResponseEntity<Boolean> deleteParamSyncScheme(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask/getParamSyncGroups"})
    public String getParamSyncGroups();

    @GetMapping(value={"/api/gcreport/v1/paramsync/dispatchtask//tasks/{taskId}/schemes"})
    public BusinessResponseEntity<TaskConditionBoxVO> getSchemes(@PathVariable(value="taskId") String var1) throws Exception;
}

