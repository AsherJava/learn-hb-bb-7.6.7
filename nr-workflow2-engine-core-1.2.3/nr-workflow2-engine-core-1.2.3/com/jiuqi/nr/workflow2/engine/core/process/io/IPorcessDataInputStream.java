/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import java.io.Closeable;
import java.util.List;

public interface IPorcessDataInputStream
extends Closeable {
    public String getTaskCode();

    public List<String> getPeriods();

    public ProcessDataOutputDescription readDescription() throws ProcessIOException;

    public IProcessDataReader getProcessDataReaders(String var1) throws ProcessIOException;
}

