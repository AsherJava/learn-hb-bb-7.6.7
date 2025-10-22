/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionNode
 */
package com.jiuqi.np.dataengine.intf.impl;

import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExpressionCollection {
    private List<IParsedExpression> advanceExpressions;
    private List<IParsedExpression> oneByOneExpressions;
    private List<IParsedExpression> networkExpressions;
    private Map<String, List<FunctionNode>> preProcessingFunctions;
    private List<CalcExpression> cycleExpressions;
    public double wight = 1.0;

    public List<IParsedExpression> getAdvanceExpressions() {
        if (this.advanceExpressions == null) {
            this.advanceExpressions = new ArrayList<IParsedExpression>();
        }
        return this.advanceExpressions;
    }

    public List<IParsedExpression> getOneByOneExpressions() {
        if (this.oneByOneExpressions == null) {
            this.oneByOneExpressions = new ArrayList<IParsedExpression>();
        }
        return this.oneByOneExpressions;
    }

    public List<IParsedExpression> getNetworkExpressions() {
        if (this.networkExpressions == null) {
            this.networkExpressions = new ArrayList<IParsedExpression>();
        }
        return this.networkExpressions;
    }

    public List<CalcExpression> getCycleExpressions() {
        if (this.cycleExpressions == null) {
            this.cycleExpressions = new ArrayList<CalcExpression>();
        }
        return this.cycleExpressions;
    }

    public Set<String> getAllPreProcessingFunctionNames() {
        return this.preProcessingFunctions == null ? null : this.preProcessingFunctions.keySet();
    }

    public void addPreProcessingFunction(FunctionNode fun) {
        String funcName;
        List<FunctionNode> funList;
        if (this.preProcessingFunctions == null) {
            this.preProcessingFunctions = new HashMap<String, List<FunctionNode>>();
        }
        if ((funList = this.preProcessingFunctions.get((funcName = fun.getDefine().name()).toUpperCase())) == null) {
            funList = new ArrayList<FunctionNode>();
            this.preProcessingFunctions.put(funcName.toUpperCase(), funList);
        }
        funList.add(fun);
    }

    public List<FunctionNode> getPreProcessingFunctionsByName(String funcName) {
        return this.preProcessingFunctions == null ? null : this.preProcessingFunctions.get(funcName.toUpperCase());
    }

    public int size() {
        int advanceExpSize = this.advanceExpressions == null ? 0 : this.advanceExpressions.size();
        int networkExpSize = this.networkExpressions == null ? 0 : this.networkExpressions.size();
        int oneByOneExpSize = this.oneByOneExpressions == null ? 0 : this.oneByOneExpressions.size();
        return advanceExpSize + networkExpSize + oneByOneExpSize;
    }

    public boolean hasOneByOneExpressions() {
        return this.oneByOneExpressions != null && this.oneByOneExpressions.size() > 0;
    }
}

