/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.tree.FormTree
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.validation.constraints.NotNull
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.efdcdatacheck.client.intf;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckConfigVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckCreateReportVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckReportPageVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultGroupVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckResultVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.EfdcCheckUserVo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcCheckInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcBatchEfdcQueryParam;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcEfdcShareFileVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormGroupFieldsInfoVO;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.TaskFormInfoVO;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.tree.FormTree;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.nr.client.feign.GCEfdcDataCheckFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GCEfdcDataCheckClient {
    public static final String API_PATH = "/api/gcreport/v1/dataCheck/";

    @PostMapping(value={"/api/gcreport/v1/dataCheck/efdcDataCheck"})
    public BusinessResponseEntity<EfdcCheckResultGroupVO> processEfdcDataCheckResultGroup(@RequestBody GcFormOperationInfo var1);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/exportPdf"})
    public void exportPdf(@RequestBody GcFormOperationInfo var1, HttpServletResponse var2) throws Exception;

    @PostMapping(value={"/api/gcreport/v1/dataCheck/efdcBatchDataCheck"})
    public BusinessResponseEntity<AsyncTaskInfo> efdcBatchDataCheck(@RequestBody GcBatchEfdcCheckInfo var1);

    @GetMapping(value={"/api/gcreport/v1/dataCheck/{asynTaskID}/efdcBatchCheckUnits"})
    public BusinessResponseEntity<Map<String, Object>> efdcBatchCheckUnits(@PathVariable(value="asynTaskID") String var1);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/efdcBatchCheckResult"})
    public BusinessResponseEntity<List<EfdcCheckResultVO>> efdcBatchCheckResult(@RequestBody GcBatchEfdcQueryParam var1);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/batchResultExport"})
    public void batchResultExport(@RequestBody GcBatchEfdcQueryParam var1, HttpServletResponse var2);

    @GetMapping(value={"/api/gcreport/v1/dataCheck/{taskId}/efdcDataCheckConfig"})
    public BusinessResponseEntity<List<EfdcCheckConfigVO>> getEfdcDataCheckConfig(@PathVariable(value="taskId") String var1) throws Exception;

    @PostMapping(value={"/api/gcreport/v1/dataCheck/{taskId}/efdcDataCheckConfig"})
    public BusinessResponseEntity<String> saveEfdcDataCheckConfig(@PathVariable(value="taskId") String var1, @RequestBody List<EfdcCheckConfigVO> var2) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/dataCheck/{taskId}/{schemeId}/reports"})
    public BusinessResponseEntity<List<TaskFormInfoVO>> reports(@PathVariable(value="taskId") String var1, @PathVariable(value="schemeId") String var2);

    @GetMapping(value={"/api/gcreport/v1/dataCheck/{schemeId}/cwFormulaScheme"})
    public BusinessResponseEntity<List<FormulaSchemeDefine>> cwFormulaScheme(@PathVariable(value="schemeId") String var1);

    @GetMapping(value={"/api/gcreport/v1/dataCheck/{formId}/{cwFormulaSchemeId}/efdcCheckZbs"})
    public BusinessResponseEntity<List<TaskFormGroupFieldsInfoVO>> efdcCheckZbs(@PathVariable(value="formId") String var1, @PathVariable(value="cwFormulaSchemeId") String var2) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/dataCheck/reportQuery"})
    public BusinessResponseEntity<EfdcCheckReportPageVO> reportQuery(@RequestParam(value="pageSize") Integer var1, @RequestParam(value="pageNum") Integer var2, @RequestParam(value="showQueryCount") boolean var3, @RequestParam(value="queryConditions") @RequestBody String var4) throws Exception;

    @GetMapping(value={"/api/gcreport/v1/dataCheck/reportQuery/viewPdf/{id}"}, produces={"application/pdf"})
    public byte[] pdfView(@PathVariable(value="id") @NotNull String var1) throws IOException;

    @GetMapping(value={"/api/gcreport/v1/dataCheck/reportQuery/download/{ids}"})
    public void batchDownload(@PathVariable(value="ids") @RequestBody Set<String> var1, HttpServletRequest var2, HttpServletResponse var3) throws IOException;

    @GetMapping(value={"/api/gcreport/v1/dataCheck/reportQuery/downloadExcel/{id}"})
    public void downloadExcel(@PathVariable(value="id") @RequestBody String var1, HttpServletRequest var2, HttpServletResponse var3);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/reportQuery/{ids}"})
    public BusinessResponseEntity<Object> bacthDelete(@PathVariable(value="ids") @RequestBody Set<String> var1);

    @GetMapping(value={"/api/gcreport/v1/dataCheck/reportQuery/clearMongoFiles"})
    public BusinessResponseEntity<Object> clearMongoFiles(@RequestParam(value="dateStr") String var1) throws Exception;

    @PostMapping(value={"/api/gcreport/v1/dataCheck/queryEfdcConfigReports/{schemeId}/{dataTime}"})
    public BusinessResponseEntity<FormTree> queryEfdcConfigReports(@PathVariable(value="schemeId") String var1, @PathVariable(value="dataTime") String var2);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/queryEfdcConfigReportsByReport/{schemeId}"})
    public BusinessResponseEntity<FormTree> queryEfdcConfigReportsByReport(@PathVariable(value="schemeId") String var1, @RequestBody Map<String, DimensionValue> var2);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/createEfdcDataCheckReport"})
    public BusinessResponseEntity<String> createEfdcDataCheckReport(@RequestBody EfdcCheckCreateReportVO var1);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/checkUser"})
    public BusinessResponseEntity<String> efdcCheckUser(@RequestBody EfdcCheckUserVo var1);

    @PostMapping(value={"/api/gcreport/v1/dataCheck/shareFile"})
    public BusinessResponseEntity<String> shareFile(@RequestBody GcEfdcShareFileVO var1);
}

