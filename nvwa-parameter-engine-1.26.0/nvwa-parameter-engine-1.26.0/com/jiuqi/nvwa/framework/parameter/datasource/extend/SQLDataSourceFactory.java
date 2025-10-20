/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;

public class SQLDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Override
    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new SQLDataSourceProvider();
    }

    @Override
    public String type() {
        return "com.jiuqi.bi.datasource.sql";
    }

    @Override
    public String title() {
        return "SQL\u67e5\u8be2";
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public AbstractParameterDataSourceModel newInstance() {
        return new SQLDataSourceModel();
    }
}

