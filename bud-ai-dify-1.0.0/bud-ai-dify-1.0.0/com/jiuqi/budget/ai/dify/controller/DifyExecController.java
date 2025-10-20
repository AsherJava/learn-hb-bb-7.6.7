/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.jiuqi.np.core.context.ContextOrganization
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.budget.ai.dify.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.jiuqi.budget.ai.dify.service.DifyService;
import com.jiuqi.np.core.context.ContextOrganization;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/budget/dify"})
public class DifyExecController {
    private static final Logger logger = LoggerFactory.getLogger(DifyExecController.class);
    @Autowired
    private DifyService difyService;
    @Value(value="${server.port}")
    private String port;
    @Value(value="${spring.cloud.client.ip-address}")
    private String ip;
    @Value(value="${budget.dify.host}")
    private String difyHost;
    @Autowired
    private OrgDataClient orgDataClient;

    @PostMapping(value={"/workflow/exec"})
    public Map<String, Object> workflowExec(@RequestBody Map<String, String> param) {
        LocalDateTime begin = LocalDateTime.now();
        String apiKey = param.remove("apiKey");
        String token = ShiroUtil.getToken();
        param.put("token", token);
        String bizServer = param.get("bizServer");
        if (!StringUtils.hasText(bizServer)) {
            param.put("bizServer", this.ip + ":" + this.port);
        }
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        String userName = user.getFullname();
        ContextOrganization organization = context.getOrganization();
        String org = organization != null ? organization.getCode() : user.getOrgCode();
        OrgDTO orgParam = new OrgDTO();
        orgParam.setCategoryname("MD_ORG");
        orgParam.setCode(org);
        orgParam.setAuthType(OrgDataOption.AuthType.NONE);
        OrgDO orgDO = this.orgDataClient.get(orgParam);
        param.put("orgTitle", orgDO.getName());
        param.put("orgCode", org);
        param.put("userName", userName);
        param.put("orgParent", orgDO.getParentcode());
        logger.info("workflowExec-param {}", (Object)param);
        JsonNode jsonNode = this.difyService.exeWorkFlow(this.difyHost, apiKey, param);
        HashMap<String, Object> result = new HashMap<String, Object>();
        JsonNode dataNode = jsonNode.get("data");
        JsonNode statusNode = dataNode.get("status");
        String status = statusNode.asText();
        if ("failed".equals(status)) {
            String error = dataNode.get("error").asText();
            logger.info("workflowExec failed [{}] error: {}", (Object)Duration.between(begin, LocalDateTime.now()), (Object)error);
            result.put("error", error);
            return result;
        }
        JsonNode outputsNode = dataNode.get("outputs");
        Iterator fields = outputsNode.fields();
        while (fields.hasNext()) {
            Map.Entry next = (Map.Entry)fields.next();
            String key = (String)next.getKey();
            String text = ((JsonNode)next.getValue()).asText();
            result.put(key, text);
        }
        result.put("sys_OrgTitle", orgDO.getName());
        result.put("sys_OrgCode", org);
        result.put("sys_UserName", userName);
        logger.info("workflowExec success [{}] result: {}", (Object)Duration.between(begin, LocalDateTime.now()), (Object)result);
        return result;
    }
}

