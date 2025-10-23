/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import java.util.List;

public interface IProcessTask {
    public String getId();

    public List<IUserAction> getActions();
}

