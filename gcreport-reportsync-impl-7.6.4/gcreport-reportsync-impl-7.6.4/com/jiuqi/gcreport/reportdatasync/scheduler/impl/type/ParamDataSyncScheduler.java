/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.file.service.CommonFileService
 *  com.jiuqi.common.reportsync.param.ReportParam
 *  com.jiuqi.common.reportsync.vo.ReportDataSyncParams
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.scheduler.impl.type;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.file.service.CommonFileService;
import com.jiuqi.common.reportsync.param.ReportParam;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.contextImpl.ParamDataContext;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataParamsSyncSchemeDao;
import com.jiuqi.gcreport.reportdatasync.dao.ReportDataSyncIssuedLogDao;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataParamsSyncSchemeEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import com.jiuqi.gcreport.reportdatasync.enums.ParamPackageStatus;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncServerListService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncParamUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.service.RunTimeTaskDefineService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ParamDataSyncScheduler
implements ISyncTypeScheduler {
    @Autowired
    private ReportDataParamsSyncSchemeDao paramsSyncSchemeDao;
    @Autowired
    private RunTimeTaskDefineService taskDefineService;
    @Autowired
    private ReportDataSyncIssuedLogDao issuedLogDao;
    @Autowired
    private CommonFileService commonFileService;
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;
    @Autowired
    private ReportDataSyncServerListService serverListService;

    @Override
    public String code() {
        return SyncTypeEnums.PARAMDATA.getCode();
    }

    @Override
    public String name() {
        return SyncTypeEnums.PARAMDATA.getName();
    }

    @Override
    public List<ReportDataSyncServerInfoVO> getServerInfoVOList() {
        List<ReportDataSyncServerInfoVO> serverInfoVOS = this.serverListService.listServerInfos().stream().filter(v -> v.getSyncMethod() != null && v.getSyncType().contains(this.code())).collect(Collectors.toList());
        return serverInfoVOS;
    }

    @Override
    public MultilevelSyncContext buildContext(String info) {
        ParamDataContext context = new ParamDataContext();
        ReportDataParamsSyncSchemeEO paramsSyncSchemeEO = (ReportDataParamsSyncSchemeEO)this.paramsSyncSchemeDao.get((Serializable)((Object)info));
        ReportDataSyncParams syncParam = (ReportDataSyncParams)JsonUtils.readValue((String)paramsSyncSchemeEO.getContent(), ReportDataSyncParams.class);
        ReportDataSyncIssuedLogEO xfLogEO = new ReportDataSyncIssuedLogEO();
        xfLogEO.setId(UUIDOrderUtils.newUUIDStr());
        xfLogEO.setSyncSchemeId(paramsSyncSchemeEO.getId());
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
        context.setParamsSyncSchemeEO(paramsSyncSchemeEO);
        context.setXfLogEO(xfLogEO);
        context.setType(SyncTypeEnums.PARAMDATA);
        return context;
    }

    @Override
    public ReportSyncFileDTO exportFileToOss(MultilevelSyncContext syncContext) {
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        ParamDataContext context = (ParamDataContext)syncContext;
        ReportDataParamsSyncSchemeEO paramsSyncSchemeEO = context.getParamsSyncSchemeEO();
        ReportDataSyncParams syncParam = (ReportDataSyncParams)JsonUtils.readValue((String)paramsSyncSchemeEO.getContent(), ReportDataSyncParams.class);
        ReportDataSyncIssuedLogEO xfLogEO = context.getXfLogEO();
        ReportDataSyncParamUtils.exportParamToOss(xfLogEO, syncParam);
        fileDTO.setMainFileId(xfLogEO.getSyncParamAttachId());
        fileDTO.setMainFile((MultipartFile)this.commonFileService.queryOssFileByFileKey(fileDTO.getMainFileId()));
        return fileDTO;
    }

    @Override
    public void afterExport(MultilevelSyncContext syncContext) {
        ParamDataContext context = (ParamDataContext)syncContext;
        ReportDataSyncIssuedLogEO xfLogEO = context.getXfLogEO();
        this.issuedLogDao.add((BaseEntity)xfLogEO);
    }

    @Override
    public boolean importData(MultilevelImportContext importContext) {
        ReportSyncFileDTO fileDTO = importContext.getFileDTO();
        MultipartFile multipartFile = fileDTO.getMainFile();
        boolean result = false;
        try {
            result = this.paramUpdateService.importReportParamFile(multipartFile, fileDTO.getDesAttachFile(), null);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
        return result;
    }

    @Override
    public boolean isSync() {
        return false;
    }

    @Override
    public boolean multilevelReturn(MultilevelReturnContext contextEnv) {
        return true;
    }
}

