/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.CalculateCheckPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class CheckGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new CalculateCheckPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u5ba1\u6838";
    }

    @Override
    public Double getOrder() {
        return 200.0;
    }
}

