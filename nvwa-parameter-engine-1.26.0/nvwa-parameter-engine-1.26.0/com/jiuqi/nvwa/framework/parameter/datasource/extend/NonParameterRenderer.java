/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;

public class NonParameterRenderer
implements IParameterRenderer {
    @Override
    public int[] getAvailableWidgetTypes(AbstractParameterDataSourceModel model) {
        if (model.getDataType() == 2) {
            return new int[]{ParameterWidgetType.DATEPICKER.value(), ParameterWidgetType.FUZZYSEARCH.value()};
        }
        return new int[]{ParameterWidgetType.FUZZYSEARCH.value()};
    }

    @Override
    public int getDefaultWidgetType(AbstractParameterDataSourceModel model, ParameterSelectMode selectMode) {
        if (model.getDataType() == 2) {
            return ParameterWidgetType.DATEPICKER.value();
        }
        return ParameterWidgetType.FUZZYSEARCH.value();
    }

    @Override
    public String getRendererPluginName(int widgetType) {
        if (widgetType == ParameterWidgetType.DATEPICKER.value() || widgetType == ParameterWidgetType.DATEPICKER_RANGE.value()) {
            return "dataset-widget-plugin";
        }
        return null;
    }
}

