/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessOperation;

public interface IProcessStatusWithOperation
extends IProcessStatus {
    public IProcessOperation getOperation();
}

