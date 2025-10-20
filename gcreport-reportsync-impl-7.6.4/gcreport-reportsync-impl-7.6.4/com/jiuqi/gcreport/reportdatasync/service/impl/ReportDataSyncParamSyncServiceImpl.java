/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Functions
 *  com.google.common.util.concurrent.AtomicDouble
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.param.ReportParam
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamProgressDataDTO
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.google.common.base.Functions;
import com.google.common.util.concurrent.AtomicDouble;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.param.ReportParam;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ParamDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataParamsSyncSchemeDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncIssuedLogDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncIssuedLogItemDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamProgressDataDTO;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataParamsSyncSchemeEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogItemEO;
import com.jiuqi.gcreport.reportdatasync.enums.ParamPackageStatus;
import com.jiuqi.gcreport.reportdatasync.enums.ParamSyncStatus;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataReceiveType;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.enums.RetryParamSyncBatchType;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.impl.type.ParamDataSyncScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamSyncService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncParamUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReportDataSyncParamSyncServiceImpl
implements ReportDataSyncParamSyncService {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncParamSyncServiceImpl.class);
    @Autowired
    private ReportDataSyncIssuedLogDao issuedLogDao;
    @Autowired
    private ReportDataSyncIssuedLogItemDao issuedLogItemDao;
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private ReportDataSyncServerListService reportDataSyncServerListService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataParamsSyncSchemeDao paramsSyncSchemeDao;
    @Autowired
    private ProgressService<ReportDataSyncParamProgressDataDTO, List<String>> progressService;
    @Autowired
    private List<ISyncMethodScheduler> serviceManagerUploadTypeList;
    @Autowired
    private ParamDataSyncScheduler paramDataSyncScheduler;

    @Override
    public Boolean uploadParamsUpdateInstructions(String paramSyncSchemeId) {
        ReportDataParamsSyncSchemeEO paramsSyncSchemeEO = (ReportDataParamsSyncSchemeEO)this.paramsSyncSchemeDao.get((Serializable)((Object)paramSyncSchemeId));
        ReportDataSyncParams syncParam = (ReportDataSyncParams)JsonUtils.readValue((String)paramsSyncSchemeEO.getContent(), ReportDataSyncParams.class);
        ReportDataSyncIssuedLogEO xfLogEO = new ReportDataSyncIssuedLogEO();
        xfLogEO.setId(UUIDOrderUtils.newUUIDStr());
        xfLogEO.setSyncSchemeId(paramSyncSchemeId);
        xfLogEO.setSyncDesAttachId(paramsSyncSchemeEO.getSyncDesAttachId());
        xfLogEO.setSyncDesAttachTitle(paramsSyncSchemeEO.getSyncDesAttachTitle());
        xfLogEO.setSyncStatus(null);
        xfLogEO.setSyncTime(new Date());
        xfLogEO.setParamStatus(ParamPackageStatus.NOT_START.getCode());
        xfLogEO.setContent(paramsSyncSchemeEO.getContent());
        NpContext npContext = NpContextHolder.getContext();
        xfLogEO.setSyncUserId(npContext.getUserId());
        xfLogEO.setSyncUserName(npContext.getUser().getFullname());
        xfLogEO.setTaskTitle(paramsSyncSchemeEO.getParamPackageTitle());
        ReportParam reportParam = syncParam.getReportParam();
        if (paramsSyncSchemeEO.getOnlyMergeParams() == 0) {
            xfLogEO.setSyncVersion(DateUtils.format((Date)new Date(), (String)"yyyyMMddHHmmssSSS"));
            xfLogEO.setTaskId(reportParam.getTaskId());
            try {
                TaskDefine taskDefine = this.taskDefineService.queryTaskDefine(syncParam.getReportParam().getTaskId());
                Objects.requireNonNull(taskDefine, "\u627e\u4e0d\u5230ID\u4e3a".concat(syncParam.getReportParam().getTaskId()).concat("\u7684\u62a5\u8868\u4efb\u52a1\u4fe1\u606f\u3002"));
                xfLogEO.setTaskCode(taskDefine.getTaskCode());
            }
            catch (Exception e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        }
        ReportDataSyncParamUtils.exportParamToOss(xfLogEO, syncParam);
        this.issuedLogDao.add((BaseEntity)xfLogEO);
        LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u540c\u6b65", (String)String.format("\u751f\u6210\u7248\u672c-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", xfLogEO.getTaskTitle(), xfLogEO.getSyncVersion()), (String)"");
        return Boolean.TRUE;
    }

    @Override
    public List<ReportDataSyncParams> listAllParamSyncScheme() {
        List<ReportDataParamsSyncSchemeEO> syncSchemeEOS = this.paramsSyncSchemeDao.listAllParamSyncScheme();
        ArrayList<ReportDataSyncParams> params = new ArrayList<ReportDataSyncParams>();
        for (ReportDataParamsSyncSchemeEO syncSchemeEO : syncSchemeEOS) {
            ReportDataSyncParams syncParamScheme = (ReportDataSyncParams)JsonUtils.readValue((String)syncSchemeEO.getContent(), ReportDataSyncParams.class);
            syncParamScheme.setTitle(syncSchemeEO.getParamPackageTitle());
            syncParamScheme.setId(syncSchemeEO.getId());
            syncParamScheme.setParamPackageTitle(syncSchemeEO.getParamPackageTitle());
            syncParamScheme.setSyncDesAttachId(syncSchemeEO.getSyncDesAttachId());
            syncParamScheme.setSyncDesAttachTitle(syncSchemeEO.getSyncDesAttachTitle());
            params.add(syncParamScheme);
        }
        return params;
    }

    @Override
    public Boolean addParamSyncScheme(MultipartFile syncDesFile, ReportDataSyncParams syncParam) {
        ReportDataParamsSyncSchemeEO paramsSyncSchemeEO = new ReportDataParamsSyncSchemeEO();
        paramsSyncSchemeEO.setId(UUIDOrderUtils.newUUIDStr());
        if (syncDesFile != null) {
            ObjectInfo syncDesObjectInfo = this.commonFileService.uploadFileToOss(syncDesFile);
            paramsSyncSchemeEO.setSyncDesAttachId(syncDesObjectInfo.getKey());
            paramsSyncSchemeEO.setSyncDesAttachTitle(syncDesObjectInfo.getName());
        }
        paramsSyncSchemeEO.setOnlyMergeParams(syncParam.getOnlyMergeParams() != false ? 1 : 0);
        paramsSyncSchemeEO.setParamPackageTitle(syncParam.getParamPackageTitle());
        paramsSyncSchemeEO.setOrdinal(new Double(OrderGenerator.newOrderID()));
        syncParam.convert2EO();
        String json = JsonUtils.writeValueAsString((Object)syncParam);
        paramsSyncSchemeEO.setContent(json);
        List<ReportDataParamsSyncSchemeEO> res = this.paramsSyncSchemeDao.queryByParamTitle(paramsSyncSchemeEO.getParamPackageTitle());
        if (res.size() != 0) {
            throw new BusinessRuntimeException("\u53c2\u6570\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
        }
        this.paramsSyncSchemeDao.add((BaseEntity)paramsSyncSchemeEO);
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Boolean updateParamSyncScheme(MultipartFile syncDesFile, ReportDataSyncParams syncParam) {
        Assert.notNull((Object)syncParam.getId(), (String)"\u53c2\u6570\u540c\u6b65\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataParamsSyncSchemeEO uploadSchemeEO = (ReportDataParamsSyncSchemeEO)this.paramsSyncSchemeDao.get((Serializable)((Object)syncParam.getId()));
        if (uploadSchemeEO == null) {
            return Boolean.FALSE;
        }
        if (syncDesFile != null) {
            ObjectInfo syncDesObjectInfo = this.commonFileService.uploadFileToOss(syncDesFile);
            uploadSchemeEO.setSyncDesAttachId(syncDesObjectInfo.getKey());
            uploadSchemeEO.setSyncDesAttachTitle(syncDesObjectInfo.getName());
        }
        uploadSchemeEO.setOnlyMergeParams(syncParam.getOnlyMergeParams() != false ? 1 : 0);
        uploadSchemeEO.setParamPackageTitle(syncParam.getParamPackageTitle());
        syncParam.convert2EO();
        String json = JsonUtils.writeValueAsString((Object)syncParam);
        uploadSchemeEO.setContent(json);
        List<ReportDataParamsSyncSchemeEO> res = this.paramsSyncSchemeDao.queryByParamTitle(uploadSchemeEO.getParamPackageTitle());
        if (res.size() == 0) {
            this.paramsSyncSchemeDao.update((BaseEntity)uploadSchemeEO);
            return Boolean.TRUE;
        } else {
            if (res.size() != 1) throw new BusinessRuntimeException("\u53c2\u6570\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
            if (!res.get(0).getId().equals(syncParam.getId())) throw new BusinessRuntimeException("\u53c2\u6570\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
            this.paramsSyncSchemeDao.update((BaseEntity)uploadSchemeEO);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteParamSyncScheme(String paramSyncSchemeId) {
        ReportDataParamsSyncSchemeEO uploadSchemeEO = (ReportDataParamsSyncSchemeEO)this.paramsSyncSchemeDao.get((Serializable)((Object)paramSyncSchemeId));
        if (uploadSchemeEO == null) {
            return Boolean.FALSE;
        }
        this.paramsSyncSchemeDao.delete((BaseEntity)uploadSchemeEO);
        return true;
    }

    @Override
    public Boolean paramSync(String paramSyncLogId) {
        ReportDataSyncIssuedLogEO reportDataSyncIssuedLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)paramSyncLogId));
        if (reportDataSyncIssuedLogEO == null) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u53c2\u6570\u5305\u751f\u6210\u8bb0\u5f55\u540e\u518d\u540c\u6b65");
        }
        ReportDataSyncParamProgressDataDTO progressDataDTO = new ReportDataSyncParamProgressDataDTO(paramSyncLogId);
        this.progressService.createProgressData((ProgressData)progressDataDTO);
        progressDataDTO.setProgressValueAndRefresh(0.1);
        NpContext context = NpContextHolder.getContext();
        CompletableFuture.runAsync(() -> {
            try {
                NpContextHolder.setContext((NpContext)context);
                this.retryParamSyncByLogIdAndBatchType(paramSyncLogId, RetryParamSyncBatchType.ALL_SYNCED_FAIL_AND_NOT_SYNC, progressDataDTO);
            }
            catch (Exception e) {
                try {
                    throw new BusinessRuntimeException((Throwable)e);
                }
                catch (Throwable throwable) {
                    progressDataDTO.setProgressValueAndRefresh(1.0);
                    NpContextHolder.clearContext();
                    LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u540c\u6b65", (String)String.format("\u540c\u6b65-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", reportDataSyncIssuedLogEO.getTaskTitle(), reportDataSyncIssuedLogEO.getSyncVersion()), (String)"");
                    throw throwable;
                }
            }
            progressDataDTO.setProgressValueAndRefresh(1.0);
            NpContextHolder.clearContext();
            LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u540c\u6b65", (String)String.format("\u540c\u6b65-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", reportDataSyncIssuedLogEO.getTaskTitle(), reportDataSyncIssuedLogEO.getSyncVersion()), (String)"");
        });
        return Boolean.TRUE;
    }

    @Override
    public PageInfo<ReportDataSyncIssuedLogVO> listXfLogs(String paramSyncSchemeId, Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncIssuedLogEO> pageInfo = this.issuedLogDao.listXfLogs(paramSyncSchemeId, pageSize, pageNum);
        List logVOS = pageInfo.getList().stream().map(logEo -> {
            ReportDataSyncIssuedLogVO xfLogVO = new ReportDataSyncIssuedLogVO();
            Date syncTime = logEo.getSyncTime();
            BeanUtils.copyProperties(logEo, xfLogVO);
            xfLogVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
            xfLogVO.setParamStatusTitle(ParamPackageStatus.findTitleByCode(xfLogVO.getParamStatus()));
            xfLogVO.setSyncStatusTitle(ParamSyncStatus.findTitleByCode(xfLogVO.getSyncStatus()));
            if (xfLogVO.getSyncStatusTitle() != null) {
                List<ReportDataSyncIssuedLogItemEO> reportDataSyncIssuedLogItemEOS = this.issuedLogItemDao.listXfLogItemsByLogId(xfLogVO.getId());
                Integer syncSuccess = (int)reportDataSyncIssuedLogItemEOS.stream().filter(eo -> eo.getStatus() != null && eo.getStatus() == 1).count();
                Integer syncFailed = (int)reportDataSyncIssuedLogItemEOS.stream().filter(eo -> !(eo.getStatus() != null && eo.getStatus() != 0 || eo.getNotSynchronized() != null && eo.getNotSynchronized() != 0)).count();
                Integer notSync = (int)reportDataSyncIssuedLogItemEOS.stream().filter(eo -> (eo.getStatus() == null || eo.getStatus() == 0) && eo.getNotSynchronized() != null && eo.getNotSynchronized() == 1).count();
                xfLogVO.setSyncSuccess(syncSuccess);
                xfLogVO.setSyncFailed(syncFailed);
                xfLogVO.setNotSync(notSync);
            }
            xfLogVO.setSyncVersion(StringUtils.isEmpty((String)logEo.getSyncVersion()) ? "--" : logEo.getSyncVersion());
            ProgressData progressData = this.progressService.queryProgressData(xfLogVO.getId());
            if (progressData != null && progressData.getProgressValue() < 1.0 && progressData.getProgressValue() > 0.0) {
                xfLogVO.setShowProgress(Boolean.valueOf(true));
                xfLogVO.setProgress(Double.valueOf(progressData.getProgressValue()));
            }
            return xfLogVO;
        }).collect(Collectors.toList());
        PageInfo pageInfoVO = PageInfo.of(logVOS, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public List<ReportDataSyncIssuedLogVO> fetchSyncParamTaskInfos() {
        List<ReportDataSyncIssuedLogEO> reportDataSyncIssuedLogEOS = this.issuedLogDao.listXfStartedLogs();
        List<ReportDataSyncIssuedLogVO> logVOS = reportDataSyncIssuedLogEOS.stream().map(logEo -> {
            ReportDataSyncIssuedLogVO xfLogVO = new ReportDataSyncIssuedLogVO();
            Date syncTime = logEo.getSyncTime();
            BeanUtils.copyProperties(logEo, xfLogVO);
            xfLogVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
            xfLogVO.setParamStatusTitle(ParamPackageStatus.findTitleByCode(xfLogVO.getParamStatus()));
            return xfLogVO;
        }).collect(Collectors.toList());
        return logVOS;
    }

    @Override
    public ReportDataSyncParamFileDTO fetchSyncParamFiles(ReportDataSyncIssuedLogVO issuedLogVO) {
        ReportDataSyncParamFileDTO dataSyncParamFileDTO = new ReportDataSyncParamFileDTO();
        String syncDesAttachId = issuedLogVO.getSyncDesAttachId();
        if (!StringUtils.isEmpty((String)syncDesAttachId)) {
            CommonFileDTO syncDesAttachFile = this.commonFileService.queryOssFileByFileKey(syncDesAttachId);
            dataSyncParamFileDTO.setSyncDesAttachId(syncDesAttachId);
            dataSyncParamFileDTO.setSyncDesAttachFile(syncDesAttachFile);
        }
        String syncParamAttachId = issuedLogVO.getSyncParamAttachId();
        CommonFileDTO syncParamAttachFile = this.commonFileService.queryOssFileByFileKey(syncParamAttachId);
        dataSyncParamFileDTO.setSyncParamAttachId(syncParamAttachId);
        dataSyncParamFileDTO.setSyncParamAttachFile(syncParamAttachFile);
        return dataSyncParamFileDTO;
    }

    @Override
    public void downloadParamUpateInstructions(HttpServletRequest request, HttpServletResponse response, String logId) {
        ReportDataSyncIssuedLogEO xfLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        String syncDesAttachId = xfLogEO.getSyncDesAttachId();
        this.commonFileService.downloadOssFile(request, response, syncDesAttachId);
    }

    @Override
    public void downloadParam(HttpServletRequest request, HttpServletResponse response, String logId) {
        ReportDataSyncIssuedLogEO xfLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        String syncParamAttachId = xfLogEO.getSyncParamAttachId();
        String ygSyncParamAttachId = xfLogEO.getYgParamAttachId();
        if (!StringUtils.isEmpty((String)ygSyncParamAttachId)) {
            this.commonFileService.downloadOssZipFile(request, response, Arrays.asList(syncParamAttachId, ygSyncParamAttachId), xfLogEO.getTaskTitle() + "_\u53c2\u6570\u5305");
        } else {
            this.commonFileService.downloadOssFile(request, response, syncParamAttachId);
        }
    }

    @Override
    public void downloadParamAndUpateInstructions(HttpServletRequest request, HttpServletResponse response, String logId) {
        ReportDataSyncIssuedLogEO xfLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        String syncParamAttachId = xfLogEO.getSyncParamAttachId();
        String ygSyncParamAttachId = xfLogEO.getYgParamAttachId();
        String syncDesAttachId = xfLogEO.getSyncDesAttachId();
        ArrayList<String> fileIds = new ArrayList<String>();
        fileIds.add(syncParamAttachId);
        if (!StringUtils.isEmpty((String)ygSyncParamAttachId)) {
            fileIds.add(ygSyncParamAttachId);
        }
        fileIds.add(syncDesAttachId);
        this.commonFileService.downloadOssZipFile(request, response, fileIds, xfLogEO.getTaskTitle() + "_\u53c2\u6570\u5305");
    }

    @Override
    public List<ReportDataSyncIssuedLogItemVO> listXfLogItemsByLogId(String logId) {
        ReportDataSyncIssuedLogEO dataSyncIssuedLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        List<ReportDataSyncIssuedLogItemEO> xfLogItemEOList = this.issuedLogItemDao.listXfLogItemsByLogId(logId);
        return xfLogItemEOList.stream().map(logItem -> {
            ReportDataSyncIssuedLogItemVO logItemVO = new ReportDataSyncIssuedLogItemVO();
            Date syncTime = logItem.getSyncTime();
            BeanUtils.copyProperties(logItem, logItemVO);
            logItemVO.setStatus(Boolean.valueOf(logItem.getStatus() != null && logItem.getStatus() == 1));
            logItemVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
            logItemVO.setIndex(String.valueOf(xfLogItemEOList.indexOf(logItem) + 1));
            logItemVO.setSyncUserName(StringUtils.isEmpty((String)logItemVO.getSyncUserName()) ? dataSyncIssuedLogEO.getSyncUserName() : logItem.getSyncUserName());
            logItemVO.setTaskTitle(dataSyncIssuedLogEO.getTaskTitle());
            return logItemVO;
        }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<ReportDataSyncIssuedLogItemVO> listXfLogItemsByLogId(String logId, Integer pageSize, Integer pageNum) {
        ReportDataSyncIssuedLogEO dataSyncIssuedLogEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        PageInfo<ReportDataSyncIssuedLogItemEO> pageInfo = this.issuedLogItemDao.listXfLogItemsByLogId(logId, pageSize, pageNum);
        List vos = pageInfo.getList().stream().map(logItem -> {
            ReportDataSyncIssuedLogItemVO logItemVO = new ReportDataSyncIssuedLogItemVO();
            Date syncTime = logItem.getSyncTime();
            BeanUtils.copyProperties(logItem, logItemVO);
            logItemVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
            logItemVO.setIndex(String.valueOf(pageInfo.getList().indexOf(logItem) + 1));
            logItemVO.setSyncUserName(StringUtils.isEmpty((String)logItemVO.getSyncUserName()) ? dataSyncIssuedLogEO.getSyncUserName() : logItem.getSyncUserName());
            logItemVO.setTaskTitle(dataSyncIssuedLogEO.getTaskTitle());
            logItemVO.setStatus(Boolean.valueOf(logItem.getStatus() != null && logItem.getStatus().equals(1)));
            logItemVO.setNotSynchronized(Boolean.valueOf(logItem.getNotSynchronized() != null && logItem.getNotSynchronized().equals(1)));
            return logItemVO;
        }).collect(Collectors.toList());
        PageInfo pageInfoVO = PageInfo.of(vos, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public ReportDataSyncIssuedLogVO queryLatestSyncParamTaskByTaskId(String taskId) {
        ReportDataSyncIssuedLogEO issuedLogEO = this.issuedLogDao.queryLatestSyncParamTaskByTaskId(taskId);
        if (issuedLogEO == null) {
            return null;
        }
        ReportDataSyncIssuedLogVO xfLogVO = new ReportDataSyncIssuedLogVO();
        Date syncTime = issuedLogEO.getSyncTime();
        BeanUtils.copyProperties((Object)issuedLogEO, xfLogVO);
        xfLogVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
        return xfLogVO;
    }

    @Override
    public ExportExcelSheet downloadParamUpateLogs(String id) {
        List<ReportDataSyncIssuedLogItemVO> logItems = this.listXfLogItemsByLogId(id);
        ExportExcelSheet exportExcelSheet = new ExportExcelSheet(Integer.valueOf(0), "\u53c2\u6570\u4e0b\u53d1\u65e5\u5fd7", Integer.valueOf(1));
        exportExcelSheet.setColumnWidths(new int[]{12800, 12800, 12800});
        String[] headerRow = new String[]{"\u5e8f\u53f7", "\u53c2\u6570\u5305\u540d\u79f0", "\u5355\u4f4d", "\u7ed3\u679c", "\u6700\u540e\u6267\u884c\u65f6\u95f4", "\u6700\u540e\u6267\u884c\u7528\u6237", "\u5931\u8d25\u539f\u56e0"};
        exportExcelSheet.getRowDatas().add(headerRow);
        for (ReportDataSyncIssuedLogItemVO logItem : logItems) {
            String status = logItem.getStatus() != false ? "\u6210\u529f" : "\u5931\u8d25";
            exportExcelSheet.getRowDatas().add(new String[]{logItem.getIndex(), logItem.getTaskTitle(), logItem.getOrgTitle(), status, logItem.getSyncTime(), logItem.getSyncUserName(), logItem.getMsg()});
        }
        return exportExcelSheet;
    }

    @Override
    public Boolean retryParamSyncByLogItemIds(List<String> paramSyncLogItemIds) {
        List<ReportDataSyncServerInfoVO> serverInfoVOS;
        if (CollectionUtils.isEmpty(paramSyncLogItemIds)) {
            return true;
        }
        List<ReportDataSyncIssuedLogItemEO> logItemEOS = this.issuedLogItemDao.listXfLogItemsByLogItemIds(paramSyncLogItemIds);
        if (CollectionUtils.isEmpty(logItemEOS)) {
            return true;
        }
        String xfTaskId = logItemEOS.get(0).getXfTaskId();
        ReportDataSyncIssuedLogEO logEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)xfTaskId));
        if (logEO == null) {
            throw new BusinessRuntimeException("\u53c2\u6570\u540c\u6b65\u8bb0\u5f55\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        String paramStatus = logEO.getParamStatus();
        if (ParamPackageStatus.DEPRECATED.getCode().equals(paramStatus)) {
            throw new BusinessRuntimeException("\u5df2\u8fc7\u671f\u7684\u53c2\u6570\u4e0d\u5141\u8bb8\u540c\u6b65");
        }
        List<String> orgCodes = logItemEOS.stream().map(ReportDataSyncIssuedLogItemEO::getOrgCode).collect(Collectors.toList());
        if (orgCodes.size() == 1) {
            this.checkOrgCodeOnly((String)orgCodes.get(0));
        }
        if (CollectionUtils.isEmpty(serverInfoVOS = this.reportDataSyncServerListService.queryServerInfoByOrgCodes(orgCodes))) {
            return false;
        }
        Map orgCode2ServerInfoMap = serverInfoVOS.stream().collect(Collectors.toConcurrentMap(ReportDataSyncServerInfoVO::getOrgCode, Functions.identity()));
        CommonFileDTO syncDesAttachFile = null;
        if (!StringUtils.isEmpty((String)logEO.getSyncDesAttachId())) {
            syncDesAttachFile = this.commonFileService.queryOssFileByFileKey(logEO.getSyncDesAttachId());
        }
        CommonFileDTO syncParamAttachFile = this.commonFileService.queryOssFileByFileKey(logEO.getSyncParamAttachId());
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        fileDTO.setMainFile((MultipartFile)syncParamAttachFile);
        fileDTO.setMainFileId(logEO.getSyncParamAttachId());
        fileDTO.setDesAttachFile((MultipartFile)syncDesAttachFile);
        fileDTO.setDesAttachFileId(logEO.getSyncDesAttachId());
        ReportDataSyncReceiveTaskVO receiveTaskVO = new ReportDataSyncReceiveTaskVO();
        receiveTaskVO.setId(logEO.getId());
        receiveTaskVO.setSyncDesAttachId(logEO.getSyncDesAttachId());
        receiveTaskVO.setSyncDesAttachTitle(logEO.getSyncDesAttachTitle());
        receiveTaskVO.setSyncParamAttachId(logEO.getSyncParamAttachId());
        receiveTaskVO.setXfTime(DateUtils.format((Date)logEO.getSyncTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        receiveTaskVO.setSyncVersion(logEO.getSyncVersion());
        receiveTaskVO.setTaskId(logEO.getTaskId());
        receiveTaskVO.setTaskTitle(logEO.getTaskTitle());
        receiveTaskVO.setReceiveType(ReportDataReceiveType.ZXXF.getCode());
        ReportDataSyncParamSyncServiceImpl paramSyncService = (ReportDataSyncParamSyncServiceImpl)SpringContextUtils.getBean(ReportDataSyncParamSyncServiceImpl.class);
        Semaphore semaphore = new Semaphore(4);
        NpContext context = NpContextHolder.getContext();
        ParamDataContext content = new ParamDataContext();
        content.setReceiveTaskVO(receiveTaskVO);
        content.setFileDTO(fileDTO);
        content.setType(SyncTypeEnums.PARAMDATA);
        ((Stream)logItemEOS.stream().parallel()).forEach(logItemEO -> {
            Boolean status;
            String msg;
            block9: {
                Optional<ISyncMethodScheduler> schedulerOpt;
                ReportDataSyncServerInfoVO serverInfoVO;
                block10: {
                    if (Boolean.TRUE.equals(logItemEO.getStatus())) {
                        return;
                    }
                    NpContextHolder.setContext((NpContext)context);
                    String orgCode = logItemEO.getOrgCode();
                    serverInfoVO = (ReportDataSyncServerInfoVO)orgCode2ServerInfoMap.get(orgCode);
                    msg = null;
                    status = Boolean.FALSE;
                    semaphore.acquire();
                    if (serverInfoVO == null) {
                        msg = "\u5355\u4f4d[" + orgCode + "]\u4e0d\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u5217\u8868\u4e2d";
                        status = Boolean.FALSE;
                        break block9;
                    }
                    if (!Boolean.TRUE.equals(serverInfoVO.getStartFlag())) {
                        msg = "\u5355\u4f4d[" + orgCode + "]\u672a\u542f\u7528\u53c2\u6570\u540c\u6b65";
                        status = Boolean.FALSE;
                        break block9;
                    }
                    if (Boolean.FALSE.equals(serverInfoVO.getSyncParamFlag())) {
                        msg = "\u5355\u4f4d[" + orgCode + "]\u5df2\u5173\u95ed\u53c2\u6570\u540c\u6b65\u9009\u9879";
                        status = Boolean.FALSE;
                        break block9;
                    }
                    schedulerOpt = this.serviceManagerUploadTypeList.stream().filter(scheduler -> scheduler.code().equals(serverInfoVO.getSyncMethod())).findFirst();
                    if (schedulerOpt.isPresent()) break block10;
                    LOGGER.error("\u670d\u52a1\u5730\u5740" + serverInfoVO.getUrl() + "\u672a\u627e\u5230\u76ee\u6807\u670d\u52a1\u5668\u7c7b\u578b\u8c03\u5ea6\u5668\uff1a" + serverInfoVO.getSyncMethod());
                    semaphore.release();
                    logItemEO.setMsg(msg);
                    logItemEO.setOrgTitle(logItemEO.getOrgTitle());
                    logItemEO.setOrgCode(logItemEO.getOrgCode());
                    logItemEO.setStatus(status != false ? 1 : 0);
                    logItemEO.setNotSynchronized(0);
                    logItemEO.setSyncTime(new Date());
                    paramSyncService.updateAndCommit((ReportDataSyncIssuedLogItemEO)((Object)logItemEO));
                    NpContextHolder.clearContext();
                    return;
                }
                try {
                    ISyncMethodScheduler syncMethodScheduler = schedulerOpt.get();
                    content.setServerInfoVO(serverInfoVO);
                    boolean result = syncMethodScheduler.sync(content);
                    syncMethodScheduler.afterSync(result, content);
                    msg = "\u53c2\u6570\u540c\u6b65\u6210\u529f";
                    status = Boolean.TRUE;
                }
                catch (Exception e) {
                    try {
                        msg = "\u53c2\u6570\u540c\u6b65\u53d1\u751f\u5f02\u5e38\uff1a\u8be6\u60c5\uff1a" + e.getMessage();
                        status = Boolean.FALSE;
                        LOGGER.error(msg, e);
                        semaphore.release();
                        logItemEO.setMsg(msg);
                        logItemEO.setOrgTitle(logItemEO.getOrgTitle());
                        logItemEO.setOrgCode(logItemEO.getOrgCode());
                        logItemEO.setStatus(status != false ? 1 : 0);
                        logItemEO.setNotSynchronized(0);
                        logItemEO.setSyncTime(new Date());
                        paramSyncService.updateAndCommit((ReportDataSyncIssuedLogItemEO)((Object)logItemEO));
                    }
                    catch (Throwable throwable) {
                        semaphore.release();
                        logItemEO.setMsg(msg);
                        logItemEO.setOrgTitle(logItemEO.getOrgTitle());
                        logItemEO.setOrgCode(logItemEO.getOrgCode());
                        logItemEO.setStatus(status != false ? 1 : 0);
                        logItemEO.setNotSynchronized(0);
                        logItemEO.setSyncTime(new Date());
                        paramSyncService.updateAndCommit((ReportDataSyncIssuedLogItemEO)((Object)logItemEO));
                        NpContextHolder.clearContext();
                        throw throwable;
                    }
                    NpContextHolder.clearContext();
                }
            }
            semaphore.release();
            logItemEO.setMsg(msg);
            logItemEO.setOrgTitle(logItemEO.getOrgTitle());
            logItemEO.setOrgCode(logItemEO.getOrgCode());
            logItemEO.setStatus(status != false ? 1 : 0);
            logItemEO.setNotSynchronized(0);
            logItemEO.setSyncTime(new Date());
            paramSyncService.updateAndCommit((ReportDataSyncIssuedLogItemEO)((Object)logItemEO));
            NpContextHolder.clearContext();
        });
        List<ReportDataSyncIssuedLogItemEO> reportDataSyncIssuedLogItemEOS = this.issuedLogItemDao.listXfLogItemsByLogId(logEO.getId());
        List successLogItems = reportDataSyncIssuedLogItemEOS.stream().filter(eo -> Boolean.TRUE.equals(eo.getStatus())).collect(Collectors.toList());
        ContextUser user = context.getUser();
        logEO.setSyncTime(new Date());
        logEO.setSyncUserId(user.getId());
        logEO.setSyncUserName(user.getFullname());
        logEO.setParamStatus(ParamPackageStatus.STARTED.getCode());
        if (successLogItems.size() == reportDataSyncIssuedLogItemEOS.size()) {
            logEO.setSyncStatus(ParamSyncStatus.ALL_SUCCESS.getCode());
        } else if (successLogItems.size() == 0) {
            logEO.setSyncStatus(ParamSyncStatus.ALL_FAIL.getCode());
        } else {
            logEO.setSyncStatus(ParamSyncStatus.PARTIAL_SUCCESS.getCode());
        }
        this.issuedLogDao.update((BaseEntity)logEO);
        return true;
    }

    @Override
    public Boolean retryParamSyncByLogIdAndBatchType(String logId, RetryParamSyncBatchType batchType, ReportDataSyncParamProgressDataDTO progressDataDTO) {
        Set orgCodes;
        if (batchType == null) {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u6279\u91cf\u540c\u6b65\u5355\u4f4d\u8303\u56f4");
        }
        if (StringUtils.isEmpty((String)logId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u540c\u6b65\u8bb0\u5f55ID\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        ReportDataSyncIssuedLogEO logEO = (ReportDataSyncIssuedLogEO)this.issuedLogDao.get((Serializable)((Object)logId));
        if (logEO == null) {
            throw new BusinessRuntimeException("\u53c2\u6570\u540c\u6b65\u8bb0\u5f55\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        String paramStatus = logEO.getParamStatus();
        if (ParamPackageStatus.DEPRECATED.getCode().equals(paramStatus)) {
            throw new BusinessRuntimeException("\u5df2\u8fc7\u671f\u7684\u53c2\u6570\u4e0d\u5141\u8bb8\u540c\u6b65");
        }
        ReportDataSyncParamSyncServiceImpl paramSyncService = (ReportDataSyncParamSyncServiceImpl)SpringContextUtils.getBean(ReportDataSyncParamSyncServiceImpl.class);
        List<ReportDataSyncServerInfoVO> serverInfoVOS = this.paramDataSyncScheduler.getServerInfoVOList();
        if (CollectionUtils.isEmpty(serverInfoVOS)) {
            return false;
        }
        Map orgCode2ServerInfoMap = serverInfoVOS.stream().filter(vo -> Boolean.TRUE.equals(vo.getStartFlag()) && !Boolean.FALSE.equals(vo.getSyncParamFlag())).collect(Collectors.toConcurrentMap(ReportDataSyncServerInfoVO::getOrgCode, Functions.identity(), (e1, e2) -> e1));
        if (orgCode2ServerInfoMap.size() == 0) {
            throw new BusinessRuntimeException("\u7cfb\u7edf\u6ca1\u6709\u627e\u5230\u9700\u8981\u53c2\u6570\u540c\u6b65\u7684\u5355\u4f4d");
        }
        List<ReportDataSyncIssuedLogItemEO> logItemEOS = this.issuedLogItemDao.listXfLogItemsByLogId(logId);
        Map orgCode2LogItemEOMap = logItemEOS.stream().filter(eo -> {
            if (StringUtils.isEmpty((String)eo.getOrgCode())) {
                throw new BusinessRuntimeException("\u5355\u4f4d\u4e3a" + eo.getOrgTitle() + "\u7684\u53c2\u6570\u540c\u6b65\u660e\u7ec6\u8bb0\u5f55orgCode\u5c5e\u6027\u4e3a\u7a7a");
            }
            return true;
        }).collect(Collectors.toConcurrentMap(ReportDataSyncIssuedLogItemEO::getOrgCode, Functions.identity()));
        switch (batchType) {
            case ALL: {
                orgCodes = orgCode2ServerInfoMap.keySet();
                break;
            }
            case ALL_NOT_SYNC: {
                orgCodes = orgCode2ServerInfoMap.keySet();
                orgCodes.removeAll(orgCode2LogItemEOMap.keySet());
                break;
            }
            case ALL_SYNCED_FAIL: {
                HashSet orgCodesByAllSyncedFail = new HashSet();
                orgCode2LogItemEOMap.forEach((orgCode, logItem) -> {
                    if (!Boolean.TRUE.equals(logItem.getStatus())) {
                        orgCodesByAllSyncedFail.add(orgCode);
                    }
                });
                orgCodes = orgCodesByAllSyncedFail;
                break;
            }
            case ALL_SYNCED_FAIL_AND_NOT_SYNC: {
                ArrayList orgCodesByAllSyncedSuccess = new ArrayList();
                orgCode2LogItemEOMap.forEach((orgCode, logItem) -> {
                    if (Boolean.TRUE.equals(logItem.getStatus())) {
                        orgCodesByAllSyncedSuccess.add(orgCode);
                    }
                });
                orgCodes = orgCode2ServerInfoMap.keySet();
                orgCodes.removeAll(orgCodesByAllSyncedSuccess);
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u672a\u652f\u6301\u7684\u6279\u91cf\u540c\u6b65\u5355\u4f4d\u8303\u56f4\u7c7b\u578b" + (Object)((Object)batchType));
            }
        }
        Semaphore semaphore = new Semaphore(4);
        NpContext context = NpContextHolder.getContext();
        ContextUser user = NpContextHolder.getContext().getUser();
        if (CollectionUtils.isEmpty(orgCodes)) {
            this.modifyUnsynchronizedRecords(logEO.getId(), user);
            throw new BusinessRuntimeException("\u7cfb\u7edf\u6ca1\u6709\u627e\u5230\u7b26\u5408\u6761\u4ef6\u7684\u53c2\u6570\u540c\u6b65\u5355\u4f4d");
        }
        if (progressDataDTO == null) {
            progressDataDTO = new ReportDataSyncParamProgressDataDTO(logId);
            this.progressService.createProgressData((ProgressData)progressDataDTO);
            progressDataDTO.setProgressValueAndRefresh(0.1);
        }
        CommonFileDTO syncDesAttachFile = null;
        syncDesAttachFile = !StringUtils.isEmpty((String)logEO.getSyncDesAttachId()) ? this.commonFileService.queryOssFileByFileKey(logEO.getSyncDesAttachId()) : null;
        CommonFileDTO syncParamAttachFile = this.commonFileService.queryOssFileByFileKey(logEO.getSyncParamAttachId());
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        fileDTO.setMainFile((MultipartFile)syncParamAttachFile);
        fileDTO.setMainFileId(logEO.getSyncParamAttachId());
        fileDTO.setDesAttachFile((MultipartFile)syncDesAttachFile);
        fileDTO.setDesAttachFileId(logEO.getSyncDesAttachId());
        ReportSyncFileDTO ygFileDTO = new ReportSyncFileDTO();
        String ygParamAttachId = logEO.getYgParamAttachId();
        if (!StringUtils.isEmpty((String)ygParamAttachId)) {
            CommonFileDTO ygSyncParamAttachFile = this.commonFileService.queryOssFileByFileKey(logEO.getYgParamAttachId());
            ygFileDTO.setMainFile((MultipartFile)ygSyncParamAttachFile);
            ygFileDTO.setMainFileId(ygParamAttachId);
        }
        Date currDate = new Date();
        ReportDataSyncReceiveTaskVO receiveTaskVO = new ReportDataSyncReceiveTaskVO();
        receiveTaskVO.setId(logEO.getId());
        receiveTaskVO.setSyncDesAttachId(logEO.getSyncDesAttachId());
        receiveTaskVO.setSyncDesAttachTitle(logEO.getSyncDesAttachTitle());
        receiveTaskVO.setSyncParamAttachId(logEO.getSyncParamAttachId());
        receiveTaskVO.setXfTime(DateUtils.format((Date)currDate, (String)"yyyy-MM-dd HH:mm:ss"));
        receiveTaskVO.setSyncVersion(logEO.getSyncVersion());
        receiveTaskVO.setTaskId(logEO.getTaskId());
        receiveTaskVO.setTaskTitle(logEO.getTaskTitle());
        receiveTaskVO.setReceiveType(ReportDataReceiveType.ZXXF.getCode());
        AtomicDouble orgStepProgress = new AtomicDouble(new BigDecimal(((double)0.95f - progressDataDTO.getProgressValue()) / (double)orgCodes.size()).setScale(6, 1).doubleValue());
        ReportDataSyncParamProgressDataDTO finalProgressDataDTO = progressDataDTO;
        ParamDataContext content = new ParamDataContext();
        content.setReceiveTaskVO(receiveTaskVO);
        content.setFileDTO(fileDTO);
        content.setType(SyncTypeEnums.PARAMDATA);
        ((Stream)orgCodes.stream().parallel()).forEach(orgCode -> {
            Optional<ISyncMethodScheduler> schedulerOpt;
            Boolean status;
            String msg;
            ReportDataSyncServerInfoVO serverInfoVO;
            ReportDataSyncIssuedLogItemEO logItemEO;
            block18: {
                logItemEO = (ReportDataSyncIssuedLogItemEO)((Object)((Object)orgCode2LogItemEOMap.get(orgCode)));
                if (logItemEO != null && Boolean.TRUE.equals(logItemEO.getStatus())) {
                    return;
                }
                serverInfoVO = (ReportDataSyncServerInfoVO)orgCode2ServerInfoMap.get(orgCode);
                if (serverInfoVO == null) {
                    return;
                }
                NpContextHolder.setContext((NpContext)context);
                msg = null;
                status = Boolean.FALSE;
                semaphore.acquire();
                schedulerOpt = this.serviceManagerUploadTypeList.stream().filter(scheduler -> scheduler.code().equals(serverInfoVO.getSyncMethod())).findFirst();
                if (schedulerOpt.isPresent()) break block18;
                LOGGER.error("\u670d\u52a1\u5730\u5740" + serverInfoVO.getUrl() + "\u672a\u627e\u5230\u76ee\u6807\u670d\u52a1\u5668\u7c7b\u578b\u8c03\u5ea6\u5668\uff1a" + serverInfoVO.getSyncMethod());
                semaphore.release();
                finalProgressDataDTO.addProgressValueAndRefresh(orgStepProgress.get());
                if (logItemEO != null) {
                    logItemEO.setMsg(msg);
                    logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                    logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                    logItemEO.setStatus(status != false ? 1 : 0);
                    logItemEO.setNotSynchronized(0);
                    logItemEO.setSyncTime(new Date());
                    logItemEO.setSyncUserId(user.getId());
                    logItemEO.setSyncUserName(user.getFullname());
                    paramSyncService.updateAndCommit(logItemEO);
                } else {
                    logItemEO = new ReportDataSyncIssuedLogItemEO();
                    logItemEO.setId(UUIDUtils.newUUIDStr());
                    logItemEO.setMsg(msg);
                    logItemEO.setXfTaskId(logEO.getId());
                    logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                    logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                    logItemEO.setStatus(status != false ? 1 : 0);
                    logItemEO.setNotSynchronized(0);
                    logItemEO.setSyncTime(new Date());
                    logItemEO.setSyncUserId(user.getId());
                    logItemEO.setSyncUserName(user.getFullname());
                    paramSyncService.insertAndCommit(logItemEO);
                }
                NpContextHolder.clearContext();
                return;
            }
            try {
                ISyncMethodScheduler syncMethodScheduler = schedulerOpt.get();
                if (serverInfoVO.getFileFormat().equals(ReportFileFormat.YG.getCode())) {
                    content.setFileDTO(ygFileDTO);
                } else {
                    content.setFileDTO(fileDTO);
                }
                content.setServerInfoVO(serverInfoVO);
                status = syncMethodScheduler.sync(content);
                syncMethodScheduler.afterSync(status, content);
                if (!status.booleanValue()) {
                    throw new BusinessRuntimeException(content.getErrorLogs());
                }
                msg = "\u53c2\u6570\u540c\u6b65\u6210\u529f";
                semaphore.release();
            }
            catch (Exception e) {
                try {
                    msg = "\u53c2\u6570\u540c\u6b65\u53d1\u751f\u5f02\u5e38\uff1a\u8be6\u60c5\uff1a" + e.getMessage();
                    status = Boolean.FALSE;
                    LOGGER.error(msg, e);
                    semaphore.release();
                }
                catch (Throwable throwable) {
                    semaphore.release();
                    finalProgressDataDTO.addProgressValueAndRefresh(orgStepProgress.get());
                    if (logItemEO != null) {
                        logItemEO.setMsg(msg);
                        logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                        logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                        logItemEO.setStatus(status != false ? 1 : 0);
                        logItemEO.setNotSynchronized(0);
                        logItemEO.setSyncTime(new Date());
                        logItemEO.setSyncUserId(user.getId());
                        logItemEO.setSyncUserName(user.getFullname());
                        paramSyncService.updateAndCommit(logItemEO);
                    } else {
                        logItemEO = new ReportDataSyncIssuedLogItemEO();
                        logItemEO.setId(UUIDUtils.newUUIDStr());
                        logItemEO.setMsg(msg);
                        logItemEO.setXfTaskId(logEO.getId());
                        logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                        logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                        logItemEO.setStatus(status != false ? 1 : 0);
                        logItemEO.setNotSynchronized(0);
                        logItemEO.setSyncTime(new Date());
                        logItemEO.setSyncUserId(user.getId());
                        logItemEO.setSyncUserName(user.getFullname());
                        paramSyncService.insertAndCommit(logItemEO);
                    }
                    NpContextHolder.clearContext();
                    throw throwable;
                }
                finalProgressDataDTO.addProgressValueAndRefresh(orgStepProgress.get());
                if (logItemEO != null) {
                    logItemEO.setMsg(msg);
                    logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                    logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                    logItemEO.setStatus(status != false ? 1 : 0);
                    logItemEO.setNotSynchronized(0);
                    logItemEO.setSyncTime(new Date());
                    logItemEO.setSyncUserId(user.getId());
                    logItemEO.setSyncUserName(user.getFullname());
                    paramSyncService.updateAndCommit(logItemEO);
                } else {
                    logItemEO = new ReportDataSyncIssuedLogItemEO();
                    logItemEO.setId(UUIDUtils.newUUIDStr());
                    logItemEO.setMsg(msg);
                    logItemEO.setXfTaskId(logEO.getId());
                    logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                    logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                    logItemEO.setStatus(status != false ? 1 : 0);
                    logItemEO.setNotSynchronized(0);
                    logItemEO.setSyncTime(new Date());
                    logItemEO.setSyncUserId(user.getId());
                    logItemEO.setSyncUserName(user.getFullname());
                    paramSyncService.insertAndCommit(logItemEO);
                }
                NpContextHolder.clearContext();
            }
            finalProgressDataDTO.addProgressValueAndRefresh(orgStepProgress.get());
            if (logItemEO != null) {
                logItemEO.setMsg(msg);
                logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                logItemEO.setStatus(status != false ? 1 : 0);
                logItemEO.setNotSynchronized(0);
                logItemEO.setSyncTime(new Date());
                logItemEO.setSyncUserId(user.getId());
                logItemEO.setSyncUserName(user.getFullname());
                paramSyncService.updateAndCommit(logItemEO);
            } else {
                logItemEO = new ReportDataSyncIssuedLogItemEO();
                logItemEO.setId(UUIDUtils.newUUIDStr());
                logItemEO.setMsg(msg);
                logItemEO.setXfTaskId(logEO.getId());
                logItemEO.setOrgTitle(serverInfoVO.getOrgTitle());
                logItemEO.setOrgCode(serverInfoVO.getOrgCode());
                logItemEO.setStatus(status != false ? 1 : 0);
                logItemEO.setNotSynchronized(0);
                logItemEO.setSyncTime(new Date());
                logItemEO.setSyncUserId(user.getId());
                logItemEO.setSyncUserName(user.getFullname());
                paramSyncService.insertAndCommit(logItemEO);
            }
            NpContextHolder.clearContext();
        });
        finalProgressDataDTO.setProgressValueAndRefresh(0.95);
        List<ReportDataSyncIssuedLogItemEO> reportDataSyncIssuedLogItemEOS = this.issuedLogItemDao.listXfLogItemsByLogId(logEO.getId());
        List successLogItems = reportDataSyncIssuedLogItemEOS.stream().filter(eo -> Boolean.TRUE.equals(eo.getStatus())).collect(Collectors.toList());
        logEO.setSyncTime(currDate);
        logEO.setSyncUserId(user.getId());
        logEO.setSyncUserName(user.getFullname());
        logEO.setParamStatus(ParamPackageStatus.STARTED.getCode());
        if (successLogItems.size() == reportDataSyncIssuedLogItemEOS.size()) {
            logEO.setSyncStatus(ParamSyncStatus.ALL_SUCCESS.getCode());
        } else if (successLogItems.size() == 0) {
            logEO.setSyncStatus(ParamSyncStatus.ALL_FAIL.getCode());
        } else {
            logEO.setSyncStatus(ParamSyncStatus.PARTIAL_SUCCESS.getCode());
        }
        this.issuedLogDao.update((BaseEntity)logEO);
        this.issuedLogDao.deprecatedHistoryIssuedLog(logEO.getTaskId(), logEO.getSyncVersion());
        this.modifyUnsynchronizedRecords(logEO.getId(), user);
        finalProgressDataDTO.setProgressValueAndRefresh(1.0);
        return true;
    }

    public Boolean modifyUnsynchronizedRecords(String logId, ContextUser user) {
        List<ReportDataSyncServerInfoVO> serverInfoVOS = this.reportDataSyncServerListService.listServerInfos();
        if (CollectionUtils.isEmpty(serverInfoVOS)) {
            return false;
        }
        List orgCodeAll = serverInfoVOS.stream().map(ReportDataSyncServerInfoVO::getOrgCode).collect(Collectors.toList());
        List<ReportDataSyncIssuedLogItemEO> logItemEOS = this.issuedLogItemDao.listXfLogItemsByLogId(logId);
        Map orgCode2LogItemEOMap = logItemEOS.stream().filter(eo -> {
            if (StringUtils.isEmpty((String)eo.getOrgCode())) {
                throw new BusinessRuntimeException("\u5355\u4f4d\u4e3a" + eo.getOrgTitle() + "\u7684\u53c2\u6570\u540c\u6b65\u660e\u7ec6\u8bb0\u5f55orgCode\u5c5e\u6027\u4e3a\u7a7a");
            }
            return true;
        }).collect(Collectors.toConcurrentMap(ReportDataSyncIssuedLogItemEO::getOrgCode, Functions.identity()));
        for (int i = 0; i < logItemEOS.size(); ++i) {
            ReportDataSyncIssuedLogItemEO logItemEO = logItemEOS.get(i);
            if (logItemEO.getNotSynchronized() == 0) continue;
            Boolean ifNotExist = true;
            for (int j = 0; j < orgCodeAll.size(); ++j) {
                if (!((String)orgCodeAll.get(j)).equals(logItemEO.getOrgCode())) continue;
                ifNotExist = false;
                break;
            }
            if (!ifNotExist.booleanValue()) continue;
            this.issuedLogItemDao.delete((BaseEntity)logItemEO);
        }
        ReportDataSyncParamSyncServiceImpl paramSyncService = (ReportDataSyncParamSyncServiceImpl)SpringContextUtils.getBean(ReportDataSyncParamSyncServiceImpl.class);
        for (int i = 0; i < serverInfoVOS.size(); ++i) {
            ReportDataSyncServerInfoVO vo = serverInfoVOS.get(i);
            if (Boolean.TRUE.equals(vo.getStartFlag()) && !Boolean.FALSE.equals(vo.getSyncParamFlag()) || orgCode2LogItemEOMap.get(vo.getOrgCode()) != null) continue;
            ReportDataSyncIssuedLogItemEO logItemEO = new ReportDataSyncIssuedLogItemEO();
            logItemEO.setId(UUIDUtils.newUUIDStr());
            logItemEO.setMsg("----");
            logItemEO.setXfTaskId(logId);
            logItemEO.setOrgTitle(vo.getOrgTitle());
            logItemEO.setOrgCode(vo.getOrgCode());
            logItemEO.setStatus(0);
            logItemEO.setNotSynchronized(1);
            logItemEO.setSyncTime(new Date());
            logItemEO.setSyncUserId(user.getId());
            logItemEO.setSyncUserName(user.getFullname());
            paramSyncService.insertAndCommit(logItemEO);
        }
        return null;
    }

    public void checkOrgCodeOnly(String orgCode) {
        List<ReportDataSyncServerInfoVO> serverInfoVOS = this.reportDataSyncServerListService.listServerInfos();
        if (CollectionUtils.isEmpty(serverInfoVOS)) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + orgCode + "]\u4e0d\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u5217\u8868\u4e2d");
        }
        Map orgCode2ServerInfoMap = serverInfoVOS.stream().collect(Collectors.toConcurrentMap(ReportDataSyncServerInfoVO::getOrgCode, Functions.identity()));
        ReportDataSyncServerInfoVO serverInfoVO = (ReportDataSyncServerInfoVO)orgCode2ServerInfoMap.get(orgCode);
        if (serverInfoVO == null) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + orgCode + "]\u4e0d\u5728\u76ee\u6807\u670d\u52a1\u5668\u7ba1\u7406\u5217\u8868\u4e2d");
        }
        if (!Boolean.TRUE.equals(serverInfoVO.getStartFlag())) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + orgCode + "]\u672a\u542f\u7528\u53c2\u6570\u540c\u6b65");
        }
        if (Boolean.FALSE.equals(serverInfoVO.getSyncParamFlag())) {
            throw new BusinessRuntimeException("\u5355\u4f4d[" + orgCode + "]\u5df2\u5173\u95ed\u53c2\u6570\u540c\u6b65\u9009\u9879");
        }
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public Boolean insertAndCommit(ReportDataSyncIssuedLogItemEO logItemEO) {
        this.issuedLogItemDao.add((BaseEntity)logItemEO);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public Boolean updateAndCommit(ReportDataSyncIssuedLogItemEO logItemEO) {
        this.issuedLogItemDao.update((BaseEntity)logItemEO);
        return Boolean.TRUE;
    }
}

