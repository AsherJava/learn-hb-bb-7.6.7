/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.va.query.template.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.va.query.template.enumerate.PluginEnum;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.FormulaPlugin;
import com.jiuqi.va.query.template.plugin.QueryPlugin;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryTemplate {
    private static final Logger logger = LoggerFactory.getLogger(QueryTemplate.class);
    private String id;
    private String code;
    private List<QueryPlugin> plugins;

    public List<QueryPlugin> getPlugins() {
        return this.plugins;
    }

    public void setPlugins(List<QueryPlugin> plugins) {
        this.plugins = plugins;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public <T> T getPluginByName(String name, Class<T> clazz) {
        if (name == null || name.trim().isEmpty() || clazz == null) {
            throw new IllegalArgumentException("Name and clazz must not be null or empty.");
        }
        if (!QueryPlugin.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz.getName() + " must be a subtype of QueryPlugin.");
        }
        Optional<QueryPlugin> first = this.plugins.stream().filter(queryPlugin -> queryPlugin.getName().equals(name)).findFirst();
        try {
            return clazz.cast(first.orElse(null));
        }
        catch (ClassCastException e) {
            logger.error("Error casting plugin to " + clazz.getName() + ": " + e.getMessage(), e);
            return null;
        }
    }

    @JsonIgnore
    public <T> T getPluginByClass(Class<T> clazz) {
        Optional<Object> first = this.plugins.stream().filter(clazz::isInstance).map(clazz::cast).findFirst();
        return first.orElse(null);
    }

    @JsonIgnore
    public DataSourcePlugin getDataSourceSet() {
        Optional<QueryPlugin> first = this.plugins.stream().filter(queryPlugin -> queryPlugin.getName().equals(PluginEnum.dataSource.name())).findFirst();
        return first.orElse(null);
    }

    @JsonIgnore
    public BaseInfoPlugin getBaseInfo() {
        Optional<QueryPlugin> first = this.plugins.stream().filter(queryPlugin -> queryPlugin.getName().equals(PluginEnum.baseInfo.name())).findFirst();
        return first.orElse(null);
    }

    @JsonIgnore
    public FormulaPlugin getFormula() {
        Optional<QueryPlugin> first = this.plugins.stream().filter(queryPlugin -> queryPlugin.getName().equals(PluginEnum.formula.name())).findFirst();
        return first.orElse(null);
    }
}

