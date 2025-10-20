/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.impl.group;

import com.jiuqi.nr.definition.option.impl.page.OtherPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class ImportExportGroup
implements TaskOptionGroup {
    @Override
    public String getPageTitle() {
        return new OtherPage().getTitle();
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u5bfc\u5165\u5bfc\u51fa";
    }

    @Override
    public Double getOrder() {
        return 100.0;
    }
}

