/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;

public class GcOffsetItemCommonDataSource
implements GcOffsetItemDataSource {
    public static GcOffsetItemCommonDataSource newInstance() {
        return new GcOffsetItemCommonDataSource();
    }

    @Override
    public String getDataSourceCode() {
        return DataSourceEnum.COMMON_DATA.getCode();
    }
}

