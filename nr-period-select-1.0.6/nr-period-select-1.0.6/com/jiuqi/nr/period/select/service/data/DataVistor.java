/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.service.data;

import com.jiuqi.nr.period.select.page.Page;

public interface DataVistor {
    public Page getPage();

    public DataVistor getParent();

    public Page writeData();
}

