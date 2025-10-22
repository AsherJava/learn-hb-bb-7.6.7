/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.page;

import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class GatherPage
implements TaskOptionPage {
    @Override
    public String getTitle() {
        return "\u6c47\u603b\u76f8\u5173";
    }

    @Override
    public Double getOrder() {
        return 300.0;
    }
}

