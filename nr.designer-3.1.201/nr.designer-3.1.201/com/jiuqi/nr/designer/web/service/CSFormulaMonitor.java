/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.designer.web.facade.FormulaCheckObj;
import java.util.HashMap;
import java.util.Map;

public class CSFormulaMonitor
extends AbstractMonitor
implements IMonitor {
    private Map<String, FormulaCheckObj> formulasMap = new HashMap<String, FormulaCheckObj>();

    public void exception(Exception e) {
        if (e instanceof FormulaParseException) {
            FormulaParseException exception = (FormulaParseException)e;
            Formula formula = exception.getErrorFormua();
            FormulaCheckObj formulaExceptionObj = null;
            formulaExceptionObj = this.formulasMap.get(formula.getFormula());
            if (formulaExceptionObj == null) {
                formulaExceptionObj = new FormulaCheckObj();
            }
            formulaExceptionObj.addErrorMsg(e.getMessage());
            formulaExceptionObj.setId(formula.getId());
            formulaExceptionObj.setFormula(formula.getFormula());
            this.formulasMap.put(formula.getId(), formulaExceptionObj);
        }
        super.exception(e);
    }

    public Map<String, FormulaCheckObj> getCheckResultMap() {
        return this.formulasMap;
    }
}

