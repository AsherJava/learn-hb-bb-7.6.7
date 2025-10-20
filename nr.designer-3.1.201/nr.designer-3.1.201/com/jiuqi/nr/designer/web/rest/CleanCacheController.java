/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.misc.AuthResetCacheService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 *  com.jiuqi.nr.graph.IRWLockExecuterManager$RWLockType
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock
 *  com.jiuqi.nr.graph.rwlock.executer.DatabaseLock$Lock
 *  com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.authz2.misc.AuthResetCacheService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u6e05\u7406\u7f13\u5b58"})
public class CleanCacheController {
    private static final String CACHE_CLEANCACHE = "CACHE_CLEANCACHE";
    private NedisCache cache;
    @Autowired
    private DatabaseLock databaseLock;
    @Autowired
    private IRWLockExecuterManager lockExecuterManager;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AuthResetCacheService authResetCacheService;
    @Autowired
    private List<DataModelCacheRefreshListener> listeners;

    @Autowired
    public void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cache = cacheProvider.getCacheManager().getCache(CACHE_CLEANCACHE);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @ApiOperation(value="\u6e05\u7406\u7f13\u5b58")
    @GetMapping(value={"clean/cache"})
    public void doClean() throws JQException {
        List locks;
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        if (!this.systemUserService.exists(user.getName())) throw new JQException((ErrorEnum)CleanCacheError.CLEAN_CACHE_ERROR_001);
        if (IRWLockExecuterManager.RWLockType.DATABASE == this.lockExecuterManager.getRWLockType() && !(locks = this.databaseLock.listLock()).isEmpty()) {
            long count = locks.stream().filter(DatabaseLock.Lock::isExclusive).count();
            throw new JQException((ErrorEnum)(count > 0L ? CleanCacheError.CLEAN_CACHE_ERROR_004 : CleanCacheError.CLEAN_CACHE_ERROR_005));
        }
        Cache.ValueWrapper valueWrapper = this.cache.get(CACHE_CLEANCACHE);
        if (valueWrapper != null) throw new JQException((ErrorEnum)CleanCacheError.CLEAN_CACHE_ERROR_002, (String)valueWrapper.get());
        this.cache.put(CACHE_CLEANCACHE, (Object)"\u7f13\u5b58");
        try {
            this.cache.put(CACHE_CLEANCACHE, (Object)"\u62a5\u8868\u7f13\u5b58");
            RefreshCache refreshCache = new RefreshCache();
            refreshCache.setRefreshAll(true);
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
            this.applicationContext.publishEvent((ApplicationEvent)new RefreshDefinitionCacheEvent());
            if (this.listeners != null) {
                this.listeners.forEach(DataModelCacheRefreshListener::onClearCache);
            }
            this.cache.put(CACHE_CLEANCACHE, (Object)"\u6743\u9650\u7f13\u5b58");
            this.authResetCacheService.resetAuthCache();
            return;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CleanCacheError.CLEAN_CACHE_ERROR_003);
        }
        finally {
            this.cache.evict(CACHE_CLEANCACHE);
            LogHelper.info((String)"\u7cfb\u7edf\u7ba1\u7406", (String)"\u6e05\u9664\u7f13\u5b58", (String)"\u6e05\u9664\u6240\u6709\u8282\u70b9\u4e0b\u6240\u6709\u4efb\u52a1\u3001\u62a5\u8868\u65b9\u6848\u7b49\u53c2\u6570\u7684\u7f13\u5b58");
        }
    }

    private static enum CleanCacheError implements ErrorEnum
    {
        CLEAN_CACHE_ERROR_001("001", "\u5f53\u524d\u767b\u5f55\u7528\u6237\u975e\u7ba1\u7406\u5458\uff0c\u65e0\u6743\u6e05\u7406\u7f13\u5b58"),
        CLEAN_CACHE_ERROR_002("002", "\u6b63\u5728\u6e05\u7406\u7f13\u5b58"),
        CLEAN_CACHE_ERROR_003("003", "\u7f13\u5b58\u6e05\u7406\u5f02\u5e38"),
        CLEAN_CACHE_ERROR_004("004", "\u6b63\u5728\u53d1\u5e03\u53c2\u6570\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5"),
        CLEAN_CACHE_ERROR_005("005", "\u6b63\u5728\u52a0\u8f7d\u7f13\u5b58\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5");

        private final String code;
        private final String message;

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }

        private CleanCacheError(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}

