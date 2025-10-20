/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeName
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@JsonTypeName(value="viewSet")
public class ViewSetPlugin
implements QueryPlugin {
    private Map<String, Object> viewSets = new HashMap<String, Object>();

    @Override
    public String getName() {
        return "viewSet";
    }

    @Override
    public String getTitle() {
        return "\u754c\u9762\u8bbe\u7f6e";
    }

    @Override
    public int getSortNum() {
        return 5;
    }

    public Map<String, Object> getViewSets() {
        return this.viewSets;
    }

    public void setViewSets(Map<String, Object> viewSets) {
        this.viewSets = viewSets;
    }

    public void putViewSet(String key, Object value) {
        this.viewSets.put(key, value);
    }

    public Object getViewSet(String key) {
        return this.viewSets.get(key);
    }
}

