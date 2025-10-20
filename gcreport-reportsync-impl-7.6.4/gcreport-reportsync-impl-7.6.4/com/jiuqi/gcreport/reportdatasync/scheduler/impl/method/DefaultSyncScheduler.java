/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.reportsync.util.CommonAuthUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamUpdateClient
 *  com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.dataentry.service.IFuncExecuteService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.feign.util.FeignUtil
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.method;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.reportsync.util.CommonAuthUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncParamUpdateClient;
import com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncUploadDataClient;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.OrgDataContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ParamDataContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ReportDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.MultilevelOrgDataLogDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncUploadLogDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.entity.MultilevelOrgDataSyncLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import com.jiuqi.gcreport.reportdatasync.enums.CheckParamVOEnums;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.enums.TaskStatusEnum;
import com.jiuqi.gcreport.reportdatasync.enums.UploadStatusEnum;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.scheduler.impl.extend.DefaultExtendHandler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncAuthUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveLogVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadCheckParamVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadSchemeVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.dataentry.service.IFuncExecuteService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.feign.util.FeignUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DefaultSyncScheduler
implements ISyncMethodScheduler {
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultSyncScheduler.class);
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;
    @Autowired
    private IFuncExecuteService funcExecuteService;
    @Autowired
    private ReportDataSyncUploadLogDao uploadLogDao;
    @Autowired
    private DefaultExtendHandler handler;

    @Override
    public MultilevelExtendHandler getHandler() {
        return this.handler;
    }

    @Override
    public String code() {
        return "DEFAULT";
    }

    @Override
    public String name() {
        return "\u76f4\u8fde";
    }

    @Override
    public boolean sync(MultilevelSyncContext syncContext) {
        ReportSyncFileDTO fileDTO = syncContext.getFileDTO();
        ReportDataSyncServerInfoVO serverInfoVO = syncContext.getServerInfoVO();
        SyncTypeEnums type = syncContext.getType();
        boolean result = false;
        switch (type) {
            case REPORTDATA: {
                result = this.reportdataSync(syncContext, fileDTO, serverInfoVO);
                break;
            }
            case PARAMDATA: {
                result = this.paramdataSync(syncContext, fileDTO, serverInfoVO);
                break;
            }
            default: {
                result = this.commonDataSync(syncContext, fileDTO, serverInfoVO);
            }
        }
        return result;
    }

    @Override
    public void afterSync(boolean syncResult, MultilevelSyncContext syncContext) {
        SyncTypeEnums type = syncContext.getType();
        if (SyncTypeEnums.REPORTDATA.equals((Object)type)) {
            ReportDataContext content = (ReportDataContext)syncContext;
            ReportDataSyncUploadLogEO uploadLogEO = content.getUploadLogEO();
            if (syncResult) {
                uploadLogEO.setUploadStatus(UploadStatusEnum.SUCCESS.getCode());
            } else {
                uploadLogEO.setUploadStatus(UploadStatusEnum.ERROR.getCode());
            }
            this.uploadLogDao.update((BaseEntity)uploadLogEO);
        }
        if (SyncTypeEnums.ORGDATA.equals((Object)type)) {
            MultilevelOrgDataLogDao orgDataLogDao = (MultilevelOrgDataLogDao)SpringContextUtils.getBean(MultilevelOrgDataLogDao.class);
            OrgDataContext context = (OrgDataContext)syncContext;
            MultilevelOrgDataSyncLogEO logEO = new MultilevelOrgDataSyncLogEO();
            BeanUtils.copyProperties((Object)context, (Object)logEO);
            NpContext npContext = NpContextHolder.getContext();
            logEO.setId(syncContext.getSn());
            logEO.setUploadUsername(npContext.getUser().getFullname());
            logEO.setUploadUserId(npContext.getUserId());
            logEO.setUploadStatus(syncResult ? UploadStatusEnum.SUCCESS.getCode() : UploadStatusEnum.ERROR.getCode());
            logEO.setUploadTime(new Date());
            logEO.setUploadMsg(syncResult ? "\u7ec4\u7ec7\u673a\u6784\u4e0a\u4f20\u6210\u529f" : "\u7ec4\u7ec7\u673a\u6784\u4e0a\u4f20\u5931\u8d25");
            orgDataLogDao.save(logEO);
        }
    }

    @Override
    public boolean testConnection(ReportDataSyncServerInfoVO serverInfoVO) {
        CommonAuthUtils.validateUserPassword((String)serverInfoVO.getTargetUrl(), (String)serverInfoVO.getTargetUserName(), (String)serverInfoVO.getTargetPwd(), (String)(serverInfoVO.getTargetEncryptType() == null ? "" : serverInfoVO.getTargetEncryptType()));
        return true;
    }

    private Boolean commonDataSync(MultilevelSyncContext envContent, ReportSyncFileDTO fileDTO, ReportDataSyncServerInfoVO serverInfoVO) {
        boolean result = false;
        try {
            MultipartFile[] multipartFileArray;
            SyncTypeEnums type = envContent.getType();
            ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
            MultilevelSyncClient client = (MultilevelSyncClient)FeignUtil.getDynamicClient(MultilevelSyncClient.class, (String)serverInfoVO.getUrl());
            MultilevelImportContext importContext = new MultilevelImportContext();
            if (fileDTO.getDesAttachFile() == null) {
                MultipartFile[] multipartFileArray2 = new MultipartFile[1];
                multipartFileArray = multipartFileArray2;
                multipartFileArray2[0] = fileDTO.getMainFile();
            } else {
                MultipartFile[] multipartFileArray3 = new MultipartFile[2];
                multipartFileArray3[0] = fileDTO.getMainFile();
                multipartFileArray = multipartFileArray3;
                multipartFileArray3[1] = fileDTO.getDesAttachFile();
            }
            MultipartFile[] multipartFiles = multipartFileArray;
            importContext.setType(type);
            result = (Boolean)client.multilevelImport(client.getOptions(), JsonUtils.writeValueAsString((Object)importContext), multipartFiles).getData();
        }
        catch (Exception e) {
            LOGGER.error("\u540c\u6b65\u5931\u8d25\uff0c\u539f\u56e0\u4e3a\uff1a" + e.getMessage(), e);
            result = false;
        }
        return result;
    }

    private Boolean paramdataSync(MultilevelSyncContext envContent, ReportSyncFileDTO fileDTO, ReportDataSyncServerInfoVO serverInfoVO) {
        MultipartFile[] multipartFileArray;
        ParamDataContext content = (ParamDataContext)envContent;
        ReportDataSyncReceiveTaskVO receiveTaskVO = content.getReceiveTaskVO();
        MultipartFile syncParamAttachFile = fileDTO.getMainFile();
        MultipartFile syncDesAttachFile = fileDTO.getDesAttachFile();
        if (syncDesAttachFile == null) {
            MultipartFile[] multipartFileArray2 = new MultipartFile[1];
            multipartFileArray = multipartFileArray2;
            multipartFileArray2[0] = syncParamAttachFile;
        } else {
            MultipartFile[] multipartFileArray3 = new MultipartFile[2];
            multipartFileArray3[0] = syncDesAttachFile;
            multipartFileArray = multipartFileArray3;
            multipartFileArray3[1] = syncParamAttachFile;
        }
        MultipartFile[] multipartFiles = multipartFileArray;
        if (syncDesAttachFile == null) {
            throw new BusinessRuntimeException("\u540c\u6b65\u6587\u4ef6\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u65b0\u751f\u6210\u53c2\u6570\u5305");
        }
        ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
        ReportDataSyncParamUpdateClient client = (ReportDataSyncParamUpdateClient)FeignUtil.getDynamicClient(ReportDataSyncParamUpdateClient.class, (String)serverInfoVO.getUrl());
        BusinessResponseEntity responseEntity = client.addTask(client.getOptions(), JsonUtils.writeValueAsString((Object)receiveTaskVO), multipartFiles);
        if (!responseEntity.isSuccess()) {
            throw new BusinessRuntimeException(responseEntity.getErrorMessage());
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Boolean reportdataSync(MultilevelSyncContext syncContext, ReportSyncFileDTO fileDTO, ReportDataSyncServerInfoVO serverInfoVO) {
        ReportDataContext context = (ReportDataContext)syncContext;
        ReportDataSyncUploadLogEO uploadLogEO = context.getUploadLogEO();
        List<String> logs = context.getLogs();
        ReportDataSyncUploadSchemeEO uploadSchemeEO = context.getUploadSchemeEO();
        UploadStatusEnum uploadStatusEnum = UploadStatusEnum.UPLOADING;
        String taskId = context.getTaskId();
        String msg = "\u6570\u636e\u4e0a\u4f20\u6210\u529f";
        boolean syncResult = true;
        try {
            ReportDataSyncUploadDataTaskVO taskVO = this.buildTaskVO(uploadLogEO, serverInfoVO);
            ReportDataSyncAuthUtils.initNvwaFeignClientTokenEnv(serverInfoVO);
            ReportDataSyncUploadDataClient client = (ReportDataSyncUploadDataClient)FeignUtil.getDynamicClient(ReportDataSyncUploadDataClient.class, (String)serverInfoVO.getUrl());
            if (!StringUtils.isEmpty((String)uploadLogEO.getTaskCode())) {
                logs.add("\u6b63\u5728\u6821\u9a8c\u62a5\u8868\u4efb\u52a1\uff1b");
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
                        List<String> checkMsg = Arrays.asList(checkResponseEntity.getErrorMessage().split(";"));
                        if (!checkMsg.isEmpty()) {
                            if (checkMsg.size() == checkParamVO.getOrgCodes().size()) throw new BusinessRuntimeException(checkResponseEntity.getErrorMessage());
                            logs.addAll(checkMsg);
                        }
                    }
                    logs.add("\u6821\u9a8c\u6210\u529f\uff1b");
                }
                uploadStatusEnum = UploadStatusEnum.SUCCESS;
            }
            MultipartFile syncDataAttachFile = fileDTO.getMainFile();
            BusinessResponseEntity responseEntity = client.uploadReportDataSyncUploadDataTask(client.getOptions(), JsonUtils.writeValueAsString((Object)taskVO), new MultipartFile[]{syncDataAttachFile});
            syncResult = responseEntity.isSuccess();
            logs.add(msg);
        }
        catch (Exception e) {
            try {
                msg = "\u6570\u636e\u4e0a\u4f20\u5931\u8d25\uff0c\u8be6\u60c5\uff1a" + e.getMessage();
                uploadStatusEnum = UploadStatusEnum.ERROR;
                syncResult = false;
                LOGGER.error(msg, e);
                logs.add(msg);
            }
            catch (Throwable throwable) {
                logs.add(msg);
                uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
                uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
                String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
                LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
                throw throwable;
            }
            uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
            uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
            String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
            LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
            return syncResult;
        }
        uploadLogEO.setUploadStatus(uploadStatusEnum.getCode());
        uploadLogEO.setUploadMsg(StringUtils.join((Object[])logs.toArray(), (String)"\n"));
        String periodTitle = ReportDataSyncUtils.getPeriodTitle(uploadSchemeEO.getPeriodStr());
        LogHelper.info((String)"\u5408\u5e76-\u6570\u636e\u4e0a\u4f20", (String)String.format("\u4e0a\u4f20\u6570\u636e-%1$s\u65b9\u6848%2$s\u65f6\u671f", uploadSchemeEO.getTitle(), periodTitle), (String)"");
        return syncResult;
    }

    private ReportDataSyncUploadDataTaskVO buildTaskVO(ReportDataSyncUploadLogEO uploadLogEO, ReportDataSyncServerInfoVO serverInfoVO) {
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
        return taskVO;
    }
}

