/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.I18nControl
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.view.impl.ControlManagerImpl
 *  com.jiuqi.va.biz.view.intf.Composite
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.biz.intf.model.I18nControl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InputControlI18n
implements I18nControl {
    public String getName() {
        return "v-input";
    }

    public String getTitle() {
        return "\u5b57\u6bb5\u5f55\u5165";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName) {
        Object information;
        String controlId = control.getId().toString();
        ArrayList<VaI18nResourceItem> pluginResourceList = new ArrayList<VaI18nResourceItem>();
        Object defaultInfo = control.getProps().get("defaultInfo");
        if (defaultInfo != null) {
            VaI18nResourceItem defaultInfoItem = new VaI18nResourceItem();
            defaultInfoItem.setName(controlId);
            defaultInfoItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#defaultInfo");
            defaultInfoItem.setTitle(defaultInfo.toString());
            pluginResourceList.add(defaultInfoItem);
        }
        if ((information = control.getProps().get("information")) != null) {
            VaI18nResourceItem infoItem = new VaI18nResourceItem();
            infoItem.setName(controlId);
            infoItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#info");
            infoItem.setTitle(information.toString());
            pluginResourceList.add(infoItem);
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        String controlId = control.get("id").toString();
        ArrayList<String> keys = new ArrayList<String>();
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#defaultInfo");
        keys.add(modelDefine.getName() + "#" + schemeName + "#" + controlId + "#info");
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        String informationI18nValue;
        String controlId = control.get("id").toString();
        String defaultInfoKey = modelDefine.getName() + "#" + schemeName + "#" + controlId + "#defaultInfo";
        String informationKey = modelDefine.getName() + "#" + schemeName + "#" + controlId + "#info";
        String defaultInfoI18nValue = i18nValueMap.get(defaultInfoKey);
        if (StringUtils.hasText(defaultInfoI18nValue)) {
            control.put("defaultInfo", defaultInfoI18nValue);
        }
        if (StringUtils.hasText(informationI18nValue = i18nValueMap.get(informationKey))) {
            control.put("information", informationI18nValue);
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        return InputControlI18n.getI18nResourceByControlName(template, controlName);
    }

    public static List<VaI18nResourceItem> getI18nResourceByControlName(Map<String, Object> template, String controlName) {
        ArrayList<VaI18nResourceItem> controlResourceList = new ArrayList<VaI18nResourceItem>();
        Composite composite = (Composite)ControlManagerImpl.createControl(template);
        ArrayList<Control> list = new ArrayList<Control>();
        InputControlI18n.parseBillDefine(list, composite, controlName);
        if (list.size() > 0) {
            for (Control control : list) {
                String controlId = control.getId().toString();
                VaI18nResourceItem controlItem = new VaI18nResourceItem();
                controlItem.setName(controlId);
                if (control.getProps().containsKey("binding")) {
                    Map binding = (Map)control.getProps().get("binding");
                    Object fieldName = binding.get("fieldName");
                    if (fieldName != null && StringUtils.hasText(fieldName.toString())) {
                        controlItem.setTitle(fieldName.toString());
                    } else {
                        controlItem.setTitle(controlId);
                    }
                } else {
                    controlItem.setTitle(controlId);
                }
                controlResourceList.add(controlItem);
            }
        }
        return controlResourceList;
    }

    public static void parseBillDefine(List<Control> list, Composite composite, String controlName) {
        List controls = composite.getChildren();
        for (Control control : controls) {
            if (controlName.equals(control.getType())) {
                list.add(control);
            }
            if (!(control instanceof Composite)) continue;
            InputControlI18n.parseBillDefine(list, (Composite)control, controlName);
        }
    }
}

