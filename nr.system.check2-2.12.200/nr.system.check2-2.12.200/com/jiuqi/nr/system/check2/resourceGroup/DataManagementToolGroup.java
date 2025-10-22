/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup
 */
package com.jiuqi.nr.system.check2.resourceGroup;

import com.jiuqi.nr.common.systemcheck.spi.ICheckResourceGroup;
import org.springframework.stereotype.Component;

@Component
public class DataManagementToolGroup
implements ICheckResourceGroup {
    public static final String DATA_MANAGEMENT_TOOL_GROUP_KEY = "group-0000-data-management-tool";
    public static final String DATA_MANAGEMENT_TOOL_GROUP_TITLE = "\u6570\u636e\u7ba1\u7406\u5de5\u5177";

    public String getKey() {
        return DATA_MANAGEMENT_TOOL_GROUP_KEY;
    }

    public String getTitle() {
        return DATA_MANAGEMENT_TOOL_GROUP_TITLE;
    }

    public String getIcon() {
        return null;
    }

    public Double getOrder() {
        return 1.0;
    }
}

