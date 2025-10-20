/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.DefaultParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDependAnalyzer;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.DefaultParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;

public abstract class AbstractParameterDataSourceFactory {
    public abstract IParameterDataSourceProvider create(AbstractParameterDataSourceModel var1) throws ParameterException;

    public IParameterDependAnalyzer createDependAnalyzer() throws ParameterException {
        return null;
    }

    public abstract String type();

    public abstract String title();

    public boolean isPrivate() {
        return false;
    }

    public int getOrder() {
        return 0;
    }

    public abstract AbstractParameterDataSourceModel newInstance();

    public IParameterValueFormat createValueFormat(AbstractParameterDataSourceModel model) {
        return new DefaultParameterValueFormat(model);
    }

    public IParameterRenderer createParameterRenderer() {
        return new DefaultParameterRenderer();
    }
}

