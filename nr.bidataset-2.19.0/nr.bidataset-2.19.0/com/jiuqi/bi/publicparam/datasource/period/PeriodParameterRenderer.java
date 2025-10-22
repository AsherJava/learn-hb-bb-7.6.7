/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  org.json.JSONObject
 */
package com.jiuqi.bi.publicparam.datasource.period;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.json.JSONObject;

public class PeriodParameterRenderer
extends DefaultParameterRenderer {
    public int[] getAvailableWidgetTypes(AbstractParameterDataSourceModel model) {
        return new int[]{1, 2, 3, 4};
    }

    public int getDefaultWidgetType(AbstractParameterDataSourceModel model, ParameterSelectMode selectMode) {
        if (selectMode == ParameterSelectMode.MUTIPLE) {
            return ParameterWidgetType.POPUP.value();
        }
        return ParameterWidgetType.DATEPICKER.value();
    }

    public JSONObject getRendererExtData(AbstractParameterDataSourceModel model, int widgetType) {
        if (model.getTimegranularity() == TimeGranularity.WEEK.value()) {
            INvwaSystemOptionService systemOptionService = (INvwaSystemOptionService)SpringBeanUtils.getBean(INvwaSystemOptionService.class);
            boolean isCustomWeek = false;
            String customWeek = systemOptionService.get("PERIOD_GROUP", "WEEKLY_SELECTION_MODE");
            if (StringUtils.isNotEmpty((String)customWeek)) {
                isCustomWeek = "list".equals(customWeek);
            }
            JSONObject json = new JSONObject();
            json.put("customWeek", isCustomWeek);
            return json;
        }
        return super.getRendererExtData(model, widgetType);
    }
}

