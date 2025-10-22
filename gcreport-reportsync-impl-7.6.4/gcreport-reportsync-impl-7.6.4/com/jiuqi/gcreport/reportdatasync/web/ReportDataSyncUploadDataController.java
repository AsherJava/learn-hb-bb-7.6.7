/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.va.domain.org.OrgDO
 *  feign.Request$Options
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient;
import com.jiuqi.gcreport.reportdatasync.config.ReportDataSyncConfig;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadDataTaskDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadDataTaskEO;
import com.jiuqi.gcreport.reportdatasync.enums.CheckParamVOEnums;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadDataService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncMessageUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.va.domain.org.OrgDO;
import feign.Request;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
public class ReportDataSyncUploadDataController
implements ReportDataSyncUploadDataClient {
    @Autowired
    private ReportDataSyncUploadDataService uploadDataService;
    @Autowired
    private ReportDataSyncUploadDataTaskDao uploadDataTaskDao;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataSyncConfig reportDataSyncConfig;
    @Autowired
    private IRunTimeViewController runtimeView;

    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadDataTaskVO>> listAllExecutingTask(Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.uploadDataService.listAllExecutingTask(pageSize, pageNum));
    }

    public BusinessResponseEntity<Boolean> dataLoading(String receiveTaskId) {
        ReportDataSyncUploadDataTaskEO uploadDataTaskEO = (ReportDataSyncUploadDataTaskEO)this.uploadDataTaskDao.get((Serializable)((Object)receiveTaskId));
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadDataTaskEO.getPeriodStr());
        LogHelper.info((String)"\u5408\u5e76-\u4e0a\u4f20\u6570\u636e\u7ba1\u7406", (String)String.format("\u624b\u52a8\u88c5\u5165-%1$s\u4efb\u52a1%2$s\u65f6\u671f", uploadDataTaskEO.getTaskTitle(), periodTitle), (String)"");
        return BusinessResponseEntity.ok((Object)this.uploadDataService.dataLoading(receiveTaskId, false));
    }

    public BusinessResponseEntity<Boolean> stopTask(String receiveTaskId) {
        return BusinessResponseEntity.ok((Object)this.uploadDataService.stopTask(receiveTaskId));
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncUploadDataTaskVO>> listAllFinishedTask(Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.uploadDataService.listAllFinishedTask(pageSize, pageNum));
    }

    public BusinessResponseEntity<Boolean> rejectTask(ReportDataSyncRejectParams rejectParams) {
        return BusinessResponseEntity.ok((Object)this.uploadDataService.rejectTask(rejectParams));
    }

    public BusinessResponseEntity<Boolean> uploadReportDataSyncUploadDataTask(Request.Options options, String uploadDataTaskVOJson, MultipartFile[] uploadDataFiles) {
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((String)uploadDataTaskVOJson, ReportDataSyncUploadDataTaskVO.class);
        Boolean isSuccess = this.uploadDataService.saveUploadDataTaskAndCommit(uploadDataTaskVO);
        this.commonFileService.uploadFileToOss(uploadDataFiles[0], uploadDataTaskVO.getSyncDataAttachId());
        ReportDataSyncMessageUtils.sendUploadDataMessage(uploadDataTaskVO);
        NpContext context = NpContextHolder.getContext();
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                this.uploadDataService.dataLoading(uploadDataTaskVO.getId(), false);
            }
            catch (Throwable throwable) {
                NpContextHolder.clearContext();
                String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadDataTaskVO.getPeriodStr());
                LogHelper.info((String)"\u5408\u5e76-\u4e0a\u4f20\u6570\u636e\u7ba1\u7406", (String)String.format("\u81ea\u52a8\u88c5\u5165-%1$s\u4efb\u52a1%2$s\u65f6\u671f", uploadDataTaskVO.getTaskTitle(), periodTitle), (String)"");
                throw throwable;
            }
            NpContextHolder.clearContext();
            String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadDataTaskVO.getPeriodStr());
            LogHelper.info((String)"\u5408\u5e76-\u4e0a\u4f20\u6570\u636e\u7ba1\u7406", (String)String.format("\u81ea\u52a8\u88c5\u5165-%1$s\u4efb\u52a1%2$s\u65f6\u671f", uploadDataTaskVO.getTaskTitle(), periodTitle), (String)"");
        });
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<Boolean> checkUploadReportData(ReportDataSyncUploadCheckParamVO checkParamVO) {
        String msg;
        try {
            msg = this.uploadDataService.checkUploadReportData(checkParamVO);
        }
        catch (BusinessRuntimeException e) {
            return BusinessResponseEntity.error((Throwable)e);
        }
        if (msg != null && !msg.isEmpty()) {
            return BusinessResponseEntity.error((String)CheckParamVOEnums.PROFESSIONAL_UNIT_STATUS_CHECK.getCode(), (String)msg);
        }
        return BusinessResponseEntity.ok((Object)Boolean.TRUE);
    }

    public BusinessResponseEntity<String> uploadJioReportData(String uploadDataTaskVOJson, MultipartFile uploadDataFile) {
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((String)uploadDataTaskVOJson, ReportDataSyncUploadDataTaskVO.class);
        this.uploadDataService.uploadJioReportData(uploadDataFile, uploadDataTaskVO);
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadDataTaskVO.getPeriodStr());
        LogHelper.info((String)"\u5408\u5e76-\u4e0a\u4f20\u6570\u636e\u7ba1\u7406", (String)String.format("\u81ea\u52a8\u88c5\u5165-%1$s\u4efb\u52a1%2$s\u65f6\u671f", uploadDataTaskVO.getTaskTitle(), periodTitle), (String)"");
        return BusinessResponseEntity.ok((Object)"\u6570\u636e\u4e0a\u4f20\u6210\u529f");
    }

    public BusinessResponseEntity<Boolean> enableDataImportCheck(String taskId) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskId);
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        return BusinessResponseEntity.ok((Object)flowsSetting.isUnitSubmitForForce());
    }

    public BusinessResponseEntity<List<OrgDO>> getOrgDO(ReportDataCheckParam checkParam) {
        return BusinessResponseEntity.ok(this.uploadDataService.getOrgDO(checkParam));
    }

    public BusinessResponseEntity<Boolean> importFromFtp(ReportDataUploadToFtpVO ftpVO) {
        return BusinessResponseEntity.ok((Object)this.uploadDataService.importFromFtp(ftpVO));
    }

    public BusinessResponseEntity<Boolean> allowFetchReportData() {
        return BusinessResponseEntity.ok((Object)Boolean.TRUE.equals(this.reportDataSyncConfig.getAllowFetchReportData()));
    }

    public BusinessResponseEntity<Boolean> fetchReportData(Map<String, String> params) {
        return BusinessResponseEntity.ok((Object)this.uploadDataService.fetchReportData(params));
    }
}

