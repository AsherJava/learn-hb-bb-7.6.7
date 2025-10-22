/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cn.hutool.core.util.CharsetUtil
 *  cn.hutool.extra.ftp.Ftp
 *  cn.hutool.extra.ftp.FtpMode
 *  cn.hutool.extra.ssh.Sftp
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.common.file.dto.CommonFileDTO
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.param.ReportDataParam
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncDataProgressDataDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  com.jiuqi.nvwa.mapping.bean.MappingScheme
 *  com.jiuqi.nvwa.mapping.service.IMappingSchemeService
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.feign.util.FeignUtil
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.extra.ssh.Sftp;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.common.file.dto.CommonFileDTO;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.param.ReportDataParam;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ReportDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadLogDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadSchemeDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportDataSyncDataProgressDataDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import com.jiuqi.gcreport.reportdatasync.enums.CheckParamVOEnums;
import com.jiuqi.gcreport.reportdatasync.enums.ReportDataSyncUploadSchemePeriodType;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.service.MultilevelSyncService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerInfoService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncAuthUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncFlowUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncMessageUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogQueryParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataUploadToFtpVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportsyncDataLoadParam;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.de.dataflow.constont.UploadStateEnum;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import com.jiuqi.nvwa.mapping.bean.MappingScheme;
import com.jiuqi.nvwa.mapping.service.IMappingSchemeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.feign.util.FeignUtil;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
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
public class ReportDataSyncUploadServiceImpl
implements ReportDataSyncUploadService {
    private static Logger LOGGER = LoggerFactory.getLogger(ReportDataSyncUploadServiceImpl.class);
    @Autowired
    private ReportDataSyncUploadSchemeDao uploadSchemeDao;
    @Autowired
    private ReportDataSyncUploadLogDao uploadLogDao;
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ProgressService<ReportDataSyncDataProgressDataDTO, List<String>> progressService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private MultilevelSyncService multilevelSyncService;
    @Autowired
    private IMappingSchemeService mappingSchemeService;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;
    @Autowired
    private ReportDataSyncServerInfoService reportDataSyncServerInfoService;
    private static final String SCHEMEROOTID = "00000000-0000-0000-0000-000000000000";

    @Override
    public String saveUploadScheme(ReportDataSyncUploadSchemeVO uploadSchemeVO) {
        ReportDataSyncUploadSchemeEO uploadSchemeEO = new ReportDataSyncUploadSchemeEO();
        BeanUtils.copyProperties(uploadSchemeVO, (Object)uploadSchemeEO);
        uploadSchemeEO.setApplicationMode(0);
        uploadSchemeEO.setId(UUIDOrderUtils.newUUIDStr());
        uploadSchemeEO.setCreateTime(new Date());
        uploadSchemeVO.convert2EO();
        String uploadSchemeJson = JsonUtils.writeValueAsString((Object)uploadSchemeVO);
        uploadSchemeEO.setContent(uploadSchemeJson);
        List<ReportDataSyncUploadSchemeEO> res = this.uploadSchemeDao.queryByTitle(uploadSchemeEO.getTitle());
        res = res.stream().filter(v -> v.getSchemeGroup().equals(uploadSchemeVO.getSchemeGroup())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(res)) {
            if (uploadSchemeVO.getSchemeGroup() == 1) {
                throw new BusinessRuntimeException("\u5206\u7ec4\u540d\u79f0\u5df2\u5b58\u5728");
            }
            throw new BusinessRuntimeException("\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
        }
        this.uploadSchemeDao.add((BaseEntity)uploadSchemeEO);
        return uploadSchemeEO.getId();
    }

    @Override
    public Boolean deleteUploadScheme(String uploadSchemeId) {
        Assert.notNull((Object)uploadSchemeId, (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        List<ReportDataSyncUploadSchemeEO> allUploadScheme = this.uploadSchemeDao.listAllUploadScheme();
        List<ReportDataSyncUploadSchemeEO> deleteSchemeEO = this.getChildSchemeById(allUploadScheme, uploadSchemeId);
        if (!CollectionUtils.isEmpty(deleteSchemeEO)) {
            this.uploadSchemeDao.deleteBatch(deleteSchemeEO);
        }
        return Boolean.TRUE;
    }

    @Override
    public String updateUploadScheme(ReportDataSyncUploadSchemeVO uploadSchemeVO) {
        Assert.notNull((Object)uploadSchemeVO.getId(), (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeVO.getId()));
        if (uploadSchemeEO == null) {
            return null;
        }
        Date createTime = uploadSchemeEO.getCreateTime();
        BeanUtils.copyProperties(uploadSchemeVO, (Object)uploadSchemeEO);
        uploadSchemeEO.setModifyTime(new Date());
        uploadSchemeEO.setCreateTime(createTime);
        uploadSchemeVO.setChildren(null);
        uploadSchemeVO.convert2EO();
        String uploadSchemeJson = JsonUtils.writeValueAsString((Object)uploadSchemeVO);
        uploadSchemeEO.setContent(uploadSchemeJson);
        List<ReportDataSyncUploadSchemeEO> res = this.uploadSchemeDao.queryByTitle(uploadSchemeEO.getTitle());
        res = res.stream().filter(v -> v.getSchemeGroup().equals(uploadSchemeVO.getSchemeGroup())).collect(Collectors.toList());
        if (res.size() == 0) {
            this.uploadSchemeDao.update((BaseEntity)uploadSchemeEO);
        } else if (res.get(0).getId().equals(uploadSchemeEO.getId())) {
            this.uploadSchemeDao.update((BaseEntity)uploadSchemeEO);
        } else {
            if (uploadSchemeVO.getSchemeGroup() == 1) {
                throw new BusinessRuntimeException("\u5206\u7ec4\u540d\u79f0\u5df2\u5b58\u5728");
            }
            throw new BusinessRuntimeException("\u65b9\u6848\u540d\u79f0\u5df2\u5b58\u5728");
        }
        return uploadSchemeEO.getId();
    }

    @Override
    public String queryUploadSchemePeriodTitle(String uploadSchemeId) {
        String periodValue;
        PeriodType taskPeriodType;
        Assert.notNull((Object)uploadSchemeId, (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeId));
        if (uploadSchemeEO.getPeriodStrTitle() != null && !uploadSchemeEO.getPeriodStrTitle().isEmpty()) {
            return uploadSchemeEO.getPeriodStrTitle();
        }
        try {
            String taskId = uploadSchemeEO.getTaskId();
            if (StringUtils.isEmpty((String)taskId)) {
                return "\u4f01\u4e1a\u7aef\u6a21\u5f0f";
            }
            TaskDefine taskDefine = this.taskDefineService.queryTaskDefine(taskId);
            taskPeriodType = taskDefine.getPeriodType();
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        String schemePeriodType = uploadSchemeEO.getPeriodType();
        ReportDataSyncUploadSchemePeriodType uploadSchemePeriodType = ReportDataSyncUploadSchemePeriodType.valueOf(schemePeriodType);
        switch (uploadSchemePeriodType) {
            case before: {
                periodValue = ReportDataSyncUtils.getBeforePriorPeriod(taskPeriodType);
                break;
            }
            case current: {
                periodValue = ReportDataSyncUtils.getCurrentPriorPeriod(taskPeriodType);
                break;
            }
            case custom: {
                periodValue = uploadSchemeEO.getPeriodStr();
                break;
            }
            default: {
                throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u7684\u4e0a\u62a5\u65b9\u6848\u65f6\u671f\u7c7b\u578b\u3002");
            }
        }
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(periodValue);
        return periodTitle;
    }

    @Override
    public Boolean uploadReportData(ReportDataParam reportData) {
        ArrayList vos;
        YearPeriodObject yp = new YearPeriodObject(null, reportData.getPeriodStr());
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)reportData.getOrgType(), (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        if (reportData.getUnitCodes().isEmpty()) {
            vos = tool.getOrgTree();
            List unitCodes = vos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
            reportData.setUnitCodes(unitCodes);
            reportData.setUnitVos(vos);
        } else {
            vos = new ArrayList();
            reportData.getUnitCodes().stream().forEach(code -> {
                GcOrgCacheVO vo = tool.getOrgByCode(code);
                vos.add(vo);
            });
            reportData.setUnitVos(vos);
        }
        ReportDataSyncUploadSchemeEO uploadSchemeEO = new ReportDataSyncUploadSchemeEO();
        ReportDataSyncUploadSchemeVO uploadSchemeVO = new ReportDataSyncUploadSchemeVO();
        uploadSchemeVO.setApplicationMode(Boolean.valueOf(false));
        uploadSchemeVO.setReportData(reportData);
        uploadSchemeVO.setTitle("\u6570\u636e\u5f55\u5165\u4e34\u65f6\u4e0a\u4f20\u65b9\u6848");
        String uploadSchemeJson = JsonUtils.writeValueAsString((Object)uploadSchemeVO);
        BeanUtils.copyProperties(uploadSchemeVO, (Object)uploadSchemeEO);
        uploadSchemeEO.setApplicationMode(0);
        uploadSchemeEO.setContent(uploadSchemeJson);
        uploadSchemeEO.setId(UUIDOrderUtils.newUUIDStr());
        uploadSchemeEO.setCreateTime(new Date());
        ReportDataContext context = new ReportDataContext();
        context.setLogs(new ArrayList<String>());
        context.setUploadSchemeEO(uploadSchemeEO);
        context.setUploadLogEO(ReportDataSyncDataUtils.buildUploadLogEO(uploadSchemeEO));
        context.setTaskId(reportData.getTaskId());
        context.setType(SyncTypeEnums.REPORTDATA);
        return this.multilevelSyncService.multilevelSync(context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Boolean uploadReportData(ReportDataSyncUploadSchemeEO uploadSchemeEO) {
        String periodStrTitle;
        String adjustCode;
        String periodValue;
        TaskDefine taskDefine;
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
        ReportDataSyncDataProgressDataDTO progressDataDTO = new ReportDataSyncDataProgressDataDTO(uploadSnId);
        this.progressService.createProgressData((ProgressData)progressDataDTO);
        progressDataDTO.setProgressValueAndRefresh(0.1);
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
        progressDataDTO.setProgressValueAndRefresh(0.2);
        ReportDataSyncDataUtils.exportDataToOss(taskDefine, uploadSchemeEO, uploadLogEO, logs);
        progressDataDTO.setProgressValueAndRefresh(0.5);
        ReportDataSyncUploadServiceImpl uploadService = (ReportDataSyncUploadServiceImpl)SpringContextUtils.getBean(ReportDataSyncUploadServiceImpl.class);
        uploadService.insertUploadLogAndCommitLog(uploadLogEO);
        progressDataDTO.setProgressValueAndRefresh(0.6);
        UploadStatusEnum uploadStatusEnum = UploadStatusEnum.UPLOADING;
        String msg = "\u6570\u636e\u4e0a\u4f20\u6210\u529f";
        try {
            this.uploadReportDataSyncTask(uploadLogEO, progressDataDTO, logs, taskId, uploadSchemeEO);
            uploadStatusEnum = UploadStatusEnum.SUCCESS;
            logs.add(msg);
        }
        catch (Exception e) {
            try {
                uploadStatusEnum = UploadStatusEnum.ERROR;
                msg = "\u6570\u636e\u4e0a\u4f20\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + e.getMessage();
                logs.add(msg);
                LOGGER.error(msg, e);
            }
            catch (Throwable throwable) {
                progressDataDTO.setProgressValueAndRefresh(1.0);
                uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
                uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
                this.uploadLogDao.update((BaseEntity)uploadLogEO);
                String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
                LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
                throw throwable;
            }
            progressDataDTO.setProgressValueAndRefresh(1.0);
            uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
            uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
            this.uploadLogDao.update((BaseEntity)uploadLogEO);
            String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
        }
        progressDataDTO.setProgressValueAndRefresh(1.0);
        uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
        uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
        this.uploadLogDao.update((BaseEntity)uploadLogEO);
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
        return Boolean.TRUE;
    }

    @Override
    public Boolean isExistInputTable(String uploadSchemeId) {
        Assert.notNull((Object)uploadSchemeId, (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeId));
        String mergeDataParams = uploadSchemeEO.getContent();
        ReportDataSyncUploadSchemeVO uploadSchemeVO = (ReportDataSyncUploadSchemeVO)JsonUtils.readValue((String)mergeDataParams, ReportDataSyncUploadSchemeVO.class);
        if (uploadSchemeVO.getReportData() == null) {
            return Boolean.FALSE;
        }
        if (uploadSchemeVO.getReportData().getFormKeys().size() == 0) {
            return Boolean.FALSE;
        }
        for (String formKey : uploadSchemeVO.getReportData().getFormKeys()) {
            List regionDefineList = this.runTimeViewController.getAllRegionsInForm(formKey);
            for (DataRegionDefine regionDefine : regionDefineList) {
                List defines;
                if (DataRegionKind.DATA_REGION_SIMPLE.equals((Object)regionDefine.getRegionKind()) || CollectionUtils.isEmpty((Collection)(defines = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(this.runTimeViewController.getFieldKeysInRegion(regionDefine.getKey()).toArray(new String[0])))) || !((DataFieldDeployInfo)defines.get(0)).getTableName().contains("GC_INPUTDATA")) continue;
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void uploadReportDataSyncTask(ReportDataSyncUploadLogEO uploadLogEO, ReportDataSyncDataProgressDataDTO progressDataDTO, List<String> logs, String taskId, ReportDataSyncUploadSchemeEO uploadSchemeEO) {
        ReportDataSyncServerInfoVO serverInfoVO = this.reportDataSyncServerInfoService.queryServerInfo();
        Objects.requireNonNull(serverInfoVO, "\u672a\u8bbe\u7f6e\u76ee\u6807\u670d\u52a1\u5668\u4fe1\u606f\u3002");
        ReportDataSyncUploadDataTaskVO taskVO = new ReportDataSyncUploadDataTaskVO();
        taskVO.setId(uploadLogEO.getId());
        taskVO.setStatus(TaskStatusEnum.WAIT.getCode());
        taskVO.setOrgTitle(serverInfoVO.getOrgTitle());
        taskVO.setOrgCode(serverInfoVO.getOrgCode());
        taskVO.setUploadTime(DateUtils.format((Date)uploadLogEO.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        taskVO.setTaskId(uploadLogEO.getTaskId());
        taskVO.setTaskCode(uploadLogEO.getTaskCode());
        taskVO.setTaskTitle(uploadLogEO.getTaskTitle());
        taskVO.setPeriodStr(uploadLogEO.getPeriodStr());
        taskVO.setPeriodValue(uploadLogEO.getPeriodValue());
        taskVO.setAdjustCode(uploadLogEO.getAdjustCode());
        taskVO.setSyncDataAttachId(uploadLogEO.getSyncDataAttachId());
        taskVO.setSyncVersion(uploadLogEO.getSyncVersion());
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncUploadDataClient client = (ReportDataSyncUploadDataClient)FeignUtil.getDynamicClient(ReportDataSyncUploadDataClient.class, (String)serverInfoVO.getTargetUrl());
        if (!StringUtils.isEmpty((String)uploadLogEO.getTaskCode())) {
            logs.add("\u6b63\u5728\u6821\u9a8c\u62a5\u8868\u4efb\u52a1\u7248\u672c\u53f7\uff1b");
            ReportDataSyncReceiveLogVO receiveLogVO = this.paramUpdateService.queryLatestSyncSuccessReceiveTaskByTaskId(taskId);
            if (receiveLogVO != null) {
                ReportDataSyncUploadCheckParamVO checkParamVO = new ReportDataSyncUploadCheckParamVO();
                checkParamVO.setOrgCode(serverInfoVO.getOrgCode());
                checkParamVO.setOrgTitle(serverInfoVO.getOrgTitle());
                checkParamVO.setPeriodStr(uploadLogEO.getPeriodStr());
                checkParamVO.setSelectAdjust(uploadLogEO.getAdjustCode());
                checkParamVO.setPeriodStrValue(uploadLogEO.getPeriodValue());
                checkParamVO.setTaskId(taskId);
                checkParamVO.setTaskCode(uploadLogEO.getTaskCode());
                checkParamVO.setTaskTitle(receiveLogVO.getTaskTitle());
                checkParamVO.setSyncVersion(receiveLogVO.getSyncVersion());
                Boolean applicationMode = uploadSchemeEO.getApplicationMode() == null || uploadSchemeEO.getApplicationMode() == 1;
                if (!applicationMode.booleanValue()) {
                    ReportDataSyncUploadSchemeVO uploadSchemeVO = null;
                    String schemeEOContent = uploadSchemeEO.getContent();
                    uploadSchemeVO = (ReportDataSyncUploadSchemeVO)JsonUtils.readValue((String)schemeEOContent, ReportDataSyncUploadSchemeVO.class);
                    Collection unitVos = null;
                    if (uploadSchemeVO.getReportData() != null) {
                        unitVos = uploadSchemeVO.getReportData().getUnitVos();
                    }
                    List<String> unitCodes = unitVos.stream().map(GcOrgCacheVO::getCode).collect(Collectors.toList());
                    List orgDOS = ReportDataSyncDataUtils.listBaseOrgs(unitCodes).getRows();
                    ArrayList<String> needCheckOrgCodes = new ArrayList<String>();
                    for (OrgDO v : orgDOS) {
                        if (v.get((Object)"gzcode") != null && !StringUtils.isEmpty((String)v.get((Object)"gzcode").toString())) {
                            needCheckOrgCodes.add(v.get((Object)"gzcode").toString());
                            continue;
                        }
                        needCheckOrgCodes.add(v.getCode());
                    }
                    checkParamVO.setOrgCodes(needCheckOrgCodes);
                    checkParamVO.setUnitVos((List)unitVos);
                }
                checkParamVO.setApplicationMode(applicationMode);
                checkParamVO.setTargetUrl(serverInfoVO.getTargetUrl());
                try {
                    FormSchemeDefine formSchemeDefine = this.funcExecuteService.queryFormScheme(taskId, uploadLogEO.getPeriodValue());
                    if (formSchemeDefine == null) {
                        throw new BusinessRuntimeException("\u67e5\u8be2\u5f53\u524d\u62a5\u8868\u65b9\u6848\u5931\u8d25");
                    }
                    ArrayList<String> schemeKeys = new ArrayList<String>();
                    schemeKeys.add(formSchemeDefine.getKey());
                    checkParamVO.setSchemeKeys(schemeKeys);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException((Throwable)e);
                }
                BusinessResponseEntity checkResponseEntity = client.checkUploadReportData(checkParamVO);
                if (!checkResponseEntity.isSuccess()) {
                    if (null == checkResponseEntity.getErrorCode()) {
                        throw new BusinessRuntimeException(checkResponseEntity.getErrorMessage());
                    }
                    if (!checkResponseEntity.getErrorCode().equals(CheckParamVOEnums.PROFESSIONAL_UNIT_STATUS_CHECK.getCode())) throw new BusinessRuntimeException(checkResponseEntity.getErrorMessage());
                    List<String> msg = Arrays.asList(checkResponseEntity.getErrorMessage().split(";"));
                    if (!msg.isEmpty()) {
                        if (msg.size() == checkParamVO.getOrgCodes().size()) throw new BusinessRuntimeException(checkResponseEntity.getErrorMessage());
                        logs.addAll(msg);
                    }
                }
                logs.add("\u7248\u672c\u53f7\u6821\u9a8c\u6210\u529f\uff1b");
            }
        }
        progressDataDTO.setProgressValueAndRefresh(0.6);
        logs.add("\u6267\u884c\u6570\u636e\u4e0a\u4f20...");
        CommonFileDTO syncDataAttachFile = this.commonFileService.queryOssFileByFileKey(uploadLogEO.getSyncDataAttachId());
        BusinessResponseEntity responseEntity = client.uploadReportDataSyncUploadDataTask(client.getOptions(), JsonUtils.writeValueAsString((Object)taskVO), new MultipartFile[]{syncDataAttachFile});
        progressDataDTO.setProgressValueAndRefresh(0.9);
        if (responseEntity.isSuccess()) return;
        throw new BusinessRuntimeException(responseEntity.getErrorMessage());
    }

    @Override
    public Boolean modifyLoadingResults(ReportsyncDataLoadParam reportsyncDataLoadParam) {
        ReportDataSyncUploadLogEO reportDataSyncUploadLogEO = null;
        if (reportsyncDataLoadParam.getId() != null) {
            reportDataSyncUploadLogEO = (ReportDataSyncUploadLogEO)this.uploadLogDao.get((Serializable)((Object)reportsyncDataLoadParam.getId()));
        }
        if (reportDataSyncUploadLogEO == null) {
            reportDataSyncUploadLogEO = this.uploadLogDao.queryFirstUploadLogByTaskCodeAndPeriodStr(reportsyncDataLoadParam.getTaskCode(), reportsyncDataLoadParam.getPeriodStr(), reportsyncDataLoadParam.getAdjustCode());
        }
        reportDataSyncUploadLogEO.setLoadMsg(reportsyncDataLoadParam.getLoadMsg());
        reportDataSyncUploadLogEO.setLoadStatus(reportsyncDataLoadParam.getLoadStatus());
        this.uploadLogDao.update((BaseEntity)reportDataSyncUploadLogEO);
        return Boolean.TRUE;
    }

    @Override
    public Boolean rejectReportData(ReportDataSyncUploadDataTaskVO uploadDataTaskVO) {
        ReportDataSyncUploadLogEO reportDataSyncUploadLogEO = null;
        if (uploadDataTaskVO.getId() != null) {
            reportDataSyncUploadLogEO = (ReportDataSyncUploadLogEO)this.uploadLogDao.get((Serializable)((Object)uploadDataTaskVO.getId()));
        }
        if (reportDataSyncUploadLogEO == null) {
            reportDataSyncUploadLogEO = this.uploadLogDao.queryFirstUploadLogByTaskCodeAndPeriodStr(uploadDataTaskVO.getTaskCode(), uploadDataTaskVO.getPeriodValue(), uploadDataTaskVO.getAdjustCode());
        }
        StringBuffer msg = new StringBuffer();
        if (uploadDataTaskVO.getPeriodValue() != null || uploadDataTaskVO.getPeriodStr() != null) {
            String periodTitle = uploadDataTaskVO.getPeriodStr() != null ? uploadDataTaskVO.getPeriodStr() : new DefaultPeriodAdapter().getPeriodTitle(uploadDataTaskVO.getPeriodValue());
            msg.append("\u9000\u56de\u65f6\u671f\uff1a").append(periodTitle).append("\n");
        }
        if (!CollectionUtils.isEmpty((Collection)uploadDataTaskVO.getReturnUnitCodes())) {
            PageVO<OrgDO> allBaseOrgs = ReportDataSyncDataUtils.listBaseOrgs(null);
            Map<String, String> gzCode2OrgCode = allBaseOrgs.getRows().stream().filter(orgDO -> !StringUtils.isEmpty((String)((String)orgDO.get((Object)"gzcode")))).collect(Collectors.toMap(OrgDO::getCode, orgDo -> (String)orgDo.get((Object)"gzcode"), (o1, o2) -> o2));
            ArrayList<String> finaCodes = new ArrayList<String>();
            for (String orgCode : uploadDataTaskVO.getReturnUnitCodes()) {
                finaCodes.add(gzCode2OrgCode.getOrDefault(orgCode, orgCode));
            }
            List baseOrgs = ReportDataSyncDataUtils.listBaseOrgs(finaCodes).getRows();
            if (!CollectionUtils.isEmpty((Collection)baseOrgs)) {
                msg.append("\u9000\u56de\u5355\u4f4d\uff1a");
                for (OrgDO OrgDO2 : baseOrgs) {
                    msg.append(OrgDO2.getCode() + "|" + OrgDO2.getName() + ";");
                }
                msg.append("\n");
            }
        }
        msg.append(uploadDataTaskVO.getRejectMsg());
        ReportDataSyncUploadLogEO uploadLogEO = new ReportDataSyncUploadLogEO();
        uploadLogEO.setId(UUIDOrderUtils.newUUIDStr());
        uploadLogEO.setSyncVersion(uploadDataTaskVO.getSyncVersion());
        uploadLogEO.setTaskId(uploadDataTaskVO.getTaskId());
        uploadLogEO.setTaskTitle(uploadDataTaskVO.getTaskTitle());
        uploadLogEO.setTaskCode(uploadDataTaskVO.getTaskCode());
        uploadLogEO.setAdjustCode(uploadDataTaskVO.getAdjustCode() == null ? "0" : uploadDataTaskVO.getAdjustCode());
        uploadLogEO.setPeriodStr(uploadDataTaskVO.getPeriodStr());
        uploadLogEO.setPeriodValue(uploadDataTaskVO.getPeriodValue());
        uploadLogEO.setUploadMsg(msg.toString());
        uploadLogEO.setUploadTime(DateUtils.parse((String)uploadDataTaskVO.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
        uploadLogEO.setUploadUsername(uploadDataTaskVO.getUploadUsername());
        uploadLogEO.setUploadUserId(uploadDataTaskVO.getUploadUserId());
        uploadLogEO.setUploadStatus(UploadStatusEnum.REJECTED.getCode());
        if (reportDataSyncUploadLogEO != null) {
            uploadLogEO.setUploadSchemeId(reportDataSyncUploadLogEO.getUploadSchemeId());
            uploadLogEO.setSyncDataAttachId(reportDataSyncUploadLogEO.getSyncDataAttachId());
        }
        this.uploadLogDao.add((BaseEntity)uploadLogEO);
        ReportDataSyncMessageUtils.sendRejectMessage(uploadDataTaskVO);
        try {
            String orgCode = uploadDataTaskVO.getOrgCode();
            if (!StringUtils.isEmpty((String)orgCode)) {
                TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(uploadDataTaskVO.getTaskId());
                ReportDataSyncFlowUtils.executeDataFlow(taskDefine.getTaskCode(), orgCode, uploadDataTaskVO.getPeriodValue(), UploadStateEnum.ACTION_REJECT, "", uploadDataTaskVO.getAdjustCode());
            }
        }
        catch (Exception e) {
            LOGGER.error("[\u5408\u5e76\u591a\u7ea7\u90e8\u7f72]\u9000\u56de\u5355\u4f4d\u5931\u8d25\uff0c\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Override
    public ReportDataSyncUploadSchemeVO getUploadSchemeById(String uploadSchemeId) {
        Assert.notNull((Object)uploadSchemeId, (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeId));
        ReportDataSyncUploadSchemeVO uploadSchemeVO = new ReportDataSyncUploadSchemeVO();
        BeanUtils.copyProperties((Object)uploadSchemeEO, uploadSchemeVO);
        return uploadSchemeVO;
    }

    @Override
    public List<ReportDataSyncUploadSchemeVO> listAllUploadScheme() {
        List<ReportDataSyncUploadSchemeEO> allUploadScheme = this.uploadSchemeDao.listAllUploadScheme();
        return allUploadScheme.stream().filter(eo -> eo.getSchemeGroup() == null || eo.getSchemeGroup() == 0).map(this::convertEO2VO).collect(Collectors.toList());
    }

    @Override
    public List<ReportDataSyncUploadSchemeVO> listAllUploadSchemeTree() {
        List<ReportDataSyncUploadSchemeEO> allUploadScheme = this.uploadSchemeDao.listAllUploadScheme();
        List<ReportDataSyncUploadSchemeVO> voList = allUploadScheme.stream().map(this::convertEO2VO).collect(Collectors.toList());
        return this.returnToList(voList);
    }

    @Override
    public List<ReportDataSyncUploadSchemeVO> listAllSchemeGroupTree() {
        List<ReportDataSyncUploadSchemeEO> allUploadScheme = this.uploadSchemeDao.listAllUploadScheme();
        List<ReportDataSyncUploadSchemeVO> voList = allUploadScheme.stream().filter(eo -> eo.getSchemeGroup() == 1).map(this::convertEO2VO).collect(Collectors.toList());
        return this.returnToList(voList);
    }

    @Override
    public PageInfo<ReportDataSyncUploadLogVO> listUploadLogsBySchemeId(ReportDataSyncUploadLogQueryParamVO param) {
        PageInfo<ReportDataSyncUploadLogEO> pageInfo = this.uploadLogDao.listUploadLogsBySchemeId(param);
        List uploadLogVOS = pageInfo.getList().stream().map(uploadLog -> {
            ProgressData progressData;
            ReportDataSyncUploadLogVO uploadLogVO = new ReportDataSyncUploadLogVO();
            BeanUtils.copyProperties(uploadLog, uploadLogVO);
            uploadLogVO.setUploadTime(DateUtils.format((Date)uploadLog.getUploadTime(), (String)"yyyy-MM-dd HH:mm:ss"));
            if (null != uploadLogVO.getLoadMsg()) {
                uploadLogVO.setUploadMsg(uploadLogVO.getUploadMsg() + "\n");
            }
            if ((progressData = this.progressService.queryProgressData(uploadLogVO.getId())) != null && progressData.getProgressValue() < 1.0 && progressData.getProgressValue() > 0.0) {
                uploadLogVO.setShowProgress(Boolean.valueOf(true));
                uploadLogVO.setProgress(Double.valueOf(progressData.getProgressValue()));
            }
            return uploadLogVO;
        }).collect(Collectors.toList());
        PageInfo voPageInfo = PageInfo.of(uploadLogVOS, (int)pageInfo.getSize());
        return voPageInfo;
    }

    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void insertUploadLogAndCommitLog(ReportDataSyncUploadLogEO uploadLogEO) {
        this.uploadLogDao.add((BaseEntity)uploadLogEO);
    }

    @Override
    public void downloadReportData(HttpServletRequest request, HttpServletResponse response, String uploadSchemeId) {
        Assert.notNull((Object)uploadSchemeId, (String)"\u4e0a\u62a5\u65b9\u6848ID\u4e0d\u80fd\u4e3a\u7a7a");
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeId));
        ReportDataSyncUploadLogEO uploadLogEO = ReportDataSyncDataUtils.buildUploadLogEO(uploadSchemeEO);
        try {
            TaskDefine taskDefine = uploadLogEO.getTaskId() == null ? null : this.taskDefineService.queryTaskDefine(uploadLogEO.getTaskId());
            ReportDataSyncDataUtils.exportDataToOss(taskDefine, uploadSchemeEO, uploadLogEO, new ArrayList<String>());
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u53c2\u6570\u5305\u5e76\u5b58\u5165oss\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20-\u6570\u636e\u5305\u4e0b\u8f7d", (String)String.format("\u6570\u636e\u5305\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f\uff0c\u6570\u636e\u5305ID: %3$s", uploadSchemeEO.getTitle(), uploadLogEO.getPeriodStr(), uploadLogEO.getSyncDataAttachId()), (String)"");
        String syncParamAttachId = uploadLogEO.getSyncDataAttachId();
        this.commonFileService.downloadOssFile(request, response, syncParamAttachId);
    }

    @Override
    public Boolean uploadToFtp(ReportDataUploadToFtpVO ftpVO) {
        LOGGER.info("\u5f00\u59cb\u4e0a\u4f20\u5230ftp\uff0c\u53c2\u6570\uff1a{}", (Object)JsonUtils.writeValueAsString((Object)ftpVO));
        String uploadSchemeId = ftpVO.getUploadSchemeId();
        ReportDataSyncUploadSchemeEO uploadSchemeEO = (ReportDataSyncUploadSchemeEO)this.uploadSchemeDao.get((Serializable)((Object)uploadSchemeId));
        try {
            Object ftp = null;
            LOGGER.info("\u5f00\u59cb\u8fde\u63a5ftp");
            ftp = ftpVO.getFtpType().equals("ftp") ? new Ftp(ftpVO.getFtpHost(), ftpVO.getFtpPort().intValue(), ftpVO.getFtpUserName(), ftpVO.getFtpPassword(), CharsetUtil.CHARSET_UTF_8, null, null, FtpMode.Active) : new Sftp(ftpVO.getFtpHost(), ftpVO.getFtpPort().intValue(), ftpVO.getFtpUserName(), ftpVO.getFtpPassword());
            LOGGER.info("\u8fde\u63a5ftp\u6210\u529f");
            ReportDataSyncUploadLogEO uploadLogEO = ReportDataSyncDataUtils.buildUploadLogEO(uploadSchemeEO);
            try {
                TaskDefine taskDefine = uploadLogEO.getTaskId() == null ? null : this.taskDefineService.queryTaskDefine(uploadLogEO.getTaskId());
                ReportDataSyncDataUtils.exportDataToOss(taskDefine, uploadSchemeEO, uploadLogEO, new ArrayList<String>());
            }
            catch (Exception e) {
                LOGGER.error("\u83b7\u53d6\u53c2\u6570\u5305\u5e76\u5b58\u5165oss\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage());
                throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
            }
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20-\u6570\u636e\u5305\u751f\u6210", (String)String.format("\u6570\u636e\u5305\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f\uff0c\u6570\u636e\u5305ID: %3$s", uploadSchemeEO.getTitle(), uploadLogEO.getPeriodStr(), uploadLogEO.getSyncDataAttachId()), (String)"");
            CommonFileDTO multipartFile = this.commonFileService.queryOssFileByFileKey(uploadLogEO.getSyncDataAttachId());
            File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "reportsync_" + multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
            ftp.upload(ftpVO.getFtpFilePath(), file);
            ftp.close();
            file.delete();
        }
        catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    private List<ReportDataSyncUploadSchemeEO> getChildSchemeById(List<ReportDataSyncUploadSchemeEO> schemeList, String id) {
        ArrayList<ReportDataSyncUploadSchemeEO> result = new ArrayList<ReportDataSyncUploadSchemeEO>();
        for (ReportDataSyncUploadSchemeEO schemeEO : schemeList) {
            if (!schemeEO.getId().equals(id)) continue;
            result.add(schemeEO);
            break;
        }
        for (ReportDataSyncUploadSchemeEO schemeEO : schemeList) {
            if (!id.equals(schemeEO.getParentId())) continue;
            result.addAll(this.getChildSchemeById(schemeList, schemeEO.getId()));
        }
        return result;
    }

    @Override
    public List<Map<String, String>> listAllMappingScheme() {
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List mappingSchemeList = this.mappingSchemeService.getAllSchemes();
        for (MappingScheme mappingScheme : mappingSchemeList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("configKey", mappingScheme.getKey());
            map.put("name", mappingScheme.getTitle());
            result.add(map);
        }
        return result;
    }

    private ReportDataSyncUploadSchemeVO convertEO2VO(ReportDataSyncUploadSchemeEO uploadSchemeEO) {
        ReportDataSyncUploadSchemeVO uploadSchemeVO = StringUtils.isEmpty((String)uploadSchemeEO.getContent()) ? new ReportDataSyncUploadSchemeVO() : (ReportDataSyncUploadSchemeVO)JsonUtils.readValue((String)uploadSchemeEO.getContent(), ReportDataSyncUploadSchemeVO.class);
        BeanUtils.copyProperties((Object)uploadSchemeEO, uploadSchemeVO);
        uploadSchemeVO.setTaskId(uploadSchemeEO.getTaskId());
        uploadSchemeVO.setId(uploadSchemeEO.getId());
        uploadSchemeVO.setTitle(uploadSchemeEO.getTitle());
        uploadSchemeVO.setPeriodType(uploadSchemeEO.getPeriodType());
        uploadSchemeVO.setPeriodStr(uploadSchemeEO.getPeriodStr());
        if (uploadSchemeVO.getSchemeGroup() == null) {
            uploadSchemeVO.setSchemeGroup(Integer.valueOf(0));
        }
        if (StringUtils.isEmpty((String)uploadSchemeVO.getParentId())) {
            uploadSchemeVO.setParentId(SCHEMEROOTID);
        }
        if (!StringUtils.isEmpty((String)uploadSchemeEO.getTaskId())) {
            try {
                TaskDefine taskDefine = this.taskDefineService.queryTaskDefine(uploadSchemeEO.getTaskId());
                uploadSchemeVO.setTaskTitle(taskDefine.getTitle());
            }
            catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return uploadSchemeVO;
    }

    private List<ReportDataSyncUploadSchemeVO> returnToList(List<ReportDataSyncUploadSchemeVO> voList) {
        ArrayList<ReportDataSyncUploadSchemeVO> voTree = new ArrayList<ReportDataSyncUploadSchemeVO>();
        HashMap<String, ReportDataSyncUploadSchemeVO> voMap = new HashMap<String, ReportDataSyncUploadSchemeVO>();
        ArrayList<String> rootMap = new ArrayList<String>();
        for (ReportDataSyncUploadSchemeVO vo : voList) {
            voMap.put(vo.getId(), vo);
            rootMap.add(vo.getId());
        }
        for (ReportDataSyncUploadSchemeVO vo : voList) {
            ReportDataSyncUploadSchemeVO parent;
            if (StringUtils.isEmpty((String)vo.getParentId()) || (parent = (ReportDataSyncUploadSchemeVO)voMap.get(vo.getParentId())) == null) continue;
            parent.getChildren().add(vo);
            rootMap.remove(vo.getId());
        }
        for (String key : rootMap) {
            voTree.add((ReportDataSyncUploadSchemeVO)voMap.get(key));
        }
        return voTree;
    }
}

