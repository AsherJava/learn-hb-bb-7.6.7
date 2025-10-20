/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface I18nPrintControl
extends NamedElement {
    public String getTitle();

    default public List<VaI18nResourceItem> getI18nResource(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        return new ArrayList<VaI18nResourceItem>();
    }

    default public void processForI18n(PluginDefine pluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
    }

    default public List<String> getAllI18nKeys(PluginDefine pluginDefine, ModelDefine modelDefine) {
        return new ArrayList<String>();
    }
}

