/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.io.common;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOInstance;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessIOOperation;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessInstanceFileWriter;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.ProcessOperationFileWriter;
import java.io.IOException;

public class ProcessDataFileWriter
implements IProcessDataWriter {
    private final String taskKey;
    private final String period;
    private ProcessInstanceFileWriter instanceWriter;
    private ProcessOperationFileWriter operationWriter;

    public ProcessDataFileWriter(String taskKey, String period) {
        this.taskKey = taskKey;
        this.period = period;
    }

    public void setInstanceWriter(ProcessInstanceFileWriter instanceWriter) {
        this.instanceWriter = instanceWriter;
    }

    public void setOperationWriter(ProcessOperationFileWriter operationWriter) {
        this.operationWriter = operationWriter;
    }

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public String getPeriod() {
        return this.period;
    }

    @Override
    public void writeInstance(IProcessIOInstance instance) {
        this.instanceWriter.write(instance);
    }

    @Override
    public void writeOperation(IProcessIOOperation operation) {
        this.operationWriter.write(operation);
    }

    @Override
    public void close() throws IOException {
        if (this.instanceWriter != null) {
            try {
                this.instanceWriter.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.operationWriter != null) {
            try {
                this.operationWriter.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

