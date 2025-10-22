/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.CalculateCheckPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class CalculateGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new CalculateCheckPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u8fd0\u7b97";
    }

    @Override
    public Double getOrder() {
        return 100.0;
    }
}

