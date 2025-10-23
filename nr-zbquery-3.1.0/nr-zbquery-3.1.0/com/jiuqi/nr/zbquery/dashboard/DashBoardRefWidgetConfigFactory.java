/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.datav.dashboard.enums.RefType
 *  com.jiuqi.nvwa.datav.dashboard.factory.AbstractRefResourceWidgetConfigFactory
 *  com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider
 *  com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider
 */
package com.jiuqi.nr.zbquery.dashboard;

import com.jiuqi.nr.zbquery.dashboard.DashBoardWidgetConfigProvider;
import com.jiuqi.nvwa.datav.dashboard.enums.RefType;
import com.jiuqi.nvwa.datav.dashboard.factory.AbstractRefResourceWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider;
import com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider;
import java.util.Collections;
import java.util.List;

public class DashBoardRefWidgetConfigFactory
extends AbstractRefResourceWidgetConfigFactory {
    public RefType getRefType() {
        return RefType.ANALYZE;
    }

    public List<String> getSupportRefResourceTypes() {
        return Collections.singletonList("com.jiuqi.nr.zbquery.manage");
    }

    public String getWidgetTypeByResourceType(String resourceType) {
        return "com.jiuqi.nr.zbquery.manage";
    }

    public IRefTreeProvider getRefTreeProvider() {
        return null;
    }

    public String getType() {
        return "QueryTypeWidget";
    }

    public String getTitle() {
        return "\u6307\u6807\u67e5\u8be2\u677f\u5757";
    }

    public int getOrder() {
        return 11;
    }

    public IWidgetConfigProvider createConfigProvider() {
        return new DashBoardWidgetConfigProvider();
    }
}

