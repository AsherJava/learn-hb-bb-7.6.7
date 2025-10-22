/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseContext
extends QueryContext {
    private List<IParsedExpression> parsedExpressions = null;
    private Map<DataEngineConsts.FormulaType, List<IParsedExpression>> parsedFomlulasByType = new HashMap<DataEngineConsts.FormulaType, List<IParsedExpression>>();
    private Map<String, IParsedExpression> conditionsByKey = new HashMap<String, IParsedExpression>();

    public ParseContext(ExecutorContext exeContext, IMonitor monitor) throws ParseException {
        super(exeContext, monitor);
        this.setMasterKeys(new DimensionValueSet());
    }

    public void addParsedExpression(IParsedExpression exp, DataEngineConsts.FormulaType type) {
        List<IParsedExpression> formulas = this.parsedFomlulasByType.get((Object)type);
        if (formulas == null) {
            formulas = new ArrayList<IParsedExpression>();
            this.parsedFomlulasByType.put(type, formulas);
        }
        formulas.add(exp);
    }

    public List<IParsedExpression> getParsedExpressions() {
        if (this.parsedExpressions != null) {
            return this.parsedExpressions;
        }
        Collection<List<IParsedExpression>> values = this.parsedFomlulasByType.values();
        if (values.isEmpty()) {
            return new ArrayList<IParsedExpression>();
        }
        return values.iterator().next();
    }

    public IParsedExpression findconditionExp(String conditionKey) {
        return this.conditionsByKey.get(conditionKey);
    }

    public IParsedExpression putConditionExp(String conditionKey, IParsedExpression conditionExp) {
        return this.conditionsByKey.put(conditionKey, conditionExp);
    }

    public Map<DataEngineConsts.FormulaType, List<IParsedExpression>> getParsedFomlulasByType() {
        return this.parsedFomlulasByType;
    }
}

