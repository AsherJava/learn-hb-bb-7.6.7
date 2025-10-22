/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.nr.workflow2.engine.core.process.io.IProcessDataImportResult;

public class ProcessDataImportResult
implements IProcessDataImportResult {
    private int instancJumpErrorCount;
    private String instanceJumpErrorInfos;

    public int getInstancJumpErrorCount() {
        return this.instancJumpErrorCount;
    }

    public void setInstancJumpErrorCount(int instancJumpErrorCount) {
        this.instancJumpErrorCount = instancJumpErrorCount;
    }

    public String getInstanceJumpErrorInfos() {
        return this.instanceJumpErrorInfos;
    }

    public void setInstanceJumpErrorInfos(String instanceJumpErrorInfos) {
        this.instanceJumpErrorInfos = instanceJumpErrorInfos;
    }
}

