/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.deploy;

import java.util.HashSet;
import java.util.Set;

public class DeployItem {
    private Set<String> designTimeKeys = new HashSet<String>();
    private Set<String> runTimeKeys = new HashSet<String>();

    public DeployItem() {
    }

    public DeployItem(Set<String> defKeys, Set<String> runTimeKeys) {
        this.designTimeKeys = defKeys;
        this.runTimeKeys = runTimeKeys;
    }

    public Set<String> getDesignTimeKeys() {
        return this.designTimeKeys;
    }

    public void setDesignTimeKeys(Set<String> designTimeKeys) {
        this.designTimeKeys = designTimeKeys;
    }

    public Set<String> getRunTimeKeys() {
        return this.runTimeKeys;
    }

    public void setRunTimeKeys(Set<String> runTimeKeys) {
        this.runTimeKeys = runTimeKeys;
    }

    public void mergeDesignKeys(Set<String> designKeys) {
        if (this.designTimeKeys == null) {
            this.designTimeKeys = new HashSet<String>();
        }
        this.designTimeKeys.addAll(designKeys);
    }

    public void mergeRunTimeKeys(Set<String> keys) {
        if (this.runTimeKeys == null) {
            this.runTimeKeys = new HashSet<String>();
        }
        this.runTimeKeys.addAll(keys);
    }

    public Set<String> getRuntimeUninDesignTimeKeys() {
        HashSet<String> keys = new HashSet<String>(this.runTimeKeys);
        keys.addAll(this.designTimeKeys);
        return keys;
    }
}

