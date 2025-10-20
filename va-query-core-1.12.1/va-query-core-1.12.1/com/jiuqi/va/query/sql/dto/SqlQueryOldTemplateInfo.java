/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.collections4.map.CaseInsensitiveMap
 */
package com.jiuqi.va.query.sql.dto;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

public class SqlQueryOldTemplateInfo {
    Map<String, CaseInsensitiveMap<String, Info>> map = new HashMap<String, CaseInsensitiveMap<String, Info>>();

    public SqlQueryOldTemplateInfo append(PluginEnum pluginEnum, String name, String title) {
        String key = pluginEnum.name();
        CaseInsensitiveMap fieldsMap = this.map.computeIfAbsent(key, k -> new CaseInsensitiveMap());
        fieldsMap.put((Object)name, (Object)new Info(name, title));
        return this;
    }

    public boolean containsKey(PluginEnum pluginEnum, String name) {
        String key = pluginEnum.name();
        if (!this.map.containsKey(key)) {
            return false;
        }
        return this.map.get(key).containsKey((Object)name);
    }

    public Info get(PluginEnum pluginEnum, String name) {
        String key = pluginEnum.name();
        if (!this.map.containsKey(key)) {
            return null;
        }
        return (Info)this.map.get(key).get((Object)name);
    }

    public static class Info {
        private String name;
        private String title;

        public Info(String name, String title) {
            this.name = name;
            this.title = title;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static enum PluginEnum {
        PARAMS,
        FIELDS;

    }
}

