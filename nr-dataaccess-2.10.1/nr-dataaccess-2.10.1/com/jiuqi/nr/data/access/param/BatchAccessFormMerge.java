/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessFormMerge;
import com.jiuqi.nr.data.access.param.DimensionCombinationWrapper;
import com.jiuqi.nr.data.access.param.IAccessForm;
import com.jiuqi.nr.data.access.param.IAccessFormMerge;
import com.jiuqi.nr.data.access.param.IBatchAccessFormMerge;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class BatchAccessFormMerge
implements IBatchAccessFormMerge {
    private DimensionCollection masterKeys;
    private Map<String, DimensionCollection> task2MasterKeys;
    private List<IAccessFormMerge> accessFormMerges;
    private Map<DimensionCombinationWrapper, Map<String, AccessFormMerge>> dim2Zb2Form;
    private Map<String, Set<String>> zb2forms;

    public void setDim2Zb2Form(Map<DimensionCombinationWrapper, Map<String, AccessFormMerge>> dim2Zb2Form) {
        this.dim2Zb2Form = dim2Zb2Form;
    }

    @Override
    public DimensionCollection getMasterKeys() {
        return this.masterKeys;
    }

    @Override
    public DimensionCollection getMasterKeysByTaskKey(String taskKey) {
        return this.task2MasterKeys.get(taskKey);
    }

    @Override
    public List<IAccessFormMerge> getAccessFormMerges() {
        if (this.accessFormMerges != null) {
            return this.accessFormMerges;
        }
        this.accessFormMerges = new ArrayList<IAccessFormMerge>();
        for (Map.Entry<DimensionCombinationWrapper, Map<String, AccessFormMerge>> dimEntry : this.dim2Zb2Form.entrySet()) {
            DimensionCombinationWrapper wrapper = dimEntry.getKey();
            Map<String, AccessFormMerge> value = dimEntry.getValue();
            HashSet<Set<IAccessForm>> formSet = new HashSet<Set<IAccessForm>>();
            for (IAccessFormMerge iAccessFormMerge : value.values()) {
                Set<IAccessForm> accessForms = iAccessFormMerge.getAccessForms();
                formSet.add(accessForms);
            }
            for (Set set : formSet) {
                AccessFormMerge merge = new AccessFormMerge();
                merge.setMasterKey(wrapper.getMasterKey());
                merge.setAccessForms(set);
                this.accessFormMerges.add(merge);
            }
        }
        return this.accessFormMerges;
    }

    @Override
    public Set<String> getFormSchemeKeys() {
        HashSet<String> formSchemeKeys = new HashSet<String>();
        List<IAccessFormMerge> forms = this.getAccessFormMerges();
        for (IAccessFormMerge form : forms) {
            Set<IAccessForm> accessForms = form.getAccessForms();
            for (IAccessForm accessForm : accessForms) {
                formSchemeKeys.add(accessForm.getFormSchemeKey());
            }
        }
        return formSchemeKeys;
    }

    @Override
    public Set<String> getTaskKeys() {
        HashSet<String> taskKeys = new HashSet<String>();
        List<IAccessFormMerge> forms = this.getAccessFormMerges();
        for (IAccessFormMerge form : forms) {
            Set<IAccessForm> accessForms = form.getAccessForms();
            for (IAccessForm accessForm : accessForms) {
                taskKeys.add(accessForm.getTaskKey());
            }
        }
        return taskKeys;
    }

    @Override
    public Set<String> getFormSchemeKeysByTasKey(String taskKey) {
        HashSet<String> formSchemeKeys = new HashSet<String>();
        List<IAccessFormMerge> forms = this.getAccessFormMerges();
        for (IAccessFormMerge form : forms) {
            Set<IAccessForm> accessForms = form.getAccessForms();
            for (IAccessForm accessForm : accessForms) {
                if (!accessForm.getTaskKey().equals(taskKey)) continue;
                formSchemeKeys.add(accessForm.getFormSchemeKey());
            }
        }
        return formSchemeKeys;
    }

    @Override
    public Set<IAccessForm> getAccessFormsByTaskKey(String taskKey) {
        HashSet<IAccessForm> formSet = new HashSet<IAccessForm>();
        List<IAccessFormMerge> forms = this.getAccessFormMerges();
        for (IAccessFormMerge form : forms) {
            Set<IAccessForm> formsByTaskKey = form.getAccessFormsByTaskKey(taskKey);
            if (CollectionUtils.isEmpty(formsByTaskKey)) continue;
            formSet.addAll(formsByTaskKey);
        }
        return formSet;
    }

    @Override
    public Set<IAccessForm> getAccessFormsByFormSchemeKey(String formSchemeKey) {
        HashSet<IAccessForm> formSet = new HashSet<IAccessForm>();
        List<IAccessFormMerge> forms = this.getAccessFormMerges();
        for (IAccessFormMerge form : forms) {
            Set<IAccessForm> formsByTaskKey = form.getAccessFormsByFormSchemeKey(formSchemeKey);
            if (CollectionUtils.isEmpty(formsByTaskKey)) continue;
            formSet.addAll(formsByTaskKey);
        }
        return formSet;
    }

    @Override
    public Set<String> getFormKeysByZbKey(String zbKey) {
        if (this.zb2forms != null) {
            return this.zb2forms.get(zbKey);
        }
        return null;
    }

    public void setZb2forms(Map<String, Set<String>> zb2forms) {
        this.zb2forms = zb2forms;
    }

    public void setMasterKeys(DimensionCollection masterKeys) {
        this.masterKeys = masterKeys;
    }

    public void setAccessFormMerges(List<IAccessFormMerge> accessFormMerges) {
        this.accessFormMerges = accessFormMerges;
    }

    public IAccessFormMerge getAccessFormMerge(DimensionCombination masterKey, String zbKey) {
        if (this.dim2Zb2Form == null) {
            return null;
        }
        Map<String, AccessFormMerge> cache = this.dim2Zb2Form.get(new DimensionCombinationWrapper(masterKey));
        if (cache == null) {
            return null;
        }
        return cache.get(zbKey);
    }

    public Map<String, DimensionCollection> getTask2MasterKeys() {
        return this.task2MasterKeys;
    }

    public void setTask2MasterKeys(Map<String, DimensionCollection> task2MasterKeys) {
        this.task2MasterKeys = task2MasterKeys;
    }

    public void putTaskMasterKeys(String taskKey, DimensionCollection collection) {
        if (this.task2MasterKeys == null) {
            this.task2MasterKeys = new HashMap<String, DimensionCollection>();
        }
        this.task2MasterKeys.put(taskKey, collection);
    }
}

