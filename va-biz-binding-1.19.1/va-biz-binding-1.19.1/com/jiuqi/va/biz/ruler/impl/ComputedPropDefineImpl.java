/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.value.ListContainerImpl;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.ruler.ComputePropNode;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.intf.ComputedPropDefine;
import com.jiuqi.va.biz.ruler.intf.Formula;
import com.jiuqi.va.biz.ruler.intf.RulerItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ComputedPropDefineImpl
extends PluginDefineImpl
implements ComputedPropDefine {
    private List<FormulaImpl> formulas = new ArrayList<FormulaImpl>();
    private transient List<FormulaImpl> backFormulas = new ArrayList<FormulaImpl>();
    private final transient Map<UUID, Integer> resultType = new HashMap<UUID, Integer>();
    private final transient List<RulerItem> items = new ArrayList<RulerItem>();
    private final transient Map<UUID, IExpression> calcFieldExpressionMap = new HashMap<UUID, IExpression>();
    private final transient Map<UUID, Object> computedProps = new HashMap<UUID, Object>();
    private final transient Map<String, ComputePropNode> computePropNodeMap = new HashMap<String, ComputePropNode>();
    private final transient Map<String, String> backCalc = new HashMap<String, String>();

    @Override
    public List<? extends Formula> getFormulas() {
        return this.formulas;
    }

    public void addItem(FormulaImpl item) {
        this.backFormulas.add(item);
    }

    public void addItem(RulerItem item) {
        this.items.add(item);
    }

    public Map<UUID, IExpression> getCalcFieldExpressionMap() {
        return this.calcFieldExpressionMap;
    }

    public Map<UUID, Object> getComputedProps() {
        return this.computedProps;
    }

    public ListContainer<RulerItem> getItems() {
        return new ListContainerImpl<RulerItem>(this.items);
    }

    public void addComputedProps(UUID id, Object propValue) {
        this.computedProps.put(id, propValue);
    }

    public Map<UUID, Integer> getResultType() {
        return this.resultType;
    }

    public void setResultType(UUID uuid, int type) {
        this.resultType.put(uuid, type);
    }

    public void addBackCalc(String name, String expression) {
        this.backCalc.put(name, expression);
    }

    public String contains(String expression) {
        for (String s : this.backCalc.keySet()) {
            if (!this.backCalc.get(s).equals(expression)) continue;
            return s;
        }
        return null;
    }

    public ComputePropNode getComputePropNodeMap(String name) {
        return this.computePropNodeMap.get(name);
    }

    public void addComputePropNodeMap(String name, ComputePropNode computePropNode) {
        this.computePropNodeMap.put(name, computePropNode);
    }
}

