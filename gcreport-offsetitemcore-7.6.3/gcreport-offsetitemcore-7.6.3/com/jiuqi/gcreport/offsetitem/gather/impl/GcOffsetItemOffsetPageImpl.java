/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;

public class GcOffsetItemOffsetPageImpl
implements GcOffsetItemPage {
    public static GcOffsetItemOffsetPageImpl newInstance() {
        return new GcOffsetItemOffsetPageImpl();
    }

    @Override
    public String getPageCode() {
        return TabSelectEnum.OFFSET_PAGE.getCode();
    }
}

