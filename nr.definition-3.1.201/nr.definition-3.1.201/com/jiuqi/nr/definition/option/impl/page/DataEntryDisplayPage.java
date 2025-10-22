/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.page;

import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class DataEntryDisplayPage
implements TaskOptionPage {
    @Override
    public String getTitle() {
        return "\u5f55\u5165\u548c\u663e\u793a";
    }

    @Override
    public Double getOrder() {
        return 100.0;
    }
}

