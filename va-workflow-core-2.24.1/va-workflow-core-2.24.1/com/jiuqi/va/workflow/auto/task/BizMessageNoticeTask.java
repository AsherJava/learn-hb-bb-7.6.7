/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.fasterxml.jackson.databind.node.TextNode
 *  com.jiuqi.va.biz.intf.autotask.AutoTask
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo
 *  com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam
 *  com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageTemplateSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageTemplateClient
 *  com.jiuqi.va.message.template.VaMessageTemplate
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateDTO
 */
package com.jiuqi.va.workflow.auto.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.jiuqi.va.biz.intf.autotask.AutoTask;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo;
import com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam;
import com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageTemplateSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageTemplateClient;
import com.jiuqi.va.message.template.VaMessageTemplate;
import com.jiuqi.va.message.template.domain.VaMessageTemplateDTO;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class BizMessageNoticeTask
implements AutoTask,
InitializingBean {
    @Autowired
    private VaMessageTemplateClient vaMessageTemplateClient;
    @Autowired
    private AuthUserClient authUserClient;
    @Autowired(required=false)
    private List<VaMessageTemplate> vaMessageTemplateList;
    private Map<String, Map<String, VaMessageTemplate>> vaMessageTemplateMap;

    public String getName() {
        return "BizMessageNotice";
    }

    public String getTitle() {
        return "\u4e1a\u52a1\u6d88\u606f\u901a\u77e5";
    }

    public String getAutoTaskModule() {
        return "general";
    }

    public Boolean canRetract() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public R execute(Object params) {
        ArrayList<UserDO> users;
        TenantDO param = (TenantDO)params;
        if (param.getExtInfo("bizCode") == null) {
            throw new WorkflowException("\u672a\u627e\u5230\u4e1a\u52a1\u6807\u8bc6\u53c2\u6570");
        }
        ArrayNode autoTaskParams = (ArrayNode)param.getExtInfo("autoTaskParam");
        List<String> userIds = this.getUserIds(autoTaskParams, users = new ArrayList<UserDO>());
        if (CollectionUtils.isEmpty(userIds)) {
            throw new WorkflowException("\u6d88\u606f\u63a5\u6536\u4eba\u4e3a\u7a7a");
        }
        Locale defaultLocale = LocaleContextHolder.getLocale();
        Map<String, List<UserDO>> languageUsersMap = this.packageLanguageUsersMap(users);
        for (Map.Entry<String, List<UserDO>> entry : languageUsersMap.entrySet()) {
            HashMap<String, Object> messageParamMap;
            String unitCode;
            VaMessageTemplateSendDTO vaMessageDO;
            ParamHandleResult paramHandleResult;
            String bizType;
            String messageTemplateName;
            VaMessageTemplate vaMessageTemplate;
            boolean diffLanguage;
            String language = entry.getKey();
            List<UserDO> languageUserDOList = entry.getValue();
            List<String> languageUserIds = languageUserDOList.stream().map(UserDO::getId).collect(Collectors.toList());
            boolean bl = diffLanguage = !Objects.equals(language, defaultLocale.toLanguageTag());
            if (diffLanguage) {
                String[] split = language.split("-");
                LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
            }
            try {
                vaMessageTemplate = this.getVaMessageTemplate(autoTaskParams);
                messageTemplateName = vaMessageTemplate == null ? "" : vaMessageTemplate.getName();
                Map<String, Object> todoParam = VaWorkflowUtils.getMap(param.getExtInfo("todoParam"));
                bizType = (String)todoParam.get("BIZTYPE");
                paramHandleResult = this.getParamHandleResult(param, languageUserIds, messageTemplateName, bizType);
                vaMessageDO = new VaMessageTemplateSendDTO();
                vaMessageDO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.issuesnotice"));
                vaMessageDO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.notice"));
                vaMessageDO.setCreateuser("system");
                vaMessageDO.setTitle(paramHandleResult.getTitle());
                unitCode = todoParam.get("UNITCODE") != null ? todoParam.get("UNITCODE").toString() : null;
                vaMessageDO.setUnitcode(unitCode);
                messageParamMap = new HashMap<String, Object>();
                if (vaMessageTemplate != null) {
                    vaMessageDO.setTemplateName(messageTemplateName);
                    todoParam.put("APPROVEUSERID", param.getExtInfo("approveUserId"));
                    VaWorkFlowDataUtils.getParamMap(todoParam, vaMessageTemplate.getTemplateParams(), messageParamMap, ShiroUtil.getTenantName());
                    messageParamMap.put("BIZDEFINE", paramHandleResult.getBizDefineTitle());
                    vaMessageDO.setParamMap(messageParamMap);
                    vaMessageDO.setFunctionModule(vaMessageTemplate.getFunction());
                }
            }
            finally {
                if (diffLanguage) {
                    LocaleContextHolder.setLocale(defaultLocale);
                }
            }
            List messageDetailInfoList = paramHandleResult.getMessageDetailInfoList();
            for (MessageDetailInfo messageDetailInfo : messageDetailInfoList) {
                vaMessageDO.setContent(messageDetailInfo.getContent());
                vaMessageDO.setReceiveUserIds(messageDetailInfo.getReceiveUserIds());
                vaMessageDO.setParam(messageDetailInfo.getParam());
                vaMessageDO.setUrlMap(messageDetailInfo.getUrlMap());
                vaMessageDO.setForbidEmail(true);
                this.vaMessageTemplateClient.sendMessage(vaMessageDO);
            }
            VaMessageTemplateSendDTO vaMessageTemplateSendDTO = new VaMessageTemplateSendDTO();
            vaMessageTemplateSendDTO.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
            vaMessageTemplateSendDTO.setReceiveUserIds(languageUserIds);
            vaMessageTemplateSendDTO.setTitle(paramHandleResult.getTitle());
            vaMessageTemplateSendDTO.setContent(paramHandleResult.getMailContent());
            vaMessageTemplateSendDTO.setUnitcode(unitCode);
            if (vaMessageTemplate != null) {
                vaMessageTemplateSendDTO.setTemplateName(messageTemplateName);
                vaMessageTemplateSendDTO.setParamMap(messageParamMap);
                vaMessageTemplateSendDTO.setFunctionModule(vaMessageTemplate.getFunction());
            } else if ("BILL".equalsIgnoreCase(bizType) && this.enableEmailTemplate()) {
                vaMessageTemplateSendDTO.setTemplateName("BILLNOTICE");
                vaMessageTemplateSendDTO.setParamMap(messageParamMap);
            }
            this.vaMessageTemplateClient.sendMessage(vaMessageTemplateSendDTO);
        }
        return R.ok();
    }

    private List<String> getUserIds(ArrayNode autoTaskParams, List<UserDO> users) {
        ArrayList<String> userIds = new ArrayList<String>();
        UserDTO userDTO = new UserDTO();
        for (JsonNode jsonNode : autoTaskParams) {
            UserDO userDO;
            JsonNode receiveUser = jsonNode.get("receiveUser");
            JsonNode realValue = receiveUser.get("realValue");
            if (realValue instanceof ArrayNode) {
                for (JsonNode real : (ArrayNode)realValue) {
                    userDTO.setId(real.asText());
                    UserDO userDO2 = this.authUserClient.get(userDTO);
                    if (userDO2 == null) continue;
                    userIds.add(userDO2.getId());
                    users.add(userDO2);
                }
                continue;
            }
            if (!(realValue instanceof TextNode) || (userDO = VaWorkFlowDataUtils.getOneUserData(ShiroUtil.getTenantName(), ((TextNode)realValue).asText())) == null) continue;
            userIds.add(userDO.getId());
            users.add(userDO);
        }
        return userIds;
    }

    private VaMessageTemplate getVaMessageTemplate(ArrayNode autoTaskParams) {
        JsonNode autoTaskParam = autoTaskParams.get(0);
        String function = autoTaskParam.get("function") == null ? "" : autoTaskParam.get("function").asText();
        String messageTemplateCode = autoTaskParam.get("messageTemplateCode") == null ? "" : autoTaskParam.get("messageTemplateCode").asText();
        VaMessageTemplate vaMessageTemplate = null;
        if (StringUtils.hasText(function)) {
            vaMessageTemplate = this.vaMessageTemplateMap.get(function).get(messageTemplateCode);
        } else {
            for (Map<String, VaMessageTemplate> templateMap : this.vaMessageTemplateMap.values()) {
                if (!templateMap.containsKey(messageTemplateCode)) continue;
                vaMessageTemplate = templateMap.get(messageTemplateCode);
                break;
            }
        }
        return vaMessageTemplate;
    }

    private ParamHandleResult getParamHandleResult(TenantDO param, List<String> userIds, String messageTemplateName, String bizType) {
        MessageNoticeParam messageNoticeParam = new MessageNoticeParam();
        messageNoticeParam.setReceiveUserIdList(userIds);
        messageNoticeParam.setParam(param.getExtInfo());
        if (!StringUtils.hasText(messageTemplateName) && "BILL".equalsIgnoreCase(bizType)) {
            messageTemplateName = "BILLNOTICE";
        }
        messageNoticeParam.setMessageTemplateCode(messageTemplateName);
        String bizDefine = (String)param.getExtInfo("bizType");
        BussinessClient bussinessClient = VaWorkflowUtils.getBussinessClient(bizType, bizDefine);
        R r = bussinessClient.handleMessageNoticeParam(messageNoticeParam);
        if (r.getCode() != 0) {
            throw new WorkflowException("\u5904\u7406\u4e1a\u52a1\u6d88\u606f\u901a\u77e5\u53c2\u6570\u5931\u8d25\uff0c" + r.getMsg());
        }
        Object data = r.get((Object)"data");
        return (ParamHandleResult)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)data), ParamHandleResult.class);
    }

    private boolean enableEmailTemplate() {
        VaMessageTemplateDTO templateParam = new VaMessageTemplateDTO();
        templateParam.setName("BILLNOTICE");
        templateParam.setFunctionmodule("VABILLNOTICE");
        R r = this.vaMessageTemplateClient.getTemplateConfig(templateParam);
        if (r.getCode() != 0 || !r.containsKey((Object)"templateConfig")) {
            return false;
        }
        VaMessageTemplateDTO templateConfig = (VaMessageTemplateDTO)r.get((Object)"templateConfig");
        Map configMap = templateConfig.getConfigJson();
        if (configMap == null || !configMap.containsKey("EMAIL")) {
            return false;
        }
        Map emailConfig = (Map)configMap.get("EMAIL");
        return emailConfig.containsKey("enable") && Boolean.TRUE.equals(emailConfig.get("enable"));
    }

    private Map<String, List<UserDO>> packageLanguageUsersMap(List<UserDO> users) {
        String defaultLanguage = LocaleContextHolder.getLocale().toLanguageTag();
        HashMap<String, List<UserDO>> i18nUsersMap = new HashMap<String, List<UserDO>>();
        for (UserDO user : users) {
            String language = user.getLanguage();
            if (!StringUtils.hasText(language)) {
                language = defaultLanguage;
            }
            if (i18nUsersMap.containsKey(language)) {
                ((List)i18nUsersMap.get(language)).add(user);
                continue;
            }
            ArrayList<UserDO> userList = new ArrayList<UserDO>();
            userList.add(user);
            i18nUsersMap.put(language, userList);
        }
        return i18nUsersMap;
    }

    public R retrack(Object params) {
        return R.ok();
    }

    @Override
    public void afterPropertiesSet() {
        this.vaMessageTemplateMap = new HashMap<String, Map<String, VaMessageTemplate>>();
        for (VaMessageTemplate vaMessageTemplate : this.vaMessageTemplateList) {
            Map<Object, Object> functionMap;
            String function = vaMessageTemplate.getFunction();
            String name = vaMessageTemplate.getName();
            if (this.vaMessageTemplateMap.containsKey(function)) {
                functionMap = this.vaMessageTemplateMap.get(function);
            } else {
                functionMap = new HashMap();
                this.vaMessageTemplateMap.put(function, functionMap);
            }
            functionMap.put(name, vaMessageTemplate);
        }
    }
}

