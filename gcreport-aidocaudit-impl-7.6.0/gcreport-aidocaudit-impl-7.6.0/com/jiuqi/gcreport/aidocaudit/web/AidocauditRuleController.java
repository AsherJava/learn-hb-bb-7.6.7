/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.aidocaudit.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.aidocaudit.dto.AidocauditRuleParamDTO;
import com.jiuqi.gcreport.aidocaudit.dto.RuleDetailDTO;
import com.jiuqi.gcreport.aidocaudit.eo.AidocauditRuleEO;
import com.jiuqi.gcreport.aidocaudit.service.IAidocauditRuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/aidocaudit/rule"})
public class AidocauditRuleController {
    @Autowired
    private IAidocauditRuleService ruleService;

    @GetMapping(value={"/list"})
    public BusinessResponseEntity<List<AidocauditRuleEO>> list() {
        return this.ruleService.list();
    }

    @GetMapping(value={"/query"})
    public BusinessResponseEntity<AidocauditRuleEO> query(@RequestParam String id) {
        return this.ruleService.query(id);
    }

    @PostMapping(value={"/add"})
    public BusinessResponseEntity<Boolean> add(@RequestBody AidocauditRuleParamDTO rule) {
        return this.ruleService.add(rule);
    }

    @PostMapping(value={"/update"})
    public BusinessResponseEntity<Boolean> updateRule(@RequestBody AidocauditRuleParamDTO rule) {
        return this.ruleService.updateRule(rule);
    }

    @PostMapping(value={"/generate"})
    public BusinessResponseEntity<Boolean> generate(@RequestBody AidocauditRuleParamDTO rule) {
        return this.ruleService.generate(rule.getId());
    }

    @PostMapping(value={"/queryRuleItem"})
    public BusinessResponseEntity<RuleDetailDTO> queryRuleItem(@RequestBody AidocauditRuleParamDTO rule) {
        RuleDetailDTO result = this.ruleService.queryRuleItem(rule.getId());
        return BusinessResponseEntity.ok((Object)result);
    }

    @PostMapping(value={"/item/update"})
    public BusinessResponseEntity<Boolean> updateRuleItem(@RequestBody AidocauditRuleParamDTO rule) {
        return this.ruleService.updateRuleItem(rule);
    }

    @PostMapping(value={"/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestBody AidocauditRuleParamDTO rule) {
        return this.ruleService.delete(rule.getId(), true);
    }

    @GetMapping(value={"/queryReportType"})
    public BusinessResponseEntity<List<AidocauditRuleEO>> queryReportType(@RequestParam Integer type) {
        return this.ruleService.queryReportType(type);
    }
}

