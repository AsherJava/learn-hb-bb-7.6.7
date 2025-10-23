/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.executors.SortCalcItem
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.task.form.util;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.SortCalcItem;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.task.form.controller.dto.FormulaCheckObj;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

class FormulaMonitor
extends AbstractMonitor
implements IMonitor {
    private Map<String, FormulaCheckObj> formulasMap = new HashMap<String, FormulaCheckObj>();
    private Map<String, FormulaCheckObj> importFormulas = new HashMap<String, FormulaCheckObj>();
    private Map<String, List<Formula>> cycles = new HashMap<String, List<Formula>>();

    FormulaMonitor() {
    }

    public void setImportFomulas(Map<String, FormulaCheckObj> importFomulas) {
        this.importFormulas = importFomulas;
    }

    public void exception(Exception e) {
        if (e instanceof FormulaParseException) {
            FormulaParseException exception = (FormulaParseException)e;
            Formula formula = exception.getErrorFormua();
            FormulaCheckObj formulaExceptionObj = null;
            if (this.importFormulas.containsKey(formula.getId())) {
                formulaExceptionObj = this.importFormulas.get(formula.getId());
            } else {
                formulaExceptionObj = this.formulasMap.get(formula.getCode());
                if (formulaExceptionObj == null) {
                    formulaExceptionObj = new FormulaCheckObj();
                }
            }
            formulaExceptionObj.addErrorMsg(e.getMessage());
            formulaExceptionObj.setCode(formula.getCode());
            this.formulasMap.put(formula.getCode(), formulaExceptionObj);
        }
        super.exception(e);
    }

    public Map<String, List<Formula>> getCycles() {
        return this.cycles;
    }

    public void message(String msg, Object sender) {
        super.message(msg, sender);
        if (sender instanceof Stack) {
            Stack stack = (Stack)sender;
            if (null == this.cycles) {
                this.cycles = new HashMap<String, List<Formula>>();
            }
            String key = OrderGenerator.newOrder();
            this.cycles.put(key, new ArrayList());
            for (Object o : stack) {
                if (!(o instanceof SortCalcItem)) continue;
                SortCalcItem sortCalcItem = (SortCalcItem)o;
                this.cycles.get(key).add(sortCalcItem.getExpression().getSource());
            }
        }
    }

    public Map<String, FormulaCheckObj> getCheckResultMap() {
        return this.formulasMap;
    }
}

