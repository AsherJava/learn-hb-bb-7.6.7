/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.portal.news.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.portal.news.INews;
import com.jiuqi.nr.portal.news.bean.NewsItem;
import com.jiuqi.nr.portal.news.dao.INewsDao;
import com.jiuqi.nr.portal.news.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl
implements INewsService {
    @Autowired
    private INewsDao newsDao;

    @Override
    public boolean addNewsItem(INews newsItem) throws Exception {
        String title = newsItem.getTitle();
        if (StringUtils.isEmpty((String)title)) {
            throw new Exception("\u65b0\u95fb\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        int insert = 0;
        insert = this.newsDao.insert(newsItem);
        return insert > 0;
    }

    @Override
    public INews queryNewsItem(String uuid) {
        INews item = this.newsDao.queryNewsItem(uuid);
        return item;
    }

    @Override
    public boolean deleteNewsItem(String uuid) {
        int delete = this.newsDao.deleteNewsItem(uuid);
        return delete > 0;
    }

    @Override
    public boolean updataItem(NewsItem item) throws Exception {
        String title = item.getTitle();
        if (StringUtils.isEmpty((String)title)) {
            throw new Exception("\u65b0\u95fb\u6807\u9898\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        int updata = 0;
        INews queryNewsItem = this.newsDao.queryNewsItem(item.getID());
        updata = queryNewsItem != null ? this.newsDao.updateItem(item) : this.newsDao.insert(item);
        return updata > 0;
    }

    @Override
    public INews[] queryNewsList(String mid) {
        INews[] items = new INews[]{};
        items = this.newsDao.queryNewsList(mid);
        return items;
    }

    @Override
    public INews[] queryAllNewsList() {
        INews[] items = new INews[]{};
        items = this.newsDao.queryAllNewsList();
        return items;
    }
}

