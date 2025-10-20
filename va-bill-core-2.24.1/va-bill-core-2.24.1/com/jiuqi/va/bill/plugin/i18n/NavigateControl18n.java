/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.I18nControl
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.model.I18nControl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NavigateControl18n
implements I18nControl {
    public String getName() {
        return "v-navigate";
    }

    public String getTitle() {
        return "\u5bfc\u822a\u9762\u677f";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        List items = (List)control.getProps().get("items");
        if (items == null || items.size() <= 0) {
            return pluginResourceList;
        }
        for (Map item : items) {
            String itemId = item.get("id").toString();
            VaI18nResourceItem navigateItem = new VaI18nResourceItem();
            navigateItem.setName(itemId);
            navigateItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + itemId);
            navigateItem.setTitle(item.get("title").toString());
            pluginResourceList.add(navigateItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        List items = (List)control.get("items");
        if (items == null || items.size() <= 0) {
            return keys;
        }
        for (Map item : items) {
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + item.get("id").toString());
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        List items = (List)control.get("items");
        if (items == null || items.size() <= 0) {
            return;
        }
        for (Map item : items) {
            String key = modelDefine.getName() + "#" + schemeName + "#" + item.get("id").toString();
            String i18nValue = i18nValueMap.get(key);
            if (!StringUtils.hasText(i18nValue)) continue;
            item.put("title", i18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

