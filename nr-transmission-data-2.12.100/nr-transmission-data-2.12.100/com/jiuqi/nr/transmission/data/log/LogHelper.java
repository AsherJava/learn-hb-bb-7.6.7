/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.log;

import com.jiuqi.nr.transmission.data.log.ILogHelper;
import java.util.ArrayList;
import java.util.List;

public class LogHelper
implements ILogHelper {
    private List<String> logs;

    @Override
    public void appendLog(String log) {
        this.getLogs().add(log);
    }

    @Override
    public List<String> getLogs() {
        if (this.logs == null) {
            this.logs = new ArrayList<String>();
        }
        return this.logs;
    }
}

