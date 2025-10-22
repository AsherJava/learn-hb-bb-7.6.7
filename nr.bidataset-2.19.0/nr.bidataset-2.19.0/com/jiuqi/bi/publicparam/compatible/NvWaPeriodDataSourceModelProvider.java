/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.parameter.model.datasource.DataSourceModel
 *  com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider
 */
package com.jiuqi.bi.publicparam.compatible;

import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.publicparam.compatible.NvWaPeriodDataSourceModel;
import com.jiuqi.nvwa.bap.parameter.extend.compatible.INvwaDataSourceModelProvider;

public class NvWaPeriodDataSourceModelProvider
implements INvwaDataSourceModelProvider {
    public String getType() {
        return "com.jiuqi.publicparam.datasource.date";
    }

    public String getTitle() {
        return "\u62a5\u8868\u65f6\u671f";
    }

    public DataSourceModel createDataSourceModel() {
        return new NvWaPeriodDataSourceModel();
    }
}

