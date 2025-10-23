/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import java.util.Calendar;

public interface IProcessOperation {
    public String getId();

    public String getInstanceId();

    public String getFromNode();

    public String getAction();

    public String getToNode();

    public String getOperator();

    public Calendar getOperateTime();

    public String getComment();

    default public String getOperateType() {
        return null;
    }

    default public boolean isForceReport() {
        return false;
    }
}

