/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.api.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.List;

public class LockParam
implements Serializable {
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection masterKeys;
    private List<String> formKeys;
    private boolean lock;
    private boolean ignoreAuth;
    private boolean forceUnLock = false;

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

    public DimensionCollection getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(DimensionCollection masterKeys) {
        this.masterKeys = masterKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public boolean isLock() {
        return this.lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public boolean isIgnoreAuth() {
        return this.ignoreAuth;
    }

    public void setIgnoreAuth(boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public boolean isForceUnLock() {
        return this.forceUnLock;
    }

    public void setForceUnLock(boolean forceUnLock) {
        this.forceUnLock = forceUnLock;
    }
}

