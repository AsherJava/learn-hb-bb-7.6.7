/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.DataSourceEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemDataSource;

public class GcOffsetItemInputDataSource
implements GcOffsetItemDataSource {
    public static GcOffsetItemInputDataSource newInstance() {
        return new GcOffsetItemInputDataSource();
    }

    @Override
    public String getDataSourceCode() {
        return DataSourceEnum.INPUT_DATA.getCode();
    }
}

