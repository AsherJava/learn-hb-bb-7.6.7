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

import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditRuleViewService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit/ruleview"})
public class AidocauditRuleViewController {
    @Autowired
    private IAidocauditRuleViewService viewService;

    @PostMapping(value={"/orguploadedstatus"})
    public Map<String, Object> orguploadedstatus(@RequestBody AuditParamDTO param) {
        return this.viewService.orguploadedstatus(param);
    }

    @PostMapping(value={"/orgauditstatus"})
    public Map<String, Object> orgauditstatus(@RequestBody AuditParamDTO param) {
        return this.viewService.orgauditstatus(param);
    }

    @PostMapping(value={"/orgquestionstatus"})
    public List<Map<String, Object>> orgquestionstatus(@RequestBody AuditParamDTO param) {
        return this.viewService.orgquestionstatus(param);
    }

    @PostMapping(value={"/orglowestscorestatus"})
    public List<Map<String, Object>> orglowestscorestatus(@RequestBody AuditParamDTO param) {
        return this.viewService.orglowestscorestatus(param);
    }
}

