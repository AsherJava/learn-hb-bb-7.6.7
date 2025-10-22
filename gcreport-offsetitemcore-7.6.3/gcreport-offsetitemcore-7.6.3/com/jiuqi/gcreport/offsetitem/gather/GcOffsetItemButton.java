/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;

public interface GcOffsetItemButton
extends GcOffSetItemAction {
    @Override
    default public String icon() {
        return null;
    }

    public boolean isVisible(QueryParamsVO var1);

    public boolean isEnable(QueryParamsVO var1);
}

