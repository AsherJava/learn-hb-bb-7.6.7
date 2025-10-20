/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.I18nAction
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.biz.intf.model.I18nAction;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserDefineActionI18n
implements I18nAction {
    public String getName() {
        return "bill-userdefine";
    }

    public String getTitle() {
        return "\u81ea\u5b9a\u4e49\u6309\u94ae";
    }

    public List<VaI18nResourceItem> getI18nResource(Map<String, Object> action, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        if (action.get("action") == null) {
            return pluginResourceList;
        }
        Map actionProp = (Map)action.get("action");
        if (actionProp.get("params") == null) {
            return pluginResourceList;
        }
        String actionId = action.get("id").toString();
        Map params = (Map)actionProp.get("params");
        VaI18nResourceItem paramItem = new VaI18nResourceItem();
        paramItem.setName(actionId);
        paramItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + actionId + "#prop#tips");
        paramItem.setTitle(params.get("tips").toString());
        pluginResourceList.add(paramItem);
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> action, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        if (action.get("action") == null) {
            return keys;
        }
        Map actionProp = (Map)action.get("action");
        if (actionProp.get("params") == null) {
            return keys;
        }
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + action.get("id").toString() + "#prop#tips");
        return keys;
    }

    public void processForI18n(Map<String, Object> action, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        if (action.get("action") == null) {
            return;
        }
        Map actionProp = (Map)action.get("action");
        if (actionProp.get("params") == null) {
            return;
        }
        Map params = (Map)actionProp.get("params");
        String key = modelDefine.getName() + "#" + schemeName + "#" + action.get("id").toString() + "#prop#tips";
        String i18nValue = i18nValueMap.get(key);
        if (StringUtils.hasText(i18nValue)) {
            params.put("tips", i18nValue);
        }
    }
}

