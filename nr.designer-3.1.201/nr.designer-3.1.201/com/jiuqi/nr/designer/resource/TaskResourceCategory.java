/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Authorization
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeType
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowAction
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.WorkflowStatus
 *  com.jiuqi.nr.common.resource.NrPrivilegeAuthority
 *  com.jiuqi.nr.common.resource.NrResource
 *  com.jiuqi.nr.common.resource.NrResourceCategory
 *  com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan
 *  com.jiuqi.nr.common.resource.bean.NrPrivilegeAuthorityItem
 *  com.jiuqi.nr.common.resource.bean.NrResourceGroupItem
 *  com.jiuqi.nr.common.resource.bean.NrResourceItem
 *  com.jiuqi.nr.common.resource.exception.ResourceException
 *  com.jiuqi.nr.common.resource.i18n.PrivilegeI18NService
 *  com.jiuqi.nr.definition.auth.authz2.ResourceType
 *  com.jiuqi.nr.definition.auth.authz2.TaskInhritePathProvider
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IPrintRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.facade.TaskGroupDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.service.TaskOrgLinkService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nvwa.authority.extend.DefaultResourceCategory
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition
 *  com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem
 *  com.jiuqi.nvwa.authority.resource.Resource
 *  com.jiuqi.nvwa.authority.resource.ResourceSearchResult
 *  com.jiuqi.nvwa.authority.util.AuthorityConst$Category_Type
 *  com.jiuqi.nvwa.authority.vo.GranteeInfo
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.nr.designer.resource;

