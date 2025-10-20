/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;

public class GcOffsetItemSumPageImpl
implements GcOffsetItemPage {
    public static GcOffsetItemSumPageImpl newInstance() {
        return new GcOffsetItemSumPageImpl();
    }

    @Override
    public String getPageCode() {
        return TabSelectEnum.ALL_PAGE.getCode();
    }
}

