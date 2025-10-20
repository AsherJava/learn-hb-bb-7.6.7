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
public class TabsControlI18n
implements I18nControl {
    public String getName() {
        return "v-tabs";
    }

    public String getTitle() {
        return "\u9875\u7b7e";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        List tabList = (List)control.getProps().get("children");
        if (tabList == null || tabList.size() <= 0) {
            return pluginResourceList;
        }
        for (Map tab : tabList) {
            if (!"v-tabs-item".equals(tab.get("type"))) continue;
            String tabId = tab.get("id").toString();
            VaI18nResourceItem tabItem = new VaI18nResourceItem();
            tabItem.setName(tabId);
            tabItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + tabId);
            tabItem.setTitle(tab.get("title").toString());
            pluginResourceList.add(tabItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        List tabList = (List)control.get("children");
        if (tabList == null || tabList.size() <= 0) {
            return keys;
        }
        for (Map tab : tabList) {
            if (!"v-tabs-item".equals(tab.get("type"))) continue;
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + tab.get("id").toString());
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        List tabList = (List)control.get("children");
        if (tabList == null || tabList.size() <= 0) {
            return;
        }
        for (Map tab : tabList) {
            String key;
            String i18nValue;
            if (!"v-tabs-item".equals(tab.get("type")) || !StringUtils.hasText(i18nValue = i18nValueMap.get(key = modelDefine.getName() + "#" + schemeName + "#" + tab.get("id").toString()))) continue;
            tab.put("title", i18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

