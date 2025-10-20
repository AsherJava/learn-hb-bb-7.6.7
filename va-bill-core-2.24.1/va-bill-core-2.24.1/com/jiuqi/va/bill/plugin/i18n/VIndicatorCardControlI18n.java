/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.I18nControl
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  org.apache.shiro.util.CollectionUtils
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
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VIndicatorCardControlI18n
implements I18nControl {
    public String getName() {
        return "v-indicator-card-control";
    }

    public String getTitle() {
        return "\u591a\u6307\u6807\u5361";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        Map tabList = (Map)control.getProps().get("binding");
        if (CollectionUtils.isEmpty((Map)tabList)) {
            return pluginResourceList;
        }
        List cardDatas = (List)tabList.get("cardDatas");
        for (Map card : cardDatas) {
            String tabId = card.get("id").toString();
            VaI18nResourceItem tabItem = new VaI18nResourceItem();
            tabItem.setName(tabId);
            tabItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + tabId);
            tabItem.setTitle(card.get("name").toString());
            pluginResourceList.add(tabItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        Map tabList = (Map)control.get("binding");
        if (CollectionUtils.isEmpty((Map)tabList)) {
            return keys;
        }
        List cardDatas = (List)tabList.get("cardDatas");
        for (Map card : cardDatas) {
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + card.get("id").toString());
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        Map tabList = (Map)control.get("binding");
        if (CollectionUtils.isEmpty((Map)tabList)) {
            return;
        }
        List cardDatas = (List)tabList.get("cardDatas");
        for (Map card : cardDatas) {
            String key = modelDefine.getName() + "#" + schemeName + "#" + card.get("id").toString();
            String i18nValue = i18nValueMap.get(key);
            if (!StringUtils.hasText(i18nValue)) continue;
            card.put("name", i18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

