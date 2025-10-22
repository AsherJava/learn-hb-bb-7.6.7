/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.page.OtherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller;

import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class EnumCheckGroup
implements TaskOptionGroup {
    public String getPageTitle() {
        return new OtherPage().getTitle();
    }

    public String getTitle() {
        return "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5";
    }

    public Double getOrder() {
        return 800.0;
    }
}

