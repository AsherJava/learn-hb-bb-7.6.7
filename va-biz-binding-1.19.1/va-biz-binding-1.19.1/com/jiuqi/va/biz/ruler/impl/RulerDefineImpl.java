/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.domain.RuleAcceptDim;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.TriggerImpl;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerDefine;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import com.jiuqi.va.biz.utils.BizBindingI18nUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RulerDefineImpl
extends PluginDefineImpl
implements RulerDefine {
    private List<FormulaImpl> formulas = new ArrayList<FormulaImpl>();
    private List<Map<String, Object>> sortInfo = new ArrayList<Map<String, Object>>();
    private List<TriggerImpl> triggers = new ArrayList<TriggerImpl>();
    private final transient List<RulerItem> items = new ArrayList<RulerItem>();
    private final transient Map<UUID, Map<String, Object>> props = new HashMap<UUID, Map<String, Object>>();
    private final transient Map<String, Map<UUID, Map<String, List<Formula>>>> objectFormulaMap = new HashMap<String, Map<UUID, Map<String, List<Formula>>>>();
    private final transient Map<UUID, String> objectTypeMap = new HashMap<UUID, String>();
    private final transient Map<UUID, IExpression> calcFieldExpressionMap = new HashMap<UUID, IExpression>();
    private final transient ConcurrentHashMap<RuleAcceptDim, Boolean> acceptCache = new ConcurrentHashMap(10000);
    private transient Map<UUID, String[]> maskFieldMap = new HashMap<UUID, String[]>();
    private transient Map<String, List<Formula>> fieldFilterMap = new HashMap<String, List<Formula>>();

    public Map<String, Map<UUID, Map<String, List<Formula>>>> getObjectFormulaMap() {
        return this.objectFormulaMap;
    }

    public Map<UUID, String> getObjectTypeMap() {
        return this.objectTypeMap;
    }

    public Map<UUID, IExpression> getCalcFieldExpressionMap() {
        return this.calcFieldExpressionMap;
    }

    void initObjectFormulaMap() {
        this.formulas.forEach(o -> {
            if (!o.isUsed()) {
                return;
            }
            String objectType = o.getObjectType();
            if (objectType == null) {
                return;
            }
            UUID objectId = o.getObjectId();
            if (objectId == null) {
                return;
            }
            String propertyType = o.getPropertyType();
            if (propertyType == null) {
                return;
            }
            Map objectMap = this.objectFormulaMap.computeIfAbsent(objectType, k -> new HashMap());
            HashMap<String, List> propMap = (HashMap<String, List>)objectMap.get(objectId);
            if (propMap == null) {
                propMap = new HashMap<String, List>();
                objectMap.put(objectId, propMap);
                String oldObjectType = this.objectTypeMap.put(objectId, objectType);
                if (oldObjectType != null && oldObjectType.equals(objectType)) {
                    throw new ModelException(BizBindingI18nUtil.getMessage("va.bizbinding.rulerdefineimpl.duplicateobjid") + String.format("%s,%s,%s", objectId, objectType, oldObjectType));
                }
            }
            List formulaList = propMap.computeIfAbsent(propertyType, k -> new ArrayList());
            formulaList.add(o);
        });
    }

    public ListContainer<FormulaImpl> getFormulas() {
        return new ListContainerImpl<FormulaImpl>(this.formulas);
    }

    public List<FormulaImpl> getFormulaList() {
        return this.formulas;
    }

    public ListContainer<TriggerImpl> getTriggers() {
        return new ListContainerImpl<TriggerImpl>(this.triggers);
    }

    public void addAllFormula(List<FormulaImpl> formulas) {
        this.formulas.addAll(formulas);
    }

    void setFormulas(List<FormulaImpl> formulas) {
        this.formulas = formulas;
    }

    void setTriggers(List<TriggerImpl> triggers) {
        this.triggers = triggers;
    }

    @Override
    public ListContainer<RulerItem> getItems() {
        return new ListContainerImpl<RulerItem>(this.items);
    }

    public Map<UUID, Map<String, Object>> getProps() {
        return this.props;
    }

    public List<RulerItem> getItemList() {
        return this.items;
    }

    public void addItem(RulerItem item) {
        this.items.add(item);
    }

    public void addMaskField(UUID id, String tableName, String fieldName) {
        String[] arr = new String[]{tableName, fieldName};
        this.maskFieldMap.put(id, arr);
    }

    public Map<UUID, String[]> getMaskFieldMap() {
        return this.maskFieldMap;
    }

    public void addProp(UUID id, String propName, Object propValue) {
        Map map = this.props.computeIfAbsent(id, o -> new HashMap());
        Object v = map.get(propName);
        if (v == null) {
            map.put(propName, propValue);
        } else if (v instanceof List) {
            ((List)v).add(propValue);
        } else {
            ArrayList<Object> list = new ArrayList<Object>();
            list.add(v);
            list.add(propValue);
            map.put(propName, list);
        }
    }

    @Override
    public ListContainer<Map<String, Object>> getSortInfo() {
        return new ListContainerImpl<Map<String, Object>>(this.sortInfo);
    }

    public Map<String, List<Formula>> getFieldFilterMap() {
        return this.fieldFilterMap;
    }

    public ConcurrentHashMap<RuleAcceptDim, Boolean> getAcceptCache() {
        return this.acceptCache;
    }
}

