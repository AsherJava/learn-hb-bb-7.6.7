/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  nr.single.map.configurations.bean.SingleConfigInfo
 *  nr.single.map.data.service.SingleMappingService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.runner.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.reportdatasync.runner.dto.ReportDataSyncRunnerSystemHookDTO;
import com.jiuqi.gcreport.reportdatasync.runner.service.ReportDataSyncRunnerService;
import java.util.List;
import java.util.stream.Collectors;
import nr.single.map.configurations.bean.SingleConfigInfo;
import nr.single.map.data.service.SingleMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportDataSyncRunnerController {
    private static final String GC_API_BASE_PATH = "/api/gcreport/v1/datasync/";
    @Autowired
    private ReportDataSyncRunnerService reportDataSyncRunnerService;
    @Autowired
    private SingleMappingService mappingConfigService;

    @GetMapping(value={"/api/gcreport/v1/datasync/runner/systemHooks"})
    public BusinessResponseEntity<List<ReportDataSyncRunnerSystemHookDTO>> querySystemHooks() {
        List runnerSystemHooks = this.reportDataSyncRunnerService.getRunnerSystemHooks().stream().map(reportDataSyncRunnerSystemHook -> {
            ReportDataSyncRunnerSystemHookDTO reportDataSyncRunnerSystemHookDTO = new ReportDataSyncRunnerSystemHookDTO();
            reportDataSyncRunnerSystemHookDTO.setHookName(reportDataSyncRunnerSystemHook.getHookName());
            reportDataSyncRunnerSystemHookDTO.setHookTitle(reportDataSyncRunnerSystemHook.getHookTitle());
            reportDataSyncRunnerSystemHookDTO.setHookDescription(reportDataSyncRunnerSystemHook.getHookDescription());
            return reportDataSyncRunnerSystemHookDTO;
        }).collect(Collectors.toList());
        return BusinessResponseEntity.ok(runnerSystemHooks);
    }

    @GetMapping(value={"/api/gcreport/v1/datasync/runner/queryMappingByTaskKey/{taskKey}"})
    public BusinessResponseEntity<List<SingleConfigInfo>> queryMappingByTaskKey(@PathVariable(value="taskKey") String taskKey) {
        List configs = this.mappingConfigService.getAllMappingInTask(taskKey);
        return BusinessResponseEntity.ok((Object)configs);
    }
}

