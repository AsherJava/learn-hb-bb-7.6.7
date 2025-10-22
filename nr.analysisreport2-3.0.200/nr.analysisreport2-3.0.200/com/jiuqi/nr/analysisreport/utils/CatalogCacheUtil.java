/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.analysisreport.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.analysisreport.chapter.bean.CatalogVo;
import com.jiuqi.nr.analysisreport.chapter.bean.ReportCatalogItem;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class CatalogCacheUtil {
    private static NedisCacheManager nedisCacheManager;

    public static String getUserCatalogCacheKey(String authorization) {
        return "RCC-REPORT" + authorization;
    }

    public static String getCatalogCacheKey(String templateKey, String versionKey) {
        return templateKey + versionKey;
    }

    public static String getCatalogUpdateTimeCacheKey(String templateKey, String versionKey) {
        return templateKey + versionKey + "RCUT";
    }

    public static void setCatalogCahe(CatalogVo catalogVo, Map<String, List<ReportCatalogItem>> map) throws JsonProcessingException {
        NedisCache cache = CatalogCacheUtil.getNedisCacheManager().getCache("RCC-REPORT");
        String templateKey = catalogVo.getTemplateKey();
        String versionKey = catalogVo.getVersionKey();
        String authorization = catalogVo.getAuthorization();
        String userCatalogCacheKey = CatalogCacheUtil.getUserCatalogCacheKey(authorization);
        HashMap<String, Object> catalogCahe = (HashMap<String, Object>)cache.get(userCatalogCacheKey, Map.class);
        if (CollectionUtils.isEmpty(catalogCahe)) {
            catalogCahe = new HashMap<String, Object>();
        }
        Date catalogUpdateTime = catalogVo.getCatalogUpdateTime();
        catalogCahe.put(CatalogCacheUtil.getCatalogCacheKey(templateKey, versionKey), map);
        catalogCahe.put(CatalogCacheUtil.getCatalogUpdateTimeCacheKey(templateKey, versionKey), catalogUpdateTime);
        cache.put(userCatalogCacheKey, catalogCahe);
    }

    public static NedisCacheManager getNedisCacheManager() {
        if (nedisCacheManager == null) {
            NedisCacheProvider nedisCacheProvider = (NedisCacheProvider)SpringBeanUtils.getBean(NedisCacheProvider.class);
            nedisCacheManager = nedisCacheProvider.getCacheManager("report_catalog_cache_manager");
        }
        return nedisCacheManager;
    }

    public static List<ReportCatalogItem> getCacheCatalog(CatalogVo catalogVo, String parentId) throws JsonProcessingException {
        NedisCache cache = CatalogCacheUtil.getNedisCacheManager().getCache("RCC-REPORT");
        String templateKey = catalogVo.getTemplateKey();
        String versionKey = catalogVo.getVersionKey();
        String authorization = catalogVo.getAuthorization();
        String userCatalogCacheKey = CatalogCacheUtil.getUserCatalogCacheKey(authorization);
        Map catalogCahe = (Map)cache.get(userCatalogCacheKey, Map.class);
        if (catalogCahe == null) {
            return null;
        }
        Map map = (Map)catalogCahe.get(CatalogCacheUtil.getCatalogCacheKey(templateKey, versionKey));
        if (map == null) {
            return null;
        }
        return (List)map.get(parentId);
    }
}

