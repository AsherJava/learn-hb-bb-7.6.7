/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.asynctask.bean;

import com.jiuqi.nr.io.asynctask.bean.RegionDataSetContext;
import java.io.Serializable;

public class CSVFileImportInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private String fileKey;
    private RegionDataSetContext regionDataSetContext;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public RegionDataSetContext getRegionDataSetContext() {
        return this.regionDataSetContext;
    }

    public void setRegionDataSetContext(RegionDataSetContext regionDataSetContext) {
        this.regionDataSetContext = regionDataSetContext;
    }
}

