/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;

public class DefaultParameterRenderer
implements IParameterRenderer {
    @Override
    public int[] getAvailableWidgetTypes(AbstractParameterDataSourceModel model) {
        if (model.getDataType() == 2) {
            return new int[]{1, 2, 3, 4};
        }
        return new int[]{1, 2, 3};
    }

    @Override
    public int getDefaultWidgetType(AbstractParameterDataSourceModel model, ParameterSelectMode selectMode) {
        if (model.getDataType() == 1) {
            return ParameterWidgetType.DEFAULT.value();
        }
        if (selectMode == ParameterSelectMode.MUTIPLE) {
            return ParameterWidgetType.POPUP.value();
        }
        if (model.getDataType() == 2 || model.isTimekey() && model.getTimegranularity() != 6 && model.getDataType() == 6) {
            return ParameterWidgetType.DATEPICKER.value();
        }
        return ParameterWidgetType.DROPDOWN.value();
    }

    @Override
    public String getRendererPluginName(int widgetType) {
        return "dataset-widget-plugin";
    }
}

