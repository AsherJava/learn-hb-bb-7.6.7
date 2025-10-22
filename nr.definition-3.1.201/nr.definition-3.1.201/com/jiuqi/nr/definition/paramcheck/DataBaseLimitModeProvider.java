/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider
 */
package com.jiuqi.nr.definition.paramcheck;

import com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DataBaseLimitModeProvider
implements IDataBaseLimitModeProvider {
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private boolean noDDL;

    public boolean databaseLimitMode() {
        return this.noDDL;
    }
}

