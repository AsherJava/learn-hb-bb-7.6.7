/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.debug.BillFormulaDebugContextVO;
import com.jiuqi.va.bill.domain.debug.BillFormulaDebugVO;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import java.util.List;

public interface BillFormulaDebugService {
    public BillFormulaDebugContextVO queryContext(String var1);

    public boolean saveContext(BillFormulaDebugContextVO var1);

    public List<BillFormulaDebugVO> getRuleDebugInfo(BillFormulaDebugVO var1);

    public List<FormulaImpl> queryRelationRules(BillFormulaDebugVO var1);
}

