/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource.extend;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;

public class CustomListDataSourceFactory
extends AbstractParameterDataSourceFactory {
    @Override
    public IParameterDataSourceProvider create(AbstractParameterDataSourceModel model) throws ParameterException {
        return new CustomListDataSourceProvider();
    }

    @Override
    public String type() {
        return "com.jiuqi.bi.datasource.customlist";
    }

    @Override
    public String title() {
        return "\u81ea\u5b9a\u4e49\u5217\u8868";
    }

    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public AbstractParameterDataSourceModel newInstance() {
        return new CustomListDataSourceModel();
    }
}

