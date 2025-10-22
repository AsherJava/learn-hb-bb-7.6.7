/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditResultDetailParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.ResultDetailDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditUnitdetailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit/ruleview/unitdetail"})
public class AidocauditUnitdetailController {
    @Autowired
    private IAidocauditUnitdetailService unitdetailService;

    @PostMapping(value={"/orgAuditResults"})
    public List<AidocauditResultDTO> orgAuditResults(@RequestBody AuditParamDTO param) {
        return this.unitdetailService.orgAuditResults(param);
    }

    @PostMapping(value={"/ruleAuditResults"})
    public List<RuleItemTreeDTO> ruleAuditResults(@RequestBody AuditParamDTO param) {
        return this.unitdetailService.ruleAuditResults(param);
    }

    @PostMapping(value={"/ruleAuditResultDetail"})
    public List<ResultDetailDTO> ruleAuditResultDetail(@RequestBody AidocauditResultDetailParamDTO param) {
        return this.unitdetailService.ruleAuditResultDetail(param);
    }
}

