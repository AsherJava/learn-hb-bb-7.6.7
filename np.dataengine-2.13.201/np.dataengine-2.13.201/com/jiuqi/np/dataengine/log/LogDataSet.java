/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.log;

import com.jiuqi.np.dataengine.log.LogMeta;
import com.jiuqi.np.dataengine.log.LogRow;
import java.util.ArrayList;
import java.util.List;

public class LogDataSet {
    private LogMeta meta;
    private List<LogRow> rows = new ArrayList<LogRow>();

    public LogDataSet() {
        this.meta = new LogMeta();
    }

    public LogMeta getMeta() {
        return this.meta;
    }

    public List<LogRow> getRows() {
        return this.rows;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(LogMeta.print()).append("\n");
        for (LogRow row : this.rows) {
            buff.append(row).append("\n");
        }
        return buff.toString();
    }
}

