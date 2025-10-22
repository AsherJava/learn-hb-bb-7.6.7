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
public class OtherGroup
implements ICheckResourceGroup {
    public static final String OTHER_GROUP_KEY = "group-0000-other_group";
    public static final String OTHER_GROUP_TITLE = "\u5176\u5b83";

    public String getKey() {
        return OTHER_GROUP_KEY;
    }

    public String getTitle() {
        return OTHER_GROUP_TITLE;
    }

    public String getIcon() {
        return null;
    }

    public Double getOrder() {
        return Double.MAX_VALUE;
    }
}

