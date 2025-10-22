/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 */
package com.jiuqi.nr.dataentry.copydes.impl;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class FormulaFinder {
    private final IFormulaRunTimeController formulaRunTimeController;
    private final String formulaSchemeKey;
    private Map<String, IParsedExpression> cache;

    public FormulaFinder(IFormulaRunTimeController formulaRunTimeController, String formulaSchemeKey) {
        this.formulaRunTimeController = formulaRunTimeController;
        this.formulaSchemeKey = formulaSchemeKey;
        this.init();
    }

    private void init() {
        List parsedExpressions = this.formulaRunTimeController.getParsedExpressionByForm(this.formulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
        this.cache = CollectionUtils.isEmpty(parsedExpressions) ? Collections.emptyMap() : parsedExpressions.stream().filter(o -> o.getRealExpression() != null && o.getSource() != null).collect(Collectors.toMap(o -> FormulaFinder.getPEKey(o.getSource().getId(), o.getRealExpression().getWildcardRow(), o.getRealExpression().getWildcardCol()), Function.identity(), (o1, o2) -> o1));
    }

    public IParsedExpression findExpression(String formulaKey, int globRow, int globCol) {
        String peKey = FormulaFinder.getPEKey(formulaKey, globRow, globCol);
        return this.cache.get(peKey);
    }

    private static String getPEKey(String formulaKey, int globRow, int globCol) {
        return formulaKey + "-" + globRow + "-" + globCol;
    }
}

