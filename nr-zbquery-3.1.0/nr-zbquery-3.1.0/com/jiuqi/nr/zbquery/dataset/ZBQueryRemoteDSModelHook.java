/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataSetException
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.nvwa.dataanalysis.dataset.remote.IRemoteDSModelHook
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.dataset;

import com.jiuqi.bi.dataset.DataSetException;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.nr.zbquery.dataset.ZBQueryDSModel;
import com.jiuqi.nvwa.dataanalysis.dataset.remote.IRemoteDSModelHook;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryRemoteDSModelHook
implements IRemoteDSModelHook {
    public boolean support(DSModel dsModel) {
        return dsModel instanceof ZBQueryDSModel;
    }

    public DSModel afterGetModel(DSModel dsModel) throws DataSetException {
        if (!this.support(dsModel)) {
            return dsModel;
        }
        ZBQueryDSModel zbQueryDSModel = (ZBQueryDSModel)dsModel;
        for (ParameterModel parameterModel : zbQueryDSModel.getParameterModels()) {
            this.toRemote(parameterModel);
        }
        return zbQueryDSModel;
    }

    private void toRemote(ParameterModel parameterModel) {
        if (parameterModel.getWidgetType() == ParameterWidgetType.UNITSELECTOR.value()) {
            parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
        } else if (parameterModel.getWidgetType() == ParameterWidgetType.DATEPICKER.value()) {
            if (parameterModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                parameterModel.setWidgetType(ParameterWidgetType.DROPDOWN.value());
            } else {
                parameterModel.setWidgetType(ParameterWidgetType.POPUP.value());
            }
        }
        AbstractParameterDataSourceModel dataSource = parameterModel.getDatasource();
        if (dataSource != null && "com.jiuqi.publicparam.datasource.date".equals(dataSource.getType())) {
            JSONObject config = new JSONObject();
            dataSource.toJson(config);
            config.put("isRemote", true);
            dataSource.fromJson(config);
        }
    }
}

