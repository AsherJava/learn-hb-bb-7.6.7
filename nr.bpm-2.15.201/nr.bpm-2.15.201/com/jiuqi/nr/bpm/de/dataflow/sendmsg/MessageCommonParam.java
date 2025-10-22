/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.context.impl.NpContextIdentity
 *  com.jiuqi.np.core.context.impl.NpContextImpl
 *  com.jiuqi.np.core.context.impl.NpContextUser
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.jobmanager.exception.PlanTaskError
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.context.impl.NpContextIdentity;
import com.jiuqi.np.core.context.impl.NpContextImpl;
import com.jiuqi.np.core.context.impl.NpContextUser;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.jobmanager.exception.PlanTaskError;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCommonParam {
    private static final Logger logger = LogFactory.getLogger(MessageCommonParam.class);
    public static final String ENTITY = "entity";
    public static final String FROM = "from";
    public static final String GROUP = "group";
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private IWorkflow workflow;
    @Resource
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    private DeEntityHelper entityHelper;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private WorkflowSettingService workflowSettingService;

    public NpContextImpl buildChineseContext() {
        final NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        HashMap contextMap = new HashMap();
        Consumer<Map.Entry<String, ContextExtension>> consumer = new Consumer<Map.Entry<String, ContextExtension>>(){

            @Override
            public void accept(Map.Entry<String, ContextExtension> extension) {
                final ContextExtension extension1 = npContext.getExtension(extension.getKey());
                ContextExtension value = extension.getValue();
                Consumer<Map.Entry<String, Object>> consumerExtension = new Consumer<Map.Entry<String, Object>>(){

                    @Override
                    public void accept(Map.Entry<String, Object> objectEntry) {
                        extension1.put(objectEntry.getKey(), (Serializable)objectEntry.getValue());
                    }
                };
                value.apply((Consumer)consumerExtension);
            }
        };
        NpContextHolder.getContext().applyExtensions((Consumer)consumer);
        try {
            NpContextUser contextUser = this.buildUserContext();
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            Locale locale = new Locale("zh", "CN");
            npContext.setLocale(locale);
            return npContext;
        }
        catch (Exception e) {
            logger.error("\u6784\u5efacontext\u5bf9\u8c61\u5f02\u5e38");
            return npContext;
        }
    }

    public NpContextImpl buildEnglishContext() {
        final NpContextImpl npContext = (NpContextImpl)NpContextHolder.createEmptyContext();
        Consumer<Map.Entry<String, ContextExtension>> consumer = new Consumer<Map.Entry<String, ContextExtension>>(){

            @Override
            public void accept(Map.Entry<String, ContextExtension> extension) {
                final ContextExtension extension1 = npContext.getExtension(extension.getKey());
                ContextExtension value = extension.getValue();
                Consumer<Map.Entry<String, Object>> consumerExtension = new Consumer<Map.Entry<String, Object>>(){

                    @Override
                    public void accept(Map.Entry<String, Object> objectEntry) {
                        extension1.put(objectEntry.getKey(), (Serializable)objectEntry.getValue());
                    }
                };
                value.apply((Consumer)consumerExtension);
            }
        };
        NpContextHolder.getContext().applyExtensions((Consumer)consumer);
        try {
            NpContextUser contextUser = this.buildUserContext();
            npContext.setUser((ContextUser)contextUser);
            NpContextIdentity identity = this.buildIdentityContext(contextUser);
            npContext.setIdentity((ContextIdentity)identity);
            Locale locale = new Locale("en", "US");
            npContext.setLocale(locale);
        }
        catch (Exception e) {
            logger.error("\u6784\u5efacontext\u5bf9\u8c61\u5f02\u5e38");
        }
        return npContext;
    }

    private NpContextIdentity buildIdentityContext(NpContextUser contextUser) throws JQException {
        NpContextIdentity identity = new NpContextIdentity();
        String identityId = contextUser.getId();
        if (NpContextHolder.getContext() != null) {
            identityId = NpContextHolder.getContext().getIdentityId();
        }
        identity.setId(identityId);
        identity.setTitle(contextUser.getFullname());
        identity.setOrgCode(contextUser.getOrgCode());
        return identity;
    }

    private NpContextUser buildUserContext() throws JQException {
        NpContextUser userContext = new NpContextUser();
        User user = this.getUser();
        if (user == null) {
            throw new JQException((ErrorEnum)PlanTaskError.QUERY_USER);
        }
        userContext.setId(user.getId());
        userContext.setName(user.getName());
        userContext.setNickname(user.getNickname());
        userContext.setOrgCode(user.getOrgCode());
        userContext.setDescription(user.getDescription());
        return userContext;
    }

    private User getUser() {
        Actor fromNpContext = Actor.fromNpContext();
        String userId = fromNpContext.getUserId();
        if (StringUtils.isEmpty((String)userId)) {
            return null;
        }
        Optional user = this.userService.find(userId);
        if (user.isPresent()) {
            return (User)user.get();
        }
        List users = this.systemUserService.getUsers();
        if (users != null && users.size() > 0) {
            return (User)users.get(0);
        }
        return null;
    }

    public String actionName(String formSchemeKey, String actionCode, String taskCode) {
        String actionName = "";
        try {
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
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
                WorkFlowDefine runWorkFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionByCode(taskCode, actionCode, runWorkFlowDefineByID.getLinkid());
                if (workflowAction != null) {
                    actionName = workflowAction.getActionTitle();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        String languageName = this.actionAndStateUtil.getLanguageName(actionCode);
        if (StringUtils.isNotEmpty((String)languageName)) {
            actionName = languageName;
        }
        return actionName;
    }

    public Map<String, String> getChineseCommonInfo(BusinessKey businessKey, String formOrGroupKey, String operator, Task afterTask) {
        NpContextImpl chineseContext = this.buildChineseContext();
        NpContextHolder.setContext((NpContext)chineseContext);
        return this.getCommonInfo(businessKey, formOrGroupKey, operator, afterTask);
    }

    public Map<String, String> getChineseCommonInfo(BusinessKey businessKey, Set<String> formOrGroupKeys, String operator, Task afterTask) {
        NpContextImpl chineseContext = this.buildChineseContext();
        NpContextHolder.setContext((NpContext)chineseContext);
        return this.getCommonInfo(businessKey, formOrGroupKeys, operator, afterTask);
    }

    public Map<String, String> getEnglishCommonInfo(BusinessKey businessKey, String formOrGroupKey, String operator, Task afterTask) {
        NpContextImpl englishContext = this.buildEnglishContext();
        NpContextHolder.setContext((NpContext)englishContext);
        return this.getCommonInfo(businessKey, formOrGroupKey, operator, afterTask);
    }

    public Map<String, String> getEnglishCommonInfo(BusinessKey businessKey, Set<String> formOrGroupKeys, String operator, Task afterTask) {
        NpContextImpl englishContext = this.buildEnglishContext();
        NpContextHolder.setContext((NpContext)englishContext);
        return this.getCommonInfo(businessKey, formOrGroupKeys, operator, afterTask);
    }

    public Map<String, String> getCommonInfo(BusinessKey businessKey, String formOrGroupKey, String operator, Task afterTask) {
        HashMap<String, String> param = new HashMap<String, String>();
        try {
            WorkFlowType startType;
            TaskDefine queryTaskDefine;
            param.put("taskNodeId", afterTask.getId());
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
            if (WorkFlowType.FORM.equals((Object)(startType = this.workflow.queryStartType(formSchemeKey))) || WorkFlowType.GROUP.equals((Object)startType)) {
                String formOrGroupName = this.commonUtil.getFormOrGroupName(businessKey.getFormSchemeKey(), formOrGroupKey);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    param.put("reportId", formOrGroupKey);
                    param.put("reportName", formOrGroupName);
                    param.put("type", FROM);
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    param.put("reportId", formOrGroupKey);
                    param.put("reportName", formOrGroupName);
                    param.put("type", GROUP);
                }
            } else {
                param.put("type", ENTITY);
            }
            String period = businessKey.getPeriod();
            param.put("period", period);
            String periodTitle = this.date(formSchemeKey, period);
            param.put("periodTitle", periodTitle);
            DimensionValueSet dimension = this.dimensionUtil.buildDimension(businessKey);
            dimension.setValue("DATATIME", (Object)period);
            List<IEntityRow> entityData = this.entityHelper.getEntityRow(formSchemeKey, dimension);
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

    public Map<String, String> getCommonInfo(BusinessKey businessKey, Set<String> formOrGroupKeys, String operator, Task afterTask) {
        HashMap<String, String> param = new HashMap<String, String>();
        try {
            WorkFlowType startType;
            TaskDefine queryTaskDefine;
            param.put("taskNodeId", afterTask.getId());
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
            if (WorkFlowType.FORM.equals((Object)(startType = this.workflow.queryStartType(formSchemeKey))) || WorkFlowType.GROUP.equals((Object)startType)) {
                String formOrGroupName = this.commonUtil.getFormOrGroupNames(businessKey.getFormSchemeKey(), formOrGroupKeys);
                String formOrGroupCode = this.commonUtil.getFormOrGroupCodes(businessKey.getFormSchemeKey(), formOrGroupKeys);
                if (WorkFlowType.FORM.equals((Object)startType)) {
                    param.put("reportId", formOrGroupCode);
                    param.put("reportName", formOrGroupName);
                    param.put("type", FROM);
                }
                if (WorkFlowType.GROUP.equals((Object)startType)) {
                    param.put("reportId", formOrGroupCode);
                    param.put("groupName", formOrGroupName);
                    param.put("type", GROUP);
                }
            } else {
                param.put("type", ENTITY);
            }
            String period = businessKey.getPeriod();
            param.put("period", period);
            String periodTitle = this.date(formSchemeKey, period);
            param.put("periodTitle", periodTitle);
            DimensionValueSet dimension = this.dimensionUtil.buildDimension(businessKey);
            dimension.setValue("DATATIME", (Object)period);
            List<IEntityRow> entityData = this.entityHelper.getEntityRow(formSchemeKey, dimension);
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

    public String getChineseDefaultModel(String formSchemeKey, Map<String, String> param, String contentDesc, boolean sendMail) {
        NpContextImpl chineseContext = this.buildChineseContext();
        NpContextHolder.setContext((NpContext)chineseContext);
        return this.defaultModel(formSchemeKey, param, contentDesc, sendMail);
    }

    private String defaultModel(String formSchemeKey, Map<String, String> param, String contentDesc, boolean sendMail) {
        String content = null;
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                TaskDefine queryTaskDefine = this.commonUtil.getTaskDefine(formScheme.getTaskKey());
                List<FormSchemeDefine> formSchemeByTask = this.commonUtil.getFormSchemeDefineByTaskKey(queryTaskDefine.getKey());
                String periodStr = param.get("period");
                String period = this.date(formSchemeKey, periodStr);
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

    public String date(String formSchemeKey, String periodStr) {
        String period = null;
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
            return periodProvider.getPeriodTitle(periodWrapper);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
            return period;
        }
    }

    public static String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (StringUtils.isEmpty((String)language) || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    public static boolean isChinese() {
        return MessageCommonParam.getLanguage().equals("zh");
    }
}

