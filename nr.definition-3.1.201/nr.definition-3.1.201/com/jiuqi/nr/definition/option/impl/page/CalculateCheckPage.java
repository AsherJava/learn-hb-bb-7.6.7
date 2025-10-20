/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.page;

import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class CalculateCheckPage
implements TaskOptionPage {
    @Override
    public String getTitle() {
        return "\u8fd0\u7b97\u5ba1\u6838";
    }

    @Override
    public Double getOrder() {
        return 200.0;
    }
}

