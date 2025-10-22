/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.page.CalculateCheckPage
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.dataentry.taskOption;

import com.jiuqi.nr.definition.option.impl.page.CalculateCheckPage;
import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class ErrorDesGroup
implements TaskOptionGroup {
    public String getPageTitle() {
        return new CalculateCheckPage().getTitle();
    }

    public String getTitle() {
        return "\u5141\u8bb8\u6dfb\u52a0\u51fa\u9519\u8bf4\u660e\u7684\u5ba1\u6838\u7c7b\u578b";
    }

    public Double getOrder() {
        return 400.0;
    }
}

