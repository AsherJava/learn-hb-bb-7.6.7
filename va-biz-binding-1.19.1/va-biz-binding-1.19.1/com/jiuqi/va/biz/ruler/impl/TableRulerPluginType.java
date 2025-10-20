/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.impl.model.PluginTypeBase;

public abstract class TableRulerPluginType
extends PluginTypeBase {
    public static final String NAME = "tableRuler";
    public static final String TITLE = "\u5b50\u8868\u89c4\u5219";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String[] getDependPlugins() {
        return new String[]{"ruler"};
    }
}

