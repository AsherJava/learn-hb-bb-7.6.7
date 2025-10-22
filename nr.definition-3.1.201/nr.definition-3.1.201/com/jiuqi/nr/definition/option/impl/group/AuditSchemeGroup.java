/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.CalculateCheckPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;

public class AuditSchemeGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new CalculateCheckPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u4e0a\u62a5\u524d\u5ba1\u6838\u4e2a\u6027\u5316\u65b9\u6848";
    }

    @Override
    public Double getOrder() {
        return 300.0;
    }

    @Override
    public boolean isCustom() {
        return true;
    }

    @Override
    public String getPluginName() {
        return "auditSchemeCustom";
    }
}

