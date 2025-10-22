/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.common;

import com.jiuqi.nr.batch.summary.common.StringLogger;

public class PrintProcessLogger
extends StringLogger {
    @Override
    protected String newActiveMessage(StringLogger.Type type, String message) {
        return super.newActiveMessage(type, message) + " -- " + this.getProcessPercent();
    }
}

