/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;

class ExpressionCondition
implements ICondition {
    private RestrictionDescriptor rstDesc;

    public ExpressionCondition(RestrictionDescriptor rstDesc) {
        this.rstDesc = rstDesc;
    }

    @Override
    public boolean canUseIndex() {
        return false;
    }

    @Override
    public int getCol() {
        return -1;
    }

    @Override
    public Object getValue(DSFormulaContext dsCxt) throws BIDataSetException {
        return this.rstDesc.condition;
    }

    @Override
    public void validate() throws BIDataSetException {
    }

    public String toString() {
        return this.rstDesc.condition.toString();
    }
}

