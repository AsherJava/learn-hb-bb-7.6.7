/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.formula.FormulaCondition;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import java.util.List;
import java.util.Map;

public interface IRunTimeFormulaConditionService {
    public Map<String, List<IParsedExpression>> getParsedFormulaConditionExpression(String var1, String ... var2);

    public List<FormulaCondition> listFormulaConditionByTask(String var1);

    public List<FormulaCondition> listFormulaConditionByScheme(String var1, String ... var2);

    public List<FormulaCondition> listFormulaConditionByKey(List<String> var1);

    public List<FormulaConditionLink> listConditionLinkByScheme(String var1);

    public List<FormulaConditionLink> listConditionLinksByCondition(List<String> var1);
}

