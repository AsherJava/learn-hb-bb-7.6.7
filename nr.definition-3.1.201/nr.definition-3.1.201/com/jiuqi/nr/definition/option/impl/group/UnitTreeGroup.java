/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.DataEntryDisplayPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;

public class UnitTreeGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new DataEntryDisplayPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u5355\u4f4d\u6811\u5206\u7ec4\u914d\u7f6e";
    }

    @Override
    public Double getOrder() {
        return 310.0;
    }

    @Override
    public boolean isCustom() {
        return true;
    }

    @Override
    public String getPluginName() {
        return "unitTreeGroup";
    }
}