import com.jiuqi.np.authz2.Authorization;
import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.PrivilegeType;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;
import com.jiuqi.nr.common.resource.NrResource;
import com.jiuqi.nr.common.resource.NrResourceCategory;
import com.jiuqi.nr.common.resource.bean.NrAuthzRightAreaPlan;
import com.jiuqi.nr.common.resource.bean.NrPrivilegeAuthorityItem;
import com.jiuqi.nr.common.resource.bean.NrResourceGroupItem;
import com.jiuqi.nr.common.resource.bean.NrResourceItem;
import com.jiuqi.nr.common.resource.exception.ResourceException;
import com.jiuqi.nr.common.resource.i18n.PrivilegeI18NService;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.auth.authz2.TaskInhritePathProvider;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IPrintRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.facade.TaskGroupDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.designer.resource.TaskAuthorityType;
import com.jiuqi.nr.designer.resource.WorkFlowPrivilegeParam;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nvwa.authority.extend.DefaultResourceCategory;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinition;
import com.jiuqi.nvwa.authority.privilege.PrivilegeDefinitionItem;
import com.jiuqi.nvwa.authority.resource.Resource;
import com.jiuqi.nvwa.authority.resource.ResourceSearchResult;
import com.jiuqi.nvwa.authority.util.AuthorityConst;
import com.jiuqi.nvwa.authority.vo.GranteeInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class TaskResourceCategory
extends DefaultResourceCategory
implements NrResourceCategory {
    public static final String TASKID = "TaskResourceCategory-1292dcf2da1e";
    private static final String TASK_GROUP_PREFIX = "tskg_";
    private static final String TASK_PREFIX = "tsk_";
    private static final String ALL_TASK_ORG_PREFIX = "atsko_";
    private static final String TASK_ORG_PREFIX = "tsko_";
    private static final String FORM_SCHEME_PREFIX = "fms_";
    static final String FORM_GROUP_PREFIX = "fmg_";
    static final String FORM_PREFIX = "fm_";
    static final String FORMULA_SCHEME_PREFIX = "fls_";
    static final String PRINT_SCHEME_PREFIX = "prts_";
    private static final String ALL_FORM_PREFIX = "afm_";
    private static final String ALL_FORMULA_SCHEME_PREFIX = "afls_";
    private static final String ALL_PRINT_SCHEME_PREFIX = "aprts_";
    private static final String SUBSTRING = "_";
    private static final String ALL_FORM_TITLE = "\u6240\u6709\u62a5\u8868";
    private static final String ALL_FORMULA_SCHEME_TITLE = "\u6240\u6709\u516c\u5f0f\u65b9\u6848";
    private static final String ALL_PRINT_SCHEME_TITLE = "\u6240\u6709\u6253\u5370\u65b9\u6848";
    private static final String ALL_TASK_GROUP_KEY = "00000000-0000-0000-0000-000000000000";
    private static final String ALL_TASK_GROUP_TITLE = "\u5168\u90e8\u4efb\u52a1";
    private static final String ALL_TASK_ORG_TITLE = "\u5355\u4f4d\u53e3\u5f84";
    private static final List<String> all = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_submit", "task_privilege_audit", "task_privilege_upload", "task_privilege_modeling", "22222222-2222-2222-2222-222222222222");
    private static final List<String> sub0 = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_modeling", "22222222-2222-2222-2222-222222222222");
    private static final List<String> sub3 = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_audit", "task_privilege_upload", "task_privilege_modeling", "22222222-2222-2222-2222-222222222222");
    private static final List<String> table0 = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_modeling", "task_privilege_data_publish", "task_privilege_read_unpublish", "22222222-2222-2222-2222-222222222222");
    private static final List<String> tableNoSubmit = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_audit", "task_privilege_upload", "task_privilege_modeling", "task_privilege_data_publish", "task_privilege_read_unpublish", "22222222-2222-2222-2222-222222222222");
    private static final List<String> table = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_submit", "task_privilege_audit", "task_privilege_upload", "task_privilege_modeling", "task_privilege_data_publish", "task_privilege_read_unpublish", "22222222-2222-2222-2222-222222222222");
    private static final List<String> sub2 = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "22222222-2222-2222-2222-222222222222");
    private static final List<String> formDutySub = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_submit", "task_privilege_audit", "task_privilege_upload");
    private static final List<String> formDutySubWithoutSubmit = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write", "task_privilege_audit", "task_privilege_upload");
    private static final List<String> formDutySubNoUpload = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "task_privilege_write");
    private static final List<String> taskOrgSub = Arrays.asList("22222222-1111-1111-1111-222222222222", "task_privilege_read", "22222222-2222-2222-2222-222222222222");
    private static final long serialVersionUID = -6343110624171105768L;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IFormulaRunTimeController iFormulaRunTimeController;
    @Autowired
    private IPrintRunTimeController iPrintRunTimeController;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private PrivilegeMetaService privilegeMetaService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeI18NService privilegeI18NService;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private TaskInhritePathProvider taskPathProvider;
    @Autowired
    private WorkflowSettingsService workflowSettingsService_2_0;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;

    public int getPrivilegeType() {
        return PrivilegeType.OBJECT_INHERIT.getValue();
    }

    public List<String> getPrivilegeIds() {
        return Collections.emptyList();
    }

    public List<NrPrivilegeAuthority> getPrivilegeAuthority() {
        return Collections.emptyList();
    }

    public String getId() {
        return TASKID;
    }

    public String getTitle() {
        return "\u4efb\u52a1";
    }

    public String getPluginName() {
        return "auth-plugin";
    }

    public int getSeq() {
        return 1;
    }

    public AuthorityConst.Category_Type getCategoryType() {
        return AuthorityConst.Category_Type.CUSTOM;
    }

    public boolean showForDuty() {
        return true;
    }

    public List<PrivilegeDefinition> getPrivilegeDefinition(GranteeInfo granteeInfo) {
        ArrayList<PrivilegeDefinition> list = new ArrayList<PrivilegeDefinition>(10);
        PrivilegeDefinitionItem item = new PrivilegeDefinitionItem();
        item.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        item.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        list.add((PrivilegeDefinition)item);
        PrivilegeDefinitionItem item1 = new PrivilegeDefinitionItem();
        item1.setPrivilegeId("22222222-3333-3333-3333-222222222222");
        item1.setPrivilegeTitle("\u6388\u6743");
        list.add((PrivilegeDefinition)item1);
        for (TaskAuthorityType value : TaskAuthorityType.values()) {
            if (("task_privilege_read_unpublish".equals(value.getId()) || "task_privilege_data_publish".equals(value.getId())) && granteeInfo != null && granteeInfo.isDuty().booleanValue()) continue;
            PrivilegeDefinitionItem privilegeDefinitionItem = new PrivilegeDefinitionItem();
            privilegeDefinitionItem.setPrivilegeId(value.getId());
            privilegeDefinitionItem.setPrivilegeTitle(value.getTitle());
            list.add((PrivilegeDefinition)privilegeDefinitionItem);
        }
        return list;
    }

    public List<NrResource> getRoot(String granteeId, int privilegeType, Object param) {
        List allTaskOtherGroups = this.iRunTimeViewController.getAllTaskGroup();
        ArrayList allTaskHasGroups = new ArrayList();
        for (TaskGroupDefine taskGroup : allTaskOtherGroups) {
            List allTasksInGroup = this.iRunTimeViewController.getAllRunTimeTasksInGroup(taskGroup.getKey());
            allTaskHasGroups.addAll(allTasksInGroup.stream().filter(taskDefine -> taskDefine != null).collect(Collectors.toList()));
        }
        List allTaskHasGroupKeys = allTaskHasGroups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        List allTaskGroup = allTaskOtherGroups.stream().filter(s -> !StringUtils.hasText(s.getParentKey())).collect(Collectors.toList());
        List<NrResource> vNrResource = allTaskGroup.stream().filter(Objects::nonNull).filter(r -> this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.TASK_GROUP.toResourceId(r.getKey()))).map(t -> this.taskGroupToResourceGroup((IMetaItem)t, granteeId, true)).collect(Collectors.toList());
        List allTaskDefines = this.iRunTimeViewController.getAllTaskDefines();
        vNrResource.addAll(allTaskDefines.stream().filter(Objects::nonNull).filter(t -> !allTaskHasGroupKeys.contains(t.getKey())).filter(r -> this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.TASK.toResourceId(r.getKey()))).map(t -> this.taskToResourceGroup((TaskDefine)t, granteeId, true, this.isDuty(param))).collect(Collectors.toList()));
        return vNrResource;
    }

    public List<NrResource> getChild(String resourceGroupId, String granteeId, Object param) {
        Map paramMap;
        try {
            paramMap = (Map)param;
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("param must be java.util.Map<String,String>");
        }
        String taskKey = null;
        String formSceKey = null;
        boolean isDuty = this.isDuty(param);
        if (paramMap != null) {
            taskKey = (String)paramMap.get("taskKey");
            formSceKey = (String)paramMap.get("formSceKey");
        }
        try {
            switch (this.getResourcePrefixTypeById(resourceGroupId)) {
                case "tskg_": {
                    ArrayList<NrResource> vNrResource = new ArrayList<NrResource>();
                    List allRunTaskList = this.iRunTimeViewController.getAllRunTimeTasksInGroup(this.getIdByResourceId(resourceGroupId));
                    vNrResource.addAll(this.iRunTimeViewController.getChildTaskGroups(this.getIdByResourceId(resourceGroupId), false).stream().filter(Objects::nonNull).filter(r -> this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.TASK_GROUP.toResourceId(r.getKey()))).map(t -> this.taskGroupToResourceGroup((IMetaItem)t, granteeId, true)).collect(Collectors.toList()));
                    vNrResource.addAll(allRunTaskList.stream().filter(Objects::nonNull).filter(r -> this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.TASK.toResourceId(r.getKey()))).map(t -> this.taskToResourceGroup((TaskDefine)t, granteeId, true, isDuty)).collect(Collectors.toList()));
                    return vNrResource;
                }
                case "tsk_": {
                    ArrayList<NrResource> resources = new ArrayList<NrResource>();
                    List orgs = this.taskOrgLinkService.getByTask(this.getIdByResourceId(resourceGroupId));
                    if (!CollectionUtils.isEmpty(orgs) && orgs.size() > 1) {
                        String taskOrgResourceId = ResourceType.ALL_TASK_ORG_STRUCTURE.toResourceId(this.getIdByResourceId(resourceGroupId));
                        resources.add((NrResource)NrResourceGroupItem.create((String)taskOrgResourceId, (String)ALL_TASK_ORG_TITLE, (int)this.getPrivilegeType(), taskOrgSub, null, (int)NrAuthzRightAreaPlan.ALL.getValue(), (Object)param));
                    }
                    resources.addAll(this.iRunTimeViewController.queryFormSchemeByTask(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(t -> {
                        boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.FORM_SCHEME.toResourceId(t.getKey()));
                        if (!hasDelegateAuth) {
                            return null;
                        }
                        HashMap<String, String> forParam = new HashMap<String, String>();
                        forParam.put("taskKey", this.getIdByResourceId(resourceGroupId));
                        forParam.put("formSceKey", t.getKey());
                        forParam.put("isDuty", String.valueOf(this.isDuty(param)));
                        List<String> taskPrivileges = this.getFormSchemePrivileges(this.getIdByResourceId(resourceGroupId), (FormSchemeDefine)t, isDuty);
                        return this.toResourceGroup((IMetaItem)t, granteeId, ResourceType.FORM_SCHEME, taskPrivileges, forParam, true);
                    }).filter(Objects::nonNull).collect(Collectors.toList()));
                    return resources;
                }
                case "atsko_": {
                    return this.taskOrgLinkService.getByTask(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(t -> {
                        boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.TASK_ORG_STRUCTURE.toResourceId(t.getKey()));
                        if (!hasDelegateAuth) {
                            return null;
                        }
                        String resourceId = ResourceType.TASK_ORG_STRUCTURE.toResourceId(t.getKey());
                        IEntityDefine entityDefine = this.entityMetaService.queryEntity(t.getEntity());
                        String resourceTitle = StringUtils.hasText(t.getEntityAlias()) ? t.getEntityAlias() : entityDefine.getTitle();
                        return new NrResourceItem(resourceId, resourceTitle, this.getPrivilegeType(), taskOrgSub, null, NrAuthzRightAreaPlan.ALL.getValue(), param);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
                case "fms_": {
                    ArrayList<NrResource> vNrResources = new ArrayList<NrResource>();
                    List<String> fspPrivileges = this.getTableGroupPrivileges(taskKey, formSceKey, isDuty);
                    String allFormResourceId = ResourceType.ALL_FORM.toResourceId(this.getIdByResourceId(resourceGroupId));
                    vNrResources.add((NrResource)NrResourceGroupItem.create((String)allFormResourceId, (String)ALL_FORM_TITLE, (int)this.getPrivilegeType(), fspPrivileges, null, (int)NrAuthzRightAreaPlan.ALL.getValue(), (Object)param));
                    String afsResourceId = ResourceType.ALL_FORMULA_SCHEME.toResourceId(this.getIdByResourceId(resourceGroupId));
                    List<String> formSchemeSubList = sub2;
                    vNrResources.add((NrResource)NrResourceGroupItem.create((String)afsResourceId, (String)ALL_FORMULA_SCHEME_TITLE, (int)this.getPrivilegeType(), formSchemeSubList, null, (int)NrAuthzRightAreaPlan.ALL.getValue(), (Object)param));
                    String apsResourceId = ResourceType.ALL_PRINT_SCHEME.toResourceId(this.getIdByResourceId(resourceGroupId));
                    vNrResources.add((NrResource)NrResourceGroupItem.create((String)apsResourceId, (String)ALL_PRINT_SCHEME_TITLE, (int)this.getPrivilegeType(), formSchemeSubList, null, (int)NrAuthzRightAreaPlan.ALL.getValue(), (Object)param));
                    return vNrResources;
                }
                case "afm_": {
                    List<String> afpPrivileges = this.getTableGroupPrivileges(taskKey, this.getIdByResourceId(resourceGroupId), isDuty);
                    return this.iRunTimeViewController.getAllFormGroupsInFormScheme(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(t -> {
                        boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.FORM_GROUP.toResourceId(t.getKey()));
                        if (!hasDelegateAuth) {
                            return null;
                        }
                        return this.toResourceGroup((IMetaItem)t, granteeId, ResourceType.FORM_GROUP, afpPrivileges, param, true);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
                case "afls_": {
                    List<String> formulaSchemeSubList = sub2;
                    return this.iFormulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(t -> {
                        boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.FORMULA_SCHEME.toResourceId(t.getKey()));
                        if (!hasDelegateAuth) {
                            return null;
                        }
                        return this.toResource((IMetaItem)t, granteeId, ResourceType.FORMULA_SCHEME, formulaSchemeSubList, param, true);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
                case "aprts_": {
                    List<String> printSchemeSubList = sub2;
                    return this.iPrintRunTimeController.getAllPrintSchemeByFormScheme(formSceKey).stream().filter(Objects::nonNull).map(r -> {
                        boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.PRINT_SCHEME.toResourceId(r.getKey()));
                        if (!hasDelegateAuth) {
                            return null;
                        }
                        return this.toResource((IMetaItem)r, granteeId, ResourceType.PRINT_SCHEME, printSchemeSubList, param, true);
                    }).filter(Objects::nonNull).collect(Collectors.toList());
                }
            }
            return this.getResourcesInFormGroup(resourceGroupId, granteeId, param, taskKey, formSceKey, isDuty);
        }
        catch (Exception e) {
            throw new ResourceException("get resource error");
        }
    }

    public List<ResourceSearchResult> searchResource(String fuzzyTitle, String key) {
        ArrayList<ResourceSearchResult> results = new ArrayList<ResourceSearchResult>();
        if (!StringUtils.hasText(key)) {
            return results;
        }
        ResourceType resourceType = ResourceType.parseFrom((String)key);
        results.add(new ResourceSearchResult(key, this.getResourceTitle(key), this.getPath(key)));
        return results;
    }

    private String getResourceTitle(String resourceId) {
        String title = "";
        ResourceType resourceType = ResourceType.parseFrom((String)resourceId);
        String objectId = resourceType.toObjectId(resourceId);
        switch (resourceType) {
            case TASK: {
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(objectId);
                if (taskDefine == null) break;
                title = taskDefine.getTitle();
                break;
            }
            case TASK_GROUP: {
                TaskGroupDefine taskGroupDefine = this.iRunTimeViewController.queryTaskGroupDefine(objectId);
                if (taskGroupDefine == null) break;
                title = taskGroupDefine.getTitle();
                break;
            }
            case FORM_SCHEME: {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(objectId);
                if (formScheme == null) break;
                title = formScheme.getTitle();
                break;
            }
            case FORM_GROUP: {
                FormGroupDefine formGroupDefine = this.iRunTimeViewController.queryFormGroup(objectId);
                if (formGroupDefine == null) break;
                title = formGroupDefine.getTitle();
                break;
            }
            case FORM: {
                FormDefine formDefine = this.iRunTimeViewController.queryFormById(objectId);
                if (formDefine == null) break;
                title = formDefine.getTitle();
                break;
            }
            case FORMULA_SCHEME: {
                FormulaSchemeDefine formulaSchemeDefine = this.iFormulaRunTimeController.queryFormulaSchemeDefine(objectId);
                if (formulaSchemeDefine == null) break;
                title = formulaSchemeDefine.getTitle();
                break;
            }
            case PRINT_SCHEME: {
                PrintTemplateSchemeDefine printTemplateSchemeDefine = null;
                try {
                    printTemplateSchemeDefine = this.iPrintRunTimeController.queryPrintTemplateSchemeDefine(objectId);
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (printTemplateSchemeDefine == null) break;
                title = printTemplateSchemeDefine.getTitle();
                break;
            }
            case ALL_FORM: {
                title = ALL_FORM_TITLE;
                break;
            }
            case ALL_FORMULA_SCHEME: {
                title = ALL_FORMULA_SCHEME_TITLE;
                break;
            }
            case ALL_PRINT_SCHEME: {
                title = ALL_PRINT_SCHEME_TITLE;
                break;
            }
            case ALL_TASK_ORG_STRUCTURE: {
                title = ALL_TASK_ORG_TITLE;
                break;
            }
            case TASK_ORG_STRUCTURE: {
                TaskOrgLinkDefine taskOrgLink = this.taskOrgLinkService.getByKey(objectId);
                if (taskOrgLink == null || StringUtils.hasText(title = taskOrgLink.getEntityAlias())) break;
                IEntityDefine entityDefine = this.entityMetaService.queryEntity(taskOrgLink.getEntity());
                title = entityDefine.getTitle();
            }
        }
        return title;
    }

    private List<String> getPath(String key) {
        ArrayList<String> path = new ArrayList<String>();
        path.add(key);
        Resource parent = this.taskPathProvider.getParent((Object)key);
        while (null != parent) {
            path.add(parent.getId());
            parent = this.taskPathProvider.getParent((Object)parent.getId());
        }
        path.add("V_GROUP_0000-0000-0000-000000000000");
        Collections.reverse(path);
        return path;
    }

    private boolean isDuty(Object param) {
        Map paramMap;
        try {
            paramMap = (Map)param;
        }
        catch (ClassCastException e) {
            throw new IllegalArgumentException("param must be java.util.Map<String,String>");
        }
        if (paramMap != null) {
            return paramMap.get("isDuty") == null ? false : paramMap.get("isDuty").toString().equals("true");
        }
        return false;
    }

    private List<NrResource> getResourcesInFormGroup(String resourceGroupId, String granteeId, Object param, String taskKey, String formSceKey, boolean isDuty) throws Exception {
        List<String> taskPrivileges;
        List<String> taskGroupPrivileges;
        if (isDuty) {
            String formGroupKey = this.getIdByResourceId(resourceGroupId);
            FormGroupDefine groupDefine = this.iRunTimeViewController.queryFormGroup(formGroupKey);
            String formSchemeKey = groupDefine == null ? "" : groupDefine.getFormSchemeKey();
            taskGroupPrivileges = this.getPrivileges(taskKey, formSchemeKey, 1, true);
            taskPrivileges = this.getPrivileges(taskKey, formSchemeKey, 2, true);
        } else {
            taskGroupPrivileges = this.getPrivileges(taskKey, formSceKey, 1, false);
            taskPrivileges = this.getPrivileges(taskKey, formSceKey, 2, false);
        }
        List<NrResource> nrResources = this.iRunTimeViewController.getChildFormGroups(this.getIdByResourceId(resourceGroupId)).stream().filter(Objects::nonNull).map(t -> {
            boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.FORM_GROUP.toResourceId(t.getKey()));
            if (!hasDelegateAuth) {
                return null;
            }
            return this.toResource((IMetaItem)t, granteeId, ResourceType.FORM_GROUP, taskGroupPrivileges, param, true);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        nrResources.addAll(this.iRunTimeViewController.getAllFormsInGroupWithoutOrder(this.getIdByResourceId(resourceGroupId), false).stream().filter(Objects::nonNull).map(t -> {
            boolean hasDelegateAuth = this.privilegeService.hasDelegateAuth("task_privilege_read", NpContextHolder.getContext().getIdentityId(), (Object)ResourceType.FORM.toResourceId(t.getKey()));
            if (!hasDelegateAuth) {
                return null;
            }
            return this.toResource((IMetaItem)t, granteeId, ResourceType.FORM, taskPrivileges, param, true);
        }).filter(Objects::nonNull).collect(Collectors.toList()));
        return nrResources;
    }

    private NrResourceGroupItem toResourceGroup(IMetaItem item, String granteeId, ResourceType resourceType, List<String> privilegeIds, Object param, boolean lazyQueryAuth) {
        String resourceId = resourceType.toResourceId(item.getKey());
        List<NrPrivilegeAuthority> nrPrivilegeAuthorities = null;
        if (!lazyQueryAuth) {
            nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, privilegeIds, resourceId);
        }
        return NrResourceGroupItem.create((String)resourceId, (String)item.getTitle(), (int)this.getPrivilegeType(), privilegeIds, nrPrivilegeAuthorities, (int)this.getAuthRightAreaPlanByResourceType(resourceType), (Object)param);
    }

    private NrResourceGroupItem taskToResourceGroup(TaskDefine taskDefine, String granteeId, boolean lazyQueryAuth, boolean isDuty) {
        List<String> taskPrivileges = this.getTaskPrivileges(taskDefine, null, 0, isDuty);
        String resourceId = ResourceType.TASK.toResourceId(taskDefine.getKey());
        List<NrPrivilegeAuthority> nrPrivilegeAuthorities = null;
        if (!lazyQueryAuth) {
            nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, taskPrivileges, resourceId);
        }
        return NrResourceGroupItem.create((String)resourceId, (String)taskDefine.getTitle(), (int)this.getPrivilegeType(), taskPrivileges, nrPrivilegeAuthorities, (int)this.getAuthRightAreaPlanByResourceType(ResourceType.TASK));
    }

    private NrResourceGroupItem taskGroupToResourceGroup(IMetaItem item, String granteeId, boolean lazyQueryAuth) {
        String resourceId = ResourceType.TASK_GROUP.toResourceId(item.getKey());
        List<NrPrivilegeAuthority> nrPrivilegeAuthorities = null;
        List<String> subList = all;
        if (!lazyQueryAuth) {
            nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, subList, resourceId);
        }
        return NrResourceGroupItem.create((String)resourceId, (String)item.getTitle(), (int)this.getPrivilegeType(), subList, nrPrivilegeAuthorities, (int)this.getAuthRightAreaPlanByResourceType(ResourceType.TASK));
    }

    private NrResourceItem toResource(IMetaItem item, String granteeId, ResourceType resourceType, List<String> privilegeIds, Object param, boolean lazyQueryAuth) {
        String resourceId = resourceType.toResourceId(item.getKey());
        List<NrPrivilegeAuthority> nrPrivilegeAuthorities = null;
        if (!lazyQueryAuth) {
            nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, privilegeIds, resourceId);
        }
        return new NrResourceItem(resourceId, item.getTitle(), this.getPrivilegeType(), privilegeIds, nrPrivilegeAuthorities, this.getAuthRightAreaPlanByResourceType(resourceType), param);
    }

    private List<NrPrivilegeAuthority> getPrivilegeAuthoritys(String granteeId, List<String> privilegeIds, String resource) {
        ArrayList<NrPrivilegeAuthority> nrPrivilegeAuthorities = new ArrayList<NrPrivilegeAuthority>();
        NrPrivilegeAuthorityItem nrPrivilegeAuthorityItemSuper = new NrPrivilegeAuthorityItem();
        nrPrivilegeAuthorities.add((NrPrivilegeAuthority)nrPrivilegeAuthorityItemSuper);
        Authority delegateAuthority = null;
        Boolean delegate = null;
        boolean accInhert = true;
        boolean isRole = this.roleService.exists(granteeId);
        for (String privilegeId : privilegeIds) {
            Authority authorityAcc;
            boolean inhert = false;
            if ("22222222-2222-2222-2222-222222222222".equals(privilegeId) || "22222222-1111-1111-1111-222222222222".equals(privilegeId)) continue;
            boolean edit = this.privilegeService.hasAuth(privilegeId, NpContextHolder.getContext().getIdentityId(), (Object)resource);
            Privilege privilege = this.privilegeMetaService.getPrivilege(privilegeId);
            String privilegeTitle = this.privilegeI18NService.getTitleByPrivilege(privilege);
            String name = privilege.getName();
            if (isRole) {
                Optional query = this.authorizationService.query(privilegeId, granteeId, resource);
                if (query.isPresent()) {
                    authorityAcc = ((Authorization)query.get()).getAccessAuthority();
                    if (delegate == null) {
                        delegateAuthority = ((Authorization)query.get()).getDelegateAuthority();
                        delegate = false;
                    }
                    accInhert = false;
                } else {
                    authorityAcc = this.privilegeService.getAuthority(privilegeId, granteeId, (Object)resource);
                    if (delegate == null) {
                        delegateAuthority = this.privilegeService.getDelegateAuthority(privilegeId, granteeId, (Object)resource);
                        delegate = true;
                    }
                    inhert = true;
                }
            } else {
                Authority authorityByIdentityCale = this.privilegeService.getAuthority(privilegeId, granteeId, (Object)resource);
                Authority authorityByIdentity = null;
                Optional query = this.authorizationService.query(privilegeId, granteeId, resource);
                if (query.isPresent()) {
                    authorityByIdentity = ((Authorization)query.get()).getAccessAuthority();
                    accInhert = false;
                }
                authorityAcc = authorityByIdentityCale;
                boolean bl = inhert = !authorityByIdentityCale.equals((Object)authorityByIdentity);
                if (delegate == null) {
                    delegateAuthority = this.privilegeService.getDelegateAuthority(privilegeId, granteeId, (Object)resource);
                    delegate = inhert;
                }
            }
            nrPrivilegeAuthorities.add((NrPrivilegeAuthority)new NrPrivilegeAuthorityItem(privilegeId, name, privilegeTitle, authorityAcc, Boolean.valueOf(inhert), Boolean.valueOf(!edit)));
        }
        nrPrivilegeAuthorities.add((NrPrivilegeAuthority)new NrPrivilegeAuthorityItem("22222222-2222-2222-2222-222222222222", "grant", "\u6388\u6743", delegateAuthority, delegate, Boolean.valueOf(false)));
        nrPrivilegeAuthorityItemSuper.setPrivilegeId("22222222-1111-1111-1111-222222222222");
        nrPrivilegeAuthorityItemSuper.setPrivilegeTitle("\u540c\u4e0a\u7ea7");
        nrPrivilegeAuthorityItemSuper.setPrivilegeName("superior");
        if (accInhert) {
            nrPrivilegeAuthorityItemSuper.setAuthority(Authority.ALLOW);
        } else {
            nrPrivilegeAuthorityItemSuper.setAuthority(Authority.UNKNOW);
        }
        nrPrivilegeAuthorityItemSuper.setReadOnly(Boolean.valueOf(false));
        return nrPrivilegeAuthorities;
    }

    private ResourceType getResourceType(String resourceId) {
        return ResourceType.parseFrom((String)resourceId);
    }

    private int getAuthRightAreaPlanByResourceType(ResourceType resourceType) {
        int authRightAreaPlan;
        switch (resourceType) {
            case TASK: 
            case TASK_GROUP: 
            case FORM_SCHEME: 
            case FORM_GROUP: {
                authRightAreaPlan = NrAuthzRightAreaPlan.ALL.getValue();
                break;
            }
            case FORM: 
            case FORMULA_SCHEME: 
            case PRINT_SCHEME: {
                authRightAreaPlan = NrAuthzRightAreaPlan.ROOT.getValue();
                break;
            }
            default: {
                authRightAreaPlan = NrAuthzRightAreaPlan.NULL.getValue();
            }
        }
        return authRightAreaPlan;
    }

    private String getResourcePrefixTypeById(String resourceId) {
        return resourceId.substring(0, resourceId.indexOf(SUBSTRING) + 1);
    }

    private String getIdByResourceId(String resourceId) {
        return resourceId.substring(resourceId.indexOf(SUBSTRING) + 1);
    }

    private List<String> getTableGroupPrivileges(@NotNull String tableKey, @NotNull String formSceKey, boolean isDuty) {
        return this.getPrivileges(tableKey, formSceKey, 1, isDuty);
    }

    private List<String> getPrivileges(@NotNull String tableKey, @NotNull String formSceKey, int type, boolean isDuty) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(tableKey);
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSceKey);
        return this.getTaskPrivileges(taskDefine, formScheme, type, isDuty);
    }

    private List<String> getFormSchemePrivileges(@NotNull String tableKey, @NotNull FormSchemeDefine formScheme, boolean isDuty) {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(tableKey);
        return this.getTaskPrivileges(taskDefine, formScheme, 0, isDuty);
    }

    private List<String> getTaskPrivileges(TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, int type, boolean isDuty) {
        TaskDefine taskDefineWithQuery;
        WorkFlowPrivilegeParam workFlowPrivilegeParam = new WorkFlowPrivilegeParam();
        workFlowPrivilegeParam.setTask(taskDefine);
        workFlowPrivilegeParam.setFormSchemeDefine(formSchemeDefine);
        if (taskDefine == null && formSchemeDefine != null && (taskDefineWithQuery = this.iRunTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey())) != null) {
            workFlowPrivilegeParam.setTask(taskDefineWithQuery);
        }
        if (workFlowPrivilegeParam.getTask() != null) {
            this.buildWorkFlowPrivilegeParam(workFlowPrivilegeParam);
        }
        if (type == 0) {
            if (workFlowPrivilegeParam.isFlow()) {
                if (workFlowPrivilegeParam.isSubmit()) {
                    return all;
                }
                return sub3;
            }
            return sub0;
        }
        if (workFlowPrivilegeParam.isFlow()) {
            if (type == 1 && (workFlowPrivilegeParam.isSubTable() || workFlowPrivilegeParam.isSubGroup())) {
                if (workFlowPrivilegeParam.isSubmit()) {
                    if (isDuty) {
                        return formDutySub;
                    }
                    return table;
                }
                if (isDuty) {
                    return formDutySubWithoutSubmit;
                }
                return tableNoSubmit;
            }
            if (type == 2 && workFlowPrivilegeParam.isSubTable()) {
                if (workFlowPrivilegeParam.isSubmit()) {
                    if (isDuty) {
                        return formDutySub;
                    }
                    return table;
                }
                if (isDuty) {
                    return formDutySubWithoutSubmit;
                }
                return tableNoSubmit;
            }
        }
        if (isDuty) {
            return formDutySubNoUpload;
        }
        return table0;
    }

    private void buildWorkFlowPrivilegeParam(WorkFlowPrivilegeParam workFlowPrivilegeParam) {
        if ("1.0".equals(workFlowPrivilegeParam.getTask().getVersion())) {
            this.buildWorkFlowPrivilegeParamForTask1(workFlowPrivilegeParam);
        } else {
            this.buildWorkFlowPrivilegeParamForTask2(workFlowPrivilegeParam);
        }
    }

    private void buildWorkFlowPrivilegeParamForTask1(WorkFlowPrivilegeParam workFlowPrivilegeParam) {
        TaskDefine taskDefine = workFlowPrivilegeParam.getTask();
        FormSchemeDefine formSchemeDefine = workFlowPrivilegeParam.getFormSchemeDefine();
        TaskFlowsDefine flowsSetting = null;
        if (taskDefine != null) {
            TaskFlowsDefine taskFlowsDefine = flowsSetting = null != taskDefine.getFlowsSetting() ? taskDefine.getFlowsSetting() : this.iRunTimeViewController.queryTaskDefine(taskDefine.getKey()).getFlowsSetting();
        }
        if (formSchemeDefine != null) {
            flowsSetting = formSchemeDefine.getFlowsSetting();
        }
        if (flowsSetting != null) {
            workFlowPrivilegeParam.setFlow(FlowsType.DEFAULT == flowsSetting.getFlowsType());
            if (workFlowPrivilegeParam.isFlow()) {
                WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
                if (wordFlowType != null) {
                    if (WorkFlowType.FORM == wordFlowType) {
                        workFlowPrivilegeParam.setSubTable(true);
                    }
                    if (WorkFlowType.GROUP == wordFlowType) {
                        workFlowPrivilegeParam.setSubGroup(true);
                    }
                }
                if (formSchemeDefine == null) {
                    try {
                        formSchemeDefine = (FormSchemeDefine)this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey()).get(0);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (formSchemeDefine != null) {
                    WorkflowStatus flowType = this.workflowSettingService.queryFlowType(formSchemeDefine.getKey());
                    if (WorkflowStatus.DEFAULT.equals((Object)flowType)) {
                        workFlowPrivilegeParam.setSubmit(flowsSetting.isUnitSubmitForCensorship());
                    } else if (WorkflowStatus.WORKFLOW.equals((Object)flowType)) {
                        workFlowPrivilegeParam.setSubmit(this.isSubmit(formSchemeDefine.getKey()));
                    }
                }
            }
        }
    }

    private void buildWorkFlowPrivilegeParamForTask2(WorkFlowPrivilegeParam workFlowPrivilegeParam) {
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService_2_0.queryWorkflowSettings(workFlowPrivilegeParam.getTask().getKey());
        if (workflowSettingsDO == null) {
            throw new RuntimeException("\u8be5\u4efb\u52a1\u4e0b\u586b\u62a5\u8ba1\u5212\u914d\u7f6e\u4e0d\u5b58\u5728\uff01");
        }
        boolean workflowEnable = workflowSettingsDO.isWorkflowEnable();
        workFlowPrivilegeParam.setFlow(workflowEnable);
        if (workflowEnable) {
            WorkflowObjectType workflowObjectType = workflowSettingsDO.getWorkflowObjectType();
            workFlowPrivilegeParam.setSubTable(workflowObjectType.equals((Object)WorkflowObjectType.FORM));
            workFlowPrivilegeParam.setSubGroup(workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP));
            workFlowPrivilegeParam.setSubmit(true);
            if (workflowSettingsDO.getWorkflowEngine().equals("jiuqi.nr.default")) {
                DefaultProcessConfig config = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowSettingsDO.getWorkflowDefine());
                WorkflowDefineTemplate workflowDefineTemplate = config.getWorkflowDefineTemplate();
                workFlowPrivilegeParam.setSubmit(workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW));
            }
        }
    }

    private boolean isSubmit(String formSchemeKey) {
        WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
        if (workFlowDefine == null) {
            return true;
        }
        List workFlowActions = this.customWorkFolwService.getRunWorkflowActionsByLinkid(workFlowDefine.getLinkid());
        if (workFlowActions != null) {
            for (WorkFlowAction workFlowAction : workFlowActions) {
                if (!"cus_submit".equals(workFlowAction.getActionCode())) continue;
                return true;
            }
        }
        return false;
    }

    public List<NrResource> getNrResourcesAuthorityById(List<String> rs, String granteeId, boolean isDuty) {
        if (CollectionUtils.isEmpty(rs)) {
            return Collections.emptyList();
        }
        ArrayList<NrResource> list = new ArrayList<NrResource>();
        block30: for (String resourceId : rs) {
            String id = this.getIdByResourceId(resourceId);
            switch (this.getResourcePrefixTypeById(resourceId)) {
                case "tskg_": {
                    TaskGroupDefine taskGroupDefine = this.iRunTimeViewController.queryTaskGroupDefine(id);
                    if (taskGroupDefine == null) break;
                    NrResourceGroupItem nrResourceGroupItem = this.taskGroupToResourceGroup((IMetaItem)taskGroupDefine, granteeId, false);
                    list.add((NrResource)nrResourceGroupItem);
                    break;
                }
                case "tsk_": {
                    TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(id);
                    if (taskDefine == null) break;
                    list.add((NrResource)this.taskToResourceGroup(taskDefine, granteeId, false, isDuty));
                    break;
                }
                case "atsko_": {
                    TaskDefine task = this.iRunTimeViewController.queryTaskDefine(id);
                    if (task == null) break;
                    String taskOrgresourceId = ResourceType.ALL_TASK_ORG_STRUCTURE.toResourceId(id);
                    List<NrPrivilegeAuthority> nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, taskOrgSub, taskOrgresourceId);
                    list.add((NrResource)NrResourceGroupItem.create((String)taskOrgresourceId, (String)ALL_TASK_ORG_TITLE, (int)this.getPrivilegeType(), taskOrgSub, nrPrivilegeAuthorities, (int)NrAuthzRightAreaPlan.ALL.getValue(), null));
                    break;
                }
                case "fms_": {
                    FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(id);
                    if (formScheme == null) break;
                    List<String> taskPrivileges = this.getFormSchemePrivileges(formScheme.getTaskKey(), formScheme, isDuty);
                    HashMap<String, String> forParam = new HashMap<String, String>();
                    forParam.put("isDuty", String.valueOf(isDuty));
                    list.add((NrResource)this.toResourceGroup((IMetaItem)formScheme, granteeId, ResourceType.FORM_SCHEME, taskPrivileges, forParam, false));
                    break;
                }
                case "afm_": {
                    FormSchemeDefine formSchemeById = this.iRunTimeViewController.getFormScheme(id);
                    if (formSchemeById == null) continue block30;
                    List<String> fspPrivileges = this.getTableGroupPrivileges(formSchemeById.getTaskKey(), id, isDuty);
                    String allFormResourceId = ResourceType.ALL_FORM.toResourceId(id);
                    List<NrPrivilegeAuthority> nrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, fspPrivileges, allFormResourceId);
                    list.add((NrResource)NrResourceGroupItem.create((String)allFormResourceId, (String)(formSchemeById.getTitle() + ":" + ALL_FORM_TITLE), (int)this.getPrivilegeType(), fspPrivileges, nrPrivilegeAuthorities, (int)NrAuthzRightAreaPlan.ALL.getValue(), null));
                    break;
                }
                case "afls_": {
                    FormSchemeDefine formSchemeById = this.iRunTimeViewController.getFormScheme(id);
                    if (formSchemeById == null) continue block30;
                    List<String> formulaSchemeSubList = sub2;
                    List<NrPrivilegeAuthority> afsNrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, formulaSchemeSubList, resourceId);
                    list.add((NrResource)NrResourceGroupItem.create((String)resourceId, (String)(formSchemeById.getTitle() + ":" + ALL_FORMULA_SCHEME_TITLE), (int)this.getPrivilegeType(), formulaSchemeSubList, afsNrPrivilegeAuthorities, (int)NrAuthzRightAreaPlan.ALL.getValue(), null));
                    break;
                }
                case "aprts_": {
                    FormSchemeDefine formSchemeById = this.iRunTimeViewController.getFormScheme(id);
                    if (formSchemeById == null) continue block30;
                    List<String> printSchemeSubList = sub2;
                    List<NrPrivilegeAuthority> apsNrPrivilegeAuthorities = this.getPrivilegeAuthoritys(granteeId, printSchemeSubList, resourceId);
                    list.add((NrResource)NrResourceGroupItem.create((String)resourceId, (String)(formSchemeById.getTitle() + ":" + ALL_PRINT_SCHEME_TITLE), (int)this.getPrivilegeType(), printSchemeSubList, apsNrPrivilegeAuthorities, (int)NrAuthzRightAreaPlan.ALL.getValue(), null));
                    break;
                }
                case "fmg_": {
                    FormGroupDefine formGroupDefine = this.iRunTimeViewController.queryFormGroup(id);
                    if (formGroupDefine == null) break;
                    String formSchemeKey = formGroupDefine.getFormSchemeKey();
                    FormSchemeDefine formSce = this.iRunTimeViewController.getFormScheme(formSchemeKey);
                    if (formSce == null) continue block30;
                    List<String> privileges = this.getPrivileges(formSce.getTaskKey(), formSce.getKey(), 1, isDuty);
                    list.add((NrResource)this.toResource((IMetaItem)formGroupDefine, granteeId, ResourceType.FORM_GROUP, privileges, null, false));
                    break;
                }
                case "fm_": {
                    FormDefine formDefine = this.iRunTimeViewController.queryFormById(id);
                    if (formDefine == null) break;
                    String formSchemeId = formDefine.getFormScheme();
                    FormSchemeDefine formSce = this.iRunTimeViewController.getFormScheme(formSchemeId);
                    if (formSce == null) continue block30;
                    List<String> privileges = this.getPrivileges(formSce.getTaskKey(), formSce.getKey(), 2, isDuty);
                    list.add((NrResource)this.toResource((IMetaItem)formDefine, granteeId, ResourceType.FORM, privileges, null, false));
                    break;
                }
                case "fls_": {
                    FormulaDefine formulaDefine = this.iFormulaRunTimeController.queryFormulaDefine(id);
                    if (formulaDefine == null) break;
                    List<String> formulaSchemeSubList = sub2;
                    list.add((NrResource)this.toResource((IMetaItem)formulaDefine, granteeId, ResourceType.FORMULA_SCHEME, formulaSchemeSubList, null, false));
                    break;
                }
                case "prts_": {
                    try {
                        PrintTemplateSchemeDefine define = this.iPrintRunTimeController.queryPrintTemplateSchemeDefine(id);
                        if (define == null) continue block30;
                        List<String> printSchemeSubList = sub2;
                        list.add((NrResource)this.toResource((IMetaItem)define, granteeId, ResourceType.PRINT_SCHEME, printSchemeSubList, null, false));
                        break;
                    }
                    catch (Exception e) {
                        throw new ResourceException((Throwable)e);
                    }
                }
                case "tsko_": {
                    TaskOrgLinkDefine taskOrg = this.taskOrgLinkService.getByKey(id);
                    if (taskOrg == null) break;
                    list.add((NrResource)this.toResource((IMetaItem)taskOrg, granteeId, ResourceType.PRINT_SCHEME, taskOrgSub, null, false));
                    break;
                }
            }
        }
        return list;
    }

    public List<NrResource> getNrResourcesAuthority(List<NrResource> rs, String granteeId, Object param) {
        if (CollectionUtils.isEmpty(rs)) {
            return Collections.emptyList();
        }
        ArrayList<NrResource> list = new ArrayList<NrResource>();
        for (NrResource r : rs) {
            if (r instanceof NrResourceItem) {
                List<NrPrivilegeAuthority> privilegeAuthoritys = this.getPrivilegeAuthoritys(granteeId, r.getPrivilegeIds(), r.getId());
                ((NrResourceItem)r).setNrPrivilegeAuthorities(privilegeAuthoritys);
                list.add(r);
                continue;
            }
            List<NrResource> byId = this.getNrResourcesAuthorityById(Collections.singletonList(r.getId()), granteeId, this.isDuty(param));
            list.addAll(byId);
        }
        return list;
    }

    public boolean supportBusinessManager(GranteeInfo granteeInfo) {
        return true;
    }
}

