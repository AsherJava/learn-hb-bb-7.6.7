/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import java.io.Closeable;

public interface IPorcessDataOutputStream
extends Closeable {
    public String getTaskKey();

    public void writeDescription(ProcessDataOutputDescription var1) throws ProcessIOException;

    public IProcessDataWriter getProcessDataWriter(String var1) throws ProcessIOException;
}

