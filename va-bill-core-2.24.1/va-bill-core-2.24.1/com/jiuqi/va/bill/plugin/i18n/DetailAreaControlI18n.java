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
public class DetailAreaControlI18n
implements I18nControl {
    public String getName() {
        return "v-detail-area";
    }

    public String getTitle() {
        return "\u5b50\u8868\u533a\u57df";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        String controlId = control.getId().toString();
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        VaI18nResourceItem detailAreaItem = new VaI18nResourceItem();
        detailAreaItem.setName(controlId);
        detailAreaItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId);
        detailAreaItem.setTitle(control.getProps().get("title").toString());
        pluginResourceList.add(detailAreaItem);
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + control.get("id").toString());
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        String key = modelDefine.getName() + "#" + schemeName + "#" + control.get("id").toString();
        String i18nValue = i18nValueMap.get(key);
        if (StringUtils.hasText(i18nValue)) {
            control.put("title", i18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

