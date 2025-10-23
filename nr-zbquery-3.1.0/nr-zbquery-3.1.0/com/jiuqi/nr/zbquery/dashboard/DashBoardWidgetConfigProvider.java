/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ResItem
 *  com.jiuqi.nvwa.datav.dashboard.domain.Widget
 *  com.jiuqi.nvwa.datav.dashboard.domain.WidgetConfig
 *  com.jiuqi.nvwa.datav.dashboard.exception.DashboardException
 *  com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider
 */
package com.jiuqi.nr.zbquery.dashboard;

import com.jiuqi.bi.transfer.engine.ResItem;
import com.jiuqi.nvwa.datav.dashboard.domain.Widget;
import com.jiuqi.nvwa.datav.dashboard.domain.WidgetConfig;
import com.jiuqi.nvwa.datav.dashboard.exception.DashboardException;
import com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider;
import java.util.List;
import java.util.Map;

public class DashBoardWidgetConfigProvider
implements IWidgetConfigProvider {
    public WidgetConfig createWidgetConfig() {
        return null;
    }

    public void onSaveAsAction(String newDashboardGuid, Map<String, String> fileKeyMap, WidgetConfig widgetConfig) throws DashboardException {
    }

    public List<String> getExtResourceIds(WidgetConfig widgetConfig) {
        return null;
    }

    public List<ResItem> getTransferRelatedResItems(Widget widget) throws DashboardException {
        return null;
    }
}

