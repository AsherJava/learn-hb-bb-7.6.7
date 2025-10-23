/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserManagerService
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.BusinessKeyUtil
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.jsoup.Jsoup
 */
package com.jiuqi.nr.workflow2.events.executor.util;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserManagerService;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyExecutor;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.BusinessKeyUtil;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageType;
import com.jiuqi.nr.workflow2.events.executor.msg.enumeration.MessageVariable;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageRelevantInfo;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationSendUtil {
    public static final String MSG_GROUP_TITLE = "\u62a5\u8868\u6d41\u7a0b\u901a\u77e5";
    public static final String MSG_TITLE = "\u901a\u77e5";
    @Autowired
    private IActorStrategyFactory actorStrategyFactory;
    @Autowired
    private BusinessKeyUtil businessKeyUtil;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private IProcessEntityQueryHelper entityQueryHelper;
    @Autowired
    private IProcessRuntimeParamHelper runtimeParamHelper;

    public MessageRelevantInfo buildVaribleReplaceMap(ProcessExecuteEnv envParam, IBusinessKey businessKey, String receiver) throws Exception {
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(envParam.getTaskKey());
        SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(envParam.getPeriod(), envParam.getTaskKey());
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.getTask(envParam.getTaskKey());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(taskDefine.getDateTime());
        String period = periodProvider.getPeriodTitle(envParam.getPeriod());
        String entityId = this.getEntityCaliber(formSchemeDefine);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        IUserTask userTask = this.processMetaDataService.getUserTask(envParam.getTaskKey(), envParam.getUserTaskCode());
        IEntityRow entityRow = this.findEntityRow((IProcessRunPara)envParam, businessKey);
        HashMap<String, String> variableReplaceMap = new HashMap<String, String>();
        variableReplaceMap.put(MessageVariable.CURRENT_NODE_NAME.code, userTask.getTitle());
        variableReplaceMap.put(MessageVariable.FORM_SCHEME_NAME.code, formSchemeDefine.getTitle());
        variableReplaceMap.put(MessageVariable.MD_NAME.code, entityRow.getTitle());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            FormObject formObject = (FormObject)businessKey.getBusinessObject();
            FormDefine formDefine = this.runTimeViewController.getForm(formObject.getFormKey(), formSchemeDefine.getKey());
            variableReplaceMap.put(MessageVariable.REPORT_NAME.code, formDefine.getTitle());
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            FormGroupObject formGroupObject = (FormGroupObject)businessKey.getBusinessObject();
            FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(formGroupObject.getFormGroupKey(), formSchemeDefine.getKey());
            variableReplaceMap.put(MessageVariable.GROUP_NAME.code, formGroupDefine.getTitle());
        }
        variableReplaceMap.put(MessageVariable.OPERATOR.code, this.getUserName(NpContextHolder.getContext().getUserId()));
        if (receiver != null && !receiver.isEmpty()) {
            variableReplaceMap.put(MessageVariable.RECEIVER.code, this.getUserName(receiver));
        }
        if (!envParam.getEnvVariables().isNull("COMMENT")) {
            variableReplaceMap.put(MessageVariable.OPERATE_EXPLANATION.code, envParam.getEnvVariables().getString("COMMENT"));
        }
        variableReplaceMap.put(MessageVariable.TASK_NAME.code, taskDefine.getTitle());
        variableReplaceMap.put(MessageVariable.PERIOD.code, period);
        if (this.isMultiEntityCaliberWithReportDimensionEnable(envParam.getTaskKey())) {
            variableReplaceMap.put(MessageVariable.ENTITY_CALIBER.code, entityDefine.getTitle());
        }
        MessageRelevantInfo relevantInfo = new MessageRelevantInfo();
        relevantInfo.setRelevantUnitKey(Collections.singleton(entityRow.getEntityKeyData()));
        relevantInfo.setRelevantUnitTitle(Collections.singleton(entityRow.getTitle()));
        relevantInfo.setVaribleReplaceMap(variableReplaceMap);
        return relevantInfo;
    }

    public MessageRelevantInfo buildVaribleReplaceMap(ProcessExecuteEnv envParam, List<IBusinessObject> businessObjects, String receiver) throws Exception {
        Object entityRow;
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(envParam.getTaskKey());
        SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(envParam.getPeriod(), envParam.getTaskKey());
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.getTask(envParam.getTaskKey());
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodProvider periodProvider = periodAdapter.getPeriodProvider(taskDefine.getDateTime());
        String period = periodProvider.getPeriodTitle(envParam.getPeriod());
        String entityId = this.getEntityCaliber(formSchemeDefine);
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        IUserTask userTask = this.processMetaDataService.getUserTask(envParam.getTaskKey(), envParam.getUserTaskCode());
        LinkedHashMap<String, List> unitWithFormOrFormGroupMap = new LinkedHashMap<String, List>();
        for (IBusinessObject businessObject : businessObjects) {
            String unitCode = businessObject.getDimensions().getDWDimensionValue().getValue().toString();
            if (workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION)) {
                unitWithFormOrFormGroupMap.put(unitCode, null);
                continue;
            }
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                FormObject formObject = (FormObject)businessObject;
                FormDefine formDefine = this.runTimeViewController.getForm(formObject.getFormKey(), formSchemeDefine.getKey());
                unitWithFormOrFormGroupMap.computeIfAbsent(unitCode, k -> new ArrayList()).add(formDefine.getKey());
                continue;
            }
            if (!workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) continue;
            FormGroupObject formGroupObject = (FormGroupObject)businessObject;
            FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(formGroupObject.getFormGroupKey(), formSchemeDefine.getKey());
            unitWithFormOrFormGroupMap.computeIfAbsent(unitCode, k -> new ArrayList()).add(formGroupDefine.getKey());
        }
        StringBuilder mdNameReplaceText = new StringBuilder();
        IEntityTable entityTable = this.getEntityTable((IProcessRunPara)envParam, entityDefine);
        Set<String> unitKeySet = unitWithFormOrFormGroupMap.keySet();
        LinkedHashSet<String> unitTitleSet = new LinkedHashSet<String>();
        for (String unitKey : unitKeySet) {
            IEntityRow entityRow2 = entityTable.findByEntityKey(unitKey);
            unitTitleSet.add(entityRow2.getTitle());
        }
        MessageRelevantInfo relevantInfo = new MessageRelevantInfo();
        relevantInfo.setRelevantUnitKey(unitKeySet);
        relevantInfo.setRelevantUnitTitle(unitTitleSet);
        ArrayList<String> unitTitle = new ArrayList<String>();
        if (unitKeySet.size() <= 3) {
            for (String unitKey : unitKeySet) {
                entityRow = entityTable.findByEntityKey(unitKey);
                unitTitle.add(entityRow.getTitle());
            }
            mdNameReplaceText.append(String.join((CharSequence)"\u3001", unitTitle));
        } else {
            Iterator<String> iterator = unitKeySet.iterator();
            for (int i = 0; i < 3 && iterator.hasNext(); ++i) {
                entityRow = entityTable.findByEntityKey(iterator.next());
                unitTitle.add(entityRow.getTitle());
            }
            mdNameReplaceText.append(String.join((CharSequence)"\u3001", unitTitle));
            mdNameReplaceText.append("\u7b49\u5171").append(unitKeySet.size()).append("\u5bb6\u5355\u4f4d");
        }
        HashMap<String, String> variableReplaceMap = new HashMap<String, String>();
        variableReplaceMap.put(MessageVariable.CURRENT_NODE_NAME.code, userTask.getTitle());
        variableReplaceMap.put(MessageVariable.FORM_SCHEME_NAME.code, formSchemeDefine.getTitle());
        variableReplaceMap.put(MessageVariable.MD_NAME.code, mdNameReplaceText.toString());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            HashSet formKeysSet = new HashSet();
            for (List keys : unitWithFormOrFormGroupMap.values()) {
                formKeysSet.addAll(keys);
            }
            StringBuilder reportNameReplaceText = new StringBuilder();
            ArrayList<String> formTitles = new ArrayList<String>();
            if (formKeysSet.size() <= 3) {
                for (String formKey : formKeysSet) {
                    FormDefine formDefine = this.runTimeViewController.getForm(formKey, formSchemeDefine.getKey());
                    formTitles.add(formDefine.getTitle());
                }
                reportNameReplaceText.append(String.join((CharSequence)"\u3001", formTitles));
            } else {
                Iterator iterator = formKeysSet.iterator();
                for (int i = 0; i < 3 && iterator.hasNext(); ++i) {
                    FormDefine formDefine = this.runTimeViewController.getForm((String)iterator.next(), formSchemeDefine.getKey());
                    formTitles.add(formDefine.getTitle());
                }
                reportNameReplaceText.append(String.join((CharSequence)"\u3001", formTitles));
                reportNameReplaceText.append("\u7b49\u5171").append(formKeysSet.size()).append("\u5f20\u62a5\u8868");
            }
            variableReplaceMap.put(MessageVariable.REPORT_NAME.code, reportNameReplaceText.toString());
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            HashSet formGroupKeysSet = new HashSet();
            for (List keys : unitWithFormOrFormGroupMap.values()) {
                formGroupKeysSet.addAll(keys);
            }
            StringBuilder groupNameReplaceText = new StringBuilder();
            ArrayList<String> formGroupTitles = new ArrayList<String>();
            if (formGroupKeysSet.size() <= 3) {
                for (String formGroupKey : formGroupKeysSet) {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup(formGroupKey, formSchemeDefine.getKey());
                    formGroupTitles.add(formGroupDefine.getTitle());
                }
                groupNameReplaceText.append(String.join((CharSequence)"\u3001", formGroupTitles));
            } else {
                Iterator iterator = formGroupKeysSet.iterator();
                for (int i = 0; i < 3 && iterator.hasNext(); ++i) {
                    FormGroupDefine formGroupDefine = this.runTimeViewController.getFormGroup((String)iterator.next(), formSchemeDefine.getKey());
                    formGroupTitles.add(formGroupDefine.getTitle());
                }
                groupNameReplaceText.append(String.join((CharSequence)"\u3001", formGroupTitles));
                groupNameReplaceText.append("\u7b49\u5171").append(formGroupKeysSet.size()).append("\u4e2a\u5206\u7ec4");
            }
            variableReplaceMap.put(MessageVariable.GROUP_NAME.code, groupNameReplaceText.toString());
        }
        variableReplaceMap.put(MessageVariable.OPERATOR.code, this.getUserName(NpContextHolder.getContext().getUserId()));
        if (receiver != null && !receiver.isEmpty()) {
            variableReplaceMap.put(MessageVariable.RECEIVER.code, this.getUserName(receiver));
        }
        if (!envParam.getEnvVariables().isNull("COMMENT")) {
            variableReplaceMap.put(MessageVariable.OPERATE_EXPLANATION.code, envParam.getEnvVariables().getString("COMMENT"));
        }
        variableReplaceMap.put(MessageVariable.TASK_NAME.code, taskDefine.getTitle());
        variableReplaceMap.put(MessageVariable.PERIOD.code, period);
        if (this.isMultiEntityCaliberWithReportDimensionEnable(envParam.getTaskKey())) {
            variableReplaceMap.put(MessageVariable.ENTITY_CALIBER.code, entityDefine.getTitle());
        }
        relevantInfo.setVaribleReplaceMap(variableReplaceMap);
        return relevantInfo;
    }

    public Set<String> getReceiverSet(ProcessExecuteEnv envParam, JSONObject messageConfig, IBusinessKey businessKey, boolean isTodoEnabled) {
        HashSet<String> matchUsers = new HashSet<String>();
        JSONArray receiverArray = messageConfig.getJSONArray("receiver");
        for (int i = 0; i < receiverArray.length(); ++i) {
            JSONObject receiverConfig = receiverArray.getJSONObject(i);
            String strategy = receiverConfig.getString("strategy");
            JSONObject strategyParam = receiverConfig.getJSONObject("param");
            List<IActorStrategyExecutor> strategyExecutors = this.getAllStrategyExecutors(envParam.getTaskKey(), strategy, strategyParam, isTodoEnabled);
            RuntimeBusinessKey runtimeBusinessKey = this.businessKeyUtil.buildRuntimeBusinessKey(businessKey);
            for (IActorStrategyExecutor strategyExecutor : strategyExecutors) {
                matchUsers.addAll(strategyExecutor.getMatchUsers(runtimeBusinessKey));
            }
        }
        return matchUsers;
    }

    public Map<String, List<IBusinessObject>> getReceiverSet(ProcessExecuteEnv envParam, JSONObject messageConfig, List<IBusinessObject> businessObjectList, boolean isTodoEnabled) {
        HashMap<String, List<IBusinessObject>> receiverMap = new HashMap<String, List<IBusinessObject>>();
        JSONArray receiverArray = messageConfig.getJSONArray("receiver");
        for (int i = 0; i < receiverArray.length(); ++i) {
            JSONObject receiverConfig = receiverArray.getJSONObject(i);
            String strategy = receiverConfig.getString("strategy");
            JSONObject strategyParam = receiverConfig.getJSONObject("param");
            List<IActorStrategyExecutor> strategyExecutors = this.getAllStrategyExecutors(envParam.getTaskKey(), strategy, strategyParam, isTodoEnabled);
            for (IBusinessObject businessObject : businessObjectList) {
                BusinessKey businessKey = new BusinessKey(envParam.getTaskKey(), businessObject);
                RuntimeBusinessKey runtimeBusinessKey = this.businessKeyUtil.buildRuntimeBusinessKey((IBusinessKey)businessKey);
                HashSet matchUsers = new HashSet();
                for (IActorStrategyExecutor strategyExecutor : strategyExecutors) {
                    matchUsers.addAll(strategyExecutor.getMatchUsers(runtimeBusinessKey));
                }
                for (String user : matchUsers) {
                    List userBusinessObjects = receiverMap.computeIfAbsent(user, k -> new ArrayList());
                    userBusinessObjects.add(businessObject);
                }
            }
        }
        return receiverMap;
    }

    public List<IActorStrategyExecutor> getAllStrategyExecutors(String taskKey, String strategy, JSONObject strategyParam, boolean isTodoEnabled) {
        String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
        DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
        ArrayList<IActorStrategyExecutor> strategyExecutors = new ArrayList<IActorStrategyExecutor>();
        if (strategy.equals("submitNodeTodoReceiverStrategy")) {
            NodeParticipant submitNodeParticipant = defaultProcessConfig.getSubmitNodeConfig().getParticipant();
            strategyExecutors.addAll(this.getActorStrategyExecutorListViaTodoReceiver(submitNodeParticipant, isTodoEnabled));
        } else if (strategy.equals("reportNodeTodoReceiverStrategy")) {
            NodeParticipant reportNodeParticipant = defaultProcessConfig.getReportNodeConfig().getParticipant();
            strategyExecutors.addAll(this.getActorStrategyExecutorListViaTodoReceiver(reportNodeParticipant, isTodoEnabled));
        } else if (strategy.equals("auditNodeTodoReceiverStrategy")) {
            NodeParticipant auditNodeParticipant = defaultProcessConfig.getAuditNodeConfig().getParticipant();
            strategyExecutors.addAll(this.getActorStrategyExecutorListViaTodoReceiver(auditNodeParticipant, isTodoEnabled));
        } else if (strategy.equals("submitNodeActionExecuterStrategy")) {
            NodeParticipant submitNodeParticipant = defaultProcessConfig.getSubmitNodeConfig().getParticipant();
            strategyExecutors.add(this.getActorStrategyExecutorViaParticipantItem(submitNodeParticipant.getActionExecuter()));
        } else if (strategy.equals("reportNodeActionExecuterStrategy")) {
            NodeParticipant reportNodeParticipant = defaultProcessConfig.getReportNodeConfig().getParticipant();
            strategyExecutors.add(this.getActorStrategyExecutorViaParticipantItem(reportNodeParticipant.getActionExecuter()));
        } else if (strategy.equals("auditNodeActionExecuterStrategy")) {
            NodeParticipant auditNodeParticipant = defaultProcessConfig.getAuditNodeConfig().getParticipant();
            strategyExecutors.add(this.getActorStrategyExecutorViaParticipantItem(auditNodeParticipant.getActionExecuter()));
        } else {
            IActorStrategyExecutor executor = this.actorStrategyFactory.queryActorStrategyExecutorFactory(strategy).createExecutor(strategyParam.toString());
            strategyExecutors.add(executor);
        }
        return strategyExecutors;
    }

    public List<IActorStrategyExecutor> getActorStrategyExecutorListViaTodoReceiver(NodeParticipant participant, boolean isTodoEnabled) {
        ArrayList<IActorStrategyExecutor> strategyExecutors = new ArrayList<IActorStrategyExecutor>();
        TodoReceiver todoReceiver = participant.getTodoReceiver();
        TodoReceiverStrategy type = todoReceiver.getType();
        if (!isTodoEnabled || type.equals((Object)TodoReceiverStrategy.IDENTICAL_TO_EXECUTOR)) {
            strategyExecutors.add(this.getActorStrategyExecutorViaParticipantItem(participant.getActionExecuter()));
        } else {
            List customParticipant = todoReceiver.getCustomParticipant();
            for (ParticipantItem item : customParticipant) {
                strategyExecutors.add(this.getActorStrategyExecutorViaParticipantItem(item));
            }
        }
        return strategyExecutors;
    }

    public IActorStrategyExecutor getActorStrategyExecutorViaParticipantItem(ParticipantItem participantItem) {
        String itemStrategy = participantItem.getStrategy();
        String itemParam = participantItem.getParam();
        return this.actorStrategyFactory.queryActorStrategyExecutorFactory(itemStrategy).createExecutor(itemParam);
    }

    public String getUserName(String userId) {
        Optional user = this.userManagerService.find(userId);
        if (!user.isPresent()) {
            Optional systemUser = this.systemUserService.find(userId);
            if (systemUser.isPresent()) {
                return ((SystemUser)systemUser.get()).getName();
            }
            return userId;
        }
        return ((User)user.get()).getFullname();
    }

    public String getEntityCaliber(FormSchemeDefine formSchemeDefine) {
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        if (entityCaliber == null) {
            entityCaliber = formSchemeDefine.getDw();
        }
        return entityCaliber;
    }

    public boolean isMultiEntityCaliberWithReportDimensionEnable(String taskKey) {
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        return taskOrgLinkDefines != null && taskOrgLinkDefines.size() > 1;
    }

    public IEntityTable getEntityTable(IProcessRunPara runEnvPara, IEntityDefine entityDefine) throws Exception {
        return this.getEntityTable(runEnvPara, entityDefine, AuthorityType.Read);
    }

    public IEntityTable getEntityTable(IProcessRunPara runEnvPara, IEntityDefine entityDefine, AuthorityType authorityType) throws Exception {
        String taskKey = runEnvPara.getTaskKey();
        String period = runEnvPara.getPeriod();
        IEntityQuery entityQuery = this.entityQueryHelper.makeIEntityQuery(taskKey, period);
        entityQuery.setAuthorityOperations(authorityType);
        EntityViewDefine entityViewDefine = this.runtimeParamHelper.buildEntityView(taskKey, period, entityDefine.getId());
        entityQuery.setEntityView(entityViewDefine);
        ExecutorContext executorContext = this.entityQueryHelper.makeExecuteContext(taskKey, period);
        return this.entityQueryHelper.getIEntityTable(entityQuery, executorContext);
    }

    public IEntityRow findEntityRow(IProcessRunPara runEnvPara, IBusinessKey businessKey) throws Exception {
        String unitId = businessKey.getBusinessObject().getDimensions().getDWDimensionValue().getValue().toString();
        IEntityDefine entityDefine = this.runtimeParamHelper.getProcessEntityDefinition(businessKey.getTask());
        IPeriodEntity periodEntityDefine = this.runtimeParamHelper.getPeriodEntityDefine(businessKey.getTask());
        IEntityTable entityTable = this.getEntityTable(runEnvPara, entityDefine);
        return entityTable.findByEntityKey(unitId);
    }

    public String postProcess(String messageType, String messageContent, MessageRelevantInfo relevantInfo) {
        Set<String> relevantUnitTitle;
        if (messageType.equals(MessageType.SHORT_MESSAGE.code)) {
            messageContent = Jsoup.parse((String)messageContent).text();
        } else if (messageType.equals(MessageType.EMAIL.code) && (relevantUnitTitle = relevantInfo.getRelevantUnitTitle()) != null && relevantUnitTitle.size() > 3) {
            StringBuilder temp = new StringBuilder(messageContent);
            temp.append("<p><br></p>").append("<p>&nbsp;&nbsp;&nbsp;&nbsp;\u5355\u4f4d\u8be6\u60c5\uff1a</p>");
            for (String unitTitle : relevantUnitTitle) {
                temp.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(unitTitle).append("</p>");
            }
            messageContent = temp.toString();
        }
        return messageContent;
    }
}

