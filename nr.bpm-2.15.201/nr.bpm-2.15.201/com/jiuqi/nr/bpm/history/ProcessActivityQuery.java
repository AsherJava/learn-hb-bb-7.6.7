/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.history;

import com.jiuqi.nr.bpm.common.ProcessActivity;
import java.util.List;

public interface ProcessActivityQuery {
    public List<ProcessActivity> queryActivityByProcessInstanceId(String var1, boolean var2);
}

