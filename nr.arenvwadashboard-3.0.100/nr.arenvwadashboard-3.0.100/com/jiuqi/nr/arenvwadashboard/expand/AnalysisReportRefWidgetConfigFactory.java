/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.datav.dashboard.enums.RefType
 *  com.jiuqi.nvwa.datav.dashboard.factory.AbstractRefResourceWidgetConfigFactory
 *  com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider
 *  com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider
 */
package com.jiuqi.nr.arenvwadashboard.expand;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.arenvwadashboard.expand.AnalysisReportRefTreeProvider;
import com.jiuqi.nr.arenvwadashboard.expand.AnalysisReportWidgetConfigProvider;
import com.jiuqi.nvwa.datav.dashboard.enums.RefType;
import com.jiuqi.nvwa.datav.dashboard.factory.AbstractRefResourceWidgetConfigFactory;
import com.jiuqi.nvwa.datav.dashboard.provider.IRefTreeProvider;
import com.jiuqi.nvwa.datav.dashboard.provider.IWidgetConfigProvider;
import java.util.Collections;
import java.util.List;

public class AnalysisReportRefWidgetConfigFactory
extends AbstractRefResourceWidgetConfigFactory {
    public RefType getRefType() {
        return RefType.EXTERNAL;
    }

    public List<String> getSupportRefResourceTypes() {
        return Collections.singletonList("com.jiuqi.nr.arenvwadashboard");
    }

    public String getWidgetTypeByResourceType(String resourceType) {
        return "AnalysisReportWidget";
    }

    public IRefTreeProvider getRefTreeProvider() {
        return (IRefTreeProvider)SpringBeanUtils.getBean(AnalysisReportRefTreeProvider.class);
    }

    public String getType() {
        return "AnalysisReportWidget";
    }

    public String getTitle() {
        return "\u5206\u6790\u62a5\u544a";
    }

    public int getOrder() {
        return 10;
    }

    public IWidgetConfigProvider createConfigProvider() {
        return new AnalysisReportWidgetConfigProvider();
    }
}

