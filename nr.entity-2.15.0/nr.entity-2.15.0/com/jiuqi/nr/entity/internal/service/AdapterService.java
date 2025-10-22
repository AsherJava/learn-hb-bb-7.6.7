/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.internal.service;

import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.adapter.IEntityAuthorityAdapter;
import java.util.List;

public interface AdapterService {
    public IEntityAdapter getGroupAdapter(String var1);

    public IEntityAdapter getEntityAdapter(String var1);

    public IEntityAdapter getEntityAdapterByCode(String var1);

    public IEntityAdapter getEntityAdapterByCategory(String var1);

    public List<IEntityAdapter> getAdapterList();

    public IEntityAuthorityAdapter getEntityAuthorityAdapter(String var1);

    public boolean judgementEntityId(String var1);

    public boolean judgementGroupId(String var1);
}

