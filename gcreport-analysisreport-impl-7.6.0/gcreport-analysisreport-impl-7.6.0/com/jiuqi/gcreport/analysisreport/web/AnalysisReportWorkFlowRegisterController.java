/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.analysisreport.web;

import com.jiuqi.gcreport.analysisreport.service.AnalysisReportDataService;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/gcreport/v1/"})
public class AnalysisReportWorkFlowRegisterController {
    @Autowired
    protected WorkflowServerClient workflowServerClient;
    @Autowired
    private AnalysisReportDataService analysisReportDataService;

    @PostMapping(value={"gcreport-analysisreport/biz/title/get"})
    public R getWorkFlowForTitle(@RequestBody TenantDO tenantDO) {
        System.out.println("\u83b7\u53d6\u4e1a\u52a1\u5bf9\u8c61\u6807\u9898\u63a5\u53e3");
        R result = new R();
        result.put("code", (Object)0);
        result.put("msg", (Object)"");
        result.put("bizcodeTitle", (Object)"\u5408\u5e76\u62a5\u8868");
        result.put("bizDefineTitle", (Object)"\u5206\u6790\u62a5\u544a");
        return result;
    }

    @PostMapping(value={"gcreport-analysisreport/bussiness/message/template/param/get"})
    R getWorkFlowMessageForKey(@RequestBody TenantDO tenantDO) {
        System.out.println("\u83b7\u53d6\u4e1a\u52a1\u53d1\u9001\u6d88\u606f\u5185\u5bb9\u63a5\u53e3");
        return new R();
    }

    @PostMapping(value={"gcreport-analysisreport/bussiness/param/variables/get"})
    Map<String, Object> getWorkFlowParamForKey(@RequestBody TenantDO tenantDO) {
        System.out.println("\u4e1a\u52a1\u53c2\u6570\u83b7\u53d6\u63a5\u53e3");
        return (Map)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)tenantDO), Map.class);
    }

    @PostMapping(value={"gcreport-analysisreport/bussiness/state/update"})
    R updateWorkFlowParamForKey(@RequestBody TenantDO tenantDO) {
        System.out.println("\u4e1a\u52a1\u72b6\u6001\u66f4\u65b0\u63a5\u53e3");
        return null;
    }

    @PostMapping(value={"gcreport-analysisreport/biz/action/agree"})
    R agree(@RequestBody TenantDO tenantDO) {
        return new R();
    }

    @PostMapping(value={"gcreport-analysisreport/biz/action/reject"})
    R reject(@RequestBody TenantDO tenantDO) {
        System.out.println("\u4e1a\u52a1\u7aef\u5b9e\u73b0\u7684\u9a73\u56de\u63a5\u53e3");
        R result = new R();
        return result;
    }

    @PostMapping(value={"gcreport-analysisreport/biz/action/retract"})
    R retract(@RequestBody TenantDO tenantDO) {
        System.out.println("\u4e1a\u52a1\u7aef\u5b9e\u73b0\u7684\u53d6\u56de\u63a5\u53e3");
        R result = new R();
        return result;
    }

    @PostMapping(value={"gcreport-analysisreport/biz/todo/param/get"})
    Map<String, Object> get(@RequestBody TenantDO tenantDO) {
        System.out.println("\u4ee3\u529e\u63a5\u53e3");
        return null;
    }

    @PostMapping(value={"gcreport-analysisreport/action/agree"})
    R agreeTest(@RequestBody WorkflowDTO tenantDO) {
        HashMap<String, String> key2Value = new HashMap<String, String>();
        key2Value.put("CREATEUSER", ShiroUtil.getUser().getId());
        tenantDO.setExtInfo(key2Value);
        return this.workflowServerClient.completeTask(tenantDO);
    }

    @PostMapping(value={"gcreport-analysisreport/gcanalysis/bussiness/process/view"})
    R processView(@RequestBody WorkflowDTO tenantDO) {
        return new R();
    }

    @PostMapping(value={"gcreport-analysisreport/action/reject"})
    R todoReject(@RequestBody WorkflowDTO tenantDO) {
        System.out.println("\u4e1a\u52a1\u7aef\u5b9e\u73b0\u7684\u53d6\u56de\u63a5\u53e3");
        R result = new R();
        return result;
    }
}

