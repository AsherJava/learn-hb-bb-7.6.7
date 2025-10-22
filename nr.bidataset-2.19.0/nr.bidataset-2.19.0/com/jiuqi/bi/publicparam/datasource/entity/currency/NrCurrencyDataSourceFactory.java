/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.bi.publicparam.datasource.entity.currency;

import com.jiuqi.bi.publicparam.datasource.entity.EntityParameterRenderer;
import com.jiuqi.bi.publicparam.datasource.entity.currency.NrCurrencyDataSourceModel;
import com.jiuqi.bi.publicparam.datasource.entity.currency.NrCurrencyDataSourceProvider;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NrCurrencyDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NrCurrencyDataSourceProvider((NrCurrencyDataSourceModel)model, this.entityViewRunTimeController, this.entityDataService, this.entityMetaService, this.dataDefinitionController, this.jdbcTemplate);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.currency";
    }

    public String title() {
        return "\u62a5\u8868\u5e01\u79cd";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new NrCurrencyDataSourceModel();
    }

    public IParameterRenderer createParameterRenderer() {
        return new EntityParameterRenderer();
    }

    public boolean isPrivate() {
        return true;
    }
}

