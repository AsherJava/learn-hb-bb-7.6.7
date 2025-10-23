/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.task.api.service.entity.impl;

import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.task.api.service.entity.IEntityDefineQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityDefineQueryServiceImpl
implements IEntityDefineQueryService {
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public int isIsolation(String entityId) {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityId);
        return entityDefine.getIsolation() != null ? entityDefine.getIsolation() : 0;
    }
}

