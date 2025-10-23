/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessInstanceFileReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessOperationFileReader;
import java.io.IOException;

public class ProcessDataFileReader
implements IProcessDataReader {
    private final String period;
    private ProcessInstanceFileReader instanceReader;
    private ProcessOperationFileReader operationReader;

    public ProcessDataFileReader(String period) {
        this.period = period;
    }

    public void setInstanceReader(ProcessInstanceFileReader instanceReader) {
        this.instanceReader = instanceReader;
    }

    public void setOperationReader(ProcessOperationFileReader operationReader) {
        this.operationReader = operationReader;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    @Override
    public IProcessIOInstance readNextInstance() {
        return this.instanceReader == null ? null : this.instanceReader.readNextInstance();
    }

    @Override
    public IProcessIOOperation readNextOperation() {
        return this.operationReader == null ? null : this.operationReader.readNextOperation();
    }

    @Override
    public void close() throws IOException {
        if (this.instanceReader != null) {
            try {
                this.instanceReader.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.operationReader != null) {
            try {
                this.operationReader.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

