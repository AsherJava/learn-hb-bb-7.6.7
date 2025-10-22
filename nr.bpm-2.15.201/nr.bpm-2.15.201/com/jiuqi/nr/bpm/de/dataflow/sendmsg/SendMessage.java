/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nvwa.framework.nros.bean.vo.HeaderBtnConfig
 *  com.jiuqi.nvwa.framework.nros.bean.vo.PortalConfig
 *  com.jiuqi.nvwa.framework.nros.service.IPortalService
 *  com.jiuqi.util.StringUtils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageCommonParam;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendBaseParam;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.TodoSendFilter;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.nvwa.framework.nros.bean.vo.HeaderBtnConfig;
import com.jiuqi.nvwa.framework.nros.bean.vo.PortalConfig;
import com.jiuqi.nvwa.framework.nros.service.IPortalService;
import com.jiuqi.util.StringUtils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SendMessage
extends SendBaseParam
implements ISendMessage {
    private static final Logger logger = LogFactory.getLogger(SendMessage.class);
    public static final String ENTITY = "entity";
    public static final String FROM = "from";
    public static final String GROUP = "group";
    public static final String MSG_TYPE_STR = "\u901a\u77e5";
    public static final String TODO_TITLE = "\u5f85\u529e\u901a\u77e5";
    @Autowired
    private List<ActorStrategy> actorStrategy;
    @Resource
    private UserService<User> userService;
    @Autowired
    private DeEntityHelper entityHelper;
    @Resource
    private WorkflowSettingService workflowSettingService;
    @Resource
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private MessageCommonParam messageCommonParam;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IPortalService portalService;
    @Autowired
    private VaMessageClient vaMessageClient;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired(required=false)
    private List<TodoSendFilter> todoSendFilters;

    @Override
    public void evaluateTodo(Task task, UserTask userTask, BusinessKey businessKey, String operator) {
        if (this.todoSendFilters != null && !this.todoSendFilters.isEmpty()) {
            for (TodoSendFilter filter : this.todoSendFilters) {
                if (filter.isEnableSendTodo(businessKey)) continue;
                return;
            }
        }
        if (SendMessageTaskConfig.canSendMessage()) {
            Set<String> actors = this.getActors(userTask, businessKey, task);
            String formSchemeKey = businessKey.getFormSchemeKey();
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            HashSet<String> lineUsers = new HashSet<String>();
            if (!defaultWorkflow) {
                boolean sendbyProtal;
                WorkFlowDefine workFlowDefineByID;
                WorkFlowLine currentWorkFlowLine = new WorkFlowLine();
                WorkflowSettingDefine workflowSettingDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                if (workflowSettingDefine != null && workflowSettingDefine.getId() != null && (workFlowDefineByID = this.customWorkFolwService.getWorkFlowDefineByID(workflowSettingDefine.getWorkflowId(), 1)) != null) {
                    List<WorkFlowLine> workflowLineByEndNode = this.customWorkFolwService.getWorkflowLineByEndNode(task.getUserTaskId(), workFlowDefineByID.getLinkid());
                    for (WorkFlowLine workFlowLine : workflowLineByEndNode) {
                        if (!workFlowLine.getBeforeNodeID().startsWith("Start")) continue;
                        currentWorkFlowLine = workFlowLine;
                        List<String> users = this.customLineUser(workFlowLine, task, actors, businessKey);
                        lineUsers.addAll(users);
                    }
                }
                if (lineUsers.size() > 0) {
                    actors = lineUsers;
                }
                if (sendbyProtal = currentWorkFlowLine.isSendby_protal()) {
                    this.send(task, actors, businessKey.toString(), operator, currentWorkFlowLine);
                }
            } else {
                this.send(task, actors, businessKey.toString(), operator);
            }
        }
    }

    @Override
    public void send(Task delegateTask, Set<String> candidateUserIds, String businessKeyStr, String operator) {
        ArrayList<String> participants = new ArrayList<String>();
        participants.addAll(candidateUserIds);
        WorkFlowLine workFlowLine = new WorkFlowLine();
        this.defaultMessageBody(delegateTask, businessKeyStr, participants, operator, workFlowLine);
    }

    @Override
    public void send(Task delegateTask, Set<String> candidateUserIds, String businessKeyStr, String operator, WorkFlowLine workFlowLine) {
        ArrayList<String> participants = new ArrayList<String>();
        participants.addAll(candidateUserIds);
        this.defaultMessageBody(delegateTask, businessKeyStr, participants, operator, workFlowLine);
    }

    @Override
    public void send(Task task, BusinessKey businessKey, String actionCode, String content, boolean sendEmail, String operator) {
        HashSet<String> formOrGroupKeys = new HashSet<String>();
        formOrGroupKeys.add(businessKey.getFormKey());
        this.send(task, businessKey, actionCode, content, sendEmail, 0, formOrGroupKeys, operator);
    }

    @Override
    public void send(Task task, BusinessKey businessKey, String actionCode, String content, boolean sendEmail, int canUploadUnitSize, Set<String> formOrGroupKeys, String operator) {
        if (this.todoSendFilters != null && !this.todoSendFilters.isEmpty()) {
            for (TodoSendFilter filter : this.todoSendFilters) {
                if (filter.isEnableSendTodo(businessKey)) continue;
                return;
            }
        }
        String formSchemeKey = businessKey.getFormSchemeKey();
        Task preTask = task;
        Optional<ProcessEngine> processEngine = this.processEngine(formSchemeKey);
        Set<String> superiorActor = this.getSuperiorActor(processEngine, task, businessKey);
        Task afterTask = this.getTasks(processEngine, businessKey);
        if (afterTask != null) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), businessKey);
            Set<String> currenActor = this.getCurrenActor(processEngine, afterTask, businessKey);
            if (formOrGroupKeys != null && !formOrGroupKeys.isEmpty()) {
                Environment environment = (Environment)SpringBeanUtils.getBean(Environment.class);
                String todoVersion = environment.getProperty("jiuqi.nr.todo.version", "2.0");
                if (todoVersion.equals("1.0")) {
                    boolean defaultWorkflow;
                    Map<String, String> commonInfo = this.messageCommonParam.getChineseCommonInfo(businessKey, formOrGroupKeys, operator, afterTask);
                    commonInfo.put("nodeName", afterTask.getName());
                    commonInfo.put("currentActionId", actionCode);
                    commonInfo.put("todoType", afterTask.getUserTaskId());
                    commonInfo.put("content", content);
                    commonInfo.put("reason", content);
                    MasterEntity masterEntity = businessKey.getMasterEntity();
                    String adjust = masterEntity.getMasterEntityKey("ADJUST");
                    if (adjust != null) {
                        commonInfo.put("adjust", adjust);
                    }
                    if (corporateValue != null) {
                        commonInfo.put("corporateValue", corporateValue);
                    }
                    if (defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey)) {
                        this.defaultFlow(afterTask, preTask, formSchemeKey, actionCode, commonInfo, superiorActor, currenActor, businessKey, content, sendEmail, formOrGroupKeys, operator);
                    } else {
                        boolean mulitiInstanceTask = this.nrParameterUtils.isMulitiInstanceTask(preTask.getUserTaskId(), formSchemeKey);
                        commonInfo.put("signNode", String.valueOf(mulitiInstanceTask));
                        this.customSendMsg(preTask, afterTask, formSchemeKey, actionCode, currenActor, commonInfo, businessKey, content, sendEmail, canUploadUnitSize, formOrGroupKeys);
                    }
                } else {
                    for (String key : formOrGroupKeys) {
                        boolean defaultWorkflow;
                        Map<String, String> commonInfo = this.messageCommonParam.getChineseCommonInfo(businessKey, key, operator, afterTask);
                        commonInfo.put("nodeName", afterTask.getName());
                        commonInfo.put("currentActionId", actionCode);
                        commonInfo.put("todoType", afterTask.getUserTaskId());
                        commonInfo.put("content", content);
                        commonInfo.put("reason", content);
                        MasterEntity masterEntity = businessKey.getMasterEntity();
                        String adjust = masterEntity.getMasterEntityKey("ADJUST");
                        if (adjust != null) {
                            commonInfo.put("adjust", adjust);
                        }
                        if (corporateValue != null) {
                            commonInfo.put("corporateValue", corporateValue);
                        }
                        if (defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey)) {
                            this.defaultFlow(afterTask, preTask, formSchemeKey, actionCode, commonInfo, superiorActor, currenActor, businessKey, content, sendEmail, formOrGroupKeys, operator);
                            continue;
                        }
                        boolean mulitiInstanceTask = this.nrParameterUtils.isMulitiInstanceTask(preTask.getUserTaskId(), formSchemeKey);
                        commonInfo.put("signNode", String.valueOf(mulitiInstanceTask));
                        this.customSendMsg(preTask, afterTask, formSchemeKey, actionCode, currenActor, commonInfo, businessKey, content, sendEmail, canUploadUnitSize, formOrGroupKeys);
                    }
                }
            } else {
                boolean defaultWorkflow;
                Map<String, String> commonInfo = this.messageCommonParam.getChineseCommonInfo(businessKey, "", operator, afterTask);
                commonInfo.put("nodeName", afterTask.getName());
                commonInfo.put("currentActionId", actionCode);
                commonInfo.put("todoType", afterTask.getUserTaskId());
                commonInfo.put("content", content);
                commonInfo.put("reason", content);
                MasterEntity masterEntity = businessKey.getMasterEntity();
                String adjust = masterEntity.getMasterEntityKey("ADJUST");
                if (adjust != null) {
                    commonInfo.put("adjust", adjust);
                }
                if (corporateValue != null) {
                    commonInfo.put("corporateValue", corporateValue);
                }
                if (defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey)) {
                    this.defaultFlow(afterTask, preTask, formSchemeKey, actionCode, commonInfo, superiorActor, currenActor, businessKey, content, sendEmail, formOrGroupKeys, operator);
                } else {
                    boolean mulitiInstanceTask = this.nrParameterUtils.isMulitiInstanceTask(preTask.getUserTaskId(), formSchemeKey);
                    commonInfo.put("signNode", String.valueOf(mulitiInstanceTask));
                    this.customSendMsg(preTask, afterTask, formSchemeKey, actionCode, currenActor, commonInfo, businessKey, content, sendEmail, canUploadUnitSize, formOrGroupKeys);
                }
            }
        }
    }

    @Override
    public void sendRetrieveMessage(Task task, Optional<UserTask> userTask, BusinessKey businessKey, DimensionValueSet dim, String formSchemeKey, Task currentTask, String operator) {
        ArrayList<String> auditList = new ArrayList<String>();
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                Set<String> actors = this.getActors(userTask.get(), businessKey, task);
                auditList.addAll(actors);
                Map<String, String> commonInfo = this.commonInfo(businessKey.toString(), businessKey.getFormKey(), operator);
                String defaultContent = this.defaultModel(formSchemeKey, commonInfo, commonInfo.get("content"), false);
                commonInfo.put("action", "\u53d6\u56de");
                this.sendMessage(task, currentTask.getId(), defaultContent, auditList, commonInfo);
                List<String> actionList = this.getAction(formSchemeKey, businessKey.getMasterEntity(), businessKey.getPeriod(), currentTask);
                if (actionList.size() > 0) {
                    Actor actor = Actor.fromNpContext();
                    ArrayList<String> targetAuditList = new ArrayList<String>();
                    targetAuditList.add(actor.getUserId().toString());
                    for (String actionCode : actionList) {
                        if (!"act_upload".equals(actionCode) && !"cus_upload".equals(actionCode) && !"act_submit".equals(actionCode) && !"cus_submit".equals(actionCode) && !"act_confirm".equals(actionCode) && !"cus_confirm".equals(actionCode)) continue;
                        String actionName = this.messageCommonParam.actionName(formSchemeKey, actionCode, currentTask.getUserTaskId());
                        commonInfo.put("action", actionName);
                        commonInfo.put("actionId", actionCode);
                        commonInfo.put("currentActionId", actionCode);
                        commonInfo.put("todoType", currentTask.getUserTaskId());
                        this.sendDaiBan(currentTask.getId(), currentTask.getUserTaskId(), defaultContent, targetAuditList, commonInfo, businessKey.getFormKey());
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void sendApplyReturnTodo(Task task, Optional<UserTask> userTask, BusinessKey businessKey, String formSchemeKey, Map<String, String> param, String operator) {
        try {
            FormSchemeDefine formScheme;
            String corporateValue;
            MasterEntity masterEntity = businessKey.getMasterEntity();
            String adjust = masterEntity.getMasterEntityKey("ADJUST");
            if (adjust != null) {
                param.put("adjust", adjust);
            }
            if ((corporateValue = this.workFlowDimensionBuilder.getCorporateValue((formScheme = this.commonUtil.getFormScheme(formSchemeKey)).getTaskKey(), businessKey)) != null) {
                param.put("corporateValue", corporateValue);
            }
            if (formScheme != null) {
                Set<String> actors = this.getActors(userTask.get(), businessKey, task);
                param.putAll(this.commonInfo(businessKey.toString(), businessKey.getFormKey(), operator));
                param.put("currentActionId", "act_apply_return");
                param.put("todoType", TodoNodeType.REQUEST_REJECT.title);
                String defaultContent = this.defaultModel(formSchemeKey, param, param.get("content"), false);
                this.sendDaiBan(TodoNodeType.REQUEST_REJECT.title, task.getUserTaskId(), defaultContent, new ArrayList<String>(actors), param);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void sendApplyReturnTodoDisAgreed(Task task, Optional<UserTask> userTask, BusinessKey businessKey, String formSchemeKey, Map<String, String> param, String operator) {
        try {
            String corporateValue;
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            MasterEntity masterEntity = businessKey.getMasterEntity();
            String adjust = masterEntity.getMasterEntityKey("ADJUST");
            if (adjust != null) {
                param.put("adjust", adjust);
            }
            if ((corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), businessKey)) != null) {
                param.put("corporateValue", corporateValue);
            }
            if (formScheme != null) {
                Set<String> actors = this.getActors(userTask.get(), businessKey, task);
                param.putAll(this.commonInfo(businessKey.toString(), businessKey.getFormKey(), operator));
                param.put("currentActionId", "act_apply_return");
                param.put("todoType", task.getUserTaskId());
                String defaultContent = this.defaultModel(formSchemeKey, param, param.get("content"), false);
                this.sendDaiBan(task.getId(), task.getUserTaskId(), defaultContent, new ArrayList<String>(actors), param);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void sendApplyReturnMessage(Task task, Optional<UserTask> userTask, BusinessKey businessKey, String formSchemeKey, Map<String, String> param, String operator) {
        if (this.todoVersion.equals("1.0")) {
            try {
                FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
                if (formScheme != null) {
                    Set<String> actors = this.getActors(userTask.get(), businessKey, task);
                    HashSet<String> formOrGroupKeys = new HashSet<String>();
                    formOrGroupKeys.add(businessKey.getFormKey());
                    param.putAll(this.commonInfoVersion1_0(businessKey.toString(), formOrGroupKeys, operator));
                    param.put("currentActionId", "act_apply_return");
                    String defaultContent = this.defaultModel(formSchemeKey, param, param.get("content"), false);
                    this.sendDaiBan(task.getId(), task.getUserTaskId(), defaultContent, new ArrayList<String>(actors), param);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        } else if (this.todoVersion.equals("2.0")) {
            try {
                String corporateValue;
                FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
                MasterEntity masterEntity = businessKey.getMasterEntity();
                String adjust = masterEntity.getMasterEntityKey("ADJUST");
                if (adjust != null) {
                    param.put("adjust", adjust);
                }
                if ((corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), businessKey)) != null) {
                    param.put("corporateValue", corporateValue);
                }
                if (formScheme != null) {
                    Set<String> actors = this.getActors(userTask.get(), businessKey, task);
                    param.putAll(this.commonInfo(businessKey.toString(), businessKey.getFormKey(), operator));
                    param.put("currentActionId", "act_apply_return");
                    param.put("todoType", TodoNodeType.REQUEST_REJECT.title);
                    String defaultContent = this.defaultModel(formSchemeKey, param, param.get("content"), false);
                    this.sendMessage(task, task.getId(), defaultContent, new ArrayList<String>(actors), param);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
        }
    }

    @Override
    public void sendDaiBan(Set<String> formOrGroupKeys, String taskId, String taskCode, String defaultContent, Map<String, String> commonInfo, List<String> currenActor) {
        if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
            for (String formOrGroupKey : formOrGroupKeys) {
                this.sendDaiBan(taskId, taskCode, defaultContent, currenActor, commonInfo, formOrGroupKey);
            }
        } else {
            this.sendDaiBan(taskId, taskCode, defaultContent, currenActor, commonInfo, null);
        }
    }

    private void defaultFlow(Task afterTask, Task preTask, String formSchemeKey, String actionCode, Map<String, String> commonInfo, Set<String> superiorActor, Set<String> currenActor, BusinessKey businessKey, String content, boolean sendEmail, Set<String> formOrGroupKeys, String operator) {
        String messageTemplate;
        FormSchemeDefine formScheme;
        String actionName;
        String defaultContent;
        if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode) || "act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            List<String> actionList = this.getAction(formSchemeKey, businessKey.getMasterEntity(), businessKey.getPeriod(), afterTask);
            if (actionList.size() > 0) {
                String actionName2;
                if (actionList.contains("act_upload")) {
                    actionName2 = this.actionName(formSchemeKey, "act_upload", afterTask.getUserTaskId());
                    commonInfo.put("action", actionName2);
                    commonInfo.put("actionId", "act_upload");
                } else if (actionList.contains("act_submit")) {
                    actionName2 = this.actionName(formSchemeKey, "act_submit", afterTask.getUserTaskId());
                    commonInfo.put("action", actionName2);
                    commonInfo.put("actionId", "act_submit");
                } else if (actionList.contains("act_confirm") && actionList.contains("act_reject")) {
                    actionName2 = this.actionName(formSchemeKey, "act_confirm", afterTask.getUserTaskId());
                    commonInfo.put("action", actionName2);
                    commonInfo.put("actionId", "act_confirm");
                } else if (actionList.contains("act_reject")) {
                    actionName2 = this.actionName(formSchemeKey, "act_reject", afterTask.getUserTaskId());
                    commonInfo.put("action", actionName2);
                    commonInfo.put("actionId", "act_reject");
                }
            }
            defaultContent = this.getContent(commonInfo, businessKey, afterTask, operator, content, false);
            this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent, commonInfo, new ArrayList<String>(currenActor));
        }
        if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            actionName = this.actionName(formSchemeKey, actionCode, afterTask.getUserTaskId());
            commonInfo.put("action", actionName);
            commonInfo.put("actionId", actionCode);
            defaultContent = this.getContent(commonInfo, businessKey, afterTask, operator, content, false);
            this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent, commonInfo, new ArrayList<String>(currenActor));
        }
        if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
            actionName = this.actionName(formSchemeKey, actionCode, afterTask.getUserTaskId());
            commonInfo.put("action", actionName);
            commonInfo.put("actionId", actionCode);
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            messageTemplate = formScheme.getFlowsSetting().getMessageTemplate();
            defaultContent = StringUtils.isNotEmpty((String)messageTemplate) ? this.getCustomMessage(preTask, messageTemplate, commonInfo, businessKey, content, 0, formOrGroupKeys, sendEmail) : this.getContent(commonInfo, businessKey, afterTask, operator, content, true);
            if (sendEmail) {
                this.sendEmail(afterTask.getId(), defaultContent, new ArrayList<String>(superiorActor), commonInfo);
            }
        }
        if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
            actionName = this.actionName(formSchemeKey, actionCode, afterTask.getUserTaskId());
            commonInfo.put("action", actionName);
            commonInfo.put("actionId", actionCode);
            if (sendEmail) {
                formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                messageTemplate = formScheme.getFlowsSetting().getMessageTemplate();
                String defContent = StringUtils.isNotEmpty((String)messageTemplate) ? this.getCustomMessage(preTask, messageTemplate, commonInfo, businessKey, content, 0, formOrGroupKeys, sendEmail) : this.getContent(commonInfo, businessKey, afterTask, operator, content, true);
                this.sendEmail(afterTask.getId(), defContent, new ArrayList<String>(currenActor), commonInfo);
            }
            defaultContent = this.getContent(commonInfo, businessKey, afterTask, operator, content, false);
            this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent, commonInfo, new ArrayList<String>(currenActor));
        }
        if ("start".equals(actionCode)) {
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            Optional<UserTask> userTask = deployService.getUserTask(afterTask.getProcessDefinitionId(), afterTask.getUserTaskId(), formSchemeKey);
            List userAction = userTask.map(usertask -> usertask.getActions()).orElse(null);
            for (UserAction action : userAction) {
                commonInfo.put("action", action.getName());
                commonInfo.put("actionId", action.getId());
                String defaultContent2 = this.getContent(commonInfo, businessKey, afterTask, operator, content, false);
                this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent2, commonInfo, new ArrayList<String>(currenActor));
            }
        }
    }

    private void customSendMsg(Task preTask, Task afterTask, String formSchemeKey, String actionCode, Set<String> currenActor, Map<String, String> commonInfo, BusinessKey businessKey, String content, boolean sendEmail, int canUploadUnitSize, Set<String> formOrGroupKeys) {
        WorkFlowLine workflowLine = this.customWorkFlowLine(preTask, afterTask, actionCode, businessKey);
        String actionName = this.actionName(formSchemeKey, actionCode, preTask.getUserTaskId());
        commonInfo.put("action", actionName);
        commonInfo.put("actionId", actionCode);
        if (workflowLine != null) {
            String customContent = this.customContent(workflowLine, preTask, formSchemeKey, commonInfo, businessKey, content, canUploadUnitSize, formOrGroupKeys, sendEmail);
            List<String> customLineUser = this.customLineUser(workflowLine, afterTask, currenActor, businessKey);
            this.send(afterTask, preTask, formSchemeKey, actionCode, currenActor, commonInfo, businessKey, content, sendEmail, canUploadUnitSize, formOrGroupKeys, customContent, customLineUser, workflowLine);
        } else {
            logger.info("\u8f6c\u79fb\u7ebf\u53c2\u6570\u4e3a\u7a7a,\u8bf7\u6838\u5b9e");
        }
    }

    private void send(Task afterTask, Task preTask, String formSchemeKey, String actionCode, Set<String> currenActor, Map<String, String> commonInfo, BusinessKey businessKey, String content, boolean sendEmail, int canUploadUnitSize, Set<String> formOrGroupKeys, String customContent, List<String> customLineUser, WorkFlowLine workflowLine) {
        String actionTitle = this.messageCommonParam.actionName(formSchemeKey, actionCode, preTask.getUserTaskId());
        commonInfo.put("action", actionTitle);
        commonInfo.put("actionId", actionCode);
        this.sendMail(customContent, content, workflowLine.getMsgcontent(), customLineUser, workflowLine.isSendby_mail(), preTask, afterTask, formSchemeKey, actionCode, commonInfo, businessKey, sendEmail, canUploadUnitSize, formOrGroupKeys, workflowLine.isSendby_phone());
        List<String> actionList = this.getAction(formSchemeKey, businessKey.getMasterEntity(), businessKey.getPeriod(), afterTask);
        if (actionList.size() > 0) {
            String actionName;
            if (actionList.contains("cus_upload")) {
                actionName = this.messageCommonParam.actionName(formSchemeKey, "cus_upload", afterTask.getUserTaskId());
                commonInfo.put("action", actionName);
                commonInfo.put("actionId", "cus_upload");
                this.sendDaiban(customContent, customLineUser, workflowLine.isSendby_protal(), afterTask, formSchemeKey, commonInfo, content, formOrGroupKeys, sendEmail);
            }
            if (actionList.contains("cus_confirm")) {
                actionName = this.messageCommonParam.actionName(formSchemeKey, "cus_confirm", afterTask.getUserTaskId());
                commonInfo.put("action", actionName);
                commonInfo.put("actionId", "cus_confirm");
                this.sendDaiban(customContent, customLineUser, workflowLine.isSendby_protal(), afterTask, formSchemeKey, commonInfo, content, formOrGroupKeys, sendEmail);
            }
            if (actionList.contains("cus_submit")) {
                actionName = this.messageCommonParam.actionName(formSchemeKey, "cus_submit", afterTask.getUserTaskId());
                commonInfo.put("action", actionName);
                commonInfo.put("actionId", "cus_submit");
                this.sendDaiban(customContent, customLineUser, workflowLine.isSendby_protal(), afterTask, formSchemeKey, commonInfo, content, formOrGroupKeys, sendEmail);
            }
            if ("start".equals(actionCode)) {
                Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                Optional<UserTask> userTask = deployService.getUserTask(afterTask.getProcessDefinitionId(), afterTask.getUserTaskId(), formSchemeKey);
                List userAction = userTask.map(usertask -> usertask.getActions()).orElse(null);
                for (UserAction action : userAction) {
                    commonInfo.put("action", action.getName());
                    commonInfo.put("actionId", action.getId());
                    String defaultContent = this.defaultModel(formSchemeKey, commonInfo, content, sendEmail);
                    this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent, commonInfo, new ArrayList<String>(currenActor));
                }
            }
        }
    }

    private String customContent(WorkFlowLine workflowLine, Task preTask, String formSchemeKey, Map<String, String> commonInfo, BusinessKey businessKey, String content, int canUploadUnitSize, Set<String> formOrGroupKeys, boolean sendEmail) {
        String customMessage = null;
        if (workflowLine != null) {
            String msgcontent = workflowLine.getMsgcontent();
            commonInfo.put("msgTemplate", msgcontent);
            customMessage = this.getCustomMessage(preTask, msgcontent, commonInfo, businessKey, content, canUploadUnitSize, formOrGroupKeys, false);
            if (customMessage == null) {
                customMessage = this.defaultModel(formSchemeKey, commonInfo, content, sendEmail);
            }
        }
        return customMessage;
    }

    private List<String> customLineUser(WorkFlowLine workflowLine, Task afterTask, Set<String> currenActor, BusinessKey businessKey) {
        Map<String, Object> msguser = workflowLine.getMsguser();
        List<String> lineUser = this.lineUser(afterTask, msguser, businessKey);
        if (lineUser.size() == 0) {
            lineUser.addAll(new ArrayList<String>(currenActor));
        }
        return lineUser;
    }

    private void sendDaiban(String customContent, List<String> customLineUser, boolean sendby_protal, Task afterTask, String formSchemeKey, Map<String, String> commonInfo, String content, Set<String> formOrGroupKeys, boolean sendMail) {
        if (sendby_protal) {
            if (customContent != null) {
                this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), customContent, commonInfo, customLineUser);
            } else {
                String defaultContent = this.defaultModel(formSchemeKey, commonInfo, content, sendMail);
                this.sendDaiBan(formOrGroupKeys, afterTask.getId(), afterTask.getUserTaskId(), defaultContent, commonInfo, customLineUser);
            }
        }
    }

    private void sendMail(String customContent, String content, String defalutContent, List<String> customLineUser, boolean sendby_mail, Task preTask, Task afterTask, String formSchemeKey, String actionCode, Map<String, String> commonInfo, BusinessKey businessKey, boolean sendEmail, int canUploadUnitSize, Set<String> formOrGroupKeys, boolean sendByPhone) {
        boolean isSendMail = false;
        WorkflowSettingDefine workflowSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowSetting.getWorkflowId(), 1);
        if (workFlowDefine != null && workFlowDefine.getId() != null) {
            isSendMail = workFlowDefine.isSendEmail();
        }
        String mailMessage = this.getCustomMessage(preTask, defalutContent, commonInfo, businessKey, content, canUploadUnitSize, formOrGroupKeys, true);
        if (isSendMail) {
            if ("cus_reject".equals(actionCode)) {
                if (sendEmail) {
                    this.sendEmail(afterTask.getId(), mailMessage, customLineUser, commonInfo);
                }
            } else if (sendby_mail) {
                this.sendEmail(afterTask.getId(), mailMessage, customLineUser, commonInfo);
            }
        } else if (sendby_mail) {
            this.sendEmail(afterTask.getId(), mailMessage, customLineUser, commonInfo);
        }
        if (sendByPhone) {
            this.sendByPhone(customLineUser, commonInfo, content);
        }
    }

    private boolean sendByPhone(List<String> customLineUser, Map<String, String> param, String content) {
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setGrouptype(MSG_TYPE_STR);
        dto.setMsgtype(TODO_TITLE);
        dto.setReceiveUserIds(customLineUser);
        dto.setMsgChannel(VaMessageOption.MsgChannel.SMS);
        dto.setContent(content);
        String actionName = param.get("action");
        dto.setTitle(actionName + MSG_TYPE_STR);
        R addMsg = this.vaMessageClient.addMsg(dto);
        int code = addMsg.getCode();
        if (code != 0) {
            logger.error(addMsg.getMsg() + ",\u77ed\u4fe1\u53d1\u9001\u5931\u8d25\uff01\uff01\uff01");
            return false;
        }
        return true;
    }

    private WorkFlowLine customWorkFlowLine(Task preTask, Task afterTask, String actionCode, BusinessKey businessKey) {
        WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        WorkFlowLine workflowLine = null;
        List<WorkFlowLine> workflowLineList = this.customWorkFolwService.getWorkflowLineByEndNode(afterTask.getUserTaskId(), workFlowDefine.getLinkid());
        if (workflowLineList != null && workflowLineList.size() > 0) {
            for (WorkFlowLine line : workflowLineList) {
                String actionid = line.getActionid();
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, workFlowDefine.getLinkid());
                if (workflowAction == null) continue;
                if (workflowLineList.size() == 1) {
                    if (!actionCode.equals(workflowAction.getActionCode()) || !afterTask.getUserTaskId().equals(line.getAfterNodeID())) continue;
                    workflowLine = line;
                    break;
                }
                if (!actionCode.equals(workflowAction.getActionCode()) || !afterTask.getUserTaskId().equals(line.getAfterNodeID()) || !preTask.getUserTaskId().equals(line.getBeforeNodeID())) continue;
                workflowLine = line;
                break;
            }
        }
        return workflowLine;
    }

    private Map<String, String> commonInfoVersion1_0(String businessKeyStr, Set<String> formOrGroupKeys, String operator) {
        HashMap<String, String> param = new HashMap<String, String>();
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyStr);
        try {
            TaskDefine queryTaskDefine;
            String formSchemeKey = businessKey.getFormSchemeKey();
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                param.put("formSchemeId", formSchemeKey);
                param.put("formSchemeTitle", formScheme.getTitle());
                param.put("taskId", formScheme.getTaskKey());
            }
            if ((queryTaskDefine = this.commonUtil.getTaskDefine(formScheme.getTaskKey())) != null) {
                String taskTitle = queryTaskDefine.getTitle();
                param.put("taskTitle", taskTitle);
                List<FormSchemeDefine> formSchemeByTask = this.commonUtil.getFormSchemeDefineByTaskKey(queryTaskDefine.getKey());
                if (formSchemeByTask.size() > 1) {
                    param.put("formSchemeSize", "1");
                } else {
                    param.put("formSchemeSize", "0");
                }
            }
            if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                String formOrGroupName = this.commonUtil.getFormOrGroupNames(businessKey.getFormSchemeKey(), formOrGroupKeys);
                String formOrGroupCode = this.commonUtil.getFormOrGroupCodes(businessKey.getFormSchemeKey(), formOrGroupKeys);
                WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    param.put("reportId", formOrGroupCode);
                    param.put("reportName", formOrGroupName);
                    param.put("type", FROM);
                } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                    param.put("reportId", formOrGroupCode);
                    param.put("groupName", formOrGroupName);
                    param.put("type", GROUP);
                } else {
                    param.put("type", ENTITY);
                }
            }
            String period = businessKey.getPeriod();
            param.put("period", period);
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            buildDimension.setValue("DATATIME", (Object)period);
            List<IEntityRow> entityData = this.entityHelper.getEntityRow(formSchemeKey, buildDimension);
            if (entityData.size() > 0) {
                for (IEntityRow iEntityRow : entityData) {
                    String entityKeyData = iEntityRow.getEntityKeyData();
                    String title = iEntityRow.getTitle();
                    param.put("unitName", title);
                    param.put("unitId", entityKeyData);
                }
            }
            param.put("operator", operator);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return param;
    }

    private Map<String, String> commonInfo(String businessKeyStr, String formOrGroupKey, String operator) {
        HashMap<String, String> param = new HashMap<String, String>();
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyStr);
        try {
            TaskDefine queryTaskDefine;
            String formSchemeKey = businessKey.getFormSchemeKey();
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                param.put("formSchemeId", formSchemeKey);
                param.put("formSchemeTitle", formScheme.getTitle());
                param.put("taskId", formScheme.getTaskKey());
            }
            if ((queryTaskDefine = this.commonUtil.getTaskDefine(formScheme.getTaskKey())) != null) {
                String taskTitle = queryTaskDefine.getTitle();
                param.put("taskTitle", taskTitle);
                List<FormSchemeDefine> formSchemeByTask = this.commonUtil.getFormSchemeDefineByTaskKey(queryTaskDefine.getKey());
                if (formSchemeByTask.size() > 1) {
                    param.put("formSchemeSize", "1");
                } else {
                    param.put("formSchemeSize", "0");
                }
            }
            if (StringUtils.isNotEmpty((String)formOrGroupKey) && formOrGroupKey != null) {
                String formOrGroupName = this.commonUtil.getFormOrGroupName(businessKey.getFormSchemeKey(), formOrGroupKey);
                WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    param.put("reportId", formOrGroupKey);
                    param.put("reportName", formOrGroupName);
                    param.put("type", FROM);
                } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                    param.put("reportId", formOrGroupKey);
                    param.put("reportName", formOrGroupName);
                    param.put("type", GROUP);
                } else {
                    param.put("type", ENTITY);
                }
            }
            String period = businessKey.getPeriod();
            param.put("period", period);
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            buildDimension.setValue("DATATIME", (Object)period);
            List<IEntityRow> entityData = this.entityHelper.getEntityRow(formSchemeKey, buildDimension);
            if (entityData.size() > 0) {
                for (IEntityRow iEntityRow : entityData) {
                    String entityKeyData = iEntityRow.getEntityKeyData();
                    String title = iEntityRow.getTitle();
                    param.put("unitName", title);
                    param.put("unitId", entityKeyData);
                }
            }
            param.put("operator", operator);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return param;
    }

    private String defaultModel(String formSchemeKey, Map<String, String> param, String contentDesc, boolean sendMail) {
        String content = null;
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                TaskDefine queryTaskDefine = this.commonUtil.getTaskDefine(formScheme.getTaskKey());
                List<FormSchemeDefine> formSchemeByTask = this.commonUtil.getFormSchemeDefineByTaskKey(queryTaskDefine.getKey());
                String periodStr = param.get("period");
                String period = this.messageCommonParam.date(formSchemeKey, periodStr);
                content = formSchemeByTask != null ? (formSchemeByTask.size() > 1 ? param.get("taskTitle") + ", " + param.get("formSchemeTitle") + ", " + period + ", " + param.get("unitName") + ", " : param.get("taskTitle") + ", " + period + ", " + param.get("unitName") + ", ") : param.get("taskTitle") + ", " + param.get("formSchemeTitle") + ", " + period + ", " + param.get("unitName") + ", ";
                content = param.get("reportName") != null ? content + param.get("reportName") + ";" : content.substring(0, content.length() - 2) + ";";
            }
            String language = NpContextHolder.getContext().getLocale().getLanguage();
            if (contentDesc != null && !contentDesc.isEmpty()) {
                content = content != null && !content.isEmpty() ? ("zh".equals(language) ? content + param.get("action") + "\u539f\u56e0:" + contentDesc : content + param.get("action") + " reason:" + contentDesc) : ("zh".equals(language) ? param.get("action") + "\u539f\u56e0:" + contentDesc : param.get("action") + " reason:" + contentDesc);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return content;
    }

    private String getCustomMessage(Task preTask, String msgcontent, Map<String, String> param, BusinessKey businessKey, String contentDesc, int canUploadUnitSize, Set<String> formOrGroupKeys, boolean mail) {
        String content = null;
        try {
            Object msgBodyObj;
            boolean hasReason = false;
            ObjectMapper objectMapper = new ObjectMapper();
            Map jsonObject = (Map)objectMapper.readValue(msgcontent, (TypeReference)new TypeReference<Map<String, Object>>(){});
            if (jsonObject != null && (msgBodyObj = jsonObject.get("msgBody")) != null && !"".equals(msgBodyObj)) {
                String formOrGroupName;
                String title;
                String msgBody = msgBodyObj.toString();
                if (mail) {
                    if (msgBody.contains("mdName")) {
                        title = param.get("unitName");
                        content = msgBody.replaceAll("\u3010mdName\u3011", title + "");
                    }
                } else if (msgBody.contains("mdName")) {
                    title = param.get("unitName");
                    content = canUploadUnitSize > 1 ? msgBody.replaceAll("\u3010mdName\u3011", canUploadUnitSize + "\u5bb6\u5355\u4f4d,") : msgBody.replaceAll("\u3010mdName\u3011", title + "");
                }
                if (msgBody.contains("nodeName")) {
                    title = preTask.getName();
                    String userTaskId = preTask.getUserTaskId();
                    if (userTaskId != null) {
                        if ("tsk_submit".equals(userTaskId)) {
                            title = "\u9001\u5ba1";
                        } else if ("tsk_start".equals(userTaskId)) {
                            title = "\u5f00\u59cb";
                        } else if ("tsk_upload".equals(userTaskId)) {
                            title = "\u4e0a\u62a5";
                        } else if ("tsk_audit".equals(userTaskId) || "tsk_audit_after_confirm".equals(userTaskId)) {
                            title = "\u5ba1\u6279";
                        }
                    }
                    content = content == null ? msgBody.replaceAll("\u3010nodeName\u3011", title + "") : content.replaceAll("\u3010nodeName\u3011", title + "");
                }
                if (msgBody.contains("period")) {
                    String periodStr = param.get("period");
                    String period = this.messageCommonParam.date(param.get("formSchemeId"), periodStr);
                    content = content == null ? msgBody.replaceAll("\u3010period\u3011", period + "") : content.replaceAll("\u3010period\u3011", period + "");
                }
                if (msgBody.contains("reportSchemeName")) {
                    title = param.get("formSchemeTitle");
                    content = content == null ? msgBody.replaceAll("\u3010reportSchemeName\u3011", title + "") : content.replaceAll("\u3010reportSchemeName\u3011", title + "");
                }
                if (msgBody.contains("taskName")) {
                    title = param.get("taskTitle");
                    content = content == null ? msgBody.replaceAll("\u3010taskName\u3011", title + "") : content.replaceAll("\u3010taskName\u3011", title + "");
                }
                if (msgBody.contains("operator")) {
                    NpContext context = NpContextHolder.getContext();
                    ContextUser user = context.getUser();
                    if (user != null) {
                        String fullName = user.getFullname();
                        content = content.replaceAll("\u3010operator\u3011", fullName + "");
                    } else {
                        content = content.replace("\u3010operator\u3011", "");
                    }
                }
                if (mail) {
                    if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                        formOrGroupName = this.commonUtil.getFormOrGroupNames(businessKey.getFormSchemeKey(), formOrGroupKeys);
                        if (msgBody.contains("reportName")) {
                            content = content.replaceAll("\u3010reportName\u3011", formOrGroupName + "");
                        }
                        if (msgBody.contains("groupName")) {
                            content = content.replaceAll("\u3010groupName\u3011", formOrGroupName + "");
                        }
                    }
                } else if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
                    if (formOrGroupKeys.size() == 1) {
                        formOrGroupName = this.commonUtil.getFormOrGroupName(businessKey.getFormSchemeKey(), formOrGroupKeys.iterator().next());
                        if (msgBody.contains("reportName")) {
                            content = content.replaceAll("\u3010reportName\u3011", formOrGroupName + "");
                        }
                        if (msgBody.contains("groupName")) {
                            content = content.replaceAll("\u3010groupName\u3011", formOrGroupName + "");
                        }
                    } else {
                        if (msgBody.contains("reportName")) {
                            content = content.replaceAll("\u3010reportName\u3011", formOrGroupKeys.size() + "\u5f20,");
                        }
                        if (msgBody.contains("groupName")) {
                            content = content.replaceAll("\u3010groupName\u3011", formOrGroupKeys.size() + "\u4e2a,");
                        }
                    }
                }
                if (msgBody.contains("reason")) {
                    hasReason = true;
                    content = content.replaceAll("\u3010reason\u3011", contentDesc + "");
                }
            }
            if (!hasReason && contentDesc != null && !contentDesc.isEmpty()) {
                content = content != null && !content.isEmpty() ? content + "\u539f\u56e0:" + contentDesc : param.get("action") + "\u539f\u56e0:" + contentDesc;
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return content;
    }

    private List<String> lineUser(Task preTask, Map<String, Object> msguser, BusinessKey businessKey) {
        ArrayList<String> actorId = new ArrayList<String>();
        try {
            if (msguser != null) {
                for (Map.Entry<String, Object> entry : msguser.entrySet()) {
                    String key = entry.getKey();
                    Object userAndRole = entry.getValue();
                    ActorStrategyParameter receiverParam = ActionMethod.getReceiverParam(key, userAndRole.toString());
                    for (ActorStrategy ai : this.actorStrategy) {
                        if (!ai.getType().equals(key)) continue;
                        Set<String> actor = ai.getActors((BusinessKeyInfo)businessKey, ai.serializeParameter(receiverParam), preTask);
                        actorId.addAll(actor);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return actorId;
    }

    private String actionName(String formSchemeKey, String actionCode, String taskCode) {
        String actionName = "";
        try {
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                Map<String, String> actionCodeAndActionName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
                if (actionCodeAndActionName != null && actionCodeAndActionName.size() > 0) {
                    for (Map.Entry<String, String> action : actionCodeAndActionName.entrySet()) {
                        String actionId = action.getKey();
                        if (!actionId.equals(actionCode)) continue;
                        actionName = actionCodeAndActionName.get(actionCode);
                    }
                } else if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
                    actionName = "\u4e0a\u62a5";
                } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
                    actionName = "\u9000\u56de";
                } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
                    actionName = "\u786e\u8ba4";
                } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
                    actionName = "\u9001\u5ba1";
                } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
                    actionName = "\u9000\u5ba1";
                }
            } else {
                WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                WorkFlowDefine workFlowDefineByID = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionByCode(taskCode, actionCode, workFlowDefineByID.getLinkid());
                if (workflowAction != null) {
                    actionName = workflowAction.getActionTitle();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return actionName;
    }

    private List<String> getAction(String formSchemeKey, MasterEntity masterEntity, String period, Task delegateTask) {
        String actionCode = null;
        ArrayList<String> actionCodeList = new ArrayList<String>();
        try {
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                Optional<UserTask> userTask = deployService.getUserTask(delegateTask.getProcessDefinitionId(), delegateTask.getUserTaskId(), formSchemeKey);
                List userAction = userTask.map(usertask -> usertask.getActions()).orElse(null);
                for (UserAction action : userAction) {
                    actionCode = action.getId();
                    actionCodeList.add(actionCode);
                }
            } else {
                String[] actions;
                WorkflowSettingDefine refSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
                String userTaskId = delegateTask.getUserTaskId();
                WorkFlowNodeSet workFlowNodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID(userTaskId, workFlowDefine.getLinkid());
                if (workFlowNodeSetByID != null && (actions = workFlowNodeSetByID.getActions()).length > 0) {
                    for (String action : actions) {
                        WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(action, workFlowDefine.getLinkid());
                        if (workflowAction == null) continue;
                        String aCode = workflowAction.getActionCode();
                        actionCodeList.add(aCode);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        return actionCodeList;
    }

    private void defaultMessageBody(Task delegateTask, String businessKeyStr, List<String> participants, String operator, WorkFlowLine workFlowLine) {
        String userTaskId = delegateTask.getUserTaskId();
        BusinessKey businessKey = BusinessKeyFormatter.parsingFromString(businessKeyStr);
        MasterEntity masterEntity = businessKey.getMasterEntity();
        String adjust = masterEntity.getMasterEntityKey("ADJUST");
        String formSchemeKey = businessKey.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), businessKey);
        String period = businessKey.getPeriod();
        HashSet<String> formOrGroupKeys = new HashSet<String>();
        formOrGroupKeys.add(businessKey.getFormKey());
        List<String> actionList = this.getAction(formSchemeKey, masterEntity, period, delegateTask);
        for (String action : actionList) {
            String customContent;
            HashMap<String, String> commonInfo = new HashMap<String, String>();
            if (adjust != null) {
                commonInfo.put("adjust", adjust);
            }
            if (corporateValue != null) {
                commonInfo.put("corporateValue", corporateValue);
            }
            commonInfo.put("todoType", userTaskId);
            commonInfo.put("nodeName", delegateTask.getName());
            commonInfo.put("currentActionId", action);
            commonInfo.put("todoType", delegateTask.getUserTaskId());
            String content = this.getContent(commonInfo, businessKey, action, userTaskId, operator, null, false, delegateTask);
            if (workFlowLine != null && workFlowLine.getId() != null && (customContent = this.customContent(workFlowLine, delegateTask, formSchemeKey, commonInfo, businessKey, content, 1, formOrGroupKeys, false)) != null) {
                content = customContent;
            }
            commonInfo.put("content", "");
            commonInfo.put("reason", "");
            WorkFlowNodeSet nodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(workFlowLine.getAfterNodeID(), workFlowLine.getLinkid());
            if (nodeSet != null) {
                commonInfo.put("signNode", String.valueOf(nodeSet.isSignNode()));
            }
            this.sendDaiBan(delegateTask.getId(), delegateTask.getUserTaskId(), content, participants, commonInfo, businessKey.getFormKey());
        }
    }

    private String getContent(Map<String, String> commonInfo, BusinessKey businessKey, Task afterTask, String operator, String contentDes, boolean senMail) {
        String defaultModelChinese = "";
        String formSchemeKey = businessKey.getFormSchemeKey();
        defaultModelChinese = this.messageCommonParam.getChineseDefaultModel(formSchemeKey, commonInfo, contentDes, senMail);
        PortalConfig runTimePortalConfig = this.portalService.getRunTimePortalConfig();
        if (runTimePortalConfig != null) {
            List header_btn = runTimePortalConfig.getHeader_btn();
            for (HeaderBtnConfig headerBtnConfig : header_btn) {
                boolean selected;
                if (!"languages".equals(headerBtnConfig.getId()) || !(selected = headerBtnConfig.isSelected())) continue;
                String actionId = commonInfo.get("actionId");
                this.appendEnglishParam(businessKey, businessKey.getFormKey(), operator, actionId, afterTask.getUserTaskId(), commonInfo, contentDes, senMail, afterTask);
                if (!senMail) continue;
                String defaultEnglish = commonInfo.get("otherContent");
                defaultModelChinese = defaultModelChinese + "<br><br>" + defaultEnglish;
            }
        }
        return defaultModelChinese;
    }

    private String getContent(Map<String, String> commonInfo, BusinessKey businessKey, String actionId, String userTaskId, String operator, String contentDes, boolean sendMail, Task afterTask) {
        String defaultModelChinese = "";
        String formSchemeKey = businessKey.getFormSchemeKey();
        Map<String, String> chinesecommonInfo = this.messageCommonParam.getChineseCommonInfo(businessKey, businessKey.getFormKey(), operator, afterTask);
        chinesecommonInfo.put("currentActionId", actionId);
        String actionNameChinese = this.messageCommonParam.actionName(formSchemeKey, actionId, userTaskId);
        chinesecommonInfo.put("action", actionNameChinese);
        chinesecommonInfo.put("actionId", actionId);
        chinesecommonInfo.put("userTaskId", userTaskId);
        defaultModelChinese = this.messageCommonParam.getChineseDefaultModel(formSchemeKey, chinesecommonInfo, contentDes, sendMail);
        this.appendEnglishParam(businessKey, businessKey.getFormKey(), operator, actionId, userTaskId, chinesecommonInfo, contentDes, sendMail, afterTask);
        commonInfo.putAll(chinesecommonInfo);
        if (sendMail) {
            String defaultEnglish = chinesecommonInfo.get("otherContent");
            defaultModelChinese = defaultModelChinese + "/n" + defaultEnglish;
        }
        return defaultModelChinese;
    }

    private void appendEnglishParam(BusinessKey businessKey, String formOrGroupKey, String operator, String actionId, String userTaskId, Map<String, String> commonInfo, String contentDes, boolean sendMail, Task afterTask) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        Map<String, String> englishCommonInfo = this.messageCommonParam.getEnglishCommonInfo(businessKey, formOrGroupKey, operator, afterTask);
        String actionNameEnglish = this.messageCommonParam.actionName(formSchemeKey, actionId, userTaskId);
        englishCommonInfo.put("actionId", actionId);
        englishCommonInfo.put("action", actionNameEnglish);
        String defaultModelEnglish = this.defaultModel(formSchemeKey, englishCommonInfo, contentDes, sendMail);
        commonInfo.put("otherContent", defaultModelEnglish);
        commonInfo.put("otherTitle", actionNameEnglish + " Notice");
        commonInfo.put("otherActionName", actionNameEnglish);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String writeValueAsString = mapper.writeValueAsString(englishCommonInfo);
            commonInfo.put("otherParam", writeValueAsString);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

