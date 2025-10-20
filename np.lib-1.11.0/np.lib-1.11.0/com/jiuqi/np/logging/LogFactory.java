/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.logging;

import com.jiuqi.np.logging.Logger;

public class LogFactory {
    public static Logger getLogger(Class<?> cls) {
        Logger log = new Logger(cls);
        return log;
    }
}

