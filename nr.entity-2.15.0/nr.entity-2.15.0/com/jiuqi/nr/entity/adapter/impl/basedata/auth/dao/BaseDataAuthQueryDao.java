/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.auth.dao;

import java.util.Collection;
import java.util.Set;

public interface BaseDataAuthQueryDao {
    public Set<String> getUserId(Set<String> var1);

    public Set<String> getRelateUserIDByOrg(Collection<String> var1);
}

