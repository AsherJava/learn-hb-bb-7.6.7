/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrProcessAuthorityProvider {
    @Autowired
    protected DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private DimensionUtil dimensionUtil;

    public boolean canUpload(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (workFlowType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.canUploadForm(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.canUploadFormGroup(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.canUploadFormScheme(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        return true;
    }

    public boolean canAudit(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (workFlowType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.canAuditForm(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.canAuditFormGroup(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.canAuditFormScheme(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        return true;
    }

    public boolean canSubmit(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (workFlowType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.canSubmitForm(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.canSubmitFormGroup(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.canSubmitFormScheme(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        return true;
    }

    public Set<String> getCanUploadIdentityKeys(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (startType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.getCanUploadIdentityKeys(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        if (startType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.getFormCanUploadIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (startType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.getFormGroupCanUploadIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        return Collections.emptySet();
    }

    public Set<String> getCanAuditIdentityKeys(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType workFlowType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (workFlowType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.getCanAuditIdentityKeys(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.getFormCanAuditIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (workFlowType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.getFormGroupCanAuditIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        return Collections.emptySet();
    }

    public Set<String> getCanSubmitIdentityKeys(BusinessKeyInfo businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        WorkFlowType startType = formScheme.getFlowsSetting().getWordFlowType();
        String dw = formScheme.getDw();
        String dwMainDimName = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        MasterEntityInfo masterEntityInfo = businessKey.getMasterEntityInfo();
        String masterEntityKey = masterEntityInfo.getMasterEntityKey(dwMainDimName);
        if (startType == WorkFlowType.ENTITY) {
            return this.definitionAuthorityProvider.getCanSubmitIdentityKeys(businessKey.getFormSchemeKey(), masterEntityKey, dw);
        }
        if (startType == WorkFlowType.FORM) {
            return this.definitionAuthorityProvider.getFormCanSubmitIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        if (startType == WorkFlowType.GROUP) {
            return this.definitionAuthorityProvider.getFormGroupCanSubmitIdentityKeys(businessKey.getFormKey(), masterEntityKey, dw);
        }
        return Collections.emptySet();
    }

    public Set<String> getCanExecuteCurrentTaskIdentityKeys(BusinessKeyInfo businessKey, Task task) {
        List<UserAction> actions = this.getUserActions(businessKey, task, null);
        return this.canExecuteCurrentTaskIdentityKeys(businessKey, actions);
    }

    public Set<String> getCanExecuteCurrentTaskIdentityKeys(BusinessKeyInfo businessKey, UserTask userTask) {
        List<UserAction> actions = this.getUserActions(businessKey, null, userTask);
        return this.canExecuteCurrentTaskIdentityKeys(businessKey, actions);
    }

    public Boolean isCanExecuteCurrentTaskIdentity(BusinessKeyInfo businessKey, Task task, String indentityKey) {
        return this.getCanExecuteCurrentTaskIdentityKeys(businessKey, task).contains(indentityKey);
    }

    public Boolean isCanExecuteCurrentTaskIdentity(BusinessKeyInfo businessKey, Task task) {
        List<UserAction> actions = this.getUserActions(businessKey, task, null);
        boolean canExe = false;
        for (UserAction action : actions) {
            String actionId = action.getId();
            if (actionId.equals("act_submit") || actionId.equals("cus_submit")) {
                canExe = this.canSubmit(businessKey);
            } else if (actionId.equals("act_upload") || actionId.equals("cus_upload") || actionId.equals("act_return")) {
                canExe = this.canUpload(businessKey);
            } else if (actionId.equals("cus_return") || actionId.equals("cus_confirm") || actionId.equals("act_confirm") || actionId.equals("cus_reject") || actionId.equals("act_reject")) {
                canExe = this.canAudit(businessKey);
            }
            if (!canExe) continue;
            break;
        }
        return canExe;
    }

    private Set<String> canExecuteCurrentTaskIdentityKeys(BusinessKeyInfo businessKey, List<UserAction> actions) {
        Set<String> identityKeys = new HashSet<String>();
        for (UserAction action : actions) {
            String actionId = action.getId();
            if (actionId.equals("act_submit") || actionId.equals("cus_submit")) {
                if (!identityKeys.isEmpty()) {
                    identityKeys.retainAll(this.getCanSubmitIdentityKeys(businessKey));
                    continue;
                }
                identityKeys = this.getCanSubmitIdentityKeys(businessKey);
                continue;
            }
            if (actionId.equals("act_upload") || actionId.equals("cus_upload") || actionId.equals("act_return")) {
                if (!identityKeys.isEmpty()) {
                    identityKeys.retainAll(this.getCanUploadIdentityKeys(businessKey));
                    continue;
                }
                identityKeys = this.getCanUploadIdentityKeys(businessKey);
                continue;
            }
            if (!actionId.equals("cus_return") && !actionId.equals("cus_confirm") && !actionId.equals("act_confirm") && !actionId.equals("cus_reject") && !actionId.equals("act_reject")) continue;
            if (!identityKeys.isEmpty()) {
                identityKeys.retainAll(this.getCanAuditIdentityKeys(businessKey));
                continue;
            }
            identityKeys = this.getCanAuditIdentityKeys(businessKey);
        }
        return identityKeys;
    }

    private List<UserAction> getUserActions(BusinessKeyInfo businessKey, Task task, UserTask userTask) {
        if (userTask != null) {
            return userTask.getActions();
        }
        Optional<ProcessEngine> processEngine = this.nrParameterUtils.getProcessEngine(businessKey.getFormSchemeKey());
        if (processEngine.isPresent()) {
            Optional<UserTask> quserTask;
            DeployService deployService = processEngine.get().getDeployService();
            if (task != null && (quserTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKey.getFormSchemeKey())).isPresent()) {
                return quserTask.get().getActions();
            }
        }
        return Collections.emptyList();
    }
}

