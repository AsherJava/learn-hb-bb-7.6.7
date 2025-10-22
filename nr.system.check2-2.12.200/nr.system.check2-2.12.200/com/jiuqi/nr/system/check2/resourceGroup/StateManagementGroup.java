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
public class StateManagementGroup
implements ICheckResourceGroup {
    public static final String STATE_MANAGEMENT_KEY = "group-0000-state_management";
    public static final String STATE_MANAGEMENT_TITLE = "\u72b6\u6001\u7ba1\u7406";

    public String getKey() {
        return STATE_MANAGEMENT_KEY;
    }

    public String getTitle() {
        return STATE_MANAGEMENT_TITLE;
    }

    public String getIcon() {
        return null;
    }

    public Double getOrder() {
        return 2.0;
    }
}

