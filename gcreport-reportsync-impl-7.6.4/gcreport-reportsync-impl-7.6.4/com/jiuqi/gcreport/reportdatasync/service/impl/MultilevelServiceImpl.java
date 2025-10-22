/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.progress.common.ProgressData
 *  com.jiuqi.common.expimp.progress.service.ProgressService
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.GcMultilevelSyncProgressDTO
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 */
package com.jiuqi.gcreport.reportdatasync.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.progress.common.ProgressData;
import com.jiuqi.common.expimp.progress.service.ProgressService;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.dto.GcMultilevelSyncProgressDTO;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncMethodScheduler;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.MultilevelSyncService;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultilevelServiceImpl
implements MultilevelSyncService {
    private static Logger LOGGER = LoggerFactory.getLogger(MultilevelServiceImpl.class);
    @Autowired
    private List<ISyncTypeScheduler> syncTypeSchedulerList;
    @Autowired
    private List<ISyncMethodScheduler> serviceManagerUploadTypeList;
    @Autowired
    private ProgressService<GcMultilevelSyncProgressDTO, List<String>> progressService;

    @Override
    public Boolean multilevelSync(MultilevelSyncContext syncContext) {
        SyncTypeEnums typeEnums = syncContext.getType();
        Optional<ISyncTypeScheduler> syncSchedulerOpt = this.syncTypeSchedulerList.stream().filter(student -> student.code().equals(typeEnums.getCode())).findFirst();
        if (!syncSchedulerOpt.isPresent()) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b\u7684\u8c03\u5ea6\u5668\uff1a" + typeEnums.getCode());
        }
        ISyncTypeScheduler syncTypeScheduler = syncSchedulerOpt.get();
        String sn = UUIDUtils.newUUIDStr();
        GcMultilevelSyncProgressDTO progressDataDTO = new GcMultilevelSyncProgressDTO(sn, "GcMultilevelSync_" + syncTypeScheduler.code());
        this.progressService.createProgressData((ProgressData)progressDataDTO);
        syncContext.setSn(sn);
        syncContext.setType(typeEnums);
        progressDataDTO.setProgressValueAndRefresh(0.1);
        ReportSyncFileDTO fileDTO = syncTypeScheduler.exportFileToOss(syncContext);
        syncContext.setFileDTO(fileDTO);
        progressDataDTO.setProgressValueAndRefresh(0.5);
        syncTypeScheduler.afterExport(syncContext);
        progressDataDTO.setProgressValueAndRefresh(0.6);
        if (syncTypeScheduler.isSync()) {
            List<ReportDataSyncServerInfoVO> syncServerInfoVOS = syncTypeScheduler.getServerInfoVOList();
            List needSyncServerInfoVOS = syncServerInfoVOS.stream().filter(vo -> Boolean.TRUE.equals(vo.getStartFlag())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(needSyncServerInfoVOS)) {
                throw new BusinessRuntimeException("\u6ca1\u6709\u627e\u5230\u9700\u8981\u540c\u6b65\u7684\u5355\u4f4d");
            }
            for (ReportDataSyncServerInfoVO syncServerInfoVO : needSyncServerInfoVOS) {
                Optional<ISyncMethodScheduler> schedulerOpt = this.serviceManagerUploadTypeList.stream().filter(scheduler -> scheduler.code().equals(syncServerInfoVO.getSyncMethod())).findFirst();
                if (!schedulerOpt.isPresent()) {
                    LOGGER.error("\u670d\u52a1\u5730\u5740" + syncServerInfoVO.getUrl() + "\u672a\u627e\u5230\u76ee\u6807\u670d\u52a1\u5668\u7c7b\u578b\u8c03\u5ea6\u5668\uff1a" + syncServerInfoVO.getSyncMethod());
                    continue;
                }
                syncContext.setServerInfoVO(syncServerInfoVO);
                ISyncMethodScheduler syncMethodScheduler = schedulerOpt.get();
                boolean syncResult = syncMethodScheduler.sync(syncContext);
                syncMethodScheduler.afterSync(syncResult, syncContext);
            }
        }
        progressDataDTO.setProgressValueAndRefresh(1.0);
        return true;
    }

    @Override
    public Boolean multilevelImport(MultilevelImportContext importContext) {
        SyncTypeEnums type = importContext.getType();
        if (type == null) {
            throw new BusinessRuntimeException("\u540c\u6b65\u7c7b\u578b\u4e3a\u7a7a");
        }
        Optional<ISyncTypeScheduler> syncSchedulerOpt = this.syncTypeSchedulerList.stream().filter(student -> student.code().equals(type.getCode())).findFirst();
        if (!syncSchedulerOpt.isPresent()) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b\u7684\u8c03\u5ea6\u5668\uff1a" + type);
        }
        ISyncTypeScheduler syncTypeScheduler = syncSchedulerOpt.get();
        return syncTypeScheduler.importData(importContext);
    }

    @Override
    public Boolean multilevelReturn(MultilevelReturnContext contextEnv) {
        SyncTypeEnums typeEnums = contextEnv.getType();
        Optional<ISyncTypeScheduler> syncSchedulerOpt = this.syncTypeSchedulerList.stream().filter(v -> v.code().equals(typeEnums.getCode())).findFirst();
        if (!syncSchedulerOpt.isPresent()) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b\u7684\u8c03\u5ea6\u5668\uff1a" + typeEnums.getCode());
        }
        ISyncTypeScheduler syncTypeScheduler = syncSchedulerOpt.get();
        syncTypeScheduler.multilevelReturn(contextEnv);
        return true;
    }
}

