/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessForm;
import com.jiuqi.nr.data.access.param.DimensionCombinationWrapper;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AccessFormMerge
implements IAccessFormMerge {
    private DimensionCombinationWrapper masterKeyWrapper;
    private Set<IAccessForm> accessForms;
    private Integer hashCode;

    private void resetHashCode() {
        this.hashCode = null;
    }

    public void addForm(String formKey, String taskKey, String formSchemeKey) {
        if (this.accessForms == null) {
            this.accessForms = new HashSet<IAccessForm>();
        }
        this.accessForms.add(new AccessForm(formKey, taskKey, formSchemeKey));
        this.resetHashCode();
    }

    public void addForm(IAccessForm form) {
        if (this.accessForms == null) {
            this.accessForms = new HashSet<IAccessForm>();
        }
        this.accessForms.add(form);
        this.resetHashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AccessFormMerge that = (AccessFormMerge)o;
        return Objects.equals(this.masterKeyWrapper, that.masterKeyWrapper) && Objects.equals(this.accessForms, that.accessForms);
    }

    public int hashCode() {
        if (this.hashCode == null) {
            this.hashCode = Objects.hash(this.masterKeyWrapper, this.getAccessForms());
        }
        return this.hashCode;
    }

    public void setAccessForms(Set<IAccessForm> accessForms) {
        this.accessForms = accessForms;
        this.resetHashCode();
    }

    public void setMasterKey(DimensionCombination masterKey) {
        this.masterKeyWrapper = new DimensionCombinationWrapper(masterKey);
        this.resetHashCode();
    }

    public DimensionCombinationWrapper getMasterKeyWrapper() {
        return this.masterKeyWrapper;
    }

    public void setMasterKeyWrapper(DimensionCombinationWrapper masterKeyWrapper) {
        this.masterKeyWrapper = masterKeyWrapper;
        this.resetHashCode();
    }

    @Override
    public DimensionCombination getMasterKey() {
        if (this.masterKeyWrapper == null) {
            return null;
        }
        return this.masterKeyWrapper.getMasterKey();
    }

    @Override
    public Set<IAccessForm> getAccessForms() {
        if (this.accessForms == null) {
            return Collections.emptySet();
        }
        return this.accessForms;
    }

    @Override
    public Set<String> getFormSchemeKeys() {
        HashSet<String> formSchemeKeys = new HashSet<String>();
        for (IAccessForm accessForm : this.getAccessForms()) {
            formSchemeKeys.add(accessForm.getFormSchemeKey());
        }
        return formSchemeKeys;
    }

    @Override
    public Set<String> getTaskKeys() {
        HashSet<String> taskKeys = new HashSet<String>();
        for (IAccessForm accessForm : this.getAccessForms()) {
            taskKeys.add(accessForm.getTaskKey());
        }
        return taskKeys;
    }

    @Override
    public Set<String> getFormSchemeKeysByTasKey(String taskKey) {
        HashSet<String> formSchemeKeys = new HashSet<String>();
        for (IAccessForm accessForm : this.getAccessForms()) {
            if (!accessForm.getTaskKey().equals(taskKey)) continue;
            formSchemeKeys.add(accessForm.getFormSchemeKey());
        }
        return formSchemeKeys;
    }

    @Override
    public Set<IAccessForm> getAccessFormsByTaskKey(String taskKey) {
        return this.getAccessForms().stream().filter(r -> r.getTaskKey().equals(taskKey)).collect(Collectors.toSet());
    }

    @Override
    public Set<IAccessForm> getAccessFormsByFormSchemeKey(String formSchemeKey) {
        return this.getAccessForms().stream().filter(r -> r.getFormSchemeKey().equals(formSchemeKey)).collect(Collectors.toSet());
    }

    @Override
    public IAccessForm getAccessFormsByFormKey(String formKey) {
        return this.getAccessForms().stream().filter(r -> r.getFormKey().equals(formKey)).findFirst().orElse(null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.accessForms != null) {
            for (IAccessForm accessForm : this.accessForms) {
                if (accessForm == null) continue;
                sb.append(accessForm).append(",");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
        }
        return "masterKey=" + this.masterKeyWrapper + ", accessForms=" + sb;
    }
}

