/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.context;

import com.jiuqi.gcreport.reportdatasync.dto.ReportSyncFileDTO;
import com.jiuqi.gcreport.reportdatasync.enums.ReportFileFormat;
import com.jiuqi.gcreport.reportdatasync.enums.SyncTypeEnums;

public class MultilevelImportContext {
    protected SyncTypeEnums type;
    protected ReportSyncFileDTO fileDTO;
    protected ReportFileFormat fileFormat = ReportFileFormat.NRD;
    protected String importInfo;

    public SyncTypeEnums getType() {
        return this.type;
    }

    public void setType(SyncTypeEnums type) {
        this.type = type;
    }

    public ReportSyncFileDTO getFileDTO() {
        return this.fileDTO;
    }

    public void setFileDTO(ReportSyncFileDTO fileDTO) {
        this.fileDTO = fileDTO;
    }

    public String getImportInfo() {
        return this.importInfo;
    }

    public void setImportInfo(String importInfo) {
        this.importInfo = importInfo;
    }

    public ReportFileFormat getFileFormat() {
        return this.fileFormat;
    }

    public void setFileFormat(ReportFileFormat fileFormat) {
        this.fileFormat = fileFormat;
    }
}

