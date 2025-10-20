/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import org.json.JSONObject;

public interface IParameterRenderer {
    public int[] getAvailableWidgetTypes(AbstractParameterDataSourceModel var1);

    public int getDefaultWidgetType(AbstractParameterDataSourceModel var1, ParameterSelectMode var2);

    default public String getRendererPluginName(int widgetType) {
        return null;
    }

    default public JSONObject getRendererExtData(AbstractParameterDataSourceModel model, int widgetType) {
        return null;
    }
}

