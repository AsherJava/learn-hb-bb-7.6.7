/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.collector;

import com.jiuqi.np.dataengine.collector.FieldExecInfo;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.ArrayList;
import java.util.List;

public class FocusInfoCollector {
    private List<FieldExecInfo> fieldExecInfos = new ArrayList<FieldExecInfo>();

    public List<FieldExecInfo> getFieldExecInfos() {
        return this.fieldExecInfos;
    }

    public void collectExpression(IParsedExpression exp) {
        for (FieldExecInfo info : this.fieldExecInfos) {
            info.collectExpression(exp);
        }
    }

    public void collectSqlLogRow(LogRow logRow) {
        for (FieldExecInfo info : this.fieldExecInfos) {
            info.collectSqlLogRow(logRow);
        }
    }
}

