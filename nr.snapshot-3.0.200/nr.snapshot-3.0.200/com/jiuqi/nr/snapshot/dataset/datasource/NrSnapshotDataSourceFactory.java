/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider
 *  com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.snapshot.dataset.datasource;

import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.snapshot.dataset.datasource.NrSnapshotDataSourceModel;
import com.jiuqi.nr.snapshot.dataset.datasource.NrSnapshotDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NrSnapshotDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NrSnapshotDataSourceProvider(this.jdbcTemplate, this.runTimeViewController, this.runtimeDataSchemeService);
    }

    public String type() {
        return "com.jiuqi.publicparam.datasource.snapshot";
    }

    public String title() {
        return "\u62a5\u8868\u5feb\u7167";
    }

    public AbstractParameterDataSourceModel newInstance() {
        return new NrSnapshotDataSourceModel();
    }

    public IParameterRenderer createParameterRenderer() {
        return new DefaultParameterRenderer();
    }
}

