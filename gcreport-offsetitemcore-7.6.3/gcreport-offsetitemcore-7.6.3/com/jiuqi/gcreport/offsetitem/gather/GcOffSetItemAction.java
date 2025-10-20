/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.gather;

import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;

public interface GcOffSetItemAction {
    public String code();

    public String title();

    default public String icon() {
        return null;
    }

    public Object execute(GcOffsetExecutorVO var1);
}

