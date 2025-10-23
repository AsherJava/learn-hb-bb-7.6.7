/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 */
package com.jiuqi.nr.workflow2.form.reject.ext.event;

import com.jiuqi.nr.workflow2.form.reject.ext.event.IFormRejectEventExecutor;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;

public interface IFormRejectEvent {
    public String getEventId();

    public String getEventTitle();

    public boolean isEnabled(IProcessExecutePara var1);

    public IFormRejectEventExecutor getEventExecutor(IProcessExecutePara var1);
}

