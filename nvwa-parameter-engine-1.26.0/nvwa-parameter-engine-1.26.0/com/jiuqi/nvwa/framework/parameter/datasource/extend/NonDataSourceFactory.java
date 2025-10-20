/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;

public class NonDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Override
    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new NonDataSourceProvider();
    }

    @Override
    public String type() {
        return "com.jiuqi.nvwa.parameter.ds.none";
    }

    @Override
    public String title() {
        return "\u65e0";
    }

    @Override
    public AbstractParameterDataSourceModel newInstance() {
        return new NonDataSourceModel();
    }

    @Override
    public IParameterRenderer createParameterRenderer() {
        return new NonParameterRenderer();
    }
}

