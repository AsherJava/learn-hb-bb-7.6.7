/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.view.impl;

import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.impl.ViewDefineImpl;
import com.jiuqi.va.biz.view.impl.ViewImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public abstract class ViewPluginType
extends PluginTypeBase {
    public static final String NAME = "view";
    public static final String TITLE = "\u754c\u9762";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    public Class<? extends ViewDefineImpl> getPluginDefineClass() {
        return ViewDefineImpl.class;
    }

    public Class<? extends ViewImpl> getPluginClass() {
        return ViewImpl.class;
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        ViewDefineImpl viewDefine = (ViewDefineImpl)pluginDefine;
        if (viewDefine.template != null) {
            ViewPluginType.resetId(viewDefine.template);
        }
        if (viewDefine.schemes != null) {
            this.initSchemeActions(viewDefine);
            this.createSchemeFields(viewDefine);
            HashMap<String, String> wizardFirstViews = new HashMap<String, String>();
            for (Map<String, Object> scheme : viewDefine.schemes) {
                if ("wizard".equals(scheme.get("type"))) {
                    List wizardInfo;
                    Map template = (Map)scheme.get("template");
                    if (CollectionUtils.isEmpty(template) || CollectionUtils.isEmpty(wizardInfo = (List)template.get("wizardInfo"))) continue;
                    String wizardFirstView = this.getWizardFirstView(wizardInfo);
                    if (((Boolean)scheme.get("default")).booleanValue()) {
                        viewDefine.setDefaultSchemeCode(wizardFirstView);
                    }
                    wizardFirstViews.put((String)scheme.get("code"), wizardFirstView);
                    continue;
                }
                if (!((Boolean)scheme.get("default")).booleanValue()) continue;
                viewDefine.setDefaultSchemeCode((String)scheme.get("code"));
            }
            viewDefine.setWizardFirstViews(wizardFirstViews);
        }
    }

    private void initSchemeActions(ViewDefineImpl viewDefine) {
        HashMap<String, Set<String>> schemeActions = new HashMap<String, Set<String>>();
        for (Map<String, Object> scheme : viewDefine.schemes) {
            String code = (String)scheme.get("code");
            Object type = scheme.get("type");
            if (type != null && type.equals("wizard")) continue;
            Map template = (Map)scheme.get("template");
            HashSet<String> actions = new HashSet<String>();
            schemeActions.put(code, actions);
            this.collectSchemeActions(template, actions);
        }
        viewDefine.schemeActions = schemeActions;
    }

    private void createSchemeFields(ViewDefineImpl viewDefine) {
        HashSet<String> views = new HashSet<String>();
        for (Map<String, Object> scheme : viewDefine.schemes) {
            Object type = scheme.get("type");
            String code = (String)scheme.get("code");
            if (type == null) {
                views.add(code);
                continue;
            }
            if (type.equals("wizard")) continue;
            views.add(code);
        }
        if (!views.isEmpty()) {
            this.handleInitBindingFields(views, viewDefine);
        }
    }

    private void handleInitBindingFields(Set<String> views, ViewDefineImpl viewDefine) {
        HashMap<String, Map<String, Set<String>>> schemeFields = new HashMap<String, Map<String, Set<String>>>();
        for (Map<String, Object> scheme : viewDefine.schemes) {
            String code = (String)scheme.get("code");
            if (!views.contains(code)) continue;
            Map tableFields = schemeFields.computeIfAbsent(code, key -> new HashMap());
            Map template = (Map)scheme.get("template");
            if (CollectionUtils.isEmpty(template)) continue;
            this.collectTableFields(template, tableFields);
        }
        viewDefine.schemeFields = schemeFields;
    }

    private void collectSchemeActions(Map<String, Object> props, Set<String> actions) {
        List childrens;
        Map action = (Map)props.get("action");
        if (action != null && !ObjectUtils.isEmpty(action.get("type"))) {
            actions.add(String.valueOf(action.get("type")));
        }
        if ((childrens = (List)props.get("children")) != null) {
            for (Map children : childrens) {
                this.collectSchemeActions(children, actions);
            }
        }
    }

    private static void resetId(Map<String, Object> props) {
        Object id = props.get("id");
        props.put("id", Utils.normalizeId(id));
        List prop_children = (List)props.get("children");
        if (prop_children != null) {
            prop_children.forEach(ViewPluginType::resetId);
        }
    }

    private void collectTableFields(Map<String, Object> props, Map<String, Set<String>> tableFields) {
        List children;
        Map binding1;
        Map binding = (Map)props.get("binding");
        if (binding != null) {
            this.addBindingFields(tableFields, binding);
        }
        if ((binding1 = (Map)props.get("binding1")) != null) {
            this.addBindingFields(tableFields, binding1);
        }
        if ((children = (List)props.get("children")) != null) {
            children.forEach(o -> this.collectTableFields((Map<String, Object>)o, tableFields));
        }
    }

    private void addBindingFields(Map<String, Set<String>> tableFields, Map<String, Object> binding) {
        String bindType = Convert.cast(binding.get("type"), String.class);
        String tableName = Convert.cast(binding.get("tableName"), String.class);
        if (Utils.isNotEmpty(tableName)) {
            List editFields;
            String fieldName;
            List viewFields;
            Object fields;
            if ("field-simple".equals(bindType) || ObjectUtils.isEmpty(bindType)) {
                String fieldName2 = Convert.cast(binding.get("fieldName"), String.class);
                if (Utils.isNotEmpty(fieldName2)) {
                    tableFields.computeIfAbsent(tableName, o -> new HashSet()).add(fieldName2);
                }
            } else if ("field-table".equals(bindType) || "table-simple".equals(bindType)) {
                fields = binding.get("fields");
                if (fields instanceof List) {
                    List fieldArray = (List)fields;
                    for (int i = 0; i < fieldArray.size(); ++i) {
                        Map field = (Map)fieldArray.get(i);
                        String fieldName3 = Convert.cast(field.get("name"), String.class);
                        tableFields.computeIfAbsent(tableName, o -> new HashSet()).add(fieldName3);
                    }
                }
            } else if ("field-multi".equals(bindType) && (fields = binding.get("fields")) instanceof Map) {
                Map fieldMap = (Map)fields;
                fieldMap.forEach((n, v) -> tableFields.computeIfAbsent(tableName, o -> new HashSet()).add(v));
            }
            if (binding.get("viewFields") != null && (viewFields = (List)binding.get("viewFields")).size() > 0) {
                for (int i = 0; i < viewFields.size(); ++i) {
                    Map field = (Map)viewFields.get(i);
                    fieldName = Convert.cast(field.get("fieldName"), String.class);
                    tableFields.computeIfAbsent(tableName, o -> new HashSet()).add(fieldName);
                }
            }
            if (binding.get("editFields") != null && (editFields = (List)binding.get("editFields")).size() > 0) {
                for (int i = 0; i < editFields.size(); ++i) {
                    Map field = (Map)editFields.get(i);
                    fieldName = Convert.cast(field.get("fieldName"), String.class);
                    tableFields.computeIfAbsent(tableName, o -> new HashSet()).add(fieldName);
                }
            }
        }
    }

    private String getWizardFirstView(List<Map<String, Object>> wizardInfo) {
        ArrayList<String> toViews = new ArrayList<String>();
        ArrayList<String> curViews = new ArrayList<String>();
        for (Map<String, Object> map : wizardInfo) {
            toViews.add((String)map.get("toView"));
            curViews.add((String)map.get("curView"));
        }
        for (String curView : curViews) {
            if (toViews.contains(curView)) continue;
            return curView;
        }
        return null;
    }
}

