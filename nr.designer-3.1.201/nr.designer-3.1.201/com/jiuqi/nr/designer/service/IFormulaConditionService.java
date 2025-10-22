/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.nr.designer.web.facade.ConditionImportResult;
import com.jiuqi.nr.designer.web.facade.FormulaConditionObj;
import com.jiuqi.nr.designer.web.facade.FormulaConditionPageObj;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface IFormulaConditionService {
    public FormulaConditionPageObj queryConditionsByTask(String var1, Long var2, Long var3);

    public void updateFormulaCondition(FormulaConditionObj var1) throws Exception;

    public void updateFormulaConditions(List<FormulaConditionObj> var1);

    public void deleteFormulaCondition(String var1);

    public void deleteFormulaConditions(List<String> var1);

    public void insertFormulaCondition(FormulaConditionObj var1);

    public void exportConditions(OutputStream var1, String var2);

    public ConditionImportResult importAddConditions(InputStream var1, String var2);

    public void deployFormulaConditions(String var1, String[] var2);

    public boolean isConditionExist(String var1);
}

