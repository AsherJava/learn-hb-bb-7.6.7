/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.ds.hier;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.extend.ds.hier.DSHierarchyDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class DsHierDataSourceModelFactory
extends DataSourceModelFactory {
    @Override
    public ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv env, MetaBuffer buffer) {
        return null;
    }

    @Override
    public DataSourceModel createDataSourceModel() {
        return new DSHierarchyDataSourceModel();
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u96c6\u5c42\u6b21";
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.datasource.dshier";
    }
}

