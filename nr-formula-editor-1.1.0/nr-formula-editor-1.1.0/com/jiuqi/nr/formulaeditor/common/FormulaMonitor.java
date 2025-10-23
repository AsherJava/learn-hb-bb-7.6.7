/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.formulaeditor.common;

import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.formulaeditor.vo.FormulaCheckData;
import java.util.HashMap;
import java.util.Map;

public class FormulaMonitor
extends AbstractMonitor
implements IMonitor {
    private Map<String, FormulaCheckData> formulasMap = new HashMap<String, FormulaCheckData>();
    private Map<String, FormulaCheckData> importFormulas = new HashMap<String, FormulaCheckData>();

    public void setImportFomulas(Map<String, FormulaCheckData> importFomulas) {
        this.importFormulas = importFomulas;
    }

    public void exception(Exception e) {
        if (e instanceof FormulaParseException) {
            FormulaParseException exception = (FormulaParseException)e;
            Formula formula = exception.getErrorFormua();
            FormulaCheckData formulaExceptionObj = null;
            if (this.importFormulas.containsKey(formula.getId())) {
                formulaExceptionObj = this.importFormulas.get(formula.getId());
            } else {
                formulaExceptionObj = this.formulasMap.get(formula.getCode());
                if (formulaExceptionObj == null) {
                    formulaExceptionObj = new FormulaCheckData();
                }
            }
            formulaExceptionObj.addErrorMsg(e.getMessage());
            formulaExceptionObj.setCode(formula.getCode());
            this.formulasMap.put(formula.getCode(), formulaExceptionObj);
        }
        super.exception(e);
    }

    public Map<String, FormulaCheckData> getCheckResultMap() {
        return this.formulasMap;
    }
}

