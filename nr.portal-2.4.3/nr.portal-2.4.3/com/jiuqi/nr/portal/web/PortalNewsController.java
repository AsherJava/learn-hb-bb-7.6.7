/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.portal.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.portal.news.INews;
import com.jiuqi.nr.portal.news.bean.AcceptObject;
import com.jiuqi.nr.portal.news.bean.NewsDefine;
import com.jiuqi.nr.portal.news.bean.NewsItem;
import com.jiuqi.nr.portal.news.bean.NewsReturnObject;
import com.jiuqi.nr.portal.news.service.INewsService;
import java.util.ArrayList;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/Portal/News"})
public class PortalNewsController {
    @Autowired
    private INewsService newsService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IEntityViewRunTimeController entityViewController;

    @RequestMapping(value={"/SaveNews"}, method={RequestMethod.POST})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturnObject addNews(@RequestBody NewsDefine define) {
        NewsReturnObject newsReturnObject = new NewsReturnObject();
        if (define == null) {
            return newsReturnObject;
        }
        NewsItem item = define.getNewsItem();
        boolean success = true;
        try {
            if (item.getID() != null) {
                INews queryNewsItem = this.newsService.queryNewsItem(item.getID());
                if (queryNewsItem != null) {
                    success = this.newsService.updataItem(item);
                } else {
                    item.setID(UUID.randomUUID().toString());
                    success = this.newsService.addNewsItem(item);
                }
            } else {
                item.setID(UUID.randomUUID().toString());
                success = this.newsService.addNewsItem(item);
            }
        }
        catch (Exception e) {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage(e.getMessage());
            return newsReturnObject;
        }
        if (success) {
            newsReturnObject.setSuccess(success);
            newsReturnObject.setNewsId(item.getID());
            return newsReturnObject;
        }
        newsReturnObject.setSuccess(success);
        newsReturnObject.setMessage("\u6ca1\u6709\u53d1\u73b0\u53d8\u66f4\u6570\u636e\uff01");
        return newsReturnObject;
    }

    @RequestMapping(value={"/QueryNewsItem"}, method={RequestMethod.POST})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturnObject queryNewsItem(@RequestBody AcceptObject obj) {
        String message = obj.getNews();
        NewsReturnObject newsReturnObject = new NewsReturnObject();
        NewsItem item = null;
        try {
            item = (NewsItem)this.newsService.queryNewsItem(message);
        }
        catch (Exception e) {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage(e.getMessage());
            return newsReturnObject;
        }
        if (item != null) {
            newsReturnObject.setSuccess(true);
            newsReturnObject.setNewsItem(item);
        } else {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage("\u6ca1\u6709\u67e5\u8be2\u5230\u6570\u636e\uff01");
        }
        return newsReturnObject;
    }

    @RequestMapping(value={"/DeleteNews"}, method={RequestMethod.POST})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturnObject deleteNewsItem(String message) {
        NewsReturnObject newsReturnObject = new NewsReturnObject();
        boolean success = true;
        try {
            success = this.newsService.deleteNewsItem(message);
        }
        catch (Exception e) {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage(e.getMessage());
        }
        if (success) {
            newsReturnObject.setSuccess(true);
            return newsReturnObject;
        }
        newsReturnObject.setSuccess(false);
        newsReturnObject.setMessage("\u6ca1\u6709\u627e\u5230\u5f85\u5220\u9664\u7684\u6570\u636e\uff01");
        return newsReturnObject;
    }

    @RequestMapping(value={"/QueryNewsList"}, method={RequestMethod.POST})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturnObject queryNewsList(@RequestBody AcceptObject obj) {
        String mid = obj.getNews();
        NewsReturnObject newsReturnObject = new NewsReturnObject();
        NewsItem[] items = new NewsItem[]{};
        try {
            items = (NewsItem[])this.newsService.queryNewsList(mid);
        }
        catch (Exception e) {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage(e.getMessage());
        }
        newsReturnObject.setSuccess(true);
        newsReturnObject.setNewsList(items);
        return newsReturnObject;
    }

    @RequestMapping(value={"/QueryAllList"}, method={RequestMethod.GET})
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    public NewsReturnObject queryAllNewsList() {
        NewsReturnObject newsReturnObject = new NewsReturnObject();
        ArrayList<NewsItem> items = new ArrayList<NewsItem>();
        try {
            INews[] queryAllNewsList;
            for (INews iNews : queryAllNewsList = this.newsService.queryAllNewsList()) {
                NewsItem item = (NewsItem)iNews;
                items.add(item);
            }
        }
        catch (Exception e) {
            newsReturnObject.setSuccess(false);
            newsReturnObject.setMessage(e.getMessage());
        }
        newsReturnObject.setSuccess(true);
        newsReturnObject.setNewsList(items.toArray(new NewsItem[items.size()]));
        return newsReturnObject;
    }
}

