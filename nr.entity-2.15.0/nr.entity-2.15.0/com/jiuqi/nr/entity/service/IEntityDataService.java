/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.service;

import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;

public interface IEntityDataService {
    public IEntityQuery newEntityQuery();

    public IEntityModify newEntityUpdate();
}

