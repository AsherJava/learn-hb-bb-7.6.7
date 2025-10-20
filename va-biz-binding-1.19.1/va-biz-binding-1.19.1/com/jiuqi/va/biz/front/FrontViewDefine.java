/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.front.FrontRulerDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.biz.view.intf.ViewDefine;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class FrontViewDefine
extends FrontPluginDefine {
    private Map<String, Object> template;
    private List<Map<String, Object>> wizardInfo;
    private Map<String, Object> props;
    private Map<String, String> viewTileInfo;
    private String schemeCode;
    private boolean isWizard;
    private final transient ViewDefine viewDefine;
    private final transient Map<String, Set<String>> bindingFields = new HashMap<String, Set<String>>();
    private final transient Map<String, Map<String, Set<String>>> viewBindingFields = new HashMap<String, Map<String, Set<String>>>();
    private transient FrontDataDefine frontDataDefine;
    private transient FrontRulerDefine frontRulerDefine;

    public FrontViewDefine() {
        this.viewDefine = null;
    }

    public FrontViewDefine(FrontModelDefine frontBillDefine, PluginDefine pluginDefine) {
        super(frontBillDefine, pluginDefine);
        this.viewDefine = (ViewDefine)((Object)pluginDefine);
        if (this.viewDefine.getTemplate() == null) {
            throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.frontviewdefine.viewtemplaterequired"));
        }
        Map<String, Object> props = this.viewDefine.getTemplate().getProps();
        this.handleProps(props);
    }

    public FrontViewDefine(FrontModelDefine frontBillDefine, PluginDefine pluginDefine, String schemeCode) {
        super(frontBillDefine, pluginDefine);
        this.schemeCode = schemeCode;
        this.viewDefine = (ViewDefine)((Object)pluginDefine);
        if (this.viewDefine.getSchemes() != null) {
            Optional<Map> findAny = this.viewDefine.getSchemes().stream().filter(o -> o.get("code").equals(schemeCode)).findAny();
            if (findAny.isPresent()) {
                Map props = (Map)findAny.get().get("template");
                this.handleProps(props);
            } else {
                if (this.viewDefine.getTemplate() == null) {
                    throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.frontviewdefine.viewtemplaterequired"));
                }
                Map<String, Object> props = this.viewDefine.getTemplate().getProps();
                this.handleProps(props);
            }
        } else {
            if (this.viewDefine.getTemplate() == null) {
                throw new RuntimeException(BizBindingI18nUtil.getMessage("va.bizbinding.frontviewdefine.viewtemplaterequired"));
            }
            Map<String, Object> props = this.viewDefine.getTemplate().getProps();
            this.handleProps(props);
        }
    }

    private void handleProps(Map<String, Object> props) {
        if ("v-wizard".equals(props.get("type")) && props.get("wizardInfo") != null) {
            HashSet<String> schemeCodes = new HashSet<String>();
            List wizardinfo = (List)props.get("wizardInfo");
            Map wizardProps = Optional.ofNullable((Map)props.get("props")).orElse(new HashMap());
            for (Map map : wizardinfo) {
                Object toView = map.get("toView");
                Object curView = map.get("curView");
                if (!ObjectUtils.isEmpty(toView)) {
                    schemeCodes.add(toView.toString());
                }
                if (ObjectUtils.isEmpty(curView)) continue;
                schemeCodes.add(curView.toString());
            }
            Object object = wizardProps.get("fixedScheme");
            if (!ObjectUtils.isEmpty(object)) {
                schemeCodes.add(String.valueOf(object));
            }
            Map<Object, Map> schemeMap = this.viewDefine.getSchemes().stream().collect(Collectors.toMap(o -> o.get("code"), o -> o));
            HashMap<String, Object> template = new HashMap<String, Object>();
            this.viewTileInfo = new HashMap<String, String>();
            for (String code : schemeCodes) {
                template.put(code, schemeMap.get(code).get("template"));
                this.viewTileInfo.put(code, String.valueOf(schemeMap.get(code).get("title")));
            }
            this.isWizard = true;
            this.template = template;
            this.wizardInfo = wizardinfo;
            this.props = wizardProps;
        } else {
            this.isWizard = false;
            this.template = props;
        }
    }

    @Override
    protected void initialize() {
        this.frontDataDefine = this.frontModelDefine.get(FrontDataDefine.class);
        this.frontRulerDefine = this.frontModelDefine.get(FrontRulerDefine.class);
        if (this.isWizard) {
            HashSet<String> controls = new HashSet<String>();
            for (Map.Entry<String, Object> entry : this.template.entrySet()) {
                String schemeCode = entry.getKey();
                Map props = (Map)entry.getValue();
                this.processProps(props, controls, schemeCode);
            }
            if (!controls.contains("bill-commit")) {
                this.frontRulerDefine.activeFixedRuler(Utils.normalizeId("bill-commit"), Arrays.asList(RulerConsts.FRONT_CONTROL_PROPS), null);
            }
            if (!controls.contains("bill-temp-save")) {
                this.frontRulerDefine.activeFixedRuler(Utils.normalizeId("bill-temp-save"), Arrays.asList(RulerConsts.FRONT_CONTROL_PROPS), null);
            }
            for (Map map : this.wizardInfo) {
                this.frontRulerDefine.activeFixedRuler(UUID.fromString((String)map.get("id")), Arrays.asList(RulerConsts.FRONT_WIZRAD_PROPS), null);
            }
        } else {
            HashSet<String> controls = new HashSet<String>();
            this.processProps(this.template, controls, null);
        }
        this.frontDataDefine.getDataDefine().getTables().stream().forEach(o -> this.frontRulerDefine.activeFixedRuler(o.getId(), Arrays.asList(RulerConsts.FRONT_TABLE_PROPS), null));
    }

    public void processProps(Map<String, Object> props, Set<String> controls, String viewName) {
        Map action;
        Map binding1;
        Map binding = (Map)props.get("binding");
        if (binding != null) {
            this.addBindingFields(binding, viewName);
        }
        if ((binding1 = (Map)props.get("binding1")) != null) {
            this.addBindingFields(binding1, viewName);
        }
        if ((action = (Map)props.get("action")) != null && !ObjectUtils.isEmpty(action.get("type")) && controls.add(action.get("type").toString())) {
            this.frontRulerDefine.activeFixedRuler(Utils.normalizeId(action.get("type")), Arrays.asList(RulerConsts.FRONT_CONTROL_PROPS), null);
        }
        if ("v-upload-list".equals(String.valueOf(props.get("type"))) && props.get("attType") != null) {
            if (props.get("attType") instanceof List) {
                List attType = (List)props.get("attType");
                attType.forEach(o -> {
                    Object idObj = o.get("id");
                    if (ObjectUtils.isEmpty(idObj)) {
                        return;
                    }
                    String idValue = Convert.cast(idObj, String.class);
                    UUID id = Utils.normalizeId(idValue);
                    this.frontRulerDefine.activeFrontObjectRuler(id, Arrays.asList(RulerConsts.FRONT_ATTACHMENT_PROPS), viewName);
                });
            }
        } else if ("v-grid".equals(String.valueOf(props.get("type")))) {
            Object o2;
            Map cardTemplate;
            Map designData;
            Object cardEntry = props.get("cardEntry");
            if (cardEntry != null && ((Boolean)cardEntry).booleanValue() && (designData = (Map)props.get("designData")) != null && (cardTemplate = (Map)designData.get("template")) != null) {
                this.processProps(cardTemplate, controls, viewName);
            }
            if ((o2 = props.get("binding")) != null) {
                Map bindingMap = (Map)o2;
                Object bindingType = bindingMap.get("type");
                if ("field-table".equals(bindingType) || "table-simple".equals(bindingType)) {
                    List fields = (List)bindingMap.get("fields");
                    if (fields == null) {
                        return;
                    }
                    for (Map field : fields) {
                        Map attProps;
                        Object attTypes;
                        String inputTypeParam;
                        if (!"v-grid-attachment".equals(field.get("inputType")) || !StringUtils.hasText(inputTypeParam = (String)field.get("inputTypeParam")) || (attTypes = (attProps = JSONUtil.parseMap((String)inputTypeParam)).get("attType")) == null || !(attTypes instanceof List)) continue;
                        ((List)attTypes).forEach(a -> {
                            Object idObj = a.get("id");
                            if (ObjectUtils.isEmpty(idObj)) {
                                return;
                            }
                            String idValue = Convert.cast(idObj, String.class);
                            UUID id = Utils.normalizeId(idValue);
                            this.frontRulerDefine.activeFrontObjectRuler(id, Arrays.asList(RulerConsts.FRONT_ATTACHMENT_PROPS), viewName);
                        });
                    }
                } else {
                    List fields = (List)bindingMap.get("fields");
                    List viewFields = (List)bindingMap.get("viewFields");
                    List cardViewFields = (List)bindingMap.get("cardViewFields");
                    List editFields = (List)bindingMap.get("editFields");
                    this.phoneFields(viewName, fields);
                    this.phoneFields(viewName, viewFields);
                    this.phoneFields(viewName, cardViewFields);
                    this.phoneFields(viewName, editFields);
                }
            }
        } else if ("v-bill-state".equals(String.valueOf(props.get("type")))) {
            Object o3 = props.get("subheading");
            this.getBillStateBindFields(o3, viewName);
            Object o1 = props.get("label");
            this.getBillStateBindFields(o1, viewName);
        }
        String idValue = Convert.cast(props.get("id"), String.class);
        UUID id = Utils.normalizeId(idValue);
        this.frontRulerDefine.activeFrontObjectRuler(id, null, viewName);
        List children = (List)props.get("children");
        if (children != null) {
            children.forEach(o -> this.processProps((Map<String, Object>)o, controls, viewName));
        }
    }

    private void phoneFields(String viewName, List<Map<String, Object>> fields) {
        if (fields == null) {
            return;
        }
        for (Map<String, Object> field : fields) {
            String inputTypeParam;
            if (!"v-grid-attachment".equals(field.get("inputType")) || !StringUtils.hasText(inputTypeParam = (String)field.get("inputTypeParam"))) continue;
            Map attProps = JSONUtil.parseMap((String)inputTypeParam);
            Object idObj = attProps.get("id");
            if (ObjectUtils.isEmpty(idObj)) {
                return;
            }
            String idValue = Convert.cast(idObj, String.class);
            UUID id = Utils.normalizeId(idValue);
            this.frontRulerDefine.activeFrontObjectRuler(id, null, viewName);
        }
    }

    private void getBillStateBindFields(Object o1, String viewName) {
        if (o1 == null) {
            return;
        }
        Map subbinding = (Map)o1;
        Map bindingField = (Map)subbinding.get("binding");
        if (bindingField == null || !StringUtils.hasText((String)bindingField.get("tableName"))) {
            return;
        }
        this.addBindingFields(bindingField, viewName);
    }

    private Set<String> addBindingTable(String tableName, boolean fieldTable, String viewName) {
        if (StringUtils.hasText(viewName)) {
            Map stringSetMap = this.viewBindingFields.computeIfAbsent(viewName, key -> new HashMap());
            return stringSetMap.computeIfAbsent(tableName, o -> {
                DataTableDefine tableDefine = this.frontDataDefine.getDataDefine().getTables().get(tableName);
                this.frontRulerDefine.activeFrontObjectRuler(tableDefine.getId(), Arrays.asList(RulerConsts.FRONT_TABLE_PROPS), viewName);
                return new HashSet();
            });
        }
        return this.bindingFields.computeIfAbsent(tableName, o -> {
            DataTableDefine tableDefine = this.frontDataDefine.getDataDefine().getTables().get(tableName);
            this.frontRulerDefine.activeFrontObjectRuler(tableDefine.getId(), Arrays.asList(RulerConsts.FRONT_TABLE_PROPS), viewName);
            return new HashSet();
        });
    }

    private void addBindingField(String tableName, Set<String> fieldNames, String fieldName, String viewName) {
        if (fieldNames.add(fieldName)) {
            this.frontDataDefine.addFrontField(tableName, fieldName);
            DataFieldDefine fieldDefine = this.frontDataDefine.getDataDefine().getTables().get(tableName).getFields().find(fieldName);
            if (fieldDefine != null) {
                this.frontRulerDefine.activeFrontObjectRuler(fieldDefine.getId(), Arrays.asList(RulerConsts.FRONT_FIELD_PROPS), viewName);
            }
        }
    }

    private void addBindingFields(Map<String, Object> binding, String viewName) {
        String bindType = Convert.cast(binding.get("type"), String.class);
        String tableName = Convert.cast(binding.get("tableName"), String.class);
        if (Utils.isNotEmpty(tableName)) {
            List editFields;
            Set<String> fieldNames;
            List viewFields;
            Object tableFields;
            Set<String> fieldNames2;
            if ("field-simple".equals(bindType) || ObjectUtils.isEmpty(bindType)) {
                fieldNames2 = this.addBindingTable(tableName, false, viewName);
                String fieldName = Convert.cast(binding.get("fieldName"), String.class);
                if (Utils.isNotEmpty(fieldName)) {
                    this.addBindingField(tableName, fieldNames2, fieldName, viewName);
                }
            } else if ("field-table".equals(bindType) || "table-simple".equals(bindType)) {
                fieldNames2 = this.addBindingTable(tableName, true, viewName);
                tableFields = binding.get("fields");
                if (tableFields instanceof List) {
                    List fieldArray = (List)tableFields;
                    for (Map field : fieldArray) {
                        String fieldName = Convert.cast(field.get("name"), String.class);
                        this.addBindingField(tableName, fieldNames2, fieldName, viewName);
                    }
                }
            } else if ("field-multi".equals(bindType)) {
                fieldNames2 = this.addBindingTable(tableName, false, viewName);
                tableFields = binding.get("fields");
                if (tableFields instanceof Map) {
                    Map fieldMap = (Map)tableFields;
                    fieldMap.forEach((n, v) -> this.addBindingField(tableName, fieldNames2, (String)v, viewName));
                }
            }
            if (binding.get("viewFields") != null && (viewFields = (List)binding.get("viewFields")).size() > 0) {
                fieldNames = this.addBindingTable(tableName, false, viewName);
                viewFields.forEach(o -> {
                    String viewField = Convert.cast(o.get("fieldName"), String.class);
                    if (Utils.isNotEmpty(viewField)) {
                        this.addBindingField(tableName, fieldNames, viewField, viewName);
                    }
                });
            }
            if (binding.get("editFields") != null && (editFields = (List)binding.get("editFields")).size() > 0) {
                fieldNames = this.addBindingTable(tableName, false, viewName);
                editFields.forEach(o -> {
                    String editField = Convert.cast(o.get("fieldName"), String.class);
                    if (Utils.isNotEmpty(editField)) {
                        this.addBindingField(tableName, fieldNames, editField, viewName);
                    }
                });
            }
        }
    }

    @Override
    protected Map<String, Set<String>> getTableFields(ModelDefine modelDefine) {
        if (this.isWizard) {
            Map<String, Map<String, Set<String>>> viewMap = this.viewBindingFields;
            HashMap<String, Set<String>> tableFieldMap = new HashMap<String, Set<String>>();
            viewMap.values().forEach(o -> {
                if (o != null) {
                    o.forEach((k, v) -> tableFieldMap.computeIfAbsent((String)k, a -> new HashSet()).addAll(v));
                }
            });
            return tableFieldMap;
        }
        return this.bindingFields;
    }

    public Map<String, Object> getTemplate() {
        return this.template;
    }

    public Map<String, String> getViewTileInfo() {
        return this.viewTileInfo;
    }

    public boolean isWizard() {
        return this.isWizard;
    }

    public String getSchemeCode() {
        return this.schemeCode;
    }
}

