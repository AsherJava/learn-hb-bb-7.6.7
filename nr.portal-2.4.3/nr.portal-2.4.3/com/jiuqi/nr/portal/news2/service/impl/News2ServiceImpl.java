/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nvwa.framework.nros.extend.log.LogProvider
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.portal.news2.cache.PortalCacheManager;
import com.jiuqi.nr.portal.news2.impl.IBaseInfo;
import com.jiuqi.nr.portal.news2.impl.NewsAbstractInfo;
import com.jiuqi.nr.portal.news2.impl.NewsImpl;
import com.jiuqi.nr.portal.news2.service.INews2Dao;
import com.jiuqi.nr.portal.news2.service.INews2Service;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import com.jiuqi.nvwa.framework.nros.extend.log.LogProvider;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class News2ServiceImpl
implements INews2Service {
    @Autowired
    private INews2Dao news2Dao;
    @Autowired
    private LogProvider logProvider;
    @Autowired
    public IQueryReadDao queryReadDao;
    @Autowired
    private NedisCacheProvider cacheProvider;
    private static final String INFO_TYPE = "news";
    private ReentrantLock lock = new ReentrantLock();
    public static final String MODULE_TITLE = "\u9996\u9875\u65b0\u95fb";

    @Override
    public String addNews(NewsImpl impl) {
        String id = UUID.randomUUID().toString();
        impl.setId(id);
        Assert.notNull((Object)impl.getMid(), "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getPortalId(), "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getExtention(), "'extention' \u4e0d\u80fd\u4e3a\u7a7a.");
        this.news2Dao.insertNews(impl);
        return id;
    }

    @Override
    public String addNewsRunning(NewsImpl impl) {
        String id = UUID.randomUUID().toString();
        impl.setId(id);
        Assert.notNull((Object)impl.getMid(), "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getPortalId(), "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getExtention(), "'extention' \u4e0d\u80fd\u4e3a\u7a7a.");
        this.news2Dao.insertNewsRunning(impl);
        this.removeCache(impl.getMid(), impl.getPortalId(), "running");
        return id;
    }

    @Override
    public Boolean modifyNews(NewsImpl impl) {
        Assert.notNull((Object)impl.getId(), "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        NewsImpl queryNews = this.news2Dao.queryNews(impl.getId(), "design");
        if (queryNews == null) {
            throw new IllegalArgumentException("\u5f53\u524d\u64cd\u4f5c\u7684\u65b0\u95fb\u4e0d\u5b58\u5728\uff01");
        }
        impl.setUpdateTime(LocalDateTime.now());
        Boolean aBoolean = this.news2Dao.updateNews(impl);
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteNews(String id) {
        Assert.notNull((Object)id, "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        NewsImpl queryNews = this.news2Dao.queryNews(id, "design");
        if (queryNews == null) {
            throw new IllegalArgumentException("\u5f53\u524d\u64cd\u4f5c\u7684\u65b0\u95fb\u4e0d\u5b58\u5728\uff01");
        }
        Boolean aBoolean = this.news2Dao.deleteNews(id);
        this.queryReadDao.deleteItemByInfoId(queryNews.getId());
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteNewsByMid(String mid, String portalId) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        List<NewsAbstractInfo> infos = this.news2Dao.queryNewsByMidAndPortalId(mid, portalId, "design");
        List<String> infoIds = infos.stream().map(IBaseInfo::getId).collect(Collectors.toList());
        Boolean aBoolean = this.news2Dao.deleteNewsByMid(mid, portalId);
        this.queryReadDao.batchDeleteItemByInfoIds(infoIds);
        return aBoolean;
    }

    @Override
    public NewsImpl queryNewsById(String id, String type) {
        Assert.notNull((Object)id, "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        if (StringUtils.isEmpty(type)) {
            type = "design";
        }
        NewsImpl queryNews = this.news2Dao.queryNews(id, type);
        this.logProvider.info(MODULE_TITLE, "\u67e5\u770b\u65b0\u95fb", "\u65b0\u95fb\u540d\u79f0:" + queryNews.getTitle());
        return queryNews;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Boolean publishNews(String mid, String portalId) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Boolean result = false;
        try {
            this.lock.lock();
            result = this.news2Dao.publishNews(mid, portalId);
            this.removeCache(mid, portalId, "running");
        }
        finally {
            this.lock.unlock();
        }
        return result;
    }

    @Override
    public List<NewsAbstractInfo> queryAllNews() {
        return this.news2Dao.queryAllNews();
    }

    @Override
    public List<NewsAbstractInfo> queryNewsByMidAndPortalId(String mid, String portalId, String type) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        if (StringUtils.isEmpty(type)) {
            type = "design";
        }
        List<NewsAbstractInfo> cacheValue = null;
        if ("running".equals(type)) {
            cacheValue = this.getCacheValue(mid, portalId, type);
            if (cacheValue == null) {
                cacheValue = this.news2Dao.queryNewsByMidAndPortalId(mid, portalId, type);
                this.setCache(cacheValue, mid, portalId, type);
            }
            cacheValue = this.addReadField(cacheValue);
            return cacheValue;
        }
        List<NewsAbstractInfo> newsAbstractInfos = this.news2Dao.queryNewsByMidAndPortalId(mid, portalId, type);
        List<NewsAbstractInfo> infos = this.addReadField(newsAbstractInfos);
        return infos;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public NewsImpl queryNewsByIdForWeb(String id, String type) {
        NewsImpl queryNews;
        Assert.notNull((Object)id, "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        if (StringUtils.isEmpty(type)) {
            type = "design";
        }
        if ((queryNews = this.news2Dao.queryNews(id, type)) == null) {
            return null;
        }
        queryNews.setViewCount(queryNews.getViewCount() + 1);
        this.news2Dao.updateViewCount(queryNews.getViewCount(), queryNews.getId(), true);
        this.news2Dao.updateViewCount(queryNews.getViewCount(), queryNews.getId(), false);
        this.queryReadDao.addReadItem(NpContextHolder.getContext().getIdentityId(), queryNews.getId(), INFO_TYPE);
        List<NewsAbstractInfo> cacheValue = this.getCacheValue(queryNews.getMid(), queryNews.getPortalId(), type);
        if (cacheValue != null) {
            for (NewsAbstractInfo info : cacheValue) {
                if (!info.getId().equals(queryNews.getId())) continue;
                info.setRead(true);
                info.setViewCount(queryNews.getViewCount());
                break;
            }
            this.setCache(cacheValue, queryNews.getMid(), queryNews.getPortalId(), type);
        }
        this.logProvider.info(MODULE_TITLE, "\u67e5\u770b\u65b0\u95fb", "\u65b0\u95fb\u540d\u79f0:" + queryNews.getTitle());
        return queryNews;
    }

    @Override
    public Boolean modifyFileOrder(String id, Integer newOrder) {
        Assert.notNull((Object)id, "'fileId' \u4e0d\u80fd\u4e3a\u7a7a.");
        NewsImpl queryNews = this.news2Dao.queryNews(id, "design");
        if (queryNews == null) {
            throw new IllegalArgumentException("\u5f53\u524d\u64cd\u4f5c\u7684\u65b0\u95fb\u4e0d\u5b58\u5728\uff01");
        }
        return this.news2Dao.modifyFileOrder(id, newOrder);
    }

    @Override
    public String addNews(NewsImpl impl, Boolean order) {
        if (order.booleanValue()) {
            Integer maxOrder = this.news2Dao.getMaxOrder();
            impl.setOrder(maxOrder + 1);
        }
        return this.addNews(impl);
    }

    protected List<NewsAbstractInfo> addReadField(List<NewsAbstractInfo> list) {
        List<String> strings = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), INFO_TYPE);
        HashSet<String> set = new HashSet<String>(strings);
        for (NewsAbstractInfo info : list) {
            if (set.contains(info.getId())) {
                info.setRead(true);
                continue;
            }
            info.setRead(false);
        }
        return list;
    }

    protected void setCache(List<NewsAbstractInfo> newsList, String mid, String portalId, String design) {
        NedisCache cache = this.getCache();
        String portalCacheKey = PortalCacheManager.getPortalCacheKey(mid, portalId, INFO_TYPE + design);
        cache.hSet(portalCacheKey, (Object)"PORTAL_CACHE_MANAGE_BASE_INFO", newsList);
    }

    @Override
    public void removeCache(String mid, String portalId, String design) {
        NedisCache cache = this.getCache();
        String portalCacheKey = PortalCacheManager.getPortalCacheKey(mid, portalId, INFO_TYPE + design);
        cache.hDel(portalCacheKey, new Object[]{"PORTAL_CACHE_MANAGE_BASE_INFO"});
    }

    protected List<NewsAbstractInfo> getCacheValue(String mid, String portalId, String design) {
        NedisCache cache = this.getCache();
        String portalCacheKey = PortalCacheManager.getPortalCacheKey(mid, portalId, INFO_TYPE + design);
        Cache.ValueWrapper valueWrapper = cache.hGet(portalCacheKey, (Object)"PORTAL_CACHE_MANAGE_BASE_INFO");
        List newsList = null;
        if (valueWrapper != null) {
            newsList = (List)valueWrapper.get();
        }
        return newsList;
    }

    protected NedisCache getCache() {
        NedisCacheManager cacheManager = this.cacheProvider.getCacheManager("PORTAL_CACHE_MANAGE");
        return cacheManager.getCache("PORTAL_CACHE_MANAGE_BASE_INFO");
    }
}

