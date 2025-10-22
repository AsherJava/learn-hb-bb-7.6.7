/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.transfer.engine.Desc
 *  com.jiuqi.bi.transfer.engine.TransferEngine
 *  com.jiuqi.bi.transfer.engine.TransferUtils
 *  com.jiuqi.bi.transfer.engine.intf.IFileRecorder
 *  com.jiuqi.bi.transfer.engine.intf.ITransferContext
 *  com.jiuqi.bi.util.type.GUID
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.base.util.ZipUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.gcreport.common.np.NpReportQueryProvider
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nvwa.transfer.TransferContext
 *  com.jiuqi.nvwa.transfer.TransferFileRecorder
 *  com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.transfer.engine.Desc;
import com.jiuqi.bi.transfer.engine.TransferEngine;
import com.jiuqi.bi.transfer.engine.TransferUtils;
import com.jiuqi.bi.transfer.engine.intf.IFileRecorder;
import com.jiuqi.bi.transfer.engine.intf.ITransferContext;
import com.jiuqi.bi.util.type.GUID;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.base.util.ZipUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveLogDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveLogItemDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncReceiveTaskDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncParamFileDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveLogItemEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncReceiveTaskEO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataReceiveType;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncParamUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncIssuedLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogItemVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportParamSyncTaskOptionVO;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nvwa.transfer.TransferContext;
import com.jiuqi.nvwa.transfer.TransferFileRecorder;
import com.jiuqi.va.paramsync.domain.VaParamSyncMultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ReportDataSyncParamUpdateServiceImpl
implements ReportDataSyncParamUpdateService {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncParamUpdateServiceImpl.class);
    @Autowired
    private ReportDataSyncReceiveTaskDao receiveTaskDao;
    @Autowired
    private ReportDataSyncReceiveLogDao receiveLogDao;
    @Autowired
    private ReportDataSyncReceiveLogItemDao receiveLogItemDao;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataSyncServerListService serverInfoService;
    private NpReportQueryProvider iRunTimeViewController;
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;
    @Autowired
    List<ISyncMethodScheduler> syncMethodSchedulerList;

    @Override
    public Boolean addTask(ReportDataSyncReceiveTaskVO receiveTaskVO) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = this.convertVO2EO(receiveTaskVO);
        this.receiveTaskDao.add((BaseEntity)receiveTaskEO);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateTask(ReportDataSyncReceiveTaskVO receiveTaskVO) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = this.convertVO2EO(receiveTaskVO);
        this.receiveTaskDao.update((BaseEntity)receiveTaskEO);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public Boolean addTaskAndCommit(ReportDataSyncReceiveTaskVO receiveTaskVO) {
        return this.addTask(receiveTaskVO);
    }

    @Override
    public PageInfo<ReportDataSyncReceiveTaskVO> listAllTasks(Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncReceiveTaskEO> pageInfo = this.receiveTaskDao.listAllReceiveTask(pageSize, pageNum);
        List vos = pageInfo.getList().stream().map(taskEo -> {
            ReportDataSyncReceiveTaskVO receiveTaskVO = this.convertEO2VO((ReportDataSyncReceiveTaskEO)((Object)taskEo));
            return receiveTaskVO;
        }).collect(Collectors.toList());
        PageInfo pageInfoVO = PageInfo.of(vos, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public List<ReportDataSyncIssuedLogVO> fetchTargetSyncParamTaskInfos() {
        List<ReportDataSyncServerInfoVO> serverList = this.serverInfoService.listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverList)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u7c7b\u578b\u4e3a\u6570\u636e\u7684\u76ee\u6807\u670d\u52a1\u5668\u4fe1\u606f");
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
        for (ISyncMethodScheduler scheduler : this.syncMethodSchedulerList) {
            if (!scheduler.code().equals(type) || scheduler.getHandler() == null) continue;
            extendService = scheduler.getHandler();
            break;
        }
        if (extendService != null) {
            return extendService.fetchTargetSyncParamTaskInfos(serverInfoVO);
        }
        return new ArrayList<ReportDataSyncIssuedLogVO>();
    }

    @Override
    public Boolean fetchTargetSyncParam(ReportDataSyncIssuedLogVO issuedLogVO) {
        List<ReportDataSyncReceiveTaskEO> receiveTaskEOS = this.receiveTaskDao.listReceiveTaskBySyncVersion(issuedLogVO.getTaskId(), issuedLogVO.getSyncVersion());
        if (!CollectionUtils.isEmpty(receiveTaskEOS)) {
            throw new BusinessRuntimeException(String.format("\u4efb\u52a1\u3010%1$s\u3011\u7248\u672c\u3010%2$s\u3011\u7684\u4efb\u52a1\u53c2\u6570\u5df2\u5b58\u5728,\u65e0\u6cd5\u8fdb\u884c\u53c2\u6570\u62c9\u53d6", issuedLogVO.getTaskTitle(), issuedLogVO.getSyncVersion()));
        }
        List<ReportDataSyncServerInfoVO> serverList = this.serverInfoService.listServerInfos(SyncTypeEnums.REPORTDATA);
        if (CollectionUtils.isEmpty(serverList)) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u7c7b\u578b\u4e3a\u6570\u636e\u7684\u76ee\u6807\u670d\u52a1\u5668\u4fe1\u606f");
        }
        ReportDataSyncServerInfoVO serverInfoVO = serverList.get(0);
        String type = serverInfoVO.getSyncMethod();
        MultilevelExtendHandler extendService = ((ISyncMethodScheduler)this.syncMethodSchedulerList.stream().filter(v -> v.code().equals(type)).findFirst().orElse(null)).getHandler();
        if (extendService == null) {
            return false;
        }
        ReportDataSyncParamFileDTO dataSyncParamFileDTO = extendService.fetchSyncParamFiles(serverInfoVO, issuedLogVO);
        if (dataSyncParamFileDTO.getSyncParamAttachFile() == null) {
            throw new BusinessRuntimeException("\u76ee\u6807\u670d\u52a1\u5668\u7684\u53c2\u6570\u5305\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u8fdb\u884c\u53c2\u6570\u62c9\u53d6\u3002");
        }
        if (!StringUtils.isEmpty((String)dataSyncParamFileDTO.getSyncDesAttachId())) {
            this.commonFileService.uploadFileToOss((MultipartFile)dataSyncParamFileDTO.getSyncDesAttachFile(), dataSyncParamFileDTO.getSyncDesAttachId());
        }
        this.commonFileService.uploadFileToOss(dataSyncParamFileDTO.getSyncParamAttachFile(), dataSyncParamFileDTO.getSyncParamAttachId());
        ReportDataSyncReceiveTaskVO receiveTaskVO = new ReportDataSyncReceiveTaskVO();
        receiveTaskVO.setId(UUIDUtils.newUUIDStr());
        receiveTaskVO.setReceiveType(ReportDataReceiveType.ZXHQ.getCode());
        receiveTaskVO.setXfTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        receiveTaskVO.setSyncTime(null);
        receiveTaskVO.setSyncVersion(issuedLogVO.getSyncVersion());
        receiveTaskVO.setSyncDesAttachTitle(issuedLogVO.getSyncDesAttachTitle());
        receiveTaskVO.setTaskCode(issuedLogVO.getTaskCode());
        receiveTaskVO.setTaskId(issuedLogVO.getTaskId());
        receiveTaskVO.setTaskTitle(issuedLogVO.getTaskTitle());
        receiveTaskVO.setSyncDesAttachId(dataSyncParamFileDTO.getSyncDesAttachId());
        receiveTaskVO.setSyncParamAttachId(dataSyncParamFileDTO.getSyncParamAttachId());
        this.addTask(receiveTaskVO);
        return true;
    }

    @Override
    public void downloadParamUpateInstructions(HttpServletRequest request, HttpServletResponse response, String receiveTaskId) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = (ReportDataSyncReceiveTaskEO)this.receiveTaskDao.get((Serializable)((Object)receiveTaskId));
        String syncDesAttachId = receiveTaskEO.getSyncDesAttachId();
        if (StringUtils.isEmpty((String)syncDesAttachId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4e0b\u8f7d\u3002");
        }
        this.commonFileService.downloadOssFile(request, response, syncDesAttachId);
    }

    @Override
    public void downloadParam(HttpServletRequest request, HttpServletResponse response, String receiveTaskId) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = (ReportDataSyncReceiveTaskEO)this.receiveTaskDao.get((Serializable)((Object)receiveTaskId));
        String syncParamAttachId = receiveTaskEO.getSyncParamAttachId();
        if (StringUtils.isEmpty((String)syncParamAttachId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u5305\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4e0b\u8f7d\u3002");
        }
        this.commonFileService.downloadOssFile(request, response, syncParamAttachId);
    }

    @Override
    public void downloadParamAndUpateInstructions(HttpServletRequest request, HttpServletResponse response, String receiveTaskId) {
        String syncDesAttachId;
        ReportDataSyncReceiveTaskEO receiveTaskEO = (ReportDataSyncReceiveTaskEO)this.receiveTaskDao.get((Serializable)((Object)receiveTaskId));
        ArrayList<CommonFileDTO> files = new ArrayList<CommonFileDTO>();
        ArrayList<String> fileNames = new ArrayList<String>();
        String syncParamAttachId = receiveTaskEO.getSyncParamAttachId();
        if (!StringUtils.isEmpty((String)syncParamAttachId)) {
            CommonFileDTO paramsFile = this.commonFileService.queryOssFileByFileKey("COMMON_FILE_OSS", syncParamAttachId);
            files.add(paramsFile);
            fileNames.add(receiveTaskEO.getTaskTitle() + ".zip");
        }
        if (!StringUtils.isEmpty((String)(syncDesAttachId = receiveTaskEO.getSyncDesAttachId()))) {
            CommonFileDTO instructionsFile = this.commonFileService.queryOssFileByFileKey("COMMON_FILE_OSS", syncDesAttachId);
            files.add(instructionsFile);
            fileNames.add(receiveTaskEO.getSyncDesAttachTitle());
        }
        if (CollectionUtils.isEmpty(files)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u5305\u548c\u53c2\u6570\u66f4\u65b0\u8bf4\u660e\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u4e0b\u8f7d\u3002");
        }
        String zipName = receiveTaskEO.getTaskTitle() + "_\u53c2\u6570\u5305";
        try (ServletOutputStream outputStream = response.getOutputStream();
             ZipOutputStream zos = new ZipOutputStream((OutputStream)outputStream, Charset.forName("GBK"));){
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(zipName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setCharacterEncoding("UTF-8");
            byte[] buf = new byte[1024];
            for (int i = 0; i < files.size(); ++i) {
                try (InputStream inputStream = ((CommonFileDTO)files.get(i)).getInputStream();){
                    int len;
                    ZipEntry zipEntry = new ZipEntry((String)fileNames.get(i));
                    zos.putNextEntry(zipEntry);
                    while ((len = inputStream.read(buf)) != -1) {
                        zos.write(buf, 0, len);
                    }
                    continue;
                }
                catch (IOException e) {
                    throw new BusinessRuntimeException((Throwable)e);
                }
                finally {
                    try {
                        zos.closeEntry();
                    }
                    catch (IOException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
            zos.closeEntry();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Boolean updateReportParamsData(String receiveTaskId, Boolean publish) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = (ReportDataSyncReceiveTaskEO)this.receiveTaskDao.get((Serializable)((Object)receiveTaskId));
        Boolean syncStatus = Boolean.FALSE;
        TreeSet<String> importLogs = new TreeSet<String>();
        try {
            ReportDataSyncParamUtils.importParam(importLogs, receiveTaskEO, publish);
            syncStatus = Boolean.TRUE;
            List<ReportDataSyncReceiveTaskEO> lastReceiveTaskEO = this.receiveTaskDao.listReceiveTaskByTaskId(receiveTaskEO.getTaskId(), receiveTaskId);
            lastReceiveTaskEO.forEach(eo -> eo.setAvailable(0));
            this.receiveTaskDao.updateBatch(lastReceiveTaskEO);
            importLogs.add("\u53c2\u6570\u66f4\u65b0\u5b8c\u6210");
            receiveTaskEO.setSyncTime(new Date());
            receiveTaskEO.setSyncStatus(syncStatus != false ? 1 : 0);
        }
        catch (Throwable e) {
            try {
                LOGGER.error(e.getMessage(), e);
                syncStatus = Boolean.FALSE;
                importLogs.add("\u53c2\u6570\u66f4\u65b0\u5931\u8d25\uff1a" + e.getMessage());
                receiveTaskEO.setSyncTime(new Date());
                receiveTaskEO.setSyncStatus(syncStatus != false ? 1 : 0);
            }
            catch (Throwable throwable) {
                receiveTaskEO.setSyncTime(new Date());
                receiveTaskEO.setSyncStatus(syncStatus != false ? 1 : 0);
                this.receiveTaskDao.update((BaseEntity)receiveTaskEO);
                StringBuilder syncLogInfoBuilder = new StringBuilder();
                importLogs.stream().forEach(importLog -> syncLogInfoBuilder.append((String)importLog).append("\n"));
                this.insertParamUpdateLog(receiveTaskEO, syncStatus, syncLogInfoBuilder.toString());
                LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u66f4\u65b0\u7ba1\u7406", (String)String.format("\u66f4\u65b0\u53c2\u6570-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", receiveTaskEO.getTaskTitle(), receiveTaskEO.getSyncVersion()), (String)"");
                throw throwable;
            }
            this.receiveTaskDao.update((BaseEntity)receiveTaskEO);
            StringBuilder syncLogInfoBuilder = new StringBuilder();
            importLogs.stream().forEach(importLog -> syncLogInfoBuilder.append((String)importLog).append("\n"));
            this.insertParamUpdateLog(receiveTaskEO, syncStatus, syncLogInfoBuilder.toString());
            LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u66f4\u65b0\u7ba1\u7406", (String)String.format("\u66f4\u65b0\u53c2\u6570-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", receiveTaskEO.getTaskTitle(), receiveTaskEO.getSyncVersion()), (String)"");
        }
        this.receiveTaskDao.update((BaseEntity)receiveTaskEO);
        StringBuilder syncLogInfoBuilder = new StringBuilder();
        importLogs.stream().forEach(importLog -> syncLogInfoBuilder.append((String)importLog).append("\n"));
        this.insertParamUpdateLog(receiveTaskEO, syncStatus, syncLogInfoBuilder.toString());
        LogHelper.info((String)"\u5408\u5e76-\u53c2\u6570\u66f4\u65b0\u7ba1\u7406", (String)String.format("\u66f4\u65b0\u53c2\u6570-%1$s\u4efb\u52a1%2$s\u7248\u672c\u53f7", receiveTaskEO.getTaskTitle(), receiveTaskEO.getSyncVersion()), (String)"");
        return Boolean.TRUE;
    }

    private void insertParamUpdateLog(ReportDataSyncReceiveTaskEO receiveTaskEO, Boolean syncStatus, String syncLogInfo) {
        ReportDataSyncReceiveLogEO reportDataSyncReceiveLogEO = new ReportDataSyncReceiveLogEO();
        BeanUtils.copyProperties((Object)receiveTaskEO, (Object)reportDataSyncReceiveLogEO);
        reportDataSyncReceiveLogEO.setId(UUIDUtils.newUUIDStr());
        reportDataSyncReceiveLogEO.setSyncStatus(syncStatus != false ? 1 : 0);
        reportDataSyncReceiveLogEO.setReceiveTaskId(receiveTaskEO.getId());
        ContextUser user = NpContextHolder.getContext().getUser();
        if (user != null) {
            reportDataSyncReceiveLogEO.setSyncUserId(user.getId());
            reportDataSyncReceiveLogEO.setSyncUserName(user.getName());
        }
        this.receiveLogDao.add((BaseEntity)reportDataSyncReceiveLogEO);
        ReportDataSyncReceiveLogItemEO reportDataSyncReceiveLogItemEO = new ReportDataSyncReceiveLogItemEO();
        reportDataSyncReceiveLogItemEO.setId(UUIDUtils.newUUIDStr());
        reportDataSyncReceiveLogItemEO.setReceiveTaskLogId(reportDataSyncReceiveLogEO.getId());
        reportDataSyncReceiveLogItemEO.setSyncLogInfo(syncLogInfo);
        this.receiveLogItemDao.add((BaseEntity)reportDataSyncReceiveLogItemEO);
    }

    @Override
    public PageInfo<ReportDataSyncReceiveLogVO> listReceiveTaskLogs(Integer pageSize, Integer pageNum) {
        PageInfo<ReportDataSyncReceiveLogEO> pageInfo = this.receiveLogDao.listReceiveTaskLogs(pageSize, pageNum);
        List vos = pageInfo.getList().stream().map(receiveLog -> {
            Date syncTime = receiveLog.getSyncTime();
            Date xfTime = receiveLog.getXfTime();
            ReportDataSyncReceiveLogVO receiveLogVO = new ReportDataSyncReceiveLogVO();
            BeanUtils.copyProperties(receiveLog, receiveLogVO);
            String available = this.receiveTaskDao.getAvailable(receiveLogVO.getReceiveTaskId());
            if (available == null) {
                receiveLogVO.setAvailable(Integer.valueOf(1));
            } else if (available.equals("0")) {
                receiveLogVO.setAvailable(Integer.valueOf(0));
            } else {
                receiveLogVO.setAvailable(Integer.valueOf(1));
            }
            receiveLogVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
            receiveLogVO.setSyncVersion(StringUtils.isEmpty((String)receiveLogVO.getSyncVersion()) ? "--" : receiveLogVO.getSyncVersion());
            receiveLogVO.setXfTime(DateUtils.format((Date)xfTime, (String)"yyyy-MM-dd HH:mm:ss"));
            receiveLogVO.setReceiveType(StringUtils.isEmpty((String)receiveLog.getReceiveType()) ? ReportDataReceiveType.ZXXF.getCode() : receiveLog.getReceiveType());
            return receiveLogVO;
        }).collect(Collectors.toList());
        PageInfo pageInfoVO = PageInfo.of(vos, (int)pageInfo.getPageNum(), (int)pageInfo.getPageSize(), (int)pageInfo.getSize());
        return pageInfoVO;
    }

    @Override
    public List<ReportDataSyncReceiveLogItemVO> listReceiveLogItemsByLogId(String syncLogInfoId) {
        List<ReportDataSyncReceiveLogItemEO> logItems = this.receiveLogItemDao.listReceiveLogItemsByLogId(syncLogInfoId);
        return logItems.stream().map(logItem -> {
            ReportDataSyncReceiveLogItemVO logItemVO = new ReportDataSyncReceiveLogItemVO();
            BeanUtils.copyProperties(logItem, logItemVO);
            return logItemVO;
        }).collect(Collectors.toList());
    }

    private ReportDataSyncReceiveTaskVO convertEO2VO(ReportDataSyncReceiveTaskEO taskEo) {
        ReportDataSyncReceiveTaskVO receiveTaskVO = new ReportDataSyncReceiveTaskVO();
        BeanUtils.copyProperties((Object)taskEo, receiveTaskVO);
        Date syncTime = taskEo.getSyncTime();
        Date xfTime = taskEo.getXfTime();
        if (!ObjectUtils.isEmpty(syncTime)) {
            receiveTaskVO.setSyncTime(DateUtils.format((Date)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        if (!ObjectUtils.isEmpty(xfTime)) {
            receiveTaskVO.setXfTime(DateUtils.format((Date)xfTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        receiveTaskVO.setSyncVersion(StringUtils.isEmpty((String)taskEo.getSyncVersion()) ? "--" : taskEo.getSyncVersion());
        receiveTaskVO.setReceiveType(StringUtils.isEmpty((String)receiveTaskVO.getReceiveType()) ? ReportDataReceiveType.ZXXF.getCode() : receiveTaskVO.getReceiveType());
        receiveTaskVO.setAvailable(Integer.valueOf(taskEo.getAvailable() == null ? 1 : taskEo.getAvailable()));
        return receiveTaskVO;
    }

    private ReportDataSyncReceiveTaskEO convertVO2EO(ReportDataSyncReceiveTaskVO taskVO) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = new ReportDataSyncReceiveTaskEO();
        BeanUtils.copyProperties(taskVO, (Object)receiveTaskEO);
        String syncTime = taskVO.getSyncTime();
        String xfTime = taskVO.getXfTime();
        if (!ObjectUtils.isEmpty(syncTime)) {
            receiveTaskEO.setSyncTime(DateUtils.parse((String)syncTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        if (!ObjectUtils.isEmpty(xfTime)) {
            receiveTaskEO.setXfTime(DateUtils.parse((String)xfTime, (String)"yyyy-MM-dd HH:mm:ss"));
        }
        receiveTaskEO.setAvailable(1);
        return receiveTaskEO;
    }

    @Override
    public ReportDataSyncReceiveLogVO queryLatestSyncSuccessReceiveTaskByTaskId(String taskId) {
        ReportDataSyncReceiveLogEO receiveLogEO = this.receiveLogDao.queryLatestSyncSuccessReceiveTaskByTaskId(taskId);
        if (receiveLogEO == null) {
            return null;
        }
        ReportDataSyncReceiveLogVO logItemVO = new ReportDataSyncReceiveLogVO();
        BeanUtils.copyProperties((Object)receiveLogEO, logItemVO);
        return logItemVO;
    }

    @Override
    public Boolean importReportParamFile(MultipartFile importFile, ImportContext context) {
        return this.importReportParamFile(importFile, null, context);
    }

    @Override
    public Boolean importReportParamFile(MultipartFile importFile, MultipartFile desAttachFile, ImportContext context) {
        ZipEntry entry2;
        Throwable throwable;
        String rootPath = System.getProperty(FilenameUtils.normalize("java.io.tmpdir")) + File.separator + "reportparamsync" + File.separator + "importparampackage" + File.separator + LocalDate.now();
        String filePath = rootPath + File.separator + importFile.getOriginalFilename();
        File file = new File(FilenameUtils.normalize(filePath));
        try {
            FileUtils.forceMkdirParent(file);
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        try (InputStream inputStream = importFile.getInputStream();){
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
        catch (IOException e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        boolean hasParams = false;
        String paramsPackageName = "";
        boolean onlyParams = false;
        try (FileInputStream inputStream = new FileInputStream(file);){
            throwable = null;
            try (ZipInputStream zipInputStream = new ZipInputStream((InputStream)inputStream, Charset.forName("GBK"));){
                while ((entry2 = zipInputStream.getNextEntry()) != null) {
                    if (entry2.getName().endsWith(".zip")) {
                        hasParams = true;
                        paramsPackageName = entry2.getName();
                    }
                    if (onlyParams || !entry2.getName().equals("ReportParamMeta.JSON")) continue;
                    onlyParams = true;
                }
                zipInputStream.closeEntry();
            }
            catch (Throwable entry2) {
                throwable = entry2;
                throw entry2;
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        if (!hasParams && !onlyParams) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u5931\u8d25\uff1a\u53c2\u6570\u5305\u5185\u5bb9\u4e0d\u6b63\u786e");
        }
        MultipartFile paramsFile = importFile;
        MultipartFile instructionFile = null;
        if (hasParams && !onlyParams) {
            try {
                throwable = null;
                try (ZipFile zipFile = new ZipFile(file, Charset.forName("GBK"));){
                    InputStream inputStream = ZipUtils.get((ZipFile)zipFile, (String)paramsPackageName);
                    byte[] buffer = IOUtils.toByteArray(inputStream);
                    paramsFile = new VaParamSyncMultipartFile("multipartFile", paramsPackageName, "multipart/form-data; charset=ISO-8859-1", buffer);
                    try (FileInputStream inputStream1 = new FileInputStream(file);
                         ZipInputStream zipInputStream = new ZipInputStream((InputStream)inputStream1, Charset.forName("GBK"));){
                        while ((entry2 = zipInputStream.getNextEntry()) != null) {
                            if (entry2.getName().endsWith(".zip")) continue;
                            InputStream instructionInputStream = ZipUtils.get((ZipFile)new ZipFile(file, Charset.forName("GBK")), (String)entry2.getName());
                            byte[] instructionBuffer = IOUtils.toByteArray(instructionInputStream);
                            instructionFile = new CommonFileDTO("multipartFile", entry2.getName(), "multipart/form-data; charset=ISO-8859-1", instructionBuffer);
                            break;
                        }
                        zipInputStream.closeEntry();
                    }
                    catch (IOException e) {
                        throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
                    }
                }
                catch (Throwable entry3) {
                    throwable = entry3;
                    throw entry3;
                }
            }
            catch (IOException e) {
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
        }
        byte[] bytes = null;
        try {
            bytes = ZipUtils.unzipFileBytesAndDeleteTempFileOnExit((MultipartFile)paramsFile, (String)"ReportParamMeta.JSON");
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (bytes == null) {
            throw new BusinessRuntimeException("\u53c2\u6570\u5305\u5185\u5bb9\u4e0d\u6b63\u786e\uff0c\u7f3a\u5c11\u6587\u4ef6\uff1aReportParamMeta.JSON");
        }
        ReportDataSyncReceiveTaskVO receiveTaskVO = (ReportDataSyncReceiveTaskVO)JsonUtils.readValue((byte[])bytes, ReportDataSyncReceiveTaskVO.class);
        receiveTaskVO.setId(UUIDOrderUtils.newUUIDStr());
        receiveTaskVO.setXfTime(DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"));
        List<ReportDataSyncReceiveTaskEO> receiveTaskEOS = this.receiveTaskDao.listReceiveTaskBySyncVersion(receiveTaskVO.getTaskId(), receiveTaskVO.getSyncVersion());
        if (!CollectionUtils.isEmpty(receiveTaskEOS)) {
            throw new BusinessRuntimeException(String.format("\u4efb\u52a1\u3010%1$s\u3011\u7248\u672c\u3010%2$s\u3011\u7684\u4efb\u52a1\u53c2\u6570\u5df2\u5b58\u5728,\u65e0\u6cd5\u91cd\u590d\u5bfc\u5165", receiveTaskVO.getTaskTitle(), receiveTaskVO.getSyncVersion()));
        }
        this.commonFileService.uploadFileToOss(paramsFile, receiveTaskVO.getSyncParamAttachId());
        if (desAttachFile != null && instructionFile == null) {
            instructionFile = desAttachFile;
        }
        if (instructionFile != null) {
            if (StringUtils.isEmpty((String)receiveTaskVO.getSyncDesAttachId())) {
                receiveTaskVO.setSyncDesAttachId(UUIDOrderUtils.newUUIDStr());
                receiveTaskVO.setSyncDesAttachTitle(instructionFile.getOriginalFilename());
            }
            this.commonFileService.uploadFileToOss(instructionFile, receiveTaskVO.getSyncDesAttachId());
        }
        this.addTask(receiveTaskVO);
        return true;
    }

    @Override
    public List<ReportParamSyncTaskOptionVO> listAllParamSyncTasks() {
        List<String> taskIds = this.receiveTaskDao.listAllReceiveTaskIds();
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        List<TaskDefine> filtedTaskDefines = allTaskDefines.stream().filter(Objects::nonNull).filter(taskDefine -> this.authorityProvider.canReadTask(taskDefine.getKey())).collect(Collectors.toList());
        ArrayList<ReportParamSyncTaskOptionVO> tasks = new ArrayList<ReportParamSyncTaskOptionVO>();
        filtedTaskDefines.forEach(task -> {
            if (taskIds.contains(task.getKey())) {
                tasks.add(new ReportParamSyncTaskOptionVO(task.getKey(), task.getTitle(), task.getDataScheme(), task.getFromPeriod(), task.getToPeriod()));
            }
        });
        return tasks;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<Object> listComparisonResults(String logId) {
        ReportDataSyncReceiveTaskEO receiveTaskEO = (ReportDataSyncReceiveTaskEO)this.receiveTaskDao.get((Serializable)((Object)logId));
        String syncParamAttachId = receiveTaskEO.getSyncParamAttachId();
        CommonFileDTO multipartFile = this.commonFileService.queryOssFileByFileKey(syncParamAttachId);
        JSONArray dataArray = new JSONArray();
        String rootPath = System.getProperty(FilenameUtils.normalize("java.io.tmpdir")) + File.separator + "reportparamsync" + File.separator + "importparam" + File.separator + LocalDate.now() + File.separator + syncParamAttachId;
        ObjectStorageService service = null;
        String fileId = GUID.newGUID();
        try {
            String filePath = rootPath + File.separator + receiveTaskEO.getTaskTitle() + ".zip";
            File file = new File(FilenameUtils.normalize(filePath));
            FileUtils.forceMkdirParent(file);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
            InputStream inputStream = ReportDataSyncParamUtils.getParamInputStream(file);
            if (inputStream == null) {
                List list = dataArray.toList();
                return list;
            }
            service = ObjectStorageManager.getInstance().createObjectService("TEMP");
            service.upload(fileId, inputStream);
            TransferFileRecorder recorder = new TransferFileRecorder(fileId);
            TransferEngine engine = new TransferEngine();
            Desc desc = engine.getDescInfo((IFileRecorder)recorder);
            String userId = NpContextHolder.getContext().getUserId();
            TransferContext context = new TransferContext(userId);
            dataArray = TransferUtils.buildTreeTableData((ITransferContext)context, (Desc)desc);
            this.changeNodeData(dataArray);
            boolean bl = false;
        }
        catch (Throwable e) {
            LOGGER.error("\u751f\u6210\u5bf9\u6bd4\u6587\u4ef6\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        finally {
            if (!StringUtils.isEmpty((String)rootPath)) {
                ReportDataSyncUtils.delFiles(new File(rootPath));
            }
            if (service != null) {
                try {
                    service.deleteObject(fileId);
                }
                catch (Throwable e) {
                    LOGGER.error("id\u4e3a" + fileId + "\u7684\u6587\u4ef6\u5220\u9664\u5931\u8d25\uff1a" + e.getMessage(), e);
                }
            }
        }
        return dataArray.toList();
    }

    private void changeNodeData(JSONArray dataArray) {
        for (int i = 0; i < dataArray.length(); ++i) {
            JSONObject resItemJson = dataArray.getJSONObject(i);
            JSONArray children = (JSONArray)resItemJson.get("children");
            if (children != null && !children.isEmpty()) {
                this.changeNodeData(children);
                continue;
            }
            if (2 == (Integer)resItemJson.get("changeMode")) {
                resItemJson = resItemJson.put("operatetype", 1);
            }
            if (0 != (Integer)resItemJson.get("changeMode") || resItemJson.get("sourceModifiedTime") != null && !StringUtils.isEmpty((String)((String)resItemJson.get("sourceModifiedTime")))) continue;
            JSONObject jSONObject = resItemJson.put("operatetype", 1);
        }
    }
}

