/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import java.util.List;

public interface IQueryPortalItemsFunction {
    public List<IBaseInfo> queryPortalItems(String var1, String var2, String var3);

    default public Integer getOrder() {
        return 0;
    }
}

