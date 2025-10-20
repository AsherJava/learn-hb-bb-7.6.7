/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.page;

import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class OtherPage
implements TaskOptionPage {
    @Override
    public String getTitle() {
        return "\u5176\u4ed6";
    }

    @Override
    public Double getOrder() {
        return 500.0;
    }
}

