/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.obj;

import java.io.Serializable;

public class BatchExportOps
implements Serializable {
    private static final long serialVersionUID = 2112319870545002537L;
    private boolean expAppendedFile;

    public boolean isExpAppendedFile() {
        return this.expAppendedFile;
    }

    public void setExpAppendedFile(boolean expAppendedFile) {
        this.expAppendedFile = expAppendedFile;
    }
}

