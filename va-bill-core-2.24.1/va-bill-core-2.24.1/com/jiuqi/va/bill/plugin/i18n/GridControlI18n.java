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
public class GridControlI18n
implements I18nControl {
    public String getName() {
        return "v-grid";
    }

    public String getTitle() {
        return "\u8868\u683c\u5f55\u5165";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        if (control.getProps().get("binding") == null) {
            return pluginResourceList;
        }
        Map binding = (Map)control.getProps().get("binding");
        if (binding.get("fields") == null) {
            return pluginResourceList;
        }
        List fields = (List)binding.get("fields");
        if (fields.size() <= 0) {
            return pluginResourceList;
        }
        String controlId = control.getId().toString();
        for (Map field : fields) {
            VaI18nResourceItem buttonItem = new VaI18nResourceItem();
            buttonItem.setName(controlId);
            buttonItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#" + field.get("name").toString());
            buttonItem.setTitle(field.get("title").toString());
            pluginResourceList.add(buttonItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        if (control.get("binding") == null) {
            return keys;
        }
        Map binding = (Map)control.get("binding");
        if (binding.get("fields") == null) {
            return keys;
        }
        List fields = (List)binding.get("fields");
        if (fields.size() <= 0) {
            return keys;
        }
        String controlId = control.get("id").toString();
        for (Map field : fields) {
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#" + field.get("name").toString());
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        if (control.get("binding") == null) {
            return;
        }
        Map binding = (Map)control.get("binding");
        if (binding.get("fields") == null) {
            return;
        }
        List fields = (List)binding.get("fields");
        if (fields.size() <= 0) {
            return;
        }
        String controlId = control.get("id").toString();
        for (Map field : fields) {
            String key = modelDefine.getName() + "#" + schemeName + "#" + controlId + "#" + field.get("name").toString();
            String i18nValue = i18nValueMap.get(key);
            if (!StringUtils.hasText(i18nValue)) continue;
            field.put("title", i18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

