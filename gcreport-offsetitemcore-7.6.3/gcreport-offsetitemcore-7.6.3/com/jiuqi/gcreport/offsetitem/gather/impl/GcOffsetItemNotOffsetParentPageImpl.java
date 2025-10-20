/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather.impl;

import com.jiuqi.gcreport.offsetitem.enums.TabSelectEnum;
import com.jiuqi.gcreport.offsetitem.gather.GcOffsetItemPage;

public class GcOffsetItemNotOffsetParentPageImpl
implements GcOffsetItemPage {
    public static GcOffsetItemNotOffsetParentPageImpl newInstance() {
        return new GcOffsetItemNotOffsetParentPageImpl();
    }

    @Override
    public String getPageCode() {
        return TabSelectEnum.NOT_OFFSET_PARENT_PAGE.getCode();
    }
}

