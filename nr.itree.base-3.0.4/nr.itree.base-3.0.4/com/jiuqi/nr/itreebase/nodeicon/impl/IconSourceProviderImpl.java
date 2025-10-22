/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.cache.NedisCache
 */
package com.jiuqi.nr.itreebase.nodeicon.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.nr.itreebase.nodeicon.IconCategory;
import com.jiuqi.nr.itreebase.nodeicon.IconSource;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceHelper;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceProvider;
import com.jiuqi.nr.itreebase.nodeicon.IconSourceScheme;
import com.jiuqi.nr.itreebase.nodeicon.impl.IconSourceSchemeOfDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IconSourceProviderImpl
implements IconSourceProvider {
    private final NedisCache nedisCache;
    private final IconSourceHelper helper;
    private Map<String, String> key2iconMap;
    private Map<IconCategory, Map<String, String>> category2ValueMap;

    public IconSourceProviderImpl(String treeDataSourceId, IconSourceScheme[] schemes, NedisCache nedisCache, IconSourceHelper helper) {
        this.nedisCache = nedisCache;
        this.helper = helper;
        this.init(treeDataSourceId, nedisCache, schemes);
    }

    @Override
    public String getDefaultIconKey() {
        return this.getIconKey("def-icon", "deficon");
    }

    @Override
    public String getIconKey(IconCategory category, String value) {
        if (IconCategory.NODE_ICONS == category) {
            String iconKey = this.category2ValueMap.get((Object)category).get(value);
            return StringUtils.isNotEmpty((String)iconKey) ? iconKey : this.getDefaultIconKey();
        }
        return this.category2ValueMap.get((Object)category).get(value);
    }

    @Override
    public Map<String, String> getBase64IconMap() {
        return this.key2iconMap;
    }

    public String getIconKey(String schemeId, String value) {
        return StringUtils.isNotEmpty((String)value) ? schemeId + "@" + value : null;
    }

    private void init(String treeDataSourceId, NedisCache nedisCache, IconSourceScheme[] iconSchemes) {
        this.key2iconMap = new HashMap<String, String>();
        this.category2ValueMap = new HashMap<IconCategory, Map<String, String>>();
        Map<IconCategory, IconSourceScheme[]> iconCategoryMap = this.toCategoryMap(iconSchemes);
        for (Map.Entry<IconCategory, IconSourceScheme[]> entry : iconCategoryMap.entrySet()) {
            IconCategory category = entry.getKey();
            IconSourceScheme[] schemes = entry.getValue();
            Map<String, String> value2IconKeyMap = this.getValue2IconKeyMap(treeDataSourceId, nedisCache, category, schemes);
            this.category2ValueMap.put(category, value2IconKeyMap);
        }
    }

    private Map<String, String> getValue2IconKeyMap(String treeDataSourceId, NedisCache nedisCache, IconCategory category, IconSourceScheme[] schemes) {
        String cacheKey = treeDataSourceId + "#" + category.toString();
        HashMap<String, String> value2IconKey = (HashMap<String, String>)nedisCache.get(cacheKey, HashMap.class);
        if (value2IconKey == null) {
            value2IconKey = new HashMap<String, String>();
            nedisCache.put(cacheKey, value2IconKey);
            for (IconSourceScheme scheme : schemes) {
                value2IconKey.putAll(this.getSchemeValue2IconKeyMap(scheme));
            }
        } else {
            for (Map.Entry entry : value2IconKey.entrySet()) {
                String iconKey = (String)entry.getValue();
                this.key2iconMap.put(iconKey, this.getCacheIcon(iconKey));
            }
            for (IconSourceScheme scheme : schemes) {
                if (scheme.canBeCached()) continue;
                value2IconKey.putAll(this.getSchemeValue2IconKeyMap(scheme));
            }
        }
        return value2IconKey;
    }

    private Map<String, String> getSchemeValue2IconKeyMap(IconSourceScheme scheme) {
        HashMap<String, String> value2IconKey = new HashMap<String, String>();
        String sourceId = scheme.getSchemeId();
        for (String value : scheme.getValues()) {
            String iconKey;
            String base64Icon;
            IconSource iconSource = scheme.getIconSource(value);
            if (null == iconSource || !StringUtils.isNotEmpty((String)(base64Icon = this.getBase64Icon(iconKey = this.getIconKey(sourceId, value), iconSource, scheme.canBeCached())))) continue;
            this.key2iconMap.put(iconKey, base64Icon);
            value2IconKey.put(value, iconKey);
        }
        return value2IconKey;
    }

    private Map<IconCategory, IconSourceScheme[]> toCategoryMap(IconSourceScheme[] schemes) {
        HashMap<IconCategory, IconSourceScheme[]> map = new HashMap<IconCategory, IconSourceScheme[]>();
        map.put(IconCategory.NODE_ICONS, new IconSourceScheme[]{new IconSourceSchemeOfDefault()});
        if (schemes != null) {
            for (IconSourceScheme scheme : schemes) {
                IconCategory category = scheme.getCategory();
                IconSourceScheme[] subs = (IconSourceScheme[])map.get((Object)category);
                if (subs == null) {
                    subs = new IconSourceScheme[]{scheme};
                    map.put(category, subs);
                    continue;
                }
                subs = Arrays.copyOf(subs, subs.length + 1);
                subs[subs.length - 1] = scheme;
                map.put(category, subs);
            }
        }
        return map;
    }

    private String getBase64Icon(String iconKey, IconSource iconSource, boolean canBeCached) {
        if (this.existInCache(iconKey)) {
            return this.getCacheIcon(iconKey);
        }
        if (canBeCached) {
            String base64Icon = this.helper.toImgSrc(iconSource);
            if (StringUtils.isNotEmpty((String)base64Icon)) {
                this.putIcon2Cache(iconKey, base64Icon);
            }
            return base64Icon;
        }
        return iconSource.getImgSrc();
    }

    private String getCacheIcon(String iconKey) {
        return (String)this.nedisCache.get(iconKey, String.class);
    }

    private boolean existInCache(String iconKey) {
        return iconKey != null && this.nedisCache.exists(iconKey);
    }

    private void putIcon2Cache(String iconKey, String base64Icon) {
        this.nedisCache.put(iconKey, (Object)base64Icon);
    }

    public String getBase64Icon(String schemeId, String value) {
        return this.getCacheIcon(this.getIconKey(schemeId, value));
    }

    public boolean existInCache(String schemeId, String value) {
        return this.existInCache(this.getIconKey(schemeId, value));
    }
}

