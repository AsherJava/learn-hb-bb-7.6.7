/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter;

import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import java.util.List;

public interface EntityGroupAdapter {
    public List<IEntityGroup> getRootEntityGroups();

    public IEntityGroup queryEntityGroup(String var1);

    public List<IEntityGroup> getChildrenEntityGroups(String var1);

    public List<IEntityDefine> getEntitiesInGroup(String var1);
}

