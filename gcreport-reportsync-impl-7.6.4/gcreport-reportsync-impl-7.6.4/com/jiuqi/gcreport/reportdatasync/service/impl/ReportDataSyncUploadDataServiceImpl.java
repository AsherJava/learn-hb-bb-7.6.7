/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.extra.ftp.Ftp
 *  cn.hutool.extra.ssh.Sftp
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.org.api.period.YearPeriodDO
 *  com.jiuqi.gcreport.org.api.period.YearPeriodUtil
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataLoadingProgressDataDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  com.jiuqi.np.asynctask.AsyncTask
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.dataentry.bean.AsyncUploadParam
 *  com.jiuqi.nr.dataentry.bean.ImportResultObject
 *  com.jiuqi.nr.dataentry.bean.UploadParam
 *  com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeFormSchemeDefineService
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  nr.single.client.bean.JIOImportResultObject
 *  nr.single.client.internal.service.upload.UploadJioServiceImpl
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.FilenameUtils
 *  org.apache.shiro.util.Assert
 *  org.springframework.mock.web.MockMultipartFile
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ssh.Sftp;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.org.api.period.YearPeriodDO;
import com.jiuqi.gcreport.org.api.period.YearPeriodUtil;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadDataTaskDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadLogDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataLoadingProgressDataDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadDataTaskEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamSyncService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadDataService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncFlowUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataCheckParam;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncRejectParams;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import com.jiuqi.np.asynctask.AsyncTask;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.dataentry.bean.AsyncUploadParam;
import com.jiuqi.nr.dataentry.bean.ImportResultObject;
import com.jiuqi.nr.dataentry.bean.UploadParam;
import com.jiuqi.nr.dataentry.monitor.SimpleAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeFormSchemeDefineService;
import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import nr.single.client.bean.JIOImportResultObject;
import nr.single.client.internal.service.upload.UploadJioServiceImpl;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReportDataSyncUploadDataServiceImpl
implements ReportDataSyncUploadDataService {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncUploadDataServiceImpl.class);
    @Autowired
    private ReportDataSyncUploadDataTaskDao uploadDataTaskDao;
    @Autowired
    private ReportDataSyncUploadLogDao uploadLogDao;
    @Autowired
    private ReportDataSyncServerListService serverListService;
    @Autowired
    private ReportDataSyncParamSyncService paramSyncService;
    @Autowired
    private ReportDataSyncUploadDataService uploadDataService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private FileService fileService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private ProgressService<ReportDataLoadingProgressDataDTO, List<String>> progressService;
    @Autowired
    private RunTimeFormSchemeDefineService formSchemeDefineService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private UploadJioServiceImpl uploadJioService;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;

    @Override
    public Boolean saveUploadDataTask(ReportDataSyncUploadDataTaskVO taskVO) {
        ReportDataSyncUploadDataTaskEO reportDataSyncUploadDataTaskEO = this.convertVO2EO(taskVO);
        this.uploadDataTaskDao.save(reportDataSyncUploadDataTaskEO);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public Boolean saveUploadDataTaskAndCommit(ReportDataSyncUploadDataTaskVO taskVO) {
        return this.saveUploadDataTask(taskVO);
    }

    @Override
    public PageInfo<ReportDataSyncUploadDataTaskVO> listAllExecutingTask(Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncUploadDataTaskEO> pageInfo = this.uploadDataTaskDao.listAllExecutingTask(pageSize, pageNum);
        List<ReportDataSyncUploadDataTaskVO> vos = this.convertEO2VO(pageInfo.getList());
        vos.stream().forEach(vo -> {
            ProgressData progressData = this.progressService.queryProgressData(vo.getId());
            if (progressData != null && progressData.getProgressValue() < 1.0 && progressData.getProgressValue() > 0.0) {
                vo.setShowProgress(Boolean.valueOf(true));
                vo.setProgress(Double.valueOf(progressData.getProgressValue()));
            }
        });
        PageInfo pageInfoVO = PageInfo.of(vos, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public Boolean dataLoading(String receiveTaskId, boolean islxdr) {
        ReportDataLoadingProgressDataDTO progressDataDTO = new ReportDataLoadingProgressDataDTO(receiveTaskId);
        this.progressService.createProgressData((ProgressData)progressDataDTO);
        ReportDataSyncUploadDataTaskEO uploadDataTaskEO = (ReportDataSyncUploadDataTaskEO)this.uploadDataTaskDao.get((Serializable)((Object)receiveTaskId));
        ReportDataSyncServerInfoVO serverInfoVO = this.serverListService.queryServerInfoByOrgCode(uploadDataTaskEO.getOrgCode());
        if (serverInfoVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + uploadDataTaskEO.getOrgCode() + "]\u672a\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u529f\u80fd\u70b9\u542f\u7528\uff0c\u4e0d\u652f\u6301\u4e0a\u4f20\u3002");
        }
        progressDataDTO.setProgressValueAndRefresh(0.1);
        if (!Boolean.FALSE.equals(serverInfoVO.getSupportAll())) {
            return this.dataLoading(uploadDataTaskEO, progressDataDTO, serverInfoVO);
        }
        this.dataLoadingJioReortData(uploadDataTaskEO);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Boolean dataLoading(ReportDataSyncUploadDataTaskEO uploadDataTaskEO, ReportDataLoadingProgressDataDTO progressDataDTO, ReportDataSyncServerInfoVO serverInfoVO) {
        String syncDataAttachId = uploadDataTaskEO.getSyncDataAttachId();
        TaskStatusEnum syncStatus = TaskStatusEnum.EXECUTING;
        String syncLogInfo = "\u6570\u636e\u6b63\u5728\u88c5\u5165...";
        progressDataDTO.setProgressValueAndRefresh(0.2);
        boolean applicationMode = true;
        boolean standardUpload = true;
        try {
            uploadDataTaskEO.setStatus(syncStatus.getCode());
            uploadDataTaskEO.setSyncLogInfo(syncLogInfo);
            this.uploadDataTaskDao.update((BaseEntity)uploadDataTaskEO);
            progressDataDTO.setProgressValueAndRefresh(0.3);
            ArrayList<String> logs = new ArrayList<String>();
            if (serverInfoVO.getFileFormat().equals(ReportFileFormat.YG.getCode())) {
                ReportDataSyncDataUtils.importHeterogeneousData(uploadDataTaskEO, syncDataAttachId);
            } else {
                applicationMode = ReportDataSyncDataUtils.importData(uploadDataTaskEO.getId(), uploadDataTaskEO.getOrgCode(), uploadDataTaskEO.getTaskTitle(), syncDataAttachId, logs, serverInfoVO);
            }
            progressDataDTO.setProgressValueAndRefresh(0.8);
            syncStatus = TaskStatusEnum.SUCCESS;
            syncLogInfo = String.join((CharSequence)" ", logs) + "\u6570\u636e\u88c5\u5165\u5b8c\u6210";
        }
        catch (Throwable e) {
            syncStatus = TaskStatusEnum.ERROR;
            standardUpload = false;
            syncLogInfo = "\u6570\u636e\u88c5\u5165\u5931\u8d25\uff1a" + e.getMessage();
            LOGGER.error(syncLogInfo, e);
        }
        finally {
            uploadDataTaskEO.setSyncTime(new Date());
            uploadDataTaskEO.setStatus(syncStatus.getCode());
            uploadDataTaskEO.setSyncLogInfo(syncLogInfo);
            this.uploadDataTaskDao.update((BaseEntity)uploadDataTaskEO);
            this.insertDataUpdateLog(uploadDataTaskEO);
            try {
                this.returnToSubordinates(uploadDataTaskEO);
            }
            catch (Exception e) {
                LOGGER.error("\u88c5\u5165\u7ed3\u679c\u8fd4\u56de\u4e0b\u7ea7\u51fa\u9519\u95ee\u9898\uff1a" + e.getMessage());
            }
            progressDataDTO.setProgressValueAndRefresh(1.0);
        }
        if (applicationMode && standardUpload) {
            NpContext context = NpContextHolder.getContext();
            CompletableFuture.runAsync(() -> {
                try {
                    NpContextHolder.setContext((NpContext)context);
                    ReportDataSyncFlowUtils.executeDataFlow(uploadDataTaskEO.getTaskCode(), uploadDataTaskEO.getOrgCode(), uploadDataTaskEO.getPeriodValue(), UploadStateEnum.ACTION_UPLOAD, "\u4e0b\u7ea7\u5355\u4f4d\u4e0a\u62a5", uploadDataTaskEO.getSelectAdjust());
                }
                finally {
                    NpContextHolder.clearContext();
                }
            });
        }
        return Boolean.TRUE;
    }

    private void insertDataUpdateLog(ReportDataSyncUploadDataTaskEO uploadDataTaskEO) {
        ReportDataSyncUploadLogEO reportDataSyncUploadLogEO = new ReportDataSyncUploadLogEO();
        BeanUtils.copyProperties((Object)uploadDataTaskEO, (Object)reportDataSyncUploadLogEO);
        reportDataSyncUploadLogEO.setId(UUIDUtils.newUUIDStr());
        reportDataSyncUploadLogEO.setUploadStatus(UploadStatusEnum.REPORTED.getCode());
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user != null) {
            reportDataSyncUploadLogEO.setUploadUserId(user.getId());
            reportDataSyncUploadLogEO.setUploadUsername(user.getName());
        }
        this.uploadLogDao.add((BaseEntity)reportDataSyncUploadLogEO);
    }

    @Override
    public Boolean stopTask(String receiveTaskId) {
        ReportDataSyncUploadDataTaskEO uploadDataTaskEO = (ReportDataSyncUploadDataTaskEO)this.uploadDataTaskDao.get((Serializable)((Object)receiveTaskId));
        uploadDataTaskEO.setStatus(TaskStatusEnum.STOP.getCode());
        this.uploadDataTaskDao.update((BaseEntity)uploadDataTaskEO);
        return Boolean.TRUE;
    }

    private Boolean returnToSubordinates(ReportDataSyncUploadDataTaskEO uploadDataTaskEO) {
        List<ReportDataSyncServerInfoVO> reportDataSyncServerInfoVOS = this.serverListService.listServerInfos();
        if (CollectionUtils.isEmpty(reportDataSyncServerInfoVOS)) {
            return Boolean.TRUE;
        }
        Optional<ReportDataSyncServerInfoVO> serverInfoVOOptional = reportDataSyncServerInfoVOS.stream().filter(vo -> vo.getOrgCode().equals(uploadDataTaskEO.getOrgCode())).findAny();
        if (!serverInfoVOOptional.isPresent() || Boolean.FALSE.equals(serverInfoVOOptional.get().getSupportAll())) {
            return Boolean.TRUE;
        }
        ReportsyncDataLoadParam reportsyncDataLoadParam = new ReportsyncDataLoadParam();
        reportsyncDataLoadParam.setLoadStatus(uploadDataTaskEO.getStatus());
        reportsyncDataLoadParam.setLoadMsg(uploadDataTaskEO.getSyncLogInfo());
        reportsyncDataLoadParam.setPeriodStr(uploadDataTaskEO.getPeriodValue());
        reportsyncDataLoadParam.setTaskCode(uploadDataTaskEO.getTaskCode());
        reportsyncDataLoadParam.setId(uploadDataTaskEO.getId());
        reportsyncDataLoadParam.setAdjustCode(uploadDataTaskEO.getSelectAdjust());
        ReportDataSyncServerInfoVO serverInfoVO = serverInfoVOOptional.get();
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
        if (null == extendService) {
            return Boolean.TRUE;
        }
        return extendService.modifyLoadingResults(serverInfoVO, reportsyncDataLoadParam);
    }

    @Override
    public PageInfo<ReportDataSyncUploadDataTaskVO> listAllFinishedTask(Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncUploadDataTaskEO> pageInfo = this.uploadDataTaskDao.listAllFinishedTask(pageSize, pageNum);
        List<ReportDataSyncUploadDataTaskVO> vos = this.convertEO2VO(pageInfo.getList());
        PageInfo pageInfoVO = PageInfo.of(vos, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public Boolean rejectTask(ReportDataSyncRejectParams rejectParams) {
        String ids = rejectParams.getIds();
        Assert.notNull((Object)ids, (String)"\u4efb\u52a1id\u4e0d\u80fd\u4e3a\u7a7a\u3002");
        List<ReportDataSyncServerInfoVO> reportDataSyncServerInfoVOS = this.serverListService.listServerInfos();
        if (CollectionUtils.isEmpty(reportDataSyncServerInfoVOS)) {
            return Boolean.TRUE;
        }
        List<String> receiveTaskIds = Arrays.asList(ids.split(","));
        List<ReportDataSyncUploadDataTaskEO> receiveTasks = this.uploadDataTaskDao.listDataUploadTasksByWhereSql(SqlUtils.getConditionOfIdsUseOr(receiveTaskIds, (String)"id"));
        receiveTasks.stream().forEach(receiveTask -> {
            String orgCode = receiveTask.getOrgCode();
            Optional<ReportDataSyncServerInfoVO> serverInfoVOOptional = reportDataSyncServerInfoVOS.stream().filter(vo -> vo.getOrgCode().equals(orgCode)).findAny();
            if (!serverInfoVOOptional.isPresent() || Boolean.FALSE.equals(serverInfoVOOptional.get().getSupportAll())) {
                return;
            }
            ReportDataSyncUploadDataTaskVO uploadDataTaskVO = this.convertEO2VO((ReportDataSyncUploadDataTaskEO)((Object)receiveTask));
            ContextUser user = NpContextHolder.getContext().getUser();
            uploadDataTaskVO.setAdjustCode(receiveTask.getSelectAdjust());
            uploadDataTaskVO.setUploadUserId(user.getId());
            uploadDataTaskVO.setUploadUsername(user.getFullname());
            uploadDataTaskVO.setRejectMsg(rejectParams.getRejectMsg());
            uploadDataTaskVO.setUploadTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
            ReportDataSyncServerInfoVO serverInfoVO = serverInfoVOOptional.get();
            String type = serverInfoVO.getSyncMethod();
            MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
            if (null != extendService) {
                extendService.rejectReportData(serverInfoVO, com.jiuqi.common.base.util.JsonUtils.writeValueAsString((Object)uploadDataTaskVO), SyncTypeEnums.REPORTDATA);
            }
            receiveTask.setStatus(TaskStatusEnum.REJECTED.getCode());
            this.uploadDataTaskDao.update((BaseEntity)receiveTask);
        });
        return Boolean.TRUE;
    }

    private List<ReportDataSyncUploadDataTaskVO> convertEO2VO(List<ReportDataSyncUploadDataTaskEO> uploadDataTaskEos) {
        return uploadDataTaskEos.stream().map(dataTask -> this.convertEO2VO((ReportDataSyncUploadDataTaskEO)((Object)dataTask))).collect(Collectors.toList());
    }

    private ReportDataSyncUploadDataTaskVO convertEO2VO(ReportDataSyncUploadDataTaskEO dataTask) {
        ReportDataSyncUploadDataTaskVO dataTaskVO = new ReportDataSyncUploadDataTaskVO();
        Date syncTime = dataTask.getSyncTime();
        Date uploadTime = dataTask.getUploadTime();
        BeanUtils.copyProperties((Object)dataTask, dataTaskVO);
        if (!ObjectUtils.isEmpty(syncTime)) {
            dataTaskVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        if (!ObjectUtils.isEmpty(uploadTime)) {
            dataTaskVO.setUploadTime(DateUtils.format((Date)uploadTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        if (null == dataTaskVO.getPeriodStr()) {
            dataTaskVO.setPeriodStr("--");
        }
        dataTaskVO.setStatus(dataTask.getStatus());
        return dataTaskVO;
    }

    private ReportDataSyncUploadDataTaskEO convertVO2EO(ReportDataSyncUploadDataTaskVO uploadDataTaskVO) {
        ReportDataSyncUploadDataTaskEO uploadDataTaskEo = new ReportDataSyncUploadDataTaskEO();
        BeanUtils.copyProperties(uploadDataTaskVO, (Object)uploadDataTaskEo);
        if (!ObjectUtils.isEmpty(uploadDataTaskVO.getSyncTime())) {
            uploadDataTaskEo.setSyncTime(DateUtils.parse((String)uploadDataTaskVO.getSyncTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        }
        if (!ObjectUtils.isEmpty(uploadDataTaskVO.getUploadTime())) {
            uploadDataTaskEo.setUploadTime(DateUtils.parse((String)uploadDataTaskVO.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        }
        uploadDataTaskEo.setStatus(TaskStatusEnum.WAIT.getCode());
        uploadDataTaskEo.setSelectAdjust(uploadDataTaskVO.getAdjustCode());
        return uploadDataTaskEo;
    }

    @Override
    public String checkUploadReportData(ReportDataSyncUploadCheckParamVO checkParamVO) {
        TaskDefine taskDefine;
        if (null != checkParamVO.getTaskCode() && (taskDefine = this.runTimeViewController.queryTaskDefineByCode(checkParamVO.getTaskCode())) == null) {
            throw new BusinessRuntimeException("\u8fde\u63a5\u7684" + checkParamVO.getTargetUrl() + "\u670d\u52a1\u6ca1\u6709" + checkParamVO.getTaskTitle() + "\u4efb\u52a1,\u8bf7\u786e\u8ba4\u540e\u518d\u6b21\u4e0a\u4f20");
        }
        ArrayList msg = new ArrayList();
        List schemeKeys = checkParamVO.getSchemeKeys();
        if (CollectionUtils.isEmpty((Collection)schemeKeys) || checkParamVO.getUnitVosCheck() == null || checkParamVO.getUnitVosCheck().equals(Boolean.FALSE)) {
            return null;
        }
        schemeKeys.forEach(schemeKey -> {
            FormSchemeDefine formSchemeStream = this.runTimeViewController.getFormScheme(schemeKey);
            if (formSchemeStream == null) {
                throw new BusinessRuntimeException("\u627e\u4e0d\u5230id\u4e3a\uff1a" + schemeKey + "\u7684\u62a5\u8868\u65b9\u6848");
            }
        });
        List orgCodes = checkParamVO.getOrgCodes();
        if (orgCodes != null) {
            for (String orgCode : orgCodes) {
                schemeKeys.forEach(schemeKey -> {
                    ActionStateBean actionState = ReportDataSyncFlowUtils.queryUnitState(schemeKey, orgCode, checkParamVO.getPeriodStrValue(), checkParamVO.getSelectAdjust());
                    if (UploadState.UPLOADED.name().equals(actionState.getCode())) {
                        msg.add("\u4e0a\u62a5\u4efb\u52a1[" + checkParamVO.getTaskTitle() + "]-\u5355\u4f4d\u4ee3\u7801[" + orgCode + "]-\u65f6\u671f[" + checkParamVO.getPeriodStr() + "]\u5728\u63a5\u6536\u7aef\u6570\u636e\u72b6\u6001\u4e3a\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u4e0a\u62a5\u3002");
                    }
                });
            }
            return msg.isEmpty() ? null : String.join((CharSequence)";", msg);
        }
        List unitVos = checkParamVO.getUnitVos();
        if (unitVos != null) {
            for (int i = 0; i < unitVos.size(); ++i) {
                String orgCode = ((GcOrgCacheVO)unitVos.get(i)).getCode();
                String orgTitle = ((GcOrgCacheVO)unitVos.get(i)).getTitle();
                schemeKeys.forEach(schemeKey -> {
                    ActionStateBean actionState = ReportDataSyncFlowUtils.queryUnitState(schemeKey, orgCode, checkParamVO.getPeriodStrValue(), checkParamVO.getSelectAdjust());
                    if (UploadState.UPLOADED.name().equals(actionState.getCode())) {
                        msg.add("\u4e0a\u62a5\u4efb\u52a1[" + checkParamVO.getTaskTitle() + "]-\u5355\u4f4d[" + orgTitle + "]-\u65f6\u671f[" + checkParamVO.getPeriodStr() + "]\u5728\u63a5\u6536\u7aef\u6570\u636e\u72b6\u6001\u4e3a\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u4e0a\u62a5\u3002");
                    }
                });
            }
        }
        return msg.isEmpty() ? null : String.join((CharSequence)";", msg);
    }

    private void dataLoadingJioReortData(ReportDataSyncUploadDataTaskEO uploadDataTaskEO) {
        CommonFileDTO syncDataAttachFile = this.commonFileService.queryOssFileByFileKey(uploadDataTaskEO.getSyncDataAttachId());
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = new ReportDataSyncUploadDataTaskVO();
        BeanUtils.copyProperties((Object)uploadDataTaskEO, uploadDataTaskVO);
        this.uploadJioReportData((MultipartFile)syncDataAttachFile, uploadDataTaskVO);
    }

    @Override
    public String uploadJioReportData(MultipartFile file, ReportDataSyncUploadDataTaskVO uploadDataTaskVO) {
        Boolean manualUpload = !StringUtils.isEmpty((String)uploadDataTaskVO.getId());
        UploadParam uploadParam = ReportDataSyncDataUtils.getUploadParam(uploadDataTaskVO);
        this.checkOrgUploadState(uploadDataTaskVO, uploadParam);
        if (!manualUpload.booleanValue()) {
            this.uploadDataService.saveUploadDataTaskAndCommit(uploadDataTaskVO);
            this.commonFileService.uploadFileToOss(file, uploadDataTaskVO.getSyncDataAttachId());
        }
        NpContext context = NpContextHolder.getContext();
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                this.executeImportData(uploadDataTaskVO, uploadParam);
            }
            finally {
                NpContextHolder.clearContext();
            }
        });
        return "\u6570\u636e\u4e0a\u4f20\u6210\u529f";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeImportData(ReportDataSyncUploadDataTaskVO uploadDataTaskVO, UploadParam uploadParam) {
        ReportDataSyncUploadDataTaskEO uploadDataTaskEO = (ReportDataSyncUploadDataTaskEO)this.uploadDataTaskDao.get((Serializable)((Object)uploadDataTaskVO.getId()));
        TaskStatusEnum syncStatus = TaskStatusEnum.EXECUTING;
        String syncLogInfo = "\u6570\u636e\u6b63\u5728\u88c5\u5165...";
        try {
            uploadDataTaskEO.setStatus(syncStatus.getCode());
            uploadDataTaskEO.setSyncLogInfo(syncLogInfo);
            this.uploadDataTaskDao.update((BaseEntity)uploadDataTaskEO);
            syncLogInfo = this.importJioReportData(uploadDataTaskEO, uploadParam);
            syncStatus = TaskStatusEnum.SUCCESS;
        }
        catch (Exception e) {
            syncStatus = TaskStatusEnum.ERROR;
            syncLogInfo = "\u6570\u636e\u88c5\u5165\u5931\u8d25\uff1a" + e.getMessage();
            LOGGER.error(syncLogInfo, e);
        }
        finally {
            uploadDataTaskEO.setSyncTime(new Date());
            uploadDataTaskEO.setStatus(syncStatus.getCode());
            uploadDataTaskEO.setSyncLogInfo(syncLogInfo);
            this.uploadDataTaskDao.update((BaseEntity)uploadDataTaskEO);
            this.insertDataUpdateLog(uploadDataTaskEO);
        }
    }

    private String importJioReportData(ReportDataSyncUploadDataTaskEO uploadDataTaskEO, UploadParam uploadParam) {
        CommonFileDTO syncDataAttachFile = this.commonFileService.queryOssFileByFileKey(uploadDataTaskEO.getSyncDataAttachId());
        String rootPath = System.getProperty(FilenameUtils.normalize((String)"java.io.tmpdir")) + File.separator + "reportdatasync" + File.separator + "importdata" + File.separator + LocalDate.now() + File.separator + uploadDataTaskEO.getSyncDataAttachId();
        String filePath = rootPath + File.separator + uploadDataTaskEO.getTaskTitle() + ".jio";
        File file = new File(FilenameUtils.normalize((String)filePath));
        try {
            FileUtils.forceMkdirParent((File)file);
            FileUtils.copyInputStreamToFile((InputStream)syncDataAttachFile.getInputStream(), (File)file);
            CacheObjectResourceRemote cacheObjectResourceRemote = (CacheObjectResourceRemote)SpringContextUtils.getBean(CacheObjectResourceRemote.class);
            SimpleAsyncProgressMonitor asyncTaskMonitor = new SimpleAsyncProgressMonitor(UUIDUtils.newUUIDStr(), cacheObjectResourceRemote);
            uploadParam.setFilePath(file.getPath());
            ImportResultObject importResultObject = this.uploadJioService.upload(file, uploadParam, (AsyncTaskMonitor)asyncTaskMonitor);
            LOGGER.info("JQR\u6570\u636e\u88c5\u5165\u7ed3\u679c\uff1a" + importResultObject.getMessage());
            this.executeDataFlow(uploadDataTaskEO);
            String string = importResultObject.getMessage();
            return string;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BusinessRuntimeException(e.getMessage());
        }
        finally {
            ReportDataSyncUtils.delFiles(new File(rootPath));
        }
    }

    private String queryImportProcessDetail(String asyncTaskInfoId, ReportDataSyncUploadDataTaskEO uploadDataTaskEO) throws InterruptedException {
        int i = 0;
        while (true) {
            AsyncTask asyncTask;
            TaskState state;
            if (ReportDataSyncDataUtils.dataSyncProcessON(state = (asyncTask = this.asyncTaskManager.queryTask(asyncTaskInfoId)).getState())) {
                Thread.sleep(1000L);
                if (++i <= 1200) continue;
                throw new RuntimeException("\u6570\u636e\u88c5\u5165\u6267\u884c\u8d85\u65f6\uff0c\u4efb\u52a1\u7ed3\u675f\u3002\u8bf7\u975e\u4e1a\u52a1\u7e41\u5fd9\u65f6\u6bb5\uff0c\u518d\u91cd\u65b0\u6267\u884c\u4efb\u52a1\u3002");
            }
            if (ReportDataSyncDataUtils.dataSyncProcessEnd(state) || ReportDataSyncDataUtils.dataSyncProcessError(state)) break;
        }
        Object resultObject = this.asyncTaskManager.queryDetail(asyncTaskInfoId);
        String jsonString = resultObject instanceof String ? (String)resultObject : JsonUtils.writeValueAsString((Object)resultObject);
        JIOImportResultObject jioImportResultObject = (JIOImportResultObject)JsonUtils.readValue((String)jsonString, JIOImportResultObject.class);
        String taskMessage = ReportDataSyncDataUtils.getTaskMessageFromAsyncTask(jioImportResultObject);
        LOGGER.info("JQR\u6570\u636e\u88c5\u5165\u7ed3\u679c\uff1a" + taskMessage);
        this.executeDataFlow(uploadDataTaskEO);
        return taskMessage;
    }

    private String importReportData(File localFile, UploadParam param) {
        String fileName = localFile.getName();
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];
        try {
            long fileSizeByte = this.fileService.tempArea().getAreaConfig().getMaxFileSize();
            double fileSizeM = (double)fileSizeByte / 1024.0;
            if (fileSizeByte < localFile.length() / 1024L) {
                throw new RuntimeException("\u6587\u4ef6\u5927\u5c0f\u5927\u4e8e\u914d\u7f6e\u503c" + fileSizeM + "M!!!");
            }
            FileInfo fileInfo = this.fileService.tempArea().upload(fileName, (InputStream)new FileInputStream(localFile));
            param.setFileKey(fileInfo.getKey());
            AsyncUploadParam importParam = new AsyncUploadParam();
            importParam.setParam(param);
            importParam.setSuffix(suffix);
            return this.asyncTaskManager.publishTask((Object)importParam, AsynctaskPoolType.ASYNCTASK_UPLOADFILE.getName());
        }
        catch (Exception e) {
            throw new RuntimeException("\u88c5\u5165\u5931\u8d25\uff1a" + e.getMessage());
        }
    }

    private void executeDataFlow(ReportDataSyncUploadDataTaskEO uploadDataTaskEO) {
        NpContext context = NpContextHolder.getContext();
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                ReportDataSyncFlowUtils.executeDataFlow(uploadDataTaskEO.getTaskCode(), uploadDataTaskEO.getOrgCode(), uploadDataTaskEO.getPeriodValue(), UploadStateEnum.ACTION_UPLOAD, "\u4e0b\u7ea7\u5355\u4f4d\u4e0a\u62a5", uploadDataTaskEO.getSelectAdjust());
            }
            finally {
                NpContextHolder.clearContext();
            }
        });
    }

    private void checkOrgUploadState(ReportDataSyncUploadDataTaskVO uploadDataTaskVO, UploadParam uploadParam) {
        ActionStateBean actionState = ReportDataSyncFlowUtils.queryUnitState(uploadParam.getFormSchemeKey(), uploadDataTaskVO.getOrgCode(), uploadDataTaskVO.getPeriodValue(), uploadDataTaskVO.getAdjustCode());
        if (UploadState.UPLOADED.name().equals(actionState.getCode()) || UploadState.SUBMITED.name().equals(actionState.getCode()) || UploadState.CONFIRMED.name().equals(actionState.getCode())) {
            throw new BusinessRuntimeException("\u4e0a\u62a5\u4efb\u52a1[" + uploadDataTaskVO.getTaskTitle() + "]-\u5355\u4f4d[" + uploadDataTaskVO.getOrgTitle() + "]-\u65f6\u671f[" + uploadDataTaskVO.getPeriodStr() + "]\u5728\u63a5\u6536\u7aef\u6570\u636e\u72b6\u6001\u4e3a\u5df2\u4e0a\u62a5\uff0c\u4e0d\u5141\u8bb8\u4e0a\u62a5\u3002");
        }
    }

    @Override
    public Boolean importReportDataFile(MultipartFile importFile, ImportContext context) throws IOException {
        byte[] bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)importFile, (String)"ReportDataMeta.JSON");
        if (bytes == null) {
            throw new BusinessRuntimeException("\u6570\u636e\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aReportDataMeta.JSON");
        }
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((byte[])bytes, ReportDataSyncUploadDataTaskVO.class);
        return this.importReportDataFile(importFile, uploadDataTaskVO, null);
    }

    @Override
    public Boolean importReportDataFile(MultipartFile importFile, ReportDataSyncUploadDataTaskVO uploadDataTaskVO, ImportContext context) throws IOException {
        uploadDataTaskVO.setId(UUIDUtils.newUUIDStr());
        uploadDataTaskVO.setStatus(TaskStatusEnum.WAIT.getCode());
        this.uploadDataService.saveUploadDataTaskAndCommit(uploadDataTaskVO);
        this.commonFileService.uploadFileToOss(importFile, uploadDataTaskVO.getSyncDataAttachId());
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadDataTaskVO.getPeriodStr());
        LogHelper.info((String)"\u5408\u5e76-\u4e0a\u4f20\u6570\u636e\u7ba1\u7406", (String)String.format("\u79bb\u7ebf\u88c5\u5165-%1$s\u4efb\u52a1%2$s\u65f6\u671f", uploadDataTaskVO.getTaskTitle(), periodTitle), (String)"");
        NpContext npContext = NpContextHolder.getContext();
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)npContext);
                this.uploadDataService.dataLoading(uploadDataTaskVO.getId(), true);
            }
            finally {
                NpContextHolder.clearContext();
            }
        });
        return Boolean.TRUE;
    }

    @Override
    public List<OrgDO> getOrgDO(ReportDataCheckParam checkParam) {
        String orgType = checkParam.getOrgType();
        OrgDataService orgService = (OrgDataService)SpringContextUtils.getBean(OrgDataService.class);
        YearPeriodDO period = YearPeriodUtil.transform(null, (String)checkParam.getPeriodStr());
        OrgDTO orgDTO = new OrgDTO();
        if (checkParam.getOrgCodes() != null && !checkParam.getOrgCodes().isEmpty()) {
            orgDTO.setOrgCodes(checkParam.getOrgCodes());
        }
        orgDTO.setVersionDate(period.getEndDate());
        orgDTO.setCategoryname(orgType);
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgDTO.setStopflag(Integer.valueOf(-1));
        List orgDOList = orgService.list(orgDTO).getRows();
        return orgDOList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Boolean importFromFtp(ReportDataUploadToFtpVO ftpVO) {
        try {
            LOGGER.info("\u5f00\u59cb\u8fde\u63a5ftp");
            Object ftp = ftpVO.getFtpType().equals("ftp") ? new Ftp(ftpVO.getFtpHost(), ftpVO.getFtpPort().intValue(), ftpVO.getFtpUserName(), ftpVO.getFtpPassword()) : new Sftp(ftpVO.getFtpHost(), ftpVO.getFtpPort().intValue(), ftpVO.getFtpUserName(), ftpVO.getFtpPassword());
            LOGGER.info("\u8fde\u63a5ftp\u6210\u529f");
            List fileNames = ftp.ls(ftpVO.getFtpFilePath());
            for (String fileName : fileNames) {
                if (!fileName.startsWith("reportsync_")) continue;
                File file = new File(System.getProperty("java.io.tmpdir") + File.separator + fileName);
                LOGGER.info("\u5f00\u59cb\u4eceftp\u4e0b\u8f7d\uff0c\u6587\u4ef6\u540d\u79f0\uff1a" + fileName);
                ftp.download(ftpVO.getFtpFilePath() + "/" + fileName, file, "zip");
                LOGGER.info("\u4e0b\u8f7d\u6210\u529f\uff1a");
                try {
                    FileInputStream input = new FileInputStream(file);
                    Throwable throwable = null;
                    try {
                        LOGGER.info("\u5f00\u59cb\u5bfc\u5165\u5230\u7cfb\u7edf\uff1a");
                        byte[] fileContent = new byte[(int)file.length()];
                        input.read(fileContent);
                        input.close();
                        MockMultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", fileContent);
                        Boolean isSuccess = this.uploadDataService.importReportDataFile((MultipartFile)multipartFile, null);
                        if (isSuccess.booleanValue()) {
                            ftp.delFile(ftpVO.getFtpFilePath() + "/" + fileName);
                        }
                        LOGGER.info("\u5bfc\u5165\u5b8c\u6210\uff0c\u7ed3\u679c\uff1a" + isSuccess);
                        file.delete();
                    }
                    catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    }
                    finally {
                        if (input == null) continue;
                        if (throwable != null) {
                            try {
                                input.close();
                            }
                            catch (Throwable throwable3) {
                                throwable.addSuppressed(throwable3);
                            }
                            continue;
                        }
                        input.close();
                    }
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException(e.getMessage());
                    return Boolean.TRUE;
                }
            }
        }
        catch (Exception e) {
            LOGGER.info("\u88c5\u5165\u5931\u8d25\uff1a" + e.getMessage());
            throw new BusinessRuntimeException(e.getMessage());
        }
    }

    @Override
    public Boolean fetchReportData(Map<String, String> params) {
        List<ReportDataSyncServerInfoVO> serverInfoVOList = this.serverListService.listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverInfoVOList)) {
            throw new BusinessRuntimeException("\u6ca1\u6709\u53ef\u7528\u7684\u6570\u636e\u540c\u6b65\u670d\u52a1");
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverInfoVOList.get(0);
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = null;
        for (ISyncMethodScheduler scheduler : this.syncMethodSchedulerList) {
            boolean i = false;
            if (!scheduler.code().equals(type) || scheduler.getHandler() == null) continue;
            extendService = scheduler.getHandler();
            break;
        }
        if (extendService == null) {
            throw new BusinessRuntimeException("\u5f53\u524d\u540c\u6b65\u7c7b\u578b\u4e0d\u652f\u6301\u6570\u636e\u62c9\u53d6");
        }
        MultipartFile file = extendService.fetchReportData(serverInfoVO, params);
        if (file == null) {
            throw new BusinessRuntimeException("\u5f53\u524d\u540c\u6b65\u7c7b\u578b\u4e0d\u652f\u6301\u6570\u636e\u62c9\u53d6");
        }
        try {
            this.importReportDataFile(file, null);
        }
        catch (Throwable e) {
            throw new BusinessRuntimeException(e.getMessage(), e);
        }
        return true;
    }
}

