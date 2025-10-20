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
public class TipsControlI18n
implements I18nControl {
    public String getName() {
        return "v-tips";
    }

    public String getTitle() {
        return "\u63d0\u793a\u4fe1\u606f";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        String controlId = control.getId().toString();
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        if (control.getProps().get("title") != null) {
            VaI18nResourceItem titleItem = new VaI18nResourceItem();
            titleItem.setName(controlId);
            titleItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#title");
            titleItem.setTitle(control.getProps().get("title").toString());
            pluginResourceList.add(titleItem);
        }
        if (control.getProps().get("content") != null) {
            VaI18nResourceItem contentItem = new VaI18nResourceItem();
            contentItem.setName(controlId);
            contentItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#content");
            contentItem.setTitle(control.getProps().get("content").toString());
            pluginResourceList.add(contentItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        String controlId = control.get("id").toString();
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#title");
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#content");
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        String contentI18nValue;
        String titleKey = modelDefine.getName() + "#" + schemeName + "#" + control.get("id").toString() + "#title";
        String contentKey = modelDefine.getName() + "#" + schemeName + "#" + control.get("id").toString() + "#content";
        String titleI18nValue = i18nValueMap.get(titleKey);
        if (StringUtils.hasText(titleI18nValue)) {
            control.put("title", titleI18nValue);
        }
        if (StringUtils.hasText(contentI18nValue = i18nValueMap.get(contentKey))) {
            control.put("content", contentI18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

