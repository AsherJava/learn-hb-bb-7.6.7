/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.front.FrontDataDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataFieldType;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.CountDataNode;
import com.jiuqi.va.biz.ruler.ModelNode;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.FormulaRulerItem;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FrontRulerDefine
extends FrontPluginDefine {
    private Map<UUID, Map<String, Object>> props = new HashMap<UUID, Map<String, Object>>();
    private Map<String, Map<UUID, Map<String, Object>>> viewProps = new HashMap<String, Map<UUID, Map<String, Object>>>();
    private Map<String, Set<String>> triggerFields = new HashMap<String, Set<String>>();
    private transient Set<UUID> processed = new HashSet<UUID>();
    private transient Map<String, Set<UUID>> viewProcessed = new HashMap<String, Set<UUID>>();
    private transient Set<UUID> fixedProcessed = new HashSet<UUID>();
    private transient Map<String, Set<UUID>> viewFixedProcessed = new HashMap<String, Set<UUID>>();
    private transient FrontDataDefine frontDataDefine;
    private transient RulerDefineImpl rulerDefine;

    public FrontRulerDefine() {
    }

    public FrontRulerDefine(FrontModelDefine frontModelDefine, PluginDefine pluginDefine) {
        super(frontModelDefine, pluginDefine);
        this.rulerDefine = (RulerDefineImpl)pluginDefine;
    }

    public Map<UUID, Map<String, Object>> getProps() {
        return this.props;
    }

    @Override
    protected void initialize() {
        this.frontDataDefine = this.frontModelDefine.get(FrontDataDefine.class);
        this.frontDataDefine.getDataDefine().getTables().forEach((index, table) -> table.getFields().forEachName((fieldName, field) -> {
            DataFieldDefineImpl dataFieldDefineImpl = (DataFieldDefineImpl)field;
            if (dataFieldDefineImpl.isBillPenetrate()) {
                this.addTriggerField(table.getName(), (String)fieldName);
            } else if (dataFieldDefineImpl.getMaskFlag() || StringUtils.hasText(dataFieldDefineImpl.getMask())) {
                this.addTriggerField(table.getName(), (String)fieldName);
            }
        }));
        this.rulerDefine.getItems().stream().forEach(o -> {
            FormulaImpl formula;
            boolean isFieldFilter = false;
            if (o instanceof FormulaRulerItem && "field".equals((formula = ((FormulaRulerItem)o).getFormula()).getObjectType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_FILTER.equals(formula.getPropertyType())) {
                isFieldFilter = true;
            }
            if (!(isFieldFilter || o.getTriggerTypes() != null && o.getTriggerTypes().contains("after-set-value"))) {
                return;
            }
            Map<String, Map<String, Boolean>> triggerFields = o.getTriggerFields(this.frontModelDefine.getModelDefine());
            if (triggerFields != null) {
                triggerFields.forEach((tableName, fields) -> fields.keySet().forEach(fieldName -> this.addTriggerField((String)tableName, (String)fieldName)));
            }
        });
        Map<UUID, Map<String, List<Formula>>> map = this.rulerDefine.getObjectFormulaMap().get("table");
        if (map == null) {
            return;
        }
        map.values().forEach(o -> {
            List list = (List)o.get("condition");
            if (list == null) {
                return;
            }
            list.forEach(formula -> {
                IASTNode expression = (IASTNode)formula.getCompiledExpression();
                expression.forEach(node -> {
                    if (node instanceof ModelNode) {
                        String tableName = ((ModelNode)node).tableDefine.getName();
                        String fieldName = ((ModelNode)node).fieldDefine.getName();
                        this.addTriggerField(tableName, fieldName);
                    }
                });
            });
        });
    }

    private void addReference(Formula formula) {
        IASTNode expression = (IASTNode)formula.getCompiledExpression();
        try {
            expression.forEach(node -> {
                if (node instanceof ModelNode) {
                    String tableName = ((ModelNode)node).tableDefine.getName();
                    String fieldName = ((ModelNode)node).fieldDefine.getName();
                    this.frontDataDefine.addFrontField(tableName, fieldName);
                }
            });
        }
        catch (Exception e) {
            throw new ModelException(e);
        }
    }

    public void addTriggerField(String tableName, String fieldName) {
        Set fields = this.triggerFields.computeIfAbsent(tableName, k -> new HashSet());
        DataTableDefine tableDefine = this.frontDataDefine.getDataDefine().getTables().get(tableName);
        DataFieldDefine fieldDefine = tableDefine.getFields().get(fieldName);
        if (fieldDefine.getFieldType() == DataFieldType.DATA) {
            fields.add(fieldName);
        }
    }

    public void activeFixedRuler(UUID id, Collection<String> propNames, String viewName) {
        Set uuids;
        if (StringUtils.hasText(viewName) ? !(uuids = this.viewFixedProcessed.computeIfAbsent(viewName, key -> new HashSet())).add(id) : !this.fixedProcessed.add(id)) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontrulerdefine.duplicateid") + id.toString());
        }
        Map<String, Object> rulerProps = this.rulerDefine.getProps().get(id);
        if (rulerProps == null) {
            return;
        }
        if (StringUtils.hasText(viewName)) {
            this.viewProps.computeIfAbsent(viewName, key -> new HashMap()).put(id, rulerProps);
        } else {
            this.props.put(id, rulerProps);
        }
    }

    public void activeFrontObjectRuler(UUID id, Collection<String> propNames, String viewName) {
        Set uuids;
        HashMap map = new HashMap();
        if (StringUtils.hasText(viewName) ? !(uuids = this.viewProcessed.computeIfAbsent(viewName, key -> new HashSet())).add(id) : !this.processed.add(id)) {
            throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.frontrulerdefine.duplicateid") + id.toString());
        }
        Map<String, Object> rulerProps = this.rulerDefine.getProps().get(id);
        if (rulerProps == null) {
            return;
        }
        String objectType = this.rulerDefine.getObjectTypeMap().get(id);
        if (objectType != null) {
            Map<String, List<Formula>> propMap = this.rulerDefine.getObjectFormulaMap().get(objectType).get(id);
            if (propNames != null) {
                propNames.forEach(o -> {
                    List formulaList;
                    Object value = rulerProps.get(o);
                    if (value != null) {
                        map.put(o, value);
                    }
                    if (this.frontModelDefine.ENABLE_SERVER_DATA_BUFFER && (formulaList = (List)propMap.get(o)) != null) {
                        formulaList.forEach(formula -> this.addReference((Formula)formula));
                    }
                });
            } else {
                rulerProps.forEach((o, value) -> {
                    List formulaList;
                    if (value != null) {
                        map.put(o, value);
                    }
                    if (this.frontModelDefine.ENABLE_SERVER_DATA_BUFFER && (formulaList = (List)propMap.get(o)) != null) {
                        formulaList.forEach(formula -> this.addReference((Formula)formula));
                    }
                });
            }
        }
        if (map.size() > 0) {
            if (StringUtils.hasText(viewName)) {
                this.viewProps.computeIfAbsent(viewName, key -> new HashMap()).put(id, map);
            } else {
                this.props.put(id, map);
            }
        }
    }

    @Override
    protected Map<String, Set<String>> getTableFields(ModelDefine modelDefine) {
        HashMap<String, Set<String>> tableFields = new HashMap<String, Set<String>>();
        List<RulerItem> formulaList = this.rulerDefine.getItemList();
        List<RulerItem> itemList = this.rulerDefine.getItemList();
        for (RulerItem rulerItem : itemList) {
            Map<String, Map<String, Boolean>> fieldsMap;
            if (rulerItem instanceof FormulaRulerItem || CollectionUtils.isEmpty(fieldsMap = rulerItem.getTriggerFields(this.frontModelDefine.getModelDefine()))) continue;
            for (Map.Entry<String, Map<String, Boolean>> entry : fieldsMap.entrySet()) {
                Map<String, Boolean> value = entry.getValue();
                if (value == null || value.isEmpty()) continue;
                tableFields.computeIfAbsent(entry.getKey(), k -> new HashSet()).addAll(value.keySet());
            }
        }
        for (RulerItem rulerItem : formulaList) {
            if (!(rulerItem instanceof FormulaRulerItem)) continue;
            FormulaImpl formula = ((FormulaRulerItem)rulerItem).getFormula();
            String objectType = formula.getObjectType();
            String propertyType = formula.getPropertyType();
            if (objectType.equals("field")) {
                if (!RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_VALIDATE2.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_INPUT.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_READONLY.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_REQUIRED.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_HIDDEN.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_MASK.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_BACKGROUNDCOLOR.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_SHOWBACKGROUNDCOLORONVIEW.equals(propertyType) && !RulerConsts.FORMULA_OBJECT_PROP_FIELD_SSOPARAM.equals(propertyType)) continue;
                this.collectFormulaTableFields(formula.getCompiledExpression(), tableFields);
                continue;
            }
            if (objectType.equals("table")) {
                if (!"readonly".equals(propertyType) && !"addRow".equals(propertyType) && !"delRow".equals(propertyType)) continue;
                this.collectFormulaTableFields(formula.getCompiledExpression(), tableFields);
                continue;
            }
            if (!objectType.equals("control")) continue;
            this.collectFormulaTableFields(formula.getCompiledExpression(), tableFields);
        }
        return tableFields;
    }

    private void collectFormulaTableFields(IExpression expression, Map<String, Set<String>> tableFields) {
        expression.forEach(node -> {
            if (node instanceof ModelNode) {
                ModelNode modelNode = (ModelNode)((Object)node);
                String tableName = modelNode.tableDefine.getName();
                String fieldName = modelNode.fieldDefine.getName();
                tableFields.computeIfAbsent(tableName, k -> new HashSet()).add(fieldName);
            } else if (node instanceof CountDataNode) {
                CountDataNode countDataNode = (CountDataNode)((Object)node);
                ModelNode modelNode = countDataNode.getModelNode();
                String tableName = modelNode.tableDefine.getName();
                String fieldName = modelNode.fieldDefine.getName();
                tableFields.computeIfAbsent(tableName, k -> new HashSet()).add(fieldName);
            }
        });
    }
}

