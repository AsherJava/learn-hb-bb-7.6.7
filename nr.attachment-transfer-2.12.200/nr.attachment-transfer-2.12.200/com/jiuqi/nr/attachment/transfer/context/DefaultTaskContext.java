/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.nr.attachment.transfer.context;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.nr.attachment.transfer.check.ResourceCheckFactory;
import com.jiuqi.nr.attachment.transfer.context.TaskContext;
import com.jiuqi.nr.attachment.transfer.monitor.IStatusModifier;
import com.jiuqi.nr.attachment.transfer.monitor.TaskMonitor;

public class DefaultTaskContext
implements TaskContext {
    private TaskMonitor taskMonitor;
    private ResourceCheckFactory resourceCheckFactory;
    private IStatusModifier statusModifier;
    private NpContext npContext;

    public DefaultTaskContext(TaskMonitor taskMonitor, NpContext npContext, ResourceCheckFactory resourceCheckFactory, IStatusModifier statusModifier) {
        this.taskMonitor = taskMonitor;
        this.resourceCheckFactory = resourceCheckFactory;
        this.statusModifier = statusModifier;
        this.npContext = npContext;
    }

    @Override
    public TaskMonitor getMonitor() {
        return this.taskMonitor;
    }

    @Override
    public ResourceCheckFactory getCheck() {
        return this.resourceCheckFactory;
    }

    @Override
    public IStatusModifier getModifier() {
        return this.statusModifier;
    }

    @Override
    public NpContext getContext() {
        return this.npContext;
    }
}

