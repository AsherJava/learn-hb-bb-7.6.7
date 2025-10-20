/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.auth;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DefinitionAuthorityProvider {
    public boolean canReadTask(String var1);

    public boolean canWriteTask(String var1);

    public boolean canWriteTask(String var1, String var2, String var3);

    public boolean canModeling(String var1);

    public boolean canSubmitTask(String var1);

    public boolean canSubmitTask(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanSubmitTaskWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canUploadTask(String var1);

    public boolean canUploadTask(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanUploadTaskWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canAuditTask(String var1);

    public boolean canAuditTask(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanAuditTaskWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canReadOrgBoundToTask(String var1, String var2);

    public boolean canReadTaskGroup(String var1);

    public boolean canTaskGroupModeling(String var1);

    public boolean canReadFormScheme(String var1);

    public boolean canFormSchemeModeling(String var1);

    public boolean canReadFormScheme(FormSchemeDefine var1);

    public boolean canUploadFormScheme(String var1);

    public boolean canUploadFormScheme(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormSchemeWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canAuditFormScheme(String var1);

    public boolean canAuditFormScheme(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormSchemeWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canSubmitFormScheme(String var1);

    public boolean canSubmitFormScheme(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormSchemeWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canReadFormGroup(String var1);

    public boolean canReadFormGroup(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanReadFormGroupWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canFormGroupModeling(String var1);

    public boolean canUploadFormGroup(String var1);

    public boolean canUploadFormGroup(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormGroupWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canAuditFormGroup(String var1);

    public boolean canAuditFormGroup(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormGroupWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canSubmitFormGroup(String var1);

    public boolean canSubmitFormGroup(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormGroupWithDuty(List<String> var1, List<String> var2, String var3);

    public Set<String> canReadFormGroups(List<String> var1);

    public boolean canReadForm(String var1);

    public boolean canReadForm(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanReadFormWithDuty(List<String> var1, List<String> var2, String var3);

    public Set<String> canReadForms(List<String> var1);

    public boolean canFormModeling(String var1);

    public boolean canWriteForm(String var1);

    public boolean canWriteForm(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanWriteFormWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canReadForm(String var1, String var2);

    public Set<String> canReadFields(List<String> var1);

    public boolean canWriteForm(String var1, String var2);

    public boolean canUploadForm(String var1);

    public boolean canUploadForm(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanUploadFormWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canAuditForm(String var1);

    public boolean canAuditForm(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanAuditFormWithDuty(List<String> var1, List<String> var2, String var3);

    @Deprecated
    default public boolean canAduitForm(String form) {
        return this.canAuditForm(form);
    }

    public boolean canSubmitForm(String var1);

    public boolean canSubmitForm(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanSubmitFormWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canPublish(String var1);

    public boolean canPublish(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanPublishWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canReadUnPublish(String var1);

    public boolean canReadUnPublish(String var1, String var2, String var3);

    public Map<String, Map<String, Boolean>> batchQueryCanReadUnPublishWithDuty(List<String> var1, List<String> var2, String var3);

    public boolean canReadFormulaScheme(String var1);

    public boolean canReadFormulaScheme(FormulaSchemeDefine var1, FormSchemeDefine var2);

    public boolean canReadPrintScheme(String var1);

    public Set<String> getCanReadIdentityKeys(String var1);

    public Set<String> getCanReadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getFormGroupCanReadIdentityKeys(String var1);

    public Set<String> getFormGroupCanReadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getFormCanReadIdentityKeys(String var1);

    public Set<String> getFormCanReadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanUploadIdentityKeys(String var1);

    public Set<String> getCanUploadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanAuditIdentityKeys(String var1);

    public Set<String> getCanAuditIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanSubmitIdentityKeys(String var1);

    public Set<String> getCanSubmitIdentityKeys(String var1, String var2, String var3);

    public Set<String> getFormGroupCanSubmitIdentityKeys(String var1);

    public Set<String> getFormGroupCanSubmitIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanFormGroupUploadIdentityKeys(String var1);

    public Set<String> getFormGroupCanUploadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanFormGroupAuditIdentityKeys(String var1);

    public Set<String> getFormGroupCanAuditIdentityKeys(String var1, String var2, String var3);

    public Set<String> getFormCanSubmitIdentityKeys(String var1);

    public Set<String> getFormCanSubmitIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanFormUploadIdentityKeys(String var1);

    public Set<String> getFormCanUploadIdentityKeys(String var1, String var2, String var3);

    public Set<String> getCanFormAuditIdentityKeys(String var1);

    public Set<String> getFormCanAuditIdentityKeys(String var1, String var2, String var3);

    public void grantAllPrivileges(String var1);

    public void grantAllPrivilegesToFormScheme(String var1);

    public void grantAllPrivilegesToTaskGroup(String var1);

    public void grantAllPrivilegesToFormulaScheme(String var1);
}

