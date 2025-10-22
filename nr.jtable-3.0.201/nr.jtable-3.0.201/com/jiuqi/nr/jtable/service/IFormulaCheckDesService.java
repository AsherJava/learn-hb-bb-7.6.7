/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.FormulaData;
import com.jiuqi.nr.jtable.params.base.SearchFormulaData;
import com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesCopyInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.input.FuzzyQueryFormulaParam;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import java.util.List;

@Deprecated
public interface IFormulaCheckDesService {
    @Deprecated
    public List<FormulaCheckDesInfo> queryFormulaCheckDes(FormulaCheckDesQueryInfo var1);

    @Deprecated
    public int batchSaveFormulaCheckDes(FormulaCheckDesBatchSaveInfo var1);

    @Deprecated
    public FormulaCheckDesInfo saveFormulaCheckDes(FormulaCheckDesInfo var1);

    @Deprecated
    public ReturnInfo removeFormulaCheckDes(FormulaCheckDesInfo var1);

    @Deprecated
    public ReturnInfo reviseCheckDesKey();

    @Deprecated
    public ReturnInfo batchSaveFormulaCheckDes(BatchSaveFormulaCheckDesInfo var1);

    @Deprecated
    public ReturnInfo copyCheckDesKey(FormulaCheckDesCopyInfo var1);

    @Deprecated
    public List<FormulaData> queryCheckResultFormulaIds(BatchSaveFormulaCheckDesInfo var1);

    public List<SearchFormulaData> fuzzyQueryFormula(FuzzyQueryFormulaParam var1);

    public ReturnInfo batchSaveCKD(BatchSaveFormulaCheckDesInfo var1);

    public FormulaCheckDesInfo saveCKD(FormulaCheckDesInfo var1);
}

