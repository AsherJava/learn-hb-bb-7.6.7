/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.singlequeryimport.service;

import com.jiuqi.nr.singlequeryimport.bean.FormSchemeItem;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.FormulaCheckParam;
import com.jiuqi.nr.singlequeryimport.bean.TaskItem;
import java.util.List;

public interface IFormulaEditorService {
    public List<TaskItem> getTaskItemList(String var1);

    public List<FormSchemeItem> getFormSchemeItemList(String var1);

    public void checkFormula(FormulaCheckParam var1) throws Exception;
}

