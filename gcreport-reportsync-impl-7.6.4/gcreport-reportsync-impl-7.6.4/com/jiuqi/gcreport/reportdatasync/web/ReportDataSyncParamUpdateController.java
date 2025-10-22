/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamUpdateClient
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO
 *  feign.Request$Options
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamUpdateClient;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveTaskDao;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveTaskEO;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncMessageUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO;
import feign.Request;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
public class ReportDataSyncParamUpdateController
implements ReportDataSyncParamUpdateClient {
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataSyncReceiveTaskDao receiveTaskDao;

    public BusinessResponseEntity<Boolean> addTask(Request.Options options, String receiveTaskVOJson, MultipartFile[] syncAttachFiles) {
        ReportDataSyncReceiveTaskVO receiveTaskVO = (ReportDataSyncReceiveTaskVO)JsonUtils.readValue((String)receiveTaskVOJson, ReportDataSyncReceiveTaskVO.class);
        List<ReportDataSyncReceiveTaskEO> receiveTaskEOS = this.receiveTaskDao.listReceiveTaskBySyncVersion(receiveTaskVO.getTaskId(), receiveTaskVO.getSyncVersion());
        if (!CollectionUtils.isEmpty(receiveTaskEOS)) {
            throw new BusinessRuntimeException(String.format("\u4efb\u52a1\u3010%1$s\u3011\u7248\u672c\u3010%2$s\u3011\u7684\u4efb\u52a1\u53c2\u6570\u5df2\u5b58\u5728,\u65e0\u6cd5\u91cd\u590d\u540c\u6b65", receiveTaskVO.getTaskTitle(), receiveTaskVO.getSyncVersion()));
        }
        if (syncAttachFiles.length > 1) {
            this.commonFileService.uploadFileToOss(syncAttachFiles[0], receiveTaskVO.getSyncDesAttachId());
            this.commonFileService.uploadFileToOss(syncAttachFiles[1], receiveTaskVO.getSyncParamAttachId());
        } else {
            this.commonFileService.uploadFileToOss(syncAttachFiles[0], receiveTaskVO.getSyncParamAttachId());
        }
        Boolean isSuccess = this.paramUpdateService.addTaskAndCommit(receiveTaskVO);
        ReportDataSyncMessageUtils.sendParamRecieveMessage(receiveTaskVO);
        return BusinessResponseEntity.ok((Object)isSuccess);
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncReceiveTaskVO>> listAllTasks(Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.paramUpdateService.listAllTasks(pageSize, pageNum));
    }

    public BusinessResponseEntity<List<ReportDataSyncIssuedLogVO>> fetchTargetSyncParamTaskInfos() {
        return BusinessResponseEntity.ok(this.paramUpdateService.fetchTargetSyncParamTaskInfos());
    }

    public BusinessResponseEntity<Boolean> fetchTargetSyncParam(ReportDataSyncIssuedLogVO issuedLogVO) {
        return BusinessResponseEntity.ok((Object)this.paramUpdateService.fetchTargetSyncParam(issuedLogVO));
    }

    public BusinessResponseEntity<Boolean> updateReportParamsData(String receiveTaskId) {
        return BusinessResponseEntity.ok((Object)this.paramUpdateService.updateReportParamsData(receiveTaskId, false));
    }

    public BusinessResponseEntity<PageInfo<ReportDataSyncReceiveLogVO>> listReceiveTaskLogs(Integer pageSize, Integer pageNum) {
        return BusinessResponseEntity.ok(this.paramUpdateService.listReceiveTaskLogs(pageSize, pageNum));
    }

    public BusinessResponseEntity<List<ReportDataSyncReceiveLogItemVO>> listReceiveLogItemsByLogId(String syncLogInfoId) {
        return BusinessResponseEntity.ok(this.paramUpdateService.listReceiveLogItemsByLogId(syncLogInfoId));
    }

    public BusinessResponseEntity<List<ReportParamSyncTaskOptionVO>> listAllParamSyncTasks() {
        List<ReportParamSyncTaskOptionVO> paramSyncTasks = this.paramUpdateService.listAllParamSyncTasks();
        return BusinessResponseEntity.ok(paramSyncTasks);
    }

    public BusinessResponseEntity<List<Object>> listComparisonResults(String logId) {
        return BusinessResponseEntity.ok(this.paramUpdateService.listComparisonResults(logId));
    }
}

