/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface I18nControl
extends NamedElement {
    public String getTitle();

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> var1, String var2);

    default public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        return new ArrayList<VaI18nResourceItem>();
    }

    default public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName, String parentId, String requestResourceType) {
        return this.getI18nResource(control, modelDefine, schemeName);
    }

    default public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
    }

    default public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        return new ArrayList<String>();
    }
}

