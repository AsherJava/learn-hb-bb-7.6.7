/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.nr.attachment.transfer.context;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.attachment.transfer.check.ResourceCheckFactory;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;

public interface TaskContext {
    public TaskMonitor getMonitor();

    public ResourceCheckFactory getCheck();

    public IStatusModifier getModifier();

    public NpContext getContext();
}

