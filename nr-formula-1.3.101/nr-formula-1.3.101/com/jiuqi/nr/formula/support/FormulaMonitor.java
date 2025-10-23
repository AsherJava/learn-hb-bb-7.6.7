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
package com.jiuqi.nr.formula.support;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.executors.SortCalcItem;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.formula.dto.FormulaExtDTO;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaMonitor<T extends FormulaExtDTO>
extends AbstractMonitor
implements IMonitor {
    private static final Logger logger = LoggerFactory.getLogger(FormulaMonitor.class);
    private final Map<String, T> checkResult;
    private Map<String, List<Formula>> cycles = new HashMap<String, List<Formula>>();

    public FormulaMonitor() {
        this.checkResult = new HashMap<String, T>();
    }

    public FormulaMonitor(Map<String, T> checkResult) {
        this.checkResult = checkResult;
    }

    public Map<String, T> getCheckResult() {
        return this.checkResult;
    }

    public void exception(Exception e) {
        FormulaParseException exception;
        Formula errorFormula;
        logger.error(e.getMessage(), e);
        if (e instanceof FormulaParseException && (errorFormula = (exception = (FormulaParseException)e).getErrorFormua()) instanceof FormulaExtDTO) {
            FormulaExtDTO formula = (FormulaExtDTO)errorFormula;
            FormulaExtDTO formulaCheck = (FormulaExtDTO)this.checkResult.get(formula.getId());
            if (formulaCheck == null) {
                formula.setErrorMsg(e.getMessage());
                this.checkResult.put(formula.getId(), formula);
            } else {
                formulaCheck.setErrorMsg(e.getMessage());
            }
        }
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
}

