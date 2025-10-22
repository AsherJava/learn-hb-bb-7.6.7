/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.onekeymerge.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.calculate.vo.DebugZbInfoVO;
import com.jiuqi.gcreport.onekeymerge.dto.AutoMergeDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskProcessDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskRecordDTO;
import com.jiuqi.gcreport.onekeymerge.dto.MergeTaskResultLogDTO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcFinishCalcResultVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcPreParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.ReturnObject;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.onekeymerge.api.OnekeyMergeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OnekeyMergeClient {
    public static final String ONEKEYMERGE_API_BASE_PATH = "/api/gcreport/v1/onekeyMerge";

    @RequestMapping(value={"/api/gcreport/v1/onekeyMerge/doOnekeyMerge"})
    public BusinessResponseEntity<Object> doOnekeyMerge(@RequestBody GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/stopOnekeyMerge"})
    public BusinessResponseEntity<Object> stopOnekeyMerge(@RequestBody GcActionParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/doDataPreProcess"})
    public BusinessResponseEntity<Object> doDataPreProcess(@RequestBody GcPreParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/doReportPick"})
    public BusinessResponseEntity<ReturnObject> doReportPick(@RequestBody GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/relationToMerge"})
    public BusinessResponseEntity<ReturnObject> doRelationToMerge(@RequestBody GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/doConversion"})
    public BusinessResponseEntity<ReturnObject> doSplit(@RequestBody @Valid GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/doCalc"})
    public BusinessResponseEntity<ReturnObject> doCalc(@RequestBody @Valid GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/doCalcCheck"})
    public BusinessResponseEntity<ReturnObject> getCurrentFinishTask(@RequestBody @Valid GcActionParamsVO var1, BindingResult var2);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/finishCalc"})
    public BusinessResponseEntity<GcFinishCalcResultVO> doCalcDone(@RequestBody @Valid GcActionParamsVO var1, BindingResult var2, HttpServletRequest var3);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/finishCalcAsync"})
    public BusinessResponseEntity<GcFinishCalcResultVO> doCalcDoneAsync(@RequestBody @Valid GcActionParamsVO var1, BindingResult var2, HttpServletRequest var3);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/taskLog/{taskLogId}"})
    public BusinessResponseEntity<TaskLog> getTaskLog(@PathVariable(value="taskLogId") String var1);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/taskLog/{taskLogId}/{taskCode}"})
    public BusinessResponseEntity<TaskLog> getTaskLogWithCode(@PathVariable(value="taskLogId") String var1, @PathVariable(value="taskCode") String var2);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/taskResult/{taskLogId}/{taskCode}"})
    public BusinessResponseEntity<Object> getTaskResult(@PathVariable(value="taskLogId") String var1, @PathVariable(value="taskCode") String var2);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/taskProcess/three"})
    public BusinessResponseEntity<Object> getTaskProcess(@RequestParam(value="gcActionParamsVO") String var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/debugZbReWrite/{zbCode}"})
    public BusinessResponseEntity<DebugZbInfoVO> debugZbReWrite(@RequestBody @Valid GcActionParamsVO var1, @PathVariable(value="zbCode") String var2);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/mergeItem/getShow"})
    public List<Map<String, Object>> mergeItem();

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/process/{taskLogId}"})
    public BusinessResponseEntity<MergeTaskProcessDTO> getMergeTaskProcess(@PathVariable(value="taskLogId") String var1);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/stopMergeTask/{taskLogId}"})
    public BusinessResponseEntity<Object> stopMergeTask(@PathVariable(value="taskLogId") String var1);

    @GetMapping(value={"/api/gcreport/v1/onekeyMerge/getMergeTaskLogs/{taskLogId}"})
    public BusinessResponseEntity<MergeTaskResultLogDTO> getMergeTaskLogs(@PathVariable(value="taskLogId") String var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/taskRecord"})
    public BusinessResponseEntity<List<MergeTaskRecordDTO>> getTaskRecord(@RequestBody GcActionParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/checkOrgInfo"})
    public BusinessResponseEntity<String> checkOrgInfo(@RequestBody GcActionParamsVO var1);

    @PostMapping(value={"/api/gcreport/v1/onekeyMerge/getPeriodByType"})
    public BusinessResponseEntity<String> getPeriodByType(@RequestBody AutoMergeDTO var1);
}

