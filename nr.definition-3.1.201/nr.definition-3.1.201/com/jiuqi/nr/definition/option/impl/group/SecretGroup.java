/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class SecretGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new OtherPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u5bc6\u7ea7";
    }

    @Override
    public Double getOrder() {
        return 200.0;
    }
}

