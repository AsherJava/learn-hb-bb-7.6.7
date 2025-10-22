/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news.dao;

import com.jiuqi.nr.portal.news.INews;
import com.jiuqi.nr.portal.news.bean.NewsItem;

public interface INewsDao {
    public int insert(INews var1);

    public INews queryNewsItem(String var1);

    public int deleteNewsItem(String var1);

    public int updateItem(NewsItem var1);

    public INews[] queryNewsList(String var1);

    public INews[] queryAllNewsList();
}

