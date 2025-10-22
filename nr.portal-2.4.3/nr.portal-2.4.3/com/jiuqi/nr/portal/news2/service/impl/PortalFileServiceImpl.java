/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.file.FileAreaConfig
 *  com.jiuqi.nr.file.FileService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.portal.news2.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.file.FileAreaConfig;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nr.portal.news.NewsImgFile;
import com.jiuqi.nr.portal.news2.cache.PortalCacheManager;
import com.jiuqi.nr.portal.news2.impl.FileImpl;
import com.jiuqi.nr.portal.news2.impl.NvwaPortalConstants;
import com.jiuqi.nr.portal.news2.service.IPortalFileDao;
import com.jiuqi.nr.portal.news2.service.IPortalFileService;
import com.jiuqi.nr.portal.news2.service.IQueryReadDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
public class PortalFileServiceImpl
implements IPortalFileService {
    @Autowired
    private IPortalFileDao dao;
    @Autowired
    private FileService fileService;
    @Autowired
    private NedisCacheProvider cacheProvider;
    private ReentrantLock lock = new ReentrantLock();
    @Autowired
    public IQueryReadDao queryReadDao;
    private static final String INFO_TYPE = "file";
    private static final Logger logger = LoggerFactory.getLogger(PortalFileServiceImpl.class);

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteFileByMid(String mid, String portalId, String tableName) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        if (!Arrays.asList(NvwaPortalConstants.storage).contains(tableName)) {
            throw new IllegalArgumentException("'type' \u4e0d\u5b57\u6bb5\u4e0d\u6b63\u786e.");
        }
        List<FileImpl> fileImpls = this.dao.queryFileByMidAndPortalId(mid, portalId);
        List<String> fileIds = fileImpls.stream().map(FileImpl::getId).collect(Collectors.toList());
        Boolean deleteFileByMid = this.dao.deleteFileByMid(mid, portalId, tableName);
        if (deleteFileByMid.booleanValue()) {
            ArrayList<String> deletes = new ArrayList<String>();
            for (FileImpl impl : fileImpls) {
                deletes.add(impl.getId());
            }
            this.deleteFile(deletes);
        }
        this.queryReadDao.batchDeleteItemByInfoIds(fileIds);
        logger.info(LocalDateTime.now() + "\u8fdb\u884c\u4e86\u5bf9mid:" + mid + " portalId: " + portalId + "\u7684\u5220\u9664\u64cd\u4f5c");
        return deleteFileByMid;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Boolean deleteFileByFileId(String fileId) {
        Assert.notNull((Object)fileId, "'fileId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Boolean deleteFileByFileId = this.dao.deleteFileByFileId(fileId);
        this.deleteFile(Arrays.asList(fileId));
        logger.info(LocalDateTime.now() + "\u8fdb\u884c\u4e86\u5bf9fileId:" + fileId + "\u7684\u5220\u9664\u64cd\u4f5c");
        this.queryReadDao.deleteItemByInfoId(fileId);
        return deleteFileByFileId;
    }

    @Override
    public Boolean saveFile(FileImpl impl) {
        Assert.notNull((Object)impl.getMid(), "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getPortalId(), "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getId(), "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        impl.setDownLoadCount(0);
        if (impl.getCreateTime() == null) {
            LocalDateTime now = LocalDateTime.now();
            impl.setCreateTime(now);
            impl.setUpdateTime(now);
        }
        return this.dao.insertFile(impl);
    }

    @Override
    public Boolean saveFileRunning(FileImpl impl) {
        Assert.notNull((Object)impl.getMid(), "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getPortalId(), "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)impl.getId(), "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        impl.setDownLoadCount(0);
        if (impl.getCreateTime() == null) {
            LocalDateTime now = LocalDateTime.now();
            impl.setCreateTime(now);
            impl.setUpdateTime(now);
        }
        Boolean aBoolean = this.dao.insertFileRunning(impl);
        this.removeCache(impl.getMid(), impl.getPortalId(), "running");
        return aBoolean;
    }

    @Override
    public Boolean modifyFile(FileImpl impl) {
        Assert.notNull((Object)impl.getId(), "'fileId' \u4e0d\u80fd\u4e3a\u7a7a.");
        impl.setDownLoadCount(impl.getDownLoadCount() >= 0 ? impl.getDownLoadCount() : 0);
        impl.setUpdateTime(LocalDateTime.now());
        return this.dao.updateFile(impl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Boolean publishFiles(String mid, String portalId) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        Boolean result = false;
        try {
            this.lock.lock();
            result = this.dao.publishFiles(mid, portalId);
            this.removeCache(mid, portalId, "running");
        }
        finally {
            this.lock.unlock();
        }
        return result;
    }

    @Override
    public Boolean modifyFileOrder(String id, Integer newOrder) {
        Assert.notNull((Object)id, "'fileId' \u4e0d\u80fd\u4e3a\u7a7a.");
        return this.dao.modifyFileOrder(id, newOrder);
    }

    private Boolean deleteFile(List<String> fileIds) {
        try {
            for (String id : fileIds) {
                this.fileService.area((FileAreaConfig)new NewsImgFile()).delete(id, Boolean.valueOf(true));
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("\u5220\u9664\u6587\u4ef6\u51fa\u9519\uff1a" + e.getMessage());
        }
        return true;
    }

    @Override
    public List<FileImpl> queryAllFile() {
        List<FileImpl> all = this.dao.queryAllFile();
        all.sort((o1, o2) -> o1.getOrder() - o2.getOrder());
        return all;
    }

    @Override
    public List<FileImpl> queryFileByMidAndPortalId(String mid, String portalId, String type) {
        Assert.notNull((Object)mid, "'mid' \u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)portalId, "'portalId' \u4e0d\u80fd\u4e3a\u7a7a.");
        List<FileImpl> all = null;
        if ("running".equals(type)) {
            all = this.getCacheValue(mid, portalId, type);
            if (all == null) {
                all = this.dao.queryFileByMidAndPortalId(mid, portalId, type);
                all.sort(Comparator.comparingInt(FileImpl::getOrder));
                this.setCache(all, mid, portalId, type);
            }
            all = this.addReadField(all);
            return all;
        }
        all = this.dao.queryFileByMidAndPortalId(mid, portalId, type);
        all.sort(Comparator.comparingInt(FileImpl::getOrder));
        all = this.addReadField(all);
        return all;
    }

    @Override
    public FileImpl queryFileByid(String fileId) {
        Assert.notNull((Object)fileId, "'id' \u4e0d\u80fd\u4e3a\u7a7a.");
        return this.dao.queryFileByFileId(fileId);
    }

    @Override
    public Boolean modifyFileRunning(FileImpl impl) {
        Assert.notNull((Object)impl.getId(), "'fileId' \u4e0d\u80fd\u4e3a\u7a7a.");
        impl.setDownLoadCount(impl.getDownLoadCount() >= 0 ? impl.getDownLoadCount() : 0);
        impl.setUpdateTime(LocalDateTime.now());
        Boolean aBoolean = this.dao.updateFileRunning(impl);
        this.removeCache(impl.getMid(), impl.getPortalId(), "running");
        return aBoolean;
    }

    @Override
    public Boolean saveFile(FileImpl impl, Boolean order) {
        if (order.booleanValue()) {
            Integer maxOrder = this.dao.getMaxOrder();
            impl.setOrder(maxOrder + 1);
        }
        return this.saveFile(impl);
    }

    protected List<FileImpl> addReadField(List<FileImpl> list) {
        List<String> strings = this.queryReadDao.queryReadList(NpContextHolder.getContext().getIdentityId(), INFO_TYPE);
        HashSet<String> set = new HashSet<String>(strings);
        for (FileImpl info : list) {
            if (set.contains(info.getId())) {
                info.setRead(true);
                continue;
            }
            info.setRead(false);
        }
        return list;
    }

    @Override
    public void updateCache(FileImpl file, String type) {
        List<FileImpl> cacheValue = this.getCacheValue(file.getMid(), file.getPortalId(), type);
        if (cacheValue != null) {
            for (FileImpl info : cacheValue) {
                if (!info.getId().equals(file.getId())) continue;
                info.setRead(true);
                break;
            }
            this.setCache(cacheValue, file.getMid(), file.getPortalId(), type);
        }
    }

    protected void setCache(List<FileImpl> newsList, String mid, String portalId, String design) {
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

    protected List<FileImpl> getCacheValue(String mid, String portalId, String design) {
        NedisCache cache = this.getCache();
        String portalCacheKey = PortalCacheManager.getPortalCacheKey(mid, portalId, INFO_TYPE + design);
        Cache.ValueWrapper valueWrapper = cache.hGet(portalCacheKey, (Object)"PORTAL_CACHE_MANAGE_BASE_INFO");
        List filesList = null;
        if (valueWrapper != null) {
            filesList = (List)valueWrapper.get();
        }
        return filesList;
    }

    protected NedisCache getCache() {
        NedisCacheManager cacheManager = this.cacheProvider.getCacheManager("PORTAL_CACHE_MANAGE");
        return cacheManager.getCache("PORTAL_CACHE_MANAGE_BASE_INFO");
    }
}

