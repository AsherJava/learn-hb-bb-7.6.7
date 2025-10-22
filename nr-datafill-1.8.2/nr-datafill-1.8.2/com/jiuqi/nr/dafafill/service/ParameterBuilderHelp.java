/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.nr.dafafill.model.ConditionField;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import java.util.List;

public interface ParameterBuilderHelp {
    default public List<ParameterModel> getParameterModels(DataFillContext dataFillContext, ConditionField conditionField) throws Exception {
        return this.getParameterModels(dataFillContext, conditionField, false);
    }

    public List<ParameterModel> getParameterModels(DataFillContext var1, ConditionField var2, boolean var3) throws Exception;

    default public List<ParameterModel> getParameterModels(DataFillContext dataFillContext, boolean addConditionField) throws Exception {
        return this.getParameterModels(dataFillContext, null, addConditionField);
    }

    default public List<ParameterModel> getParameterModels(DataFillContext dataFillContext) throws Exception {
        return this.getParameterModels(dataFillContext, null, false);
    }
}

