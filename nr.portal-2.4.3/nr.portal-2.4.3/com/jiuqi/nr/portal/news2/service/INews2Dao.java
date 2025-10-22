/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.service;

import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import java.util.List;

public interface INews2Dao {
    public Boolean insertNews(NewsImpl var1);

    public Boolean insertNewsRunning(NewsImpl var1);

    public Boolean updateNews(NewsImpl var1);

    public Boolean updateNewsRunning(NewsImpl var1);

    public Boolean updateViewCount(int var1, String var2, boolean var3);

    public Boolean deleteNews(String var1);

    public Boolean deleteNewsByMid(String var1, String var2);

    public NewsImpl queryNews(String var1, String var2);

    public Boolean publishNews(String var1, String var2);

    public Boolean modifyNewsOrder(String var1, Integer var2);

    public List<NewsAbstractInfo> queryAllNews();

    public List<NewsAbstractInfo> queryNewsByMidAndPortalId(String var1, String var2, String var3);

    public Boolean modifyFileOrder(String var1, Integer var2);

    public Integer getMaxOrder();
}

