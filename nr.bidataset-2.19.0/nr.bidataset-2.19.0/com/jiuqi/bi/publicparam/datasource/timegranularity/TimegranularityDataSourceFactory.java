/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 */
package com.jiuqi.bi.publicparam.datasource.timegranularity;

import com.jiuqi.bi.publicparam.datasource.timegranularity.TimegranularityDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.timegranularity.TimegranularityDataSourceProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimegranularityDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private PeriodEngineService periodEngineService;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new TimegranularityDataSourceProvider((TimegranularityDataSourceModel)model, this.periodEngineService);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.timegranularity";
    }

    public String title() {
        return "\u65f6\u95f4\u7c92\u5ea6";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new TimegranularityDataSourceModel();
    }
}

