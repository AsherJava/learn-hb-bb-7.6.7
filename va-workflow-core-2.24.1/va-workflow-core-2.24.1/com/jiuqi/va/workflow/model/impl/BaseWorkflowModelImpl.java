/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.CommonWorkflowModelImpl
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.meta.ModuleDTO
 *  com.jiuqi.va.domain.option.OptionItemDO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.todo.TaskDTO
 *  com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo
 *  com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam
 *  com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO
 *  com.jiuqi.va.domain.workflow.service.WorkflowSevice
 *  com.jiuqi.va.feign.client.AuthUserClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BussinessClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.TodoBusinessClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  com.jiuqi.va.message.domain.VaMessageTemplateSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageTemplateClient
 *  com.jiuqi.va.message.template.VaMessageTemplate
 *  com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO
 *  com.jiuqi.va.trans.service.VaTransMessageService
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  org.apache.shiro.util.ThreadContext
 */
package com.jiuqi.va.workflow.model.impl;

import com.jiuqi.va.biz.impl.model.CommonWorkflowModelImpl;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.meta.ModuleDTO;
import com.jiuqi.va.domain.option.OptionItemDO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.todo.TaskDTO;
import com.jiuqi.va.domain.todo.VaTodoTaskTypeEnum;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.domain.workflow.auto.task.MessageDetailInfo;
import com.jiuqi.va.domain.workflow.auto.task.MessageNoticeParam;
import com.jiuqi.va.domain.workflow.auto.task.ParamHandleResult;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.plusapproval.PlusApprovalInfoDO;
import com.jiuqi.va.domain.workflow.service.WorkflowSevice;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BussinessClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.TodoBusinessClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.mapper.domain.TenantDO;
import com.jiuqi.va.message.domain.VaMessageTemplateSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageTemplateClient;
import com.jiuqi.va.message.template.VaMessageTemplate;
import com.jiuqi.va.message.template.domain.VaMessageTemplateParamDO;
import com.jiuqi.va.trans.service.VaTransMessageService;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.config.BizTypeConfig;
import com.jiuqi.va.workflow.config.ThreadPoolConst;
import com.jiuqi.va.workflow.message.VaWorkflowApprovaledTemplate;
import com.jiuqi.va.workflow.message.VaWorkflowCarbonCopyTemplate;
import com.jiuqi.va.workflow.message.VaWorkflowNotifyCreateUserTemplate;
import com.jiuqi.va.workflow.message.VaWorkflowRejectTemplate;
import com.jiuqi.va.workflow.message.VaWorkflowWaitingApprovalTemplate;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.model.WorkflowModelHelper;
import com.jiuqi.va.workflow.utils.VaWorkFlowDataUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import com.jiuqi.va.workflow.utils.VaWorkflowUtils;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public abstract class BaseWorkflowModelImpl
extends CommonWorkflowModelImpl
implements WorkflowModel {
    private static final Logger log = LoggerFactory.getLogger(BaseWorkflowModelImpl.class);
    protected static final String PRE_KEY = "processInstanceId:";
    protected static final String KEY_BIZ_TYPE = "bizType";
    protected WorkflowSevice workflowSevice;
    protected VaTransMessageService vaTransMessageService;
    protected VaMessageTemplateClient vaMessageTemplateClient;
    protected MetaDataClient metaDataClient;
    protected OrgDataClient orgDataClient;
    protected EnumDataClient enumDataClient;
    protected AuthUserClient authUserClient;
    protected BaseDataClient baseDataClient;
    protected VaSystemOptionClient vaSystemOptionClient;
    protected BizTypeConfig bizTypeConfig;
    protected WorkflowModelHelper workflowModelHelper;
    protected TodoClient todoClient;

    protected Map<String, Object> getWorkflowVariables(ProcessDO processDO, String bizType, String bizDefine) {
        BussinessClient bussinessClient;
        if ("BILL".equals(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
        }
        TenantDO tenant = new TenantDO();
        tenant.addExtInfo("bizCode", (Object)processDO.getBizcode());
        tenant.addExtInfo(KEY_BIZ_TYPE, (Object)bizDefine);
        tenant.addExtInfo("workflowdefinekey", (Object)processDO.getDefinekey());
        tenant.addExtInfo("workflowdefineversion", (Object)processDO.getDefineversion());
        tenant.setTraceId(Utils.getTraceId());
        return bussinessClient.getBussinessParamVariables(tenant);
    }

    protected void sendMessage(List<Map<String, Object>> todos, String tenantName, WorkflowDTO workflowDTO) {
        Locale parentLocale = LocaleContextHolder.getLocale();
        ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(() -> {
            try {
                ThreadContext.unbindSubject();
                ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
                Utils.setTraceId((String)workflowDTO.getTraceId());
                LocaleContextHolder.setLocale(parentLocale);
                String bizCode = workflowDTO.getBizCode();
                String bizDefine = workflowDTO.getBizDefine();
                String bizType = workflowDTO.getBizType();
                OptionItemDO optionItemDO = new OptionItemDO();
                optionItemDO.setName("SYSADDR");
                List optionItemList = this.vaSystemOptionClient.list(optionItemDO);
                String sysAddress = ((OptionItemVO)optionItemList.get(0)).getVal();
                boolean hashMode = true;
                String url = sysAddress + "/anon/wxwork/login?tenantName=" + tenantName + "&hashMode=" + hashMode + "&url=";
                String createUser = null;
                String bizDefineTitle = null;
                String bizCodeTitle = null;
                VaWorkflowWaitingApprovalTemplate template = (VaWorkflowWaitingApprovalTemplate)((Object)((Object)ApplicationContextRegister.getBean(VaWorkflowWaitingApprovalTemplate.class)));
                List<VaMessageTemplateParamDO> params = template.getTemplateParams();
                Boolean isForbidEmail = workflowDTO.isDisableSendMailFlag();
                Boolean isForbidSMS = workflowDTO.isDisableSendSMSFlag();
                Locale defaultLocale = LocaleContextHolder.getLocale();
                HashMap<String, String> languageBizDefineMap = new HashMap<String, String>();
                HashMap<String, String> languageBizDefineCodeMap = new HashMap<String, String>();
                for (Map todo : todos) {
                    List<String> users;
                    String taskId = (String)todo.get("TASKID");
                    String unitcode = (String)todo.get("UNITCODE");
                    String taskDefineKey = (String)todo.get("TASKDEFINEKEY");
                    if (!isForbidEmail.booleanValue()) {
                        isForbidEmail = this.isForbidEmail(taskDefineKey);
                    }
                    if (createUser == null) {
                        createUser = todo.get("CREATEUSER") != null ? (String)todo.get("CREATEUSER") : (String)workflowDTO.getExtInfo("CREATEUSER");
                        if (createUser == null) {
                            throw new RuntimeException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570\uff0c \u521b\u5efa\u4eba\u4e3a\u7a7a");
                        }
                        UserDTO userDTO = new UserDTO();
                        userDTO.setId(createUser);
                        UserDO userDO = this.authUserClient.get(userDTO);
                        createUser = userDO.getName();
                    }
                    if ((users = VaWorkflowUtils.getList(todo.get("USERS"))).isEmpty()) {
                        users.add((String)todo.get("PARTICIPANT"));
                    }
                    Map<String, List<String>> i18nUserIdsMap = this.packageLanguageUsersMap(users);
                    for (Map.Entry<String, List<String>> entry : i18nUserIdsMap.entrySet()) {
                        VaMessageTemplateSendDTO vaMessageSendDTO;
                        boolean diffLanguage;
                        String language = entry.getKey();
                        List<String> userIds = entry.getValue();
                        boolean bl = diffLanguage = !Objects.equals(language, defaultLocale.toLanguageTag());
                        if (diffLanguage) {
                            String[] split = language.split("-");
                            LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                        }
                        try {
                            String base64Url = null;
                            if ("BILL".equalsIgnoreCase(bizType)) {
                                if (languageBizDefineMap.containsKey(language + bizDefine)) {
                                    bizDefineTitle = (String)languageBizDefineMap.get(language + bizDefine);
                                } else {
                                    TenantDO tenantDO = new TenantDO();
                                    tenantDO.setTraceId(Utils.getTraceId());
                                    tenantDO.addExtInfo("defineCode", (Object)bizDefine);
                                    R r = this.metaDataClient.findMetaInfoByDefineCode(tenantDO);
                                    bizDefineTitle = (String)r.get((Object)"title");
                                    languageBizDefineMap.put(language + bizDefine, bizDefineTitle);
                                }
                                bizCodeTitle = bizCode;
                                String todoUrl = "/gmt/bill/billApproval?fromType=0&defineCode=" + bizDefine + "&billCode=" + bizCode + "&taskCode=";
                                base64Url = new String(VaWorkflowUtils.encodeUrlSafe((todoUrl + taskId).getBytes(StandardCharsets.UTF_8)));
                            } else {
                                String key = language + bizDefine + bizCode;
                                if (languageBizDefineMap.containsKey(key)) {
                                    bizDefineTitle = (String)languageBizDefineMap.get(key);
                                    bizCodeTitle = (String)languageBizDefineCodeMap.get(key);
                                } else {
                                    TenantDO tenantDO = new TenantDO();
                                    tenantDO.setTraceId(Utils.getTraceId());
                                    tenantDO.addExtInfo("bizCode", (Object)bizCode);
                                    tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
                                    BussinessClient bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                                    R r = bussinessClient.getBizTitle(tenantDO);
                                    if (r.getCode() == 0) {
                                        bizDefineTitle = r.get((Object)"bizDefineTitle") != null ? String.valueOf(r.get((Object)"bizDefineTitle")) : null;
                                        bizCodeTitle = r.get((Object)"bizcodeTitle") != null ? String.valueOf(r.get((Object)"bizcodeTitle")) : null;
                                        languageBizDefineMap.put(key, bizDefineTitle);
                                        languageBizDefineCodeMap.put(key, bizCodeTitle);
                                    }
                                }
                                if ("BUDGET".equalsIgnoreCase(bizType)) {
                                    String todoUrl = "/budget/main?fromType=0&BIZCODE=" + bizCode + "&isTodo=true&TASKID=" + taskId;
                                    base64Url = new String(VaWorkflowUtils.encodeUrlSafe(todoUrl.getBytes(StandardCharsets.UTF_8)));
                                }
                            }
                            HashMap<String, Object> messageParamMap = new HashMap<String, Object>();
                            messageParamMap.put("BIZDEFINECODE", bizDefine);
                            messageParamMap.put("BIZDEFINE", bizDefineTitle);
                            messageParamMap.put("BIZCODE", bizCodeTitle);
                            if ("BUDGET".equalsIgnoreCase(bizType)) {
                                messageParamMap.put("BIZTITLE", bizCodeTitle);
                            }
                            VaWorkFlowDataUtils.getParamMap(todo, params, messageParamMap, tenantName);
                            HashMap<String, String> urlMap = new HashMap<String, String>(16);
                            urlMap.put("wxWorkUrl", url + base64Url);
                            urlMap.put("emailUrl", url + base64Url);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("url", "/#/sso/VaNeedToDo?taskCode=" + taskId);
                            map.put("appName", "todo-app");
                            map.put("appTitle", VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todo") + "-" + bizDefineTitle + bizCodeTitle);
                            map.put("funcName", "vaApprovalPlugin");
                            HashMap<String, String> appConfigMap = new HashMap<String, String>();
                            appConfigMap.put("taskCode", taskId);
                            map.put("appConfig", JSONUtil.toJSONString(appConfigMap));
                            vaMessageSendDTO = new VaMessageTemplateSendDTO();
                            vaMessageSendDTO.setTraceId(Utils.getTraceId());
                            vaMessageSendDTO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todonotice"));
                            vaMessageSendDTO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todo"));
                            vaMessageSendDTO.setCreateuser("system");
                            String content = bizDefineTitle + " " + VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.createbilluser") + createUser + " " + VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todoissues") + bizCodeTitle;
                            vaMessageSendDTO.setTitle(content);
                            vaMessageSendDTO.setContent(content);
                            vaMessageSendDTO.setParam(JSONUtil.toJSONString(map));
                            vaMessageSendDTO.setParamMap(messageParamMap);
                            vaMessageSendDTO.setUrlMap(urlMap);
                            vaMessageSendDTO.setTemplateName("WAITINGAPPROVAL");
                            vaMessageSendDTO.setFunctionModule("VAWORKFLOW");
                            vaMessageSendDTO.setForbidEmail(isForbidEmail.booleanValue());
                            vaMessageSendDTO.setForbidSMS(isForbidSMS.booleanValue());
                            vaMessageSendDTO.setUnitcode(unitcode);
                            vaMessageSendDTO.setReceiveUserIds(userIds);
                        }
                        finally {
                            if (diffLanguage) {
                                LocaleContextHolder.setLocale(defaultLocale);
                            }
                        }
                        this.vaMessageTemplateClient.sendMessage(vaMessageSendDTO);
                    }
                }
            }
            catch (Exception e) {
                log.error("\u53d1\u9001\u5f85\u529e\u901a\u77e5\u6d88\u606f\u5931\u8d25", e);
            }
            finally {
                ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            }
        });
    }

    private Map<String, List<String>> packageLanguageUsersMap(List<String> users) {
        String defaultLanguage = LocaleContextHolder.getLocale().toLanguageTag();
        HashMap<String, List<String>> i18nUserIdsMap = new HashMap<String, List<String>>();
        for (String user : users) {
            UserDO userDO = VaWorkFlowDataUtils.getOneUserData(null, user);
            if (userDO == null) continue;
            String language = userDO.getLanguage();
            if (!StringUtils.hasText(language)) {
                language = defaultLanguage;
            }
            if (i18nUserIdsMap.containsKey(language)) {
                ((List)i18nUserIdsMap.get(language)).add(user);
                continue;
            }
            ArrayList<String> userList = new ArrayList<String>();
            userList.add(user);
            i18nUserIdsMap.put(language, userList);
        }
        return i18nUserIdsMap;
    }

    public void sendCarbonCopyMessage(WorkflowDTO workflowDTO, Map<String, Object> currentTask, UserLoginDTO user) {
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return;
        }
        if (CollectionUtils.isEmpty(workflowDTO.getCarbonCopyUserIds())) {
            return;
        }
        Locale parentLocale = LocaleContextHolder.getLocale();
        SendCarbonCopyMessageTask sendCarbonCopyMessageTask = new SendCarbonCopyMessageTask(workflowDTO, user, currentTask, parentLocale);
        ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(sendCarbonCopyMessageTask);
    }

    private ParamHandleResult getParamHandleResult(Map<String, Object> param, List<String> userIds, String bizDefine, String bizType) {
        MessageNoticeParam messageNoticeParam = new MessageNoticeParam();
        messageNoticeParam.setReceiveUserIdList(userIds);
        messageNoticeParam.setParam(param);
        messageNoticeParam.setMessageTemplateCode("CARBONCOPYMESSAGE");
        BussinessClient bussinessClient = VaWorkflowUtils.getBussinessClient(bizType, bizDefine);
        R r = bussinessClient.handleMessageNoticeParam(messageNoticeParam);
        if (r.getCode() != 0) {
            throw new WorkflowException("\u5904\u7406\u5de5\u4f5c\u6d41\u6284\u9001\u901a\u77e5\u53c2\u6570\u5931\u8d25\uff0c" + r.getMsg());
        }
        Object data = r.get((Object)"data");
        return (ParamHandleResult)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)data), ParamHandleResult.class);
    }

    protected void counterSignerAuditSendMessage(WorkflowDTO workflowDTO, Map<String, Object> currentTask, UserLoginDTO user) {
        if (Objects.isNull(currentTask) || Objects.isNull(workflowDTO)) {
            return;
        }
        String counterSignFlag = String.valueOf(currentTask.get("COUNTERSIGNFLAG"));
        if (!"1".equals(counterSignFlag)) {
            return;
        }
        String taskType = String.valueOf(currentTask.get("TASKTYPE"));
        if (!String.valueOf(VaTodoTaskTypeEnum.JOIN.getValue()).equals(taskType)) {
            return;
        }
        int approvalResult = workflowDTO.getApprovalResult();
        if (1 != approvalResult) {
            return;
        }
        String bizCode = workflowDTO.getBizCode();
        if (!StringUtils.hasText(bizCode)) {
            return;
        }
        Locale parentLocale = LocaleContextHolder.getLocale();
        CounterSignerSendMessageTask counterSignerSendMessageTask = new CounterSignerSendMessageTask(workflowDTO, user, this.vaMessageTemplateClient, this.workflowModelHelper, parentLocale);
        ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(counterSignerSendMessageTask);
    }

    public Map<String, Object> getCosignOriginalToDoTask(String bizCode, String userId, String traceId) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setBizCode(bizCode);
        taskDTO.setBackendRequest(true);
        taskDTO.setPagination(false);
        taskDTO.setTraceId(traceId);
        PageVO pageVO = Optional.ofNullable(this.todoClient.listUnfinished(taskDTO)).orElse(new PageVO());
        List rows = Optional.ofNullable(pageVO.getRows()).orElse(Collections.emptyList());
        Map taskInfo = rows.stream().filter(x -> String.valueOf(x.get("PARTICIPANT")).equals(userId)).findAny().orElse(null);
        if (ObjectUtils.isEmpty(taskInfo)) {
            PageVO tempPageVO = Optional.ofNullable(this.todoClient.listHistoryTask(taskDTO)).orElse(new PageVO());
            List tempRows = Optional.ofNullable(tempPageVO.getRows()).orElse(Collections.emptyList());
            taskInfo = tempRows.stream().filter(x -> String.valueOf(x.get("COMPLETEUSER")).equals(userId)).findAny().orElse(null);
        }
        Assert.notEmpty(taskInfo, "\u672a\u80fd\u627e\u5230\u539f\u59cb\u5f85\u529e\u4fe1\u606f");
        return taskInfo;
    }

    public Map<String, Object> getCosignMessageContentParamMap(String taskId, String bizDefineTitle, String bizCode) {
        HashMap<String, Object> map = new HashMap<String, Object>(6);
        map.put("url", "/#/sso/VaNeedToDo?taskCode=" + taskId);
        map.put("appName", "todo-app");
        map.put("appTitle", "\u5f85\u529e-" + bizDefineTitle + bizCode);
        map.put("funcName", "vaApprovalPlugin");
        HashMap<String, String> appConfigMap = new HashMap<String, String>(1);
        appConfigMap.put("taskCode", taskId);
        map.put("appConfig", JSONUtil.toJSONString(appConfigMap));
        return map;
    }

    public Map<String, Object> loadTodoParam(WorkflowDTO workflowDTO) {
        TodoBusinessClient todoBusinessClient;
        String bizType = workflowDTO.getBizType();
        String bizDefine = workflowDTO.getBizDefine();
        String bizCode = workflowDTO.getBizCode();
        if ("BILL".equalsIgnoreCase(bizType)) {
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setModuleName(bizDefine.split("_")[0]);
            moduleDTO.setFunctionType("BILL");
            moduleDTO.setTraceId(Utils.getTraceId());
            R r = this.metaDataClient.getModuleByName(moduleDTO);
            String server = String.valueOf(r.get((Object)"server"));
            String path = String.valueOf(r.get((Object)"path"));
            todoBusinessClient = (TodoBusinessClient)FeignUtil.getDynamicClient(TodoBusinessClient.class, (String)server, (String)(path + "/bill"));
        } else {
            todoBusinessClient = VaWorkflowUtils.getDynamicFeignClient(TodoBusinessClient.class, this.bizTypeConfig, bizType);
        }
        TenantDO tenantDO = new TenantDO();
        tenantDO.addExtInfo(KEY_BIZ_TYPE, (Object)bizType);
        tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
        tenantDO.addExtInfo("bizCode", (Object)bizCode);
        tenantDO.setTraceId(Utils.getTraceId());
        Map todoParam = todoBusinessClient.getBussinessTodoParam(tenantDO);
        if (todoParam == null) {
            return new HashMap<String, Object>(16);
        }
        return todoParam;
    }

    protected void sendCompleteMessage(WorkflowDTO workflowDTO) {
        String tenantName = workflowDTO.getTenantName();
        Locale parentLocale = LocaleContextHolder.getLocale();
        ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(() -> {
            try {
                VaMessageTemplateSendDTO vaMessageSendDTO;
                boolean diffLanguage;
                ThreadContext.unbindSubject();
                ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
                Utils.setTraceId((String)workflowDTO.getTraceId());
                LocaleContextHolder.setLocale(parentLocale);
                String bizCode = workflowDTO.getBizCode();
                String bizDefine = workflowDTO.getBizDefine();
                String bizType = workflowDTO.getBizType();
                Map todoParamMap = workflowDTO.getTodoParamMap();
                workflowDTO.putAll(todoParamMap);
                workflowDTO.getExtInfo().put("BIZCODE", workflowDTO.getBizCode());
                Map taskInfo = workflowDTO.getExtInfo();
                String submitUser = (String)taskInfo.get("SUBMITUSER");
                if (!StringUtils.hasText(submitUser)) {
                    return;
                }
                UserDO submitUserDO = VaWorkFlowDataUtils.getOneUserData(null, submitUser);
                if (submitUserDO == null) {
                    return;
                }
                Locale defaultLocale = LocaleContextHolder.getLocale();
                String language = submitUserDO.getLanguage();
                boolean bl = diffLanguage = StringUtils.hasText(language) && !Objects.equals(language, defaultLocale.toLanguageTag());
                if (diffLanguage) {
                    String[] split = language.split("-");
                    LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                }
                try {
                    String title;
                    VaMessageTemplate template;
                    BussinessClient bussinessClient;
                    OptionItemDO optionItemDO = new OptionItemDO();
                    optionItemDO.setName("SYSADDR");
                    List optionItemList = this.vaSystemOptionClient.list(optionItemDO);
                    String sysAddress = ((OptionItemVO)optionItemList.get(0)).getVal();
                    String url = sysAddress + "/anon/wxwork/login?tenantName=" + tenantName + "&hashMode=true&url=";
                    if ("BILL".equals(bizType)) {
                        ModuleDTO moduleDTO = new ModuleDTO();
                        moduleDTO.setModuleName(bizDefine.split("_")[0]);
                        moduleDTO.setFunctionType("BILL");
                        R r = this.metaDataClient.getModuleByName(moduleDTO);
                        String server = String.valueOf(r.get((Object)"server"));
                        String path = String.valueOf(r.get((Object)"path"));
                        bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
                    } else {
                        bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                    }
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.addExtInfo("bizCode", (Object)bizCode);
                    tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
                    tenantDO.addExtInfo("submitUser", (Object)submitUser);
                    tenantDO.setTraceId(workflowDTO.getTraceId());
                    Map msgTemplateParamMap = bussinessClient.getMessageTemplateParamMap(tenantDO);
                    Object submitTimeObj = taskInfo.get("SUBMITTIME");
                    String submitTime = this.Object2StringTime(submitTimeObj);
                    Object completeTimeObj = taskInfo.get("COMPLETETIME");
                    String completeTime = this.Object2StringTime(completeTimeObj);
                    String bizDefineTitle = (String)msgTemplateParamMap.get("bizDefineTitle");
                    String bizCodeTitle = (String)msgTemplateParamMap.get("bizCodeTitle");
                    HashMap<String, Object> messageParamMap = new HashMap<String, Object>(16);
                    messageParamMap.put("BIZDEFINECODE", bizDefine);
                    messageParamMap.put("BIZDEFINE", bizDefineTitle);
                    messageParamMap.put("BIZCODE", bizCodeTitle);
                    messageParamMap.put("SUBMITTIME", submitTime);
                    messageParamMap.put("COMPLETETIME", completeTime);
                    if ("BUDGET".equalsIgnoreCase(bizType)) {
                        messageParamMap.put("BIZTITLE", bizCodeTitle);
                    }
                    if (2 == workflowDTO.getApprovalResult()) {
                        template = (VaMessageTemplate)ApplicationContextRegister.getBean(VaWorkflowRejectTemplate.class);
                        messageParamMap.put("REJECTREASON", workflowDTO.getApprovalComment());
                        title = VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.rejectmsg", submitTime, bizDefineTitle + bizCodeTitle, workflowDTO.getApprovalComment());
                    } else {
                        title = VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.approvaledmsg", submitTime, bizDefineTitle + bizCodeTitle);
                        template = (VaMessageTemplate)ApplicationContextRegister.getBean(VaWorkflowApprovaledTemplate.class);
                    }
                    vaMessageSendDTO = new VaMessageTemplateSendDTO();
                    vaMessageSendDTO.setTraceId(workflowDTO.getTraceId());
                    vaMessageSendDTO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.approvalresult"));
                    vaMessageSendDTO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.notice"));
                    vaMessageSendDTO.setCreateuser("system");
                    vaMessageSendDTO.setFunctionModule(template.getFunction());
                    vaMessageSendDTO.setTemplateName(template.getName());
                    vaMessageSendDTO.setUnitcode(taskInfo.get("UNITCODE") != null ? taskInfo.get("UNITCODE").toString() : null);
                    List params = template.getTemplateParams();
                    vaMessageSendDTO.setTitle(title);
                    vaMessageSendDTO.setContent(title);
                    vaMessageSendDTO.setReceiveUserIds(Collections.singletonList(submitUser));
                    Object appParam = msgTemplateParamMap.get("appParam");
                    if (appParam != null) {
                        vaMessageSendDTO.setParam(JSONUtil.toJSONString(appParam));
                    }
                    VaWorkFlowDataUtils.getParamMap(taskInfo, params, messageParamMap, tenantName);
                    vaMessageSendDTO.setParamMap(messageParamMap);
                    HashMap<String, String> urlMap = new HashMap<String, String>(16);
                    urlMap.put("wxWorkUrl", url + msgTemplateParamMap.get("mobileUrl"));
                    vaMessageSendDTO.setUrlMap(urlMap);
                }
                finally {
                    if (diffLanguage) {
                        LocaleContextHolder.setLocale(defaultLocale);
                    }
                }
                this.vaMessageTemplateClient.sendMessage(vaMessageSendDTO);
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            finally {
                ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            }
        });
    }

    protected void sendNotifyCreateUserMessage(WorkflowDTO workflowDTO, List<ProcessNodeDO> unApprovalProcessNodes, List<ProcessNodeDO> approvalProcessNodes, List<String> nodeCodes) {
        String tenantName = workflowDTO.getTenantName();
        Locale parentLocale = LocaleContextHolder.getLocale();
        ThreadPoolConst.SEND_MESSAGE_THREADPOOL.execute(() -> {
            try {
                VaMessageTemplateSendDTO vaMessageSendDTO;
                HashMap<String, Object> messageParamMap;
                String bizCodeTitle;
                String bizDefineTitle;
                String submitTime;
                boolean diffLanguage;
                ThreadContext.unbindSubject();
                ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
                Utils.setTraceId((String)workflowDTO.getTraceId());
                LocaleContextHolder.setLocale(parentLocale);
                String bizCode = workflowDTO.getBizCode();
                String bizDefine = workflowDTO.getBizDefine();
                String bizType = workflowDTO.getBizType();
                Map taskInfo = workflowDTO.getExtInfo();
                String submitUser = (String)taskInfo.get("SUBMITUSER");
                UserDO submitUserDO = VaWorkFlowDataUtils.getOneUserData(tenantName, submitUser);
                if (submitUserDO == null) {
                    return;
                }
                String language = submitUserDO.getLanguage();
                Locale defaultLocale = LocaleContextHolder.getLocale();
                boolean bl = diffLanguage = StringUtils.hasText(language) && !Objects.equals(language, defaultLocale.toLanguageTag());
                if (diffLanguage) {
                    String[] split = language.split("-");
                    LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                }
                try {
                    BussinessClient bussinessClient;
                    OptionItemDO optionItemDO = new OptionItemDO();
                    optionItemDO.setName("SYSADDR");
                    List optionItemList = this.vaSystemOptionClient.list(optionItemDO);
                    String sysAddress = ((OptionItemVO)optionItemList.get(0)).getVal();
                    String url = sysAddress + "/anon/wxwork/login?tenantName=" + tenantName + "&hashMode=true&url=";
                    if ("BILL".equals(bizType)) {
                        ModuleDTO moduleDTO = new ModuleDTO();
                        moduleDTO.setModuleName(bizDefine.split("_")[0]);
                        moduleDTO.setFunctionType("BILL");
                        R r = this.metaDataClient.getModuleByName(moduleDTO);
                        String server = String.valueOf(r.get((Object)"server"));
                        String path = String.valueOf(r.get((Object)"path"));
                        bussinessClient = (BussinessClient)FeignUtil.getDynamicClient(BussinessClient.class, (String)server, (String)(path + "/bill"));
                    } else {
                        bussinessClient = VaWorkflowUtils.getDynamicFeignClient(BussinessClient.class, this.bizTypeConfig, bizType);
                    }
                    TenantDO tenantDO = new TenantDO();
                    tenantDO.addExtInfo("bizCode", (Object)bizCode);
                    tenantDO.addExtInfo("bizDefine", (Object)bizDefine);
                    tenantDO.addExtInfo("submitUser", (Object)submitUser);
                    tenantDO.setTraceId(workflowDTO.getTraceId());
                    Map msgTemplateParamMap = bussinessClient.getMessageTemplateParamMap(tenantDO);
                    Object submitTimeObj = taskInfo.get("SUBMITTIME");
                    submitTime = this.Object2StringTime(submitTimeObj);
                    bizDefineTitle = (String)msgTemplateParamMap.get("bizDefineTitle");
                    bizCodeTitle = (String)msgTemplateParamMap.get("bizCodeTitle");
                    messageParamMap = new HashMap<String, Object>(16);
                    messageParamMap.put("BIZDEFINECODE", bizDefine);
                    messageParamMap.put("BIZDEFINE", bizDefineTitle);
                    messageParamMap.put("BIZCODE", bizCodeTitle);
                    messageParamMap.put("SUBMITTIME", submitTime);
                    if ("BUDGET".equalsIgnoreCase(bizType)) {
                        messageParamMap.put("BIZTITLE", bizCodeTitle);
                    }
                    VaMessageTemplate template = (VaMessageTemplate)ApplicationContextRegister.getBean(VaWorkflowNotifyCreateUserTemplate.class);
                    vaMessageSendDTO = new VaMessageTemplateSendDTO();
                    vaMessageSendDTO.setTraceId(workflowDTO.getTraceId());
                    vaMessageSendDTO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.approvalresult"));
                    vaMessageSendDTO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.notice"));
                    vaMessageSendDTO.setCreateuser("system");
                    vaMessageSendDTO.setFunctionModule(template.getFunction());
                    vaMessageSendDTO.setTemplateName(template.getName());
                    vaMessageSendDTO.setUnitcode(taskInfo.get("UNITCODE") != null ? taskInfo.get("UNITCODE").toString() : null);
                    List params = template.getTemplateParams();
                    vaMessageSendDTO.setReceiveUserIds(Collections.singletonList(submitUser));
                    Object appParam = msgTemplateParamMap.get("appParam");
                    if (appParam != null) {
                        vaMessageSendDTO.setParam(JSONUtil.toJSONString(appParam));
                    }
                    VaWorkFlowDataUtils.getParamMap(taskInfo, params, messageParamMap, tenantName);
                    vaMessageSendDTO.setParamMap(messageParamMap);
                    HashMap<String, String> urlMap = new HashMap<String, String>(16);
                    urlMap.put("wxWorkUrl", url + msgTemplateParamMap.get("mobileUrl"));
                    vaMessageSendDTO.setUrlMap(urlMap);
                }
                finally {
                    if (diffLanguage) {
                        LocaleContextHolder.setLocale(defaultLocale);
                    }
                }
                boolean i18nEnable = Boolean.TRUE.equals(VaI18nParamUtil.getTranslationEnabled());
                for (int i = 0; i < nodeCodes.size(); ++i) {
                    if (diffLanguage) {
                        String[] split = language.split("-");
                        LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                    }
                    try {
                        if (nodeCodes.size() > 1 && i <= nodeCodes.size() - 2) {
                            this.delSendNotifyCreateUserMessageParam(workflowDTO, unApprovalProcessNodes, approvalProcessNodes, (String)nodeCodes.get(i), (String)nodeCodes.get(i + 1), i18nEnable);
                        } else {
                            this.delSendNotifyCreateUserMessageParam(workflowDTO, unApprovalProcessNodes, approvalProcessNodes, (String)nodeCodes.get(i), null, i18nEnable);
                        }
                        String nextNodeName = (String)workflowDTO.getExtInfo("NEXTNODENAME");
                        String currNodeName = (String)workflowDTO.getExtInfo("CURRNODENAME");
                        String completeUser = (String)workflowDTO.getExtInfo("COMPLETEUSER");
                        String title = VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.createusernoticemsg", submitTime, bizDefineTitle + bizCodeTitle, currNodeName + completeUser, nextNodeName);
                        messageParamMap.put("NEXTNODENAME", nextNodeName);
                        messageParamMap.put("CURRNODENAME", currNodeName);
                        messageParamMap.put("COMPLETEUSER", completeUser);
                        vaMessageSendDTO.setTitle(title);
                        vaMessageSendDTO.setContent(title);
                    }
                    finally {
                        if (diffLanguage) {
                            LocaleContextHolder.setLocale(defaultLocale);
                        }
                    }
                    this.vaMessageTemplateClient.sendMessage(vaMessageSendDTO);
                }
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            finally {
                ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            }
        });
    }

    private String Object2StringTime(Object timeObj) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringTime = timeObj instanceof Date ? sdf.format(timeObj) : (timeObj instanceof Long ? sdf.format(new Date((Long)timeObj)) : (String)timeObj);
        return stringTime;
    }

    private void delSendNotifyCreateUserMessageParam(WorkflowDTO workflowDTO, List<ProcessNodeDO> unApprovalProcessNodes, List<ProcessNodeDO> approvalProcessNodes, String nodeCode, String nextNodeCode, boolean i18nEnable) {
        HashMap<String, String> nodeNameMap = new HashMap<String, String>(4);
        ArrayList<ProcessNodeDO> nextNode = new ArrayList<ProcessNodeDO>();
        if (StringUtils.hasText(nextNodeCode)) {
            for (ProcessNodeDO approvalProcessNode : approvalProcessNodes) {
                if (!nextNodeCode.equals(approvalProcessNode.getNodecode()) || StringUtils.hasText(workflowDTO.getSubProcessBranch()) && !Objects.equals(workflowDTO.getSubProcessBranch(), approvalProcessNode.getSubprocessbranch())) continue;
                nodeNameMap.put(approvalProcessNode.getProcessnodename(), nextNodeCode);
                nextNode.add(approvalProcessNode);
            }
        } else {
            for (ProcessNodeDO unApprovalProcessNode : unApprovalProcessNodes) {
                String processNodeName = unApprovalProcessNode.getProcessnodename();
                String processNodeCode = unApprovalProcessNode.getNodecode();
                if (nodeNameMap.containsKey(processNodeName) && ((String)nodeNameMap.get(processNodeName)).equals(processNodeCode)) continue;
                nodeNameMap.put(processNodeName, processNodeCode);
                nextNode.add(unApprovalProcessNode);
            }
        }
        String subProcessBranch = workflowDTO.getSubProcessBranch();
        String currPgwBranch = null;
        ProcessNodeDO currNode = null;
        boolean breakFlag = false;
        ArrayList<String> completeUserIds = new ArrayList<String>();
        for (ProcessNodeDO processNodeDO : approvalProcessNodes) {
            if (StringUtils.hasText(subProcessBranch) && !subProcessBranch.equals(processNodeDO.getSubprocessbranch()) || StringUtils.hasText(currPgwBranch) && !currPgwBranch.equals(processNodeDO.getPgwbranch())) continue;
            if (nodeCode.equals(processNodeDO.getNodecode())) {
                currNode = processNodeDO;
                completeUserIds.add(processNodeDO.getCompleteuserid());
                if (StringUtils.hasText(processNodeDO.getPgwbranch())) {
                    currPgwBranch = processNodeDO.getPgwbranch();
                }
                breakFlag = true;
            }
            if (nodeCode.equals(processNodeDO.getNodecode()) || !breakFlag) continue;
            break;
        }
        ArrayList<String> completeUserNames = new ArrayList<String>();
        for (String completeUserId : completeUserIds) {
            UserDO userDO = VaWorkFlowDataUtils.getOneUserData(workflowDTO.tenantName, completeUserId);
            if (userDO == null) continue;
            completeUserNames.add(userDO.getName());
        }
        if (i18nEnable) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            HashMap<String, Object> bizCodeCacheMap = new HashMap<String, Object>();
            VaWorkFlowI18nUtils.convertProcessNodeListI18n(nextNode, hashMap, bizCodeCacheMap);
            ArrayList<ProcessNodeDO> currNodes = new ArrayList<ProcessNodeDO>();
            currNodes.add(currNode);
            VaWorkFlowI18nUtils.convertProcessNodeListI18n(currNodes, hashMap, bizCodeCacheMap);
        }
        List list = nextNode.stream().map(ProcessNodeDO::getProcessnodename).collect(Collectors.toList());
        workflowDTO.addExtInfo("NEXTNODENAME", (Object)String.join((CharSequence)"\u3001", list));
        workflowDTO.addExtInfo("CURRNODENAME", (Object)(currNode == null ? "" : currNode.getProcessnodename()));
        workflowDTO.addExtInfo("COMPLETEUSER", (Object)String.join((CharSequence)"\u3001", completeUserNames));
    }

    protected boolean isForbidEmail(String taskDefineKey) {
        return false;
    }

    public void setAuthUserClient(AuthUserClient authUserClient) {
        this.authUserClient = authUserClient;
    }

    public void setVaSystemOptionClient(VaSystemOptionClient vaSystemOptionClient) {
        this.vaSystemOptionClient = vaSystemOptionClient;
    }

    public void setEnumDataClient(EnumDataClient enumDataClient) {
        this.enumDataClient = enumDataClient;
    }

    public void setMetaDataClient(MetaDataClient metaDataClient) {
        this.metaDataClient = metaDataClient;
    }

    public void setOrgDataClient(OrgDataClient orgDataClient) {
        this.orgDataClient = orgDataClient;
    }

    public void setBaseDataClient(BaseDataClient baseDataClient) {
        this.baseDataClient = baseDataClient;
    }

    public void setWorkflowSevice(WorkflowSevice workflowSevice) {
        this.workflowSevice = workflowSevice;
    }

    public void setBizTypeConfig(BizTypeConfig bizTypeConfig) {
        this.bizTypeConfig = bizTypeConfig;
    }

    public void setVaMessageTemplateClient(VaMessageTemplateClient vaMessageTemplateClient) {
        this.vaMessageTemplateClient = vaMessageTemplateClient;
    }

    public void setVaTransMessageService(VaTransMessageService vaTransMessageService) {
        this.vaTransMessageService = vaTransMessageService;
    }

    public WorkflowModelHelper getWorkflowModelHelper() {
        return this.workflowModelHelper;
    }

    public void setWorkflowModelHelper(WorkflowModelHelper workflowModelHelper) {
        this.workflowModelHelper = workflowModelHelper;
    }

    public void setTodoClient(TodoClient todoClient) {
        this.todoClient = todoClient;
    }

    protected final class CounterSignerSendMessageTask
    implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(CounterSignerSendMessageTask.class);
        private final WorkflowDTO workflowDTO;
        private final UserLoginDTO user;
        private final VaMessageTemplateClient messageTemplateClient;
        private final WorkflowModelHelper workflowModelHelper;
        private final Locale parentLocale;

        CounterSignerSendMessageTask(WorkflowDTO workflowDto, UserLoginDTO userLoginDTO, VaMessageTemplateClient aMessageTemplateClient, WorkflowModelHelper aWorkflowModelHelper, Locale parentLocale) {
            this.workflowDTO = workflowDto;
            this.user = userLoginDTO;
            this.messageTemplateClient = aMessageTemplateClient;
            this.workflowModelHelper = aWorkflowModelHelper;
            this.parentLocale = parentLocale;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                VaMessageTemplateSendDTO vaMessageSendDTO;
                boolean diffLanguage;
                String tenantName = this.user.getTenantName();
                ThreadContext.unbindSubject();
                ThreadContext.put((Object)"SECURITY_TENANT_KEY", (Object)tenantName);
                Utils.setTraceId((String)this.workflowDTO.getTraceId());
                LocaleContextHolder.setLocale(this.parentLocale);
                String nodeCode = this.workflowDTO.getNodeCode();
                String processInstanceId = this.workflowDTO.getProcessInstanceId();
                String name = Optional.ofNullable(this.user.getName()).orElse(this.user.getUsername());
                boolean consult = this.workflowDTO.isConsult();
                String delegateId = (String)this.workflowDTO.getExtInfo("delegateId");
                String id = consult && StringUtils.hasText(delegateId) ? delegateId : this.user.getId();
                PlusApprovalInfoDO plusApprovalInfoDTO = this.workflowModelHelper.getPlusApprovalInfoDo(nodeCode, processInstanceId, id);
                Assert.notNull((Object)plusApprovalInfoDTO, "\u672a\u67e5\u5230\u52a0\u7b7e\u4fe1\u606f\uff1a" + nodeCode + " " + processInstanceId + " " + id);
                String userId = plusApprovalInfoDTO.getUsername();
                UserDO userDO = VaWorkFlowDataUtils.getOneUserData(tenantName, userId);
                if (userDO == null) {
                    this.logger.warn("\u6d41\u7a0b:{}\u52a0\u7b7e\u4eba\u5ba1\u6279\u901a\u8fc7\u7ed9\u539f\u5ba1\u6279\u4eba:{}\u53d1\u9001\u6d88\u606f\uff0c\u83b7\u53d6\u539f\u5ba1\u6279\u4eba\u4e3a\u7a7a", (Object)processInstanceId, (Object)userId);
                    return;
                }
                String bizCode = this.workflowDTO.getBizCode();
                String traceId = this.workflowDTO.getTraceId();
                Map<String, Object> taskInfo = BaseWorkflowModelImpl.this.getCosignOriginalToDoTask(bizCode, userId, traceId);
                if (this.workflowDTO.isConsult()) {
                    TaskDTO taskDTO = new TaskDTO();
                    taskDTO.setTaskId((String)taskInfo.get("TASKID"));
                    taskDTO.setParticipant(userId);
                    taskDTO.setBackendRequest(true);
                    taskDTO.setPagination(false);
                    taskDTO.setTraceId(traceId);
                    List tasks = BaseWorkflowModelImpl.this.todoClient.listUnfinished(taskDTO).getRows();
                    Map originalTaskMap = !CollectionUtils.isEmpty(tasks) ? (Map)tasks.get(0) : null;
                    Assert.notEmpty(originalTaskMap, "\u6ca1\u6709\u67e5\u5230\u5ba1\u6279\u4eba\uff1a" + userId + " \u7684\u5f85\u529e");
                } else {
                    Assert.hasText(userId, "\u52a0\u7b7e\u540c\u610f\u53d1\u9001\u53cd\u9988\u6d88\u606f\u5ba1\u6279\u4eba\u4fe1\u606f\u6ca1\u6709\u627e\u5230");
                }
                Locale defaultLocale = LocaleContextHolder.getLocale();
                String language = userDO.getLanguage();
                boolean bl = diffLanguage = StringUtils.hasText(language) && !Objects.equals(language, defaultLocale.toLanguageTag());
                if (diffLanguage) {
                    String[] split = language.split("-");
                    LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                }
                try {
                    String bizDefine = this.workflowDTO.getBizDefine();
                    String bizType = this.workflowDTO.getBizType();
                    String bizDefineTitle = this.workflowModelHelper.getBizDefineTitle(bizType, bizCode, bizDefine);
                    boolean isForbidEmail = this.workflowDTO.isDisableSendMailFlag();
                    if (!isForbidEmail) {
                        isForbidEmail = BaseWorkflowModelImpl.this.isForbidEmail(nodeCode);
                    }
                    String content = this.workflowDTO.isConsult() ? (StringUtils.hasText(delegateId) ? VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.delegateapprovalnoticemsg", bizDefineTitle + " " + bizCode, name) : VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.plusapprovalnoticemsg", bizDefineTitle + " " + bizCode, name)) : VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.pluscompletenoticemsg", bizDefineTitle + " " + bizCode, name);
                    ArrayList<String> userIdList = new ArrayList<String>();
                    userIdList.add(userId);
                    VaWorkflowWaitingApprovalTemplate waitingApprovalTemplate = (VaWorkflowWaitingApprovalTemplate)((Object)ApplicationContextRegister.getBean(VaWorkflowWaitingApprovalTemplate.class));
                    List<VaMessageTemplateParamDO> messageTemplateParamList = waitingApprovalTemplate.getTemplateParams();
                    Map<String, Object> todoParam = BaseWorkflowModelImpl.this.loadTodoParam(this.workflowDTO);
                    Objects.requireNonNull(taskInfo, "\u672a\u80fd\u627e\u5230\u539f\u59cb\u5f85\u529e\u4fe1\u606f").putAll(todoParam);
                    String taskId = (String)taskInfo.get("TASKID");
                    String unitCode = (String)taskInfo.get("UNITCODE");
                    HashMap<String, Object> messageParamMap = new HashMap<String, Object>(16);
                    messageParamMap.put("BIZDEFINECODE", bizDefine);
                    messageParamMap.put("BIZDEFINE", bizDefineTitle);
                    messageParamMap.put("BIZCODE", bizCode);
                    VaWorkFlowDataUtils.getParamMap(taskInfo, messageTemplateParamList, messageParamMap, tenantName);
                    Map<String, Object> map = BaseWorkflowModelImpl.this.getCosignMessageContentParamMap(taskId, bizDefineTitle, bizCode);
                    vaMessageSendDTO = new VaMessageTemplateSendDTO();
                    vaMessageSendDTO.setTraceId(traceId);
                    vaMessageSendDTO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todonotice"));
                    vaMessageSendDTO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.todo"));
                    vaMessageSendDTO.setCreateuser("system");
                    vaMessageSendDTO.setTitle(content);
                    vaMessageSendDTO.setContent(content);
                    vaMessageSendDTO.setParam(JSONUtil.toJSONString(map));
                    vaMessageSendDTO.setReceiveUserIds(userIdList);
                    vaMessageSendDTO.setParamMap(messageParamMap);
                    vaMessageSendDTO.setForbidEmail(isForbidEmail);
                    vaMessageSendDTO.setUnitcode(unitCode);
                }
                finally {
                    if (diffLanguage) {
                        LocaleContextHolder.setLocale(defaultLocale);
                    }
                }
                this.messageTemplateClient.sendMessage(vaMessageSendDTO);
            }
            catch (Exception e) {
                this.logger.error("[\u5de5\u4f5c\u6d41]>>>\u52a0\u7b7e\u4eba\u5ba1\u6279\u540c\u610f\u53d1\u9001\u53cd\u9988\u6d88\u606f\u5931\u8d25", e);
            }
            finally {
                ThreadContext.remove((Object)"SECURITY_TENANT_KEY");
            }
        }
    }

    private final class SendCarbonCopyMessageTask
    implements Runnable {
        private final Logger logger = LoggerFactory.getLogger(SendCarbonCopyMessageTask.class);
        private final WorkflowDTO workflowDTO;
        private final UserLoginDTO user;
        private final Map<String, Object> currentTodo;
        private final Locale parentLocale;

        SendCarbonCopyMessageTask(WorkflowDTO workflowDto, UserLoginDTO userLoginDTO, Map<String, Object> currentTask, Locale parentLocale) {
            this.workflowDTO = workflowDto;
            this.user = userLoginDTO;
            this.currentTodo = currentTask;
            this.parentLocale = parentLocale;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                List userIds = this.workflowDTO.getCarbonCopyUserIds();
                if (userIds.isEmpty()) {
                    return;
                }
                ShiroUtil.unbindUser();
                ShiroUtil.bindUser((UserLoginDTO)this.user);
                Utils.setTraceId((String)this.workflowDTO.getTraceId());
                LocaleContextHolder.setLocale(this.parentLocale);
                this.currentTodo.put("APPROVEUSERID", this.user.getId());
                boolean isForbidEmail = this.workflowDTO.isDisableSendMailFlag();
                String bizCode = this.workflowDTO.getBizCode();
                String bizDefine = this.workflowDTO.getBizDefine();
                String remark = this.workflowDTO.getCarbonCopyRemark();
                Locale defaultLocale = LocaleContextHolder.getLocale();
                Map languageUsersMap = BaseWorkflowModelImpl.this.packageLanguageUsersMap(userIds);
                for (Map.Entry entry : languageUsersMap.entrySet()) {
                    String messageTitle;
                    ParamHandleResult paramHandleResult;
                    boolean diffLanguage;
                    String language = (String)entry.getKey();
                    List users = (List)entry.getValue();
                    VaMessageTemplateSendDTO vaMessageSendDTO = new VaMessageTemplateSendDTO();
                    vaMessageSendDTO.setTraceId(Utils.getTraceId());
                    vaMessageSendDTO.setCreateuser("system");
                    vaMessageSendDTO.setUnitcode((String)this.currentTodo.get("UNITCODE"));
                    if (!isForbidEmail) {
                        String taskDefineKey = (String)this.currentTodo.get("TASKDEFINEKEY");
                        isForbidEmail = BaseWorkflowModelImpl.this.isForbidEmail(taskDefineKey);
                    }
                    vaMessageSendDTO.setForbidEmail(isForbidEmail);
                    vaMessageSendDTO.setForbidSMS(this.workflowDTO.isDisableSendSMSFlag());
                    String bizType = Optional.ofNullable(this.workflowDTO.getBizType()).orElse("bill");
                    HashMap<String, Object> param = new HashMap<String, Object>();
                    param.put("todoParam", this.currentTodo);
                    param.put(BaseWorkflowModelImpl.KEY_BIZ_TYPE, bizDefine);
                    param.put("bizCode", bizCode);
                    param.put("approveUserId", this.user.getId());
                    boolean bl = diffLanguage = !Objects.equals(language, defaultLocale.toLanguageTag());
                    if (diffLanguage) {
                        String[] split = language.split("-");
                        LocaleContextHolder.setLocale(new Locale(split[0], split[1]));
                    }
                    try {
                        vaMessageSendDTO.setMsgtype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.issuesnotice"));
                        vaMessageSendDTO.setGrouptype(VaWorkFlowI18nUtils.getInfo("va.workflow.msgnotice.notice"));
                        paramHandleResult = BaseWorkflowModelImpl.this.getParamHandleResult(param, users, bizDefine, bizType);
                        messageTitle = paramHandleResult.getTitle();
                        if (StringUtils.hasText(remark)) {
                            messageTitle = messageTitle + remark;
                        }
                        vaMessageSendDTO.setTitle(messageTitle);
                        HashMap<String, Object> messageParamMap = new HashMap<String, Object>(16);
                        messageParamMap.put("CARBONCOPY_REMARK", remark);
                        messageParamMap.put("BIZCODE", bizCode);
                        messageParamMap.put("BIZDEFINECODE", bizDefine);
                        messageParamMap.put("BIZDEFINE", paramHandleResult.getBizDefineTitle());
                        messageParamMap.put("BIZTITLE", paramHandleResult.getBizCodeTitle());
                        VaWorkflowCarbonCopyTemplate template = (VaWorkflowCarbonCopyTemplate)((Object)ApplicationContextRegister.getBean(VaWorkflowCarbonCopyTemplate.class));
                        VaWorkFlowDataUtils.getParamMap(this.currentTodo, template.getTemplateParams(), messageParamMap, ShiroUtil.getTenantName());
                        vaMessageSendDTO.setParamMap(messageParamMap);
                        vaMessageSendDTO.setTemplateName("CARBONCOPYMESSAGE");
                        vaMessageSendDTO.setFunctionModule("VAWORKFLOW");
                    }
                    finally {
                        if (diffLanguage) {
                            LocaleContextHolder.setLocale(defaultLocale);
                        }
                    }
                    List messageDetailInfoList = paramHandleResult.getMessageDetailInfoList();
                    for (MessageDetailInfo messageDetailInfo : messageDetailInfoList) {
                        vaMessageSendDTO.setContent(messageTitle);
                        vaMessageSendDTO.setReceiveUserIds(messageDetailInfo.getReceiveUserIds());
                        vaMessageSendDTO.setParam(messageDetailInfo.getParam());
                        vaMessageSendDTO.setUrlMap(messageDetailInfo.getUrlMap());
                        BaseWorkflowModelImpl.this.vaMessageTemplateClient.sendMessage(vaMessageSendDTO);
                    }
                }
            }
            catch (Exception e) {
                this.logger.error("[\u5de5\u4f5c\u6d41]>>>\u6284\u9001\u53d1\u9001\u6d88\u606f\u51fa\u9519\uff1a{}", (Object)e.getMessage(), (Object)e);
            }
            finally {
                ShiroUtil.unbindUser();
            }
        }
    }
}

