/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.model.I18nAction
 *  com.jiuqi.va.biz.intf.model.I18nControl
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.view.impl.ControlManagerImpl
 *  com.jiuqi.va.biz.view.intf.Composite
 *  com.jiuqi.va.biz.view.intf.Control
 *  com.jiuqi.va.i18n.domain.VaI18nResourceItem
 *  org.apache.commons.collections4.map.HashedMap
 */
package com.jiuqi.va.bill.plugin.i18n;

import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.model.I18nAction;
import com.jiuqi.va.biz.intf.model.I18nControl;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.view.impl.ControlManagerImpl;
import com.jiuqi.va.biz.view.intf.Composite;
import com.jiuqi.va.biz.view.intf.Control;
import com.jiuqi.va.i18n.domain.VaI18nResourceItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ToolbarControl18n
implements I18nControl {
    @Autowired(required=false)
    private final List<I18nAction> actionList = new ArrayList<I18nAction>();

    public String getName() {
        return "v-tool-bar";
    }

    public String getTitle() {
        return "\u5de5\u5177\u680f";
    }

    public List<VaI18nResourceItem> getI18nResource(Control control, ModelDefine modelDefine, String schemeName, String parentId, String requestResourceType) {
        ArrayList<VaI18nResourceItem> pluginResourceList;
        block16: {
            block14: {
                block15: {
                    pluginResourceList = new ArrayList<VaI18nResourceItem>();
                    if (!"resource".equals(requestResourceType)) break block14;
                    if (!parentId.endsWith(control.getId().toString())) break block15;
                    List actionList = (List)control.getProps().get("children");
                    if (actionList == null || actionList.size() <= 0) {
                        return pluginResourceList;
                    }
                    for (Map action : actionList) {
                        String actionId = action.get("id").toString();
                        VaI18nResourceItem actionItem = new VaI18nResourceItem();
                        actionItem.setName(actionId);
                        actionItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + actionId);
                        actionItem.setTitle(action.get("title").toString());
                        pluginResourceList.add(actionItem);
                        List childActionList = (List)action.get("children");
                        if (childActionList == null || childActionList.size() <= 0) continue;
                        for (Map childAction : childActionList) {
                            String childActionId = childAction.get("id").toString();
                            VaI18nResourceItem childActionItem = new VaI18nResourceItem();
                            childActionItem.setName(childActionId);
                            childActionItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + childActionId);
                            childActionItem.setTitle(childAction.get("title").toString());
                            pluginResourceList.add(childActionItem);
                        }
                    }
                    break block16;
                }
                if (!parentId.endsWith("&prop")) break block16;
                String[] nodes = parentId.split("#");
                String actionName = "";
                String actionId = nodes[nodes.length - 1].split("&")[0];
                for (String node : nodes) {
                    if (!node.contains("&action")) continue;
                    actionName = node.split("&")[0];
                }
                for (I18nAction i18nAction : this.actionList) {
                    if (!i18nAction.getName().equals(actionName)) continue;
                    List actionList = (List)control.getProps().get("children");
                    if (actionList == null || actionList.size() <= 0) {
                        return pluginResourceList;
                    }
                    for (Map action : actionList) {
                        if (!actionId.equals(action.get("id").toString())) continue;
                        return i18nAction.getI18nResource(action, modelDefine, schemeName);
                    }
                }
                break block16;
            }
            if ("category".equals(requestResourceType)) {
                if (parentId.endsWith(control.getId().toString())) {
                    for (I18nAction action : this.actionList) {
                        VaI18nResourceItem actionItem = new VaI18nResourceItem();
                        actionItem.setName(action.getName() + "&action");
                        actionItem.setTitle(action.getTitle());
                        actionItem.setGroupFlag(true);
                        actionItem.setCategoryFlag(true);
                        pluginResourceList.add(actionItem);
                    }
                } else if (parentId.endsWith("&action")) {
                    String[] nodes = parentId.split("#");
                    String parentNode = nodes[nodes.length - 1];
                    List actionList = (List)control.getProps().get("children");
                    if (actionList == null || actionList.size() <= 0) {
                        return pluginResourceList;
                    }
                    for (Map action : actionList) {
                        Map actionProp;
                        if (action.get("action") == null || (actionProp = (Map)action.get("action")).get("type") == null || !parentNode.split("&")[0].equals(actionProp.get("type").toString())) continue;
                        String actionId = action.get("id").toString();
                        VaI18nResourceItem actionItem = new VaI18nResourceItem();
                        actionItem.setName(actionId + "&prop");
                        actionItem.setUniqueName(modelDefine.getName() + "#" + schemeName + "#" + actionId + "#prop");
                        actionItem.setTitle(action.get("title").toString());
                        pluginResourceList.add(actionItem);
                    }
                }
            }
        }
        return pluginResourceList;
    }

    public List<String> getAllI18nKeys(Map<String, Object> control, ModelDefine modelDefine, String schemeName) {
        ArrayList<String> keys = new ArrayList<String>();
        List actionList = (List)control.get("children");
        if (actionList == null || actionList.size() <= 0) {
            return keys;
        }
        for (Map action : actionList) {
            Map actionProp;
            keys.add(modelDefine.getName() + "#" + schemeName + "#" + action.get("id").toString());
            if (action.get("action") == null || (actionProp = (Map)action.get("action")).get("type") == null) continue;
            keys.add("VA#bill#BillCommonResource#ToolBar#" + actionProp.get("type").toString());
            for (I18nAction i18nAction : this.actionList) {
                List actionKeys;
                if (!i18nAction.getName().equals(actionProp.get("type").toString()) || (actionKeys = i18nAction.getAllI18nKeys(action, modelDefine, schemeName)) == null || actionKeys.size() <= 0) continue;
                keys.addAll(actionKeys);
            }
            List childActionList = (List)action.get("children");
            if (childActionList == null || childActionList.size() <= 0) continue;
            for (Map childAction : childActionList) {
                Map childActionProp;
                keys.add(modelDefine.getName() + "#" + schemeName + "#" + childAction.get("id").toString());
                if (childAction.get("action") == null || (childActionProp = (Map)childAction.get("action")).get("type") == null) continue;
                keys.add("VA#bill#BillCommonResource#ToolBar#" + childActionProp.get("type").toString());
                for (I18nAction i18nAction : this.actionList) {
                    List actionKeys;
                    if (!i18nAction.getName().equals(childActionProp.get("type").toString()) || (actionKeys = i18nAction.getAllI18nKeys(childAction, modelDefine, schemeName)) == null || actionKeys.size() <= 0) continue;
                    keys.addAll(actionKeys);
                }
            }
        }
        return keys;
    }

    public void processForI18n(Map<String, Object> control, ModelDefine modelDefine, String schemeName, Map<String, String> i18nValueMap) {
        List actionList = (List)control.get("children");
        if (actionList == null || actionList.size() <= 0) {
            return;
        }
        HashedMap actionMap = new HashedMap();
        for (I18nAction i18nAction : this.actionList) {
            actionMap.put(i18nAction.getName(), i18nAction);
        }
        for (Map action : actionList) {
            List childActionList;
            I18nAction i18nAction;
            String commonI18nValue;
            Map actionProp;
            String key = modelDefine.getName() + "#" + schemeName + "#" + action.get("id").toString();
            String i18nValue = i18nValueMap.get(key);
            if (StringUtils.hasText(i18nValue)) {
                action.put("title", i18nValue);
            }
            if (action.get("action") == null || (actionProp = (Map)action.get("action")).get("type") == null) continue;
            if (!StringUtils.hasText(i18nValue) && StringUtils.hasText(commonI18nValue = i18nValueMap.get("VA#bill#BillCommonResource#ToolBar#" + actionProp.get("type").toString()))) {
                action.put("title", commonI18nValue);
            }
            if ((i18nAction = (I18nAction)actionMap.get(actionProp.get("type").toString())) != null) {
                i18nAction.processForI18n(action, modelDefine, schemeName, i18nValueMap);
            }
            if ((childActionList = (List)action.get("children")) == null || childActionList.size() <= 0) continue;
            for (Map childAction : childActionList) {
                I18nAction childI18nAction;
                String commonI18nValue2;
                Map childActionProp;
                String childKey = modelDefine.getName() + "#" + schemeName + "#" + childAction.get("id").toString();
                String childI18nValue = i18nValueMap.get(childKey);
                if (StringUtils.hasText(childI18nValue)) {
                    childAction.put("title", childI18nValue);
                }
                if (childAction.get("action") == null || (childActionProp = (Map)childAction.get("action")).get("type") == null) continue;
                if (!StringUtils.hasText(childI18nValue) && StringUtils.hasText(commonI18nValue2 = i18nValueMap.get("VA#bill#BillCommonResource#ToolBar#" + childActionProp.get("type").toString()))) {
                    childAction.put("title", commonI18nValue2);
                }
                if ((childI18nAction = (I18nAction)actionMap.get(childActionProp.get("type").toString())) == null) continue;
                childI18nAction.processForI18n(childAction, modelDefine, schemeName, i18nValueMap);
            }
        }
    }

    public List<VaI18nResourceItem> getI18nControList(Map<String, Object> template, String controlName) {
        ArrayList<VaI18nResourceItem> controlResourceList = new ArrayList<VaI18nResourceItem>();
        Composite composite = (Composite)ControlManagerImpl.createControl(template);
        ArrayList<Control> list = new ArrayList<Control>();
        BillCoreI18nUtil.parseBillDefine(list, composite, controlName);
        if (list.size() > 0) {
            for (Control control : list) {
                VaI18nResourceItem controlItem = new VaI18nResourceItem();
                controlItem.setName(control.getId().toString());
                if (control.getProps().containsKey("title")) {
                    controlItem.setTitle(control.getProps().get("title").toString());
                } else {
                    controlItem.setTitle(control.getId().toString());
                }
                controlItem.setGroupFlag(true);
                controlItem.setCategoryFlag(true);
                controlResourceList.add(controlItem);
            }
        }
        return controlResourceList;
    }
}

