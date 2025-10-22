/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.EvalExecutor
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisConditionJudger {
    private static final Logger logger = LogFactory.getLogger(AnalysisConditionJudger.class);
    private List<IExpression> conditions = new ArrayList<IExpression>();
    private Map<Integer, Integer> rowConditionIndexMap = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> colConditionIndexMap = new HashMap<Integer, Integer>();
    private EvalExecutor evalExecutor;
    private int[] evalIndexes;

    public int addRowCondition(int row, IExpression condition) {
        Integer rowConditionIndex = this.rowConditionIndexMap.get(row);
        if (rowConditionIndex != null) {
            return rowConditionIndex;
        }
        rowConditionIndex = this.addCondition(condition);
        this.rowConditionIndexMap.put(row, rowConditionIndex);
        return rowConditionIndex;
    }

    public int addColCondition(int col, IExpression condition) {
        Integer colConditionIndex = this.colConditionIndexMap.get(col);
        if (colConditionIndex != null) {
            return colConditionIndex;
        }
        colConditionIndex = this.addCondition(condition);
        this.colConditionIndexMap.put(col, colConditionIndex);
        return colConditionIndex;
    }

    public boolean judge(AnalysisContext context, int index) {
        try {
            return this.evalExecutor.getResult(this.evalIndexes[index]).getAsBool();
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), (Throwable)e);
            return false;
        }
    }

    private int addCondition(IExpression condition) {
        int index = this.conditions.size();
        this.conditions.add(condition);
        return index;
    }

    public List<IExpression> getConditions() {
        return this.conditions;
    }

    public void setEvalExecutor(EvalExecutor evalExecutor) {
        this.evalExecutor = evalExecutor;
        this.evalIndexes = new int[this.conditions.size()];
        if (evalExecutor != null) {
            block0: for (int i = 0; i < this.conditions.size(); ++i) {
                IExpression condition = this.conditions.get(i);
                for (int evalIndex = 0; evalIndex < evalExecutor.size(); ++evalIndex) {
                    ASTNode node = evalExecutor.get(evalIndex);
                    if (node != condition) continue;
                    this.evalIndexes[i] = evalIndex;
                    continue block0;
                }
            }
        }
    }
}

