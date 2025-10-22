/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.datasource.DataSourceModel
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider
 */
package com.jiuqi.bi.publicparam.compatible;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.publicparam.compatible.NvWaDimDataSourceModel;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider;

public class NvWaDimDataSourceModelProvider
implements INvwaDataSourceModelProvider {
    public String getType() {
        return "com.jiuqi.publicparam.datasource.dimension";
    }

    public String getTitle() {
        return "\u62a5\u8868\u5b9e\u4f53";
    }

    public DataSourceModel createDataSourceModel() {
        return new NvWaDimDataSourceModel();
    }
}

