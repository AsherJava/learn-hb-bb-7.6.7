/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.restrict.ICondition;
import com.jiuqi.bi.dataset.restrict.RestrictionDescriptor;

class AggrCondition
implements ICondition {
    private RestrictionDescriptor rstDesc;

    public AggrCondition(RestrictionDescriptor rstDesc) {
        this.rstDesc = rstDesc;
    }

    @Override
    public boolean canUseIndex() {
        return false;
    }

    @Override
    public int getCol() {
        return this.rstDesc.fieldIdx;
    }

    @Override
    public Object getValue(DSFormulaContext dsCxt) throws BIDataSetException {
        throw new BIDataSetException("\u805a\u5408\u6a21\u5f0f\u7684\u9650\u5b9a\u6761\u4ef6\u4e0d\u80fd\u8fdb\u884c\u6c42\u503c\u8fd0\u7b97");
    }

    @Override
    public void validate() throws BIDataSetException {
    }

    public String toString() {
        return this.rstDesc.item.getName() + "=" + this.rstDesc.condition;
    }
}

