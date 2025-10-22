/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 */
package com.jiuqi.bi.publicparam.datasource.period.adjust;

import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceProvider;
import com.jiuqi.bi.publicparam.datasource.period.adjust.NrAdjustPeriodDataSourceModel;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;

public class NrAdjustPeriodDataSourceProvider
extends NrPeriodDataSourceProvider
implements IParameterDataSourceProvider {
    private IAdjustPeriodService adjustPeriodService;

    public NrAdjustPeriodDataSourceProvider(NrAdjustPeriodDataSourceModel dataSourceModel, PeriodEngineService periodEngineService, IDataDefinitionRuntimeController dataDefinitionController, IAdjustPeriodService adjustPeriodService) {
        super(dataSourceModel, periodEngineService, dataDefinitionController);
        this.adjustPeriodService = adjustPeriodService;
    }

    @Override
    public ParameterResultset getDefaultValue(ParameterDataSourceContext context) throws ParameterException {
        ParameterResultset result = super.getDefaultValue(context);
        ParameterModel model = context.getModel();
        AbstractParameterValueConfig cfg = model.getValueConfig();
        String mode = cfg.getDefaultValueMode();
        if (result.size() == 1 && mode.equals("appoint")) {
            String bindingData = cfg.getDefaultValue().getBindingData();
            ParameterResultItem parameterResultItem = result.get(0);
            this.resetResultItemBybinding(bindingData, parameterResultItem);
        }
        return result;
    }

    @Override
    public ParameterResultset compute(ParameterDataSourceContext context, AbstractParameterValue value) throws ParameterException {
        String bindingData = value.getBindingData();
        ParameterResultset result = super.compute(context, value);
        for (ParameterResultItem item : result) {
            this.resetResultItemBybinding(bindingData, item);
        }
        return result;
    }

    private void resetResultItemBybinding(String bindingData, ParameterResultItem parameterResultItem) {
        parameterResultItem.setBinding(bindingData);
        if (bindingData != null && !"0".equals(bindingData)) {
            NrAdjustPeriodDataSourceModel adjustPeriodDataSourceModel = (NrAdjustPeriodDataSourceModel)this.dataSourceModel;
            PeriodType pt = PeriodType.fromType((int)this.dataSourceModel.getPeriodType());
            String period = TimeDimUtils.timeKeyToPeriod((String)((String)parameterResultItem.getValue()), (PeriodType)pt);
            AdjustPeriod adjustPeriod = this.adjustPeriodService.queryAdjustPeriods(adjustPeriodDataSourceModel.dataScheme, period, bindingData);
            if (adjustPeriod != null) {
                parameterResultItem.setTitle(adjustPeriod.getTitle());
            }
        }
    }
}

