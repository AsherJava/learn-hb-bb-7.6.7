/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import java.io.Closeable;

public interface IProcessDataWriter
extends Closeable {
    public String getTaskKey();

    public String getPeriod();

    public void writeInstance(IProcessIOInstance var1);

    public void writeOperation(IProcessIOOperation var1);
}

