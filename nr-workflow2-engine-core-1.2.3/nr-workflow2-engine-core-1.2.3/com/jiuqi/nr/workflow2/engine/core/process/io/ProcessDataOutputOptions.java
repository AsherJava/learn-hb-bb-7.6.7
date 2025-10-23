/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io;

import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataType;
import com.jiuqi.nr.workflow2.engine.core.process.io.Version;
import java.util.HashSet;
import java.util.Set;

public class ProcessDataOutputOptions {
    private Set<ProcessDataType> outputDataTypes = new HashSet<ProcessDataType>();

    public Set<ProcessDataType> getOutputDataTypes() {
        return this.outputDataTypes;
    }

    public ProcessDataOutputOptions(Version version, ProcessDataType ... outputDataTypes) {
        for (ProcessDataType dataType : outputDataTypes) {
            this.outputDataTypes.add(dataType);
        }
    }
}

