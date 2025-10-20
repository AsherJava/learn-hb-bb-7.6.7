/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 *  com.jiuqi.np.authz2.privilege.AuthzType
 *  com.jiuqi.np.authz2.privilege.Privilege
 *  com.jiuqi.np.authz2.privilege.PrivilegeStrategy
 *  com.jiuqi.np.authz2.privilege.extension.PrivilegeRule
 *  com.jiuqi.np.authz2.privilege.service.AuthorizationService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.np.authz2.privilege.AuthzType;
import com.jiuqi.np.authz2.privilege.Privilege;
import com.jiuqi.np.authz2.privilege.PrivilegeStrategy;
import com.jiuqi.np.authz2.privilege.extension.PrivilegeRule;
import com.jiuqi.np.authz2.privilege.service.AuthorizationService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeMetaService;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.auth.DefinitionPrivilegeRuleFactory;
import com.jiuqi.nr.definition.auth.authz2.FormSchemeResource;
import com.jiuqi.nr.definition.auth.authz2.FormulaSchemeResoucre;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.auth.authz2.TaskAuthorityConstants;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.service.TaskOrgLinkService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class TaskAuthorityService
implements DefinitionAuthorityProvider,
InitializingBean {
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private PrivilegeMetaService privilegeMetaService;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired(required=false)
    private List<DefinitionPrivilegeRuleFactory> privilegeRuleFactories;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService metaService;
    @Autowired
    private TaskOrgLinkService taskOrgLinkService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.privilegeRuleFactories == null || this.privilegeRuleFactories.size() == 1) {
            return;
        }
        this.privilegeRuleFactories.sort((t1, t2) -> t1.getOrder().compareTo(t2.getOrder()));
    }

    @Override
    public boolean canReadTask(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_read");
    }

    @Override
    public boolean canWriteTask(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_write");
    }

    @Override
    public boolean canWriteTask(String taskKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.TASK.toResourceId(taskKey), "task_privilege_write", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public boolean canModeling(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return true;
        }
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_modeling");
    }

    @Override
    public boolean canSubmitTask(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_submit");
    }

    @Override
    public boolean canSubmitTask(String taskKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.TASK.toResourceId(taskKey), "task_privilege_submit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanSubmitTaskWithDuty(List<String> taskKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.TASK, taskKey)), ResourceType.TASK, "task_privilege_submit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canUploadTask(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_upload");
    }

    @Override
    public boolean canUploadTask(String taskKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.TASK.toResourceId(taskKey), "task_privilege_upload", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanUploadTaskWithDuty(List<String> taskKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.TASK, taskKey)), ResourceType.TASK, "task_privilege_upload", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canAuditTask(String taskKey) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK.toResourceId(taskKey), "task_privilege_audit");
    }

    @Override
    public boolean canAuditTask(String taskKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.TASK.toResourceId(taskKey), "task_privilege_audit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanAuditTaskWithDuty(List<String> taskKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.TASK, taskKey)), ResourceType.TASK, "task_privilege_audit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canReadOrgBoundToTask(String taskKey, String entityId) {
        Assert.notNull((Object)taskKey, "parameter 'taskKey' must not be null.");
        Assert.notNull((Object)entityId, "parameter 'entityId' must not be null.");
        List<TaskOrgLinkDefine> links = this.taskOrgLinkService.getByTask(taskKey);
        if (CollectionUtils.isEmpty(links) || links.size() == 1) {
            return true;
        }
        List filterLink = links.stream().filter(link -> link.getEntity().equals(entityId)).collect(Collectors.toList());
        return this.canOperateTaskResource(ResourceType.TASK_ORG_STRUCTURE.toResourceId(((TaskOrgLinkDefine)filterLink.get(0)).getKey()), "task_privilege_read");
    }

    @Override
    public boolean canReadTaskGroup(String taskGroupKey) {
        Assert.notNull((Object)taskGroupKey, "parameter 'taskGroupKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.TASK_GROUP.toResourceId(taskGroupKey), "task_privilege_read");
    }

    @Override
    public boolean canTaskGroupModeling(String taskGroupKey) {
        Assert.notNull((Object)taskGroupKey, "parameter 'taskGroupKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return true;
        }
        return this.canOperateTaskResource(ResourceType.TASK_GROUP.toResourceId(taskGroupKey), "task_privilege_modeling");
    }

    @Override
    public boolean canReadFormScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_read");
    }

    @Override
    public boolean canFormSchemeModeling(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return true;
        }
        return this.canOperateTaskResource(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_modeling");
    }

    @Override
    public boolean canReadFormScheme(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        FormSchemeResource resource = new FormSchemeResource(formScheme);
        return this.privilegeService.hasAuth("task_privilege_read", identityId, (Object)resource);
    }

    @Override
    public boolean canReadFormGroup(String formGroupKey) {
        Assert.notNull((Object)formGroupKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_GROUP.toResourceId(formGroupKey), "task_privilege_read");
    }

    @Override
    public boolean canReadFormGroup(String formGroupKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formGroupKey, "parameter 'formGroupKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_GROUP.toResourceId(formGroupKey), "task_privilege_read", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanReadFormGroupWithDuty(List<String> formGroupKey, List<String> entityKeyData, String entityId) {
        Assert.notNull(formGroupKey, "parameter 'formGroupKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_GROUP, formGroupKey)), ResourceType.FORM_GROUP, "task_privilege_read", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public boolean canFormGroupModeling(String formGroupKey) {
        Assert.notNull((Object)formGroupKey, "parameter 'formGroupKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return true;
        }
        return this.canOperateTaskResource(ResourceType.FORM_GROUP.toResourceId(formGroupKey), "task_privilege_modeling");
    }

    @Override
    public boolean canUploadFormGroup(String formGroup) {
        Assert.notNull((Object)formGroup, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_GROUP.toResourceId(formGroup), "task_privilege_upload");
    }

    @Override
    public boolean canUploadFormGroup(String formGroupKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formGroupKey, "parameter 'formGroupKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_GROUP.toResourceId(formGroupKey), "task_privilege_upload", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormGroupWithDuty(List<String> formGroupKey, List<String> entityKeyData, String entityId) {
        Assert.notNull(formGroupKey, "parameter 'formGroupKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_GROUP, formGroupKey)), ResourceType.FORM_GROUP, "task_privilege_upload", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public boolean canAuditFormGroup(String formGroup) {
        Assert.notNull((Object)formGroup, "parameter 'formGroup' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_GROUP.toResourceId(formGroup), "task_privilege_audit");
    }

    @Override
    public boolean canAuditFormGroup(String formGroupKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formGroupKey, "parameter 'formGroupKey' must not be null.");
        Assert.notNull((Object)entityId, "parameter 'entityId' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_GROUP.toResourceId(formGroupKey), "task_privilege_audit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormGroupWithDuty(List<String> formGroupKey, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(formGroupKey, "parameter 'formGroupKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_GROUP, formGroupKey)), ResourceType.FORM_GROUP, "task_privilege_audit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canSubmitFormGroup(String formGroup) {
        Assert.notNull((Object)formGroup, "parameter 'formGroup' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_GROUP.toResourceId(formGroup), "task_privilege_submit");
    }

    @Override
    public boolean canSubmitFormGroup(String formGroup, String entityKeyData, String entityId) {
        Assert.notNull((Object)formGroup, "parameter 'formGroup' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_GROUP.toResourceId(formGroup), "task_privilege_submit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormGroupWithDuty(List<String> formGroup, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(formGroup, "parameter 'formGroupKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_GROUP, formGroup)), ResourceType.FORM_GROUP, "task_privilege_submit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public Set<String> canReadFormGroups(List<String> formGroups) {
        HashSet<String> resources = new HashSet<String>(formGroups);
        if (CollectionUtils.isEmpty(resources)) {
            return resources;
        }
        return this.filterResourceUnionDuty(resources, "task_privilege_read", ResourceType.FORM_GROUP);
    }

    @Override
    public boolean canReadForm(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_read");
    }

    @Override
    public boolean canReadForm(String formKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(formKey), "task_privilege_read", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanReadFormWithDuty(List<String> formKey, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(formKey, "parameter 'formKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, formKey)), ResourceType.FORM, "task_privilege_read", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public Set<String> canReadForms(List<String> formKeys) {
        HashSet<String> resources = new HashSet<String>(formKeys);
        if (CollectionUtils.isEmpty(resources)) {
            return resources;
        }
        return this.filterResourceUnionDuty(resources, "task_privilege_read", ResourceType.FORM);
    }

    @Override
    public boolean canFormModeling(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return true;
        }
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_modeling");
    }

    @Override
    public boolean canWriteForm(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_write");
    }

    @Override
    public boolean canWriteForm(String formKey, String entityKeyData, String entityID) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(formKey), "task_privilege_write", entityKeyData, this.getOrgType(entityID));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanWriteFormWithDuty(List<String> formKey, List<String> entityKeyDatas, String entityID) {
        Assert.notNull(formKey, "parameter 'formKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, formKey)), ResourceType.FORM, "task_privilege_write", entityKeyDatas, this.getOrgType(entityID));
    }

    @Override
    public boolean canReadForm(String formKey, String identity) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_read", identity);
    }

    @Override
    public boolean canWriteForm(String formKey, String identity) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_write", identity);
    }

    @Override
    public boolean canUploadForm(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_upload");
    }

    @Override
    public boolean canUploadForm(String form, String entityKeyData, String entityId) {
        Assert.notNull((Object)form, "parameter 'form' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(form), "task_privilege_upload", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormWithDuty(List<String> form, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(form, "parameter 'form' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, form)), ResourceType.FORM, "task_privilege_upload", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canAuditForm(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_audit");
    }

    @Override
    public boolean canAuditForm(String form, String entityKeyData, String entityId) {
        Assert.notNull((Object)form, "parameter 'form' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(form), "task_privilege_audit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormWithDuty(List<String> form, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(form, "parameter 'form' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, form)), ResourceType.FORM, "task_privilege_audit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canSubmitForm(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_submit");
    }

    @Override
    public boolean canSubmitForm(String form, String entityKeyData, String entityId) {
        Assert.notNull((Object)form, "parameter 'form' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(form), "task_privilege_submit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormWithDuty(List<String> form, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(form, "parameter 'form' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, form)), ResourceType.FORM, "task_privilege_submit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canReadFormulaScheme(String formulaSchemeKey) {
        Assert.notNull((Object)formulaSchemeKey, "parameter 'formulaSchemeKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORMULA_SCHEME.toResourceId(formulaSchemeKey), "task_privilege_read");
    }

    @Override
    public boolean canReadFormulaScheme(FormulaSchemeDefine formulaScheme, FormSchemeDefine formScheme) {
        Assert.notNull((Object)formulaScheme, "parameter 'formulaSchemeKey' must not be null.");
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        FormulaSchemeResoucre resoucre = new FormulaSchemeResoucre(formulaScheme, formScheme);
        return this.privilegeService.hasAuth("task_privilege_read", identityId, (Object)resoucre);
    }

    @Override
    public boolean canReadPrintScheme(String printSchemeKey) {
        Assert.notNull((Object)printSchemeKey, "parameter 'printSchemeKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.PRINT_SCHEME.toResourceId(printSchemeKey), "task_privilege_read");
    }

    @Override
    public void grantAllPrivileges(String taskId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String resourceId = ResourceType.TASK.toResourceId(taskId);
        this.doGrantAll(resourceId, granteeId);
    }

    @Override
    public void grantAllPrivilegesToFormScheme(String formSchemeId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String resourceId = ResourceType.FORM_SCHEME.toResourceId(formSchemeId);
        this.doGrantAll(resourceId, granteeId);
    }

    private void doGrantAll(String resourceId, String granteeId) {
        String grantorId = "SYSTEM.ROOT";
        int privilegeType = TaskAuthorityConstants.PRIVILEGE_TYPE;
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("task_privilege_read", resourceId, privilegeType, authority).grant("task_privilege_write", resourceId, privilegeType, authority).grant("task_privilege_upload", resourceId, privilegeType, authority).grant("task_privilege_audit", resourceId, privilegeType, authority).grant("task_privilege_modeling", resourceId, privilegeType, authority).grant("task_privilege_data_publish", resourceId, privilegeType, authority).grant("task_privilege_read_unpublish", resourceId, privilegeType, authority).submit();
    }

    @Override
    public void grantAllPrivilegesToTaskGroup(String taskGroupId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String grantorId = "SYSTEM.ROOT";
        String resourceId = ResourceType.TASK_GROUP.toResourceId(taskGroupId);
        int privilegeType = TaskAuthorityConstants.PRIVILEGE_TYPE;
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("task_privilege_read", resourceId, privilegeType, authority).grant("task_privilege_write", resourceId, privilegeType, authority).grant("task_privilege_submit", resourceId, privilegeType, authority).grant("task_privilege_upload", resourceId, privilegeType, authority).grant("task_privilege_audit", resourceId, privilegeType, authority).grant("task_privilege_modeling", resourceId, privilegeType, authority).submit();
    }

    @Override
    public void grantAllPrivilegesToFormulaScheme(String formulaSchemeId) {
        String granteeId = NpContextHolder.getContext().getIdentityId();
        if (this.systemIdentityService.isSystemIdentity(granteeId)) {
            return;
        }
        String grantorId = "SYSTEM.ROOT";
        String resourceId = ResourceType.FORMULA_SCHEME.toResourceId(formulaSchemeId);
        int privilegeType = TaskAuthorityConstants.PRIVILEGE_TYPE;
        Authority authority = Authority.ALLOW;
        this.authorizationService.getBatchGrantor(grantorId, granteeId).grant("task_privilege_read", resourceId, privilegeType, authority).submit();
    }

    @Override
    public Set<String> canReadFields(List<String> fieldKeys) {
        if (CollectionUtils.isEmpty(fieldKeys)) {
            return Collections.emptySet();
        }
        HashSet<String> canReadFieldsList = new HashSet<String>();
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return canReadFieldsList;
        }
        if (this.systemIdentityService.isSystemIdentity(identityId)) {
            canReadFieldsList.addAll(fieldKeys);
            return canReadFieldsList;
        }
        fieldKeys.forEach(fieldKey -> {
            Collection<String> formKeysByField = this.runTimeViewController.getFormKeysByField((String)fieldKey);
            if (CollectionUtils.isEmpty(formKeysByField)) {
                canReadFieldsList.add((String)fieldKey);
            } else {
                for (String formKey : formKeysByField) {
                    if (!this.canReadForm(formKey)) continue;
                    canReadFieldsList.add((String)fieldKey);
                    break;
                }
            }
        });
        return canReadFieldsList;
    }

    @Override
    public boolean canUploadFormScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_upload");
    }

    @Override
    public boolean canUploadFormScheme(String formSchemeKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_upload", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormSchemeWithDuty(List<String> formSchemeKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_SCHEME, formSchemeKey)), ResourceType.FORM_SCHEME, "task_privilege_upload", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canAuditFormScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_audit");
    }

    @Override
    public boolean canAuditFormScheme(String formSchemeKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_audit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormSchemeWithDuty(List<String> formSchemeKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_SCHEME, formSchemeKey)), ResourceType.FORM_SCHEME, "task_privilege_audit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canSubmitFormScheme(String formSchemeKey) {
        Assert.notNull((Object)formSchemeKey, "parameter 'taskKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_submit");
    }

    @Override
    public boolean canSubmitFormScheme(String formSchemeKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formSchemeKey, "parameter 'formSchemeKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), "task_privilege_submit", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormSchemeWithDuty(List<String> formSchemeKey, List<String> entityKeyDatas, String entityId) {
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM_SCHEME, formSchemeKey)), ResourceType.FORM_SCHEME, "task_privilege_submit", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canPublish(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_data_publish");
    }

    @Override
    public boolean canPublish(String formKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(formKey), "task_privilege_data_publish", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanPublishWithDuty(List<String> formKey, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(formKey, "parameter 'formKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, formKey)), ResourceType.FORM, "task_privilege_data_publish", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public boolean canReadUnPublish(String formKey) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResource(ResourceType.FORM.toResourceId(formKey), "task_privilege_read_unpublish");
    }

    @Override
    public boolean canReadUnPublish(String formKey, String entityKeyData, String entityId) {
        Assert.notNull((Object)formKey, "parameter 'formKey' must not be null.");
        return this.canOperateTaskResourceWithDuty(ResourceType.FORM.toResourceId(formKey), "task_privilege_read_unpublish", entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Map<String, Map<String, Boolean>> batchQueryCanReadUnPublishWithDuty(List<String> formKey, List<String> entityKeyDatas, String entityId) {
        Assert.notNull(formKey, "parameter 'formKey' must not be null.");
        return this.batchQueryOperateTaskResourceWithDuty(new ArrayList<Object>(this.batchToResourceId(ResourceType.FORM, formKey)), ResourceType.FORM, "task_privilege_read_unpublish", entityKeyDatas, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String formSchemeKey) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey));
    }

    @Override
    public Set<String> getCanReadIdentityKeys(String formSchemeKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getFormGroupCanReadIdentityKeys(String formGroupKey) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM_GROUP.toResourceId(formGroupKey));
    }

    @Override
    public Set<String> getFormGroupCanReadIdentityKeys(String formGroupKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM_GROUP.toResourceId(formGroupKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getFormCanReadIdentityKeys(String formKey) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM.toResourceId(formKey));
    }

    @Override
    public Set<String> getFormCanReadIdentityKeys(String formKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_read", ResourceType.FORM.toResourceId(formKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String formSchemeKey) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey));
    }

    @Override
    public Set<String> getCanUploadIdentityKeys(String formSchemeKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String formSchemeKey) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey));
    }

    @Override
    public Set<String> getCanAuditIdentityKeys(String formSchemeKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String formSchemeKey) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey));
    }

    @Override
    public Set<String> getCanSubmitIdentityKeys(String formSchemeKey, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM_SCHEME.toResourceId(formSchemeKey), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getFormGroupCanSubmitIdentityKeys(String formGroup) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM_GROUP.toResourceId(formGroup));
    }

    @Override
    public Set<String> getFormGroupCanSubmitIdentityKeys(String formGroup, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM_GROUP.toResourceId(formGroup), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanFormGroupUploadIdentityKeys(String formGroup) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM_GROUP.toResourceId(formGroup));
    }

    @Override
    public Set<String> getFormGroupCanUploadIdentityKeys(String formGroup, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM_GROUP.toResourceId(formGroup), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanFormGroupAuditIdentityKeys(String formGroup) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM_GROUP.toResourceId(formGroup));
    }

    @Override
    public Set<String> getFormGroupCanAuditIdentityKeys(String formGroup, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM_GROUP.toResourceId(formGroup), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getFormCanSubmitIdentityKeys(String form) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM.toResourceId(form));
    }

    @Override
    public Set<String> getFormCanSubmitIdentityKeys(String form, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_submit", ResourceType.FORM.toResourceId(form), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanFormUploadIdentityKeys(String form) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM.toResourceId(form));
    }

    @Override
    public Set<String> getFormCanUploadIdentityKeys(String form, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_upload", ResourceType.FORM.toResourceId(form), entityKeyData, this.getOrgType(entityId));
    }

    @Override
    public Set<String> getCanFormAuditIdentityKeys(String form) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM.toResourceId(form));
    }

    @Override
    public Set<String> getFormCanAuditIdentityKeys(String form, String entityKeyData, String entityId) {
        return this.getCanOperateIdentityKeys("task_privilege_audit", ResourceType.FORM.toResourceId(form), entityKeyData, this.getOrgType(entityId));
    }

    protected boolean canOperateTaskResource(String resourceId, String privilegeId) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        return this.canOperateTaskResource(resourceId, privilegeId, identityId);
    }

    protected boolean canOperateTaskResourceWithDuty(String resourceId, String privilegeId, String entityKeyData, String orgType) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return false;
        }
        return Authority.ALLOW == this.privilegeService.getAuthority(privilegeId, identityId, (Object)resourceId, entityKeyData, orgType);
    }

    protected Set<Object> batchToResourceId(ResourceType resourceType, List<String> keys) {
        HashSet<Object> resourceId = new HashSet<Object>();
        for (String resourceKey : keys) {
            resourceId.add(resourceType.toResourceId(resourceKey));
        }
        return resourceId;
    }

    protected Map<String, Map<String, Boolean>> batchQueryOperateTaskResourceWithDuty(List<Object> resourceId, ResourceType resourceType, String privilegeId, List<String> entityKeyDatas, String orgType) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return new HashMap<String, Map<String, Boolean>>();
        }
        Map authMap = this.privilegeService.getAuthoritys(privilegeId, identityId, resourceId, entityKeyDatas, orgType);
        return this.dutyAuthResultConvert(authMap, resourceType);
    }

    protected Set<String> filterResourceUnionDuty(Set<String> resources, String privilegeId, ResourceType resourceType) {
        String identityId = NpContextHolder.getContext().getIdentityId();
        if (identityId == null) {
            return new HashSet<String>();
        }
        if (this.systemIdentityService.isSystemIdentity(identityId)) {
            return resources;
        }
        Set hasAuthResources = resources.stream().filter(this::canReadForm).collect(Collectors.toSet());
        Privilege privilege = this.privilegeMetaService.getPrivilege(privilegeId);
        PrivilegeStrategy privilegeStrategy = this.privilegeMetaService.getPrivilegeStrategy(privilege.getType());
        Set hasAuthResourcesWithDuty = privilegeStrategy.getHasAuthResourceOnlyDuty(privilege, identityId, AuthzType.ACCESS);
        if (!CollectionUtils.isEmpty(hasAuthResourcesWithDuty)) {
            Set curAuthResources = hasAuthResources.stream().filter(v -> v.startsWith(resourceType.getPrefix())).collect(Collectors.toSet());
            Set hasAuthResourceObjectIds = curAuthResources.stream().map(v -> resourceType.toObjectId((String)v)).collect(Collectors.toSet());
            hasAuthResources.addAll(hasAuthResourceObjectIds);
        }
        resources.retainAll(hasAuthResources);
        return resources;
    }

    private Map<String, Map<String, Boolean>> dutyAuthResultConvert(Map<String, Map<String, Authority>> authMap, ResourceType resourceType) {
        HashMap<String, Map<String, Boolean>> result = new HashMap<String, Map<String, Boolean>>();
        if (authMap.isEmpty()) {
            return result;
        }
        HashMap<String, String> resourceKeyCache = new HashMap<String, String>(16);
        for (String entityKeyData : authMap.keySet()) {
            Map<String, Authority> authority = authMap.get(entityKeyData);
            HashMap<String, Boolean> resourceAuthMap = new HashMap<String, Boolean>();
            for (String resourceId : authority.keySet()) {
                String resourceKey = resourceKeyCache.computeIfAbsent(resourceId, e -> resourceType.toObjectId(resourceId));
                if (Authority.ALLOW == authority.get(resourceId)) {
                    resourceAuthMap.put(resourceKey, true);
                    continue;
                }
                resourceAuthMap.put(resourceKey, false);
            }
            result.put(entityKeyData, resourceAuthMap);
        }
        return result;
    }

    protected boolean canOperateTaskResource(String resourceId, String privilegeId, String identityId) {
        if (identityId == null) {
            return false;
        }
        return this.privilegeService.hasAuth(privilegeId, identityId, (Object)resourceId);
    }

    protected Set<String> getCanOperateIdentityKeys(String privilegeId, String resourceId) {
        return this.privilegeService.getHasAuthUsersAndIdentityMappUserId(privilegeId, (Object)resourceId);
    }

    protected Set<String> getCanOperateIdentityKeys(String privilegeId, String resourceId, String entityKeyData, String orgType) {
        return this.privilegeService.getHasAuthUsersAndIdentityMappUserId(privilegeId, (Object)resourceId, entityKeyData, orgType);
    }

    protected List<PrivilegeRule> buildPrivilegeRule() {
        if (this.privilegeRuleFactories == null) {
            return Collections.emptyList();
        }
        ArrayList<PrivilegeRule> privilegeRules = new ArrayList<PrivilegeRule>(this.privilegeRuleFactories.size());
        for (DefinitionPrivilegeRuleFactory factory : this.privilegeRuleFactories) {
            PrivilegeRule privilegeRule = factory.createPrivilegeRule();
            if (privilegeRule == null) continue;
            privilegeRules.add(privilegeRule);
        }
        return privilegeRules;
    }

    private String getOrgType(String entityId) {
        return this.metaService.getEntityCode(entityId);
    }
}

