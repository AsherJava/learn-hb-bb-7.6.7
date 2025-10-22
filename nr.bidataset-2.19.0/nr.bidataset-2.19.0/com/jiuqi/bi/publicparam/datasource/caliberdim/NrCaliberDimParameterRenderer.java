/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 */
package com.jiuqi.bi.publicparam.datasource.caliberdim;

import com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;

public class NrCaliberDimParameterRenderer
extends DefaultParameterRenderer {
    public int[] getAvailableWidgetTypes(AbstractParameterDataSourceModel model) {
        return new int[]{1, 2, 3, 6};
    }

    public int getDefaultWidgetType(AbstractParameterDataSourceModel model, ParameterSelectMode selectMode) {
        if (selectMode == ParameterSelectMode.SINGLE) {
            return ParameterWidgetType.DROPDOWN.value();
        }
        return ParameterWidgetType.UNITSELECTOR.value();
    }

    public String getRendererPluginName(int widgetType) {
        if (widgetType == ParameterWidgetType.UNITSELECTOR.value()) {
            return "nr-dataset-unitselector-plugin";
        }
        return super.getRendererPluginName(widgetType);
    }
}

