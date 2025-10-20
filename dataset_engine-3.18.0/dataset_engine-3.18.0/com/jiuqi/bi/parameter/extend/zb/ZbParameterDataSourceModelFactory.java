/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.zb;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class ZbParameterDataSourceModelFactory
extends DataSourceModelFactory {
    @Override
    public ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv env, MetaBuffer buffer) {
        return null;
    }

    @Override
    public DataSourceModel createDataSourceModel() {
        return new ZbParameterDataSourceModel();
    }

    @Override
    public String getTitle() {
        return "\u6307\u6807\u53c2\u6570";
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.datasource.zbparameter";
    }
}

