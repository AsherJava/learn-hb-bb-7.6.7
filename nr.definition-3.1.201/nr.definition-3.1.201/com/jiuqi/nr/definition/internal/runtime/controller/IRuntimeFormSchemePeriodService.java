/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import java.util.List;

public interface IRuntimeFormSchemePeriodService {
    default public SchemePeriodLinkDefine querySchemePeriodLinkBySchemeAndPeriod(String scheme, String period) {
        return this.querySchemePeriodLinkByScheme(scheme).stream().filter(p -> period.equals(p.getPeriodKey())).findFirst().orElse(null);
    }

    public List<SchemePeriodLinkDefine> querySchemePeriodLinkByScheme(String var1);
}

