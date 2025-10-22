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
public class CachingToolGroup
implements ICheckResourceGroup {
    public static final String CACHING_TOOL_GROUP_KEY = "group-0000-caching-tool";
    public static final String CACHING_TOOL_GROUP_TITLE = "\u7f13\u5b58\u5de5\u5177";

    public String getKey() {
        return CACHING_TOOL_GROUP_KEY;
    }

    public String getTitle() {
        return CACHING_TOOL_GROUP_TITLE;
    }

    public String getIcon() {
        return null;
    }

    public Double getOrder() {
        return 4.0;
    }
}

