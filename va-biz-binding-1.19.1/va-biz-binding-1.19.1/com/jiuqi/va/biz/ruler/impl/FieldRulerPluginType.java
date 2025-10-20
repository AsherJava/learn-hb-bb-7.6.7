/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.impl.model.PluginTypeBase;

public abstract class FieldRulerPluginType
extends PluginTypeBase {
    public static final String NAME = "fieldRuler";
    public static final String TITLE = "\u5b57\u6bb5\u89c4\u5219";

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

