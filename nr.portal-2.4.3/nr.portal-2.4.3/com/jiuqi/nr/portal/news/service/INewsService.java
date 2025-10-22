/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news.service;

import com.jiuqi.nr.portal.news.INews;
import com.jiuqi.nr.portal.news.bean.NewsItem;

public interface INewsService {
    public boolean addNewsItem(INews var1) throws Exception;

    public INews queryNewsItem(String var1);

    public boolean deleteNewsItem(String var1);

    public boolean updataItem(NewsItem var1) throws Exception;

    public INews[] queryNewsList(String var1);

    public INews[] queryAllNewsList();
}

