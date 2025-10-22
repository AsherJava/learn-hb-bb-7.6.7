/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class NodeGatherGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u8282\u70b9\u6c47\u603b";
    }

    @Override
    public Double getOrder() {
        return 100.0;
    }
}

