/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.impl.internal.service;

import com.jiuqi.np.definition.impl.internal.service.EntityInfoCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class DefinitionImplHelper {
    private static EntityInfoCacheService entityInfoCacheService;

    @Autowired
    public void setEntityInfoCacheService(EntityInfoCacheService entityInfoCacheService) {
        DefinitionImplHelper.entityInfoCacheService = entityInfoCacheService;
    }

    public static String getEntityIdByColumn(String columnId) {
        return entityInfoCacheService.getEntityIdByBizColumn(columnId);
    }

    public static String getEntityIdByTable(String tableId) {
        return entityInfoCacheService.getEntityIdByTable(tableId);
    }
}

