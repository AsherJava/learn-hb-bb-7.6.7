/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import java.util.List;

public interface INews2Service {
    public String addNews(NewsImpl var1);

    public String addNewsRunning(NewsImpl var1);

    public Boolean modifyNews(NewsImpl var1);

    public Boolean deleteNews(String var1);

    public Boolean deleteNewsByMid(String var1, String var2);

    public NewsImpl queryNewsById(String var1, String var2);

    public NewsImpl queryNewsByIdForWeb(String var1, String var2);

    default public NewsImpl queryNewsById(String id) {
        return this.queryNewsById(id, "design");
    }

    public List<NewsAbstractInfo> queryAllNews();

    public List<NewsAbstractInfo> queryNewsByMidAndPortalId(String var1, String var2, String var3);

    default public List<NewsAbstractInfo> queryNewsByMidAndPortalId(String mid, String portalId) {
        return this.queryNewsByMidAndPortalId(mid, portalId, "design");
    }

    public Boolean publishNews(String var1, String var2);

    public Boolean modifyFileOrder(String var1, Integer var2);

    public String addNews(NewsImpl var1, Boolean var2);

    public void removeCache(String var1, String var2, String var3);
}

