/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.front.FrontPluginDefine
 *  com.jiuqi.va.biz.front.FrontViewDefine
 *  com.jiuqi.va.biz.intf.model.I18nControl
 *  com.jiuqi.va.biz.intf.model.I18nPlugin
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.view.impl.ControlManagerImpl
 *  com.jiuqi.va.biz.view.impl.ViewDefineImpl
 *  com.jiuqi.va.biz.view.intf.Composite
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontViewDefine;
import com.jiuqi.va.biz.intf.model.I18nControl;
import com.jiuqi.va.biz.intf.model.I18nPlugin;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class ViewPluginI18n
implements I18nPlugin {
    @Autowired(required=false)
    private final List<I18nControl> controlList = new ArrayList<I18nControl>();

    public String getName() {
        return "view";
    }

    public String getTitle() {
        return "\u754c\u9762";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return ViewDefineImpl.class;
    }

    public boolean isGroup() {
        return true;
    }

    public boolean isFrontTrans() {
        return true;
    }

    public List<VaI18nResourceItem> getI18nResource(PluginDefine pluginDefine, ModelDefine modelDefine, String parentId, String requestResourceType) {
        ArrayList<VaI18nResourceItem> pluginResourceList;
        block11: {
            String[] nodes;
            List schemes;
            block14: {
                String parentNode;
                block13: {
                    block12: {
                        pluginResourceList = new ArrayList<VaI18nResourceItem>();
                        ViewDefineImpl viewDefine = (ViewDefineImpl)pluginDefine;
                        schemes = viewDefine.getSchemes();
                        if (schemes == null || schemes.size() <= 0) {
                            return pluginResourceList;
                        }
                        nodes = parentId.split("#");
                        parentNode = nodes[nodes.length - 1];
                        if (!parentId.endsWith("&plugin")) break block12;
                        for (Map scheme : schemes) {
                            Object type = scheme.get("type");
                            if (type != null && !type.toString().equals("normal")) continue;
                            VaI18nResourceItem schemeItem = new VaI18nResourceItem();
                            schemeItem.setName(scheme.get("code") + "&scheme");
                            schemeItem.setTitle(scheme.get("title") != null ? scheme.get("title").toString() : "");
                            schemeItem.setGroupFlag("category".equals(requestResourceType));
                            schemeItem.setCategoryFlag(true);
                            schemeItem.setUniqueName(modelDefine.getName() + "&define#view&plugin#" + scheme.get("code") + "&scheme");
                            pluginResourceList.add(schemeItem);
                        }
                        break block11;
                    }
                    if (!parentId.endsWith("&scheme")) break block13;
                    for (I18nControl control : this.controlList) {
                        VaI18nResourceItem controlItem = new VaI18nResourceItem();
                        controlItem.setName(control.getName() + "&control");
                        controlItem.setTitle(control.getTitle());
                        controlItem.setGroupFlag(true);
                        controlItem.setCategoryFlag(true);
                        pluginResourceList.add(controlItem);
                    }
                    break block11;
                }
                if (!parentId.endsWith("&control")) break block14;
                String controlName = parentNode.split("&")[0];
                String schemeName = "";
                for (String node : nodes) {
                    if (!node.contains("&scheme")) continue;
                    schemeName = node.split("&")[0];
                }
                for (Map scheme : schemes) {
                    if (!scheme.get("code").equals(schemeName)) continue;
                    for (I18nControl control : this.controlList) {
                        if (!control.getName().equals(controlName)) continue;
                        Map template = (Map)scheme.get("template");
                        return control.getI18nControList(template, controlName);
                    }
                }
                break block11;
            }
            if (!parentId.contains("&control")) break block11;
            String controlName = "";
            String schemeName = "";
            int controlIndex = 1;
            for (String node : nodes) {
                if (node.contains("&scheme")) {
                    schemeName = node.split("&")[0];
                }
                if (node.contains("&control")) {
                    controlName = node.split("&")[0];
                    break;
                }
                ++controlIndex;
            }
            for (Map scheme : schemes) {
                if (!scheme.get("code").equals(schemeName)) continue;
                Map template = (Map)scheme.get("template");
                Composite composite = (Composite)ControlManagerImpl.createControl((Map)template);
                Control control = this.getControl(composite, controlName, nodes[controlIndex]);
                if (control == null) break;
                for (I18nControl i18nControl : this.controlList) {
                    if (!i18nControl.getName().equals(controlName)) continue;
                    return i18nControl.getI18nResource(control, modelDefine, schemeName, parentId, requestResourceType);
                }
                break;
            }
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(PluginDefine pluginDefine, ModelDefine modelDefine) {
        List<String> controlKeys;
        ArrayList<String> keys = new ArrayList<String>();
        ViewDefineImpl viewDefine = (ViewDefineImpl)pluginDefine;
        List schemes = viewDefine.getSchemes();
        if (schemes == null || schemes.size() <= 0) {
            return keys;
        }
        Composite currTemplate = viewDefine.getTemplate();
        UUID currTemplateId = currTemplate.getId();
        String currTemplateSchemeName = "";
        for (Map scheme : schemes) {
            List<String> controlKeys2;
            keys.add(modelDefine.getName() + "&define#view&plugin#" + scheme.get("code") + "&scheme");
            String schemeName = scheme.get("code").toString();
            Map template = (Map)scheme.get("template");
            if (ObjectUtils.isEmpty(template) || ObjectUtils.isEmpty(template.get("id"))) continue;
            if (template.get("id").toString().equals(currTemplateId.toString())) {
                currTemplateSchemeName = schemeName;
            }
            if ((controlKeys2 = this.getControlI18nKeys(template, modelDefine, schemeName)).size() <= 0) continue;
            keys.addAll(controlKeys2);
        }
        if (StringUtils.hasText(currTemplateSchemeName) && (controlKeys = this.getControlI18nKeys(currTemplate.getProps(), modelDefine, currTemplateSchemeName)).size() > 0) {
            keys.addAll(controlKeys);
        }
        return keys;
    }

    private List<String> getControlI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        if (!control.containsKey("children")) {
            return keys;
        }
        List children = (List)control.get("children");
        if (children == null || children.size() <= 0) {
            return keys;
        }
        for (Map child : children) {
            for (I18nControl i18nControl : this.controlList) {
                List controlI18nKeys;
                if (!i18nControl.getName().equals(child.get("type")) || (controlI18nKeys = i18nControl.getAllI18nKeys(child, modelDefine, schemeName)) == null || controlI18nKeys.size() <= 0) continue;
                keys.addAll(controlI18nKeys);
            }
            List<String> controlI18nKeys = this.getControlI18nKeys(child, modelDefine, schemeName);
            if (controlI18nKeys == null || controlI18nKeys.size() <= 0) continue;
            keys.addAll(controlI18nKeys);
        }
        return keys;
    }

    private void processControlForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        if (!control.containsKey("children")) {
            return;
        }
        List children = (List)control.get("children");
        if (children == null || children.size() <= 0) {
            return;
        }
        for (Map child : children) {
            for (I18nControl i18nControl : this.controlList) {
                if (!i18nControl.getName().equals(child.get("type"))) continue;
                i18nControl.processForI18n(child, modelDefine, schemeName, i18nValueMap);
            }
            this.processControlForI18n(child, modelDefine, schemeName, i18nValueMap);
        }
    }

    public void processForI18n(FrontPluginDefine frontPluginDefine, ModelDefine modelDefine, Map<String, String> i18nValueMap) {
        FrontViewDefine viewDefine = (FrontViewDefine)frontPluginDefine;
        if (viewDefine.isWizard()) {
            Map template = viewDefine.getTemplate();
            Map viewTileInfo = viewDefine.getViewTileInfo();
            if (template != null) {
                for (Map.Entry entry : template.entrySet()) {
                    String i18Title = i18nValueMap.get(modelDefine.getName() + "&define#view&plugin#" + (String)entry.getKey() + "&scheme");
                    if (StringUtils.hasText(i18Title)) {
                        viewTileInfo.put(entry.getKey(), i18Title);
                    }
                    this.processControlForI18n((Map)entry.getValue(), modelDefine, (String)entry.getKey(), i18nValueMap);
                }
            }
        } else {
            this.processControlForI18n(viewDefine.getTemplate(), modelDefine, viewDefine.getSchemeCode(), i18nValueMap);
        }
    }

    private Control getControl(Composite composite, String controlName, String controlId) {
        List controls = composite.getChildren();
        for (Control control : controls) {
            Control currControl;
            if (controlName.equals(control.getType()) && controlId.equals(control.getId().toString())) {
                return control;
            }
            if (!(control instanceof Composite) || (currControl = this.getControl((Composite)control, controlName, controlId)) == null) continue;
            return currControl;
        }
        return null;
    }
}

