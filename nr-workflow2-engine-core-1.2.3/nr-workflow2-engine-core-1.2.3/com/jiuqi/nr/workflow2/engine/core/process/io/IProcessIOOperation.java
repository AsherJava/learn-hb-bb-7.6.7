/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import java.util.Calendar;

public interface IProcessIOOperation {
    public String getInstanceId();

    public String getSourceUserTask();

    public String getTargetUserTask();

    public String getAction();

    public Calendar getOperateTime();

    public String getOperator();

    public String getComment();

    public boolean isForceReport();
}

