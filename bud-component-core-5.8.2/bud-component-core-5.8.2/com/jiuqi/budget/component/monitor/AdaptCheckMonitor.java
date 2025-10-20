/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.exception.FormulaParseException
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.budget.component.monitor;

import com.jiuqi.np.dataengine.exception.FormulaParseException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public class AdaptCheckMonitor
extends AbstractMonitor
implements IMonitor {
    private String message;

    public void exception(Exception e) {
        if (e instanceof FormulaParseException) {
            FormulaParseException exception = (FormulaParseException)e;
            this.message = exception.getMessage();
        }
        super.exception(e);
    }

    public String getFormulaCheckResult() {
        return this.message;
    }
}

