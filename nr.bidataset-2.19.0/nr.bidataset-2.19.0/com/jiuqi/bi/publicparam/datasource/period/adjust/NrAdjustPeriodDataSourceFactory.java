/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 */
package com.jiuqi.bi.publicparam.datasource.period.adjust;

import com.jiuqi.bi.publicparam.datasource.period.adjust.AdjustPeriodParameterRenderer;
import com.jiuqi.bi.publicparam.datasource.period.adjust.NrAdjustPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.adjust.NrAdjustPeriodDataSourceProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrAdjustPeriodDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IAdjustPeriodService adjustPeriodService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NrAdjustPeriodDataSourceProvider((NrAdjustPeriodDataSourceModel)model, this.periodEngineService, this.dataDefinitionController, this.adjustPeriodService);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.adjustDate";
    }

    public String title() {
        return "\u62a5\u8868\u65f6\u671f&\u8c03\u6574\u671f";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new NrAdjustPeriodDataSourceModel();
    }

    public IParameterRenderer createParameterRenderer() {
        return new AdjustPeriodParameterRenderer();
    }

    public boolean isPrivate() {
        return true;
    }
}

