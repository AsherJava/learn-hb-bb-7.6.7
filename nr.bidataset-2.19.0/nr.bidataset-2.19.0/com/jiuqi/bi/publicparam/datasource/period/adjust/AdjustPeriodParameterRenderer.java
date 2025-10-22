/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.publicparam.datasource.period.adjust;

import com.jiuqi.bi.publicparam.datasource.period.PeriodParameterRenderer;

public class AdjustPeriodParameterRenderer
extends PeriodParameterRenderer {
    public String getRendererPluginName(int widgetType) {
        if (widgetType == 101) {
            return "nr-dataset-adjust-plugin";
        }
        return super.getRendererPluginName(widgetType);
    }
}

