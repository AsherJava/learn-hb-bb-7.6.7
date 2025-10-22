/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.np.dataengine.collector.FieldExecInfo;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class WanningCollector {
    private List<FieldExecInfo> duplicateAssignNodes = new ArrayList<FieldExecInfo>();
    private List<IParsedExpression> oneByOneExpressions = new ArrayList<IParsedExpression>();
    private List<CalcExpression> cycleExpressions = new ArrayList<CalcExpression>();

    public List<FieldExecInfo> getDuplicateAssignNodes() {
        return this.duplicateAssignNodes;
    }

    public List<IParsedExpression> getOneByOneExpressions() {
        return this.oneByOneExpressions;
    }

    public List<CalcExpression> getCycleExpressions() {
        return this.cycleExpressions;
    }
}

