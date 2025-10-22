/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import java.util.List;

public interface IQueryUnRead {
    public List<String> queryUnReadByMidAndPortalId(String var1, String var2, String var3);

    public Boolean quickRead(String var1, String var2, String var3);
}

