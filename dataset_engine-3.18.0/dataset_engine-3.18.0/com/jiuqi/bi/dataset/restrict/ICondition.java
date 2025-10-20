/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.restrict;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;

interface ICondition {
    public int getCol();

    public boolean canUseIndex();

    public void validate() throws BIDataSetException;

    public Object getValue(DSFormulaContext var1) throws BIDataSetException;
}

