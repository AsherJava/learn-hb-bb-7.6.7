/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AuditDetailParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultScoreParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultitemOrderDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditAttachmentDetailService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit/ruleview/attachment"})
public class AidocauditAttachmentDetailController {
    @Autowired
    private IAidocauditAttachmentDetailService attachmentDetailService;

    @PostMapping(value={"/auditDetails"})
    public List<ResultitemOrderDTO> auditDetails(@RequestBody AuditDetailParamDTO param) {
        return this.attachmentDetailService.auditDetails(param.getId(), param.getResultType());
    }

    @PostMapping(value={"/resultScore"})
    public BusinessResponseEntity<Map<String, Object>> resultScore(@RequestBody ResultScoreParamDTO param) {
        return BusinessResponseEntity.ok(this.attachmentDetailService.resultScore(param.getRuleId(), param.getResultId()));
    }
}

