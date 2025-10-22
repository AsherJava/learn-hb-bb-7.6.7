/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.page.GatherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.data.gather.option;

import com.jiuqi.nr.definition.option.impl.page.GatherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class NodeCheckGroup
implements TaskOptionGroup {
    public String getPageTitle() {
        return new GatherPage().getTitle();
    }

    public String getTitle() {
        return "\u8282\u70b9\u68c0\u67e5";
    }

    public Double getOrder() {
        return 200.0;
    }
}

