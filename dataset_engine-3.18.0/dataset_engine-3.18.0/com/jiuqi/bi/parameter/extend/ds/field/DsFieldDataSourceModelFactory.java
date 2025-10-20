/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.ds.field;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.extend.ds.field.DSFieldDataSourceModel;
import com.jiuqi.bi.parameter.extend.ds.field.DsFieldCascadeAnalyzer;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class DsFieldDataSourceModelFactory
extends DataSourceModelFactory {
    @Override
    public ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv env, MetaBuffer buffer) {
        return new DsFieldCascadeAnalyzer();
    }

    @Override
    public DataSourceModel createDataSourceModel() {
        return new DSFieldDataSourceModel();
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u96c6\u5b57\u6bb5";
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.datasource.dsfield";
    }
}

