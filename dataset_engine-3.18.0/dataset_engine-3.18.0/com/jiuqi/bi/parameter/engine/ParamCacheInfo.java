/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.manager.IDataSourceDataProvider;
import com.jiuqi.bi.parameter.model.ParameterModel;

public class ParamCacheInfo {
    public ParameterModel model;
    public Object value;
    public boolean modify;
    public IDataSourceDataProvider dataSourceProvider;
    public DataSourceMetaInfo dataSourceMetaInfo;

    public ParamCacheInfo(ParameterModel model) {
        this.model = model;
    }
}

