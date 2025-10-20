/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.extend.sql;

import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.cascade.MetaBuffer;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.ICascadeAnalyzer;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;

public class SqlDataSourceModelFactory
extends DataSourceModelFactory {
    @Override
    public ICascadeAnalyzer createCascadeAnalyzer(ParameterEngineEnv env, MetaBuffer buffer) {
        return null;
    }

    @Override
    public DataSourceModel createDataSourceModel() {
        return new SQLDataSourceModel();
    }

    @Override
    public String getTitle() {
        return "SQL\u67e5\u8be2";
    }

    @Override
    public String getType() {
        return "com.jiuqi.bi.datasource.sql";
    }
}

