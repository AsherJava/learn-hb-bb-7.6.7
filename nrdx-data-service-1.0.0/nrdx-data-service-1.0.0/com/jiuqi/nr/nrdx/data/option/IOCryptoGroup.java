/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.page.OtherPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.nrdx.data.option;

import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class IOCryptoGroup
implements TaskOptionGroup {
    public static final String GROUP_TITLE = "NRDX\u5bfc\u51fa\u52a0\u5bc6";

    public String getPageTitle() {
        return new OtherPage().getTitle();
    }

    public String getTitle() {
        return GROUP_TITLE;
    }

    public Double getOrder() {
        return 150.0;
    }
}

