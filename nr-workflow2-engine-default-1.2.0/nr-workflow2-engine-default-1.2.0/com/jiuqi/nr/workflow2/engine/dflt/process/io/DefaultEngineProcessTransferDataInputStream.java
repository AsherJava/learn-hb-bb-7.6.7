/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader
 *  com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription
 *  com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileInputStream
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessIOException;
import com.jiuqi.nr.workflow2.engine.core.process.io.IPorcessDataInputStream;
import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataReader;
import com.jiuqi.nr.workflow2.engine.core.process.io.ProcessDataOutputDescription;
import com.jiuqi.nr.workflow2.engine.core.process.io.common.PorcessDataFileInputStream;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.TransferDirectoryInitializer;
import java.io.IOException;
import java.util.List;

public class DefaultEngineProcessTransferDataInputStream
implements IPorcessDataInputStream {
    public static final String SUFFIX_TEMP = "_temp";
    public static final String TRANSFER_DIRECTORY_NAME = "transferDir";
    private final IPorcessDataInputStream originalInputStream;
    private final TransferDirectoryInitializer initializer;
    private IPorcessDataInputStream transferInputStream;
    boolean isNeedProcess;

    public DefaultEngineProcessTransferDataInputStream(String rootPath, WorkflowSettingsDO newSettings) {
        try {
            String transferDir = rootPath + SUFFIX_TEMP;
            this.originalInputStream = new PorcessDataFileInputStream(rootPath);
            this.initializer = new TransferDirectoryInitializer(transferDir, this.originalInputStream, newSettings);
            this.isNeedProcess = this.initializer.isNeedProcess();
            if (this.isNeedProcess) {
                this.initializer.close();
                this.transferInputStream = new PorcessDataFileInputStream(transferDir);
            }
        }
        catch (Exception e) {
            try {
                this.close();
            }
            catch (IOException ioE) {
                throw new RuntimeException(ioE);
            }
            throw new RuntimeException("\u9ed8\u8ba4\u6d41\u7a0b\u5f15\u64ce\u6d41\u7a0b\u6570\u636e\u8fc1\u79fb\u6570\u636e\u8f93\u5165\u6d41[" + this.getClass().getName() + "]\u521d\u59cb\u5316\u5931\u8d25" + e);
        }
    }

    public String getTaskCode() {
        return this.originalInputStream.getTaskCode();
    }

    public List<String> getPeriods() {
        return this.originalInputStream.getPeriods();
    }

    public ProcessDataOutputDescription readDescription() throws ProcessIOException {
        if (this.isNeedProcess) {
            return this.transferInputStream.readDescription();
        }
        return this.originalInputStream.readDescription();
    }

    public IProcessDataReader getProcessDataReaders(String period) throws ProcessIOException {
        if (this.isNeedProcess) {
            return this.transferInputStream.getProcessDataReaders(period);
        }
        return this.originalInputStream.getProcessDataReaders(period);
    }

    public void close() throws IOException {
        if (this.transferInputStream != null) {
            this.transferInputStream.close();
        }
        if (this.initializer != null) {
            this.initializer.close();
        }
        if (this.originalInputStream != null) {
            this.originalInputStream.close();
        }
    }
}

