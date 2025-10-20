/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient;
import com.jiuqi.gcreport.reportdatasync.config.ReportDataSyncConfig;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerInfoService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtil;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ReportDataSyncUploadController
implements ReportDataSyncUploadClient {
    @Autowired
    private ReportDataSyncUploadService reportDataSyncUploadService;
    @Autowired
    private ReportDataSyncConfig reportDataSyncConfig;
    @Autowired
    private ReportDataSyncServerInfoService reportDataSyncServerInfoService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;
    @Autowired
    private ReportDataSyncServerListService serverListService;

    public BusinessResponseEntity<String> saveUploadScheme(ReportDataSyncUploadSchemeVO uploadSchemeVO) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.saveUploadScheme(uploadSchemeVO));
    }

    public BusinessResponseEntity<Boolean> deleteUploadScheme(String uploadSchemeId) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.deleteUploadScheme(uploadSchemeId));
    }

    public BusinessResponseEntity<String> updateUploadScheme(ReportDataSyncUploadSchemeVO uploadSchemeVO) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.updateUploadScheme(uploadSchemeVO));
    }

    public BusinessResponseEntity<String> queryUploadSchemePeriodTitle(String uploadSchemeId) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.queryUploadSchemePeriodTitle(uploadSchemeId));
    }

    public BusinessResponseEntity<Boolean> uploadReportData(ReportDataParam reportData) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.uploadReportData(reportData));
    }

    public BusinessResponseEntity<Boolean> isExistInputTable(@PathVariable(value="uploadSchemeId") String uploadSchemeId) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.isExistInputTable(uploadSchemeId));
    }

    public BusinessResponseEntity<Boolean> rejectReportData(ReportDataSyncUploadDataTaskVO uploadDataTaskVO) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.rejectReportData(uploadDataTaskVO));
    }

    public BusinessResponseEntity<ReportDataSyncUploadSchemeVO> getUploadSchemeById(String uploadSchemeId) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.getUploadSchemeById(uploadSchemeId));
    }

    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllUploadScheme() {
        return BusinessResponseEntity.ok(this.reportDataSyncUploadService.listAllUploadScheme());
    }

    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllUploadSchemeTree() {
        return BusinessResponseEntity.ok(this.reportDataSyncUploadService.listAllUploadSchemeTree());
    }

    public BusinessResponseEntity<List<ReportDataSyncUploadSchemeVO>> listAllSchemeGroupTree() {
        return BusinessResponseEntity.ok(this.reportDataSyncUploadService.listAllSchemeGroupTree());
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadLogVO>> listUploadLogsBySchemeId(ReportDataSyncUploadLogQueryParamVO param) {
        return BusinessResponseEntity.ok(this.reportDataSyncUploadService.listUploadLogsBySchemeId(param));
    }

    public String getUploadDataGroups() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList paramSyncGroups = new ArrayList();
        HashMap<String, String> group = new HashMap<String, String>();
        group.put("key", "taskList");
        group.put("title", "\u4efb\u52a1\u5217\u8868");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "invest");
        group.put("title", "\u6295\u8d44\u53f0\u8d26");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "asset");
        group.put("title", "\u8d44\u4ea7\u53f0\u8d26");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "lessor");
        group.put("title", "\u878d\u8d44\u79df\u8d41\u53f0\u8d26");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "offsetData");
        group.put("title", "\u62b5\u9500\u5206\u5f55");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "offsetInitData");
        group.put("title", "\u62b5\u9500\u521d\u59cb");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "conversionRateData");
        group.put("title", "\u6298\u7b97\u6c47\u7387");
        paramSyncGroups.add(group);
        try {
            return mapper.writeValueAsString(paramSyncGroups);
        }
        catch (JsonProcessingException e) {
            throw new BusinessRuntimeException("\u6570\u636e\u540c\u6b65\u529f\u80fd\u83b7\u53d6\u5c55\u793a\u5206\u7ec4\u5931\u8d25");
        }
    }

    public String getUploadDataFileFormat() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList paramSyncGroups = new ArrayList();
        HashMap<String, String> group = new HashMap<String, String>();
        group.put("key", "nrd");
        group.put("title", "nrd");
        paramSyncGroups.add(group);
        group = new HashMap();
        group.put("key", "jio");
        group.put("title", "jio");
        paramSyncGroups.add(group);
        try {
            return mapper.writeValueAsString(paramSyncGroups);
        }
        catch (JsonProcessingException e) {
            throw new BusinessRuntimeException("\u6570\u636e\u540c\u6b65\u529f\u80fd\u83b7\u53d6\u5c55\u793a\u5206\u7ec4\u5931\u8d25");
        }
    }

    public BusinessResponseEntity<Boolean> isParamAllModal() {
        Boolean paramAllModal = this.reportDataSyncConfig.getParamAllModal();
        return BusinessResponseEntity.ok((Object)(paramAllModal == null ? false : paramAllModal));
    }

    public BusinessResponseEntity<Boolean> enableDataImport(String taskId) {
        Boolean pd = null;
        try {
            ReportDataSyncServerInfoVO serverInfoVO = this.serverListService.listServerInfos().stream().filter(v -> v.getSyncType() != null && v.getSyncType().contains(SyncTypeEnums.REPORTDATA.getCode())).findFirst().get();
            String type = serverInfoVO.getSyncMethod();
            MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
            pd = extendService.enableDataImport(serverInfoVO, taskId);
            if (pd == null) {
                throw new BusinessRuntimeException("\u4e0a\u7ea7\u83b7\u53d6\u662f\u5426\u5141\u8bb8\u5f3a\u5236\u4e0a\u62a5\u5931\u8d25");
            }
        }
        catch (Exception e) {
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskId);
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            pd = flowsSetting.isUnitSubmitForForce();
        }
        return BusinessResponseEntity.ok((Object)pd);
    }

    public BusinessResponseEntity<String> getSchemeIdByPeriodAndTask(String taskId, String periodStr) {
        return BusinessResponseEntity.ok((Object)ReportDataSyncUtil.getSchemeIdByPeriodAndTask(taskId, periodStr));
    }

    public BusinessResponseEntity<String> getSchemeIdByPeriodOffsetAndTask(String taskId, Integer periodOffSet) {
        return BusinessResponseEntity.ok((Object)ReportDataSyncUtil.getSchemeIdByPeriodOffsetAndTask(taskId, periodOffSet));
    }

    public BusinessResponseEntity<Boolean> modifyLoadingResults(ReportsyncDataLoadParam reportsyncDataLoadParam) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.modifyLoadingResults(reportsyncDataLoadParam));
    }

    public BusinessResponseEntity<Boolean> uploadToFtp(ReportDataUploadToFtpVO ftpVO) {
        return BusinessResponseEntity.ok((Object)this.reportDataSyncUploadService.uploadToFtp(ftpVO));
    }

    public BusinessResponseEntity<String> getFileFormat() {
        return BusinessResponseEntity.ok((Object)ReportDataSyncUtil.getFileFormat());
    }

    public BusinessResponseEntity<List<Map<String, String>>> listAllMappingScheme() {
        return BusinessResponseEntity.ok(this.reportDataSyncUploadService.listAllMappingScheme());
    }
}

