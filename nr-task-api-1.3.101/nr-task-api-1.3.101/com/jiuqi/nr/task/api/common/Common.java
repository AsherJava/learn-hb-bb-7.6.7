/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.common;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Common {
    public static String printStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}

