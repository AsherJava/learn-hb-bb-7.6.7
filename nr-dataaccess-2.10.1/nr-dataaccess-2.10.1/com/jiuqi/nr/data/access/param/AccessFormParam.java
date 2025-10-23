/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccessFormParam
implements Serializable {
    private static final long serialVersionUID = 2726543521139042237L;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection masterKey;
    private List<String> formKeys = new ArrayList<String>();
    private Set<String> ignoreAccessItems = new HashSet<String>();
    private AccessLevel.FormAccessLevel formAccessLevel = AccessLevel.FormAccessLevel.FORM_READ;
    private Boolean containGroup = Boolean.FALSE;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getCollectionMasterKey() {
        return this.masterKey;
    }

    public void setCollectionMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public AccessLevel.FormAccessLevel getFormAccessLevel() {
        return this.formAccessLevel;
    }

    public void setFormAccessLevel(AccessLevel.FormAccessLevel formAccessLevel) {
        this.formAccessLevel = formAccessLevel;
    }

    public Set<String> getIgnoreAccessItems() {
        return this.ignoreAccessItems;
    }

    public void setIgnoreAccessItems(Set<String> ignoreAccessItems) {
        this.ignoreAccessItems = ignoreAccessItems;
    }

    public Boolean isContainGroup() {
        return this.containGroup;
    }

    @Deprecated
    public void setContainGroup(Boolean containGroup) {
        this.containGroup = containGroup;
    }
}

