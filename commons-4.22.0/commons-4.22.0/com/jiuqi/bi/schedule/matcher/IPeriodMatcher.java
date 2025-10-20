/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.schedule.matcher;

import com.jiuqi.bi.schedule.Task;
import java.util.Calendar;

public interface IPeriodMatcher {
    public boolean match(Calendar var1, Task var2);
}

