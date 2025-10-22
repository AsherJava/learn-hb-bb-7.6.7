/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.single.listener;

import com.jiuqi.nr.bpm.impl.single.event.SingleFormRejectEvent;
import java.util.List;

public interface ISingleFormRejectListener {
    public void execute(SingleFormRejectEvent var1);

    public List<String> taskActionIds();
}

