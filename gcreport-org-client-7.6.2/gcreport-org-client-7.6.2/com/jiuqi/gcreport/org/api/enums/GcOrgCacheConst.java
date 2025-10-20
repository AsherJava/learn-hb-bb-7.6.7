/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.org.api.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.vo.OrgTypeVO;
import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import java.util.ArrayList;
import java.util.List;

public class GcOrgCacheConst {
    public static final String CACHEMANAGE_NAME_GCORG_REDIS = "cachemanage_gcreport_orgdata_redis";
    public static final String CACHEMANAGE_NAME_GCORG_CAFFEINE = "cachemanage_gcreport_orgdata_caffeine";
    public static final String GC_ORG_CACHE_CHANGE_STATE = "com.jiuqi.gcreport.org.cache.listener.message.state";
    public static final String CACHE_KEY_GC_ORG_DATA_TREE = "KEY_GCORG_DATATREE";
    public static final String CACHE_KEY_GC_ORG_DATA_TREE_KEY = "KEY_GCORG_DATATREE_USER";
    private static final String CACHE_DATA_KEY_LIST = "KEY_GCORG_DATALIST";
    private static final String CACHE_DATA_KEY_NOAUTHZ = "KEY_GCORG_DATANOAUTHZ";
    private static final String CACHE_KEY_GC_ORG_TYPE = "KEY_GCORG_TYPE";
    private static final String CACHE_KEY_GC_ORG_VERSION = "KEY_GCORG_VERSION";
    private static final String CACHE_KEY_GC_ORG_DATA_LIST = "KEY_GCORG_DATALIST";
    private static final String CACHE_KEY_GC_ORG_DATA_AUTHZ_TREE_BEGIN = "KEY_GCORG_DATATREE_AUTHZ_";
    public static List<String> CACHE_NAME_LIST = new ArrayList<String>();

    public static String getOrgDataCacheName(OrgTypeVO type) {
        return type.getName();
    }

    public static String getOrgDataCacheKey(GcAuthorityType type, String userId) {
        if (StringUtils.isNull((String)userId) || type == GcAuthorityType.NONE) {
            return "CACHE_DATA_KEY_NOAUTHZ";
        }
        return userId;
    }

    public static String getOrgDataListKey(GcAuthorityType type, String userId) {
        return "KEY_GCORG_DATALIST";
    }

    public static String getOrgDataHashKey(OrgVersionVO ver, GcAuthorityType type) {
        if (type == null || type == GcAuthorityType.NONE) {
            return "KEY_" + ver.getId();
        }
        return "KEY_" + ver.getId() + "_" + type.name();
    }

    public static String getOrgTypeCacheKey() {
        return CACHE_KEY_GC_ORG_TYPE;
    }

    public static String getOrgVersionCacheKey() {
        return CACHE_KEY_GC_ORG_VERSION;
    }

    public static String getOrgDataListCacheKey() {
        return "KEY_GCORG_DATALIST";
    }

    public static String getOrgAuthzTreeCacheKey(GcAuthorityType type) {
        return CACHE_KEY_GC_ORG_DATA_AUTHZ_TREE_BEGIN + type.getType();
    }

    public static String getOrgTypeKey(String name) {
        return name;
    }

    public static String getOrgVersionKey(OrgTypeVO type) {
        return type.getName();
    }

    public static String getOrgDataNoAuthzKey(OrgTypeVO type, OrgVersionVO ver) {
        return type.getName() + "_" + ver.getId();
    }

    public static String getOrgDataAuthzKey(OrgTypeVO type, OrgVersionVO ver, String userId) {
        if (StringUtils.isNull((String)userId)) {
            return GcOrgCacheConst.getOrgDataNoAuthzKey(type, ver);
        }
        return GcOrgCacheConst.getOrgDataNoAuthzKey(type, ver) + "_" + userId;
    }

    static {
        CACHE_NAME_LIST.add(CACHE_KEY_GC_ORG_TYPE);
        CACHE_NAME_LIST.add(CACHE_KEY_GC_ORG_VERSION);
        CACHE_NAME_LIST.add("KEY_GCORG_DATALIST");
        for (GcAuthorityType authz : GcAuthorityType.values()) {
            CACHE_NAME_LIST.add(authz.getCacheKey());
        }
    }
}

