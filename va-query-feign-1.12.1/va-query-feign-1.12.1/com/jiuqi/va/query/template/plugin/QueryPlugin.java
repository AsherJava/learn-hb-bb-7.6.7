/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.va.query.template.plugin;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.va.query.template.plugin.BaseInfoPlugin;
import com.jiuqi.va.query.template.plugin.DataSourcePlugin;
import com.jiuqi.va.query.template.plugin.FormulaPlugin;
import com.jiuqi.va.query.template.plugin.QueryFieldsPlugin;
import com.jiuqi.va.query.template.plugin.QueryPrintPlugin;
import com.jiuqi.va.query.template.plugin.QueryRelatePlugin;
import com.jiuqi.va.query.template.plugin.ToolBarPlugin;
import com.jiuqi.va.query.template.plugin.ViewDesignPlugin;
import com.jiuqi.va.query.template.plugin.ViewSetPlugin;
import com.jiuqi.va.query.template.vo.QueryPluginCheckVO;
import com.jiuqi.va.query.template.vo.QueryTemplate;

@JsonSubTypes(value={@JsonSubTypes.Type(value=BaseInfoPlugin.class), @JsonSubTypes.Type(value=DataSourcePlugin.class), @JsonSubTypes.Type(value=ToolBarPlugin.class), @JsonSubTypes.Type(value=QueryFieldsPlugin.class), @JsonSubTypes.Type(value=ViewSetPlugin.class), @JsonSubTypes.Type(value=QueryRelatePlugin.class), @JsonSubTypes.Type(value=ViewDesignPlugin.class), @JsonSubTypes.Type(value=FormulaPlugin.class), @JsonSubTypes.Type(value=QueryPrintPlugin.class)})
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="name")
public interface QueryPlugin {
    public String getName();

    public String getTitle();

    default public void initPlugin() {
    }

    default public int getSortNum() {
        return 99;
    }

    default public QueryPluginCheckVO checkPlugin(QueryPlugin queryPlugin, QueryTemplate queryTemplate) {
        return new QueryPluginCheckVO();
    }

    default public boolean isHidden() {
        return false;
    }
}

