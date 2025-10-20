/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO
 *  com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums
 *  com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO
 *  feign.Request$Options
 *  org.springframework.web.bind.annotation.RestController
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.gcreport.reportdatasync.api.MultilevelSyncClient;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelImportContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelReturnContext;
import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;
import com.jiuqi.gcreport.reportdatasync.scheduler.ISyncTypeScheduler;
import com.jiuqi.gcreport.reportdatasync.service.MultilevelSyncService;
import com.jiuqi.gcreport.reportdatasync.vo.MultilevelSyncVO;
import feign.Request;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Primary
public class MultilevelSyncController
implements MultilevelSyncClient {
    @Autowired
    private MultilevelSyncService syncService;
    @Autowired
    private List<ISyncTypeScheduler> syncTypeSchedulerList;

    public BusinessResponseEntity<Boolean> multilevelSync(MultilevelSyncVO multilevelSyncVO) {
        Optional<ISyncTypeScheduler> syncSchedulerOpt = this.syncTypeSchedulerList.stream().filter(student -> student.code().equals(multilevelSyncVO.getType())).findFirst();
        if (!syncSchedulerOpt.isPresent()) {
            throw new BusinessRuntimeException("\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b\u7684\u8c03\u5ea6\u5668\uff1a" + multilevelSyncVO.getType());
        }
        MultilevelSyncContext syncContext = syncSchedulerOpt.get().buildContext(multilevelSyncVO.getInfo());
        syncContext.setType(SyncTypeEnums.getSyncTypeEnumsByCode((String)multilevelSyncVO.getType()));
        return BusinessResponseEntity.ok((Object)this.syncService.multilevelSync(syncContext));
    }

    public BusinessResponseEntity<Boolean> multilevelImport(Request.Options options, String importContextJson, MultipartFile[] syncAttachFiles) {
        MultilevelImportContext importContext = (MultilevelImportContext)JsonUtils.readValue((String)importContextJson, MultilevelImportContext.class);
        ReportSyncFileDTO fileDTO = new ReportSyncFileDTO();
        fileDTO.setMainFile(syncAttachFiles[0]);
        if (syncAttachFiles.length > 1) {
            fileDTO.setDesAttachFile(syncAttachFiles[1]);
        }
        importContext.setFileDTO(fileDTO);
        return BusinessResponseEntity.ok((Object)this.syncService.multilevelImport(importContext));
    }

    public BusinessResponseEntity<Boolean> multilevelReturn(MultilevelSyncVO multilevelSyncVO) {
        String type = multilevelSyncVO.getType();
        SyncTypeEnums typeEnums = SyncTypeEnums.getSyncTypeEnumsByCode((String)type);
        if (typeEnums == null) {
            throw new BusinessRuntimeException("\u540c\u6b65\u7c7b\u578b\u4e3a\u7a7a");
        }
        MultilevelReturnContext multilevelReturnContext = new MultilevelReturnContext();
        multilevelReturnContext.setInfoJson(multilevelSyncVO.getInfo());
        multilevelReturnContext.setType(typeEnums);
        return BusinessResponseEntity.ok((Object)this.syncService.multilevelReturn(multilevelReturnContext));
    }
}

