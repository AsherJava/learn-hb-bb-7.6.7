/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 */
package com.jiuqi.bi.publicparam.datasource.period;

import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.period.NrPeriodDataSourceProvider;
import com.jiuqi.bi.publicparam.datasource.period.PeriodParameterRenderer;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NrPeriodDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NrPeriodDataSourceProvider((NrPeriodDataSourceModel)model, this.periodEngineService, this.dataDefinitionController);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.date";
    }

    public String title() {
        return "\u62a5\u8868\u65f6\u671f";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new NrPeriodDataSourceModel();
    }

    public IParameterRenderer createParameterRenderer() {
        return new PeriodParameterRenderer();
    }
}

