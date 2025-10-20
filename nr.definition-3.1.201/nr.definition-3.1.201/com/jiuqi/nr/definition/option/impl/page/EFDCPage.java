/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.page;

import com.jiuqi.nr.definition.option.spi.TaskOptionPage;
import org.springframework.stereotype.Component;

@Component
public class EFDCPage
implements TaskOptionPage {
    @Override
    public String getTitle() {
        return "\u8d26\u8868\u53d6\u6570";
    }

    @Override
    public Double getOrder() {
        return 400.0;
    }
}

