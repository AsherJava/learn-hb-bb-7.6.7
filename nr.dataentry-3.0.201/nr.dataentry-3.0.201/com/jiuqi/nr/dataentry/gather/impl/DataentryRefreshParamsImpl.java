/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.gather.impl;

import com.jiuqi.nr.dataentry.bean.DataentryRefreshParam;
import com.jiuqi.nr.dataentry.gather.IDataentryRefreshParams;
import com.jiuqi.nr.dataentry.gather.IRegisterDataentryRefreshParams;
import com.jiuqi.nr.dataentry.paramInfo.DataEntryRefreshParams;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataentryRefreshParamsImpl
implements IDataentryRefreshParams {
    @Autowired(required=false)
    private List<IRegisterDataentryRefreshParams> dataentryRefreshParamsServices;
    public static final String FORM_REFRESH = "FORM_REFRESH";
    public static final String UNIT_REFRESH = "UNIT_REFRESH";

    @Override
    public List<DataEntryRefreshParams> addDataentryRefreshParams(DataentryRefreshParam dataentryRefreshParam) {
        ArrayList<DataEntryRefreshParams> dataEntryRefreshParamsList = new ArrayList<DataEntryRefreshParams>();
        String type = dataentryRefreshParam.getRefreshType();
        JtableContext context = dataentryRefreshParam.getContext();
        if (this.dataentryRefreshParamsServices == null || this.dataentryRefreshParamsServices.size() == 0 || type == null || context == null) {
            return dataEntryRefreshParamsList;
        }
        for (IRegisterDataentryRefreshParams dataentryRefreshParamsService : this.dataentryRefreshParamsServices) {
            Object pramaValue;
            String pramaKey;
            DataEntryRefreshParams dataEntryRefreshParams;
            IRegisterDataentryRefreshParams.RefreshType inputType = dataentryRefreshParamsService.getRefreshType();
            if (inputType == null) continue;
            if (type.equals(FORM_REFRESH)) {
                if (!inputType.toString().equals(FORM_REFRESH)) continue;
                dataEntryRefreshParams = new DataEntryRefreshParams();
                pramaKey = dataentryRefreshParamsService.getPramaKey(context);
                pramaValue = dataentryRefreshParamsService.getPramaValue(context);
                if (pramaKey == null || pramaValue == null) continue;
                dataEntryRefreshParams.setParamKey(pramaKey);
                dataEntryRefreshParams.setParamValue(pramaValue);
                dataEntryRefreshParamsList.add(dataEntryRefreshParams);
                continue;
            }
            dataEntryRefreshParams = new DataEntryRefreshParams();
            pramaKey = dataentryRefreshParamsService.getPramaKey(context);
            pramaValue = dataentryRefreshParamsService.getPramaValue(context);
            if (pramaKey == null || pramaValue == null) continue;
            dataEntryRefreshParams.setParamKey(pramaKey);
            dataEntryRefreshParams.setParamValue(pramaValue);
            dataEntryRefreshParamsList.add(dataEntryRefreshParams);
        }
        return dataEntryRefreshParamsList;
    }
}

