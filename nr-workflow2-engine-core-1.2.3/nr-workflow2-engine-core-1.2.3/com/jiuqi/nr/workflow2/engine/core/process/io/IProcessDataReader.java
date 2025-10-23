/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import java.io.Closeable;

public interface IProcessDataReader
extends Closeable {
    public String getPeriod();

    public IProcessIOInstance readNextInstance();

    public IProcessIOOperation readNextOperation();
}

