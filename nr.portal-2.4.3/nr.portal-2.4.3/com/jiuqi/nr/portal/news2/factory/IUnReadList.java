/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.factory;

import com.jiuqi.nr.portal.news2.service.IQueryPortalItemsFunction;
import java.util.List;

public interface IUnReadList {
    public List<String> queryUnReadByMidAndPortalId(List<IQueryPortalItemsFunction> var1, String var2, String var3, String var4);

    public String getType();
}

