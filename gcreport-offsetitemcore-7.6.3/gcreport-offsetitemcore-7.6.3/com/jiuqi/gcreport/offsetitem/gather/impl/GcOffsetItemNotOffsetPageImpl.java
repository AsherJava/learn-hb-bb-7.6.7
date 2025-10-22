/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;

public class GcOffsetItemNotOffsetPageImpl
implements GcOffsetItemPage {
    public static GcOffsetItemNotOffsetPageImpl newInstance() {
        return new GcOffsetItemNotOffsetPageImpl();
    }

    @Override
    public String getPageCode() {
        return TabSelectEnum.NOT_OFFSET_PAGE.getCode();
    }
}

