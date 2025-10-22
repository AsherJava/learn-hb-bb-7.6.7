/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Interner
 *  com.google.common.collect.Interners
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgCacheConst
 *  com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent
 *  com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.OrgTypeVO
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  org.apache.commons.lang3.time.DateFormatUtils
 */
package com.jiuqi.gcreport.org.impl.cache.service.impl;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgCacheConst;
import com.jiuqi.gcreport.org.api.event.GcOrgBaseEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataAuthzChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataCacheClearEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgDataItemChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgTypeChangeEvent;
import com.jiuqi.gcreport.org.api.event.GcOrgVersionChangeEvent;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.org.impl.base.GcOrgBaseParam;
import com.jiuqi.gcreport.org.impl.cache.base.GcOrgQueryParam;
import com.jiuqi.gcreport.org.impl.cache.base.VersionMap;
import com.jiuqi.gcreport.org.impl.cache.impl.GcOrgParam;
import com.jiuqi.gcreport.org.impl.cache.service.impl.GcOrgServiceBase;
import com.jiuqi.gcreport.org.impl.cache.util.GcOrgCacheUtil;
import com.jiuqi.gcreport.org.impl.util.base.OrgParamParse;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.OrgAuthClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

@Service
public class GcOrgCacheManage
extends GcOrgServiceBase {
    private static final Logger logger = LoggerFactory.getLogger(GcOrgCacheManage.class);
    private static Interner<String> lock = Interners.newWeakInterner();
    @Autowired
    private OrgAuthClient orgAuthClient;
    @Autowired
    private SystemUserService userService;
    private NedisCacheManager manager = DefaultCacheProvider.getCacheManager((String)"cachemanage_gcreport_orgdata_caffeine");
    public static final String CACHE_KEY_GC_ORG_DATA_TREE = "KEY_GCORG_DATATREE";
    public static final String CACHE_KEY_GC_ORG_DATA_TREE_KEY = "KEY_GCORG_DATATREE_USER";
    private static final String CACHE_DATA_KEY_LIST = "KEY_GCORG_DATALIST";
    private static final String CACHE_DATA_KEY_NOAUTHZ = "KEY_GCORG_DATANOAUTHZ";

    public void clearUserCache(GcOrgDataAuthzChangeEvent event) {
        if (event.getUsers().isEmpty()) {
            return;
        }
        Set cacheNames = event.getCacheNames();
        boolean clearAll = false;
        if (cacheNames.contains("MD_ORG")) {
            clearAll = true;
        }
        boolean finalClearAll = clearAll;
        this.manager.getCacheNames().forEach(v -> {
            if (finalClearAll) {
                NedisCache cache = this.manager.getCache(v);
                cache.mEvict((Collection)event.getUsers());
            } else if (cacheNames.isEmpty() || cacheNames.contains(v)) {
                NedisCache cache = this.manager.getCache(v);
                cache.mEvict((Collection)event.getUsers());
            }
        });
    }

    public void clearOneCache(GcOrgDataItemChangeEvent event) {
        String cacheName = event.getCacheName();
        this.manager.getCacheNames().forEach(v -> {
            if (cacheName == null || v.equals(cacheName)) {
                this.manager.getCache(v).clear();
            }
        });
    }

    public void clearAllCache(GcOrgDataCacheClearEvent event) {
        this.manager.getCacheNames().forEach(v -> this.manager.getCache(v).clear());
    }

    public void clearTVCache(GcOrgBaseEvent<?> message) {
        if (message instanceof GcOrgTypeChangeEvent) {
            this.clearOrgType((GcOrgTypeChangeEvent)message);
        } else if (message instanceof GcOrgVersionChangeEvent) {
            this.clearOrgVersion((GcOrgVersionChangeEvent)message);
        }
    }

    private void clearOrgType(GcOrgTypeChangeEvent event) {
        NedisCache cache = this.manager.getCache(GcOrgCacheConst.getOrgTypeCacheKey());
        if (StringUtils.isNull((String)event.getCacheKey())) {
            cache.clear();
        } else {
            cache.evict(event.getCacheKey());
        }
    }

    private void clearOrgVersion(GcOrgVersionChangeEvent event) {
        NedisCache cache = this.manager.getCache(GcOrgCacheConst.getOrgVersionCacheKey());
        if (StringUtils.isNull((String)event.getCacheKey())) {
            cache.clear();
        } else {
            cache.evict(event.getCacheKey());
        }
    }

    public void initOrgCacheData(GcOrgParam param, DoThingsNoCache<GcOrgCacheVO> back) {
        this.getOrgList(param, back);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public OrgTypeVO getOrgType(String name, DoThingsNoCache<OrgTypeVO> back) {
        NedisCache cache = this.manager.getCache(GcOrgCacheConst.getOrgTypeCacheKey());
        OrgTypeVO data = null;
        Cache.ValueWrapper valueWrapper = cache.get(name);
        if (valueWrapper != null) {
            data = (OrgTypeVO)valueWrapper.get();
        }
        if (data == null) {
            NedisCache nedisCache = cache;
            synchronized (nedisCache) {
                List<OrgTypeVO> listDatas = back.getListDatas(OrgParamParse.createDefaultParam(name, v -> {}));
                if (listDatas != null && listDatas.size() > 0) {
                    data = listDatas.get(0);
                }
                if (data == null) {
                    throw new RuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b" + name);
                }
                cache.put(data.getName(), (Object)data);
            }
        }
        return data;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public OrgVersionVO getOrgVersion(OrgTypeVO type, Date time, DoThingsNoCache<OrgVersionVO> back) {
        NedisCache cache = this.manager.getCache(GcOrgCacheConst.getOrgVersionCacheKey());
        OrgVersionVO datas = null;
        Cache.ValueWrapper valueWrapper = cache.hGet(type.getName(), (Object)time);
        if (valueWrapper != null) {
            datas = (OrgVersionVO)valueWrapper.get();
        }
        if (datas == null) {
            NedisCache nedisCache = cache;
            synchronized (nedisCache) {
                List<OrgVersionVO> list = back.getListDatas(OrgParamParse.createDefaultParam(type.getName(), time, v -> {}));
                VersionMap map = new VersionMap();
                list.stream().forEach(vo -> map.put((OrgVersionVO)vo));
                cache.put(type.getName(), (Object)map);
                datas = map.get(time);
            }
        }
        if (datas != null) {
            return datas;
        }
        throw new RuntimeException("\u6ca1\u6709\u67e5\u627e\u5230" + type.getName() + "\u7c7b\u578b\u4e0b" + DateFormatUtils.format((Date)time, (String)"yyyy-MM-dd") + "\u65f6\u95f4\u5bf9\u5e94\u7684\u7ec4\u7ec7\u673a\u6784\u7248\u672c");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<GcOrgCacheVO> getOrgTree(GcOrgBaseParam param, DoThingsNoCache<GcOrgCacheVO> back) {
        String hashKey;
        ContextUser contextUser;
        if (param.getAuthType() == null) {
            return null;
        }
        GcAuthorityType auType = this.isSysUser() ? GcAuthorityType.NONE : param.getAuthType();
        String orgCode = "";
        if (auType != GcAuthorityType.NONE && (contextUser = NpContextHolder.getContext().getUser()) != null) {
            orgCode = Optional.ofNullable(contextUser.getOrgCode()).orElse("");
        }
        NedisCache cache = this.manager.getCache(GcOrgCacheManage.getOrgDataCacheName(param.getType()));
        List<GcOrgCacheVO> datas = null;
        String key = GcOrgCacheManage.getOrgDataCacheKey(param.getAuthType());
        Cache.ValueWrapper valueWrapper = cache.hGet(key, (Object)(hashKey = GcOrgCacheManage.getOrgDataHashKey(param.getVersion(), param.getAuthType(), orgCode)));
        if (valueWrapper != null) {
            datas = (List<GcOrgCacheVO>)valueWrapper.get();
        }
        if (datas == null || datas.size() < 1) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u6743\u9650\u6811\u7f13\u5b58\u5f00\u59cb\u52a0\u8f7d");
            List<GcOrgCacheVO> orgList = this.getOrgList(OrgParamParse.createParam(param, v -> v.setAuthType(OrgDataOption.AuthType.NONE)), back);
            String string = (String)lock.intern((Object)(key + hashKey));
            synchronized (string) {
                Cache.ValueWrapper cachedWrapper = cache.hGet(key, (Object)hashKey);
                if (cachedWrapper != null) {
                    datas = (List)cachedWrapper.get();
                }
                if (!CollectionUtils.isEmpty((Collection)datas)) {
                    return datas;
                }
                Collection<GcOrgCacheVO> authzObjects = this.getAuthzKey(auType, param, orgList);
                datas = this.collectionToCacheTree(authzObjects);
                cache.hSet(key, (Object)hashKey, datas);
                logger.info("\u7ec4\u7ec7\u673a\u6784\u6743\u9650\u6811\u7f13\u5b58\u7ed3\u675f\u52a0\u8f7d");
            }
        }
        return new ArrayList<GcOrgCacheVO>(datas);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<GcOrgCacheVO> getOrgList(GcOrgParam param, DoThingsNoCache<GcOrgCacheVO> back) {
        String hashKey;
        NedisCache cache = this.manager.getCache(GcOrgCacheManage.getOrgDataCacheName(param.getTypeVo()));
        List<GcOrgCacheVO> datas = null;
        String key = GcOrgCacheManage.getOrgDataListKey();
        Cache.ValueWrapper valueWrapper = cache.hGet(key, (Object)(hashKey = GcOrgCacheManage.getOrgDataHashKey(param.getVersionVo(), GcAuthorityType.NONE, "")));
        if (valueWrapper != null) {
            datas = (List<GcOrgCacheVO>)valueWrapper.get();
        }
        if (datas == null || datas.size() < 1) {
            String string = (String)lock.intern((Object)hashKey);
            synchronized (string) {
                Cache.ValueWrapper cachedWrapper = cache.hGet(key, (Object)hashKey);
                if (cachedWrapper != null) {
                    datas = (List)cachedWrapper.get();
                }
                if (!CollectionUtils.isEmpty((Collection)datas)) {
                    return datas;
                }
                logger.info("\u7c7b\u578b[" + param.getTypeVo().getTable() + "  " + param.getTypeVo().getTitle() + "]\u7248\u672c[" + param.getVersionVo().getTitle() + "]\u7ec4\u7ec7\u673a\u6784\u5217\u8868\u7f13\u5b58\u5f00\u59cb\u52a0\u8f7d");
                datas = back.getListDatas(param);
                datas = this.formatOrgKinds((Collection<GcOrgCacheVO>)datas);
                cache.hSet(key, (Object)hashKey, datas);
                logger.info("\u7c7b\u578b[" + param.getTypeVo().getTable() + "  " + param.getTypeVo().getTitle() + "]\u7248\u672c[" + param.getVersionVo().getTitle() + "]\u7ec4\u7ec7\u673a\u6784\u5217\u8868\u7f13\u5b58\u7ed3\u675f\u52a0\u8f7d");
            }
        }
        return datas;
    }

    @Deprecated
    private Collection<GcOrgCacheVO> getAuthzKey(GcAuthorityType auType, GcOrgBaseParam param, List<GcOrgCacheVO> orgList) {
        if (auType == GcAuthorityType.NONE) {
            return orgList.stream().sorted(Comparator.comparingDouble(GcOrgCacheVO::getOrdinal)).collect(Collectors.toList());
        }
        OrgDataOption.AuthType authType = GcOrgCacheUtil.replaceVAuthz(auType);
        Set<Object> key = new HashSet();
        if (!(param instanceof GcOrgQueryParam)) {
            throw new NullPointerException("\u5f53\u524d\u67e5\u8be2\u6743\u9650\u8bbe\u7f6e\u6709\u8bef");
        }
        key = this.getCanOperateEntityKeys(authType.toString(), param.getOrgtypeName(), param.getStartDate());
        ArrayList list = CollectionUtils.newArrayList();
        for (GcOrgCacheVO entry : orgList) {
            if (!key.contains(entry.getCode())) continue;
            list.add(entry);
        }
        return list.stream().sorted(Comparator.comparingDouble(GcOrgCacheVO::getOrdinal)).collect(Collectors.toList());
    }

    private Set<String> getCanOperateEntityKeys(String authType, String orgTable, Date versionEndDate) {
        NpContext context = NpContextHolder.getContext();
        if (context == null || context.getUser() == null || context.getUser().getName() == null) {
            throw new RuntimeException("\u4e0a\u4e0b\u6587\u4e2d\u65e0\u6cd5\u83b7\u5f97\u7528\u6237\u767b\u5f55\u540d\u4fe1\u606f");
        }
        OrgDTO orgDataDTO = new OrgDTO();
        orgDataDTO.setCategoryname(orgTable.toUpperCase());
        orgDataDTO.setVersionDate(versionEndDate);
        orgDataDTO.setAuthType(OrgDataOption.AuthType.valueOf((String)authType));
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        UserDO userDO = new UserDO();
        userDO.setUsername(context.getUser().getName());
        orgAuthFindDTO.setUserDO(userDO);
        orgAuthFindDTO.setOrgDTO(orgDataDTO);
        return this.orgAuthClient.findAuth(orgAuthFindDTO);
    }

    public static String getOrgDataCacheName(OrgTypeVO type) {
        return type.getName();
    }

    public static String getOrgDataCacheKey(GcAuthorityType type) {
        if (type == GcAuthorityType.NONE) {
            return CACHE_DATA_KEY_NOAUTHZ;
        }
        return NpContextHolder.getContext().getIdentityId();
    }

    public static String getOrgDataListKey() {
        return CACHE_DATA_KEY_LIST;
    }

    public static String getOrgDataHashKey(OrgVersionVO ver, GcAuthorityType type, String orgCode) {
        if (type == null || type == GcAuthorityType.NONE) {
            return "KEY_" + ver.getId();
        }
        return "KEY_" + ver.getId() + "_" + type.name() + orgCode;
    }

    private boolean isSysUser() {
        String userName = NpContextHolder.getContext().getUserName();
        if (StringUtils.isEmpty((String)userName)) {
            return false;
        }
        SystemUser user = (SystemUser)this.userService.getByUsername(userName);
        return user != null;
    }

    @FunctionalInterface
    public static interface DoThingsNoCache<T> {
        public List<T> getListDatas(GcOrgParam var1);
    }
}

