/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.type;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadClient;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ReportDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadSchemeDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadDataService;
import com.jiuqi.gcreport.reportdatasync.service.impl.ReportDataSyncUploadServiceImpl;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReportDataSyncScheduler
implements ISyncTypeScheduler {
    @Autowired
    private ReportDataSyncUploadSchemeDao uploadSchemeDao;
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private ReportDataSyncUploadServiceImpl uploadService;
    @Autowired
    private ReportDataSyncServerListService serverListService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataSyncUploadDataService uploadDataService;
    @Autowired
    private ReportDataSyncUploadClient uploadClient;

    @Override
    public String code() {
        return SyncTypeEnums.REPORTDATA.getCode();
    }

    @Override
    public String name() {
        return SyncTypeEnums.REPORTDATA.getName();
    }

    @Override
    public List<ReportDataSyncServerInfoVO> getServerInfoVOList() {
        Optional<ReportDataSyncServerInfoVO> serverInfoVOS = this.serverListService.listServerInfos().stream().filter(v -> Boolean.TRUE.equals(v.getStartFlag()) && v.getSyncType() != null && v.getSyncType().contains(this.code())).findFirst();
        if (!serverInfoVOS.isPresent()) {
            throw new BusinessRuntimeException("\u76ee\u6807\u670d\u52a1\u7ba1\u7406\u4e2d\uff0c\u540c\u6b65\u7c7b\u578b\u4e3a" + this.name() + "\u7684\u8bb0\u5f55\u4e0d\u5b58\u5728");
        }
        return new ArrayList<ReportDataSyncServerInfoVO>(Arrays.asList(serverInfoVOS.get()));
    }

    @Override
    public MultilevelSyncContext buildContext(String info) {
        String periodStrTitle;
        String adjustCode;
        String periodValue;
        TaskDefine taskDefine;
        ReportDataContext context = new ReportDataContext();
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)info));
        if (uploadSchemeEO == null) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230id\u4e3a" + info + "\u7684\u6570\u636e\u4e0a\u4f20\u65b9\u6848");
        }
        ArrayList<String> logs = new ArrayList<String>();
        String taskId = uploadSchemeEO.getTaskId();
        try {
            if (uploadSchemeEO.getApplicationMode() == null || uploadSchemeEO.getApplicationMode() == 1) {
                taskDefine = this.taskDefineService.queryTaskDefine(taskId);
                PeriodType taskPeriodType = taskDefine.getPeriodType();
                periodValue = ReportDataSyncDataUtils.getPeriodValue(uploadSchemeEO.getPeriodType(), uploadSchemeEO.getPeriodStr(), taskPeriodType);
                adjustCode = uploadSchemeEO.getAdjustCode();
                periodStrTitle = uploadSchemeEO.getPeriodStrTitle() != null && !uploadSchemeEO.getPeriodStrTitle().isEmpty() ? uploadSchemeEO.getPeriodStrTitle() : null;
            } else {
                String mergeDataParams = uploadSchemeEO.getContent();
                ReportDataSyncUploadSchemeVO uploadSchemeVO = (ReportDataSyncUploadSchemeVO)JsonUtils.readValue((String)mergeDataParams, ReportDataSyncUploadSchemeVO.class);
                ReportDataParam reportData = uploadSchemeVO.getReportData();
                if (reportData != null && !StringUtils.isEmpty((String)reportData.getTaskId())) {
                    taskId = reportData.getTaskId();
                    taskDefine = this.taskDefineService.queryTaskDefine(taskId);
                    PeriodType taskPeriodType = taskDefine.getPeriodType();
                    periodStrTitle = reportData.getPeriodStrTitle() != null && !reportData.getPeriodStrTitle().isEmpty() ? reportData.getPeriodStrTitle() : null;
                    adjustCode = reportData.getAdjustCode();
                    if (StringUtils.isEmpty((String)reportData.getPeriodStr()) && null != reportData.getPeriodOffset()) {
                        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((int)taskPeriodType.type(), (int)reportData.getPeriodOffset());
                        periodValue = currentPeriod.toString();
                        periodStrTitle = null;
                    } else {
                        periodValue = reportData.getPeriodStr();
                    }
                } else {
                    taskDefine = null;
                    periodValue = null;
                    adjustCode = null;
                    periodStrTitle = null;
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        String uploadSnId = UUIDOrderUtils.newUUIDStr();
        ReportDataSyncUploadLogEO uploadLogEO = new ReportDataSyncUploadLogEO();
        uploadLogEO.setId(uploadSnId);
        uploadLogEO.setSyncVersion(DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmssSSS"));
        uploadLogEO.setTaskId(uploadSchemeEO.getTaskId());
        uploadLogEO.setTaskTitle(taskDefine == null ? uploadSchemeEO.getTitle() : taskDefine.getTitle());
        uploadLogEO.setTaskCode(taskDefine == null ? null : taskDefine.getTaskCode());
        uploadLogEO.setUploadSchemeId(uploadSchemeEO.getId());
        uploadLogEO.setPeriodValue(periodValue);
        uploadLogEO.setAdjustCode(StringUtils.isEmpty((String)adjustCode) ? "0" : adjustCode);
        if (periodStrTitle != null && !periodStrTitle.isEmpty()) {
            uploadLogEO.setPeriodStr(periodStrTitle);
        } else {
            uploadLogEO.setPeriodStr(periodValue == null ? null : ReportDataSyncUtils.getPeriodTitle(periodValue));
        }
        NpContext npContext = NpContextHolder.getContext();
        uploadLogEO.setUploadUsername(npContext.getUser().getFullname());
        uploadLogEO.setUploadUserId(npContext.getUserId());
        uploadLogEO.setUploadStatus(UploadStatusEnum.UPLOADING.getCode());
        uploadLogEO.setUploadTime(new Date());
        context.setLogs(logs);
        context.setUploadLogEO(uploadLogEO);
        context.setUploadSchemeEO(uploadSchemeEO);
        context.setTaskId(taskDefine == null ? null : taskDefine.getKey());
        context.setType(SyncTypeEnums.REPORTDATA);
        return context;
    }

    @Override
    public ReportSyncFileDTO exportFileToOss(MultilevelSyncContext envContext) {
        ReportDataContext context = (ReportDataContext)envContext;
        ReportDataSyncUploadLogEO uploadLogEO = context.getUploadLogEO();
        uploadLogEO.setSyncDataAttachId(context.getSn());
        String taskId = context.getTaskId();
        List<String> logs = context.getLogs();
        try {
            TaskDefine taskDefine = taskId == null ? null : this.taskDefineService.queryTaskDefine(taskId);
            ReportDataSyncDataUtils.exportDataToOss(taskDefine, context.getUploadSchemeEO(), uploadLogEO, logs);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        fileDTO.setMainFileId(uploadLogEO.getSyncDataAttachId());
        fileDTO.setMainFile((MultipartFile)this.commonFileService.queryOssFileByFileKey(fileDTO.getMainFileId()));
        return fileDTO;
    }

    @Override
    public void afterExport(MultilevelSyncContext syncContext) {
        ReportDataContext context = (ReportDataContext)syncContext;
        List<String> logs = context.getLogs();
        ReportDataSyncUploadLogEO uploadLogEO = context.getUploadLogEO();
        uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
        this.uploadService.insertUploadLogAndCommitLog(uploadLogEO);
    }

    @Override
    public boolean importData(MultilevelImportContext importContext) {
        ReportSyncFileDTO fileDTO = importContext.getFileDTO();
        MultipartFile multipartFile = fileDTO.getMainFile();
        ReportFileFormat fileFormat = importContext.getFileFormat();
        boolean result = false;
        try {
            if (fileFormat.equals((Object)ReportFileFormat.NRD) || fileFormat.equals((Object)ReportFileFormat.JIO)) {
                result = this.uploadDataService.importReportDataFile(multipartFile, null);
            } else {
                ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((String)importContext.getImportInfo(), ReportDataSyncUploadDataTaskVO.class);
                result = this.uploadDataService.importReportDataFile(multipartFile, uploadDataTaskVO, null);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    @Override
    public boolean isSync() {
        return true;
    }

    @Override
    public boolean multilevelReturn(MultilevelReturnContext context) {
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((String)context.getInfoJson(), ReportDataSyncUploadDataTaskVO.class);
        return (Boolean)this.uploadClient.rejectReportData(uploadDataTaskVO).getData();
    }
}

