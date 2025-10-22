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
public class ParamToolGroup
implements ICheckResourceGroup {
    public static final String PARAM_TOOL_GROUP_KEY = "group-0000-param-tool";
    public static final String PARAM_TOOL_GROUP_TITLE = "\u53c2\u6570\u5de5\u5177";

    public String getKey() {
        return PARAM_TOOL_GROUP_KEY;
    }

    public String getTitle() {
        return PARAM_TOOL_GROUP_TITLE;
    }

    public String getIcon() {
        return null;
    }

    public Double getOrder() {
        return 3.0;
    }
}

