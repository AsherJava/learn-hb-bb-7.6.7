/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataEngineEntityUtil
implements IEntityUpgrader {
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    public boolean isDataScheme(String tableKey) {
        return this.dataSchemeService.getDataTable(tableKey) != null;
    }
}

