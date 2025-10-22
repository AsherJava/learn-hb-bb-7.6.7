/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.formtype.internal.impl;

import com.jiuqi.nr.formtype.common.FormTypeConsts;
import com.jiuqi.nr.formtype.internal.icon.CommonIconSource;
import com.jiuqi.nr.formtype.internal.icon.DefaultIconSource;
import com.jiuqi.nr.formtype.internal.icon.IconSourceProvider;
import com.jiuqi.nr.formtype.service.IUnitTreeIconStorage;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeIconStorageImpl
implements IUnitTreeIconStorage {
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    private static final Map<String, Map<String, String>> CACHE = new ConcurrentHashMap<String, Map<String, String>>();

    private String getIconFolder() {
        String value = this.systemOptionService.get("form_type_option_id", "formtype_option_iconscheme");
        if ("1".equals(value)) {
            return FormTypeConsts.ICONS_FOLDER.get("1");
        }
        return FormTypeConsts.ICONS_FOLDER.get("0");
    }

    @Override
    public String getBase64Icon(String schemeKey, String key) {
        IconSourceProvider provider = this.getIconProvider(schemeKey);
        String base64Icon = null;
        try {
            base64Icon = provider.getBase64Icon(key);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return base64Icon;
    }

    private Set<String> getIconKeys(String schemeKey) {
        HashSet<String> keys = new HashSet<String>();
        IconSourceProvider provider = this.getIconProvider(schemeKey);
        try {
            Properties properties = provider.getProperties();
            if (null != properties) {
                properties.keySet().forEach(k -> keys.add(k.toString()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return keys;
    }

    private IconSourceProvider getIconProvider(String schemeKey) {
        if (FormTypeConsts.ICONS_FOLDER.containsValue(schemeKey)) {
            return new CommonIconSource(schemeKey, schemeKey, null);
        }
        return new DefaultIconSource();
    }

    @Override
    public Map<String, String> getAllBase64Icon() {
        return this.getAllBase64Icon(this.getIconFolder());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Map<String, String> getAllBase64Icon(String schemeKey) {
        if (CACHE.containsKey(schemeKey)) {
            return CACHE.get(schemeKey);
        }
        Map<String, Map<String, String>> map = CACHE;
        synchronized (map) {
            if (CACHE.containsKey(schemeKey)) {
                return CACHE.get(schemeKey);
            }
            HashMap<String, String> allIcon = new HashMap<String, String>();
            Set<String> iconKeys = this.getIconKeys(schemeKey);
            for (String key : iconKeys) {
                allIcon.put(key, this.getBase64Icon(schemeKey, key));
            }
            Map<String, String> map2 = Collections.unmodifiableMap(allIcon);
            CACHE.put(schemeKey, map2);
            return map2;
        }
    }
}

