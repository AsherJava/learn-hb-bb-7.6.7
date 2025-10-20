/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.DataEntryDisplayPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class DataEntryGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new DataEntryDisplayPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u5f55\u5165";
    }

    @Override
    public Double getOrder() {
        return 100.0;
    }
}

