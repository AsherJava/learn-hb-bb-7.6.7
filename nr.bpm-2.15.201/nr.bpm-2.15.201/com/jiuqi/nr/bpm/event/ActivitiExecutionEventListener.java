/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.event;

import com.jiuqi.nr.bpm.event.ActivitiExecutionEndEvent;

public interface ActivitiExecutionEventListener {
    public void onStart(ActivitiExecutionEndEvent var1);

    public void onEnd(ActivitiExecutionEndEvent var1);
}

