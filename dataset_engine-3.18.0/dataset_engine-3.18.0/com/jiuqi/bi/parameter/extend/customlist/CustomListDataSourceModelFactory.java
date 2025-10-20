/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.customlist;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.extend.customlist.CustomListDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class CustomListDataSourceModelFactory
extends DataSourceModelFactory {
    @Override
    public ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv env, MetaBuffer buffer) {
        return null;
    }

    @Override
    public DataSourceModel createDataSourceModel() {
        CustomListDataSourceModel dataSourceModel = new CustomListDataSourceModel();
        dataSourceModel.setType(this.getType());
        return dataSourceModel;
    }

    @Override
    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u5217\u8868";
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.datasource.customlist";
    }
}

