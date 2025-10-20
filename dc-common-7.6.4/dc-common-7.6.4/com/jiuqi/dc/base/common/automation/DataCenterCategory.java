/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.automation.category.AbstractAutomationCategory
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationCategory
 */
package com.jiuqi.dc.base.common.automation;

import com.jiuqi.common.automation.category.AbstractAutomationCategory;
import com.jiuqi.nvwa.core.automation.annotation.AutomationCategory;

@AutomationCategory(name="datacenter", path="/\u4e00\u672c\u8d26\u4ea7\u54c1", title="\u4e00\u672c\u8d26\u4ea7\u54c1")
public class DataCenterCategory
extends AbstractAutomationCategory {
    public String categoryName() {
        return "datacenter";
    }
}

