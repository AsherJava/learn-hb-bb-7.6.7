/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import java.util.Calendar;

public interface IProcessIOInstance {
    public String getId();

    public IBusinessObject getBusinessObject();

    public String getCurrentUserTask();

    public String getStatus();

    public String getCurrentUserTaskCode();

    public Calendar getStartTime();

    public Calendar getUpdateTime();
}

