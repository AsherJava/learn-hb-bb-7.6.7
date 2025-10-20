/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AuditProgressReusltDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit/log"})
public class AidocauditLogController {
    @Autowired
    private IAidocauditLogService aidocauditLogService;

    @GetMapping(value={"/getAuditProgress"})
    public BusinessResponseEntity<AuditProgressReusltDTO> getAuditProgress() {
        AuditProgressReusltDTO auditProgressReusltDTO = this.aidocauditLogService.getAuditProgress();
        return BusinessResponseEntity.ok((Object)auditProgressReusltDTO);
    }
}

