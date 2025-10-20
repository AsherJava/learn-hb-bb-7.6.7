/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;

public class GcOffsetItemConnectDealDataSource
implements GcOffsetItemDataSource {
    public static GcOffsetItemConnectDealDataSource newInstance() {
        return new GcOffsetItemConnectDealDataSource();
    }

    @Override
    public String getDataSourceCode() {
        return DataSourceEnum.CONNECT_DEAL_DATA.getCode();
    }
}

