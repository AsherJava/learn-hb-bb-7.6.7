/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AuditParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.CheckScoredResultDTO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit"})
public class AidocauditScoreController {
    @Autowired
    private IAidocauditScoreService scoreService;

    @ResponseBody
    @PostMapping(value={"/checkScoredResult"})
    public BusinessResponseEntity<CheckScoredResultDTO> checkHistoryResult(@RequestBody AuditParamDTO param) {
        return this.scoreService.checkScoredResult(param);
    }

    @ResponseBody
    @PostMapping(value={"/audit"})
    public BusinessResponseEntity<Boolean> audit(@RequestBody AuditParamDTO param) {
        return this.scoreService.audit(param);
    }
}

