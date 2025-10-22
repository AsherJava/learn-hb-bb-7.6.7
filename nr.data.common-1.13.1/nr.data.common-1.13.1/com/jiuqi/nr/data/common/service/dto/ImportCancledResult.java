/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service.dto;

import com.jiuqi.nr.data.common.param.ImportFileDataRange;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportCancledResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private ImportFileDataRange importFileDataRange;
    private double progress = 0.0;
    private final List<String> successFiles = new ArrayList<String>();

    public ImportFileDataRange getImportFileDataRange() {
        return this.importFileDataRange;
    }

    public void setImportFileDataRange(ImportFileDataRange importFileDataRange) {
        this.importFileDataRange = importFileDataRange;
    }

    public double getProgress() {
        return this.progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public List<String> getSuccessFiles() {
        return this.successFiles;
    }
}

