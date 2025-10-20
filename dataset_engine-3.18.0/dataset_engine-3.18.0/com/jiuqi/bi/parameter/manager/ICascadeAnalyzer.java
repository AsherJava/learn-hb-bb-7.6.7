/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.manager;

import com.jiuqi.bi.parameter.DataSourceException;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import java.util.List;

public interface ICascadeAnalyzer {
    public List<String> analyze(DataSourceModel var1, ParameterEngineEnv var2, boolean var3) throws DataSourceException;
}

