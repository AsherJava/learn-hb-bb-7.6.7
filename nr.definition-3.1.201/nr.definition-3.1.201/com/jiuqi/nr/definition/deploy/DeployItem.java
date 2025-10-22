/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.deploy;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class DeployItem {
    private Set<UUID> designTimeKeys = new HashSet<UUID>();
    private Set<UUID> runTimeKeys = new HashSet<UUID>();

    public DeployItem() {
    }

    public DeployItem(Set<UUID> defKeys, Set<UUID> runTimeKeys) {
        this.designTimeKeys = defKeys;
        this.runTimeKeys = runTimeKeys;
    }

    public Set<UUID> getDesignTimeKeys() {
        return this.designTimeKeys;
    }

    public void setDesignTimeKeys(Set<UUID> designTimeKeys) {
        this.designTimeKeys = designTimeKeys;
    }

    public Set<UUID> getRunTimeKeys() {
        return this.runTimeKeys;
    }

    public void setRunTimeKeys(Set<UUID> runTimeKeys) {
        this.runTimeKeys = runTimeKeys;
    }

    public void mergeDesignKeys(Set<UUID> designKeys) {
        if (this.designTimeKeys == null) {
            this.designTimeKeys = new HashSet<UUID>();
        }
        this.designTimeKeys.addAll(designKeys);
    }

    public void mergeRunTimeKeys(Set<UUID> keys) {
        if (this.runTimeKeys == null) {
            this.runTimeKeys = new HashSet<UUID>();
        }
        this.runTimeKeys.addAll(keys);
    }

    public Set<UUID> getRuntimeUninDesignTimeKeys() {
        HashSet<UUID> keys = new HashSet<UUID>(this.runTimeKeys);
        keys.addAll(this.designTimeKeys);
        return keys;
    }
}

