/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.ruler.impl.EditableFieldsDefineImpl;

public abstract class EditableFieldsPluginType
extends PluginTypeBase {
    public static final String NAME = "editableFields";
    public static final String TITLE = "\u53ef\u7f16\u8f91\u5b57\u6bb5";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return EditableFieldsDefineImpl.class;
    }
}

