/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import java.util.HashSet;
import java.util.List;

public class EntityBatchAuthCache {
    HashSet<DimensionCacheKey> notWriteEntitys;
    boolean ignoreAuth;
    List<String> dimKeys;

    public HashSet<DimensionCacheKey> getNotWriteEntitys() {
        return this.notWriteEntitys;
    }

    public void setNotWriteEntitys(HashSet<DimensionCacheKey> notWriteEntitys) {
        this.notWriteEntitys = notWriteEntitys;
    }

    public boolean isIgnoreAuth() {
        return this.ignoreAuth;
    }

    public void setIgnoreAuth(boolean ignoreAuth) {
        this.ignoreAuth = ignoreAuth;
    }

    public List<String> getDimKeys() {
        return this.dimKeys;
    }

    public void setDimKeys(List<String> dimKeys) {
        this.dimKeys = dimKeys;
    }
}

