/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.node.CheckExpression;
import java.util.ArrayList;
import java.util.List;

public class RegionExceInfo {
    private long cost;
    private int queryRecordCount;
    private int updateRecordCount;
    private List<CalcExpression> calcExpressions = new ArrayList<CalcExpression>();
    private List<CheckExpression> checkExpressions = new ArrayList<CheckExpression>();
    private List<CheckExpression> specialFuncExpressions = new ArrayList<CheckExpression>();
    private List<IASTNode> statNodes = new ArrayList<IASTNode>();
    private List<IASTNode> evalNodes = new ArrayList<IASTNode>();
    private List<LogRow> sqlLogs = new ArrayList<LogRow>();
    private List<String> errorMessages = new ArrayList<String>();
}

