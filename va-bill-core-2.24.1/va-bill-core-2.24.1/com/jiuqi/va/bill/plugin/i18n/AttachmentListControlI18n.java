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
public class AttachmentListControlI18n
implements I18nControl {
    public String getName() {
        return "v-upload-list";
    }

    public String getTitle() {
        return "\u9644\u4ef6\u5217\u8868";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        List colList = (List)control.getProps().get("tableConfig");
        if (colList == null || colList.size() <= 0) {
            return pluginResourceList;
        }
        String controlId = control.getId().toString();
        for (Map col : colList) {
            String colProp = col.get("prop").toString();
            VaI18nResourceItem tabItem = new VaI18nResourceItem();
            tabItem.setName(colProp);
            tabItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#" + colProp);
            tabItem.setTitle(col.get("label").toString());
            pluginResourceList.add(tabItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        List colList = (List)control.get("tableConfig");
        if (colList == null || colList.size() <= 0) {
            return keys;
        }
        for (Map col : colList) {
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + control.get("id").toString() + "#" + col.get("prop").toString());
            keys.add("VA#bill#BillCommonResource#AttachmentList#" + col.get("prop").toString());
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        List colList = (List)control.get("tableConfig");
        if (colList == null || colList.size() <= 0) {
            return;
        }
        String controlId = control.get("id").toString();
        for (Map col : colList) {
            String key = modelDefine.getName() + "#" + schemeName + "#" + controlId + "#" + col.get("prop").toString();
            String i18nValue = i18nValueMap.get(key);
            if (StringUtils.hasText(i18nValue)) {
                col.put("label", i18nValue);
                continue;
            }
            String commonI18nValue = i18nValueMap.get("VA#bill#BillCommonResource#AttachmentList#" + col.get("prop").toString());
            if (!StringUtils.hasText(commonI18nValue)) continue;
            col.put("label", commonI18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return BillCoreI18nUtil.getI18nResourceByControlName(template, controlName);
    }
}

